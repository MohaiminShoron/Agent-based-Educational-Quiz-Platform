How to load the program

1. Import the project files.

2. Loading the libraries
   
  a. Add all the libraries to the library dependency of the project in intellij IDEA. Setting -> Project Structure -> Libraries -> +
-> Java -> 'add the .jar files'
  b. Add the MySql.jar file to the module dependency in intellij IDEA. Setting -> Project Structure -> Modules -> Dependencies -> +
-> 'add the MySql .jar files'

How to run the program

1. Go to Edit Configuration
2. Add class name; write "jade.Boot"
3. Add the following arguments:

-agents interface:Agents.Interface;quizMasterAgent:Agents.QuizMaster;databaseAgent:Agents.Database;questionInterface:Agents.QuestionInterface;scoreAgent:Agents.Score
