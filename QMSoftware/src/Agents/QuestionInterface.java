/*    
    The QuestionInterface works as an helping hand to the interface agent to print the questions of the desired category in the 
    proper manner.
    1. Prints the questions, with options.
    2. Has the command to enter the answer from the user.
    3. computes the score by evaluating the entered answer with the right answer.
    4. Prints the score with the help of the score agent.
*/

package Agents;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import java.util.Objects;
import java.util.Scanner;

public class QuestionInterface extends Agent {
    private MessageHandler messageHandler;

     // This is the setup() function which holds the 'agent has been started' comment and the implemented behaviours in this agent.

    @Override
    protected void setup() {
        System.out.println(getLocalName() + " is ready.");
        messageHandler = new MessageHandler(this);
        addBehaviour(new ReceiveQuestionListBehaviour());
    }

    /* 
       This behavior class is for receiving the questions sent from the database. It then calls the 'processQuestion(content)' 
       function for further processing of the questions and printing them. It also calls the function called 'sendScore()' to 
       handle the scoring mechanism of the project.
    */
    
    private class ReceiveQuestionListBehaviour extends SimpleBehaviour {
        private boolean done = false;
        private int count = 0;
        private int score = 0;

        @Override
        public void action() {
            ACLMessage questionMsg = receive();
            if (questionMsg != null) {
                String content = questionMsg.getContent();

                if (!"End of Questions".equals(content)) {
                    processQuestion(content);
                } else {
                    // Send score to Score agent
                    sendScore();
                    done = true;
                }
            } else {
                block();
            }
        }

        /*
        This processQuestion(String content) function processes the received questions. It holds the mechanism of receiving the
        entered answers by the users. It computes the score per question and sends the score to the score agent for printing.
        */

        private void processQuestion(String content) {
            // Split the content into question and options
            String[] parts = content.split("#");
            String question = parts[0].trim();
            String correctAnswer = parts[parts.length - 1].trim(); // Correct answer is the last part

            // Print question and options
            System.out.println("\n" + (count + 1) + ". " + question);
            for (int i = 1; i < parts.length - 1; i++) {
                System.out.println(i + ": " + parts[i].trim());
            }

            // Get user's answer
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nEnter your answer number: ");
            String selectedAns = scanner.nextLine();

            // Check answer and update score
            if (Objects.equals(correctAnswer, selectedAns)) {
                score += 10;
            }
            count++;

            // Check if quiz is complete
            if (count >= 10) {
                MessageHandler.sendInformD(score + "", "scoreAgent");
                done = true;
            }
        }

        //Processes the score and sends it the score agent.
        
        private void sendScore() {
            ACLMessage scoreMsg = new ACLMessage(ACLMessage.INFORM);
            scoreMsg.addReceiver(new AID("scoreAgent", AID.ISLOCALNAME));
            scoreMsg.setContent(String.valueOf(score));
            send(scoreMsg);
        }

        @Override
        public boolean done() {
            return done;
        }
    }
}
