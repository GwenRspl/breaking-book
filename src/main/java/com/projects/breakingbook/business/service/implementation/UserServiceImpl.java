package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.business.service.UserService;
import com.projects.breakingbook.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.findAllUsers();
    }

    @Override
    public Optional<User> getOne(final Long id) {
        return this.userRepository.findUserById(id);
    }

    @Override
    public boolean create(final User user) {
        return this.userRepository.createUser(user);
    }

    @Override
    public Optional<User> findUserByUsername(final String username) {
        return this.userRepository.findUserByUsername(username);
    }

    @Override
    public boolean update(final Long id, final User user) {
        final Optional<User> optionalUser = this.userRepository.findUserById(id);
        if (optionalUser.isPresent()) {
            if (user.getUsername() == null) {
                user.setUsername(optionalUser.get().getUsername());
            }
            if (user.getAvatar() == null) {
                user.setAvatar(optionalUser.get().getAvatar());
            }
            if (user.getEmail() == null) {
                user.setEmail(optionalUser.get().getEmail());
            }
            if (user.getPassword() == null) {
                user.setPassword(optionalUser.get().getPassword());
            }
            return this.userRepository.updateUser(id, user);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(final Long id) {
        return this.userRepository.deleteUserById(id);
    }

    @Override
    public Boolean existsByUsername(final String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(final String email) {
        return this.userRepository.existsByEmail(email);
    }
}
