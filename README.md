# Hệ Thống Đặt Lịch Dịch Vụ

Một hệ thống đặt lịch dịch vụ toàn diện được xây dựng bằng Java Servlets, JSP và SQL Server, cho phép người dùng đặt lịch hẹn với các dịch vụ và nhân viên.

## Tổng Quan

Dự án này triển khai một nền tảng đặt lịch dịch vụ cho phép người dùng đăng ký tài khoản, đặt lịch hẹn với các dịch vụ và nhân viên, quản lý lịch hẹn cá nhân, và cung cấp các công cụ thống kê cho quản trị viên. Hệ thống tuân theo mô hình kiến trúc MVC để phân tách rõ ràng các thành phần và dễ bảo trì.

## Tính Năng Chính

### 1. Quản Lý Người Dùng & Xác Thực (Sinh viên 1)

- **Đăng Ký**

  - Tạo tài khoản mới với thông tin: Họ tên, email, mật khẩu, xác nhận mật khẩu, số điện thoại
  - Xác thực thông tin đầu vào

- **Đăng Nhập**

  - Đăng nhập hệ thống với email và mật khẩu
  - Tùy chọn ghi nhớ đăng nhập

- **Quên Mật Khẩu**

  - Gửi email reset mật khẩu
  - Xác nhận bằng mã xác nhận
  - Đặt mật khẩu mới

- **Hồ Sơ Cá Nhân**

  - Cập nhật thông tin: Họ tên, ảnh đại diện, số điện thoại, giới tính, ngày sinh

- **Đổi Mật Khẩu**

  - Thay đổi mật khẩu với xác nhận mật khẩu cũ

- **Quản Lý Vai Trò Người Dùng**
  - Phân quyền (admin, khách đặt lịch, nhân viên)
  - Gán quyền và hiển thị danh sách người dùng

### 2. Chức Năng Đặt Lịch & Quản Lý Lịch Cá Nhân (Sinh viên 2)

- **Tạo Lịch Mới**

  - Đặt cuộc hẹn theo dịch vụ, giờ, nhân viên
  - Thông tin bao gồm: Ngày, giờ, dịch vụ, người tiếp nhận, ghi chú

- **Danh Sách Lịch Đã Đặt**

  - Hiển thị toàn bộ lịch đã đặt với thông tin: Ngày, giờ, dịch vụ, trạng thái
  - Chức năng hủy lịch

- **Hủy Lịch**

  - Xác nhận hủy lịch, cập nhật trạng thái
  - Nhập lý do hủy

- **Chi Tiết Lịch**

  - Hiển thị thông tin chi tiết: Ngày, giờ, dịch vụ, người thực hiện, trạng thái

- **Lịch Sử Đặt Lịch**
  - Xem toàn bộ lịch đã thực hiện
  - Tìm kiếm và phân trang

### 3. Quản Lý Dịch Vụ & Nhân Sự (Sinh viên 3)

- **Danh Sách Dịch Vụ**

  - Quản lý các dịch vụ có thể đặt lịch
  - Thông tin: Tên, mô tả, giá, thời gian thực hiện, trạng thái

- **Thêm/Sửa Dịch Vụ**

  - Tạo mới hoặc cập nhật dịch vụ
  - Thông tin: Tên, mô tả, giá, hình ảnh, thời gian, trạng thái

- **Danh Sách Nhân Viên**

  - Quản lý người thực hiện dịch vụ (bác sĩ, tư vấn viên...)
  - Thông tin: Tên, chuyên môn, email, số điện thoại, ảnh đại diện, trạng thái

- **Thêm/Sửa Nhân Viên**

  - Nhập thông tin nhân viên mới
  - Thông tin: Họ tên, chuyên môn, giờ làm, mô tả ngắn, trạng thái

- **Phân Ca Làm Việc**
  - Gán khung giờ làm việc cho từng nhân viên
  - Chọn người, chọn ngày, chọn giờ bắt đầu/kết thúc

### 4. Thống Kê & Báo Cáo (Sinh viên 4)

- **Tổng Quan Đặt Lịch**

  - Biểu đồ số lượng lịch theo ngày, trạng thái
  - Hiển thị số lượt đặt, số lượt hủy

- **Thống Kê Theo Dịch Vụ**

  - Số lượt đặt theo từng loại dịch vụ
  - Thông tin: Dịch vụ, số lượt, % tăng trưởng

- **Thống Kê Theo Nhân Viên**

  - Tần suất hoạt động của nhân viên
  - Thông tin: Tên nhân viên, số lịch phục vụ, thời gian làm việc

- **Thống Kê Theo Người Dùng**

  - Top người dùng đặt nhiều, tỷ lệ hủy
  - Thông tin: Tên, số lịch, tỷ lệ hủy

- **Xuất Báo Cáo Excel/PDF**
  - Tùy chọn lọc theo ngày, dịch vụ, nhân sự
  - Chức năng xuất file

## Công Nghệ Sử Dụng

- **Backend**: Java Servlets, JSP
- **Frontend**: HTML, CSS, JavaScript, Bootstrap
- **Cơ Sở Dữ Liệu**: Microsoft SQL Server
- **Máy Chủ**: Apache Tomcat 10.1.x
- **Xác Thực**: Triển khai tùy chỉnh với mã hóa mật khẩu
- **Biểu Đồ**: Chart.js
- **Xuất Báo Cáo**: Apache POI (Excel), iText (PDF)

## Cấu Trúc Dự Án

```
BookingSystem/
├── src/
│   └── java/
│       ├── controller/    # Bộ điều khiển Servlet
│       ├── dao/           # Các đối tượng truy cập dữ liệu
│       ├── database/      # Tiện ích kết nối cơ sở dữ liệu
│       ├── model/         # Mô hình dữ liệu
│       └── util/          # Các lớp tiện ích
├── web/
│   ├── WEB-INF/
│   │   ├── views/         # Các file JSP
│   │   └── web.xml        # Cấu hình ứng dụng web
│   ├── css/               # Stylesheets
│   ├── js/                # Các file JavaScript
│   └── images/            # Tài nguyên hình ảnh
└── README.md              # Tài liệu dự án
```

## Cấu Trúc Cơ Sở Dữ Liệu

Ứng dụng sử dụng các bảng chính sau:

- **Users**: Lưu trữ thông tin tài khoản người dùng (id, họ tên, email, mật khẩu, số điện thoại, giới tính, ngày sinh, vai trò, trạng thái)
- **Services**: Chứa thông tin về các dịch vụ (id, tên, mô tả, giá, thời gian thực hiện, hình ảnh, trạng thái)
- **Staff**: Thông tin nhân viên (id, họ tên, chuyên môn, email, số điện thoại, ảnh đại diện, trạng thái)
- **Schedules**: Lịch làm việc của nhân viên (id, nhân viên id, ngày, giờ bắt đầu, giờ kết thúc)
- **Bookings**: Thông tin đặt lịch (id, người dùng id, dịch vụ id, nhân viên id, ngày, giờ, ghi chú, trạng thái, lý do hủy)

## Hướng Dẫn Cài Đặt

### Yêu Cầu Hệ Thống

- JDK 11 trở lên
- Apache Tomcat 10.1.x
- Microsoft SQL Server
- SQL Server JDBC Driver

### Thiết Lập Cơ Sở Dữ Liệu

1. Tạo cơ sở dữ liệu mới trong SQL Server
2. Chạy script SQL để tạo các bảng và dữ liệu mẫu
3. Cấu hình kết nối cơ sở dữ liệu trong `src/java/database/DBContext.java`

### Triển Khai

1. Clone repository
2. Thêm SQL Server JDBC driver vào thư viện của dự án
3. Build dự án
4. Triển khai lên Tomcat
5. Truy cập ứng dụng tại `http://localhost:9999/BookingSystem/`

## Tính Năng Bảo Mật

- Mã hóa mật khẩu với salt để lưu trữ an toàn
- Xác thực đầu vào để ngăn chặn SQL injection
- Quản lý phiên cho người dùng đã xác thực
- Kiểm soát truy cập dựa trên vai trò
- Bảo vệ chống tấn công CSRF

## Người Đóng Góp

- **Sinh viên 1**: Phạm Xuân Hiếu _ HE150075 - Quản lý người dùng & xác thực
- **Sinh viên 2**: Mai Tiến Dũng _ HE186519 - Chức năng đặt lịch & quản lý lịch cá nhân
- **Sinh viên 3**: Phạm Quốc Tuấn _ HE181869 - Quản lý dịch vụ & nhân sự
- **Sinh viên 4**: Lê Anh Minh _ HE180621 - Thống kê & báo cáo

## Giấy Phép

[Chỉ định thông tin giấy phép của bạn tại đây]

---

## Lời Cảm Ơn

- [Liệt kê các thư viện, tài nguyên hoặc nguồn cảm hứng]
