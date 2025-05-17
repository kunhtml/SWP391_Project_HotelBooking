CREATE DATABASE Hotel_Booking;


-- Bảng Hotels
CREATE TABLE Hotels (
    HotelID INT PRIMARY KEY IDENTITY(1,1),
    HotelName NVARCHAR(255) NOT NULL,
    Address NVARCHAR(255) NOT NULL,
    City NVARCHAR(100),
    Country NVARCHAR(100),
    PhoneNumber VARCHAR(20),
    Email VARCHAR(100),
    Website VARCHAR(255),
    StarRating TINYINT,
    Description NTEXT,
    ImageURL VARCHAR(255)
);

-- Bảng Rooms
CREATE TABLE Rooms (
    RoomID INT PRIMARY KEY IDENTITY(1,1),
    HotelID INT NOT NULL,
    RoomType NVARCHAR(50) NOT NULL,
    RoomNumber VARCHAR(10) NOT NULL,
    Capacity TINYINT,
    BasePrice DECIMAL(10, 2) NOT NULL,
    Description NTEXT,
    ImageURL VARCHAR(255),
    FOREIGN KEY (HotelID) REFERENCES Hotels(HotelID)
);

-- Bảng Customers
CREATE TABLE Customers (
    CustomerID INT PRIMARY KEY IDENTITY(1,1),
    FirstName NVARCHAR(50) NOT NULL,
    LastName NVARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    PhoneNumber VARCHAR(20),
    Address NVARCHAR(255),
    City NVARCHAR(100),
    Country NVARCHAR(100),
    RegistrationDate DATETIME DEFAULT GETDATE()
);

-- Bảng Bookings
CREATE TABLE Bookings (
    BookingID INT PRIMARY KEY IDENTITY(1,1),
    CustomerID INT NOT NULL,
    RoomID INT NOT NULL,
    CheckInDate DATE NOT NULL,
    CheckOutDate DATE NOT NULL,
    NumberOfGuests TINYINT NOT NULL,
    BookingDate DATETIME DEFAULT GETDATE(),
    TotalPrice DECIMAL(10, 2),
    BookingStatus VARCHAR(50),
    SpecialRequests NTEXT,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)
);

-- Bảng Users (Quản lý người dùng và phân quyền)
CREATE TABLE Users (
    UserID INT PRIMARY KEY IDENTITY(1,1),
    FullName NVARCHAR(255) NOT NULL,
    Username VARCHAR(50) UNIQUE NOT NULL,
    PasswordHash VARBINARY(255) NOT NULL,
    Salt VARBINARY(255) NOT NULL,
    HotelID INT,
    IsGroup BIT DEFAULT 0,
    Role VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE,
    PhoneNumber VARCHAR(20),
    IsActive BIT DEFAULT 1,
    CreatedDate DATETIME DEFAULT GETDATE(),
    LastLogin DATETIME,
    FOREIGN KEY (HotelID) REFERENCES Hotels(HotelID)
);

-- Thêm index cho bảng Users
CREATE INDEX IX_Users_Username ON Users (Username);
CREATE INDEX IX_Users_Email ON Users (Email);
CREATE INDEX IX_Users_HotelID ON Users (HotelID);

-- Bảng Services
CREATE TABLE Services (
    ServiceID INT PRIMARY KEY IDENTITY(1,1),
    HotelID INT NOT NULL,
    ServiceName NVARCHAR(100) NOT NULL,
    Description NTEXT,
    Price DECIMAL(10, 2),
    FOREIGN KEY (HotelID) REFERENCES Hotels(HotelID)
);

-- Bảng RoomFacilities
CREATE TABLE RoomFacilities (
    FacilityID INT PRIMARY KEY IDENTITY(1,1),
    FacilityName NVARCHAR(100) NOT NULL,
    Description NTEXT
);

-- Bảng trung gian Room_Facilities (mối quan hệ Nhiều - Nhiều giữa Rooms và RoomFacilities)
CREATE TABLE Room_Facilities (
    RoomID INT NOT NULL,
    FacilityID INT NOT NULL,
    PRIMARY KEY (RoomID, FacilityID),
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID),
    FOREIGN KEY (FacilityID) REFERENCES RoomFacilities(FacilityID)
);

-- Bảng SeasonalPrices (ví dụ về quản lý giá đặc biệt theo mùa)
CREATE TABLE SeasonalPrices (
    PriceID INT PRIMARY KEY IDENTITY(1,1),
    RoomID INT NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    SpecialPrice DECIMAL(10, 2) NOT NULL,
    Description NVARCHAR(255),
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)
);