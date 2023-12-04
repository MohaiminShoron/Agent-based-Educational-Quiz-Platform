<template>
  <div class="registration-container">
    <h2>User Registration</h2>
    <form @submit.prevent="registerUser" class="registration-form">
      <label for="username">Username:</label>
      <input type="text" v-model="username" required />
      <br />
      <label for="email">Email:</label>
      <input type="text" v-model="email" required />
      <br />
      <label for="password">Password:</label>
      <input type="password" v-model="password" required />
      <br />
      <button type="submit">Register</button>
    </form>
    <router-link to="/login">Go to Login</router-link>
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
  height: 100vh;
}

.registration-form {
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

router-link {
  margin-top: 16px;
  text-align: center;
  color: #2196f3;
  text-decoration: underline;
  cursor: pointer;
}
</style>
