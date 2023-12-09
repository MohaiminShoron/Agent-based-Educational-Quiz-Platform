<template>
  <!-- Container to center the login card on the page -->
  <div class="login-container">
    <div class="card login-card">
      <div class="card-body">
        <h2 class="card-title text-center mb-4">Login</h2>
        <!-- Login form with event handler to prevent default form submission -->
        <form @submit.prevent="loginUser">
          <div class="mb-3">
            <label for="username" class="form-label">Username:</label>
            <input
              type="text"
              v-model="username"
              class="form-control"
              id="username"
              required
            />
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input
              type="password"
              v-model="password"
              class="form-control"
              id="password"
              required
            />
          </div>
          <div class="d-grid gap-2">
            <button type="submit" class="btn btn-success btn-lg">Login</button>
            <router-link to="/register" class="btn btn-outline-primary btn-lg"
              >Go to Register</router-link
            >
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    // Method to handle user login
    loginUser() {
      // API call for user authentication
      this.$axios
        .post(`${process.env.VUE_APP_API_URL}/api/login`, {
          username: this.username,
          password: this.password,
        })
        .then((response) => {
          localStorage.setItem("token", "response.data.token"); // Store authentication token and user details on successful login
          this.$store.commit("setUser", {
            userId: response.data.userId,
            username: response.data.username,
          });
          localStorage.setItem("userId", response.data.userId);
          localStorage.setItem("username", response.data.username);

          // Redirect the user to the home page after successful login
          this.$router.push("/");
        })
        .catch((error) => {
          // Handle error
          console.log("error: ", error);
          alert("Login failed, please try again.");
        });

      console.log("Logging in user...");
    },
  },
};
</script>
<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-image: url("~@/assets/background.jpg");
  background-size: cover;
  background-position: center;
}

.login-card {
  width: 100%;
  max-width: 400px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 15px;
  padding: 2rem;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

.form-label {
  font-size: 1.2rem;
}
.btn-outline-primary {
  color: #0d6efd;
  border-color: #0d6efd;
}

.btn-outline-primary:hover {
  color: #fff;
  background-color: #0d6efd;
  border-color: #0d6efd;
}

.router-link {
  text-decoration: none; /* Remove underline from links */
}

.btn-lg {
  padding: 0.5rem 1rem;
  font-size: 1rem;
}

/*  media queries for responsiveness */
@media (max-width: 576px) {
  .btn-lg {
    padding: 0.5rem; /* Smaller padding for small screens */
    font-size: 0.9rem; /* Smaller font size for small screens */
  }
}
</style>
