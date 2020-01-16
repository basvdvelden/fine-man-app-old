package nl.management.finance.app.ui.transactions;

public class TransactionView {
    private String bookingDate;
    private String debtorName;
    private String creditorName;
    private String amount;
    private String description;
    private String initiatingParty;

    public TransactionView(String bookingDate, String debtorName, String amount, String description,
                           String initiatingParty, String creditorName) {
        this.bookingDate = bookingDate;
        this.debtorName = debtorName;
        this.amount = amount;
        this.description = description;
        this.initiatingParty = initiatingParty;
        this.creditorName = creditorName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getInitiatingParty() {
        return initiatingParty;
    }

    public String getCreditorName() {
        return creditorName;
    }
}
