package com.bookmyshow.services;

import com.bookmyshow.exceptions.UserNotFoundException;
import com.bookmyshow.exceptions.WrongUserCredentialsException;
import com.bookmyshow.models.User;
import com.bookmyshow.respositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(String email, String password) throws UserNotFoundException, WrongUserCredentialsException {
        User user = null;
        Optional<User> optionalUser = userRepository.findAllByEmail(email);

        if(optionalUser.isPresent()) {
            //Call the login method
            user = signIn(email, password);
        } else {
            //Create and register the user.
            user = new User();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setEmail(email);
            user.setPassword(bCryptPasswordEncoder.encode(password));

            user = userRepository.save(user);
            System.out.println("Sign up successful");
        }


        return user;
    }

    public User signIn(String email, String password) throws UserNotFoundException, WrongUserCredentialsException {
        Optional<User> optionalUser = userRepository.findAllByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User user = optionalUser.get();
        String userPassword = user.getPassword();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder.matches(password, userPassword)) {
            System.out.println("Sign In Successful");
        } else {
            throw new WrongUserCredentialsException("Wrong password");
        }

        return  user;
    }
}
