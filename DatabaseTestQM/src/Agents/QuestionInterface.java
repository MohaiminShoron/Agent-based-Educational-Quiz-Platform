package Agents;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Objects;
import java.util.Scanner;

public class QuestionInterface extends Agent {
    private MessageHandler messageHandler;

    @Override
    protected void setup() {
        System.out.println(getLocalName() + " is ready.");

        messageHandler = new MessageHandler(this);

        addBehaviour(new ReceiveQuestionListBehaviour());
    }
    //New
    private class ReceiveQuestionListBehaviour extends SimpleBehaviour {
        private boolean done = false;
        public int count = 0;
        public int score = 0;
        Boolean totalFlag = false;
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
                    totalFlag = true;
                    done = true;
                }
            } else {
                block();
            }
        }

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
