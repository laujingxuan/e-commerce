package com.example.user.service;

import com.example.shared.enums.Role;
import com.example.shared.exception.CustomUnauthorizedException;
import com.example.user.DTO.UserActionDTO;
import com.example.user.DTO.UserDetailsDTO;
import com.example.user.dao.UserRepository;
import com.example.user.entity.User;
import com.example.user.modelMapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    private WebClient webClient;

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, WebClient webClient, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.webClient = webClient;
        this.userMapper = userMapper;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public UserDetailsDTO getUserDetails(String pathUuid, String userUuid, String authority, String jwtToken, String baseUrl) {
        if (Role.valueOf(authority) != Role.ROLE_ADMIN && !pathUuid.equals(userUuid)) {
            throw new CustomUnauthorizedException("User is unauthorized");
        }
        Optional<User> optionalUser = userRepository.findById(userUuid);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User info is not found");
        }
        User user = optionalUser.get();
        UserDetailsDTO userDetailsDTO = userMapper.mapToDTO(user);
        Mono<List<UserActionDTO>> response = webClient.get()
                .uri(baseUrl + "/actions/list/" + pathUuid)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.isError(), clientResponse -> {
                    throw new HttpClientErrorException(clientResponse.statusCode());
                })
                // there are other options like .bodyToMono as well
                .bodyToFlux(UserActionDTO.class)
                .collectList();
        // use response.subscribe if logic dont need blocking
        List<UserActionDTO> actionDTOList = response.block();
        userDetailsDTO.setUserActionDTOList(actionDTOList);
        return userDetailsDTO;
    }

    @Override
    public boolean isUserUuidValid(String pathUuid, String userUuid, String authority) {
        if (Role.valueOf(authority) != Role.ROLE_ADMIN && !pathUuid.equals(userUuid)) {
            throw new CustomUnauthorizedException("User is unauthorized");
        }
        Optional<User> optionalUser = userRepository.findById(pathUuid);
        if (optionalUser.isPresent()) {
            return true;
        }
        return false;
    }
}