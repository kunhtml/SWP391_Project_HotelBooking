// Main JavaScript for Hotel Booking Website

document.addEventListener("DOMContentLoaded", function () {
  // Initialize date pickers
  initDatePickers();

  // Handle booking form submission
  initBookingForm();

  // Mobile menu toggle
  initMobileMenu();
});

// Initialize date pickers for check-in and check-out
function initDatePickers() {
  // This is a placeholder for date picker initialization
  // You would typically use a library like flatpickr or datepicker.js

  // For example, if using flatpickr:
  // if (typeof flatpickr !== 'undefined') {
  //     flatpickr("#check-in", {
  //         minDate: "today",
  //         onChange: function(selectedDates, dateStr) {
  //             // Set the min date of checkout to be the check-in date
  //             checkOutPicker.set("minDate", dateStr);
  //         }
  //     });
  //
  //     var checkOutPicker = flatpickr("#check-out", {
  //         minDate: "today"
  //     });
  // }

  // For now, we'll just use the built-in date input
  const checkInInput = document.getElementById("check-in");
  const checkOutInput = document.getElementById("check-out");

  if (checkInInput && checkOutInput) {
    // Set min date to today
    const today = new Date().toISOString().split("T")[0];
    checkInInput.min = today;
    checkOutInput.min = today;

    // When check-in date changes, update check-out min date
    checkInInput.addEventListener("change", function () {
      checkOutInput.min = this.value;

      // If check-out date is before check-in date, update it
      if (checkOutInput.value < checkInInput.value) {
        checkOutInput.value = checkInInput.value;
      }
    });
  }
}

// Handle booking form submission
function initBookingForm() {
  const bookingForm = document.getElementById("booking-form");

  if (bookingForm) {
    bookingForm.addEventListener("submit", function (e) {
      e.preventDefault();

      // Get form values
      const checkIn = document.getElementById("check-in").value;
      const checkOut = document.getElementById("check-out").value;
      const adults = document.getElementById("adults").value;
      const children = document.getElementById("children").value;
      const rooms = document.getElementById("rooms").value;

      // Validate form
      if (!checkIn || !checkOut) {
        alert("Please select check-in and check-out dates");
        return;
      }

      // Redirect to search results page with parameters
      window.location.href =
        `${window.location.origin}${window.location.pathname.substring(
          0,
          window.location.pathname.lastIndexOf("/")
        )}` +
        `/search?check-in=${checkIn}&check-out=${checkOut}&adults=${adults}&children=${children}&rooms=${rooms}`;
    });
  }
}

// Mobile menu toggle
function initMobileMenu() {
  const menuToggle = document.getElementById("menu-toggle");
  const navMenu = document.getElementById("nav-menu");

  if (menuToggle && navMenu) {
    menuToggle.addEventListener("click", function () {
      navMenu.classList.toggle("active");
      menuToggle.classList.toggle("active");
    });
  }
}

// Smooth scroll for navigation links
document.querySelectorAll('a[href^="#"]').forEach((anchor) => {
  anchor.addEventListener("click", function (e) {
    e.preventDefault();

    const target = document.querySelector(this.getAttribute("href"));

    if (target) {
      window.scrollTo({
        top: target.offsetTop - 100,
        behavior: "smooth",
      });

      // Close mobile menu if open
      const navMenu = document.getElementById("nav-menu");
      const menuToggle = document.getElementById("menu-toggle");

      if (navMenu && navMenu.classList.contains("active")) {
        navMenu.classList.remove("active");
        menuToggle.classList.remove("active");
      }
    }
  });
});

// Room filtering functionality (placeholder)
function filterRooms(category) {
  const roomCards = document.querySelectorAll(".room-card");

  roomCards.forEach((card) => {
    if (category === "all") {
      card.style.display = "block";
    } else if (card.dataset.category === category) {
      card.style.display = "block";
    } else {
      card.style.display = "none";
    }
  });
}

// Initialize room filter buttons
const filterButtons = document.querySelectorAll(".filter-btn");
if (filterButtons.length > 0) {
  filterButtons.forEach((button) => {
    button.addEventListener("click", function () {
      // Remove active class from all buttons
      filterButtons.forEach((btn) => btn.classList.remove("active"));

      // Add active class to clicked button
      this.classList.add("active");

      // Filter rooms
      const category = this.dataset.filter;
      filterRooms(category);
    });
  });
}
