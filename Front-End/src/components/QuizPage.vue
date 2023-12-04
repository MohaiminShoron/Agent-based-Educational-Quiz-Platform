<template>
    <div class="quiz-page">
      <h2>Quiz: {{ categoryName }}</h2>
      <div v-if="loading" class="loading">Loading questions...</div>
      <div v-else>
        <ul class="questions-list">
          <li v-for="question in questions" :key="question.id" class="question-item">
            <h3>{{ question.text }}</h3>
            <ul class="options-list">
              <li v-for="option in question.options" :key="option.id" class="option-item">
                {{ option.text }}
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      categoryId: {
        type: Number,
        required: true,
      },
    },
    data() {
      return {
        questions: [],
        categoryName: '',
        loading: true,
      };
    },
    created() {
      this.fetchQuizQuestions();
    },
    methods: {
      fetchQuizQuestions() {
        this.loading = true;
        this.$axios.get(`${process.env.VUE_APP_API_URL}/api/questions/${this.categoryId}`)
          .then(response => {
            this.questions = response.data;
            this.categoryName = response.data.length > 0 ? response.data[0].category.name : 'Unknown';
            this.loading = false;
          })
          .catch(error => {
            console.error("Error fetching questions:", error);
            this.loading = false;
            alert("Failed to load questions.");
          });
      },
    },
  };
  </script>
  
  <style scoped>
  .loading {
    text-align: center;
  }
  
  .questions-list {
    list-style-type: none;
    padding: 0;
  }
  
  .question-item {
    margin-bottom: 15px;
  }
  
  .options-list {
    list-style-type: none;
    padding: 0;
  }
  
  .option-item {
    background-color: #f4f4f4;
    margin-bottom: 5px;
    padding: 10px;
    border-radius: 5px;
    cursor: pointer;
  }
  </style>
  