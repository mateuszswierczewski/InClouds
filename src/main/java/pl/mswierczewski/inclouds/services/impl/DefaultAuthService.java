package pl.mswierczewski.inclouds.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.mswierczewski.inclouds.dtos.auth.AuthenticationRequest;
import pl.mswierczewski.inclouds.dtos.auth.AuthenticationResponse;
import pl.mswierczewski.inclouds.dtos.user.UserResponse;
import pl.mswierczewski.inclouds.exceptions.api.JWTException;
import pl.mswierczewski.inclouds.exceptions.api.UserException;
import pl.mswierczewski.inclouds.mappers.UserMapper;
import pl.mswierczewski.inclouds.models.User;
import pl.mswierczewski.inclouds.services.AuthService;
import pl.mswierczewski.inclouds.services.UserService;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:jwt.properties")
public class DefaultAuthService implements AuthService {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_PREFIX = "Bearer ";
    public static final String AUTHORITIES = "authorities";
    public static final String REFRESH = "ref_exp";

    @Value("${jwt.secretkey}")
    private String secretKeyString;

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Bean
    private SecretKey secretKey(){
        return Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    public DefaultAuthService(UserService userService, UserMapper userMapper, @Lazy AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            if (!userService.existsByEmail(request.getEmail())) {
                throw UserException.USER_NOT_FOUND;
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();
            String token = generateJWTToken(user);

            UserResponse userResponse = userMapper.mapToUserResponse(user);

            return new AuthenticationResponse(token, userResponse);

        } catch (DisabledException e) {
            throw UserException.ACCOUNT_DISABLED;
        } catch (LockedException e) {
            throw UserException.ACCOUNT_LOCKED;
        } catch (BadCredentialsException e) {
            throw UserException.BAD_CREDENTIALS;
        }
    }

    @Override
    public void authorizeRequest(HttpServletRequest httpServletRequest) {
        try {
            Optional<String> tokenOptional = extractTokenFromRequest(httpServletRequest);

            tokenOptional.ifPresent(
                    token -> {
                        Authentication authentication = validateToken(token);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
            );
        } catch (ExpiredJwtException e) {
            throw JWTException.EXPIRED_JWT;
        } catch (MalformedJwtException e) {
            throw JWTException.MALFORMED_JWT;
        } catch (SignatureException e) {
            throw JWTException.SIGNATURE_ERROR;
        }
    }

    @Override
    public String refreshToken(HttpServletRequest httpServletRequest) {
        Optional<String> tokenOptional = extractTokenFromRequest(httpServletRequest);
        String token = tokenOptional.orElseThrow(() -> JWTException.JWT_NOT_FOUND);

        Claims claims;

        try {
            claims = getClaims(token);
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }

        Instant refreshExpiration = getRefreshExpiration(claims);

        if (refreshExpiration.isAfter(Instant.now())) {
            String email = claims.getSubject();
            User user = userService.getUserByEmail(email);

            token = generateJWTToken(user);

            Authentication authentication = validateToken(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return token;
        } else throw JWTException.EXPIRED_JWT;
    }

    private String generateJWTToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(AUTHORITIES, user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet())
        );

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(1, ChronoUnit.MINUTES);
        Instant refreshExpration = issuedAt.plus(35, ChronoUnit.MINUTES);

        claims.put(REFRESH, refreshExpration.toEpochMilli());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .addClaims(claims)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .signWith(secretKey())
                .compact();
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        String tokenString = request.getHeader(AUTHORIZATION_HEADER);

        if (tokenString == null || !tokenString.startsWith(AUTHORIZATION_PREFIX))
            return Optional.empty();
        else
            return Optional.of(tokenString.replace(AUTHORIZATION_PREFIX, ""));
    }

    public Authentication validateToken(String token) {
        Claims claims = getClaims(token);

        String username = claims.getSubject();

        if (!userService.existsByEmail(username)){
            throw UserException.USER_NOT_FOUND;
        }

        Set<SimpleGrantedAuthority> authorities = getAuthorities(claims);

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
        );
    }

    @SuppressWarnings("unchecked")
    private Set<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        List<String> authorities = (List<String>) claims.get(AUTHORITIES);

        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    private Instant getRefreshExpiration(Claims claims) {
        return Instant.ofEpochSecond((Long) claims.get(REFRESH));
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }



}
