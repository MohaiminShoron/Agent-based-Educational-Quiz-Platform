package agents;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import entity.UserInfo;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HttpServerAgent extends Agent {
    private HttpServer server;


    protected void setup() {
        System.out.println(getLocalName() + " is ready.");

        try {
            // Create an HTTP server listening on port 8000
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/registerUser", new UserRegistrationHandler());
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
}

