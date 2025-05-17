
-- Tạo cơ sở dữ liệu nếu chưa tồn tại
IF NOT EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = 'Hotel_Booking')
BEGIN
    CREATE DATABASE Hotel_Booking; -- Tạo cơ sở dữ liệu mới tên là Hotel_Booking
END
GO

USE Hotel_Booking; -- Chuyển sang sử dụng cơ sở dữ liệu Hotel_Booking
GO

-- Xóa các bảng hiện có nếu chúng tồn tại (theo thứ tự ngược lại của các phụ thuộc)
-- Điều này đảm bảo rằng các bảng con được xóa trước các bảng cha để tránh lỗi ràng buộc khóa ngoại
IF OBJECT_ID('dbo.Payments', 'U') IS NOT NULL DROP TABLE dbo.Payments; -- Xóa bảng Thanh toán
IF OBJECT_ID('dbo.BookingServices', 'U') IS NOT NULL DROP TABLE dbo.BookingServices; -- Xóa bảng Dịch vụ đặt phòng
IF OBJECT_ID('dbo.Services', 'U') IS NOT NULL DROP TABLE dbo.Services; -- Xóa bảng Dịch vụ
IF OBJECT_ID('dbo.BookingRooms', 'U') IS NOT NULL DROP TABLE dbo.BookingRooms; -- Xóa bảng Phòng đặt
IF OBJECT_ID('dbo.Bookings', 'U') IS NOT NULL DROP TABLE dbo.Bookings; -- Xóa bảng Đặt phòng
IF OBJECT_ID('dbo.Rooms', 'U') IS NOT NULL DROP TABLE dbo.Rooms; -- Xóa bảng Phòng
IF OBJECT_ID('dbo.RoomTypes', 'U') IS NOT NULL DROP TABLE dbo.RoomTypes; -- Xóa bảng Loại phòng
IF OBJECT_ID('dbo.Hotels', 'U') IS NOT NULL DROP TABLE dbo.Hotels; -- Xóa bảng Khách sạn
IF OBJECT_ID('dbo.Users', 'U') IS NOT NULL DROP TABLE dbo.Users; -- Xóa bảng Người dùng
GO

-- Tạo bảng Người dùng (Users)
CREATE TABLE dbo.Users (
    userID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng
    fullName NVARCHAR(100) NOT NULL, -- Họ tên đầy đủ, bắt buộc
    username NVARCHAR(50) NOT NULL UNIQUE, -- Tên đăng nhập, bắt buộc và duy nhất
    passwordHash VARBINARY(MAX) NOT NULL, -- Mật khẩu đã được mã hóa, bắt buộc
    salt VARBINARY(MAX) NOT NULL, -- Giá trị salt dùng để mã hóa mật khẩu, bắt buộc
    email NVARCHAR(100) NOT NULL UNIQUE, -- Email, bắt buộc và duy nhất
    role NVARCHAR(20) NOT NULL, -- Vai trò (admin, user, staff...), bắt buộc
    gender NVARCHAR(10) NULL, -- Giới tính, không bắt buộc
    phoneNumber NVARCHAR(20) NULL, -- Số điện thoại, không bắt buộc
    hotelID INT NULL, -- ID khách sạn (cho nhân viên), không bắt buộc
    isGroup BIT NOT NULL DEFAULT 0, -- Là tài khoản nhóm hay không, mặc định là 0 (không)
    isActive BIT NOT NULL DEFAULT 1, -- Tài khoản có hoạt động hay không, mặc định là 1 (có)
    createdDate DATETIME NOT NULL DEFAULT GETDATE(), -- Ngày tạo tài khoản, mặc định là ngày hiện tại
    lastLogin DATETIME NULL -- Lần đăng nhập cuối cùng, không bắt buộc
);
GO

-- Tạo bảng Khách sạn (Hotels)
CREATE TABLE dbo.Hotels (
    hotelID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng
    name NVARCHAR(100) NOT NULL, -- Tên khách sạn, bắt buộc
    address NVARCHAR(200) NOT NULL, -- Địa chỉ, bắt buộc
    city NVARCHAR(50) NOT NULL, -- Thành phố, bắt buộc
    country NVARCHAR(50) NOT NULL, -- Quốc gia, bắt buộc
    postalCode NVARCHAR(20) NULL, -- Mã bưu điện, không bắt buộc
    phone NVARCHAR(20) NOT NULL, -- Số điện thoại, bắt buộc
    email NVARCHAR(100) NOT NULL, -- Email, bắt buộc
    website NVARCHAR(100) NULL, -- Website, không bắt buộc
    description NVARCHAR(MAX) NULL, -- Mô tả, không bắt buộc
    starRating INT NULL, -- Xếp hạng sao, không bắt buộc
    checkInTime TIME NOT NULL DEFAULT '14:00:00', -- Giờ nhận phòng, mặc định là 14:00
    checkOutTime TIME NOT NULL DEFAULT '12:00:00', -- Giờ trả phòng, mặc định là 12:00
    isActive BIT NOT NULL DEFAULT 1, -- Khách sạn có hoạt động hay không, mặc định là 1 (có)
    createdDate DATETIME NOT NULL DEFAULT GETDATE(), -- Ngày tạo, mặc định là ngày hiện tại
    updatedDate DATETIME NULL -- Ngày cập nhật, không bắt buộc
);
GO

-- Thêm khóa ngoại vào bảng Users để liên kết với bảng Hotels
ALTER TABLE dbo.Users
ADD CONSTRAINT FK_Users_Hotels FOREIGN KEY (hotelID) REFERENCES dbo.Hotels(hotelID);
GO

-- Tạo bảng Loại phòng (RoomTypes)
CREATE TABLE dbo.RoomTypes (
    roomTypeID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng
    hotelID INT NOT NULL, -- ID khách sạn, bắt buộc
    name NVARCHAR(50) NOT NULL, -- Tên loại phòng, bắt buộc
    description NVARCHAR(MAX) NULL, -- Mô tả, không bắt buộc
    basePrice DECIMAL(10, 2) NOT NULL, -- Giá cơ bản, bắt buộc
    capacity INT NOT NULL, -- Sức chứa (số người), bắt buộc
    bedType NVARCHAR(50) NULL, -- Loại giường, không bắt buộc
    amenities NVARCHAR(MAX) NULL, -- Tiện nghi, không bắt buộc
    isActive BIT NOT NULL DEFAULT 1, -- Loại phòng có hoạt động hay không, mặc định là 1 (có)
    createdDate DATETIME NOT NULL DEFAULT GETDATE(), -- Ngày tạo, mặc định là ngày hiện tại
    updatedDate DATETIME NULL, -- Ngày cập nhật, không bắt buộc
    CONSTRAINT FK_RoomTypes_Hotels FOREIGN KEY (hotelID) REFERENCES dbo.Hotels(hotelID) -- Khóa ngoại liên kết với bảng Hotels
);
GO

-- Tạo bảng Phòng (Rooms)
CREATE TABLE dbo.Rooms (
    roomID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng
    roomTypeID INT NOT NULL, -- ID loại phòng, bắt buộc
    roomNumber NVARCHAR(20) NOT NULL, -- Số phòng, bắt buộc
    floor NVARCHAR(10) NULL, -- Tầng, không bắt buộc
    status NVARCHAR(20) NOT NULL DEFAULT 'Available', -- Trạng thái (Available, Occupied, Maintenance...), mặc định là Available
    isActive BIT NOT NULL DEFAULT 1, -- Phòng có hoạt động hay không, mặc định là 1 (có)
    createdDate DATETIME NOT NULL DEFAULT GETDATE(), -- Ngày tạo, mặc định là ngày hiện tại
    updatedDate DATETIME NULL, -- Ngày cập nhật, không bắt buộc
    CONSTRAINT FK_Rooms_RoomTypes FOREIGN KEY (roomTypeID) REFERENCES dbo.RoomTypes(roomTypeID), -- Khóa ngoại liên kết với bảng RoomTypes
    CONSTRAINT UQ_Rooms_RoomNumber UNIQUE (roomTypeID, roomNumber) -- Ràng buộc duy nhất cho số phòng trong mỗi loại phòng
);
GO

-- Tạo bảng Đặt phòng (Bookings)
CREATE TABLE dbo.Bookings (
    bookingID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng
    userID INT NOT NULL, -- ID người dùng, bắt buộc
    bookingDate DATETIME NOT NULL DEFAULT GETDATE(), -- Ngày đặt phòng, mặc định là ngày hiện tại
    checkInDate DATE NOT NULL, -- Ngày nhận phòng, bắt buộc
    checkOutDate DATE NOT NULL, -- Ngày trả phòng, bắt buộc
    totalGuests INT NOT NULL, -- Tổng số khách, bắt buộc
    adults INT NOT NULL, -- Số người lớn, bắt buộc
    children INT NOT NULL DEFAULT 0, -- Số trẻ em, mặc định là 0
    specialRequests NVARCHAR(MAX) NULL, -- Yêu cầu đặc biệt, không bắt buộc
    status NVARCHAR(20) NOT NULL DEFAULT 'Pending', -- Trạng thái (Pending, Confirmed, Cancelled, Completed), mặc định là Pending
    totalAmount DECIMAL(10, 2) NOT NULL, -- Tổng số tiền, bắt buộc
    paymentStatus NVARCHAR(20) NOT NULL DEFAULT 'Unpaid', -- Trạng thái thanh toán (Unpaid, Partially Paid, Paid), mặc định là Unpaid
    createdDate DATETIME NOT NULL DEFAULT GETDATE(), -- Ngày tạo, mặc định là ngày hiện tại
    updatedDate DATETIME NULL, -- Ngày cập nhật, không bắt buộc
    CONSTRAINT FK_Bookings_Users FOREIGN KEY (userID) REFERENCES dbo.Users(userID), -- Khóa ngoại liên kết với bảng Users
    CONSTRAINT CK_Bookings_Dates CHECK (checkOutDate > checkInDate) -- Ràng buộc kiểm tra ngày trả phòng phải sau ngày nhận phòng
);
GO

-- Tạo bảng Phòng đặt (BookingRooms) - cho phép đặt nhiều phòng trong một đơn đặt phòng
CREATE TABLE dbo.BookingRooms (
    bookingRoomID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng
    bookingID INT NOT NULL, -- ID đặt phòng, bắt buộc
    roomID INT NOT NULL, -- ID phòng, bắt buộc
    pricePerNight DECIMAL(10, 2) NOT NULL, -- Giá mỗi đêm, bắt buộc
    CONSTRAINT FK_BookingRooms_Bookings FOREIGN KEY (bookingID) REFERENCES dbo.Bookings(bookingID), -- Khóa ngoại liên kết với bảng Bookings
    CONSTRAINT FK_BookingRooms_Rooms FOREIGN KEY (roomID) REFERENCES dbo.Rooms(roomID), -- Khóa ngoại liên kết với bảng Rooms
    CONSTRAINT UQ_BookingRooms UNIQUE (bookingID, roomID) -- Ràng buộc duy nhất cho mỗi phòng trong một đơn đặt phòng
);
GO

-- Tạo bảng Dịch vụ (Services)
CREATE TABLE dbo.Services (
    serviceID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng
    hotelID INT NOT NULL, -- ID khách sạn, bắt buộc
    name NVARCHAR(100) NOT NULL, -- Tên dịch vụ, bắt buộc
    description NVARCHAR(MAX) NULL, -- Mô tả, không bắt buộc
    price DECIMAL(10, 2) NOT NULL, -- Giá, bắt buộc
    category NVARCHAR(50) NULL, -- Danh mục (Restaurant, Spa, Laundry...), không bắt buộc
    isActive BIT NOT NULL DEFAULT 1, -- Dịch vụ có hoạt động hay không, mặc định là 1 (có)
    createdDate DATETIME NOT NULL DEFAULT GETDATE(), -- Ngày tạo, mặc định là ngày hiện tại
    updatedDate DATETIME NULL, -- Ngày cập nhật, không bắt buộc
    CONSTRAINT FK_Services_Hotels FOREIGN KEY (hotelID) REFERENCES dbo.Hotels(hotelID) -- Khóa ngoại liên kết với bảng Hotels
);
GO

-- Tạo bảng Dịch vụ đặt phòng (BookingServices) - cho các dịch vụ được thêm vào đơn đặt phòng
CREATE TABLE dbo.BookingServices (
    bookingServiceID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng
    bookingID INT NOT NULL, -- ID đặt phòng, bắt buộc
    serviceID INT NOT NULL, -- ID dịch vụ, bắt buộc
    quantity INT NOT NULL DEFAULT 1, -- Số lượng, mặc định là 1
    price DECIMAL(10, 2) NOT NULL, -- Giá, bắt buộc
    serviceDate DATETIME NOT NULL, -- Ngày sử dụng dịch vụ, bắt buộc
    notes NVARCHAR(MAX) NULL, -- Ghi chú, không bắt buộc
    CONSTRAINT FK_BookingServices_Bookings FOREIGN KEY (bookingID) REFERENCES dbo.Bookings(bookingID), -- Khóa ngoại liên kết với bảng Bookings
    CONSTRAINT FK_BookingServices_Services FOREIGN KEY (serviceID) REFERENCES dbo.Services(serviceID) -- Khóa ngoại liên kết với bảng Services
);
GO

-- Tạo bảng Thanh toán (Payments)
CREATE TABLE dbo.Payments (
    paymentID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng
    bookingID INT NOT NULL, -- ID đặt phòng, bắt buộc
    amount DECIMAL(10, 2) NOT NULL, -- Số tiền, bắt buộc
    paymentDate DATETIME NOT NULL DEFAULT GETDATE(), -- Ngày thanh toán, mặc định là ngày hiện tại
    paymentMethod NVARCHAR(50) NOT NULL, -- Phương thức thanh toán (Credit Card, Cash, Bank Transfer...), bắt buộc
    transactionID NVARCHAR(100) NULL, -- ID giao dịch, không bắt buộc
    status NVARCHAR(20) NOT NULL DEFAULT 'Completed', -- Trạng thái (Pending, Completed, Failed, Refunded), mặc định là Completed
    notes NVARCHAR(MAX) NULL, -- Ghi chú, không bắt buộc
    CONSTRAINT FK_Payments_Bookings FOREIGN KEY (bookingID) REFERENCES dbo.Bookings(bookingID) -- Khóa ngoại liên kết với bảng Bookings
);
GO

-- Tạo các chỉ mục để cải thiện hiệu suất truy vấn
CREATE INDEX IX_Users_Email ON dbo.Users(email); -- Chỉ mục cho email người dùng
CREATE INDEX IX_Users_PhoneNumber ON dbo.Users(phoneNumber); -- Chỉ mục cho số điện thoại người dùng
CREATE INDEX IX_Bookings_UserID ON dbo.Bookings(userID); -- Chỉ mục cho ID người dùng trong bảng Bookings
CREATE INDEX IX_Bookings_Dates ON dbo.Bookings(checkInDate, checkOutDate); -- Chỉ mục cho ngày nhận và trả phòng
CREATE INDEX IX_Rooms_Status ON dbo.Rooms(status); -- Chỉ mục cho trạng thái phòng
CREATE INDEX IX_BookingRooms_RoomID ON dbo.BookingRooms(roomID); -- Chỉ mục cho ID phòng trong bảng BookingRooms
GO

-- Chèn dữ liệu mẫu

-- Chèn một khách sạn mặc định
INSERT INTO dbo.Hotels (name, address, city, country, postalCode, phone, email, website, description, starRating)
VALUES ('Luxury Hotel', '123 Main Street', 'New York', 'USA', '10001', '+1-212-555-1234', 'info@luxuryhotel.com', 'www.luxuryhotel.com', 'Khách sạn 5 sao sang trọng ở trung tâm thành phố New York.', 5);
GO

-- Chèn các loại phòng
INSERT INTO dbo.RoomTypes (hotelID, name, description, basePrice, capacity, bedType, amenities)
VALUES 
(1, 'Standard Room', 'Phòng tiêu chuẩn thoải mái với các tiện nghi cơ bản.', 100.00, 2, 'Queen', 'Wi-Fi, TV, Điều hòa, Mini Bar'),
(1, 'Deluxe Room', 'Phòng rộng rãi với các tiện nghi cao cấp.', 150.00, 2, 'King', 'Wi-Fi, TV, Điều hòa, Mini Bar, Ban công, Tầm nhìn thành phố'),
(1, 'Luxury Suite', 'Phòng suite sang trọng với khu vực sinh hoạt riêng biệt.', 250.00, 4, 'King + Sofa Bed', 'Wi-Fi, TV, Điều hòa, Mini Bar, Ban công, Tầm nhìn thành phố, Bồn tắm sục, Bếp nhỏ'),
(1, 'Family Room', 'Phòng rộng rãi lý tưởng cho gia đình.', 200.00, 4, '2 Queen', 'Wi-Fi, TV, Điều hòa, Mini Bar, Phòng kết nối');
GO

-- Chèn các phòng
INSERT INTO dbo.Rooms (roomTypeID, roomNumber, floor, status)
VALUES 
-- Phòng Standard
(1, '101', '1', 'Available'),
(1, '102', '1', 'Available'),
(1, '103', '1', 'Available'),
(1, '104', '1', 'Available'),
(1, '105', '1', 'Available'),
-- Phòng Deluxe
(2, '201', '2', 'Available'),
(2, '202', '2', 'Available'),
(2, '203', '2', 'Available'),
(2, '204', '2', 'Available'),
(2, '205', '2', 'Available'),
-- Phòng Luxury Suite
(3, '301', '3', 'Available'),
(3, '302', '3', 'Available'),
(3, '303', '3', 'Available'),
-- Phòng Family
(4, '401', '4', 'Available'),
(4, '402', '4', 'Available'),
(4, '403', '4', 'Available');
GO

-- Chèn các dịch vụ
INSERT INTO dbo.Services (hotelID, name, description, price, category)
VALUES 
(1, 'Breakfast Buffet', 'Bữa sáng buffet đầy đủ với các món nóng và lạnh.', 25.00, 'Restaurant'),
(1, 'Room Service', 'Dịch vụ phòng 24 giờ.', 10.00, 'Restaurant'),
(1, 'Spa Treatment', 'Dịch vụ spa thư giãn.', 80.00, 'Spa'),
(1, 'Laundry Service', 'Dịch vụ giặt là trong ngày.', 15.00, 'Laundry'),
(1, 'Airport Transfer', 'Dịch vụ đưa đón sân bay riêng.', 50.00, 'Transportation'),
(1, 'Gym Access', 'Sử dụng phòng tập gym của khách sạn.', 10.00, 'Fitness'),
(1, 'Business Center', 'Sử dụng trung tâm doanh nhân với dịch vụ in ấn và quét tài liệu.', 20.00, 'Business');
GO

-- Chèn người dùng admin (mật khẩu: admin123)
-- Lưu ý: Trong ứng dụng thực tế, bạn sẽ sử dụng mã hóa mật khẩu đúng cách
-- Việc mã hóa mật khẩu thực tế được thực hiện trong mã Java
INSERT INTO dbo.Users (fullName, username, passwordHash, salt, email, role, gender, phoneNumber, hotelID, isGroup, isActive)
VALUES ('Admin User', 'admin', 0x0123456789ABCDEF, 0x0123456789ABCDEF, 'admin@luxuryhotel.com', 'admin', 'Male', '+1-212-555-0000', 1, 0, 1);
GO

-- Hiển thị cấu trúc bảng
PRINT 'Cơ sở dữ liệu đã được tạo thành công. Cấu trúc bảng:';
EXEC sp_help 'Users'; -- Hiển thị cấu trúc bảng Users
EXEC sp_help 'Hotels'; -- Hiển thị cấu trúc bảng Hotels
EXEC sp_help 'RoomTypes'; -- Hiển thị cấu trúc bảng RoomTypes
EXEC sp_help 'Rooms'; -- Hiển thị cấu trúc bảng Rooms
EXEC sp_help 'Bookings'; -- Hiển thị cấu trúc bảng Bookings
EXEC sp_help 'BookingRooms'; -- Hiển thị cấu trúc bảng BookingRooms
EXEC sp_help 'Services'; -- Hiển thị cấu trúc bảng Services
EXEC sp_help 'BookingServices'; -- Hiển thị cấu trúc bảng BookingServices
EXEC sp_help 'Payments'; -- Hiển thị cấu trúc bảng Payments
GO
