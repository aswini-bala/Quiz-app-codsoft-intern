import java.util.*;

public class QuizApp {
    static Scanner scanner = new Scanner(System.in);
    static int score = 0;
    static final int QUESTION_TIME_LIMIT = 10; // Time limit in seconds per question
    
    // Question class to store question, options, and correct answer
    static class Question {
        String questionText;
        String[] options;
        int correctAnswer;

        public Question(String questionText, String[] options, int correctAnswer) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    public static void main(String[] args) {
        // List of questions
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", 
            new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 3));
        questions.add(new Question("What is 2 + 2?", 
            new String[]{"1. 3", "2. 4", "3. 5", "4. 6"}, 2));
        questions.add(new Question("Which language is known as the language of the web?", 
            new String[]{"1. Java", "2. Python", "3. JavaScript", "4. C++"}, 3));

        // Start the quiz
        System.out.println("Welcome to the Quiz! You have " + QUESTION_TIME_LIMIT + " seconds for each question.");
        
        for (Question question : questions) {
            if (!askQuestion(question)) {
                System.out.println("Quiz ended due to time out.");
                System.exit(0);  // Exit the program immediately when time runs out
            }
        }
        
        // Show results
        showResults(questions.size());
    }

    // Ask a question, display options, and get the answer within the time limit
    public static boolean askQuestion(Question question) {
        System.out.println("\n" + question.questionText);
        for (String option : question.options) {
            System.out.println(option);
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up!");
                System.exit(0);  // Exit the program if the time limit is exceeded
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, QUESTION_TIME_LIMIT * 1000);

        System.out.print("Enter your answer (1-4): ");
        long startTime = System.currentTimeMillis();
        int answer = scanner.nextInt();
        long endTime = System.currentTimeMillis();

        timer.cancel();  // Stop the timer as user answered within the time

        if (endTime - startTime <= QUESTION_TIME_LIMIT * 1000) {
            if (answer == question.correctAnswer) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect.");
            }
            return true;  // Return true if question was answered within the time
        } else {
            System.out.println("You did not answer in time.");
            return false;  // Return false if time ran out
        }
    }

    // Display final score and results
    public static void showResults(int totalQuestions) {
        System.out.println("\nQuiz Completed!");
        System.out.println("Your Score: " + score + "/" + totalQuestions);
        
        if (score == totalQuestions) {
            System.out.println("Excellent! You got all answers right.");
        } else {
            System.out.println("Review the answers and try again to improve!");
        }
    }
}
