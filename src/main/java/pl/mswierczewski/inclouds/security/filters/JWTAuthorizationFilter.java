package pl.mswierczewski.inclouds.security.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.mswierczewski.inclouds.dtos.error.ApiErrorResponse;
import pl.mswierczewski.inclouds.exceptions.api.ApiException;
import pl.mswierczewski.inclouds.services.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

   private final AuthService authService;

    public JWTAuthorizationFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            authService.authorizeRequest(httpServletRequest);
            filterChain.doFilter(httpServletRequest, httpServletResponse);

        } catch (ApiException e) {
            if (e.getMessage().equals("JWT is expired")) {
                try {
                    String token = authService.refreshToken(httpServletRequest);
                    httpServletResponse.setHeader("Authorization", token);
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                } catch (ApiException ex) {
                    abortRequest(httpServletResponse, ex);
                }
            } else {
                abortRequest(httpServletResponse, e);
            }
        }
    }

    private void abortRequest(HttpServletResponse httpServletResponse, ApiException e) throws IOException {
        ApiErrorResponse response = new ApiErrorResponse(
                e.getClass().toString(),
                e.getMessage(),
                e.getStatus()
        );

        httpServletResponse.setStatus(e.getStatus().value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(convertObjectToJson(response));
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        return mapper.writeValueAsString(object);
    }
}
