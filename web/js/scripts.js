// Wait for the DOM to be fully loaded
document.addEventListener("DOMContentLoaded", function () {
  // Handle images for consistent display
  handleImages();

  // Initialize date pickers with default values
  initializeDatePickers();

  // Initialize form validation
  initializeFormValidation();
});

/**
 * Handle images for consistent display
 */
function handleImages() {
  const images = document.querySelectorAll("img");

  images.forEach((img) => {
    // Handle image loading errors
    img.onerror = function () {
      // Get image dimensions
      const width = this.width || 300;
      const height = this.height || 200;

      // Get image alt text or use a default
      const altText = this.alt || "Image";

      // Replace with local default image
      if (
        this.classList.contains("rounded-circle") ||
        altText.includes("Profile")
      ) {
        // For profile images
        this.src = `${window.location.pathname.substring(
          0,
          window.location.pathname.indexOf("/", 1)
        )}/img/default-avatar.svg`;
      } else {
        // For other images
        this.src = `${window.location.pathname.substring(
          0,
          window.location.pathname.indexOf("/", 1)
        )}/img/default-image.svg`;
      }

      // Add a class to style it
      this.classList.add("placeholder-img");
    };

    // Add loading="lazy" for better performance
    img.setAttribute("loading", "lazy");

    // Ensure all images have alt text
    if (!img.alt) {
      img.alt = "Hotel image";
    }
  });

  // Apply specific handling for room images
  const roomImages = document.querySelectorAll(".room-card img");
  roomImages.forEach((img) => {
    img.addEventListener("load", function () {
      // Ensure parent container has proper height
      const container = this.closest(".col-md-5");
      if (container) {
        container.style.minHeight = "200px";
      }
    });
  });

  // Apply specific handling for team member images
  const teamImages = document.querySelectorAll(".team-card img");
  teamImages.forEach((img) => {
    img.addEventListener("load", function () {
      // Ensure consistent aspect ratio
      this.style.objectPosition = "center top";
    });
  });
}

/**
 * Initialize date pickers with default values
 */
function initializeDatePickers() {
  // Set default check-in date (today)
  const checkInInputs = document.querySelectorAll('input[name="checkIn"]');
  const today = new Date();
  const todayFormatted = formatDate(today);

  // Set default check-out date (tomorrow)
  const tomorrow = new Date();
  tomorrow.setDate(tomorrow.getDate() + 1);
  const tomorrowFormatted = formatDate(tomorrow);

  checkInInputs.forEach((input) => {
    input.value = todayFormatted;
    input.min = todayFormatted;

    // Find the corresponding check-out input
    const form = input.closest("form");
    if (form) {
      const checkOutInput = form.querySelector('input[name="checkOut"]');
      if (checkOutInput) {
        checkOutInput.value = tomorrowFormatted;
        checkOutInput.min = tomorrowFormatted;

        // Add event listener to update check-out min date when check-in changes
        input.addEventListener("change", function () {
          const newCheckIn = new Date(this.value);
          const newCheckOut = new Date(newCheckIn);
          newCheckOut.setDate(newCheckOut.getDate() + 1);

          checkOutInput.min = formatDate(newCheckOut);

          // If check-out date is before new check-in date, update it
          if (new Date(checkOutInput.value) <= newCheckIn) {
            checkOutInput.value = formatDate(newCheckOut);
          }
        });
      }
    }
  });
}

/**
 * Format a date as YYYY-MM-DD for input[type="date"]
 * @param {Date} date - The date to format
 * @returns {string} Formatted date string
 */
function formatDate(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}

/**
 * Initialize form validation
 */
function initializeFormValidation() {
  // Registration form validation
  const registrationForm = document.getElementById("registrationForm");
  if (registrationForm) {
    registrationForm.addEventListener("submit", function (event) {
      const password = document.getElementById("password").value;
      const confirmPassword = document.getElementById("confirmPassword").value;

      if (password !== confirmPassword) {
        event.preventDefault();
        alert("Passwords do not match!");
      }

      if (password.length < 8) {
        event.preventDefault();
        alert("Password must be at least 8 characters long!");
      }
    });
  }

  // Search form validation
  const searchForms = document.querySelectorAll('form[action="search"]');
  searchForms.forEach((form) => {
    form.addEventListener("submit", function (event) {
      const checkIn = new Date(
        form.querySelector('input[name="checkIn"]').value
      );
      const checkOut = new Date(
        form.querySelector('input[name="checkOut"]').value
      );

      if (checkOut <= checkIn) {
        event.preventDefault();
        alert("Check-out date must be after check-in date!");
      }
    });
  });
}

/**
 * Calculate the number of nights between two dates
 * @param {Date} checkIn - Check-in date
 * @param {Date} checkOut - Check-out date
 * @returns {number} Number of nights
 */
function calculateNights(checkIn, checkOut) {
  const timeDiff = checkOut.getTime() - checkIn.getTime();
  return Math.ceil(timeDiff / (1000 * 3600 * 24));
}

/**
 * Calculate the total price based on room price and number of nights
 * @param {number} pricePerNight - Price per night
 * @param {number} nights - Number of nights
 * @returns {number} Total price
 */
function calculateTotalPrice(pricePerNight, nights) {
  return pricePerNight * nights;
}
