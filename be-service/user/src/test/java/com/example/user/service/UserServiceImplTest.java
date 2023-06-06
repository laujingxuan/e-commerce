package com.example.user.service;

import com.example.shared.enums.Role;
import com.example.user.DTO.UserActionDTO;
import com.example.user.DTO.UserDetailsDTO;
import com.example.user.dao.UserRepository;
import com.example.user.entity.User;
import com.example.user.modelMapper.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// @ExtendWith is JUnit 5 annotation
@ExtendWith({SpringExtension.class, MockitoExtension.class})
//// The properties attribute takes an array of key-value pairs, where the key is the property name and the value is the new value you want to set.
//@TestPropertySource(properties = {"action.baseurl=newValue"})
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    // When you annotate a field or parameter with @Mock, Mockito creates a mock object of the specified type and injects it into the test class or method. The mock object can then be used to define the behavior you want to simulate during the test.
    @Mock
    private UserMapper userMapper;

    // When using @InjectMocks, you should use the implementation class, not the interface.
    @InjectMocks
    private UserServiceImpl userService;

    private static MockWebServer mockBackEnd;

    // @BeforeAll and @AfterAll methods run once before and after all test cases in the class, respectively.
    @BeforeAll
    static void setUpAll() throws IOException {
        System.out.println("Before all tests");
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    // @BeforeEach and @AfterEach methods run before and after each individual test case, respectively.
    @BeforeEach
    void setUp() {
        System.out.println("Before each test");
        userService = new UserServiceImpl(userRepository, WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build(), userMapper);
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each test");
    }

    @AfterAll
    static void tearDownAll() throws IOException {
        System.out.println("After all tests");
        mockBackEnd.shutdown();
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        User testUser = new User("John", "password", "email");
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(testUser);
        // assertEquals 3rd param, message is optional. To display custom error message
        assertEquals(testUser, userService.loadUserByUsername(testUser.getUsername()), "User returned not match");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        User testUser = new User("John", "password", "email");
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(testUser.getUsername());
        });
        assertEquals(exception.getMessage(), "User not found");
    }

    @Test
    void testGetUserDetails_ValidInput_ReturnsUserDetailsDTO() throws JsonProcessingException, InterruptedException {
        // Mock UserRepository behavior
        String userUuid = "123";
        String pathUuid = "123";
        String authority = "ROLE_USER";
        String jwtToken = "testingjwttoken123456";
        String username = "John";
        String password = "password";
        String email = "email";
        User testUser = new User(username, password, email, Role.valueOf(authority));
        testUser.setId(userUuid);
        when(userRepository.findById(userUuid)).thenReturn(Optional.of(testUser));

        // Mock UserMapper behavior
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(userUuid, username, email, Role.valueOf(authority));
        when(userMapper.mapToDTO(testUser)).thenReturn(userDetailsDTO);

        // Mock WebClient behavior
        UserActionDTO mockUserAction = new UserActionDTO("test", 1, "CREATE_ITEM", Timestamp.valueOf(LocalDateTime.now()));
        List<UserActionDTO> mockList = new ArrayList<>();
        mockList.add(mockUserAction);
        ObjectMapper objectMapper = new ObjectMapper();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockList))
                .addHeader("Content-Type", "application/json"));


        // Invoke the method under test
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        UserDetailsDTO result = userService.getUserDetails(pathUuid, userUuid, authority, jwtToken, baseUrl);
        // Verify the expected behavior
        assertNotNull(result);
        assertEquals(userDetailsDTO, result);

        // Verify the request received during the test case
        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/actions/list/" + userUuid, recordedRequest.getPath());

        // Verify the interactions with mock objects
        verify(userRepository).findById(userUuid);
        verify(userMapper).mapToDTO(testUser);
    }
}
