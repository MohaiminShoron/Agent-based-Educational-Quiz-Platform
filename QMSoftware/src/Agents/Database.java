/*
   The database handles all the mechanism that will happen on the database
   1. Retrieving information from the database.
   2. Saving information from the database for future retrieval.
*/

package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Database extends Agent {
    
    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/seng696";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pknbgr86A@";
    private Gson gson;

     // This is the setup() function which holds the 'agent has been started' comment and the implemented behaviours in this agent.

    @Override
    protected void setup() {
        System.out.println(getLocalName() + " is ready.");
        gson = new Gson();  // Initialize Gson instance
        addBehaviour(new HandleUserInfoBehaviour());
        addBehaviour(new HandleQuizmasterRequestsBehaviour());
        addBehaviour(new HandleRetrievingQuestionsBehaviour());
    }

    /* 
       Behaviour to  UserInfo messages
       This behaviour saves the information received from the user to the database for registration, log-in and authentication
       purpose. It calls a function called 'storeUserInfoInDatabase(userInfo)' which receives the user information and makes the
       necessary arrangement to perform this feature.
    */
    
    private class HandleUserInfoBehaviour extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null && msg.getOntology().equals("UserInfo")) {
                UserInfo userInfo = gson.fromJson(msg.getContent(), UserInfo.class);
                storeUserInfoInDatabase(userInfo);
            } else {
                block();
            }
        }
    }

    // The function to store user information to the database according to the database table.
    
    private void storeUserInfoInDatabase(UserInfo userInfo) {
        String sql = "INSERT INTO users (email, password, username) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userInfo.getEmail());
            pstmt.setString(2, userInfo.getPassword());
            pstmt.setString(3, userInfo.getUsername()); // Consider hashing passwords

            pstmt.executeUpdate();
            System.out.println("\nUser info stored successfully ... The available categories are:\n");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to store user info");
        }
    }
       
    /* Behaviour for handling requests from QuizmasterAgent. It calls another function called 'sendCategories()' to fetch the 
    categories from the databse. */
    
    private class HandleQuizmasterRequestsBehaviour extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null && "Send the categories to choose from ...".equals(msg.getContent())) {
                sendCategories();
            }
            else {
                block();
            }
        }
    }

    /* 
    This function runs the query to fetch the categories from the database. These fetched categories are then sent to the 
    interface agent for category selection. It runs exceptions if the entered command to fetch the categories is infeasible.
    */
    
    private void sendCategories() {
        String sql = "SELECT id, name FROM categories ORDER BY id";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                sendMessage(ACLMessage.INFORM, id + ". " + name, "interface");
            }

            sendMessage(ACLMessage.INFORM, "No Categories Available", "interface");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch categories");
        }
    }

     /* 
        Behaviour for handling requests from QuizmasterAgent.
        This behaviour runs a function called 'sendQuestionList(category)'. This function receives the desired category and handles
        retrieving the questions of the desired category from the database.
     */
    
    private class HandleRetrievingQuestionsBehaviour extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
            String content = msg.getContent();
                String category = content.split("#")[1].trim();
                sendQuestionList(category);
            }
            else {
                block();
            }
        }
    }

    /* 
    This function runs the query to fetch the questions for the desired category from the database. These fetched questions are 
    then sent to the QuestionInterface agent for further processing. It runs an exception if the questions could not be fetched 
    for invalid command.
    */
    
    private void sendQuestionList(String category) {
        String sql = "SELECT q.id as questionID, q.text as questionText, o.text as optionText "
                + "FROM questions q "
                + "INNER JOIN options o ON q.id = o.question_id "
                + "WHERE q.category_id = ? "
                + "ORDER BY q.id, o.id";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(category));
            ResultSet rs = pstmt.executeQuery();

            StringBuilder questionWithOptions = new StringBuilder();
            int lastQuestionID = -1;
            int optionNumber = 1;

            while (rs.next()) {
                int currentQuestionID = rs.getInt("questionID");
                if (currentQuestionID != lastQuestionID) {
                    if (lastQuestionID != -1) {
                        sendMessage(ACLMessage.INFORM, questionWithOptions.toString(), "questionInterface");
                        questionWithOptions = new StringBuilder();
                    }
                    questionWithOptions.append(rs.getString("questionText")).append("?\n");
                    optionNumber = 1; // Reset option number for new question
                }
                questionWithOptions.append("option ").append(optionNumber++).append(": ").append(rs.getString("optionText")).append("\n");
                lastQuestionID = currentQuestionID;
            }

            if (lastQuestionID != -1) {
                sendMessage(ACLMessage.INFORM, questionWithOptions.toString(), "questionInterface");
            }

            sendMessage(ACLMessage.INFORM, "End of Questions", "questionInterface");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch Questions");
        }
    }


    private void sendMessage(int performative, String content, String receiverName) {
        ACLMessage msg = new ACLMessage(performative);
        msg.addReceiver(new AID(receiverName, AID.ISLOCALNAME));
        msg.setContent(content);
        send(msg);
    }
}
