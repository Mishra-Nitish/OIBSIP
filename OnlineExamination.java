import java.util.*;

class User {
    private final String username;
    private final String password;
    private final String fullName;

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
}

class Question {
    private final String questionText;
    private final List<String> options;
    private final int correctAnswerIndex;

    public Question(String questionText, List<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() { return questionText; }
    public List<String> getOptions() { return options; }
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }
}

public class OnlineExamination {

    private static User currentUser;
    private static List<Question> questionList = new ArrayList<>();
    private static List<Integer> selectedAnswers = new ArrayList<>();
    private static Timer examTimer;
    private static int timeLeft = 1800;
    private static boolean examOver = false;

    public static void main(String[] args) {
        loadQuestions();
        login();
    }

    private static void login() {
        Scanner userInput = new Scanner(System.in);

        System.out.println("<------------- Welcome to Online Exam System --------->");
        System.out.print("Enter username: ");
        String username = userInput.nextLine();

        System.out.print("Enter password: ");
        String password = userInput.nextLine();

        System.out.print("Enter full name: ");
        String fullName = userInput.nextLine();

        currentUser = new User(username, password, fullName);

        showMenu(userInput);
    }

    private static void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n-- Main Menu --");
            System.out.println("1. Start Exam");
            System.out.println("2. Update Profile");
            System.out.println("3. Change Password");
            System.out.println("4. Logout");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    startExam(scanner);
                    break;
                case "2":
                    updateProfile(scanner);
                    break;
                case "3":
                    changePassword(scanner);
                    break;
                case "4":
                    logout();
                    return;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    private static void loadQuestions() {
        questionList.add(new Question("What is 34 * 34?", List.of("1122", "1156", "1100", "1170"), 1));
        questionList.add(new Question("Which is the largest planet?", List.of("Earth", "Mars", "Jupiter", "Saturn"), 2));
        questionList.add(new Question("Symbol for Gold?", List.of("Gd", "Au", "Ag", "Go"), 1));
        questionList.add(new Question("Who painted Mona Lisa?", List.of("Van Gogh", "Picasso", "Leonardo da Vinci", "Da Vinci Code"), 2));
        questionList.add(new Question("Which country won 1983 WC?", List.of("India", "England", "Australia", "West Indies"), 0));
        questionList.add(new Question("Which gas do plants absorb from the atmosphere?", List.of("Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"), 2));
        questionList.add(new Question("Who is known as the Father of Computers?", List.of("Albert Einstein", "Isaac Newton", "Charles Babbage", "Alan Turing"), 2));
        questionList.add(new Question("What is the capital of Australia?", List.of("Sydney", "Melbourne", "Canberra", "Brisbane"), 2));
        questionList.add(new Question("Which is the smallest continent by land area?", List.of("Europe", "Australia", "Antarctica", "South America"), 1));
        questionList.add(new Question("Which blood type is considered a universal donor?", List.of("A", "B", "AB", "O Negative"), 3));
        questionList.add(new Question("How many bytes are there in 1 kilobyte (KB)?", List.of("1000", "1024", "512", "2048"), 1));
        questionList.add(new Question("Which Indian river is considered the holiest?", List.of("Ganga", "Yamuna", "Brahmaputra", "Godavari"), 0));
        questionList.add(new Question("Who discovered penicillin?", List.of("Marie Curie", "Alexander Fleming", "Louis Pasteur", "Gregor Mendel"), 1));
        questionList.add(new Question("What is the hardest natural substance?", List.of("Gold", "Iron", "Diamond", "Quartz"), 2));
        questionList.add(new Question("In which year did India gain independence?", List.of("1947", "1950", "1930", "1942"), 0));
    }

    private static void startExam(Scanner scanner) {
        selectedAnswers.clear();
        timeLeft = 1800;
        examOver = false;

        System.out.println("\nStarting exam... (Duration: 30 minutes)");

        examTimer = new Timer();
        examTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (examOver) {
                    examTimer.cancel();
                    return;
                }

                timeLeft--;

                if (timeLeft % 60 == 0 && timeLeft > 0) {
                    System.out.println("Time left: " + (timeLeft / 60) + " minutes");
                }

                if (timeLeft <= 0) {
                    submitExam();
                }
            }
        }, 1000, 1000);

        for (int i = 0; i < questionList.size(); i++) {
            Question q = questionList.get(i);
            System.out.println("\nQ" + (i + 1) + ": " + q.getQuestionText());

            List<String> opts = q.getOptions();
            for (int j = 0; j < opts.size(); j++) {
                System.out.println((j + 1) + ". " + opts.get(j));
            }

            int ans = -1;
            while (true) {
                System.out.print("Enter your answer (1-" + opts.size() + "): ");
                try {
                    ans = Integer.parseInt(scanner.nextLine());
                    if (ans >= 1 && ans <= opts.size()) break;
                } catch (Exception e) {

                }
                System.out.println("Invalid input. Please try again.");
            }
            selectedAnswers.add(ans - 1);
        }

        submitExam();
    }

    private static void submitExam() {
        if (examOver) return;
        examOver = true;

        if (examTimer != null) {
            examTimer.cancel();
        }
        System.out.println("\nExam submitted! Calculating results...");
        showResult();
    }

    // Display final score
    private static void showResult() {
        int score = 0;
        for (int i = 0; i < selectedAnswers.size(); i++) {
            if (selectedAnswers.get(i) == questionList.get(i).getCorrectAnswerIndex()) {
                score++;
            }
        }

        double percentage = (score * 100.0) / questionList.size();
        System.out.println("Your Score: " + score + "/" + questionList.size());
        System.out.println("Percentage: " + String.format("%.2f", percentage) + "%");

        if (percentage >= 40) {
            System.out.println("Result: PASS");
        } else {
            System.out.println("Result: FAIL");
        }
    }

    private static void updateProfile(Scanner scanner) {
        System.out.print("Enter new full name: ");
        String newName = scanner.nextLine();
        currentUser = new User(currentUser.getUsername(), currentUser.getPassword(), newName);
        System.out.println("Profile updated.");
    }

    private static void changePassword(Scanner scanner) {
        System.out.print("Enter current password: ");
        String oldPass = scanner.nextLine();

        if (oldPass.equals(currentUser.getPassword())) {
            System.out.print("Enter new password: ");
            String newPass = scanner.nextLine();
            currentUser = new User(currentUser.getUsername(), newPass, currentUser.getFullName());
            System.out.println("Password changed.");
        } else {
            System.out.println("Wrong password! Try again.");
        }
    }

    private static void logout() {
        System.out.println("Goodbye, " + currentUser.getFullName() + "! You've been logged out.");
    }
}
