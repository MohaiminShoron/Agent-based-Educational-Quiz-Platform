// This is a small agent that handles the printing feature of the computed scores generated from the whole quiz session.

package Agents;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

// Main Score Agent Class, which extends the Agent library.

public class Score extends Agent {

    // This is the setup() function that holds the 'agent has been started' comment and the implemented behaviours in this agent.
    @Override
    protected void setup() {
        System.out.println(getLocalName() + " is ready.");
        addBehaviour(new PublishScoreBehaviour());
    }

    /*
      This behaviour receives the computed score from the QuestionInterface agent and prints it for the user to see how much 
      the user could score.
    */

    private class PublishScoreBehaviour extends SimpleBehaviour {
        private boolean done = false;

        @Override
        public void action() {
            ACLMessage scoreMsg = receive();
            if (scoreMsg != null) {
                String content = scoreMsg.getContent();
                System.out.println("\nYour Score is " + content + " out of 5.\n");
                System.out.println("The quiz session has ended.\n");
                done = true;
            } else {
                block();
            }
        }

        @Override
        public boolean done() {
            return done;
        }
    }
}
