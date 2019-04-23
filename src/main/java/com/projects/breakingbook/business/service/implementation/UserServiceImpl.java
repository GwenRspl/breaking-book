package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.service.UserService;
import com.projects.breakingbook.persistence.entity.User;
import com.projects.breakingbook.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.findAllUsers();
    }

    @Override
    public Optional<User> getOne(Long id) {
        return this.userRepository.findUserById(id);
    }

    @Override
    public boolean create(User user) {
        return this.userRepository.createUser(user);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public boolean update(Long id, User user) {
        Optional<User> optionalUser = this.userRepository.findUserById(id);
        if (optionalUser.isPresent()) {
            if (user.getUsername() == null) user.setUsername(optionalUser.get().getUsername());
            if (user.getAvatar() == null) user.setAvatar(optionalUser.get().getAvatar());
            if (user.getEmail() == null) user.setEmail(optionalUser.get().getEmail());
            if (user.getPassword() == null) user.setPassword(optionalUser.get().getPassword());
            return this.userRepository.updateUser(id, user);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        return this.userRepository.deleteUserById(id);
    }

    @Override
    public boolean deleteAll() {
        return this.userRepository.deleteAllUsers();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
