<template>
  <div class="quiz-page container py-5">
    <h2
      class="text-center mb-4"
      v-for="category in categories"
      :key="category.id"
    >
      {{ categoryName }}
    </h2>
    <div v-if="loading" class="text-center">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>
    <div v-else>
      <form @submit.prevent="submitQuiz">
        <div
          v-for="(question, qIndex) in questions"
          :key="question.id"
          class="card mb-3"
        >
          <div class="card-header">Question {{ qIndex + 1 }}</div>
          <div class="card-body">
            <h5 class="card-title">{{ question.text }}</h5>
            <div
              v-for="(option, idx) in question.options"
              :key="idx"
              class="form-check"
            >
              <input
                class="form-check-input"
                type="radio"
                :name="'question' + question.id"
                :id="'option' + idx"
                :value="option.text"
                @change="selectAnswer(qIndex, option)"
              />
              <label class="form-check-label" :for="'option' + idx">
                {{ option.text }}
              </label>
            </div>
          </div>
          <div class="card-footer" v-if="submitted">
            <div v-if="userAnswers[qIndex]?.correct" class="correct-answer">
              Correct!
            </div>
            <div v-else class="incorrect-answer">
              Incorrect! Correct answer:
              {{ getCorrectAnswer(question.options) }}
            </div>
          </div>
        </div>
        <div class="d-grid gap-2">
          <button type="submit" class="btn btn-primary" :disabled="submitted">
            Submit Quiz
          </button>
        </div>
      </form>
    </div>
    <!-- Bootstrap 5 Modal -->
    <div
      class="modal fade"
      id="scoreModal"
      tabindex="-1"
      aria-labelledby="scoreModalLabel"
      aria-hidden="true"
      ref="scoreModal"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="scoreModalLabel">Quiz Results</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <p>Your score is: {{ score }}/{{ questions.length *10 }}</p>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import bootstrap from "bootstrap/dist/js/bootstrap.bundle.min.js";

export default {
  props: {
    categoryId: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      categories: [],
      questions: [],
      userAnswers: [], // Store user answers
      categoryName: "",
      loading: true,
      submitted: false, // Tracking if the quiz has been submitted
      score: 0,
      userId: "",
      username: "",
    };
  },
  created() {
    this.fetchQuestions();
  },
  methods: {
    fetchQuestions() {
      this.loading = true;
      this.$axios
        .get(`${process.env.VUE_APP_API_URL}/api/questions/${this.categoryId}`)
        .then((response) => {
          this.questions = response.data;
          // this.categoryName = response.data.length > 0 ? response.data[0].category.name : 'Unknown';
          this.loading = false;
        })
        .catch((error) => {
          console.error("Error fetching questions:", error);
          this.loading = false;
          // alert("Failed to load questions.");
        });
    },
    submitQuiz() {
      // Calculate the score
      let correctCount = 0;
      this.questions.forEach((question, index) => {
        const userAnswer = this.userAnswers[index];
        if (
          userAnswer &&
          question.options.some(
            (option) => option.text === userAnswer.text && option.correct
          )
        ) {
          correctCount++;
        }
      });

      // Each correct answer is worth 10 points
      const score = correctCount * 10;

      // Set the calculated score
      this.score = score;
      this.submitted = true;

      // Show the results modal
      this.showModal();

      // Preparing the score data for the API
      const scoreData = {
        userId: this.userId,
        username: this.username,
        scoreValue: score,
        categoryId: this.categoryId,
      };
      console.log("Submitting scoreData:", scoreData);
      // Posting the score to the backend
      this.$axios
        .post(`${process.env.VUE_APP_API_URL}/api/scores`, scoreData)
        .then((response) => {
          console.log("Score saved:", response.data);
        })
        .catch((error) => {
          console.error("Error posting score:", error);
        });
    },
    showModal() {
      // Using Bootstrap's modal method to show the results modal
      const scoreModal = new bootstrap.Modal(this.$refs.scoreModal);
      scoreModal.show();
    },
    getCorrectAnswer(options) {
      const correct = options.find((option) => option.correct);
      return correct ? correct.text : "Unknown";
    },

    // selectAnswer(questionIndex, option) {
    //   this.userAnswers[questionIndex] = {
    //     text: option.text,
    //     correct: option.correct,
    //   };
    // },
    selectAnswer(questionIndex, option) {
      this.userAnswers[questionIndex] = option;
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
.feedback {
  margin-top: 0.5rem;
}
.correct-answer {
  color: #28a745; /* Bootstrap success color */
}
.incorrect-answer {
  color: #dc3545; /* Bootstrap danger color */
}
</style>
