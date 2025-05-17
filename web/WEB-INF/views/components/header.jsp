<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
        <div class="container">
            <a class="navbar-brand" href="home">
                <span class="logo-text">LuxuryHotel</span>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link ${pageContext.request.servletPath == '/WEB-INF/views/index.jsp' ? 'active' : ''}" href="home">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${pageContext.request.servletPath == '/WEB-INF/views/rooms.jsp' ? 'active' : ''}" href="rooms">Rooms</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${pageContext.request.servletPath == '/WEB-INF/views/services.jsp' ? 'active' : ''}" href="services">Services</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${pageContext.request.servletPath == '/WEB-INF/views/about.jsp' ? 'active' : ''}" href="about">About Us</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${pageContext.request.servletPath == '/WEB-INF/views/contact.jsp' ? 'active' : ''}" href="contact">Contact</a>
                    </li>
                </ul>
                <div class="d-flex">
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <a href="login" class="btn btn-outline-primary me-2">Login</a>
                            <a href="register" class="btn btn-primary">Register</a>
                        </c:when>
                        <c:otherwise>
                            <div class="dropdown">
                                <button class="btn btn-outline-primary dropdown-toggle" type="button" id="userDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="fas fa-user-circle me-1"></i> ${sessionScope.user.fullName}
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                                    <c:choose>
                                        <c:when test="${sessionScope.user.role == 'admin'}">
                                            <li><a class="dropdown-item" href="admin/dashboard"><i class="fas fa-user-shield me-2"></i>Admin Panel</a></li>
                                            <li><hr class="dropdown-divider"></li>
                                            <li><a class="dropdown-item text-danger" href="logout"><i class="fas fa-sign-out-alt me-2"></i>Logout</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a class="dropdown-item" href="profile"><i class="fas fa-user me-2"></i>My Profile</a></li>
                                            <c:if test="${sessionScope.user.role == 'admin'}">
                                                <li><a class="dropdown-item" href="admin/dashboard"><i class="fas fa-user-shield me-2"></i>Admin Panel</a></li>
                                            </c:if>
                                            <li><hr class="dropdown-divider"></li>
                                            <li><a class="dropdown-item text-danger" href="logout"><i class="fas fa-sign-out-alt me-2"></i>Logout</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </nav>
</header>
