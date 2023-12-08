<template>
  <div class="quiz-page">
  <div class="quiz-container">
    <!-- <h2
      class="text-center mb-4"
      v-for="category in categories"
      :key="category.id"
    >
      {{ categoryName }}
    </h2> -->
    <div class="timer-container mb-4">
      <h3 v-if="submitted">Quiz Completed</h3>
      <h3 v-else>Time remaining: {{ timer }} seconds</h3>
    </div>
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
        <div class="d-flex justify-content-between align-items-center mt-3">
          <!-- Show Leaderboard Button -->
          <button
            v-if="submitted && score > 0"
            type="button"
            class="btn btn-custom-leaderboard"
            @click="showLeaderboard"
          >
            Show Leaderboard
          </button>

          <!-- Submit Quiz Button -->
          <button type="submit" class="btn btn-custom-submit" :disabled="submitted">
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
            <p>Your score is: {{ score }}/{{ questions.length * 10 }}</p>
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
</div>
</template>

<script>
import bootstrap from "bootstrap/dist/js/bootstrap.bundle.min.js";

export default {
  props: {
    categoryId: {
      type: [Number, String],
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
      timer: 60,
      interval: null,
    };
  },
  created() {
    // Check if user details are stored in Vuex or localStorage
    this.userId = this.$store.state.userId || localStorage.getItem("userId");
    this.username =
      this.$store.state.username || localStorage.getItem("username");

    if (!this.userId || !this.username) {
      // If user details are not found, redirect to the login page
      alert("Please login to continue.");
      this.$router.push("/login");
    } else {
      // If user details are found, proceed to fetch categories or questions
      this.fetchQuestions();
    }
  },
  mounted() {
    this.startTimer();
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
    startTimer() {
      this.interval = setInterval(() => {
        if (this.timer > 0) {
          this.timer--;
        } else {
          this.stopTimer();
          this.submitQuiz();
        }
      }, 1000);
    },
    stopTimer() {
      clearInterval(this.interval);
    },
    submitQuiz() {
      this.stopTimer();
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

      // Setting the calculated score
      this.score = score;
      this.submitted = true;

      // Show the results modal
      this.showModal();

      const userId = localStorage.getItem("userId") || this.$store.state.userId;
      const username =
        localStorage.getItem("username") || this.$store.state.username;

      // Preparing the score data for the API
      const scoreData = {
        userId: userId,
        username: username,
        scoreValue: this.score,
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
    showLeaderboard() {
      // Navigating to the leaderboard page with the categoryId
      this.$router.push({
        name: "Leaderboard",
        params: { categoryId: this.categoryId },
      });
    },
    beforeDestroy() {
      this.stopTimer();
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
    selectAnswer(questionIndex, option) {
      this.userAnswers[questionIndex] = option;
    },
  },
};
</script>

<style scoped>
.quiz-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-image: url('~@/assets/background.jpg'); 
  background-size: cover;
  background-position: center;
}

.quiz-container {
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 15px;
  padding: 2rem;
  margin: 1rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 800px;
}

.timer-container {
  text-align: center;
  background-color: #f8f9fa;
  padding: 0.75rem 1.25rem;
  border-radius: .25rem;
  margin-bottom: 1rem;
}

.timer-container h3 {
  color: #dc3545; /* Bootstrap danger color for emphasis */
  font-weight: bold;
  margin: 0;
}

.card {
  border: none;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.card-header {
  background-color: #f8f9fa;
  font-weight: bold;
}

.card-body {
  background-color: #fff;
}

.card-footer {
  background-color: #f8f9fa;
}

.correct-answer {
  color: #28a745; /* Bootstrap success color */
}

.incorrect-answer {
  color: #dc3545; /* Bootstrap danger color */
}
.btn-custom-leaderboard {
  background-color: #f9a825; 
  color: white; 
  border: none; 
  transition: background-color 0.3s ease; /* Smooth transition for hover effect */
}

.btn-custom-leaderboard:hover {
  background-color: #fbc02d; 
}

.btn-custom-submit {
  background-color: #5e35b1; 
  color: white; 
  border: none; 
  transition: background-color 0.3s ease; /* Smooth transition for hover effect */
}

.btn-custom-submit:hover {
  background-color: #673ab7; 
  cursor: pointer; 
}

/* If the button is disabled, applying different styling */
.btn-custom-submit:disabled {
  background-color: #9575cd; /* Lighter purple color to indicate disabled state */
  color: #fff; 
  cursor: not-allowed; /* Change cursor to not-allowed icon */
}
</style>
