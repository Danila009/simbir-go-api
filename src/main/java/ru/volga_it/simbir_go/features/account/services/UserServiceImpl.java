package ru.volga_it.simbir_go.features.account.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.volga_it.simbir_go.common.exceptions.BadRequestException;
import ru.volga_it.simbir_go.common.exceptions.ResourceNotFoundException;
import ru.volga_it.simbir_go.features.account.dto.admin.AdminUpdateUserParams;
import ru.volga_it.simbir_go.features.account.dto.params.UpdateUserParams;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;
import ru.volga_it.simbir_go.features.account.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
                .orElseThrow(() -> new ResourceNotFoundException("User not found by username"));
    }

    @Override
    @Transactional
    public UserEntity add(UserEntity user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new BadRequestException("Username is busy");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(Long id, AdminUpdateUserParams params) {
        if(userRepository.findByUsername(params.username()).isPresent())
            throw new BadRequestException("Username is busy");

        UserEntity user = getById(id);
        user.setPassword(passwordEncoder.encode(params.password()));
        user.setUsername(params.username());
        user.setIsAdmin(params.isAdmin());
        user.setBalance(params.balance());

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(Long id, UpdateUserParams params) {
        if(userRepository.findByUsername(params.username()).isPresent())
            throw new BadRequestException("Username is busy");

        UserEntity user = getById(id);
        user.setUsername(params.username());
        user.setPassword(passwordEncoder.encode(params.password()));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void plusBalance(Long id, Double sum) {
        UserEntity user = getById(id);
        user.setBalance(user.getBalance() + sum);
        userRepository.save(user);
    }
}
