<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Admin Dashboard - Hotel Booking System</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
    />
    <style>
      .sidebar {
        min-height: calc(100vh - 56px);
        background-color: #343a40;
      }
      .sidebar .nav-link {
        color: rgba(255, 255, 255, 0.75);
        padding: 0.75rem 1rem;
        border-radius: 0;
      }
      .sidebar .nav-link:hover {
        color: rgba(255, 255, 255, 0.95);
        background-color: rgba(255, 255, 255, 0.1);
      }
      .sidebar .nav-link.active {
        color: #fff;
        background-color: rgba(255, 255, 255, 0.2);
      }
      .sidebar .nav-link i {
        width: 20px;
        margin-right: 10px;
        text-align: center;
      }
      .card-dashboard {
        border-left: 4px solid;
        border-radius: 4px;
      }
      .card-dashboard.primary {
        border-left-color: #4e73df;
      }
      .card-dashboard.success {
        border-left-color: #1cc88a;
      }
      .card-dashboard.info {
        border-left-color: #36b9cc;
      }
      .card-dashboard.warning {
        border-left-color: #f6c23e;
      }
      .card-dashboard .card-body {
        padding: 1.25rem;
      }
      .card-dashboard .card-title {
        text-transform: uppercase;
        color: #4e73df;
        font-weight: 700;
        font-size: 0.8rem;
        margin-bottom: 0.25rem;
      }
      .card-dashboard .card-value {
        color: #5a5c69;
        font-weight: 700;
        font-size: 1.5rem;
        margin-bottom: 0;
      }
      .card-dashboard.primary .card-title {
        color: #4e73df;
      }
      .card-dashboard.success .card-title {
        color: #1cc88a;
      }
      .card-dashboard.info .card-title {
        color: #36b9cc;
      }
      .card-dashboard.warning .card-title {
        color: #f6c23e;
      }
      .table-responsive {
        overflow-x: auto;
      }
    </style>
  </head>
  <body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
          <i class="fas fa-hotel me-2"></i>Hotel Booking System
        </a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav ms-auto">
            <li class="nav-item dropdown">
              <a
                class="nav-link dropdown-toggle"
                href="#"
                id="navbarDropdown"
                role="button"
                data-bs-toggle="dropdown"
              >
                <i class="fas fa-user-circle me-1"></i> ${user.fullName}
              </a>
              <ul class="dropdown-menu dropdown-menu-end">
                <li>
                  <a
                    class="dropdown-item text-danger"
                    href="${pageContext.request.contextPath}/logout"
                    ><i class="fas fa-sign-out-alt me-2"></i>Logout</a
                  >
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <!-- Sidebar -->
        <div class="col-md-3 col-lg-2 d-md-block sidebar collapse">
          <div class="position-sticky pt-3">
            <ul class="nav flex-column">
              <li class="nav-item">
                <a
                  class="nav-link ${activeTab == null || activeTab == 'dashboard' ? 'active' : ''}"
                  href="#dashboard"
                  data-bs-toggle="tab"
                  onclick="setActiveTab('dashboard')"
                >
                  <i class="fas fa-tachometer-alt"></i> Dashboard
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link ${activeTab == 'users' ? 'active' : ''}"
                   href="#users"
                   data-bs-toggle="tab"
                   onclick="setActiveTab('users')">
                  <i class="fas fa-users"></i> Users
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link ${activeTab == 'rooms' ? 'active' : ''}"
                   href="#rooms"
                   data-bs-toggle="tab"
                   onclick="setActiveTab('rooms')">
                  <i class="fas fa-bed"></i> Rooms
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link ${activeTab == 'revenue' ? 'active' : ''}"
                   href="#revenue"
                   data-bs-toggle="tab"
                   onclick="setActiveTab('revenue')">
                  <i class="fas fa-chart-line"></i> Revenue
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link ${activeTab == 'settings' ? 'active' : ''}"
                   href="#settings"
                   data-bs-toggle="tab"
                   onclick="setActiveTab('settings')">
                  <i class="fas fa-cog"></i> Settings
                </a>
              </li>
            </ul>
          </div>
        </div>

        <!-- Main Content -->
        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
          <div class="tab-content">
            <!-- Dashboard Tab -->
            <div class="tab-pane fade ${activeTab == null || activeTab == 'dashboard' ? 'show active' : ''}" id="dashboard">
              <div
                class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom"
              >
                <h1 class="h2">Dashboard</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                  <div class="btn-group me-2">
                    <button
                      type="button"
                      class="btn btn-sm btn-outline-secondary"
                    >
                      Share
                    </button>
                    <button
                      type="button"
                      class="btn btn-sm btn-outline-secondary"
                    >
                      Export
                    </button>
                  </div>
                  <button
                    type="button"
                    class="btn btn-sm btn-outline-secondary dropdown-toggle"
                  >
                    <i class="fas fa-calendar me-1"></i> This week
                  </button>
                </div>
              </div>

              <!-- Dashboard Cards -->
              <div class="row">
                <div class="col-xl-3 col-md-6 mb-4">
                  <div class="card card-dashboard primary shadow h-100">
                    <div class="card-body">
                      <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                          <div class="card-title">Total Users</div>
                          <div class="card-value">${totalUsers}</div>
                        </div>
                        <div class="col-auto">
                          <i class="fas fa-users fa-2x text-gray-300"></i>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-xl-3 col-md-6 mb-4">
                  <div class="card card-dashboard success shadow h-100">
                    <div class="card-body">
                      <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                          <div class="card-title">Active Bookings</div>
                          <div class="card-value">${totalBookings}</div>
                        </div>
                        <div class="col-auto">
                          <i
                            class="fas fa-calendar-check fa-2x text-gray-300"
                          ></i>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-xl-3 col-md-6 mb-4">
                  <div class="card card-dashboard info shadow h-100">
                    <div class="card-body">
                      <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                          <div class="card-title">Available Rooms</div>
                          <div class="card-value">${totalRooms}</div>
                        </div>
                        <div class="col-auto">
                          <i class="fas fa-bed fa-2x text-gray-300"></i>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-xl-3 col-md-6 mb-4">
                  <div class="card card-dashboard warning shadow h-100">
                    <div class="card-body">
                      <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                          <div class="card-title">Monthly Revenue</div>
                          <div class="card-value">$<fmt:formatNumber value="${totalRevenue != null ? totalRevenue : 0}" pattern="#,##0" /></div>
                        </div>
                        <div class="col-auto">
                          <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Recent Bookings -->
              <div class="card mb-4">
                <div class="card-header">
                  <i class="fas fa-table me-1"></i>
                  Recent Bookings
                </div>
                <div class="card-body">
                  <div class="table-responsive">
                    <table class="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th>ID</th>
                          <th>Customer</th>
                          <th>Room</th>
                          <th>Check In</th>
                          <th>Check Out</th>
                          <th>Status</th>
                          <th>Total</th>
                          <th>Actions</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:choose>
                          <c:when test="${empty recentBookings}">
                            <tr>
                              <td colspan="8" class="text-center">No bookings found</td>
                            </tr>
                          </c:when>
                          <c:otherwise>
                            <c:forEach items="${recentBookings}" var="booking">
                              <tr>
                                <td>#${booking.bookingID}</td>
                                <td>${booking.customer.fullName}</td>
                                <td>${booking.room.roomNumber}</td>
                                <td><fmt:formatDate value="${booking.checkInDate}" pattern="yyyy-MM-dd" /></td>
                                <td><fmt:formatDate value="${booking.checkOutDate}" pattern="yyyy-MM-dd" /></td>
                                <td>
                                  <c:choose>
                                    <c:when test="${booking.bookingStatus == 'Confirmed'}">
                                      <span class="badge bg-success">Confirmed</span>
                                    </c:when>
                                    <c:when test="${booking.bookingStatus == 'Pending'}">
                                      <span class="badge bg-warning text-dark">Pending</span>
                                    </c:when>
                                    <c:when test="${booking.bookingStatus == 'Cancelled'}">
                                      <span class="badge bg-danger">Cancelled</span>
                                    </c:when>
                                    <c:when test="${booking.bookingStatus == 'Completed'}">
                                      <span class="badge bg-info">Completed</span>
                                    </c:when>
                                    <c:otherwise>
                                      <span class="badge bg-secondary">${booking.bookingStatus}</span>
                                    </c:otherwise>
                                  </c:choose>
                                </td>
                                <td>$<fmt:formatNumber value="${booking.totalPrice}" pattern="#,##0.00" /></td>
                                <td>
                                  <a href="#" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#viewBookingModal${booking.bookingID}">
                                    <i class="fas fa-eye"></i>
                                  </a>
                                  <a href="${pageContext.request.contextPath}/admin/bookings?action=view&bookingId=${booking.bookingID}" class="btn btn-sm btn-warning">
                                    <i class="fas fa-edit"></i>
                                  </a>
                                </td>
                              </tr>

                              <!-- View Booking Modal -->
                              <div class="modal fade" id="viewBookingModal${booking.bookingID}" tabindex="-1" aria-labelledby="viewBookingModalLabel${booking.bookingID}" aria-hidden="true">
                                <div class="modal-dialog">
                                  <div class="modal-content">
                                    <div class="modal-header">
                                      <h5 class="modal-title" id="viewBookingModalLabel${booking.bookingID}">Booking Details</h5>
                                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                      <div class="mb-3">
                                        <strong>Booking ID:</strong> #${booking.bookingID}
                                      </div>
                                      <div class="mb-3">
                                        <strong>Customer:</strong> ${booking.customer.fullName}
                                      </div>
                                      <div class="mb-3">
                                        <strong>Email:</strong> ${booking.customer.email}
                                      </div>
                                      <div class="mb-3">
                                        <strong>Room:</strong> ${booking.room.roomNumber}
                                      </div>
                                      <div class="mb-3">
                                        <strong>Check In:</strong> <fmt:formatDate value="${booking.checkInDate}" pattern="yyyy-MM-dd" />
                                      </div>
                                      <div class="mb-3">
                                        <strong>Check Out:</strong> <fmt:formatDate value="${booking.checkOutDate}" pattern="yyyy-MM-dd" />
                                      </div>
                                      <div class="mb-3">
                                        <strong>Guests:</strong> ${booking.numberOfGuests}
                                      </div>
                                      <div class="mb-3">
                                        <strong>Status:</strong>
                                        <c:choose>
                                          <c:when test="${booking.bookingStatus == 'Confirmed'}">
                                            <span class="badge bg-success">Confirmed</span>
                                          </c:when>
                                          <c:when test="${booking.bookingStatus == 'Pending'}">
                                            <span class="badge bg-warning text-dark">Pending</span>
                                          </c:when>
                                          <c:when test="${booking.bookingStatus == 'Cancelled'}">
                                            <span class="badge bg-danger">Cancelled</span>
                                          </c:when>
                                          <c:when test="${booking.bookingStatus == 'Completed'}">
                                            <span class="badge bg-info">Completed</span>
                                          </c:when>
                                          <c:otherwise>
                                            <span class="badge bg-secondary">${booking.bookingStatus}</span>
                                          </c:otherwise>
                                        </c:choose>
                                      </div>
                                      <div class="mb-3">
                                        <strong>Total:</strong> $<fmt:formatNumber value="${booking.totalPrice}" pattern="#,##0.00" />
                                      </div>
                                      <div class="mb-3">
                                        <strong>Booking Date:</strong> <fmt:formatDate value="${booking.bookingDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                                      </div>
                                      <c:if test="${not empty booking.specialRequests}">
                                        <div class="mb-3">
                                          <strong>Special Requests:</strong> ${booking.specialRequests}
                                        </div>
                                      </c:if>
                                    </div>
                                    <div class="modal-footer">
                                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                      <a href="${pageContext.request.contextPath}/admin/bookings?action=view&bookingId=${booking.bookingID}" class="btn btn-primary">Edit</a>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </c:forEach>
                          </c:otherwise>
                        </c:choose>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>

            <!-- Users Tab -->
            <div class="tab-pane fade ${activeTab == 'users' ? 'show active' : ''}" id="users">
              <div
                class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom"
              >
                <h1 class="h2">User Management</h1>
                <button
                  type="button"
                  class="btn btn-primary"
                  data-bs-toggle="modal"
                  data-bs-target="#addUserModal"
                >
                  <i class="fas fa-plus me-1"></i> Add New User
                </button>
              </div>

              <!-- Search and Filter -->
              <div class="row mb-3">
                <form id="userSearchForm" action="${pageContext.request.contextPath}/admin/dashboard" method="get">
                  <input type="hidden" name="tab" value="users" id="tabInput">
                  <div class="row">
                    <div class="col-md-6">
                      <div class="input-group">
                        <input
                          type="text"
                          class="form-control"
                          name="search"
                          id="searchInput"
                          placeholder="Search users..."
                          value="${param.search}"
                        />
                        <button class="btn btn-primary" type="submit">
                          <i class="fas fa-search"></i> Search
                        </button>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="d-flex justify-content-end">
                        <select class="form-select me-2" style="width: auto" name="role" id="roleFilter">
                          <option value="">All Roles</option>
                          <option value="admin" ${param.role == 'admin' ? 'selected' : ''}>Admin</option>
                          <option value="user" ${param.role == 'user' ? 'selected' : ''}>User</option>
                          <option value="staff" ${param.role == 'staff' ? 'selected' : ''}>Staff</option>
                        </select>
                        <select class="form-select" style="width: auto" name="status" id="statusFilter">
                          <option value="" ${param.status == '' || param.status == null ? 'selected' : ''}>All Status</option>
                          <option value="1" ${param.status == '1' ? 'selected' : ''}>Active</option>
                          <option value="0" ${param.status == '0' ? 'selected' : ''}>Inactive</option>
                        </select>
                        <button type="button" class="btn btn-secondary" id="resetFiltersBtn">
                          <i class="fas fa-sync"></i> Reset Filters
                        </button>
                      </div>
                    </div>
                  </div>
                </form>
              </div>

              <!-- Users Table -->
              <div class="card mb-4">
                <div class="card-header">
                  <i class="fas fa-users me-1"></i>
                  Users
                </div>
                <div class="card-body">
                  <div class="table-responsive">
                    <table class="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th>ID</th>
                          <th>Name</th>
                          <th>Username</th>
                          <th>Email</th>
                          <th>Role</th>
                          <th>Status</th>
                          <th>Created Date</th>
                          <th>Actions</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${users}" var="userItem">
                          <tr>
                            <td>${userItem.userID}</td>
                            <td>${userItem.fullName}</td>
                            <td>${userItem.username}</td>
                            <td>${userItem.email}</td>
                            <td>
                              <c:choose>
                                <c:when test="${userItem.role == 'admin'}">
                                  <span class="badge bg-danger">Admin</span>
                                </c:when>
                                <c:when test="${userItem.role == 'staff'}">
                                  <span class="badge bg-info">Staff</span>
                                </c:when>
                                <c:otherwise>
                                  <span class="badge bg-primary">User</span>
                                </c:otherwise>
                              </c:choose>
                            </td>
                            <td>
                              <c:choose>
                                <c:when test="${userItem.isActive}">
                                  <span class="badge bg-success">Active</span>
                                </c:when>
                                <c:otherwise>
                                  <span class="badge bg-secondary">Inactive</span>
                                </c:otherwise>
                              </c:choose>
                            </td>
                            <td>
                              <fmt:formatDate value="${userItem.createdDate}" pattern="yyyy-MM-dd" />
                            </td>
                            <td>
                              <a href="#" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#viewUserModal${userItem.userID}">
                                <i class="fas fa-eye"></i>
                              </a>
                              <a href="#" class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#editUserModal${userItem.userID}">
                                <i class="fas fa-edit"></i>
                              </a>
                              <a href="#" class="btn btn-sm btn-danger" onclick="if(confirm('Are you sure you want to delete this user?')) { window.location.href='${pageContext.request.contextPath}/admin/users?action=delete&userId=${userItem.userID}'; }">
                                <i class="fas fa-trash"></i>
                              </a>
                            </td>
                          </tr>

                          <!-- View User Modal -->
                          <div class="modal fade" id="viewUserModal${userItem.userID}" tabindex="-1" aria-labelledby="viewUserModalLabel${userItem.userID}" aria-hidden="true">
                            <div class="modal-dialog">
                              <div class="modal-content">
                                <div class="modal-header">
                                  <h5 class="modal-title" id="viewUserModalLabel${userItem.userID}">User Details</h5>
                                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                  <div class="row">
                                    <div class="col-md-4 text-center mb-3">
                                      <!-- User Profile Image -->
                                      <c:choose>
                                        <c:when test="${not empty userItem.profileImage}">
                                          <img src="${pageContext.request.contextPath}/storage/uploads/profile/${userItem.profileImage}"
                                               class="img-fluid rounded-circle mb-2"
                                               alt="${userItem.fullName}"
                                               style="width: 150px; height: 150px; object-fit: cover;"
                                               onerror="this.src='${pageContext.request.contextPath}/img/default-profile.svg'">
                                        </c:when>
                                        <c:otherwise>
                                          <img src="${pageContext.request.contextPath}/img/default-profile.svg"
                                               class="img-fluid rounded-circle mb-2"
                                               alt="Default Profile"
                                               style="width: 150px; height: 150px; object-fit: cover;">
                                        </c:otherwise>
                                      </c:choose>
                                      <h5>${userItem.fullName}</h5>
                                      <span class="badge
                                        <c:choose>
                                          <c:when test="${userItem.role == 'admin'}">bg-danger</c:when>
                                          <c:when test="${userItem.role == 'staff'}">bg-info</c:when>
                                          <c:otherwise>bg-primary</c:otherwise>
                                        </c:choose>">
                                        ${userItem.role}
                                      </span>
                                    </div>
                                    <div class="col-md-8">
                                      <div class="mb-3">
                                        <strong>ID:</strong> ${userItem.userID}
                                      </div>
                                      <div class="mb-3">
                                        <strong>Username:</strong> ${userItem.username}
                                      </div>
                                      <div class="mb-3">
                                        <strong>Email:</strong> ${userItem.email}
                                      </div>
                                      <div class="mb-3">
                                        <strong>Gender:</strong> ${userItem.gender}
                                      </div>
                                      <div class="mb-3">
                                        <strong>Phone:</strong> ${userItem.phoneNumber}
                                      </div>
                                      <div class="mb-3">
                                        <strong>Status:</strong>
                                        <c:choose>
                                          <c:when test="${userItem.isActive}">
                                            <span class="badge bg-success">Active</span>
                                          </c:when>
                                          <c:otherwise>
                                            <span class="badge bg-secondary">Inactive</span>
                                          </c:otherwise>
                                        </c:choose>
                                      </div>
                                      <div class="mb-3">
                                        <strong>Created Date:</strong> <fmt:formatDate value="${userItem.createdDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                                      </div>
                                      <div class="mb-3">
                                        <strong>Last Login:</strong> <fmt:formatDate value="${userItem.lastLogin}" pattern="yyyy-MM-dd HH:mm:ss" />
                                      </div>
                                    </div>
                                  </div>
                                </div>
                                <div class="modal-footer">
                                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                </div>
                              </div>
                            </div>
                          </div>

                          <!-- Edit User Modal -->
                          <div class="modal fade" id="editUserModal${userItem.userID}" tabindex="-1" aria-labelledby="editUserModalLabel${userItem.userID}" aria-hidden="true">
                            <div class="modal-dialog">
                              <div class="modal-content">
                                <div class="modal-header">
                                  <h5 class="modal-title" id="editUserModalLabel${userItem.userID}">Edit User</h5>
                                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <form action="${pageContext.request.contextPath}/admin/users" method="post">
                                  <input type="hidden" name="action" value="update">
                                  <input type="hidden" name="userId" value="${userItem.userID}">
                                  <div class="modal-body">
                                    <div class="mb-3">
                                      <label for="fullName${userItem.userID}" class="form-label">Full Name</label>
                                      <input type="text" class="form-control" id="fullName${userItem.userID}" name="fullName" value="${userItem.fullName}" required>
                                    </div>
                                    <div class="mb-3">
                                      <label for="email${userItem.userID}" class="form-label">Email</label>
                                      <input type="email" class="form-control" id="email${userItem.userID}" name="email" value="${userItem.email}" required>
                                    </div>
                                    <div class="mb-3">
                                      <label for="role${userItem.userID}" class="form-label">Role</label>
                                      <select class="form-select" id="role${userItem.userID}" name="role" required 
                                                        ${user.userID == userItem.userID ? 'disabled' : ''}>
                                                        <option value="admin" ${userItem.role == 'admin' ? 'selected' : ''}>Admin</option>
                                                        <option value="staff" ${userItem.role == 'staff' ? 'selected' : ''}>Staff</option>
                                                        <option value="user" ${userItem.role == 'user' ? 'selected' : ''}>User</option>
                                                      </select>
                                                      <c:if test="${user.userID == userItem.userID}">
                                                        <input type="hidden" name="role" value="${userItem.role}">
                                                        <small class="text-muted">
                                                          <i class="fas fa-lock me-1"></i>You cannot change your own role
                                                        </small>
                                                      </c:if>
                                    </div>
                                    <div class="mb-3">
                                      <label for="gender${userItem.userID}" class="form-label">Gender</label>
                                      <select class="form-select" id="gender${userItem.userID}" name="gender">
                                        <option value="" ${empty userItem.gender ? 'selected' : ''}>Select Gender</option>
                                        <option value="Male" ${userItem.gender == 'Male' ? 'selected' : ''}>Male</option>
                                        <option value="Female" ${userItem.gender == 'Female' ? 'selected' : ''}>Female</option>
                                        <option value="Other" ${userItem.gender == 'Other' ? 'selected' : ''}>Other</option>
                                      </select>
                                    </div>
                                    <div class="mb-3">
                                      <label for="phoneNumber${userItem.userID}" class="form-label">Phone Number</label>
                                      <input type="tel" class="form-control" id="phoneNumber${userItem.userID}" name="phoneNumber" value="${userItem.phoneNumber}">
                                    </div>
                                    <div class="mb-3">
                                      <label for="isActive${userItem.userID}" class="form-label">Status</label>
                                      <select class="form-select" id="isActive${userItem.userID}" name="isActive">
                                        <option value="1" ${userItem.isActive ? 'selected' : ''}>Active</option>
                                        <option value="0" ${!userItem.isActive ? 'selected' : ''}>Inactive</option>
                                      </select>
                                    </div>
                                  </div>
                                  <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                  </div>
                                </form>
                              </div>
                            </div>
                          </div>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>

                  <!-- Pagination -->
                  <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                      <li class="page-item disabled">
                        <a
                          class="page-link"
                          href="#"
                          tabindex="-1"
                          aria-disabled="true"
                          >Previous</a
                        >
                      </li>
                      <li class="page-item active">
                        <a class="page-link" href="#">1</a>
                      </li>
                      <li class="page-item">
                        <a class="page-link" href="#">2</a>
                      </li>
                      <li class="page-item">
                        <a class="page-link" href="#">3</a>
                      </li>
                      <li class="page-item">
                        <a class="page-link" href="#">Next</a>
                      </li>
                    </ul>
                  </nav>
                </div>
              </div>

              <!-- Add User Modal -->
              <div
                class="modal fade"
                id="addUserModal"
                tabindex="-1"
                aria-labelledby="addUserModalLabel"
                aria-hidden="true"
              >
                <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="addUserModalLabel">
                        Add New User
                      </h5>
                      <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div class="modal-body">
                      <form id="addUserForm">
                        <div class="row mb-3">
                          <div class="col-md-6">
                            <label for="fullName" class="form-label"
                              >Full Name</label
                            >
                            <input
                              type="text"
                              class="form-control"
                              id="fullName"
                              name="fullName"
                              required
                            />
                          </div>
                          <div class="col-md-6">
                            <label for="username" class="form-label"
                              >Username</label
                            >
                            <input
                              type="text"
                              class="form-control"
                              id="username"
                              name="username"
                              required
                            />
                          </div>
                        </div>
                        <div class="row mb-3">
                          <div class="col-md-6">
                            <label for="email" class="form-label">Email</label>
                            <input
                              type="email"
                              class="form-control"
                              id="email"
                              name="email"
                              required
                            />
                          </div>
                          <div class="col-md-6">
                            <label for="phoneNumber" class="form-label"
                              >Phone Number</label
                            >
                            <input
                              type="tel"
                              class="form-control"
                              id="phoneNumber"
                              name="phoneNumber"
                            />
                          </div>
                        </div>
                        <div class="row mb-3">
                          <div class="col-md-6">
                            <label for="password" class="form-label"
                              >Password</label
                            >
                            <input
                              type="password"
                              class="form-control"
                              id="password"
                              name="password"
                              required
                            />
                          </div>
                          <div class="col-md-6">
                            <label for="confirmPassword" class="form-label"
                              >Confirm Password</label
                            >
                            <input
                              type="password"
                              class="form-control"
                              id="confirmPassword"
                              name="confirmPassword"
                              required
                            />
                          </div>
                        </div>
                        <div class="row mb-3">
                          <div class="col-md-6">
                            <label for="role" class="form-label">Role</label>
                            <select
                              class="form-select"
                              id="role"
                              name="role"
                              required
                            >
                              <option value="">Select Role</option>
                              <option value="admin">Admin</option>
                              <option value="user">User</option>
                              <option value="staff">Staff</option>
                            </select>
                          </div>
                          <div class="col-md-6">
                            <label for="gender" class="form-label"
                              >Gender</label
                            >
                            <select
                              class="form-select"
                              id="gender"
                              name="gender"
                            >
                              <option value="">Select Gender</option>
                              <option value="Male">Male</option>
                              <option value="Female">Female</option>
                              <option value="Other">Other</option>
                            </select>
                          </div>
                        </div>
                      </form>
                    </div>
                    <div class="modal-footer">
                      <button
                        type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal"
                      >
                        Cancel
                      </button>
                      <button type="button" class="btn btn-primary">
                        Add User
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Rooms Tab -->
            <div class="tab-pane fade ${activeTab == 'rooms' ? 'show active' : ''}" id="rooms">
              <div
                class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom"
              >
                <h1 class="h2">Room Management</h1>
                <button
                  type="button"
                  class="btn btn-primary"
                  data-bs-toggle="modal"
                  data-bs-target="#addRoomModal"
                >
                  <i class="fas fa-plus me-1"></i> Add New Room
                </button>
              </div>

              <!-- Search and Filter -->
              <div class="row mb-3">
                <form id="roomSearchForm" action="${pageContext.request.contextPath}/admin/dashboard" method="get">
                  <input type="hidden" name="tab" value="rooms" id="roomTabInput">
                  <div class="row">
                    <div class="col-md-6">
                      <div class="input-group">
                        <input
                          type="text"
                          class="form-control"
                          name="roomSearch"
                          id="roomSearchInput"
                          placeholder="Search rooms..."
                          value="${param.roomSearch}"
                        />
                        <button class="btn btn-primary" type="submit">
                          <i class="fas fa-search"></i> Search
                        </button>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="d-flex justify-content-end">
                        <select class="form-select me-2" style="width: auto" name="roomType" id="roomTypeFilter">
                          <option value="">All Room Types</option>
                          <option value="Standard">Standard</option>
                          <option value="Deluxe">Deluxe</option>
                          <option value="Suite">Suite</option>
                        </select>
                        <select class="form-select me-2" style="width: auto" name="roomStatus" id="roomStatusFilter">
                          <option value="">All Status</option>
                          <option value="Available">Available</option>
                          <option value="Occupied">Occupied</option>
                          <option value="Maintenance">Maintenance</option>
                        </select>
                        <button type="button" class="btn btn-secondary" id="resetRoomFiltersBtn">
                          <i class="fas fa-sync"></i> Reset Filters
                        </button>
                      </div>
                    </div>
                  </div>
                </form>
              </div>

              <!-- Rooms Table -->
              <div class="card mb-4">
                <div class="card-header">
                  <i class="fas fa-bed me-1"></i>
                  Rooms
                </div>
                <div class="card-body">
                  <div class="table-responsive">
                    <table class="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th>Room #</th>
                          <th>Type</th>
                          <th>Price</th>
                          <th>Capacity</th>
                          <th>Status</th>
                          <th>Features</th>
                          <th>Actions</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td colspan="7" class="text-center">Room management will be implemented later</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>

              <!-- Add Room Modal Placeholder -->
              <div class="modal fade" id="addRoomModal" tabindex="-1" aria-labelledby="addRoomModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="addRoomModalLabel">Add New Room</h5>
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                      <p class="text-center">Room management functionality will be implemented later.</p>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Revenue Tab -->
            <div class="tab-pane fade ${activeTab == 'revenue' ? 'show active' : ''}" id="revenue">
              <div
                class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom"
              >
                <h1 class="h2">Revenue Management</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                  <div class="btn-group me-2">
                    <button type="button" class="btn btn-sm btn-outline-secondary">Export</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary">Print</button>
                  </div>
                  <button type="button" class="btn btn-sm btn-outline-secondary dropdown-toggle">
                    <i class="fas fa-calendar me-1"></i> This Month
                  </button>
                </div>
              </div>

              <!-- Revenue Overview Cards -->
              <div class="row">
                <div class="col-xl-3 col-md-6 mb-4">
                  <div class="card card-dashboard success shadow h-100">
                    <div class="card-body">
                      <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                          <div class="card-title">Total Revenue</div>
                          <div class="card-value">$10,000</div>
                        </div>
                        <div class="col-auto">
                          <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-xl-3 col-md-6 mb-4">
                  <div class="card card-dashboard primary shadow h-100">
                    <div class="card-body">
                      <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                          <div class="card-title">Bookings</div>
                          <div class="card-value">150</div>
                        </div>
                        <div class="col-auto">
                          <i class="fas fa-calendar-check fa-2x text-gray-300"></i>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-xl-3 col-md-6 mb-4">
                  <div class="card card-dashboard info shadow h-100">
                    <div class="card-body">
                      <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                          <div class="card-title">Occupancy Rate</div>
                          <div class="card-value">75%</div>
                        </div>
                        <div class="col-auto">
                          <i class="fas fa-bed fa-2x text-gray-300"></i>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-xl-3 col-md-6 mb-4">
                  <div class="card card-dashboard warning shadow h-100">
                    <div class="card-body">
                      <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                          <div class="card-title">Avg. Daily Rate</div>
                          <div class="card-value">$120</div>
                        </div>
                        <div class="col-auto">
                          <i class="fas fa-chart-line fa-2x text-gray-300"></i>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Revenue Chart Placeholder -->
              <div class="card mb-4">
                <div class="card-header">
                  <i class="fas fa-chart-bar me-1"></i>
                  Monthly Revenue
                </div>
                <div class="card-body">
                  <div style="height: 300px; background-color: #f8f9fa; display: flex; align-items: center; justify-content: center;">
                    <p class="text-muted">Revenue chart will be implemented later</p>
                  </div>
                </div>
              </div>

              <!-- Revenue by Room Type -->
              <div class="card mb-4">
                <div class="card-header">
                  <i class="fas fa-chart-pie me-1"></i>
                  Revenue by Room Type
                </div>
                <div class="card-body">
                  <div class="table-responsive">
                    <table class="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th>Room Type</th>
                          <th>Bookings</th>
                          <th>Revenue</th>
                          <th>Avg. Price</th>
                          <th>Occupancy Rate</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td colspan="5" class="text-center">Revenue management will be implemented later</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>

            <!-- Settings Tab -->
            <div class="tab-pane fade ${activeTab == 'settings' ? 'show active' : ''}" id="settings">
              <div
                class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom"
              >
                <h1 class="h2">Settings</h1>
              </div>

              <!-- Settings Placeholder -->
              <div class="card mb-4">
                <div class="card-header">
                  <i class="fas fa-cog me-1"></i>
                  System Settings
                </div>
                <div class="card-body">
                  <form>
                    <div class="mb-3">
                      <label for="hotelName" class="form-label"
                        >Hotel Name</label
                      >
                      <input
                        type="text"
                        class="form-control"
                        id="hotelName"
                        value="Luxury Hotel"
                      />
                    </div>
                    <div class="mb-3">
                      <label for="contactEmail" class="form-label"
                        >Contact Email</label
                      >
                      <input
                        type="email"
                        class="form-control"
                        id="contactEmail"
                        value="info@luxuryhotel.com"
                      />
                    </div>
                    <div class="mb-3">
                      <label for="contactPhone" class="form-label"
                        >Contact Phone</label
                      >
                      <input
                        type="tel"
                        class="form-control"
                        id="contactPhone"
                        value="+1-212-555-0000"
                      />
                    </div>
                    <button type="submit" class="btn btn-primary">
                      Save Settings
                    </button>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
      // Function to set active tab in URL parameter
      function setActiveTab(tabName) {
        // We're using this to remember the tab for form submissions
        // This doesn't navigate, just updates a cookie
        document.cookie = `activeTab=${tabName}; path=/`;
      }

      // Activate tabs based on activeTab attribute or URL hash
      document.addEventListener("DOMContentLoaded", function () {
        const activeTab = "${activeTab}";
        let tabSelector = "#dashboard";

        if (activeTab && activeTab !== "") {
          tabSelector = "#" + activeTab;
        } else if (window.location.hash) {
          tabSelector = window.location.hash;
        }

        const tabToActivate = document.querySelector(
          `.nav-link[href="${tabSelector}"]`
        );

        if (tabToActivate) {
          const tab = new bootstrap.Tab(tabToActivate);
          tab.show();
        }

        // Handle filter changes
        const roleFilter = document.getElementById('roleFilter');
        const statusFilter = document.getElementById('statusFilter');
        const searchInput = document.getElementById('searchInput');

        if (roleFilter && statusFilter) {
          // Auto-submit when role filter changes
          roleFilter.addEventListener('change', function() {
            document.getElementById('userSearchForm').submit();
          });

          // Auto-submit when status filter changes
          statusFilter.addEventListener('change', function() {
            document.getElementById('userSearchForm').submit();
          });
        }

        // Add event listener for search input
        if (searchInput) {
          // Auto-submit when user presses Enter in search input
          searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
              e.preventDefault(); // Prevent default form submission
              document.getElementById('userSearchForm').submit();
            }
          });
        }

        // Handle form submission
        const userSearchForm = document.getElementById('userSearchForm');
        if (userSearchForm) {
          userSearchForm.addEventListener('submit', function(e) {
            // Get the active tab from cookie
            const cookies = document.cookie.split(';');
            let activeTabCookie = '';
            for (let i = 0; i < cookies.length; i++) {
              const cookie = cookies[i].trim();
              if (cookie.startsWith('activeTab=')) {
                activeTabCookie = cookie.substring('activeTab='.length);
                break;
              }
            }

            // Set the tab input value
            if (activeTabCookie) {
              document.getElementById('tabInput').value = activeTabCookie;
            }
          });
        }

        // Handle user reset button click
        const resetFiltersBtn = document.getElementById('resetFiltersBtn');
        if (resetFiltersBtn) {
          resetFiltersBtn.addEventListener('click', function() {
            // Show the users tab
            const usersTab = document.querySelector('.nav-link[href="#users"]');
            if (usersTab) {
              const tab = new bootstrap.Tab(usersTab);
              tab.show();
              setActiveTab('users');
            }

            // Clear search input
            const searchInput = document.getElementById('searchInput');
            if (searchInput) {
              searchInput.value = '';
            }

            // Reset role filter
            const roleFilter = document.getElementById('roleFilter');
            if (roleFilter) {
              roleFilter.value = '';
            }

            // Reset status filter
            const statusFilter = document.getElementById('statusFilter');
            if (statusFilter) {
              statusFilter.value = '';
            }

            // Reload the page with the users tab active
            window.location.href = '${pageContext.request.contextPath}/admin/dashboard?tab=users';
          });
        }

        // Handle room reset button click
        const resetRoomFiltersBtn = document.getElementById('resetRoomFiltersBtn');
        if (resetRoomFiltersBtn) {
          resetRoomFiltersBtn.addEventListener('click', function() {
            // Show the rooms tab
            const roomsTab = document.querySelector('.nav-link[href="#rooms"]');
            if (roomsTab) {
              const tab = new bootstrap.Tab(roomsTab);
              tab.show();
              setActiveTab('rooms');
            }

            // Clear room search input
            const roomSearchInput = document.getElementById('roomSearchInput');
            if (roomSearchInput) {
              roomSearchInput.value = '';
            }

            // Reset room type filter
            const roomTypeFilter = document.getElementById('roomTypeFilter');
            if (roomTypeFilter) {
              roomTypeFilter.value = '';
            }

            // Reset room status filter
            const roomStatusFilter = document.getElementById('roomStatusFilter');
            if (roomStatusFilter) {
              roomStatusFilter.value = '';
            }

            // Reload the page with the rooms tab active
            window.location.href = '${pageContext.request.contextPath}/admin/dashboard?tab=rooms';
          });
        }
      });
    </script>
  </body>
</html>
