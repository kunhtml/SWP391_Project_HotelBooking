# YÊU CẦU HỆ THỐNG WEBSITE KHÁCH SẠN QUY MÔ NHỎ

## 1. GIỚI THIỆU

### 1.1 Mục đích
Tài liệu này mô tả các yêu cầu chức năng và phi chức năng cho hệ thống website khách sạn quy mô nhỏ. Mục đích của hệ thống là cung cấp một nền tảng trực tuyến cho phép khách hàng xem thông tin, đặt phòng và thanh toán trực tuyến, đồng thời giúp chủ khách sạn quản lý hoạt động kinh doanh hiệu quả.

### 1.2 Phạm vi
Hệ thống bao gồm một website công khai dành cho khách hàng và một hệ thống quản lý nội bộ dành cho nhân viên và quản lý khách sạn. Hệ thống sẽ được triển khai trên nền tảng web, hỗ trợ truy cập từ máy tính và thiết bị di động.

### 1.3 Đối tượng sử dụng
- Khách hàng: Người dùng tìm kiếm và đặt phòng khách sạn
- Nhân viên lễ tân: Quản lý đặt phòng và check-in/check-out
- Quản lý khách sạn: Quản lý tổng thể hoạt động kinh doanh
- Quản trị viên hệ thống: Quản lý và bảo trì hệ thống

## 2. YÊU CẦU CHỨC NĂNG

### 2.1 Phân hệ Khách hàng (Frontend)

#### 2.1.1 Trang chủ và Thông tin chung
- Hiển thị thông tin tổng quan về khách sạn (giới thiệu, tiện ích, dịch vụ)
- Hiển thị hình ảnh khách sạn và phòng nổi bật
- Hiển thị khuyến mãi và ưu đãi đặc biệt
- Cung cấp thông tin liên hệ và vị trí địa lý của khách sạn
- Hiển thị đánh giá và phản hồi từ khách hàng

#### 2.1.2 Tìm kiếm và Đặt phòng
- Tìm kiếm phòng theo ngày check-in, check-out, số lượng khách
- Hiển thị danh sách phòng có sẵn với đầy đủ thông tin (loại phòng, giá, tiện nghi)
- Xem chi tiết phòng với hình ảnh, mô tả và tiện nghi
- Chọn và đặt phòng
- Nhập thông tin khách hàng và yêu cầu đặc biệt
- Xác nhận đặt phòng qua email

#### 2.1.3 Thanh toán
- Hỗ trợ thanh toán trực tuyến (thẻ tín dụng, chuyển khoản ngân hàng)
- Hỗ trợ thanh toán tại khách sạn
- Hiển thị hóa đơn chi tiết
- Gửi xác nhận thanh toán qua email

#### 2.1.4 Tài khoản Khách hàng
- Đăng ký tài khoản với xác minh email OTP
- Đăng nhập/Đăng xuất
- Quản lý thông tin cá nhân
- Xem lịch sử đặt phòng
- Quản lý đặt phòng hiện tại (hủy, thay đổi)
- Lưu phòng yêu thích

#### 2.1.5 Đánh giá và Phản hồi
- Đánh giá và viết nhận xét sau khi sử dụng dịch vụ
- Xem đánh giá từ khách hàng khác

### 2.2 Phân hệ Quản lý (Backend)

#### 2.2.1 Quản lý Phòng
- Thêm, sửa, xóa thông tin phòng
- Quản lý loại phòng và giá phòng
- Cập nhật trạng thái phòng (có sẵn, đã đặt, đang dọn dẹp)
- Quản lý hình ảnh phòng
- Thiết lập giá theo mùa và khuyến mãi

#### 2.2.2 Quản lý Đặt phòng
- Xem danh sách đặt phòng (tất cả, theo ngày, theo trạng thái)
- Xác nhận hoặc hủy đặt phòng
- Quản lý check-in và check-out
- Tìm kiếm đặt phòng theo nhiều tiêu chí
- Gửi email nhắc nhở check-in/check-out

#### 2.2.3 Quản lý Khách hàng
- Xem danh sách khách hàng
- Tìm kiếm thông tin khách hàng
- Quản lý thông tin liên hệ
- Xem lịch sử đặt phòng của khách hàng
- Gửi email marketing và khuyến mãi

#### 2.2.4 Quản lý Thanh toán
- Xem danh sách thanh toán
- Xác nhận thanh toán thủ công
- Xuất hóa đơn
- Báo cáo doanh thu (theo ngày, tuần, tháng, năm)

#### 2.2.5 Báo cáo và Thống kê
- Báo cáo công suất phòng
- Báo cáo doanh thu
- Thống kê khách hàng
- Phân tích đánh giá của khách hàng
- Xuất báo cáo dưới dạng PDF, Excel

#### 2.2.6 Quản lý Người dùng
- Thêm, sửa, xóa tài khoản nhân viên
- Phân quyền người dùng
- Quản lý vai trò và quyền hạn
- Theo dõi hoạt động người dùng

## 3. YÊU CẦU PHI CHỨC NĂNG

### 3.1 Hiệu suất
- Thời gian phản hồi trang web dưới 3 giây
- Hỗ trợ tối thiểu 100 người dùng đồng thời
- Thời gian xử lý đặt phòng dưới 5 giây

### 3.2 Bảo mật
- Mã hóa thông tin cá nhân và thanh toán
- Xác thực hai yếu tố cho tài khoản quản trị
- Bảo vệ chống tấn công SQL injection, XSS
- Tuân thủ quy định bảo vệ dữ liệu cá nhân

### 3.3 Độ tin cậy
- Hệ thống hoạt động 24/7 với thời gian ngừng hoạt động không quá 0.1%
- Sao lưu dữ liệu hàng ngày
- Khả năng phục hồi dữ liệu khi có sự cố

### 3.4 Khả năng sử dụng
- Giao diện thân thiện, dễ sử dụng
- Thiết kế responsive, tương thích với các thiết bị
- Hỗ trợ đa ngôn ngữ (tiếng Việt, tiếng Anh)
- Thời gian đào tạo nhân viên sử dụng hệ thống dưới 2 giờ

### 3.5 Khả năng mở rộng
- Kiến trúc cho phép mở rộng thêm tính năng
- Hỗ trợ tích hợp với các hệ thống bên thứ ba (như Booking.com, Agoda)
- Khả năng mở rộng quy mô khi lượng người dùng tăng

## 4. GIỚI HẠN VÀ RÀNG BUỘC

### 4.1 Giới hạn kỹ thuật
- Hệ thống được phát triển trên nền tảng web
- Sử dụng công nghệ Java Servlet/JSP, SQL Server
- Hỗ trợ các trình duyệt phổ biến (Chrome, Firefox, Safari, Edge)

### 4.2 Ràng buộc pháp lý
- Tuân thủ quy định về lưu trữ thông tin cá nhân
- Tuân thủ quy định về giao dịch trực tuyến
- Cung cấp điều khoản sử dụng và chính sách bảo mật rõ ràng

### 4.3 Ràng buộc ngân sách
- Chi phí phát triển ban đầu không quá X triệu đồng
- Chi phí duy trì hàng tháng không quá Y triệu đồng

## 5. GIAI ĐOẠN TRIỂN KHAI

### 5.1 Giai đoạn 1: Cơ bản (2 tháng)
- Phát triển trang thông tin khách sạn
- Chức năng tìm kiếm và đặt phòng cơ bản
- Quản lý phòng và đặt phòng cho nhân viên

### 5.2 Giai đoạn 2: Nâng cao (2 tháng)
- Tích hợp thanh toán trực tuyến
- Hệ thống tài khoản khách hàng
- Báo cáo và thống kê cơ bản

### 5.3 Giai đoạn 3: Hoàn thiện (1 tháng)
- Tính năng đánh giá và phản hồi
- Báo cáo và thống kê nâng cao
- Tối ưu hóa hiệu suất và trải nghiệm người dùng

## 6. PHỤ LỤC

### 6.1 Thuật ngữ
- **OTP**: One-Time Password, mật khẩu dùng một lần
- **Check-in**: Thủ tục nhận phòng
- **Check-out**: Thủ tục trả phòng
- **Frontend**: Giao diện người dùng cuối
- **Backend**: Hệ thống quản lý nội bộ

### 6.2 Tài liệu tham khảo
- Các quy định về kinh doanh khách sạn
- Tiêu chuẩn bảo mật thông tin cá nhân
- Hướng dẫn thiết kế trải nghiệm người dùng
