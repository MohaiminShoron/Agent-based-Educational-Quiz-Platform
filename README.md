# Seng-696-gr2
Repository for our group project for SENG 696

# Front-End

This project is an educational quiz platform developed using Vue.js 3. It allows users to register, log in, and take quizzes on various topics. The application features a dynamic and user-friendly interface, making it suitable for users of all ages, including kids.

### Features

- User registration and authentication.
- Selection of quiz categories.
- Timed quizzes with multiple-choice questions.
- Leaderboards displaying user rankings based on scores.

## Built With

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