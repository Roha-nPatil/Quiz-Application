package model;

import dao.QuizDAO;
import dao.QuizDAOImplimentation;
import dto.QuizDTO;

import javax.xml.stream.events.DTD;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainApp {

    static Scanner sc =new Scanner(System.in);
    static QuizDAOImplimentation dao = new QuizDAOImplimentation();
    private static final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    public static void main(String[] args)
    {
        boolean status = true;

        while (status)
        {
            System.out.println("\n*** QUIZ APPLICATION ***\n");
            System.out.println("1. ADD QUESTIONS");
            System.out.println("2. REMOVE QUESTIONS");
            System.out.println("3. UPDATE QUESTIONS");
            System.out.println("4. DISPLAY ALL QUESTIONS");
            System.out.println("5. TAKE QUIZ");
            System.out.println("6. CREATE ACCOUNT");
            System.out.println("7. EXIT");
            System.out.println("----------------------------------");
            System.out.print("Select Option : ");
            int choice1 = sc.nextInt();

            if (choice1 < 5 || choice1 == 6)
            {
                authenticateUser();
            }

            switch (choice1) {
                case 1:
                    addQuestion(sc);
                    break;
                case 2:
                    removeQuestion(sc);
                    break;
                case 3:
                    updateQuestion(sc);
                    break;
                case 4:
                    displayAllQuestion(sc);
                    break;
                case 5:
                    takeQuiz(sc);
                    break;
                case 6:
                    createAccount(sc);
                    break;
                case 7:
                    status = false;
                default:
                    System.err.println("Invalid Input");
            }
        }
    }

    private static void authenticateUser() {
        System.out.println();
        System.out.println("LOGIN");
        System.out.println();

        System.out.print("ENTER EMAIL : ");
        sc.nextLine();
        String email = sc.nextLine();

        System.out.print("ENTER PASSWORD : ");
        String password = sc.next();

        boolean count = dao.authentication(email, password);
        if (count == false) {
            System.err.println("Invalid login credentials...");
            System.exit(0);
        } else {
            System.out.println("Login successful...");
            System.out.println();
        }
    }

    private static void addQuestion(Scanner sc) {
        System.out.println("ENTER QUESTION");
        sc.nextLine();
        String question = sc.nextLine();
        System.out.println("ENTER OPTION 1");
        String option1 = sc.nextLine();
        System.out.println("ENTER OPTION 2");
        String option2 = sc.nextLine();
        System.out.println("ENTER OPTION 3");
        String option3 = sc.nextLine();
        System.out.println("ENTER OPTION 4");
        String option4 = sc.nextLine();
        System.out.println("ENTER ANSWER");
        String answer = sc.nextLine();

        QuizDTO q = new QuizDTO();
        q.setQuestion(question);
        q.setOption1(option1);
        q.setOption2(option2);
        q.setOption3(option3);
        q.setOption4(option4);
        q.setAnswer(answer);

        int count = dao.addQuestion(q);
        if(count>0)
        {
            System.out.println("Question Added Successfully.");
        }else{
            System.err.println("Question Not Added");
        }
    }

    private static void removeQuestion(Scanner sc) {
        System.out.println("ENTER Question ID");
        int questionId = sc.nextInt();

        int count = dao.removeQuestion(questionId);
        if(count>0)
        {
            System.out.println("Question Removed Successfully\n");
        }else{
            System.err.println("Invalid Question Id\n");
        }
    }

    private static void updateQuestion(Scanner sc) {
        System.out.println("1. MODIFY QUESTION");
        System.out.println("2. MODIFY OPTION");
        System.out.println("----------------------------------");
        System.out.print("Select Option : ");
        int choice2 = sc.nextInt();
        switch(choice2)
        {
            case 1:
                modifyQuestion();
                break;
            case 2:
                modifyOptions();
        }
    }

    private static void modifyQuestion() {
        System.out.println("ENTER QUESTION ID");
        int questionId = sc.nextInt();

        System.out.println("ENTER QUESTION");
        sc.nextLine();
        String modifyQuestion = sc.nextLine();

        QuizDTO q = new QuizDTO();
        q.setQuestion(modifyQuestion);

        int count = dao.modifyQuestion(q, questionId);
        if(count>0)
        {
            System.out.println("Question Updated Successfully.");
        }else{
            System.err.println("Question Not Updated");
        }
    }

    private static void modifyOptions() {
        System.out.println("ENTER ID");
        int questionId = sc.nextInt();

        System.out.println("ENTER OPTION 1");
        sc.nextLine();
        String option1 = sc.nextLine();

        System.out.println("ENTER OPTION 2");
        String option2 = sc.nextLine();

        System.out.println("ENTER OPTION 3");
        String option3 = sc.nextLine();

        System.out.println("ENTER OPTION 4");
        String option4 = sc.nextLine();

        System.out.println("ENTER ANSWER");
        String answer = sc.nextLine();

        QuizDTO q = new QuizDTO();
        q.setOption1(option1);
        q.setOption2(option2);
        q.setOption3(option3);
        q.setOption4(option4);
        q.setAnswer(answer);

        int count = dao.modifyOptions(q,questionId);
        if(count>0){
            System.out.println("OPTION UPDATED SUCCESSFULLY");
        }else{
            System.err.println("INVALID INPUT");
        }
    }

    private static void displayAllQuestion(Scanner sc) {
        List<QuizDTO> questionList= dao.displayQuestion();

        for (QuizDTO q: questionList)
        {
            System.out.println("Q"+q.getQuestionId()+". "+q.getQuestion());
            System.out.println("1. "+q.getOption1());
            System.out.println("2. "+q.getOption2());
            System.out.println("3. "+q.getOption3());
            System.out.println("4. "+q.getOption4());
            System.out.println("-------->Answer : "+q.getAnswer());
            System.out.println("\n----------------------------------");
        }
    }

    static QuizDTO d = new QuizDTO();
    public static void takeQuiz(Scanner sc) {
        QuizDTO d = new QuizDTO();
        List<QuizDTO> questionList = dao.displayQuestion();
        int marks = 0;

//        System.out.println("Starting quiz timer... (20 minutes)");
//        int finalMarks = marks;
//        timer.schedule(() -> {
//            System.out.println("Time's up! Submitting quiz automatically...");
//            submitQuiz(finalMarks);
//        }, 15, TimeUnit.SECONDS);


        System.out.println("READY FOR TEST ");
        String ans = sc.nextLine();
        for (QuizDTO q : questionList) {
            System.out.println("Q"   + q.getQuestionId() + ". " + q.getQuestion());
            System.out.println("1. " + q.getOption1());
            System.out.println("2. " + q.getOption2());
            System.out.println("3. " + q.getOption3());
            System.out.println("4. " + q.getOption4());
            System.out.println("ENTER YOUR ANS ");
            ans = sc.nextLine();
            String actualAns = q.getAnswer();
            if(ans.equals(actualAns)) {
                marks += 5;
            } else{
                marks -= 2;
            }
        }
        d.setMarks(marks);
//        timer.shutdown();
        submitQuiz(marks);
    }

    private static void submitQuiz(int marks) {
        QuizDTO dto = new QuizDTO();
        System.out.println("-----------------------------------");
        System.out.println("YOUR TOTAL MARKS ARE : " + marks);
        System.out.println("-----------------------------------");
        System.exit(0);
    }

    private static void createAccount(Scanner sc) {
        System.out.println("*** CREATE ACCOUNT ***\n");
        System.out.print("ENTER EMAIL : ");
        sc.nextLine();
        String email = sc.nextLine();

        System.out.print("ENTER PASSWORD : ");
        String password = sc.next();

        if (password.length() < 8) {
            System.err.println("Password must be at least 8 characters long.");
            System.exit(0);
        }

        dao.createAccount(email, password);
        System.out.println("Account Created...");

    }
}
