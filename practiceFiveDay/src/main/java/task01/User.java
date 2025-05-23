package task01;

public class User {
    private String u_login_name;
    private String u_login_pwd;
    private String u_name;
    private String u_wallet;
    private java.util.Date save_money_time;
    private java.util.Date draw_money_time;

    // Getters and Setters
    public String getU_login_name() { return u_login_name; }
    public void setU_login_name(String u_login_name) { this.u_login_name = u_login_name; }
    public String getU_login_pwd() { return u_login_pwd; }
    public void setU_login_pwd(String u_login_pwd) { this.u_login_pwd = u_login_pwd; }
    public String getU_name() { return u_name; }
    public void setU_name(String u_name) { this.u_name = u_name; }
    public String getU_wallet() { return u_wallet; }
    public void setU_wallet(String u_wallet) { this.u_wallet = u_wallet; }
    public java.util.Date getSave_money_time() { return save_money_time; }
    public void setSave_money_time(java.util.Date save_money_time) { this.save_money_time = save_money_time; }
    public java.util.Date getDraw_money_time() { return draw_money_time; }
    public void setDraw_money_time(java.util.Date draw_money_time) { this.draw_money_time = draw_money_time; }
}