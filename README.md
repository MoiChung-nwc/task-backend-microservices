# Task Microservices Project

Dự án gồm 6 service con:

- `taskuser`: Đăng ký, đăng nhập và mã hóa người dùng bằng JWT.
- `taskservice`: Quản lý CRUD các công việc.
- `tasksubmission`: Gửi tiến độ công việc của người dùng.
- `authservice`: Xác thực role và quyền sử dụng JWT.
- `gateway`: API Gateway định tuyến và xác thực token.
- `eureka`: Service discovery bằng Eureka Server.
