// Names: Jake Wade, Tomas Caruso
// Date: 2/7/23
// file name: QuestionsGame




import java.util.*;
import java.io.*;


public class QuestionsGame {


    //overall Value root
    QuestionNode root;


    //Contructor with object
    public QuestionsGame(String object){
        //set to overall
        root = new QuestionNode(object);
    }


    //Consructor with scanner
    public QuestionsGame(Scanner input){
        //Go through file add all to tree
        while(input.hasNextLine()){
            //Call helper sort
            root = treeSorter(input);
        }
    }


    //helper sort
    private QuestionNode treeSorter(Scanner input){
        //General values
            //Start type
            String type = input.nextLine();
            //Start Question
            String question = input.nextLine();
            //make node with question
            QuestionNode temp = new QuestionNode(question);
           
            //IF it starts with question ignore
            if(type.equalsIgnoreCase("Q:")) {
                //The type left
                temp.yes = treeSorter(input);
                //The type right
                temp.no = treeSorter(input);    
            }
            return temp;
    }


    //To check if questionnode is empty
    public boolean isEmpty(){
        //if not return false
        if(root != null){
            return false;
        }
        //if empty true
        else{
            return true;
        }
    }


    //Class for node
    public class QuestionNode {


        //data e for any type
        public String data;
        public QuestionNode yes;
        public QuestionNode no;


        //Constructor with data
        public QuestionNode(String data){
            this(data, null, null);
        }


        //Constrtor with data and yes/no
        public QuestionNode(String data, QuestionNode no, QuestionNode yes){
            this.data = data;
            this.no = no;
            this.yes = yes;
        }
        // Your code here
    }


    //Public called
    public void saveQuestions(PrintStream output){
        //if null error
            if(output == null) {
            	throw new IllegalArgumentException();
            }
           //Call helper to add
             saveQuestion(output,root);
    }
    private void saveQuestion(PrintStream output, QuestionNode temp){
        //If leaf
        if(temp.yes == null || temp.no ==  null){
            //Pull out Answer user
            output.println("A:");
            //Add to the file
            output.println(temp.data);
        }
        //Not leaf
        else{
            //Pull out Question
            output.println("Q:");
            //Add question to file
            output.println(temp.data);
            //go to leaf yes
            saveQuestion(output , temp.yes);
            //go to leaf no to check
            saveQuestion(output , temp.no);


        }
    }



     //Play call
    public void play(Scanner console){
        //call helper
     root=play(root,console);




    }


    //Play helper
    private QuestionNode play(QuestionNode temp,Scanner console){
        //If leaf
        if(temp.yes == null || temp.no ==  null) {
            //Check if your object
            System.out.print("Would your object happen to be " + temp.data + "? y/n ");
            //To get the y/n
            String cons = console.nextLine().trim().toLowerCase();
            //if the object equals yes it is a win
            if(cons.equals("y")){
                System.out.print("I win \nGAME OVER");
                System.exit(0);
            }
            //not yes add to it
            else{
                //asks the player what answer they were thinking of
                System.out.println("what were you thinking of?");
                //preferredAns is a new QuestionNode with console.nextLine() as the data inside
                QuestionNode preferredAns = new QuestionNode(console.nextLine());
              //asks the player for a question that can be used to distinguish the object
                System.out.println("what is a question to distinguish that object from?");
                //the question is picked up by the scanner as the string prefferredQuestion
                String preferredQuestion = console.nextLine();
              //asks the player if the object is in the yes or no node from the last question
                System.out.println("is the object the yes or no answer to that question?");
                //the answer is picked up by the scanner as the string preferredYN
                String preferredYN = console.nextLine().trim().toLowerCase();
                //if preferredYN is equal to y
                if(preferredYN.equalsIgnoreCase("y")){
                	//temp is set to a new QuestionNode with preferredQuestion as the data, preferredAns as the node to the yes side of temp, and previous temp to the no side
                    temp = new QuestionNode(preferredQuestion, temp, preferredAns);
                }
                else{ 
                	//temp is set to a new QuestionNode with preferredQuestion as the data, preferredAns as the node to the no side of temp, and previous temp to the yes side
                    temp = new QuestionNode(preferredQuestion, preferredAns, temp);
                }
            }
        }
        else{
        	//if not on leaf ans is set to answer returned in questionTime
            String ans = questionTime(temp, console);
            //if ans is y
            if(ans.equals("Y")){
            	//play recurs moving through temp.yes
                temp.yes = play(temp.yes, console);
            }
            else{
            	//play recurs moving through temp.no
                temp.no = play(temp.no, console);
            }
        }
        //temp is returned
        return temp;


    }


    //method asks the user question stored as data in QuestionNode
    private String questionTime(QuestionNode temp, Scanner console){
    	//prints out the question 
        System.out.println(temp.data);
        //scanner picks up answer as string answer
        String answer = console.nextLine();
        //if answer is equal to y
        if(answer.trim().toLowerCase().startsWith("y")){
        	//returns Y
            return "Y";
        }
        else{
        	//returns N
            return "N";
        }
    }
}















