package agents;

import entity.Category;
import entity.Score;
import entity.UserInfo;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAgent extends Agent {

    protected void setup() {

        addBehaviour(new DatabaseHandler());
        addBehaviour(new SendCategoriesBehaviour());
        addBehaviour(new ScoreSavingBehaviour());
    }


    private class SendCategoriesBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            MessageTemplate.MatchConversationId("FetchCategories");

            ACLMessage request = myAgent.receive(mt);

            if (request != null) {
                String[] categories = fetchCategoriesFromDatabase();
                String jsonResponse = new Gson().toJson(categories);

                ACLMessage reply = request.createReply();
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContent(jsonResponse);
                myAgent.send(reply);
            } else {
                block();
            }
        }

        private String[] fetchCategoriesFromDatabase() {
            List<String> categoriesJson = new ArrayList<>();
            // Database connection string
            String dbUrl = "jdbc:mysql://localhost:3306/seng696";
            String dbUser = "root";
            String dbPass = "";

            // SQL query to fetch categories
            String query = "SELECT id, name FROM categories";

            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    // Convert each category to JSON and add to the list
                    Category category = new Category(id, name);
                    String categoryJson = new Gson().toJson(category);
                    categoriesJson.add(categoryJson);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Convert the list of JSON strings to an array and return
            return categoriesJson.toArray(new String[0]);
        }
    }


    private class DatabaseHandler extends CyclicBehaviour {
        @Override
        public void action() {
            System.out.println(getLocalName() + " is ready.");

            // Listen for a specific type of message from HttpServerAgent
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            MessageTemplate.MatchConversationId("UserRegistration");
            ACLMessage msg = myAgent.receive(mt);

            if (msg != null) {
                // Parse the received message
                String content = msg.getContent();
                UserInfo data = new Gson().fromJson(content, UserInfo.class);

                // Call the method to insert data into the database
                boolean success = processUserRegistration(data);

                // Optionally, send a response back to HttpServerAgent
                ACLMessage reply = msg.createReply();
                reply.setContent(success ? "User info stored successfully" : "Failure");
                myAgent.send(reply);
            } else {
                block();
            }
        }

        private boolean processUserRegistration(UserInfo data) {
            // Implement database insertion logic here
            // Example:
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/seng696", "root", "")) {
                String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, data.getUsername());
                    stmt.setString(2, data.getPassword()); // Consider hashing the password
                    stmt.setString(3, data.getEmail());
                    int rowsAffected = stmt.executeUpdate();
                    return rowsAffected > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

    }

    private class ScoreSavingBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.and(
                    MessageTemplate.MatchPerformative(ACLMessage.AGREE),
                    MessageTemplate.MatchConversationId("ScoreSaving"));

            ACLMessage msg = myAgent.receive(mt);

            if (msg != null) {
                // Parse the received message
                String content = msg.getContent();
                Score score = new Gson().fromJson(content, Score.class);

                // Call the method to insert score data into the database
                boolean success = processScoreSaving(score);

                // Optionally, send a response back to HttpServerAgent
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.AGREE);
                reply.setContent(success ? "Score saved successfully" : "Failure");
                myAgent.send(reply);
            } else {
                block();
            }
        }

        private boolean processScoreSaving(Score score) {
            // Implement database insertion logic for score
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/seng696", "root", "")) {
                String query = "INSERT INTO scores (user_id, username, score_value, category_id) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setLong(1, score.getUserId());
                    stmt.setString(2, score.getUsername());
                    stmt.setInt(3, score.getScoreValue());
                    stmt.setLong(4, score.getCategoryId());
                    int rowsAffected = stmt.executeUpdate();
                    return rowsAffected > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }




}

