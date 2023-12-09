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
       This behavior class is for receiving the questions sent from the database. It then calls the answerSelection(contentCA) 
       function for selecting and evaluating answers.
    */
    
    private class ReceiveQuestionListBehaviour extends SimpleBehaviour {
        private boolean done = false;
        public int count = 0;
        public int score = 0;
        public char sl = 'A';
        
        @Override
        public void action() {

            ACLMessage questionMsg = receive();
            if (questionMsg != null) {
                String content = questionMsg.getContent();

                if (!"End of Questions".equals(content)) {
                    String question = content.split("#")[0].trim();
                    String contentCA = content.split("#")[1].trim();
                    System.out.println("\n"+sl+". "+question);
                    sl++;
                    answerSelection(contentCA);
                } else {
                    done = true;
                }
            } else {
                block();
            }
        }

        //This answerSelection(String contentCA) function handles the answer selection feature.

        private void answerSelection(String contentCA) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nEnter your answer number: ");
            String selectedAns = scanner.nextLine();
            if(Objects.equals(contentCA, selectedAns)){
                count++;
                score+=10;
            }else{
                count++;
            }
            if(count >= 10){
                MessageHandler.sendInformD(score+"", "scoreAgent");
                done();
            }

        }
        @Override
        public boolean done() {
            return done;
        }
    }
}
