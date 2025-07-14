import java.util.ArrayList;
import java.util.Scanner;

public class AtmInterface {
    private static final ArrayList<String> transactionsHistory = new ArrayList<>();
    private static int balance = 0;
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to ATM");
        System.out.print("Enter your User ID: ");
        String userId = sc.nextLine();

        System.out.print("Enter your User PIN: ");
        String pin = sc.nextLine();

        // Verification
        if (userId.equals("user123") && pin.equals("1234")) {
            System.out.println("Login Successful.");
            mainMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void mainMenu() {
        int choice;

        do {
            System.out.println("\nMain Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: showTransactionHistory(); break;
                case 2: withdraw(); break;
                case 3: deposit(); break;
                case 4: transfer(); break;
                case 5: System.out.println("Thank you for using the ATM."); break;
                default: System.out.println("Invalid option. Try again.");
            }
        } while (choice != 5);
    }

    private static void showTransactionHistory() {
        if (transactionsHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("\nTransaction History:");
            for (String transaction : transactionsHistory) {
                System.out.println(transaction);
            }
        }
    }

    private static void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        int amount = sc.nextInt();
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionsHistory.add("Withdraw: ₹" + amount);
            System.out.println("Withdrawal successful. Current balance: ₹" + balance);
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }

    private static void deposit() {
        System.out.print("Enter amount to deposit: ");
        int amount = sc.nextInt();
        if (amount > 0) {
            balance += amount;
            transactionsHistory.add("Deposit: ₹" + amount);
            System.out.println("Deposit successful. Current balance: ₹" + balance);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    private static void transfer() {
        sc.nextLine();
        System.out.print("Enter recipient's account number: ");
        String recipientAccount = sc.nextLine();
        System.out.print("Enter amount to transfer: ");
        int amount = sc.nextInt();

        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionsHistory.add("Transfer: ₹" + amount + " to account " + recipientAccount);
            System.out.println("Transfer successful. Current balance: ₹" + balance);
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }
}