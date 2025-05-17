<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Hotel Booking System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <!-- Header -->
    <jsp:include page="components/header.jsp" />

    <!-- Dashboard Section -->
    <section class="dashboard-section py-5">
        <div class="container">
            <div class="row">
                <!-- Sidebar -->
                <div class="col-md-3">
                    <div class="card mb-4">
                        <div class="card-body">
                            <div class="d-flex align-items-center mb-3">
                                <div class="avatar me-3">
                                    <c:choose>
                                        <c:when test="${empty sessionScope.user.profileImage}">
                                            <i class="fas fa-user-circle fa-3x"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/${sessionScope.user.profileImage}" alt="Profile Image" class="rounded-circle" style="width: 48px; height: 48px; object-fit: cover;">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div>
                                    <h5 class="mb-0">${sessionScope.user.fullName}</h5>
                                    <small class="text-muted">${sessionScope.user.email}</small>
                                </div>
                            </div>
                            <hr>
                            <ul class="nav flex-column">
                                <li class="nav-item">
                                    <a class="nav-link active" href="#bookings" data-bs-toggle="tab">
                                        <i class="fas fa-calendar-alt me-2"></i> My Bookings
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#profile" data-bs-toggle="tab">
                                        <i class="fas fa-user me-2"></i> Profile
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#settings" data-bs-toggle="tab">
                                        <i class="fas fa-cog me-2"></i> Settings
                                    </a>
                                </li>
                                <c:if test="${sessionScope.user.role == 'admin'}">
                                    <li class="nav-item">
                                        <a class="nav-link" href="#admin" data-bs-toggle="tab">
                                            <i class="fas fa-user-shield me-2"></i> Admin Panel
                                        </a>
                                    </li>
                                </c:if>
                                <li class="nav-item">
                                    <a class="nav-link text-danger" href="logout">
                                        <i class="fas fa-sign-out-alt me-2"></i> Logout
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- Main Content -->
                <div class="col-md-9">
                    <div class="tab-content">
                        <!-- Bookings Tab -->
                        <div class="tab-pane fade show active" id="bookings">
                            <div class="card">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h4 class="mb-0">My Bookings</h4>
                                    <a href="search" class="btn btn-primary btn-sm">
                                        <i class="fas fa-plus me-1"></i> New Booking
                                    </a>
                                </div>
                                <div class="card-body">
                                    <c:choose>
                                        <c:when test="${empty bookings}">
                                            <div class="text-center py-5">
                                                <i class="fas fa-calendar-times fa-4x text-muted mb-3"></i>
                                                <h5>No bookings found</h5>
                                                <p>You haven't made any bookings yet.</p>
                                                <a href="search" class="btn btn-primary">Book a Room</a>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="table-responsive">
                                                <table class="table table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th>Booking ID</th>
                                                            <th>Room</th>
                                                            <th>Check In</th>
                                                            <th>Check Out</th>
                                                            <th>Status</th>
                                                            <th>Actions</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <!-- Sample data, replace with actual bookings -->
                                                        <tr>
                                                            <td>#12345</td>
                                                            <td>Deluxe Room</td>
                                                            <td>2023-06-15</td>
                                                            <td>2023-06-18</td>
                                                            <td><span class="badge bg-success">Confirmed</span></td>
                                                            <td>
                                                                <a href="#" class="btn btn-sm btn-outline-primary">View</a>
                                                                <a href="#" class="btn btn-sm btn-outline-danger">Cancel</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>#12346</td>
                                                            <td>Standard Room</td>
                                                            <td>2023-07-10</td>
                                                            <td>2023-07-15</td>
                                                            <td><span class="badge bg-warning text-dark">Pending</span></td>
                                                            <td>
                                                                <a href="#" class="btn btn-sm btn-outline-primary">View</a>
                                                                <a href="#" class="btn btn-sm btn-outline-danger">Cancel</a>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>

                        <!-- Profile Tab -->
                        <div class="tab-pane fade" id="profile">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="mb-0">Profile Information</h4>
                                </div>
                                <div class="card-body">
                                    <!-- Profile Image Upload -->
                                    <div class="row mb-4">
                                        <div class="col-md-12">
                                            <div class="d-flex align-items-center mb-3">
                                                <div class="me-4">
                                                    <c:choose>
                                                        <c:when test="${empty sessionScope.user.profileImage}">
                                                            <div class="profile-image-placeholder">
                                                                <i class="fas fa-user-circle fa-5x"></i>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="${pageContext.request.contextPath}/${sessionScope.user.profileImage}" alt="Profile Image" class="rounded-circle" style="width: 100px; height: 100px; object-fit: cover;">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div>
                                                    <h5 class="mb-1">Profile Picture</h5>
                                                    <p class="text-muted small mb-3">Upload a new profile picture. JPG, JPEG, PNG or GIF (max. 10MB)</p>
                                                    <form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
                                                        <input type="hidden" name="uploadType" value="profile">
                                                        <div class="input-group">
                                                            <input type="file" class="form-control form-control-sm" id="profileImage" name="file" accept="image/*">
                                                            <button class="btn btn-primary btn-sm" type="submit">Upload</button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <hr class="mb-4">

                                    <!-- Profile Information Form -->
                                    <form action="${pageContext.request.contextPath}/profile" method="post">
                                        <input type="hidden" name="action" value="updateProfile">
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="fullName" class="form-label">Full Name</label>
                                                <input type="text" class="form-control" id="fullName" name="fullName" value="${sessionScope.user.fullName}" required>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="email" class="form-label">Email</label>
                                                <input type="email" class="form-control" id="email" value="${sessionScope.user.email}" readonly>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="phoneNumber" class="form-label">Phone Number</label>
                                                <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" value="${sessionScope.user.phoneNumber}">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="gender" class="form-label">Gender</label>
                                                <select class="form-select" id="gender" name="gender">
                                                    <option value="" ${empty sessionScope.user.gender ? 'selected' : ''}>Select Gender</option>
                                                    <option value="Male" ${sessionScope.user.gender == 'Male' ? 'selected' : ''}>Male</option>
                                                    <option value="Female" ${sessionScope.user.gender == 'Female' ? 'selected' : ''}>Female</option>
                                                    <option value="Other" ${sessionScope.user.gender == 'Other' ? 'selected' : ''}>Other</option>
                                                </select>
                                            </div>
                                        </div>
                                        <button type="submit" class="btn btn-primary">Update Profile</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!-- Settings Tab -->
                        <div class="tab-pane fade" id="settings">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="mb-0">Account Settings</h4>
                                </div>
                                <div class="card-body">
                                    <h5>Change Password</h5>
                                    <form>
                                        <div class="mb-3">
                                            <label for="currentPassword" class="form-label">Current Password</label>
                                            <input type="password" class="form-control" id="currentPassword" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="newPassword" class="form-label">New Password</label>
                                            <input type="password" class="form-control" id="newPassword" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="confirmNewPassword" class="form-label">Confirm New Password</label>
                                            <input type="password" class="form-control" id="confirmNewPassword" required>
                                        </div>
                                        <button type="submit" class="btn btn-primary">Change Password</button>
                                    </form>

                                    <hr class="my-4">

                                    <h5>Notification Preferences</h5>
                                    <form>
                                        <div class="form-check mb-2">
                                            <input class="form-check-input" type="checkbox" id="emailNotifications" checked>
                                            <label class="form-check-label" for="emailNotifications">
                                                Email Notifications
                                            </label>
                                        </div>
                                        <div class="form-check mb-2">
                                            <input class="form-check-input" type="checkbox" id="smsNotifications">
                                            <label class="form-check-label" for="smsNotifications">
                                                SMS Notifications
                                            </label>
                                        </div>
                                        <div class="form-check mb-3">
                                            <input class="form-check-input" type="checkbox" id="promotionalEmails" checked>
                                            <label class="form-check-label" for="promotionalEmails">
                                                Promotional Emails
                                            </label>
                                        </div>
                                        <button type="submit" class="btn btn-primary">Save Preferences</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!-- Admin Panel Tab -->
                        <c:if test="${sessionScope.user.role == 'admin'}">
                            <div class="tab-pane fade" id="admin">
                                <div class="card">
                                    <div class="card-header">
                                        <h4 class="mb-0">Admin Panel</h4>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-4 mb-3">
                                                <div class="card bg-primary text-white">
                                                    <div class="card-body">
                                                        <h5 class="card-title">Total Users</h5>
                                                        <h2 class="mb-0">125</h2>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-4 mb-3">
                                                <div class="card bg-success text-white">
                                                    <div class="card-body">
                                                        <h5 class="card-title">Active Bookings</h5>
                                                        <h2 class="mb-0">42</h2>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-4 mb-3">
                                                <div class="card bg-info text-white">
                                                    <div class="card-body">
                                                        <h5 class="card-title">Revenue</h5>
                                                        <h2 class="mb-0">$12,450</h2>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="mt-4">
                                            <h5>Quick Actions</h5>
                                            <div class="d-flex flex-wrap gap-2">
                                                <a href="#" class="btn btn-outline-primary">Manage Users</a>
                                                <a href="#" class="btn btn-outline-primary">Manage Rooms</a>
                                                <a href="#" class="btn btn-outline-primary">Manage Bookings</a>
                                                <a href="#" class="btn btn-outline-primary">View Reports</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <jsp:include page="components/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/scripts.js"></script>
</body>
</html>
