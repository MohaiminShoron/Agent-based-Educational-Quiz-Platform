<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light px-5">
      <router-link to="/" class="navbar-brand">Quiz Application</router-link>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse justify-content-end" id="navbarNavAltMarkup">
        <div class="navbar-nav">
          <router-link to="/" class="nav-item nav-link active">Home</router-link>
          <router-link to="/login" class="nav-item nav-link" v-if="!isLoggedIn">Login</router-link>
          <router-link to="/register" class="nav-item nav-link" v-if="!isLoggedIn">Register</router-link>
          <a class="nav-item nav-link" href="#" v-if="isLoggedIn" @click="logout">Logout</a>
        </div>
      </div>
    </nav>
  </template>
<script>
export default {
  computed: {
    isLoggedIn() {
      return this.$store.getters.isLoggedIn;
    },
  },
  methods: {
    logout() {
      this.$store.dispatch("logoutUser");
      this.$router.push("/login");
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
