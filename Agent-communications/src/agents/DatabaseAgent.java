package agents;

import entity.UserInfo;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseAgent extends Agent {

    protected void setup() {
        addBehaviour(new DatabaseHandler());
    }

    private class DatabaseHandler extends CyclicBehaviour {
        @Override
        public void action() {
            System.out.println(getLocalName() + " is ready.");

            // Listen for a specific type of message from HttpServerAgent
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
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

}
