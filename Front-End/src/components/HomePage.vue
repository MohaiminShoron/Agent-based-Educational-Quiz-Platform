<template>
  <div class="home-container">
    <div class="text-center">
      <h2>Welcome to the Quiz Platform</h2>
      <p>Select a category and test your knowledge!</p>
      <div class="d-grid gap-2 col-6 mx-auto">
        <router-link to="/categories" class="btn btn-primary btn-lg">Take a Quiz</router-link>
        <button @click="logoutUser" class="btn btn-secondary btn-lg">Logout</button>
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
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f8f9fa; /* Light gray background */
}

.text-center {
  text-align: center;
}

.d-grid {
  max-width: 300px; /* Set a max-width for buttons for better aesthetics */
}
.logout-button {
  width: 100%;
  padding: 10px;
  background-color: #4caf50;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 16px;
}

.categories-button {
  width: 100%;
  padding: 10px;
  background-color: #2196f3;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 16px;
}
</style>
