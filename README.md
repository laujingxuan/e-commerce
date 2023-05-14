# e-commerce
An e-commerce system with React JS for FE and Spring for BE

3 services for BE. Communicated with Kafka
1) User service
2) Item service
3) Email service

User service
1) Login
- JWT token
2) Logout
- Invalid JWT token
3) User signup
- Send email after a new user signup(Trigger email service through kafka)

Email service
1) Send email

Item service
1) Get item list
2) Get item details
3) Create item
4) Update item
5) Delete item