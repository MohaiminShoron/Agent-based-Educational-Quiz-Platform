package agents;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import entity.Category;
import entity.UserInfo;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class HttpServerAgent extends Agent {
    private HttpServer server;


    protected void setup() {
        System.out.println(getLocalName() + " is ready.");

        try {
            // Create an HTTP server listening on port 8000
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            // Context for user registration
            server.createContext("/registerUser", new UserRegistrationHandler());
            // Context for getting categories
            server.createContext("/getCategories", new CategoriesHandler());
            // Context for saving scores
            server.createContext("/saveScore", new SaveScoreHandler());

            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("HTTP Server started on port 8000");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void takeDown() {
        // Stop the HTTP server
        server.stop(0);
        System.out.println("HTTP Server stopped");
    }

    private class UserRegistrationHandler implements HttpHandler {
        private Gson gson = new Gson();
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if ("POST".equals(httpExchange.getRequestMethod())) {
                InputStream inputStream = httpExchange.getRequestBody();
                String requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                UserInfo registrationData = gson.fromJson(requestBody, UserInfo.class);

                boolean success = sendRegistrationDataToDatabaseAgent(registrationData);

                String response = success ? "User registration successful" : "User registration failed";
                httpExchange.sendResponseHeaders(success ? 200 : 500, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                String response = "Method not allowed";
                httpExchange.sendResponseHeaders(405, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        private boolean sendRegistrationDataToDatabaseAgent(UserInfo data) {
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.setConversationId("UserRegistration");
            msg.addReceiver(new AID("databaseAgent", AID.ISLOCALNAME));
            msg.setOntology("UserInfo");
            msg.setContent(gson.toJson(data));
            send(msg);

            // Wait for reply from Database agent
            ACLMessage reply = blockingReceive(MessageTemplate.MatchOntology("UserInfo"));
            if (reply != null) {
                return "User info stored successfully".equals(reply.getContent());
            } else {
                return false;
            }
        }
    }

    private class CategoriesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if ("GET".equals(httpExchange.getRequestMethod())) {
                String[] categories = requestCategoriesFromDatabaseAgent();

                String jsonResponse = new Gson().toJson(categories);

                httpExchange.sendResponseHeaders(200, jsonResponse.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(jsonResponse.getBytes());
                os.close();
            } else {
                String response = "Method not allowed";
                httpExchange.sendResponseHeaders(405, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

//        private String[] requestCategoriesFromDatabaseAgent() {
//            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
//            msg.setConversationId("FetchCategories");
//            msg.addReceiver(new AID("databaseAgent", AID.ISLOCALNAME));
//            send(msg);
//
//            ACLMessage reply = blockingReceive(MessageTemplate.MatchConversationId("FetchCategories"));
//            if (reply != null && reply.getPerformative() == ACLMessage.INFORM) {
//                Type listType = new TypeToken<List<Category>>(){}.getType();
//                List<Category> categories = new Gson().fromJson(reply.getContent(), listType);
//                return categories.stream().map(category -> new Gson().toJson(category)).toArray(String[]::new);
//            } else {
//                return new String[0];
//            }
//        }

        private String[] requestCategoriesFromDatabaseAgent() {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setConversationId("FetchCategories");
            msg.addReceiver(new AID("databaseAgent", AID.ISLOCALNAME));
            send(msg);

            ACLMessage reply = blockingReceive(MessageTemplate.MatchConversationId("FetchCategories"));
            if (reply != null && reply.getPerformative() == ACLMessage.INFORM) {
                return new Gson().fromJson(reply.getContent(), String[].class);
            } else {
                return new String[0]; // Or handle the error as appropriate
            }
        }
    }

    private class SaveScoreHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Read the request body
                InputStream is = exchange.getRequestBody();
                String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                // Forward the score data to the DatabaseAgent
                boolean success= forwardScoreToDatabaseAgent(requestBody);

                // Send a response back to the Spring Boot application
                String responseText = success ? "Score Received" : "Score saving failed";
                exchange.sendResponseHeaders(200, responseText.length());
                OutputStream os = exchange.getResponseBody();
                os.write(responseText.getBytes());
                os.close();

            } else {
                // Handle other HTTP methods or errors
                exchange.sendResponseHeaders(405, 0); // 405 Method Not Allowed
                exchange.close();
            }
        }

        private boolean forwardScoreToDatabaseAgent(String scoreData) {
            ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
            msg.setConversationId("ScoreSaving");
            msg.addReceiver(new AID("databaseAgent", AID.ISLOCALNAME));
            msg.setOntology("Score");
            msg.setContent(scoreData);
            send(msg);

            // Wait for reply from Database agent
            ACLMessage reply = blockingReceive(MessageTemplate.MatchOntology("Score"));
            if (reply != null && reply.getPerformative() == ACLMessage.AGREE) {
                return "Score saved successfully".equals(reply.getContent());
            } else {
                return false;
            }
        }
    }
}

