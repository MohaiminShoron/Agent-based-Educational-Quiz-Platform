There are two agent-based programs in this branch. For this project, the main focus is on "QMSoftware". 
"DatabaseTestQM" has been also included to show the change in implementation when we have a different database structure.

The program descriptions for each program are:

  1. DatabaseTestQM

This program is built on a three-table database (database name "quizmasterUsers") structure, where we have one table to store the questions and their options called 'question'. This table has eight columns, such as questionID, categoryID, questionText, option1, option2, option3, option4, and correctAnswer.
   
   2. QMSoftware

This program is built on a four-table database (database name "seng696") structure, where we have two tables to store the questions and their options, called 'questions' and 'options' respectively.

  a. The table 'questions' has three columns such as id, text, and category_id
  b. The table 'options' four columns such as id, is_correct, text, and question_id.

How run the program "QMSoftware"? ...

Step 1: Load the program "QMSoftware"

   a. Clone the project files.
   b. Open the "QMSoftware" project in a new window on the IDE. Preferably in the intellij IDEA.

Step 2: Loading the libraries
   
  a. Add all the libraries to the library dependency of the project in intellij IDEA. 
  Setting -> Project Structure -> Libraries -> + -> Java -> 'add the .jar files'
  b. Add the MySql.jar file to the module dependency in intellij IDEA. Setting -> Project Structure -> Modules -> Dependencies -> + -> 'add the MySql .jar files'

Step 3: Handling the database

   a. Turn on the MySQL server.
   b. Make a new schema on the MySql workbench and import or copy-paste the query in the "seng696.sql" file. Run the query to generate tables.
   c. Make the necessary modifications (server link, username, password) to the DatabaseConnectionTest file and the database agent.

Step 4: Run the program

1. Go to Edit Configuration.
2. Give a program name. Preferably "QMsoftware".
3. Add class name; write "jade.Boot".
4. Add the following arguments:

-agents interface:Agents.Interface;quizMasterAgent:Agents.QuizMaster;databaseAgent:Agents.Database;questionInterface:Agents.QuestionInterface;scoreAgent:Agents.Score
