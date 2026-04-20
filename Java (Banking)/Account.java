import java.util.ArrayList;
import java.util.List;


public abstract class Account implements BankOperations {
    private String accountNumber;
    private String accountHolder;
    private String password;
    private double balance; 
    private List<Transaction> history; 

    public Account(String accountNumber, String accountHolder, String password, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.password = password;
        this.balance = initialBalance;
        this.history = new ArrayList<>();
        
        if (initialBalance > 0) {
            recordTransaction("CREDIT (Initial Deposit)", initialBalance);
        }
    }


    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    
    protected void adjustBalance(double amount) {
        this.balance += amount;
    }

    protected void recordTransaction(String type, double amount) {
        history.add(new Transaction(type, amount, this.balance));
    }


    @Override
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be strictly greater than zero.");
        }
        adjustBalance(amount);
        recordTransaction("CREDIT", amount);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be strictly positive.");
        }
        if (amount > this.balance) {
            throw new InsufficientFundsException("Cannot withdraw $" + amount + ". Available balance is $" + this.balance + ".");
        }
        
        adjustBalance(-amount); 
        recordTransaction("DEBIT", amount);
    }


    @Override
    public void showHistory() {
        System.out.println("\n--- Transaction History for " + accountHolder + " ---");
        
        int totalTransactions = history.size();
        
        if (totalTransactions == 0) {
            System.out.println("No transactions found.");
            return;
        }
        
        int displayCount = Math.min(5, totalTransactions);
        int start = totalTransactions - displayCount;
        
        System.out.println("Showing last " + displayCount + " of " + totalTransactions + " transactions:");
        
        for (int i = start; i < totalTransactions; i++) {
            System.out.println(history.get(i).toString());
        }
        System.out.println("--------------------------------------------------");
    }

    public abstract void applyMonthlyFees();
}