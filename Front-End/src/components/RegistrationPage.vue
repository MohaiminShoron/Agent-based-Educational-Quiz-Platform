<template>
  <div class="registration-container">
    <div class="card registration-card">
      <div class="card-body">
        <h2 class="card-title text-center mb-4">User Registration</h2>
        <form @submit.prevent="registerUser">
          <div class="mb-3">
            <label for="username" class="form-label">Username:</label>
            <input type="text" v-model="username" class="form-control" id="username" required />
          </div>
          <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input type="email" v-model="email" class="form-control" id="email" required />
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input type="password" v-model="password" class="form-control" id="password" required />
          </div>
          <div class="d-grid gap-2">
            <button type="submit" class="btn btn-success btn-lg">Register</button>
            <router-link to="/login" class="btn btn-outline-primary btn-lg">Go to Login</router-link>
          </div>
          <!-- <div class="mt-4 text-center">
            <router-link to="/login">Go to Login</router-link>
          </div> -->
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
    };
  },
  methods: {
    registerUser() {
     
      this.$axios.post(`${process.env.VUE_APP_API_URL}/api/user_registration`, { username: this.username, email: this.email, password: this.password })
        .then(response => {
            console.log("response: ",response)
            alert("Registration successful! ")
              this.$router.push('/login');
        })
        .catch(error => {
            console.log("error: ", error)
          
          alert("Something went wrong try again")

        });
      console.log('Registering user...');
    },
  },
};
</script>

<style scoped>
.registration-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  /* background-image: url('../assets/logo.png'); */
  background-size: cover;
  background-position: center;
  background-image: url('~@/assets/background.jpg');
}

.registration-card {
  width: 100%;
  max-width: 400px;
  background-color: rgba(255, 255, 255, 0.9); /* Slightly transparent white */
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
    font-size: 0.9rem; 
  }
}
/* .router-link {
  color: var(--bs-primary);
  font-weight: bold;
} */
</style>