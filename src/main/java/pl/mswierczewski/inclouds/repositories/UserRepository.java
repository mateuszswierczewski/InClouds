package pl.mswierczewski.inclouds.repositories;

import org.springframework.stereotype.Repository;
import pl.mswierczewski.inclouds.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
