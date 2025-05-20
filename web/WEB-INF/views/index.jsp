<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%> <%@ taglib prefix="c"
         uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0, shrink-to-fit=no"
            />
        <title>Hotel Booking System - Home</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
            rel="stylesheet"
            />
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
            />
        <link
            rel="stylesheet"
            href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;500;600;700&display=swap"
            />
        <link rel="stylesheet" href="css/styles.css" />
    </head>
    <body>
        <!-- Header -->
        <jsp:include page="components/header.jsp" />

        <!-- Hero Section -->
        <section
            class="hero-section"
            style="
            background-image: url('images/banner.jpg');
            background-size: cover;
            background-position: center;
            "
            >
            <div class="container">
                <div class="row">
                    <div class="col-md-6">
                        <h1>Find Your Perfect Stay</h1>
                        <p>Discover the best hotels at the best prices</p>

                        <!-- Search Form -->
                        <div class="search-form-container mt-4">
                            <form action="search" method="get" class="search-form">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="checkIn" class="form-label">Check In</label>
                                        <input
                                            type="date"
                                            class="form-control"
                                            id="checkIn"
                                            name="checkIn"
                                            required
                                            />
                                    </div>
                                    <div class="col-md-6">
                                        <label for="checkOut" class="form-label">Check Out</label>
                                        <input
                                            type="date"
                                            class="form-control"
                                            id="checkOut"
                                            name="checkOut"
                                            required
                                            />
                                    </div>
                                    <div class="col-md-4">
                                        <label for="adults" class="form-label">Adults</label>
                                        <select class="form-select" id="adults" name="adults">
                                            <option value="1">1</option>
                                            <option value="2" selected>2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <label for="children" class="form-label">Children</label>
                                        <select class="form-select" id="children" name="children">
                                            <option value="0" selected>0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <label for="roomCount" class="form-label">Rooms</label>
                                        <select class="form-select" id="roomCount" name="roomCount">
                                            <option value="1" selected>1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                    </div>
                                    <div class="col-12">
                                        <button type="submit" class="btn btn-primary w-100">
                                            Search Rooms
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <!-- This column is intentionally left empty to maintain the layout -->
                    </div>
                </div>
            </div>
        </section>

        <!-- Featured Rooms Section -->
        <section class="featured-rooms py-5">
            <div class="container">
                <h2 class="text-center mb-4">Featured Rooms</h2>
                <div class="row">
                    <div class="col-md-4 mb-4">
                        <div class="card h-100">
                            <img
                                src="images/room01.jpg"
                                class="card-img-top"
                                alt="Standard Room"
                                loading="lazy"
                                />
                            <div class="card-body">
                                <h5 class="card-title">Standard Room</h5>
                                <p class="card-text">
                                    Comfortable room with all the essentials for a pleasant stay.
                                </p>
                                <p class="price">$99 / night</p>
                                <a href="rooms" class="btn btn-outline-primary">View Details</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4 mb-4">
                        <div class="card h-100">
                            <img
                                src="images/room02.jpg"
                                class="card-img-top"
                                alt="Deluxe Room"
                                loading="lazy"
                                />
                            <div class="card-body">
                                <h5 class="card-title">Deluxe Room</h5>
                                <p class="card-text">
                                    Spacious room with additional amenities and city views.
                                </p>
                                <p class="price">$149 / night</p>
                                <a href="rooms" class="btn btn-outline-primary">View Details</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4 mb-4">
                        <div class="card h-100">
                            <img
                                src="images/room03.jpg"
                                class="card-img-top"
                                alt="Luxury Suite"
                                loading="lazy"
                                />
                            <div class="card-body">
                                <h5 class="card-title">Luxury Suite</h5>
                                <p class="card-text">
                                    Premium suite with separate living area and panoramic views.
                                </p>
                                <p class="price">$249 / night</p>
                                <a href="rooms" class="btn btn-outline-primary">View Details</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Services Section -->
        <section class="services py-5 bg-light">
            <div class="container">
                <h2 class="text-center mb-4">Our Services</h2>
                <div class="row">
                    <div class="col-md-3 mb-4 text-center">
                        <div class="service-item">
                            <i class="fas fa-utensils fa-3x mb-3"></i>
                            <h5>Restaurant</h5>
                            <p>Enjoy delicious meals at our in-house restaurant.</p>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4 text-center">
                        <div class="service-item">
                            <i class="fas fa-swimming-pool fa-3x mb-3"></i>
                            <h5>Swimming Pool</h5>
                            <p>Relax and refresh in our swimming pool.</p>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4 text-center">
                        <div class="service-item">
                            <i class="fas fa-spa fa-3x mb-3"></i>
                            <h5>Spa & Wellness</h5>
                            <p>Pamper yourself with our spa treatments.</p>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4 text-center">
                        <div class="service-item">
                            <i class="fas fa-dumbbell fa-3x mb-3"></i>
                            <h5>Fitness Center</h5>
                            <p>Stay fit during your stay at our fitness center.</p>
                        </div>
                    </div>
                </div>
                <div class="text-center mt-3">
                    <a href="services" class="btn btn-primary">View All Services</a>
                </div>
            </div>
        </section>

        <!-- Footer -->
        <jsp:include page="components/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/scripts.js"></script>
    </body>
</html>
