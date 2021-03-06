package com.ipan97.springbootauditsample.service.impl;

import com.ipan97.springbootauditsample.domain.User;
import com.ipan97.springbootauditsample.repository.elasticsearch.UserSearchRepository;
import com.ipan97.springbootauditsample.repository.jpa.UserRepository;
import com.ipan97.springbootauditsample.service.UserService;
import com.ipan97.springbootauditsample.service.dto.UserDto;
import com.ipan97.springbootauditsample.service.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserSearchRepository userSearchRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserSearchRepository userSearchRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserDto> search(Pageable pageable) {
        return userSearchRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userRepository.save(user);
        userSearchRepository.save(user);
        return userMapper.toDto(user);
    }
}
