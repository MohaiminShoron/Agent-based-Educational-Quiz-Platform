# Seng-696-gr2
The Educational Quiz Platform is a web-based application designed to offer an interactive and engaging quiz experience. It features Vue.js 3 as the front-end and on the backend we have a Spring Boot-based API middleware that interfaces with a JADE agent-based system. This platform allows users to register, take quizzes in various categories, and view their scores.

### Features

- User registration and authentication.
- 4 quiz categories to select from.
- Timed quizzes with multiple-choice questions.
- Leaderboards displaying user rankings based on scores per category.

## Front-End

This project is an educational quiz platform developed using Vue.js 3. It allows users to register, log in, and take quizzes on various topics. The application features a dynamic and user-friendly interface, making it suitable for users of all ages, including kids.



### Built With

- [Vue.js 3](https://vuejs.org/)
- [Bootstrap 5](https://getbootstrap.com/)
- [Axios](https://github.com/axios/axios) for API calls.

### Directory Structure

| Name                              | Description |
| --------------------------------- | ----------- |
| **node_modules/**                 | Contains all the npm dependencies. |
| **public/**                       | Static files such as the index.html entry file. |
| **src/**                          | Source files for the Vue application. |
| **src/assets**                    | Assets such as images and global styles. |
| **src/components**                | Vue components for various parts of the application such as HomePage, Login, etc. |
| **src/App.vue**                   | The main application component that includes the nav bar and router view. |
| **src/main.js**                   | The entry point for the Vue application. Sets up the Vue instance. |
| **src/store.js**                  | Vuex store for state management across the application. |
| **.env**                          | Environment variables for the Vue application. |
| **.gitignore**                    | Specifies intentionally untracked files to ignore. |
| **babel.config.js**               | Configuration for Babel. |
| **jsconfig.json**                 | Configuration for JavaScript language features. |
| **package-lock.json**             | Automatically generated file for any operations where npm modifies either the node_modules tree, package.json. |
| **package.json**                  | Defines npm behaviors and packages for the project. |
| **vue.config.js**                 | Configuration for the Vue CLI project. |


### Requirements

- [Node.js](https://nodejs.org/) v14 or newer.

### Installation

- Clone the repo - `git clone https://github.com/MohaiminShoron/Seng-696-gr2.git`.
- Install project dependencies — `npm install`.
- Launch the app — `npm run serve`, it will become available at [http://localhost:8080](http://localhost:8080/).

## Backend

### Getting Started

These instructions will help you set up the project on your local machine for development and testing.

### Prerequisites
- Java 11
- Spring Boot
- JADE (Java Agent Development Framework)
- MySQL

### Architecture

## Spring Boot Application (API Middleware)

The Spring Boot application serves as an API middleware, facilitating communication between the frontend and the JADE agent system. It exposes RESTful endpoints for user registration and login, quiz interactions, score submissions, etc.

### Key Endpoints:

- POST /api/register: Registers a new user.
- POST /api/login: Allows registered user to login.
- GET /api/categories: Retrieves available quiz categories.
- GET /api/questions/{category_id}: Retrieves multiple-choice questions for a given category.
- POST /api/scores: Submits and saves quiz scores.
- GET /api/leaderboard/{category_id}: Fetch top scores for given category to show leaderboard.

## Agent-Communication Application

The JADE agent system orchestrates the backend functionalities of the quiz platform, handling data operations and agent interactions.

### Roles of JADE Agents

- HttpServerAgent: Acts as an interface between the Spring Boot application and the JADE system, handling HTTP requests and translating them into agent communication.
- DatabaseAgent: Manages interactions with the MySQL database for storing and retrieving user data, scores, and categories.


## Database Schema

The database schema for the Educational Quiz Platform is designed to support efficient storage and retrieval of quiz-related data. It comprises several key tables, each serving a specific purpose in the application:

### Users Table
- **Description**: Stores information about registered users.
- **Fields**:
  - `UserId` (Primary Key): A unique identifier for each user.
  - `Username`: The user's chosen username.
  - `Password`: The user's password (stored securely).
  - `Email`: The user's email address.

### Scores Table
- **Description**: Keeps track of users' scores for different quiz attempts.
- **Fields**:
  - `ScoreId` (Primary Key): A unique identifier for each score entry.
  - `UserId` (Foreign Key): Links to the `UserId` in the Users table.
  - `ScoreValue`: The score achieved by the user.
  - `CategoryId` (Foreign Key): The category of the quiz taken.

### Questions Table
- **Description**: Contains the quiz questions.
- **Fields**:
  - `QuestionId` (Primary Key): A unique identifier for each question.
  - `CategoryId` (Foreign Key): The category to which the question belongs.
  - `Text`: The text of the question.
  - `Options`: The multiple-choice options for the question.
  - `Answer`: The correct answer to the question.

### Options Table
- **Description**: Provides the multiple-choice options for each question.
- **Fields**:
  - `OptionId` (Primary Key): A unique identifier for each option.
  - `QuestionId` (Foreign Key): Links to the `QuestionId` in the Questions table.
  - `Text`: The options of a question.
  - `IsCorrect`: Indicates whether this option is the correct answer to the question.


### Categories Table
- **Description**: Lists the different categories of quizzes available.
- **Fields**:
  - `CategoryId` (Primary Key): A unique identifier for each category.
  - `CategoryName`: The name of the quiz category (e.g., Science, Mathematics).
