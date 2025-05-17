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
                  class="nav-link active"
                  href="#dashboard"
                  data-bs-toggle="tab"
                >
                  <i class="fas fa-tachometer-alt"></i> Dashboard
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#users" data-bs-toggle="tab">
                  <i class="fas fa-users"></i> Users
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#settings" data-bs-toggle="tab">
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
            <div class="tab-pane fade show active" id="dashboard">
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
            <div class="tab-pane fade" id="users">
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
                <div class="col-md-6">
                  <div class="input-group">
                    <input
                      type="text"
                      class="form-control"
                      placeholder="Search users..."
                    />
                    <button class="btn btn-outline-secondary" type="button">
                      <i class="fas fa-search"></i>
                    </button>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="d-flex justify-content-end">
                    <select class="form-select me-2" style="width: auto">
                      <option value="">All Roles</option>
                      <option value="admin">Admin</option>
                      <option value="user">User</option>
                      <option value="staff">Staff</option>
                    </select>
                    <select class="form-select" style="width: auto">
                      <option value="1">Active</option>
                      <option value="0">Inactive</option>
                      <option value="">All Status</option>
                    </select>
                  </div>
                </div>
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
                                  <div class="mb-3">
                                    <strong>ID:</strong> ${userItem.userID}
                                  </div>
                                  <div class="mb-3">
                                    <strong>Name:</strong> ${userItem.fullName}
                                  </div>
                                  <div class="mb-3">
                                    <strong>Username:</strong> ${userItem.username}
                                  </div>
                                  <div class="mb-3">
                                    <strong>Email:</strong> ${userItem.email}
                                  </div>
                                  <div class="mb-3">
                                    <strong>Role:</strong> ${userItem.role}
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
                                      <select class="form-select" id="role${userItem.userID}" name="role" required>
                                        <option value="admin" ${userItem.role == 'admin' ? 'selected' : ''}>Admin</option>
                                        <option value="staff" ${userItem.role == 'staff' ? 'selected' : ''}>Staff</option>
                                        <option value="user" ${userItem.role == 'user' ? 'selected' : ''}>User</option>
                                      </select>
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



            <!-- Settings Tab -->
            <div class="tab-pane fade" id="settings">
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
      // Activate tabs based on URL hash
      document.addEventListener("DOMContentLoaded", function () {
        const hash = window.location.hash || "#dashboard";
        const tabToActivate = document.querySelector(
          `.nav-link[href="${hash}"]`
        );
        if (tabToActivate) {
          const tab = new bootstrap.Tab(tabToActivate);
          tab.show();
        }

        // No chart initialization needed
      });
    </script>
  </body>
</html>
