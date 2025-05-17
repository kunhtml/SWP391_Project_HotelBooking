<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <% // Ensure proper encoding for JSP
response.setCharacterEncoding("UTF-8"); request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Contact Us - Luxury Hotel</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    />
    <link rel="stylesheet" href="css/styles.css" />
    <style>
      .contact-info-item {
        margin-bottom: 1.5rem;
      }
      .contact-info-item i {
        font-size: 1.5rem;
        color: #0d6efd;
        margin-right: 1rem;
        width: 2rem;
        text-align: center;
      }
      .map-container {
        height: 400px;
        width: 100%;
        margin-bottom: 2rem;
      }
      .contact-form .form-control {
        margin-bottom: 1rem;
      }
    </style>
  </head>
  <body>
    <jsp:include page="components/header.jsp" />

    <div class="container py-5">
      <div class="row mb-4">
        <div class="col-12 text-center">
          <h1 class="display-4 mb-3">Contact Us</h1>
          <p class="lead">
            We'd love to hear from you. Please feel free to get in touch with
            us.
          </p>
        </div>
      </div>

      <div class="row">
        <div class="col-lg-6 mb-4">
          <div class="card shadow-sm h-100">
            <div class="card-body">
              <h3 class="card-title mb-4">Contact Information</h3>

              <div class="contact-info-item d-flex align-items-center">
                <i class="fas fa-map-marker-alt"></i>
                <div>
                  <h5 class="mb-0">Address</h5>
                  <p class="mb-0">${contactSettings.contact_address_vi}</p>
                </div>
              </div>

              <div class="contact-info-item d-flex align-items-center">
                <i class="fas fa-phone"></i>
                <div>
                  <h5 class="mb-0">Phone</h5>
                  <p class="mb-0">${contactSettings.contact_phone}</p>
                </div>
              </div>

              <div class="contact-info-item d-flex align-items-center">
                <i class="fas fa-envelope"></i>
                <div>
                  <h5 class="mb-0">Email</h5>
                  <p class="mb-0">${contactSettings.contact_email}</p>
                </div>
              </div>

              <div class="contact-info-item d-flex align-items-center">
                <i class="fas fa-clock"></i>
                <div>
                  <h5 class="mb-0">Working Hours</h5>
                  <p class="mb-0">Monday - Sunday: 24/7</p>
                  <p class="mb-0">Reception is always open</p>
                </div>
              </div>

              <div class="mt-4">
                <h5>Follow Us</h5>
                <div class="social-icons">
                  <c:if test="${not empty contactSettings.social_facebook}">
                    <a
                      href="${contactSettings.social_facebook}"
                      class="me-3"
                      target="_blank"
                    >
                      <i class="fab fa-facebook-f fa-lg"></i>
                    </a>
                  </c:if>
                  <c:if test="${not empty contactSettings.social_twitter}">
                    <a
                      href="${contactSettings.social_twitter}"
                      class="me-3"
                      target="_blank"
                    >
                      <i class="fab fa-twitter fa-lg"></i>
                    </a>
                  </c:if>
                  <c:if test="${not empty contactSettings.social_instagram}">
                    <a
                      href="${contactSettings.social_instagram}"
                      class="me-3"
                      target="_blank"
                    >
                      <i class="fab fa-instagram fa-lg"></i>
                    </a>
                  </c:if>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-lg-6 mb-4">
          <div class="card shadow-sm h-100">
            <div class="card-body">
              <h3 class="card-title mb-4">Send Us a Message</h3>

              <c:if test="${not empty sessionScope.successMessage}">
                <div
                  class="alert alert-success alert-dismissible fade show"
                  role="alert"
                >
                  ${sessionScope.successMessage}
                  <button
                    type="button"
                    class="btn-close"
                    data-bs-dismiss="alert"
                    aria-label="Close"
                  ></button>
                </div>
                <% session.removeAttribute("successMessage"); %>
              </c:if>

              <form class="contact-form" action="contact" method="post">
                <div class="mb-3">
                  <label for="name" class="form-label">Your Name</label>
                  <input
                    type="text"
                    class="form-control"
                    id="name"
                    name="name"
                    required
                  />
                </div>

                <div class="mb-3">
                  <label for="email" class="form-label">Your Email</label>
                  <input
                    type="email"
                    class="form-control"
                    id="email"
                    name="email"
                    required
                  />
                </div>

                <div class="mb-3">
                  <label for="subject" class="form-label">Subject</label>
                  <input
                    type="text"
                    class="form-control"
                    id="subject"
                    name="subject"
                    required
                  />
                </div>

                <div class="mb-3">
                  <label for="message" class="form-label">Message</label>
                  <textarea
                    class="form-control"
                    id="message"
                    name="message"
                    rows="5"
                    required
                  ></textarea>
                </div>

                <button type="submit" class="btn btn-primary">
                  Send Message
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>

      <div class="row mt-4">
        <div class="col-12">
          <div class="card shadow-sm">
            <div class="card-body p-0">
              <h3 class="card-title p-4 mb-0">Our Location</h3>
              <div class="map-container">
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3724.506341458941!2d105.52052559678953!3d21.012416700000003!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135abc60e7d3f19%3A0x2be9d7d0b5abcbf4!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBGUFQgSMOgIE7hu5lp!5e0!3m2!1svi!2s!4v1747508463526!5m2!1svi!2s" width="1100" height="399" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <jsp:include page="components/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
