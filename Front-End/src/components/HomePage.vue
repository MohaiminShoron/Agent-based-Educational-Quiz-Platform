<template>
  <div class="home-container">
    <div class="card home-card">
      <div class="card-body">
        <h2 class="card-title text-center mb-4">Welcome to the Quiz Application</h2>
        <p class="card-text text-center">Start a quiz and test your knowledge!</p>
        <div class="d-grid gap-2 col-6 mx-auto">
          <router-link to="/categories" class="btn btn-primary btn-lg">Take a Quiz</router-link>
          <button @click="logoutUser" class="btn btn-secondary btn-lg">Logout</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    // Check if user is logged in
    if (!localStorage.getItem('token')) {
      // User is not logged in, redirect to login page
      this.$router.push('/login');
    }
  },
  methods: {
    logoutUser() {
      // // Clear token from localStorage
      // localStorage.removeItem('token');

      this.$store.dispatch('logoutUser');
      // Redirect to login page
      this.$router.push('/login');
    },
    goToCategories() {
      this.$router.push('/categories');
    },
  },
};
</script>

<style scoped>
.home-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-image: url('~@/assets/background.jpg');
  background-size: cover;
  background-position: center;
}

.home-card {
  width: 100%;
  max-width: 600px; /* Adjusted max-width to make the card wider */
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 15px;
  padding: 2rem;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
  margin: auto; /* Added to center the card horizontally */
}

.card-title {
  font-size: 2rem;
  color: var(--bs-primary);
  margin-bottom: 1rem; 
}
.card-text {
  font-size: 1.25rem; 
  color: var(--bs-secondary); 
  margin-bottom: 1.5rem; 
}
.btn-primary {
  background-color: #007bff; 
  border-color: #007bff; 
}

.btn-primary:hover {
  background-color: #0056b3; 
  border-color: #0056b3; 
}

.btn-secondary {
  background-color: #6c757d; 
  border-color: #6c757d; 
}

.btn-secondary:hover {
  background-color: #545b62; 
  border-color: #545b62; 
}
.btn-lg {
  font-size: 1rem;
  padding: 0.5rem 1rem;
}

.d-grid {
  max-width: none;
}
</style>