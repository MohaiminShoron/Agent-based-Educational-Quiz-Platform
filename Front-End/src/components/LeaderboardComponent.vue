<template>
  <div class="leaderboard-page">
    <div class="leaderboard-container">
      <h2 class="text-center mb-4">Quiz Leaderboard</h2>
      <!-- Conditionally render the table if there is leaderboard data -->
      <div v-if="leaderboard.length" class="table-responsive">
        <table class="table table-hover">
          <thead class="table-header">
            <tr>
              <th scope="col">Rank</th>
              <th scope="col">Username</th>
              <th scope="col">Score</th>
            </tr>
          </thead>
          <tbody>
            <!-- Loop through each entry in the leaderboard array and display -->
            <tr
              v-for="(entry, index) in leaderboard"
              :key="entry.userId"
              :class="{ 'current-user': isCurrentUser(entry.userId) }"
            >
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
  </div>
</template>

<script>
export default {
  // Define the props expected by the component, categoryId is required for fetching leaderboard
  props: {
    categoryId: {
      type: [Number, String],
      required: true,
    },
  },
  data() {
    return {
      leaderboard: [], // Array to hold leaderboard data
      currentUserId: null, //To track the current logged-in user's ID for highlighting in the leaderboard
    };
  },
  // When the component is created, fetch the leaderboard data
  created() {
    this.fetchLeaderboard();
  },
  methods: {
    // Fetch leaderboard data from the backend based on the categoryId
    fetchLeaderboard() {
      this.$axios
        .get(
          `${process.env.VUE_APP_API_URL}/api/leaderboard/${this.categoryId}`
        )
        .then((response) => {
          this.leaderboard = response.data; // Populate the leaderboard array with the response data
        })
        .catch((error) => {
          console.error("Error fetching leaderboard:", error);
        });
    },
    isCurrentUser(userId) {
      // This method would check if the leaderboard entry belongs to the current user
      return userId === this.currentUserId;
    },
    getCurrentUser() {},
  },
};
</script>

<style scoped>
.leaderboard-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-image: url("~@/assets/background.jpg");
  background-size: cover;
  background-position: center;
}

.leaderboard-container {
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 15px;
  padding: 2rem;
  margin: 1rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 800px;
}

.table-header {
  background-color: #f8f9fa;
}

.table-hover tbody tr:hover {
  background-color: #f4f4f4;
}

.current-user {
  background-color: #d2e3fc; /* Highlight color for the current user */
}

/* Additional styling for table to fit the overall design */
.table {
  background-color: #fff;
  margin-bottom: 0;
  border-radius: 0.25rem;
}

.table th,
.table td {
  border-top: none;
}

.text-center p {
  color: #6c757d; /* Muted text color for no data message */
}
</style>
