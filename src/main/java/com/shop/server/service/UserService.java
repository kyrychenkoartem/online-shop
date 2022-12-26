package com.shop.server.service;

import com.shop.server.dao.UserDao;
import com.shop.server.exception.BadRequestException;
import com.shop.server.exception.NotFoundException;
import com.shop.server.mapper.UserMapper;
import com.shop.server.mapper.extractor.UserExtractor;
import com.shop.server.model.dto.user.UserRegistrationRequest;
import com.shop.server.model.dto.user.UserResponse;
import com.shop.server.model.dto.user.UserUpdateRequest;
import com.shop.server.model.entity.User;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.service.encoder.PasswordEncoder;
import com.shop.server.service.encoder.PasswordEncoderService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final UserExtractor extractor = UserExtractor.getExtractor();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final PasswordEncoder passwordEncoder = new PasswordEncoderService();


    public UserResponse getById(Long userId) {
        log.info("[getById] invoked with userId = [{}]", userId);
        Optional<User> optionalUser = userDao.findById(userId, extractor);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.USER_NOT_FOUND_EXCEPTION, userId);
        }
        return userMapper.toResponse(optionalUser.get());
    }

    public UserResponse getByEmail(String email) {
        log.info("[getByEmail] invoked with email = [{}]", email);
        Optional<User> optionalUser = userDao.findByEmail(email, extractor);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.USER_NOT_FOUND_EXCEPTION, email);
        }
        return userMapper.toResponse(optionalUser.get());
    }

    public List<UserResponse> getAll() {
        log.info("[getAll] invoked");
        List<User> users = userDao.findAll(extractor);
        if (users.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.USERS_NOT_FOUND_EXCEPTION);
        }
        return users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse save(UserRegistrationRequest request) {
        log.info("[save] invoked with request = [{}]", request);
        Optional<User> optionalUser = userDao.findByEmail(request.getEmail(), extractor);
        if (optionalUser.isPresent()) {
            log.error("[save] User [{}] already exists", request.getEmail());
            throw new BadRequestException(ErrorResponseStatusType.USER_ALREADY_EXISTS_EXCEPTION, request.getEmail());
        }
        String password = passwordEncoder.encode(request.getPassword());
        User user = userMapper.toEntity(request, password);
        userDao.save(user);
        return userMapper.toResponse(user);
    }

    public UserResponse update(Long userId, UserUpdateRequest request) {
        log.info("[update] invoked with request = [{}]", request);
        Optional<User> optionalUser = userDao.findById(userId, extractor);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.USER_NOT_FOUND_EXCEPTION, userId);
        }
        User user = userMapper.toEntity(request, optionalUser.get());
        userDao.update(user);
        return userMapper.toResponse(user);
    }

    public void delete(Long userId) {
        log.info("[delete] invoked with userId = [{}]", userId);
        Optional<User> optionalUser = userDao.findById(userId, extractor);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.USER_NOT_FOUND_EXCEPTION, userId);
        }
        userDao.delete(userId);
    }

    public UserService getInstance() {
        return INSTANCE;
    }
}
