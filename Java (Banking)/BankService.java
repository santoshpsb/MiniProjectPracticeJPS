import java.util.HashMap;
import java.util.Map;


public class BankService {
    
    private Map<String, Account> accountDatabase;

    public BankService() {
        this.accountDatabase = new HashMap<>();
    }

    
    public void createSavingsAccount(String accNum, String name, String password, double initialDeposit, double interestRate) {
        String normalizedAccNum = accNum.toUpperCase();
        if (accountDatabase.containsKey(normalizedAccNum)) {
            System.out.println("Error: Account number already exists.");
            return;
        }

        if (initialDeposit <= 0) {
            System.out.println("Creation Failed: Initial deposit must be strictly greater than zero.");
            return;
        }

        if (interestRate < 0) {
            System.out.println("Creation Failed: Interest rate cannot be negative.");
            return;
        }

        Account newAccount = new SavingsAccount(accNum, name, password, initialDeposit, interestRate);
        accountDatabase.put(accNum, newAccount);
        System.out.println("Savings Account successfully created for " + name + "!");
    }

    
    public Account login(String accNum, String password) throws AuthException {
        Account account = accountDatabase.get(accNum);
        
        if (account == null) {
            throw new AuthException("Account not found.");
        }
        if (!account.verifyPassword(password)) {
            throw new AuthException("Incorrect password.");
        }
        
        return account; 
    }

    
    public void processMonthlyEnd() {
        System.out.println("\n--- Processing End of Month Fees/Interest ---");
        
        for (Account acc : accountDatabase.values()) {
            acc.applyMonthlyFees(); 
        }
        System.out.println("All accounts updated successfully.");
    }
    
    
    public void transferMoney(Account sender, String receiverAccNum, double amount) {
        Account receiver = accountDatabase.get(receiverAccNum);
        
        if (receiver == null) {
            System.out.println("Transfer Failed: Receiver account not found.");
            return;
        }
        
        try {
            sender.withdraw(amount);
            receiver.deposit(amount);
            System.out.println("Successfully transferred $" + amount + " to " + receiver.getAccountHolder());
            
        } catch (InsufficientFundsException | InvalidAmountException e) {
            System.out.println("Transfer Failed: " + e.getMessage());
        }
    }
    

}