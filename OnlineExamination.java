import java.util.*;

class User2 {
    private final String username;
    private final String password;
    private final String fullName;

    public User2(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
}

class Question2 {
    private final String questionText;
    private final List<String> options;
    private final int correctOptionIndex;

    public Question2(String questionText, List<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() { return questionText; }
    public List<String> getOptions() { return options; }
    public int getCorrectOptionIndex() { return correctOptionIndex; }
}

public class OnlineExamination {
    private static User currentUser;
    private static List<Question> questions = new ArrayList<>();
    private static List<Integer> selectedAnswers;
    private static Timer timer;
    private static int remainingTimeInSeconds = 1800;

    public static void main(String[] args) {
        initializeQuestions();
        login();
    }

    private static void initializeQuestions() {
        questions.add(new Question("Result of 34*34?", List.of("1152", "1154", "1156", "1166"), 2));
        questions.add(new Question("Which planet is the largest in solar system?", List.of("Earth", "Venus", "Saturn", "Jupiter"), 3));
        questions.add(new Question("What is the chemical symbol for gold?", List.of("P", "Au", "Cu", "He"), 1));
        questions.add(new Question("What is the gravitational pull of Earth?", List.of("10 m/s2", "9.8 m/s2", "1.62 m/s2", "12.54 m/s2"), 1));
        questions.add(new Question("Who painted the Mona Lisa?", List.of("Mr. Paul", "Aristotle", "Leonardo da Vinci", "James Vinci"), 2));
        questions.add(new Question("What is considered the Silicon Valley of India?", List.of("Mumbai", "Hyderabad", "Madurai", "Bengaluru"), 3));
        questions.add(new Question("Which is the largest country in the world?", List.of("China", "Russia", "India", "USA"), 1));
        questions.add(new Question("What is 5 + 5 * 5?", List.of("30", "35", "53", "10"), 0));
        questions.add(new Question("Who invented the telescope?", List.of("Isaac Newton", "Albert Einstein", "Galileo Galilei", "Aristotle"), 2));
        questions.add(new Question("Which country won the World Cup in 1983?", List.of("Australia", "England", "Pakistan", "India"), 3));
    }

    private static void login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        System.out.print("Enter your full name: ");
        String fullName = sc.nextLine();

        currentUser = new User(username, password, fullName);
        showMainMenu(sc);
    }

    private static void showMainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Start Exam");
            System.out.println("2. Update Profile");
            System.out.println("3. Change Password");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            switch (input) {
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
                    logout(scanner);
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-4.");
            }
        }
    }

    private static void startExam(Scanner scanner) {
        selectedAnswers = new ArrayList<>();
        remainingTimeInSeconds = 1800;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                remainingTimeInSeconds--;
                if (remainingTimeInSeconds % 60 == 0) {
                    System.out.println("\nRemaining Time: " + (remainingTimeInSeconds / 60) + " minutes");
                }
                if (remainingTimeInSeconds <= 0) {
                    autoSubmit();
                }
            }
        }, 1000, 1000);

        System.out.println("\n--- Exam Started (30 minutes) ---");

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + q.getQuestionText());
            List<String> opts = q.getOptions();
            for (int j = 0; j < opts.size(); j++) {
                System.out.println((j + 1) + ". " + opts.get(j));
            }

            int answer = -1;
            while (true) {
                System.out.print("Your answer (1-" + opts.size() + "): ");
                try {
                    answer = Integer.parseInt(scanner.nextLine());
                    if (answer >= 1 && answer <= opts.size()) break;
                } catch (Exception ignored) {}
                System.out.println("Invalid input. Try again.");
            }
            selectedAnswers.add(answer - 1);
        }
        autoSubmit();
    }

    private static void autoSubmit() {
        if (timer != null) {
            timer.cancel();
        }
        System.out.println("\nSubmitting your exam...");
        showResult();
    }

    private static void showResult() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (i >= selectedAnswers.size()) continue;
            if (selectedAnswers.get(i) == questions.get(i).getCorrectOptionIndex()) {
                score++;
            }
        }

        System.out.println("You scored: " + score + " out of " + questions.size());
    }

    private static void updateProfile(Scanner scanner) {
        System.out.print("Enter your new full name: ");
        String newFullName = scanner.nextLine();
        currentUser = new User(currentUser.getUsername(), currentUser.getPassword(), newFullName);
        System.out.println("Profile updated successfully.");
    }

    private static void changePassword(Scanner scanner) {
        System.out.print("Enter current password: ");
        String currPass = scanner.nextLine();
        if (currPass.equals(currentUser.getPassword())) {
            System.out.print("Enter new password: ");
            String newPass = scanner.nextLine();
            currentUser = new User(currentUser.getUsername(), newPass, currentUser.getFullName());
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Incorrect password.");
        }
    }

    private static void logout(Scanner scanner) {
        System.out.println("Thank you, " + currentUser.getFullName() + ". Logging out.");
    }
}
