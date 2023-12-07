<template>
    <div class="container my-5">
      <h2 class="text-center mb-4">Quiz Leaderboard</h2>
      <div v-if="leaderboard.length" class="table-responsive">
        <table class="table table-hover shadow-sm">
          <thead class="table-light">
            <tr>
              <th scope="col">Rank</th>
              <th scope="col">Username</th>
              <th scope="col">Score</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(entry, index) in leaderboard" :key="entry.userId" :class="{'table-primary': isCurrentUser(entry.userId)}">
              <th scope="row">{{ index + 1 }}</th>
              <td>{{ entry.username }}</td>
              <td>{{ entry.scoreValue }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div v-else class="text-center">
        <p>No leaderboard data available.</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      categoryId: {
        type: [Number, String],
        required: true
      }
    },
    data() {
      return {
        leaderboard: [],
        currentUserId: null // Setting this based on the logged-in user's ID
      };
    },
    created() {
      this.fetchLeaderboard();
    },
    methods: {
      fetchLeaderboard() {
        this.$axios.get(`${process.env.VUE_APP_API_URL}/api/leaderboard/${this.categoryId}`)
          .then(response => {
            this.leaderboard = response.data;
          })
          .catch(error => {
            console.error('Error fetching leaderboard:', error);
          });
      },
      isCurrentUser(userId) {
      // This method would check if the leaderboard entry belongs to the current user
      return userId === this.currentUserId;
    },
    getCurrentUser() {
    }
    }
  };
  </script>

<style scoped>
.table-hover tbody tr:hover {
  background-color: #f4f4f4;
}
.table-primary {
  background-color: #d2e3fc; /* Highlight color for the current user */
}
</style>
  