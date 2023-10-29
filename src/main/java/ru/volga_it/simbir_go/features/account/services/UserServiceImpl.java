package ru.volga_it.simbir_go.features.account.services;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.volga_it.simbir_go.common.exceptions.ResourceNotFoundException;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;
import ru.volga_it.simbir_go.features.account.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<UserEntity> getAll(Integer offset, Integer limit) {
        return userRepository.findAll(PageRequest.of(offset, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public UserEntity add(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(Long id, UserEntity updatedUser) {
        // Check user not found by id
        getById(id);
        updatedUser.setId(id);
        userRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void plusBalance(Long id, Double number) {
        UserEntity user = getById(id);
        user.setBalance(user.getBalance() + number);
        userRepository.save(user);
    }
}
