<template>
  <div class="categories-container">
    <div class="card categories-card">
      <div class="card-body">
        <h2 class="card-title text-center mb-4">Quiz Categories</h2>
        <div v-if="categories.length">
          <!-- Check if categories are loaded -->
          <div v-for="category in categories" :key="category.id" class="mb-3">
            <!-- Loop through each category and display it -->
            <div class="d-flex justify-content-between align-items-center">
              <h5 class="category-name">{{ category.name }}</h5>
              <button
                @click="startQuiz(category.id)"
                class="btn btn-start-quiz"
              >
                Start Quiz
              </button>
            </div>
          </div>
        </div>
        <!-- Show loading message if categories are not yet loaded -->
        <p v-else class="text-center">Loading categories...</p>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      categories: [], // Initial empty array for categories
    };
  },
  created() {
    this.fetchCategories(); // Fetch categories when the component is created
  },
  methods: {
    fetchCategories() {
      // Axios call to get categories from the API
      this.$axios
        .get(`${process.env.VUE_APP_API_URL}/api/categories`)
        .then((response) => {
          this.categories = response.data; // Populate the categories data property with the response
        })
        .catch((error) => {
          // Log and alert if there is an error fetching the categories
          console.error("Error fetching categories:", error);
          alert("Failed to load categories.");
        });
    },
    startQuiz(categoryId) {
      this.$router.push({ name: "quiz", params: { categoryId } }); // Method to navigate to the quiz page with the selected category ID
    },
  },
};
</script>

<style scoped>
.categories-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-image: url("~@/assets/background.jpg");
  background-size: cover;
  background-position: center;
}

.categories-card {
  width: 100%;
  max-width: 500px;
  background-color: rgba(255, 255, 255, 0.95);
  border-radius: 15px;
  padding: 2rem;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
  margin-bottom: 2rem;
}

.card-title {
  font-size: 1.5rem;
  color: #333;
}

.category-name {
  font-size: 1.2rem;
  margin: 0;
}

.btn-start-quiz {
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 20px;
  padding: 0.5rem 1.5rem;
  transition: background-color 0.3s ease;
}

.btn-start-quiz:hover {
  background-color: #43a047;
}

@media (max-width: 767px) {
  .categories-card {
    padding: 1rem;
  }

  .card-title {
    font-size: 1.2rem;
  }

  .btn-start-quiz {
    padding: 0.375rem 1rem;
  }
}
</style>
