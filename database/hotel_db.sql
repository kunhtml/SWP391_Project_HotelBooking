-- Script tạo cơ sở dữ liệu Hotel_Booking hoàn chỉnh
-- Bao gồm tất cả các bảng và dữ liệu mẫu
-- Phiên bản không sử dụng salt riêng biệt

-- Tạo cơ sở dữ liệu nếu chưa tồn tại
IF NOT EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = 'Hotel_Booking')
BEGIN
    CREATE DATABASE Hotel_Booking;
END
GO

USE Hotel_Booking;
GO

-- Xóa các bảng hiện có nếu có
IF OBJECT_ID('dbo.Payments', 'U') IS NOT NULL DROP TABLE dbo.Payments;
IF OBJECT_ID('dbo.BookingServices', 'U') IS NOT NULL DROP TABLE dbo.BookingServices;
IF OBJECT_ID('dbo.Services', 'U') IS NOT NULL DROP TABLE dbo.Services;
IF OBJECT_ID('dbo.BookingRooms', 'U') IS NOT NULL DROP TABLE dbo.BookingRooms;
IF OBJECT_ID('dbo.Bookings', 'U') IS NOT NULL DROP TABLE dbo.Bookings;
IF OBJECT_ID('dbo.Rooms', 'U') IS NOT NULL DROP TABLE dbo.Rooms;
IF OBJECT_ID('dbo.RoomTypes', 'U') IS NOT NULL DROP TABLE dbo.RoomTypes;
IF OBJECT_ID('dbo.Users', 'U') IS NOT NULL DROP TABLE dbo.Users;
IF OBJECT_ID('dbo.Hotels', 'U') IS NOT NULL DROP TABLE dbo.Hotels;
GO

-- Tạo bảng Hotels (Khách sạn)
CREATE TABLE dbo.Hotels (
    hotelID INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    address NVARCHAR(255) NOT NULL,
    city NVARCHAR(50) NOT NULL,
    country NVARCHAR(50) NOT NULL,
    phone NVARCHAR(20) NOT NULL,
    email NVARCHAR(100) NOT NULL,
    description NVARCHAR(MAX) NULL,
    starRating INT NOT NULL,
    checkInTime TIME NOT NULL DEFAULT '14:00:00',
    checkOutTime TIME NOT NULL DEFAULT '12:00:00',
    isActive BIT NOT NULL DEFAULT 1,
    createdDate DATETIME NOT NULL DEFAULT GETDATE(),
    updatedDate DATETIME NULL
);
GO

-- Tạo bảng Users (Người dùng) - Không sử dụng salt riêng biệt
CREATE TABLE dbo.Users (
    userID INT IDENTITY(1,1) PRIMARY KEY,
    fullName NVARCHAR(100) NOT NULL,
    username NVARCHAR(50) NOT NULL UNIQUE,
    passwordHash NVARCHAR(255) NOT NULL, -- Lưu trữ mật khẩu đã hash dưới dạng chuỗi
    email NVARCHAR(100) NOT NULL UNIQUE,
    role NVARCHAR(20) NOT NULL,
    gender NVARCHAR(10) NULL,
    phoneNumber NVARCHAR(20) NULL,
    hotelID INT NULL,
    isGroup BIT NOT NULL DEFAULT 0,
    isActive BIT NOT NULL DEFAULT 1,
    createdDate DATETIME NOT NULL DEFAULT GETDATE(),
    lastLogin DATETIME NULL,
    profileImage VARCHAR(255) NULL,
    CONSTRAINT FK_Users_Hotels FOREIGN KEY (hotelID) REFERENCES dbo.Hotels(hotelID)
);
GO

-- Tạo bảng RoomTypes (Loại phòng)
CREATE TABLE dbo.RoomTypes (
    roomTypeID INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(50) NOT NULL,
    description NVARCHAR(MAX) NULL,
    basePrice DECIMAL(10, 2) NOT NULL,
    capacity INT NOT NULL,
    bedType NVARCHAR(50) NOT NULL,
    amenities NVARCHAR(MAX) NULL,
    imageURL NVARCHAR(255) NULL,
    isActive BIT NOT NULL DEFAULT 1,
    createdDate DATETIME NOT NULL DEFAULT GETDATE(),
    updatedDate DATETIME NULL
);
GO

-- Tạo bảng Rooms (Phòng)
CREATE TABLE dbo.Rooms (
    roomID INT IDENTITY(1,1) PRIMARY KEY,
    roomNumber NVARCHAR(20) NOT NULL,
    roomTypeID INT NOT NULL,
    hotelID INT NOT NULL,
    floor INT NOT NULL,
    status NVARCHAR(20) NOT NULL DEFAULT 'Available',
    isActive BIT NOT NULL DEFAULT 1,
    createdDate DATETIME NOT NULL DEFAULT GETDATE(),
    updatedDate DATETIME NULL,
    CONSTRAINT FK_Rooms_RoomTypes FOREIGN KEY (roomTypeID) REFERENCES dbo.RoomTypes(roomTypeID),
    CONSTRAINT FK_Rooms_Hotels FOREIGN KEY (hotelID) REFERENCES dbo.Hotels(hotelID),
    CONSTRAINT UQ_Rooms_RoomNumber_HotelID UNIQUE (roomNumber, hotelID)
);
GO

-- Tạo bảng Bookings (Đặt phòng)
CREATE TABLE dbo.Bookings (
    bookingID INT IDENTITY(1,1) PRIMARY KEY,
    userID INT NOT NULL,
    bookingDate DATETIME NOT NULL DEFAULT GETDATE(),
    checkInDate DATE NOT NULL,
    checkOutDate DATE NOT NULL,
    totalGuests INT NOT NULL,
    adults INT NOT NULL,
    children INT NOT NULL DEFAULT 0,
    specialRequests NVARCHAR(MAX) NULL,
    status NVARCHAR(20) NOT NULL DEFAULT 'Pending',
    totalAmount DECIMAL(10, 2) NOT NULL,
    paymentStatus NVARCHAR(20) NOT NULL DEFAULT 'Unpaid',
    createdDate DATETIME NOT NULL DEFAULT GETDATE(),
    updatedDate DATETIME NULL,
    CONSTRAINT FK_Bookings_Users FOREIGN KEY (userID) REFERENCES dbo.Users(userID),
    CONSTRAINT CK_Bookings_Dates CHECK (checkOutDate > checkInDate)
);
GO

-- Tạo bảng BookingRooms (Phòng đã đặt)
CREATE TABLE dbo.BookingRooms (
    bookingRoomID INT IDENTITY(1,1) PRIMARY KEY,
    bookingID INT NOT NULL,
    roomID INT NOT NULL,
    pricePerNight DECIMAL(10, 2) NOT NULL,
    CONSTRAINT FK_BookingRooms_Bookings FOREIGN KEY (bookingID) REFERENCES dbo.Bookings(bookingID),
    CONSTRAINT FK_BookingRooms_Rooms FOREIGN KEY (roomID) REFERENCES dbo.Rooms(roomID),
    CONSTRAINT UQ_BookingRooms_BookingID_RoomID UNIQUE (bookingID, roomID)
);
GO

-- Tạo bảng Services (Dịch vụ)
CREATE TABLE dbo.Services (
    serviceID INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(MAX) NULL,
    price DECIMAL(10, 2) NOT NULL,
    hotelID INT NOT NULL,
    category NVARCHAR(50) NOT NULL,
    isActive BIT NOT NULL DEFAULT 1,
    createdDate DATETIME NOT NULL DEFAULT GETDATE(),
    updatedDate DATETIME NULL,
    CONSTRAINT FK_Services_Hotels FOREIGN KEY (hotelID) REFERENCES dbo.Hotels(hotelID)
);
GO

-- Tạo bảng BookingServices (Dịch vụ đã đặt)
CREATE TABLE dbo.BookingServices (
    bookingServiceID INT IDENTITY(1,1) PRIMARY KEY,
    bookingID INT NOT NULL,
    serviceID INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT FK_BookingServices_Bookings FOREIGN KEY (bookingID) REFERENCES dbo.Bookings(bookingID),
    CONSTRAINT FK_BookingServices_Services FOREIGN KEY (serviceID) REFERENCES dbo.Services(serviceID)
);
GO

-- Tạo bảng Payments (Thanh toán)
CREATE TABLE dbo.Payments (
    paymentID INT IDENTITY(1,1) PRIMARY KEY,
    bookingID INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    paymentMethod NVARCHAR(50) NOT NULL,
    paymentDate DATETIME NOT NULL DEFAULT GETDATE(),
    status NVARCHAR(20) NOT NULL DEFAULT 'Completed',
    transactionID NVARCHAR(100) NULL,
    CONSTRAINT FK_Payments_Bookings FOREIGN KEY (bookingID) REFERENCES dbo.Bookings(bookingID)
);
GO

-- Tạo các chỉ mục để cải thiện hiệu suất truy vấn
CREATE INDEX IX_Users_Email ON dbo.Users(email);
CREATE INDEX IX_Users_PhoneNumber ON dbo.Users(phoneNumber);
CREATE INDEX IX_Bookings_UserID ON dbo.Bookings(userID);
CREATE INDEX IX_Bookings_Dates ON dbo.Bookings(checkInDate, checkOutDate);
CREATE INDEX IX_Rooms_Status ON dbo.Rooms(status);
CREATE INDEX IX_BookingRooms_RoomID ON dbo.BookingRooms(roomID);
GO

-- Chèn dữ liệu mẫu

-- Chèn khách sạn mẫu
INSERT INTO dbo.Hotels (name, address, city, country, phone, email, description, starRating)
VALUES ('Luxury Hotel & Spa', '123 Main Street', 'New York', 'USA', '+1-212-555-1234', 'info@luxuryhotel.com', 
        'A luxurious 5-star hotel in the heart of New York City, offering premium amenities and exceptional service.', 5);
GO

-- Chèn loại phòng mẫu
INSERT INTO dbo.RoomTypes (name, description, basePrice, capacity, bedType, amenities)
VALUES 
('Standard Room', 'Comfortable room with essential amenities', 100.00, 2, 'Queen', 'Wi-Fi, TV, Air Conditioning, Mini Bar'),
('Deluxe Room', 'Spacious room with premium amenities', 150.00, 2, 'King', 'Wi-Fi, TV, Air Conditioning, Mini Bar, Bathtub, City View'),
('Suite', 'Luxury suite with separate living area', 250.00, 4, 'King + Sofa Bed', 'Wi-Fi, TV, Air Conditioning, Mini Bar, Bathtub, City View, Living Room, Kitchenette'),
('Family Room', 'Spacious room for families', 200.00, 4, 'Two Queen Beds', 'Wi-Fi, TV, Air Conditioning, Mini Bar, Bathtub');
GO

-- Chèn phòng mẫu
INSERT INTO dbo.Rooms (roomNumber, roomTypeID, hotelID, floor, status)
VALUES 
('101', 1, 1, 1, 'Available'), ('102', 1, 1, 1, 'Available'), ('103', 1, 1, 1, 'Available'), ('104', 1, 1, 1, 'Available'),
('201', 2, 1, 2, 'Available'), ('202', 2, 1, 2, 'Available'), ('203', 2, 1, 2, 'Available'), ('204', 2, 1, 2, 'Available'),
('301', 3, 1, 3, 'Available'), ('302', 3, 1, 3, 'Available'), ('303', 3, 1, 3, 'Available'), ('304', 3, 1, 3, 'Available'),
('401', 4, 1, 4, 'Available'), ('402', 4, 1, 4, 'Available'), ('403', 4, 1, 4, 'Available'), ('404', 4, 1, 4, 'Available');
GO

-- Chèn dịch vụ mẫu
INSERT INTO dbo.Services (name, description, price, hotelID, category)
VALUES 
('Breakfast Buffet', 'Full breakfast buffet with international cuisine', 25.00, 1, 'Dining'),
('Room Service', '24-hour room service', 10.00, 1, 'Dining'),
('Spa Treatment', 'Full body massage and spa treatment', 120.00, 1, 'Wellness'),
('Airport Transfer', 'Private transfer to/from airport', 50.00, 1, 'Transportation'),
('Laundry Service', 'Same-day laundry service', 30.00, 1, 'Housekeeping'),
('Gym Access', 'Access to fully equipped gym', 15.00, 1, 'Fitness'),
('Swimming Pool', 'Access to rooftop infinity pool', 20.00, 1, 'Recreation');
GO

-- Chèn người dùng admin với mật khẩu đã hash đơn giản (mật khẩu: admin123)
INSERT INTO dbo.Users (fullName, username, passwordHash, email, role, gender, phoneNumber, hotelID, isGroup, isActive)
VALUES ('Admin User', 'admin', 'admin123', 'admin@luxuryhotel.com', 'admin', 'Male', '+1-212-555-0000', 1, 0, 1);
GO

-- Chèn người dùng test với mật khẩu đã hash đơn giản (mật khẩu: password123)
INSERT INTO dbo.Users (fullName, username, passwordHash, email, role, gender, phoneNumber, isGroup, isActive)
VALUES ('Test User', 'testuser', 'password123', 'testuser@example.com', 'user', 'Male', '1234567890', 0, 1);
GO

-- Hiển thị thông báo hoàn thành
PRINT 'Cơ sở dữ liệu Hotel_Booking đã được tạo thành công!';
PRINT 'Tài khoản admin: username=admin, password=admin123';
PRINT 'Tài khoản test: username=testuser, password=password123';
GO
