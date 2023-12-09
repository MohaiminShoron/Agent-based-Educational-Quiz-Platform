<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light px-5">
    <router-link to="/" class="navbar-brand"
      >Educational Quiz Platform</router-link
    >
    <!-- Toggler for mobile view -->
    <button
      class="navbar-toggler"
      type="button"
      data-toggle="collapse"
      data-target="#navbarNavAltMarkup"
      aria-controls="navbarNavAltMarkup"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon"></span>
    </button>
    <!-- Navbar items -->
    <div
      class="collapse navbar-collapse justify-content-end"
      id="navbarNavAltMarkup"
    >
      <div class="navbar-nav">
         <!-- Link to the Home page -->
        <router-link to="/" class="nav-item nav-link active">Home</router-link>
        <!-- Links to Login and Register, shown only if the user is not logged in -->
        <router-link to="/login" class="nav-item nav-link" v-if="!isLoggedIn"
          >Login</router-link
        >
        <router-link to="/register" class="nav-item nav-link" v-if="!isLoggedIn"
          >Register</router-link
        >
        <!-- Logout link, shown only if the user is logged in -->
        <a class="nav-item nav-link" href="#" v-if="isLoggedIn" @click="logout"
          >Logout</a
        >
      </div>
    </div>
  </nav>
</template>
<script>
export default {
  computed: {
    // Computed property to determine if the user is logged in
    isLoggedIn() {
      return this.$store.getters.isLoggedIn;
    },
  },
  methods: {
    // Method to handle user logout
    logout() {
      this.$store.dispatch("logoutUser"); // Dispatching logout action
      this.$router.push("/login"); // Redirecting to the login page
    },
  },
};
</script>

<style scoped>
.navbar-brand {
  font-size: 1.5rem;
  font-weight: bold;
  color: var(--bs-primary);
}
.nav-link {
  font-size: 1.2rem;
}

/* Adjustments for mobile screens */
@media (max-width: 576px) {
  .navbar {
    padding-left: 15px;
    padding-right: 15px;
  }

  .navbar-nav .nav-link {
    padding-right: 0.5rem;
  }
}
</style>
