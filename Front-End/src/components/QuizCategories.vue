<template>
  <div class="container py-5">
    <div class="row">
      <div class="col-lg-8 offset-lg-2">
        <h2 class="text-center mb-4">Quiz Categories</h2>
        <div v-if="categories.length">
          <div class="card mb-3" v-for="category in categories" :key="category.id">
            <div class="card-body d-flex justify-content-between align-items-center">
              <h5 class="card-title mb-0">{{ category.name }}</h5>
              <button @click="startQuiz(category.id)" class="btn btn-outline-primary">Start Quiz</button>
            </div>
          </div>
        </div>
        <p v-else class="text-center">Loading categories...</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      categories: [],
    };
  },
  created() {
    this.fetchCategories();
  },
  methods: {
    fetchCategories() {
      this.$axios
        .get(`${process.env.VUE_APP_API_URL}/api/categories`)
        .then((response) => {
          this.categories = response.data;
        })
        .catch((error) => {
          console.error("Error fetching categories:", error);
          alert("Failed to load categories.");
        });
    },
    startQuiz(categoryId) {
      this.$router.push({ name: 'quiz', params: { categoryId } });
    },
  },
};
</script>

<style scoped>
.container {
  max-width: 800px; 
}

.btn-outline-primary {
  padding: 0.375rem 0.75rem;
}

.btn-outline-primary:hover {
  color: #fff;
  background-color: #0d6efd; 
  border-color: #0d6efd;
}

.card {
  margin-bottom: 1rem;
}

.card-title {
  font-size: 1.25rem;
}
</style>
