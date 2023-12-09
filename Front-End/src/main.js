// Importing Vue and other necessary libraries
import { createApp } from "vue";
import App from "./App.vue";
import axios from "axios";
import store from "./store";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { createRouter, createWebHistory } from "vue-router";

//Importing Vue components
import RegistrationPage from "./components/RegistrationPage.vue";
import LoginPage from "./components/LoginPage.vue";
import HomePage from "./components/HomePage.vue";
import QuizCategories from "./components/QuizCategories.vue";
import QuizPage from "./components/QuizPage.vue";
import LeaderboardComponent from "./components/LeaderboardComponent.vue";

// Defining the routes for the application
const routes = [
  { path: "/register", component: RegistrationPage },
  { path: "/login", component: LoginPage },
  { path: "/", component: HomePage, meta: { requiresAuth: true } },
  // Route for the quiz categories, accessible only if authenticated
  {
    path: "/categories",
    component: QuizCategories,
    meta: { requiresAuth: true },
  },
  // Route for a specific quiz, accessible only if authenticated
  {
    path: "/quiz/:categoryId",
    name: "quiz",
    component: QuizPage,
    props: true,
    meta: { requiresAuth: true },
  },
  // Route for the leaderboard, with dynamic category ID
  {
    path: "/leaderboard/:categoryId",
    name: "Leaderboard",
    component: LeaderboardComponent,
    props: true
  },
];

// Creating the router instance
const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Creating the Vue app instance
const app = createApp(App);

// Setting Axios as a global property for network requests
app.config.globalProperties.$axios = axios;

// Registering Vuex store and Vue Router with the app
app.use(store);
app.use(router);

// Mounting the app to the DOM
app.mount("#app");
