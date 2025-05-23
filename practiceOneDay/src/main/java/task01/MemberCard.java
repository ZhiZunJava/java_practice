package task01;

public class MemberCard {
    private int cardNumber;    // 会员卡号
    private double balance;    // 余额

    // 构造方法
    public MemberCard(int cardNumber, double balance) {
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    // 支付方法
    public void pay(double amount) throws Exception {
        if (amount <= 0) {
            throw new IllegalArgumentException("支付金额必须大于0");
        }
        if (amount > balance) {
            throw new Exception("余额不足，支付失败！");
        }
        balance -= amount;
        System.out.println("支付成功，剩余余额：" + balance);
    }

    // Getter
    public int getCardNumber() {
        return cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    // Setter（如果需要的话）
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
