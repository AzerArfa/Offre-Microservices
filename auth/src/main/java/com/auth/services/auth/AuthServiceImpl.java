package com.auth.services.auth;

import com.auth.dto.SignupRequest;
import com.auth.dto.UserDto;
import com.auth.entity.User;
import com.auth.enums.UserRole;
import com.auth.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.USER);
        User createdUser = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        return userDto;
    }

    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(User::getUserDto).orElse(null);
    }

    @Override
    public UserDto makeAdmin(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setRole(UserRole.ADMIN);
            return userRepository.save(existingUser).getUserDto();
        }
        return null;
    }

    @PostConstruct
    public void createAdminAccount() {
        User superAdminAccount = userRepository.findByRole(UserRole.SUPERADMIN);
        if (null == superAdminAccount) {
            User user = new User();
            user.setEmail("superadmin@test.com");
            user.setName("superadmin");
            user.setRole(UserRole.SUPERADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("superadmin"));
            userRepository.save(user);
        }
    }
}
