<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Register - Hotel Booking System</title>
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

    <!-- Register Section -->
    <section class="register-section py-5">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-md-8">
            <div class="card shadow">
              <div class="card-body p-5">
                <h2 class="text-center mb-4">Create an Account</h2>

                <!-- Error Message -->
                <c:if test="${not empty error}">
                  <div
                    class="alert alert-danger alert-dismissible fade show"
                    role="alert"
                  >
                    ${error}
                    <button
                      type="button"
                      class="btn-close"
                      data-bs-dismiss="alert"
                      aria-label="Close"
                    ></button>
                  </div>
                </c:if>

                <!-- Registration Form -->
                <form action="register" method="post" id="registrationForm">
                  <div class="row">
                    <div class="col-md-6 mb-3">
                      <label for="fullName" class="form-label">Full Name</label>
                      <input
                        type="text"
                        class="form-control"
                        id="fullName"
                        name="fullName"
                        value="${fullName}"
                        required
                      />
                    </div>
                    <div class="col-md-6 mb-3">
                      <label for="email" class="form-label">Email</label>
                      <input
                        type="email"
                        class="form-control"
                        id="email"
                        name="email"
                        value="${email}"
                        required
                      />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-6 mb-3">
                      <label for="password" class="form-label">Password</label>
                      <input
                        type="password"
                        class="form-control"
                        id="password"
                        name="password"
                        required
                      />
                      <div class="form-text">
                        Password must be at least 8 characters long and include
                        letters and numbers.
                      </div>
                    </div>
                    <div class="col-md-6 mb-3">
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
                  <div class="row">
                    <div class="col-md-6 mb-3">
                      <label for="phoneNumber" class="form-label"
                        >Phone Number</label
                      >
                      <input
                        type="tel"
                        class="form-control"
                        id="phoneNumber"
                        name="phoneNumber"
                        placeholder="Enter your phone number"
                        required
                      />
                    </div>
                    <div class="col-md-6 mb-3">
                      <label class="form-label">Gender</label>
                      <div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="radio"
                          name="gender" id="male" value="male" ${gender ==
                          'male' ? 'checked' : ''}>
                          <label class="form-check-label" for="male"
                            >Male</label
                          >
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="radio"
                          name="gender" id="female" value="female" ${gender ==
                          'female' ? 'checked' : ''}>
                          <label class="form-check-label" for="female"
                            >Female</label
                          >
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="radio"
                          name="gender" id="other" value="other" ${gender ==
                          'other' ? 'checked' : ''}>
                          <label class="form-check-label" for="other"
                            >Other</label
                          >
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="mb-3 form-check">
                    <input
                      type="checkbox"
                      class="form-check-input"
                      id="termsAgreement"
                      name="termsAgreement"
                      required
                    />
                    <label class="form-check-label" for="termsAgreement"
                      >I agree to the <a href="#">Terms and Conditions</a> and
                      <a href="#">Privacy Policy</a></label
                    >
                  </div>
                  <div class="d-grid">
                    <button type="submit" class="btn btn-primary">
                      Register
                    </button>
                  </div>
                </form>

                <div class="mt-3 text-center">
                  <p>Already have an account? <a href="login">Login here</a></p>
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
    <script>
      // Form validation
      document
        .getElementById("registrationForm")
        .addEventListener("submit", function (event) {
          const password = document.getElementById("password").value;
          const confirmPassword =
            document.getElementById("confirmPassword").value;

          if (password !== confirmPassword) {
            event.preventDefault();
            alert("Passwords do not match!");
          }

          if (password.length < 8) {
            event.preventDefault();
            alert("Password must be at least 8 characters long!");
          }
        });
    </script>
  </body>
</html>
