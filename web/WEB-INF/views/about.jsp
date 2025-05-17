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
    <title>About Us - Hotel Booking System</title>
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
            <h1>About Us</h1>
            <nav aria-label="breadcrumb">
              <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="home">Home</a></li>
                <li class="breadcrumb-item active" aria-current="page">
                  About Us
                </li>
              </ol>
            </nav>
          </div>
        </div>
      </div>
    </section>

    <!-- About Section -->
    <section class="about-section py-5">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-lg-6 mb-4 mb-lg-0">
            <img
              src="images/banner.jpg"
              alt="Hotel Exterior"
              class="img-fluid rounded"
              loading="lazy"
            />
          </div>
          <div class="col-lg-6">
            <h2>Welcome to Our Hotel</h2>
            <p class="lead">A Luxury Experience Like No Other</p>
            <p>
              Founded in 2010, our hotel has been providing exceptional
              hospitality services to guests from around the world. We pride
              ourselves on our attention to detail, personalized service, and
              commitment to making every stay memorable.
            </p>
            <p>
              Our hotel is ideally located in the heart of the city, offering
              easy access to major attractions, shopping centers, and business
              districts. Whether you're traveling for business or leisure, our
              dedicated team is here to ensure your stay exceeds expectations.
            </p>
            <div class="row mt-4">
              <div class="col-md-6">
                <div class="d-flex align-items-center mb-3">
                  <i class="fas fa-hotel fa-2x text-primary me-3"></i>
                  <div>
                    <h5 class="mb-0">Luxury Accommodations</h5>
                    <p class="mb-0 text-muted">100+ Rooms & Suites</p>
                  </div>
                </div>
              </div>
              <div class="col-md-6">
                <div class="d-flex align-items-center mb-3">
                  <i class="fas fa-utensils fa-2x text-primary me-3"></i>
                  <div>
                    <h5 class="mb-0">Fine Dining</h5>
                    <p class="mb-0 text-muted">3 Restaurants & Bars</p>
                  </div>
                </div>
              </div>
              <div class="col-md-6">
                <div class="d-flex align-items-center mb-3">
                  <i class="fas fa-spa fa-2x text-primary me-3"></i>
                  <div>
                    <h5 class="mb-0">Wellness Center</h5>
                    <p class="mb-0 text-muted">Spa, Pool & Fitness</p>
                  </div>
                </div>
              </div>
              <div class="col-md-6">
                <div class="d-flex align-items-center mb-3">
                  <i class="fas fa-concierge-bell fa-2x text-primary me-3"></i>
                  <div>
                    <h5 class="mb-0">24/7 Service</h5>
                    <p class="mb-0 text-muted">Always at Your Service</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Our Mission -->
    <section class="mission-section py-5 bg-light">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-8 text-center">
            <h2 class="mb-4">Our Mission</h2>
            <p class="lead">
              To provide exceptional hospitality experiences that exceed our
              guests' expectations through personalized service, luxurious
              accommodations, and attention to every detail.
            </p>
            <div class="row mt-5">
              <div class="col-md-4 mb-4">
                <div class="mission-item">
                  <i class="fas fa-heart fa-3x text-primary mb-3"></i>
                  <h4>Passion</h4>
                  <p>
                    We are passionate about hospitality and dedicated to
                    creating memorable experiences for our guests.
                  </p>
                </div>
              </div>
              <div class="col-md-4 mb-4">
                <div class="mission-item">
                  <i class="fas fa-gem fa-3x text-primary mb-3"></i>
                  <h4>Excellence</h4>
                  <p>
                    We strive for excellence in everything we do, from service
                    to amenities to cleanliness.
                  </p>
                </div>
              </div>
              <div class="col-md-4 mb-4">
                <div class="mission-item">
                  <i class="fas fa-handshake fa-3x text-primary mb-3"></i>
                  <h4>Integrity</h4>
                  <p>
                    We operate with integrity, honesty, and transparency in all
                    our interactions.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Our Team -->
    <section class="team-section py-5">
      <div class="container">
        <h2 class="text-center mb-5">Meet Our Team</h2>
        <div class="row">
          <div class="col-lg-3 col-md-6 mb-4">
            <div class="card team-card h-100">
              <img
                src="images/room01.jpg"
                class="card-img-top"
                alt="Team Member"
                loading="lazy"
              />
              <div class="card-body text-center">
                <h5 class="card-title">John Smith</h5>
                <p class="text-muted">General Manager</p>
                <p class="card-text">
                  With over 20 years of experience in the hospitality industry,
                  John leads our team with passion and dedication.
                </p>
                <div class="social-icons">
                  <a href="#" class="text-dark me-2"
                    ><i class="fab fa-linkedin"></i
                  ></a>
                  <a href="#" class="text-dark me-2"
                    ><i class="fab fa-twitter"></i
                  ></a>
                  <a href="#" class="text-dark"
                    ><i class="fas fa-envelope"></i
                  ></a>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-3 col-md-6 mb-4">
            <div class="card team-card h-100">
              <img
                src="images/room02.jpg"
                class="card-img-top"
                alt="Team Member"
                loading="lazy"
              />
              <div class="card-body text-center">
                <h5 class="card-title">Sarah Johnson</h5>
                <p class="text-muted">Front Office Manager</p>
                <p class="card-text">
                  Sarah ensures that every guest receives a warm welcome and
                  exceptional service throughout their stay.
                </p>
                <div class="social-icons">
                  <a href="#" class="text-dark me-2"
                    ><i class="fab fa-linkedin"></i
                  ></a>
                  <a href="#" class="text-dark me-2"
                    ><i class="fab fa-twitter"></i
                  ></a>
                  <a href="#" class="text-dark"
                    ><i class="fas fa-envelope"></i
                  ></a>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-3 col-md-6 mb-4">
            <div class="card team-card h-100">
              <img
                src="images/room03.jpg"
                class="card-img-top"
                alt="Team Member"
                loading="lazy"
              />
              <div class="card-body text-center">
                <h5 class="card-title">Michael Chen</h5>
                <p class="text-muted">Executive Chef</p>
                <p class="card-text">
                  Michael brings culinary excellence to our restaurants with his
                  innovative approach to fine dining.
                </p>
                <div class="social-icons">
                  <a href="#" class="text-dark me-2"
                    ><i class="fab fa-linkedin"></i
                  ></a>
                  <a href="#" class="text-dark me-2"
                    ><i class="fab fa-twitter"></i
                  ></a>
                  <a href="#" class="text-dark"
                    ><i class="fas fa-envelope"></i
                  ></a>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-3 col-md-6 mb-4">
            <div class="card team-card h-100">
              <img
                src="images/room01.jpg"
                class="card-img-top"
                alt="Team Member"
                loading="lazy"
              />
              <div class="card-body text-center">
                <h5 class="card-title">Emily Rodriguez</h5>
                <p class="text-muted">Spa Director</p>
                <p class="card-text">
                  Emily creates rejuvenating experiences that help our guests
                  relax and unwind during their stay.
                </p>
                <div class="social-icons">
                  <a href="#" class="text-dark me-2"
                    ><i class="fab fa-linkedin"></i
                  ></a>
                  <a href="#" class="text-dark me-2"
                    ><i class="fab fa-twitter"></i
                  ></a>
                  <a href="#" class="text-dark"
                    ><i class="fas fa-envelope"></i
                  ></a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Testimonials -->
    <section class="testimonials-section py-5 bg-light">
      <div class="container">
        <h2 class="text-center mb-5">What Our Guests Say</h2>
        <div class="row">
          <div class="col-md-4 mb-4">
            <div class="card h-100">
              <div class="card-body">
                <div class="mb-3">
                  <i class="fas fa-star text-warning"></i>
                  <i class="fas fa-star text-warning"></i>
                  <i class="fas fa-star text-warning"></i>
                  <i class="fas fa-star text-warning"></i>
                  <i class="fas fa-star text-warning"></i>
                </div>
                <p class="card-text">
                  "Exceptional service from start to finish. The staff went
                  above and beyond to make our stay memorable. The rooms are
                  luxurious and the food is outstanding."
                </p>
                <div class="d-flex align-items-center mt-3">
                  <img
                    src="images/room01.jpg"
                    alt="Guest"
                    class="rounded-circle me-3"
                    width="50"
                    height="50"
                    loading="lazy"
                  />
                  <div>
                    <h6 class="mb-0">Robert Williams</h6>
                    <small class="text-muted">New York, USA</small>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-4 mb-4">
            <div class="card h-100">
              <div class="card-body">
                <div class="mb-3">
                  <i class="fas fa-star text-warning"></i>
                  <i class="fas fa-star text-warning"></i>
                  <i class="fas fa-star text-warning"></i>
                  <i class="fas fa-star text-warning"></i>
                  <i class="fas fa-star text-warning"></i>
                </div>
                <p class="card-text">
                  "Perfect location with stunning views. The spa treatments were
                  amazing and the staff was incredibly attentive. Will
                  definitely return on our next trip!"
                </p>
                <div class="d-flex align-items-center mt-3">
                  <img
                    src="images/room02.jpg"
                    alt="Guest"
                    class="rounded-circle me-3"
                    width="50"
                    height="50"
                    loading="lazy"
                  />
                  <div>
                    <h6 class="mb-0">Emma Thompson</h6>
                    <small class="text-muted">London, UK</small>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-4 mb-4">
            <div class="card h-100">
              <div class="card-body">
                <div class="mb-3">
                  <i class="fas fa-star text-warning"></i>
                  <i class="fas fa-star text-warning"></i>
                  <i class="fas fa-star text-warning"></i>
                  <i class="fas fa-star text-warning"></i>
                  <i class="fas fa-star text-warning"></i>
                </div>
                <p class="card-text">
                  "We stayed for our anniversary and the hotel made it truly
                  special. From the champagne welcome to the romantic dinner,
                  every detail was perfect."
                </p>
                <div class="d-flex align-items-center mt-3">
                  <img
                    src="images/room03.jpg"
                    alt="Guest"
                    class="rounded-circle me-3"
                    width="50"
                  />
                  <div>
                    <h6 class="mb-0">David & Lisa Kim</h6>
                    <small class="text-muted">Seoul, South Korea</small>
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
