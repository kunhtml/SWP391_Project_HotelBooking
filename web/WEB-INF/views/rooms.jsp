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
    <title>Rooms - Hotel Booking System</title>
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

    <!-- Page Header -->
    <section class="page-header bg-light py-5">
      <div class="container">
        <div class="row">
          <div class="col-12">
            <h1>Our Rooms</h1>
            <nav aria-label="breadcrumb">
              <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="home">Home</a></li>
                <li class="breadcrumb-item active" aria-current="page">
                  Rooms
                </li>
              </ol>
            </nav>
          </div>
        </div>
      </div>
    </section>

    <!-- Rooms Section -->
    <section class="rooms-section py-5">
      <div class="container">
        <!-- Search Form -->
        <div class="card mb-4">
          <div class="card-body">
            <form action="search" method="get" class="row g-3">
              <div class="col-md-3">
                <label for="checkIn" class="form-label">Check In</label>
                <input
                  type="date"
                  class="form-control"
                  id="checkIn"
                  name="checkIn"
                  required
                />
              </div>
              <div class="col-md-3">
                <label for="checkOut" class="form-label">Check Out</label>
                <input
                  type="date"
                  class="form-control"
                  id="checkOut"
                  name="checkOut"
                  required
                />
              </div>
              <div class="col-md-2">
                <label for="adults" class="form-label">Adults</label>
                <select class="form-select" id="adults" name="adults">
                  <option value="1">1</option>
                  <option value="2" selected>2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
                </select>
              </div>
              <div class="col-md-2">
                <label for="children" class="form-label">Children</label>
                <select class="form-select" id="children" name="children">
                  <option value="0" selected>0</option>
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                </select>
              </div>
              <div class="col-md-2">
                <label for="roomCount" class="form-label">Rooms</label>
                <select class="form-select" id="roomCount" name="roomCount">
                  <option value="1" selected>1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                </select>
              </div>
              <div class="col-12">
                <button type="submit" class="btn btn-primary">
                  Search Availability
                </button>
              </div>
            </form>
          </div>
        </div>

        <!-- Room Types -->
        <div class="row">
          <!-- Standard Room -->
          <div class="col-md-6 mb-4">
            <div class="card room-card h-100">
              <div class="row g-0">
                <div class="col-md-5">
                  <img
                    src="images/room01.jpg"
                    class="img-fluid rounded-start h-100"
                    alt="Standard Room"
                    loading="lazy"
                  />
                </div>
                <div class="col-md-7">
                  <div class="card-body d-flex flex-column h-100">
                    <h5 class="card-title">Standard Room</h5>
                    <div class="room-features mb-2">
                      <span class="badge bg-light text-dark me-1"
                        ><i class="fas fa-user me-1"></i> 2 Guests</span
                      >
                      <span class="badge bg-light text-dark me-1"
                        ><i class="fas fa-bed me-1"></i> 1 Bed</span
                      >
                      <span class="badge bg-light text-dark"
                        ><i class="fas fa-ruler-combined me-1"></i> 25 m²</span
                      >
                    </div>
                    <p class="card-text">
                      Comfortable room with all the essentials for a pleasant
                      stay.
                    </p>
                    <ul class="list-unstyled">
                      <li><i class="fas fa-wifi me-2"></i> Free WiFi</li>
                      <li><i class="fas fa-tv me-2"></i> Flat-screen TV</li>
                      <li>
                        <i class="fas fa-snowflake me-2"></i> Air conditioning
                      </li>
                      <li>
                        <i class="fas fa-shower me-2"></i> Private bathroom
                      </li>
                    </ul>
                    <div class="mt-auto">
                      <div
                        class="d-flex justify-content-between align-items-center"
                      >
                        <div class="price">
                          <span class="fs-4 fw-bold">$99</span> / night
                        </div>
                        <a
                          href="search?roomType=standard"
                          class="btn btn-primary"
                          >Book Now</a
                        >
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Deluxe Room -->
          <div class="col-md-6 mb-4">
            <div class="card room-card h-100">
              <div class="row g-0">
                <div class="col-md-5">
                  <img
                    src="images/room02.jpg"
                    class="img-fluid rounded-start h-100"
                    alt="Deluxe Room"
                    loading="lazy"
                  />
                </div>
                <div class="col-md-7">
                  <div class="card-body d-flex flex-column h-100">
                    <h5 class="card-title">Deluxe Room</h5>
                    <div class="room-features mb-2">
                      <span class="badge bg-light text-dark me-1"
                        ><i class="fas fa-user me-1"></i> 2 Guests</span
                      >
                      <span class="badge bg-light text-dark me-1"
                        ><i class="fas fa-bed me-1"></i> 1 King Bed</span
                      >
                      <span class="badge bg-light text-dark"
                        ><i class="fas fa-ruler-combined me-1"></i> 35 m²</span
                      >
                    </div>
                    <p class="card-text">
                      Spacious room with additional amenities and city views.
                    </p>
                    <ul class="list-unstyled">
                      <li><i class="fas fa-wifi me-2"></i> Free WiFi</li>
                      <li><i class="fas fa-tv me-2"></i> 42" Flat-screen TV</li>
                      <li>
                        <i class="fas fa-snowflake me-2"></i> Air conditioning
                      </li>
                      <li><i class="fas fa-bath me-2"></i> Bathtub</li>
                      <li><i class="fas fa-coffee me-2"></i> Coffee machine</li>
                    </ul>
                    <div class="mt-auto">
                      <div
                        class="d-flex justify-content-between align-items-center"
                      >
                        <div class="price">
                          <span class="fs-4 fw-bold">$149</span> / night
                        </div>
                        <a href="search?roomType=deluxe" class="btn btn-primary"
                          >Book Now</a
                        >
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Luxury Suite -->
          <div class="col-md-6 mb-4">
            <div class="card room-card h-100">
              <div class="row g-0">
                <div class="col-md-5">
                  <img
                    src="images/room03.jpg"
                    class="img-fluid rounded-start h-100"
                    alt="Luxury Suite"
                    loading="lazy"
                  />
                </div>
                <div class="col-md-7">
                  <div class="card-body d-flex flex-column h-100">
                    <h5 class="card-title">Luxury Suite</h5>
                    <div class="room-features mb-2">
                      <span class="badge bg-light text-dark me-1"
                        ><i class="fas fa-user me-1"></i> 4 Guests</span
                      >
                      <span class="badge bg-light text-dark me-1"
                        ><i class="fas fa-bed me-1"></i> 1 King Bed + Sofa
                        Bed</span
                      >
                      <span class="badge bg-light text-dark"
                        ><i class="fas fa-ruler-combined me-1"></i> 50 m²</span
                      >
                    </div>
                    <p class="card-text">
                      Premium suite with separate living area and panoramic
                      views.
                    </p>
                    <ul class="list-unstyled">
                      <li><i class="fas fa-wifi me-2"></i> Free WiFi</li>
                      <li><i class="fas fa-tv me-2"></i> 50" Smart TV</li>
                      <li>
                        <i class="fas fa-snowflake me-2"></i> Climate control
                      </li>
                      <li><i class="fas fa-bath me-2"></i> Luxury bathroom</li>
                      <li>
                        <i class="fas fa-couch me-2"></i> Separate living area
                      </li>
                      <li><i class="fas fa-utensils me-2"></i> Minibar</li>
                    </ul>
                    <div class="mt-auto">
                      <div
                        class="d-flex justify-content-between align-items-center"
                      >
                        <div class="price">
                          <span class="fs-4 fw-bold">$249</span> / night
                        </div>
                        <a href="search?roomType=suite" class="btn btn-primary"
                          >Book Now</a
                        >
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Family Room -->
          <div class="col-md-6 mb-4">
            <div class="card room-card h-100">
              <div class="row g-0">
                <div class="col-md-5">
                  <img
                    src="images/room01.jpg"
                    class="img-fluid rounded-start h-100"
                    alt="Family Room"
                    loading="lazy"
                  />
                </div>
                <div class="col-md-7">
                  <div class="card-body d-flex flex-column h-100">
                    <h5 class="card-title">Family Room</h5>
                    <div class="room-features mb-2">
                      <span class="badge bg-light text-dark me-1"
                        ><i class="fas fa-user me-1"></i> 4 Guests</span
                      >
                      <span class="badge bg-light text-dark me-1"
                        ><i class="fas fa-bed me-1"></i> 2 Queen Beds</span
                      >
                      <span class="badge bg-light text-dark"
                        ><i class="fas fa-ruler-combined me-1"></i> 40 m²</span
                      >
                    </div>
                    <p class="card-text">
                      Spacious room designed for families with children.
                    </p>
                    <ul class="list-unstyled">
                      <li><i class="fas fa-wifi me-2"></i> Free WiFi</li>
                      <li><i class="fas fa-tv me-2"></i> 42" Flat-screen TV</li>
                      <li>
                        <i class="fas fa-snowflake me-2"></i> Air conditioning
                      </li>
                      <li>
                        <i class="fas fa-shower me-2"></i> Private bathroom
                      </li>
                      <li>
                        <i class="fas fa-baby me-2"></i> Child-friendly
                        amenities
                      </li>
                      <li>
                        <i class="fas fa-gamepad me-2"></i> Game console
                        available
                      </li>
                    </ul>
                    <div class="mt-auto">
                      <div
                        class="d-flex justify-content-between align-items-center"
                      >
                        <div class="price">
                          <span class="fs-4 fw-bold">$179</span> / night
                        </div>
                        <a href="search?roomType=family" class="btn btn-primary"
                          >Book Now</a
                        >
                      </div>
                    </div>
                  </div>
                </div>
              </div>
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
