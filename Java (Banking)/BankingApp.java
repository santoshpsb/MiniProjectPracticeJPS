import java.util.InputMismatchException;
import java.util.Scanner;


public class BankingApp {

    private static BankService bankService = new BankService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n MAIN MENU ");
            System.out.println("1. Create Savings Account");
            System.out.println("2. Login to Account");
            System.out.println("3. Process Global Monthly Fees ");
            System.out.println("4. Exit System");
            System.out.print("Select an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        handleCreateAccount();
                        break;
                    case 2:
                        handleLogin();
                        break;
                    
                    case 3:
                        bankService.processMonthlyEnd();
                        break;
                    case 4:
                        System.out.println("Thank you!");
                        scanner.close();
                        System.exit(0);
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number");
                scanner.nextLine(); 
            }
        }
    }


    private static void handleCreateAccount() {
        System.out.print("Enter a new Account Number: ");
        String accNum = scanner.nextLine();
        
        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Create a Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter Initial Deposit Amount: $");
        double deposit = scanner.nextDouble();
        scanner.nextLine();
        
        System.out.print("Enter Annual Interest Rate : ");
        double interestRate = scanner.nextDouble();
        scanner.nextLine();
        
        bankService.createSavingsAccount(accNum, name, password, deposit, interestRate);
    }

    private static void handleLogin() {
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        try {
            Account activeAccount = bankService.login(accNum, password);
            System.out.println("\nLogin Successful! Welcome, " + activeAccount.getAccountHolder());
            
            showAccountMenu(activeAccount);

        } catch (AuthException e) {
            System.out.println("LOGIN FAILED: " + e.getMessage());
        }
    }

    private static void showAccountMenu(Account account) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n  DASHBOARD ");
            System.out.println("Current Balance: $" + String.format("%.2f", account.getBalance()));
            System.out.println("1. Deposit Funds");
            System.out.println("2. Withdraw Funds");
            System.out.println("3. Transfer Funds");
            System.out.println("4. View Transaction History");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.print("Enter deposit amount: $");
                        double depAmount = scanner.nextDouble();
                        account.deposit(depAmount); 
                        System.out.println("Deposit successful!");
                        break;

                    case 2:
                        System.out.print("Enter withdrawal amount: $");
                        double withAmount = scanner.nextDouble();
                        account.withdraw(withAmount); 
                        System.out.println("Withdrawal successful!");
                        break;

                    case 3:
                        System.out.print("Enter destination Account Number: ");
                        String receiverAcc = scanner.nextLine();
                        System.out.print("Enter transfer amount: $");
                        double transAmount = scanner.nextDouble();
                        bankService.transferMoney(account, receiverAcc, transAmount);
                        break;

                    case 4:
                        account.showHistory();
                        break;

                    case 5:
                        System.out.println("Logging out...");
                        loggedIn = false;
                        break;

                    default:
                        System.out.println("Invalid selection.");
                }
            } catch (InvalidAmountException | InsufficientFundsException e) {
                
                System.out.println("TRANSACTION ERROR: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scanner.nextLine(); 
            }
        }
    }
}