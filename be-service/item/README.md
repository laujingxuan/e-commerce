# API Summary
## Item API

### 1. Get All Items

- **Endpoint**: `GET /items`
- **Description**: This API retrieves all items.
- **Response**:
    - **Success**: HTTP 200 OK with a list of `ItemDTO` objects containing the item details.
    - **Failure**: HTTP 500 INTERNAL SERVER ERROR if an error occurs during the retrieval of items.

### 2. Get Item Details

- **Endpoint**: `GET /items/{id}`
- **Description**: This API retrieves the details of a specific item based on the provided `id`.
- **Response**:
    - **Success**: HTTP 200 OK with the `ItemDTO` object containing the item details.
    - **Failure**: HTTP 404 NOT FOUND if the item is not found.

### 3. Create Item

- **Endpoint**: `POST /items`
- **Description**: This API creates a new item.
- **Request Header**: The API expects a JWT token in the `Authorization` header.
- **Request Body**: The API expects a JSON object representing the `ItemDTO` to be created.
- **Response**:
    - **Success**: HTTP 201 CREATED if the item is created successfully.
    - **Failure**:
        - HTTP 400 BAD REQUEST if the request body is invalid or contains errors.
        - HTTP 401 UNAUTHORIZED if the JWT token is invalid or expired.

### 4. Update Item

- **Endpoint**: `PUT /items`
- **Description**: This API updates an existing item.
- **Request Header**: The API expects a JWT token in the `Authorization` header.
- **Request Body**: The API expects a JSON object representing the updated `ItemDTO`.
- **Response**:
    - **Success**: HTTP 200 OK if the item is updated successfully.
    - **Failure**:
        - HTTP 400 BAD REQUEST if the request body is invalid or contains errors.
        - HTTP 401 UNAUTHORIZED if the JWT token is invalid or expired.

### 5. Delete Item

- **Endpoint**: `DELETE /items/{id}`
- **Description**: This API deletes an item based on the provided `id`.
- **Request Header**: The API expects a JWT token in the `Authorization` header.
- **Response**:
    - **Success**: HTTP 204 NO CONTENT if the item is deleted successfully.
    - **Failure**:
        - HTTP 400 BAD REQUEST if the item cannot be deleted.
        - HTTP 401 UNAUTHORIZED if the JWT token is invalid or expired.