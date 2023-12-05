<template>
  <div class="quiz-page container py-5">
    <h2 class="text-center">Quiz</h2>
    <div v-if="loading" class="text-center">Loading questions...</div>
    <div v-else class="mt-4">
      <div class="card mb-4" v-for="(question, index) in questions" :key="question.id">
        <div class="card-header">
          Question {{ index + 1 }}
        </div>
        <div class="card-body">
          <h5 class="card-title">{{ question.text }}</h5>
          <div class="mb-3">
            <div class="form-check" v-for="(option, idx) in question.options" :key="idx">
              <input class="form-check-input" type="radio" :name="'option' + question.id" :id="'option' + question.id + '-' + idx" :value="option.correct">
              <label class="form-check-label" :for="'option' + question.id + '-' + idx">
                {{ option.text }}
              </label>
            </div>
          </div>
        </div>
      </div>
      <div class="text-center">
        <button class="btn btn-primary" @click="submitQuiz">Submit Quiz</button>
      </div>
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
      this.fetchQuestions();
    },
    methods: {
      fetchQuestions() {
        this.loading = true;
        this.$axios.get(`${process.env.VUE_APP_API_URL}/api/questions/${this.categoryId}`)
          .then(response => {
            this.questions = response.data;
            // this.categoryName = response.data.length > 0 ? response.data[0].category.name : 'Unknown';
            this.loading = false;
          })
          .catch(error => {
            console.error("Error fetching questions:", error);
            this.loading = false;
            // alert("Failed to load questions.");
          });
      },
      submitQuiz() {
      // TODO: Handle quiz submission
      alert('Quiz submitted!');
    },
    },
  };
  </script>
  
  <style scoped>
  .quiz-page {
  max-width: 800px;
  margin: auto;
}

.form-check-label {
  margin-left: 0.3rem;
}
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
  