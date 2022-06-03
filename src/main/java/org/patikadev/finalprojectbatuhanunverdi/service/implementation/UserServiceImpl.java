package org.patikadev.finalprojectbatuhanunverdi.service.implementation;

import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.converter.UserConverter;
import org.patikadev.finalprojectbatuhanunverdi.entity.Account;
import org.patikadev.finalprojectbatuhanunverdi.entity.User;
import org.patikadev.finalprojectbatuhanunverdi.exception.BusinessServiceOperationException;
import org.patikadev.finalprojectbatuhanunverdi.model.request.UserRequest;
import org.patikadev.finalprojectbatuhanunverdi.repository.AccountRepository;
import org.patikadev.finalprojectbatuhanunverdi.repository.UserRepository;
import org.patikadev.finalprojectbatuhanunverdi.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserConverter userConverter;
    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    @Override
    public void addUser(UserRequest userRequest) {
        User user = userConverter.toUser(userRequest);
        if (userRepository.findByTc(user.getTc()).isPresent()) {
            throw new BusinessServiceOperationException.UserAlreadyHaveException("This user is already registered in our service");
        }
        if (userRepository.findByTc(user.getEmail()).isPresent()) {
            throw new BusinessServiceOperationException.UserAlreadyHaveException("This email is already registered in our service");
        }
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new BusinessServiceOperationException.UserAlreadyHaveException("This user is already registered in our service");
        }
        userRepository.save(user);

    }

    @Override
    public void updateUser(Long id, UserRequest userRequest) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessServiceOperationException.UserNotFound("Customer not found"));
        if (!user.getEmail().equals(userRequest.getEmail())) {
            user.setEmail(userRequest.getEmail());
        }
        if (!user.getPhoneNumber().equals(userRequest.getPhoneNumber())) {
            user.setPhoneNumber(userRequest.getPhoneNumber());
        }
        if (!passwordEncoder.matches(user.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        user.setName(userRequest.getName());
        user.setDOB(userRequest.getDOB());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.getById(id);
        if (accountRepository.findByUser(user).isEmpty()) {
            userRepository.deleteById(id);
        } else {
            List<Account> accounts = accountRepository.findByUser(user).stream().toList();
            for (Account account : accounts) {
                if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                    throw new BusinessServiceOperationException.AccountHasMoneyException("You have money in this account. " +
                            "You cant delete this account without withdrawing the money");
                } else if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
                    throw new BusinessServiceOperationException.AccountHasDebtException("Your account has debt");
                }
            }
            userRepository.deleteById(id);
        }
    }
}
