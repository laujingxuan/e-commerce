# API Summary
## Action API

### 1. Create User Action

- **Endpoint**: `POST /actions/create`
- **Description**: This API creates a new user action.
- **Request Header**: The API expects a JWT token in the `Authorization` header.
- **Request Body**: The API expects a JSON object representing the `UserActionDTO` to be created.
- **Response**:
    - **Success**: HTTP 201 CREATED if the user action is created successfully.
    - **Failure**:
        - HTTP 400 BAD REQUEST if the request body is invalid or contains errors.
        - HTTP 401 UNAUTHORIZED if the JWT token is invalid or expired.

### 2. Get User Action List

- **Endpoint**: `GET /actions/list/{pathUuid}`
- **Description**: This API retrieves a list of user actions based on the provided `pathUuid`.
- **Request Header**: The API expects a JWT token in the `Authorization` header.
- **Response**:
    - **Success**: HTTP 200 OK with a list of `UserActionDTO` objects containing the user action details.
    - **Failure**:
        - HTTP 401 UNAUTHORIZED if the JWT token is invalid or expired.
        - HTTP 403 FORBIDDEN if the user does not have the required authority to access the user action list.