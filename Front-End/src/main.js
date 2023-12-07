import { createApp } from "vue";
import App from "./App.vue";
import axios from "axios";
import store from "./store";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { createRouter, createWebHistory } from "vue-router";
import RegistrationPage from "./components/RegistrationPage.vue";
import LoginPage from "./components/LoginPage.vue";
import HomePage from "./components/HomePage.vue";
import QuizCategories from "./components/QuizCategories.vue";
import QuizPage from "./components/QuizPage.vue";
// import LeaderboardPage from "./components/LeaderboardPage.vue";
import LeaderboardComponent from "./components/LeaderboardComponent.vue";

const routes = [
  { path: "/register", component: RegistrationPage },
  { path: "/login", component: LoginPage },
  { path: "/", component: HomePage, meta: { requiresAuth: true } },
  {
    path: "/categories",
    component: QuizCategories,
    meta: { requiresAuth: true },
  },
  {
    path: "/quiz/:categoryId",
    name: "quiz",
    component: QuizPage,
    props: true,
    meta: { requiresAuth: true },
  },
  {
    path: "/leaderboard/:categoryId",
    name: "Leaderboard",
    component: LeaderboardComponent,
    props: true
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

const app = createApp(App);

app.config.globalProperties.$axios = axios;

app.use(store);
app.use(router);

app.mount("#app");
