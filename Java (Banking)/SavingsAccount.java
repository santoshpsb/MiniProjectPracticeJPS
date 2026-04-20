public class SavingsAccount extends Account {
    
    private double interestRate; 

    public SavingsAccount(String accountNumber, String accountHolder, String password, double initialBalance, double interestRate) {
        super(accountNumber, accountHolder, password, initialBalance);
        this.interestRate = interestRate;
    }

    @Override
    public void applyMonthlyFees() {
        double interest = getBalance() * (interestRate / 100);
        
        if (interest > 0) {
            adjustBalance(interest);
            recordTransaction("CREDIT (Monthly Interest)", interest);
        }
    }
    
    
}