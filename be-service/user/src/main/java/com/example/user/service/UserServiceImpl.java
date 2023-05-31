package com.example.user.service;

import com.example.shared.enums.Role;
import com.example.user.DTO.UserActionDTO;
import com.example.user.DTO.UserDetailsDTO;
import com.example.user.dao.UserRepository;
import com.example.user.entity.User;
import com.example.user.modelMapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            String errMsg = e.getMessage();
            if (e.getMessage().contains("email")) {
                errMsg = "Email constraints not met";
            } else if (e.getMessage().contains("username")) {
                errMsg = "Username constraints not met";
            }
            logger.warn("Save user: {}", errMsg);
        } catch (Exception e) {
            logger.error("Error while saving user", e);
        }
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
    public UserDetailsDTO getUserDetails(String pathUuid, String userUuid, String authority, String jwtToken) {
        try {
            if (Role.valueOf(authority) != Role.ROLE_ADMIN && !pathUuid.equals(userUuid)) {
                throw new IllegalAccessException("User is unauthorized");
            }
            Optional<User> optionalUser = userRepository.findById(userUuid);
            if (!optionalUser.isPresent()) {
                throw new UsernameNotFoundException("User info is not found");
            }
            User user = optionalUser.get();
            UserDetailsDTO userDetailsDTO = userMapper.mapToDTO(user);
            Mono<List<UserActionDTO>> response = webClient.get()
                    .uri("http://localhost:8082/actions/list/" + pathUuid)
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
        } catch (Exception e) {
            logger.error("Get user details exception", e);
            return null;
        }
    }

    @Override
    public boolean isUserUuidValid(String pathUuid, String userUuid, String authority) {
        try {
            if (Role.valueOf(authority) != Role.ROLE_ADMIN && !pathUuid.equals(userUuid)) {
                throw new IllegalAccessException("User is unauthorized");
            }
            Optional<User> optionalUser = userRepository.findById(pathUuid);
            if (optionalUser.isPresent()) {
                return true;
            }
        } catch (IllegalAccessException e) {
            logger.warn("Authorization failed: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error for isUserUuidValid", e);
        }
        return false;
    }
}
