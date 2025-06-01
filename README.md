# Hệ Thống Quản Lý Công Việc Dạng Microservices

Dự án này là một **hệ thống Microservices sử dụng Spring Boot** để quản lý công việc, được xây dựng với các công nghệ sau:

* ✅ Spring Boot
* ✅ Spring Security với JWT
* ✅ Eureka Server để khám phá dịch vụ (Service Discovery)
* ✅ Spring Cloud Gateway
* ✅ MySQL
* ✅ OpenFeign (giao tiếp giữa các dịch vụ)

---

## 📆 Tổng Quan Các Dịch Vụ

| Tên Dịch Vụ        | Mô Tả                                                                       |
| ------------------ | --------------------------------------------------------------------------- |
| **taskuser**       | Đăng ký người dùng, đăng nhập và mã hóa dữ liệu sử dụng JWT                 |
| **taskservice**    | Các thao tác CRUD để quản lý công việc                                      |
| **tasksubmission** | Xử lý cập nhật tiến độ và nộp công việc                                     |
| **authservice**    | Kiểm tra quyền truy cập dựa trên vai trò thông qua JWT (bảo mật cấp method) |
| **gateway**        | API Gateway - định tuyến request và giải mã token JWT                       |
| **eureka**         | Eureka Discovery Server - đăng ký và tìm kiếm các dịch vụ                   |

---

## 🛡️ Bảo Mật

* Xác thực bằng JWT
* Phân quyền bằng các enum `Role` và `Permission`
* Hỗ trợ làm mới token
* Dữ liệu nhạy cảm được mã hóa bằng RSA/AES

---

## 🚀 Hướng Dẫn Chạy Ứng Dụng

> Đảm bảo bạn đã cài đặt Java 17+, Maven và MySQL.

```bash
# Di chuyển đến thư mục gốc dự án
cd "D:\Spring Boot\Task"

# Khởi động Eureka Server
cd eureka
mvn spring-boot:run

# Khởi động các dịch vụ còn lại (mỗi terminal riêng biệt)
cd ../gateway && mvn spring-boot:run
cd ../taskuser && mvn spring-boot:run
cd ../taskservice && mvn spring-boot:run
cd ../tasksubmission && mvn spring-boot:run
cd ../authservice && mvn spring-boot:run
```
