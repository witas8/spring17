package com.example.spring17.service.user;

import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceDeleter {

    private final UserRepo userRepo;

    public void deleteUserById(Long id) {
        userRepo.findById(id)
                .ifPresentOrElse(user -> {
                            log.info("User is deleted from the database {}", user);
                            userRepo.delete(user);
                        },
                        () -> {
                            throw new NotFoundException("User", "id", id.toString());
                        });
    }
}
