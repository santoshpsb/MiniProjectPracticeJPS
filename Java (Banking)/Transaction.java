import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Transaction {
    private String type; 
    private double amount;
    private double finalBalance;
    private LocalDateTime timestamp;

    public Transaction(String type, double amount, double finalBalance) {
        this.type = type;
        this.amount = amount;
        this.finalBalance = finalBalance;
        this.timestamp = LocalDateTime.now(); 
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s: $%.2f | Balance: $%.2f", 
            timestamp.format(formatter), type, amount, finalBalance);
    }
}