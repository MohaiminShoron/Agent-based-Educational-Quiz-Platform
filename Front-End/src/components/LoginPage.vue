<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="loginUser" class="login-form">
      <label for="username">Username:</label>
      <input type="text" v-model="username" required />
      <br />
      <label for="password">Password:</label>
      <input type="password" v-model="password" required />
      <br />
      <button type="submit">Login</button>
    </form>
    <router-link to="/register">Go to Register</router-link>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    loginUser() {

      this.$axios.post(`${process.env.VUE_APP_API_URL}/api/login`, { username: this.username, password: this.password })
        .then(response => {
          localStorage.setItem('token', "response.data.token");
          this.$store.commit('setUser', {
          userId: response.data.userId,
          username: response.data.username
        });
        localStorage.setItem('userId', response.data.userId);
        localStorage.setItem('username', response.data.username);
        
        // Redirect the user to the home page after successful login
        this.$router.push('/');
      })
        .catch(error => {
          // Handle error
          console.log("error: ", error)
          alert("Login failed, please try again.")
        });

      console.log('Logging in user...');
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
  height: 100vh;
}

.login-form {
  width: 300px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

label {
  display: block;
  margin-bottom: 8px;
}

input {
  width: 100%;
  padding: 8px;
  margin-bottom: 16px;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #4caf50;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>
