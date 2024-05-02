package com.bookmyshow.respositories;

import com.bookmyshow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long userId); //Select query for user using user id.

    Optional<User> findAllByEmail(String email);

    @Override
    User save(User user);
}
