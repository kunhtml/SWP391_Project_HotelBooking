<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login - Hotel Booking System</title>
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

    <!-- Login Section -->
    <section class="login-section py-5">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-md-6">
            <div class="card shadow">
              <div class="card-body p-5">
                <h2 class="text-center mb-4">Login</h2>

                <!-- Success Message -->
                <c:if test="${not empty sessionScope.registrationSuccess}">
                  <div
                    class="alert alert-success alert-dismissible fade show"
                    role="alert"
                  >
                    ${sessionScope.registrationSuccess}
                    <button
                      type="button"
                      class="btn-close"
                      data-bs-dismiss="alert"
                      aria-label="Close"
                    ></button>
                  </div>
                  <c:remove var="registrationSuccess" scope="session" />
                </c:if>

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

                <!-- Login Form -->
                <form action="login" method="post">
                  <div class="mb-3">
                    <label for="username" class="form-label"
                      >Email or Phone Number</label
                    >
                    <input
                      type="text"
                      class="form-control"
                      id="username"
                      name="username"
                      placeholder="Enter your email or phone number"
                      required
                    />
                  </div>
                  <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input
                      type="password"
                      class="form-control"
                      id="password"
                      name="password"
                      required
                    />
                  </div>
                  <div class="mb-3 form-check">
                    <input
                      type="checkbox"
                      class="form-check-input"
                      id="rememberMe"
                      name="rememberMe"
                    />
                    <label class="form-check-label" for="rememberMe"
                      >Remember me</label
                    >
                  </div>
                  <div class="d-grid">
                    <button type="submit" class="btn btn-primary">Login</button>
                  </div>
                </form>

                <div class="mt-3 text-center">
                  <p>
                    Don't have an account? <a href="register">Register here</a>
                  </p>
                  <p><a href="#">Forgot password?</a></p>
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
