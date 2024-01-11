package com.imag.master_groups.service;

import com.imag.master_groups.dto.UserDto;
import com.imag.master_groups.exception.UserDefinedException;
import com.imag.master_groups.model.User;
import com.imag.master_groups.repository.UserDao;
import com.imag.master_groups.security.CustomUserDetails;
import com.imag.master_groups.utils.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    public static Integer idValue;

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByEmail(username);
        return user.map(CustomUserDetails::new).orElseThrow(() -> new UserDefinedException("Invalid username"));
    }

    @Transactional
    public User addUser(User user) throws UserDefinedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser1 = userDao.findByEmail(auth.getName());
        List<User> users = userDao.findAll();
        if (!users.isEmpty()) {
            String userId = users.stream().sorted(Comparator.comparing(User::getUserId)
                    .reversed()).findFirst().get().getUserId();
            UserService.idValue = Integer.parseInt(userId.substring(5));
        }
        UserService.idValue = 0;
        user.setUserId(("user_" + (++UserService.idValue)));
        user.setFirstName(Validation.nameValidation(user.getFirstName()));
        user.setLastName(Validation.nameValidation(user.getLastName()));
        Optional<User> optionalUser = userDao.findByEmail(user.getEmail().toUpperCase());
        if (optionalUser.isEmpty()) {
            user.setEmail(Validation.mailValidation(user.getEmail()));
        } else
            throw new UserDefinedException("Email is already exists....!");
        user.setPassword(passwordEncoder.encode(Validation.passwordValidation(user.getPassword())));
        user.setAddress(Validation.addressValidation(user.getAddress()));
        user.setPhoneNumber(Validation.mobileValidation(user.getPhoneNumber()));
        user.setCreatedBy(auth.getName());
        user.setUpdatedBy(auth.getName());
        user.setStatus(1);
        user.setUserCreatedAt(Timestamp.from(Instant.now()));
        user.setCreatedAt(Timestamp.from(Instant.now()));
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        user.setDeletedAt(null);
        return userDao.save(user);
    }

    @Transactional
    public User updateUserDetails(String userId, UserDto userDto) throws UserDefinedException {
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser1 = userDao.findByEmail(auth.getName());
        Optional<User> optionalUser = userDao.findById(userId.toUpperCase());
        if (optionalUser.isEmpty())
            throw new UserDefinedException("User not found...!");
        User user = optionalUser.get();
        if (userDto.getFirstName() != null && !userDto.getFirstName().isEmpty()) {
            user.setFirstName(Validation.nameValidation(userDto.getFirstName()));
        }
        if (userDto.getLastName() != null && !userDto.getLastName().isEmpty()) {
            user.setFirstName(Validation.nameValidation(userDto.getLastName()));
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            Optional<User> optionalUserEmail = userDao.findByEmail(userDto.getEmail().toUpperCase());
            if (optionalUserEmail.isEmpty())
                user.setEmail(Validation.mailValidation(userDto.getEmail()));
            else
                throw new UserDefinedException("Email is already exists....!");
        }
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(Validation.passwordValidation(userDto.getPassword())));
        }
        if (userDto.getAddress() != null && !userDto.getAddress().isEmpty()) {
            user.setAddress(Validation.addressValidation(userDto.getAddress()));
        }
        if (userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(Validation.mobileValidation(userDto.getPhoneNumber()));
        }
        user.setUpdatedBy(optionalUser1.get().getUserId());
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        return userDao.save(user);
    }

    public List<User> getUsers() throws UserDefinedException {
        List<User> users = userDao.findByStatus(1);
        if (users.isEmpty()) throw new UserDefinedException("there is no data in Users table");
        return users;
    }

    @Transactional
    public String removeUser(String userId) throws UserDefinedException {
        Optional<User> optionalUser = userDao.findById(userId.toUpperCase());
        if (optionalUser.isEmpty() || optionalUser.get().getStatus() == 0)
            throw new UserDefinedException("User is not found");
        User user = optionalUser.get();
        user.setStatus(0);
        user.setDeletedAt(Timestamp.from(Instant.now()));
        userDao.save(user);
        return "User deleted temporarily...!";
    }

    @Transactional
    public String deleteUser(String userId) throws UserDefinedException {
        Optional<User> optionalUser = userDao.findById(userId.toUpperCase());
        if (optionalUser.isEmpty())
            throw new UserDefinedException("user is not found");
        userDao.delete(optionalUser.get());
        return "User deleted Permanently...!";
    }

    public String getUserIdByUsername(String username) {
        User user = userDao.findByEmail(username).get();
        return user.getUserId();
    }
}
