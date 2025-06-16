import java.util.ArrayList;
import java.util.Scanner;

class atmInterface {
    private static ArrayList<String> transactionsHistory = new ArrayList<>();
    private static int balance = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Hello welcome to ATM ");
        System.out.print("Enter your User ID: ");
        sc.nextLine();
        System.out.print(" Enter your User PIN: ");
        sc.nextLine();

        System.out.println("you are login sucessfully");
        mainMenu();
    }

    private static void mainMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMain Menu:");
            System.out.println("1.Transactions History");
            System.out.println("2.Withdraw");
            System.out.println("3.Deposit");
            System.out.println("4.Transfer");
            System.out.println("5.Quit");
            System.out.print("Enter your choice from main menu: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1: tranHistory();
                        break;
                case 2: withdrawExecute();
                        break;
                case 3: depositExecute();
                        break;
                case 4: transferExecute();
                        break;
                case 5: System.out.println("Thank you for using the ATM! and visit again");
                        break;
                default:System.out.println("It's Invalid! Please enter a valid option.");
            }
        } while (choice != 5);
    }

    private static void tranHistory() {
        System.out.println("\nTransaction History:");
        for (String transaction : transactionsHistory) {
            System.out.println(transaction);
        }
    }

    private static void withdrawExecute() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter amount to withdraw: ");
        int amount = sc.nextInt();

        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionsHistory.add("Withdraw: " + amount);
            System.out.println("Withdrawal successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }

    private static void depositExecute() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter amount to deposit: ");
        int amount = sc.nextInt();

        if (amount > 0) {
            balance += amount;
            transactionsHistory.add("Deposit: " + amount);
            System.out.println("Deposit successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    private static void transferExecute() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter recipient's account number: ");
        String recipientAccount = sc.nextLine();

        System.out.print("Enter amount to transfer: ");
        int amount = sc.nextInt();

        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionsHistory.add("Transfer: " + amount + " to " + recipientAccount);
            System.out.println("Transfer successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }
}