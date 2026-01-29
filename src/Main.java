import java.util.Scanner;

// Abstract class for Account (Abstraction)
abstract class Account {
    protected String accNumber;
    protected String accHolderName;
    protected double balance;

    public Account(String accNumber, String accHolderName, double balance) {
        this.accNumber = accNumber;
        this.accHolderName = accHolderName;
        this.balance = balance;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public String getAccHolderName() {
        return accHolderName;
    }

    public double getBalance() {
        return balance;
    }

    // Abstract methods
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    public abstract void displayAccountInfo();
}

// Savings Account (Inheritance)
class SavingsAccount extends Account {
    private double interestRate = 0.03;

    public SavingsAccount(String accNumber, String accHolderName, double balance) {
        super(accNumber, accHolderName, balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited ₹" + amount);
        } else {
            System.out.println("Invalid amount!");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn ₹" + amount);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    @Override
    public void displayAccountInfo() {
        System.out.println("Savings Account - " + accNumber + ", Holder: " + accHolderName + ", Balance: ₹" + balance);
    }
}

// Current Account (Inheritance)
class CurrentAccount extends Account {
    private double overdraftLimit = 5000;

    public CurrentAccount(String accNumber, String accHolderName, double balance) {
        super(accNumber, accHolderName, balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited ₹" + amount);
        } else {
            System.out.println("Invalid amount!");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance + overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrawn ₹" + amount);
        } else {
            System.out.println("Overdraft limit exceeded!");
        }
    }

    @Override
    public void displayAccountInfo() {
        System.out.println("Current Account - " + accNumber + ", Holder: " + accHolderName + ", Balance: ₹" + balance);
    }
}

// Main Banking System
class BankingSystem {
    static Scanner sc = new Scanner(System.in);
    static Account[] accounts = new Account[10]; // store up to 10 accounts
    static int accCount = 0;

    // Helper method to find account by number
    static Account findAccount(String accNumber) {
        for (int i = 0; i < accCount; i++) {
            if (accounts[i].getAccNumber().equals(accNumber)) {
                return accounts[i];
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int choice = 0;

        while (choice != 6) {
            System.out.println("\n🏦 BANKING MANAGEMENT SYSTEM");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Funds");
            System.out.println("5. View Account Info");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    transferFunds();
                    break;
                case 5:
                    viewAccountInfo();
                    break;
                case 6:
                    System.out.println("Thank you for using our bank!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void createAccount() {
        if (accCount >= accounts.length) {
            System.out.println("Account limit reached!");
            return;
        }

        System.out.print("Enter Account Number: ");
        String accNo = sc.next();
        System.out.print("Enter Account Holder Name: ");
        String name = sc.next();
        System.out.print("Enter Initial Deposit: ");
        double balance = sc.nextDouble();

        System.out.println("Select Account Type: 1. Savings  2. Current");
        int type = sc.nextInt();

        if (type == 1) {
            accounts[accCount++] = new SavingsAccount(accNo, name, balance);
        } else if (type == 2) {
            accounts[accCount++] = new CurrentAccount(accNo, name, balance);
        } else {
            System.out.println("Invalid account type!");
        }
        System.out.println("Account created successfully!");
    }

    static void depositMoney() {
        System.out.print("Enter Account Number: ");
        String accNo = sc.next();
        Account acc = findAccount(accNo);

        if (acc != null) {
            System.out.print("Enter amount to deposit: ");
            double amt = sc.nextDouble();
            acc.deposit(amt);
        } else {
            System.out.println("Account not found!");
        }
    }

    static void withdrawMoney() {
        System.out.print("Enter Account Number: ");
        String accNo = sc.next();
        Account acc = findAccount(accNo);

        if (acc != null) {
            System.out.print("Enter amount to withdraw: ");
            double amt = sc.nextDouble();
            acc.withdraw(amt);
        } else {
            System.out.println("Account not found!");
        }
    }

    static void transferFunds() {
        System.out.print("Enter Source Account Number: ");
        String srcNo = sc.next();
        System.out.print("Enter Destination Account Number: ");
        String destNo = sc.next();

        Account src = findAccount(srcNo);
        Account dest = findAccount(destNo);

        if (src != null && dest != null) {
            System.out.print("Enter amount to transfer: ");
            double amt = sc.nextDouble();
            if (amt > 0 && amt <= src.getBalance()) {
                src.withdraw(amt);
                dest.deposit(amt);
                System.out.println("Transfer successful!");
            } else {
                System.out.println("Insufficient balance!");
            }
        } else {
            System.out.println("Invalid account(s)!");
        }
    }

    static void viewAccountInfo() {
        System.out.print("Enter Account Number: ");
        String accNo = sc.next();
        Account acc = findAccount(accNo);

        if (acc != null) {
            acc.displayAccountInfo();
        } else {
            System.out.println("Account not found!");
        }
    }
}
