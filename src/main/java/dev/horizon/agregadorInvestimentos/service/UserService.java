package dev.horizon.agregadorInvestimentos.service;

import dev.horizon.agregadorInvestimentos.repository.UserRepository;
import dev.horizon.agregadorInvestimentos.controller.CreateUserDto;
import dev.horizon.agregadorInvestimentos.controller.UpdateUserDto;
import dev.horizon.agregadorInvestimentos.entity.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID CreateUser(CreateUserDto createUserDto) {

        var entity = new User(null, createUserDto.username(), createUserDto.email(), createUserDto.password(), Instant.now(), null);

        var userSaved =  userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);
        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {
        var userEntity = userRepository.findById(UUID.fromString(userId));

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }

            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }
}
