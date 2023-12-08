/*    
The QuizMaster agent acts as a middle bridge to handle commands sent between the agents. This agent's implementation is more 
beneficial in real-life scenarios where we may have a large number of agents working on a project.
*/

package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

// Main QuizMaster Agent Class, which extends the Agent library.

public class QuizMaster extends Agent {
    
    private MessageHandler messageHandler;

    // This is the setup() function which holds the 'agent has been started' comment and the implemented behaviours in this agent.

    @Override
    protected void setup() {
        System.out.println(getLocalName() + " is ready.");
        messageHandler = new MessageHandler(this);
        addBehaviour(new InitiateQuizBehaviour());
        addBehaviour(new ReceiveCategorySelectionBehaviour());
    }

    /* 
    Behaviour for initiating the quiz. 
    It sends the command 'fetch the available categories from the database' to the 
    database agent. After getting this command, the database agent will fetch the available categories from the database.
    This command basically starts the quiz process.
    */
    
    private class InitiateQuizBehaviour extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null && "Start the quiz ...".equals(msg.getContent())) {
                messageHandler.sendRequest("Send the categories to choose from ...", "databaseAgent");
            }
            else {
                block();
            }
        }
    }

    /* 
       Behaviour for receiving category selection from the interface agent.
       This behavior receives the 'desired category entered by the user' from the interface agent and sends it to the database
       agent. The database agent will then receive this category serial number and generate the questions for that particular category.
    */
    
    private class ReceiveCategorySelectionBehaviour extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null && msg.getPerformative() == ACLMessage.INFORM) {
                // Process the received category serial number
                String categorySerialNumber = msg.getContent();
                System.out.println("Generating Questions of the desired category: " + categorySerialNumber);
                // Send the serial number to the databaseAgent to retrieve information
                messageHandler.sendRequest("Category#"+categorySerialNumber, "databaseAgent");
            } else {
                block();
            }
        }
    }

    // MessageHandler class to encapsulate message sending logic
    
    private class MessageHandler {
        private Agent agent;

        public MessageHandler(Agent agent) {
            this.agent = agent;
        }

        public void sendRequest(String content, String receiverName) {
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(new AID(receiverName, AID.ISLOCALNAME));
            msg.setContent(content);
            agent.send(msg);
        }
    }
}
