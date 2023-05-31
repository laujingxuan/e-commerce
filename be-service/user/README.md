# API Summary

## Login API

### 1. Login API

- **Endpoint**: `POST /login`
- **Description**: This API is used to authenticate a user by providing their username and password. It performs the authentication process and returns an authentication token if the authentication is successful.
- **Request Body**: `LoginRequest` object containing the username and password.
- **Response**:
    - **Success**: HTTP 200 OK with an `AuthenticationResponse` object containing the authentication token.
    - **Failure**: HTTP 401 UNAUTHORIZED.

### 2. Test API

- **Endpoint**: `GET /get_test`
- **Description**: This API is a test endpoint that returns a simple string response for testing purposes.
- **Response**: String response with the value "test".

## User API

### 1. Get User Details

- **Endpoint**: `GET /users/{pathUuid}`
- **Description**: This API retrieves the details of a user based on the provided `pathUuid`. The user details are returned as a `UserDetailsDTO` object.
- **Request Header**: The API expects a JWT token in the `Authorization` header.
- **Response**:
    - **Success**: HTTP 200 OK with the `UserDetailsDTO` object containing the user details.
    - **Failure**:
        - HTTP 401 UNAUTHORIZED if the JWT token is invalid or expired.
        - HTTP 500 INTERNAL SERVER ERROR if an error occurs during the retrieval of user details.

### 2. Check User UUID Validity

- **Endpoint**: `GET /users/validity/{pathUuid}`
- **Description**: This API checks the validity of a user UUID based on the provided `pathUuid`. It verifies if the user UUID belongs to the authenticated user.
- **Request Header**: The API expects a JWT token in the `Authorization` header.
- **Response**:
    - **Success**: HTTP 200 OK if the user UUID is valid.
    - **Failure**: HTTP 400 BAD REQUEST if the user UUID is not valid or does not belong to the authenticated user.
        - HTTP 401 UNAUTHORIZED if the JWT token is invalid or expired.