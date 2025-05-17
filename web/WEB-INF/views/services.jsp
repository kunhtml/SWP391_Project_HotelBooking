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
    <title>Services - Hotel Booking System</title>
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
            <h1>Our Services</h1>
            <nav aria-label="breadcrumb">
              <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="home">Home</a></li>
                <li class="breadcrumb-item active" aria-current="page">
                  Services
                </li>
              </ol>
            </nav>
          </div>
        </div>
      </div>
    </section>

    <!-- Services Section -->
    <section class="services-section py-5">
      <div class="container">
        <div class="row">
          <div class="col-lg-4 mb-4">
            <div class="card h-100 service-card">
              <img
                src="images/room01.jpg"
                class="card-img-top"
                alt="Restaurant"
              />
              <div class="card-body">
                <h5 class="card-title">
                  <i class="fas fa-utensils me-2"></i>Restaurant
                </h5>
                <p class="card-text">
                  Our in-house restaurant offers a diverse menu featuring both
                  local and international cuisine prepared by our expert chefs
                  using the freshest ingredients.
                </p>
                <ul class="list-unstyled">
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i
                    >Breakfast: 6:30 AM - 10:30 AM
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Lunch:
                    12:00 PM - 2:30 PM
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Dinner:
                    6:30 PM - 10:30 PM
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Room
                    service available
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Special
                    dietary options
                  </li>
                </ul>
              </div>
            </div>
          </div>

          <div class="col-lg-4 mb-4">
            <div class="card h-100 service-card">
              <img
                src="images/room02.jpg"
                class="card-img-top"
                alt="Swimming Pool"
              />
              <div class="card-body">
                <h5 class="card-title">
                  <i class="fas fa-swimming-pool me-2"></i>Swimming Pool
                </h5>
                <p class="card-text">
                  Take a refreshing dip in our swimming pool or relax on the sun
                  loungers while enjoying a drink from the poolside bar.
                </p>
                <ul class="list-unstyled">
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Open
                    daily: 7:00 AM - 9:00 PM
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Heated
                    pool
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Towels
                    provided
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i
                    >Poolside bar service
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i
                    >Children's pool area
                  </li>
                </ul>
              </div>
            </div>
          </div>

          <div class="col-lg-4 mb-4">
            <div class="card h-100 service-card">
              <img
                src="images/room03.jpg"
                class="card-img-top"
                alt="Spa & Wellness"
              />
              <div class="card-body">
                <h5 class="card-title">
                  <i class="fas fa-spa me-2"></i>Spa & Wellness
                </h5>
                <p class="card-text">
                  Indulge in a range of spa treatments designed to relax and
                  rejuvenate your body and mind in our tranquil spa center.
                </p>
                <ul class="list-unstyled">
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Open
                    daily: 10:00 AM - 8:00 PM
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Massage
                    therapy
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Facial
                    treatments
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Body
                    scrubs and wraps
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Sauna
                    and steam room
                  </li>
                </ul>
              </div>
            </div>
          </div>

          <div class="col-lg-4 mb-4">
            <div class="card h-100 service-card">
              <img
                src="images/room01.jpg"
                class="card-img-top"
                alt="Fitness Center"
              />
              <div class="card-body">
                <h5 class="card-title">
                  <i class="fas fa-dumbbell me-2"></i>Fitness Center
                </h5>
                <p class="card-text">
                  Stay fit during your stay with our state-of-the-art fitness
                  center equipped with modern cardio and strength training
                  equipment.
                </p>
                <ul class="list-unstyled">
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Open
                    24/7
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Cardio
                    equipment
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Weight
                    training area
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Yoga
                    mats and exercise balls
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i
                    >Personal training available
                  </li>
                </ul>
              </div>
            </div>
          </div>

          <div class="col-lg-4 mb-4">
            <div class="card h-100 service-card">
              <img
                src="images/room02.jpg"
                class="card-img-top"
                alt="Business Center"
              />
              <div class="card-body">
                <h5 class="card-title">
                  <i class="fas fa-briefcase me-2"></i>Business Center
                </h5>
                <p class="card-text">
                  Our business center provides all the facilities you need to
                  stay productive during your stay, including meeting rooms and
                  office equipment.
                </p>
                <ul class="list-unstyled">
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Open
                    daily: 7:00 AM - 10:00 PM
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i
                    >Computer workstations
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i
                    >Printing and scanning services
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Meeting
                    rooms for rent
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i
                    >High-speed internet
                  </li>
                </ul>
              </div>
            </div>
          </div>

          <div class="col-lg-4 mb-4">
            <div class="card h-100 service-card">
              <img
                src="images/room03.jpg"
                class="card-img-top"
                alt="Concierge Service"
              />
              <div class="card-body">
                <h5 class="card-title">
                  <i class="fas fa-concierge-bell me-2"></i>Concierge Service
                </h5>
                <p class="card-text">
                  Our dedicated concierge team is available to assist with
                  restaurant reservations, tour bookings, transportation
                  arrangements, and more.
                </p>
                <ul class="list-unstyled">
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i
                    >Available 24/7
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Local
                    recommendations
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Ticket
                    bookings
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i
                    >Transportation arrangements
                  </li>
                  <li>
                    <i class="fas fa-check-circle me-2 text-success"></i>Special
                    requests
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Additional Services -->
    <section class="additional-services py-5 bg-light">
      <div class="container">
        <h2 class="text-center mb-4">Additional Services</h2>
        <div class="row">
          <div class="col-md-6">
            <div class="card mb-4">
              <div class="card-body">
                <h5 class="card-title">
                  <i class="fas fa-car me-2"></i>Airport Transfer
                </h5>
                <p class="card-text">
                  We offer convenient airport transfer services to ensure a
                  smooth start and end to your stay with us.
                </p>
                <p><strong>Price:</strong> From $30 one-way</p>
                <a href="#" class="btn btn-outline-primary">Book Transfer</a>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="card mb-4">
              <div class="card-body">
                <h5 class="card-title">
                  <i class="fas fa-bicycle me-2"></i>Bicycle Rental
                </h5>
                <p class="card-text">
                  Explore the surrounding area at your own pace with our bicycle
                  rental service.
                </p>
                <p><strong>Price:</strong> $10 per day</p>
                <a href="#" class="btn btn-outline-primary">Rent a Bicycle</a>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="card mb-4">
              <div class="card-body">
                <h5 class="card-title">
                  <i class="fas fa-baby me-2"></i>Babysitting
                </h5>
                <p class="card-text">
                  Enjoy some adult time while our professional babysitters take
                  care of your children.
                </p>
                <p><strong>Price:</strong> $15 per hour</p>
                <a href="#" class="btn btn-outline-primary"
                  >Request Babysitting</a
                >
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="card mb-4">
              <div class="card-body">
                <h5 class="card-title">
                  <i class="fas fa-globe me-2"></i>Guided Tours
                </h5>
                <p class="card-text">
                  Discover the local attractions with our experienced guides who
                  know all the best spots.
                </p>
                <p><strong>Price:</strong> From $45 per person</p>
                <a href="#" class="btn btn-outline-primary">Book a Tour</a>
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
