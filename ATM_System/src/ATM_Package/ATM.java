package ATM_Package;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.plaf.synth.ColorType;
import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ATM implements ActionListener{
int number_of_trials;
ArrayList<String> AccountNames,AccountNumber,UserPIN,AvailableBalance;
String[] saved_data;
String username_loggedin;

JFrame insufficent_funds;
JFrame Starting_frame;
JFrame Home_frame;
JFrame Check_balance;
JFrame Withdraw;
JFrame preceipt;
JFrame changePin;
JFrame deposit_frame;
JFrame take_account_frame;
JFrame take_transfer_amount_frame;

JLabel welcome;
JLabel username;
JLabel Pincode;
JLabel trials;

JTextField user_text;
JTextField amount;
JTextField amount_dep;
JPasswordField Pin_text;

JButton Login;
JButton check_balance;
JButton withdraw;
JButton change_pin;
JButton deposit;
JButton transfer;
JButton log_out;
JButton Return_bal;
JButton Withdraw_button;
JButton Yes;
JButton No;
JButton Return_withdraw_error;
JButton Change;
JButton deposit_button;
JButton enter_account_button;
JButton enter_money_button;
 
ATM()
{
number_of_trials = 0;
AccountNames = new ArrayList<String>();
AccountNumber = new ArrayList<String>();
UserPIN = new ArrayList<String>();
AvailableBalance = new ArrayList<String>();

ReadFile();


welcome = new JLabel();
welcome.setText("Welcome to my ATM, please login");
welcome.setHorizontalTextPosition(JLabel.CENTER);
welcome.setBounds(100,10,200,100);
welcome.setForeground(Color.white);
welcome.setFont(new Font("MV Boli",Font.PLAIN,12));

trials = new JLabel();
trials.setText("Number of Trials Left:" + (3-number_of_trials));
trials.setHorizontalTextPosition(JLabel.CENTER);
trials.setBounds(125,250,200,100);
trials.setForeground(Color.green);
trials.setFont(new Font("MV Boli",Font.PLAIN,12));
trials.setVisible(false);

username = new JLabel();
username.setText("Username");
username.setBounds(80,100,200,100);
username.setHorizontalTextPosition(JLabel.CENTER);
username.setForeground(Color.white);
username.setFont(new Font("MV Boli",Font.PLAIN,12));

Pincode = new JLabel();
Pincode.setText("PinCode");
Pincode.setBounds(80,150,200,100);
Pincode.setHorizontalTextPosition(JLabel.CENTER);
Pincode.setForeground(Color.white);
Pincode.setFont(new Font("MV Boli",Font.PLAIN,12));

user_text = new JTextField();
user_text.setBounds(150,140,150,20);

Pin_text = new JPasswordField();
Pin_text.setBounds(150,190,150,20);

Login = new JButton("Login");
Login.setFocusable(false);
Login.setBackground(Color.green);
Login.setBounds(150,250,100,30);
Login.addActionListener(this);


Starting_frame = new JFrame();
Starting_frame.setTitle("ATM");
Starting_frame.getContentPane().setBackground(Color.black);
Starting_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
Starting_frame.setResizable(false);
Starting_frame.setSize(400,400);
Starting_frame.setVisible(true);
Starting_frame.setLayout(null);
Starting_frame.add(welcome);
Starting_frame.add(username);
Starting_frame.add(Pincode);
Starting_frame.add(user_text);
Starting_frame.add(Pin_text);
Starting_frame.add(Login);
Starting_frame.add(trials);
}

@Override
public void actionPerformed(ActionEvent e) {
if(e.getSource() == Login)
{
String entered_username = user_text.getText();
String entered_PIN = Pin_text.getText();
if(AccountNames.contains(entered_username) && (UserPIN.contains(entered_PIN)))
{
username_loggedin = entered_username;
Starting_frame.dispose();
HomePage();

}
else
{
number_of_trials++;
trials.setVisible(true);
trials.setText("Number of Trials Left:" + (3-number_of_trials));
if(number_of_trials == 3)
Starting_frame.dispose();
}
}
if(e.getSource() == check_balance)
{
Home_frame.dispose();
Check_balance();
}
if(e.getSource() == Return_bal)
{
Check_balance.dispose();
HomePage();
}
if(e.getSource() == log_out)
{
Home_frame.dispose();
ATM new_ATM = new ATM();
}
if(e.getSource() == withdraw)
{
Home_frame.dispose();
withdraw();
}
if(e.getSource() == Withdraw_button)
{
Withdraw.dispose();
String amount_string = amount.getText();
BigDecimal amount_entered = new BigDecimal(amount_string);
BigDecimal available_bal = new BigDecimal(AvailableBalance.get(AccountNames.indexOf(username_loggedin)));
if(amount_entered.compareTo(available_bal) == 1)
{
System.out.println("Insufficent funds");
error();
}
else
{
available_bal = available_bal.subtract(amount_entered);
String new_bal = available_bal.toString();
AvailableBalance.set(AccountNames.indexOf(username_loggedin), new_bal);


System.out.println(AccountNames);
System.out.println(AccountNumber);
System.out.println(UserPIN);
System.out.println(AvailableBalance);

WriteFile();
System.out.println("Withdrawn");
print_receipt();
}

}
if(e.getSource() == Yes)
{
preceipt.dispose();
HomePage();
}
if(e.getSource() == No)
{
preceipt.dispose();
HomePage();
}
if(e.getSource() == Return_withdraw_error)
{
insufficent_funds.dispose();
HomePage();
}
if(e.getSource()== change_pin)
{
Home_frame.dispose();
change_pin();
}
if(e.getSource() == Change)
{
changePin.dispose();
String newPin =amount.getText();
UserPIN.set(AccountNames.indexOf(username_loggedin), newPin);

System.out.println(AccountNames);
System.out.println(AccountNumber);
System.out.println(UserPIN);
System.out.println(AvailableBalance);


WriteFile();
HomePage();
}
if(e.getSource() ==deposit)
{
Home_frame.dispose();
deposit();

}
if(e.getSource() == deposit_button)
{
String deposit_am = amount_dep.getText();
BigDecimal dep = new BigDecimal(deposit_am);
BigDecimal bal = new BigDecimal(AvailableBalance.get(AccountNames.indexOf(username_loggedin)));
bal = bal.add(dep);
AvailableBalance.set(AccountNames.indexOf(username_loggedin), bal.toString());

System.out.println(AccountNames);
System.out.println(AccountNumber);
System.out.println(UserPIN);
System.out.println(AvailableBalance);

WriteFile();
deposit_frame.dispose();
print_receipt();
}
if(e.getSource() == transfer)
{
Home_frame.dispose();
take_accountname();
}
if(e.getSource()== enter_account_button)
{
String entered_account = amount.getText();
if(AccountNames.contains(entered_account))
{
take_account_frame.dispose();
enter_amount_transfer();
}
else
{
take_account_frame.dispose();
error();
}

}
if(e.getSource() == enter_money_button)
{
String number = amount.getText();
BigDecimal num = new BigDecimal(number);
BigDecimal available_bal = new BigDecimal(AvailableBalance.get(AccountNames.indexOf(username_loggedin)));
if(num.compareTo(available_bal) == 1)
{
System.out.println("Insufficent funds");
}
else
{
System.out.println("Worked");
}
}
}
public void enter_amount_transfer()
{
JLabel enter_amount = new JLabel();
enter_amount.setText("Enter Transfer amount");
enter_amount.setHorizontalTextPosition(JLabel.CENTER);
enter_amount.setBounds(60,10,300,100);
enter_amount.setForeground(Color.white);
enter_amount.setFont(new Font("MV Boli",Font.PLAIN,11));


amount = new JTextField();
amount.setBounds(110,100,150,20);

enter_money_button = new JButton("Enter");
enter_money_button.setFocusable(false);
enter_money_button.setBackground(Color.green);
enter_money_button.setBounds(110,200,150,30);
enter_money_button.addActionListener(this);

take_transfer_amount_frame = new JFrame();
take_transfer_amount_frame.setTitle("ATM");
take_transfer_amount_frame.getContentPane().setBackground(Color.black);
take_transfer_amount_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
take_transfer_amount_frame.setResizable(false);
take_transfer_amount_frame.setSize(400,400);
take_transfer_amount_frame.setVisible(true);
take_transfer_amount_frame.setLayout(null);
take_transfer_amount_frame.add(enter_amount);
take_transfer_amount_frame.add(amount);
take_transfer_amount_frame.add(enter_account_button);
}
public void take_accountname()
{
JLabel enter_account = new JLabel();
enter_account.setText("Enter Account Name who you wish to transfer to");
enter_account.setHorizontalTextPosition(JLabel.CENTER);
enter_account.setBounds(60,10,300,100);
enter_account.setForeground(Color.white);
enter_account.setFont(new Font("MV Boli",Font.PLAIN,11));


amount = new JTextField();
amount.setBounds(110,100,150,20);

enter_account_button = new JButton("Enter");
enter_account_button.setFocusable(false);
enter_account_button.setBackground(Color.green);
enter_account_button.setBounds(110,200,150,30);
enter_account_button.addActionListener(this);

take_account_frame = new JFrame();
take_account_frame.setTitle("ATM");
take_account_frame.getContentPane().setBackground(Color.black);
take_account_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
take_account_frame.setResizable(false);
take_account_frame.setSize(400,400);
take_account_frame.setVisible(true);
take_account_frame.setLayout(null);
take_account_frame.add(enter_account);
take_account_frame.add(amount);
take_account_frame.add(enter_account_button);
}
public void deposit()
{
JLabel enter_amount = new JLabel();
enter_amount.setText("Enter amount to deposit");
enter_amount.setHorizontalTextPosition(JLabel.CENTER);
enter_amount.setBounds(110,10,200,100);
enter_amount.setForeground(Color.white);
enter_amount.setFont(new Font("MV Boli",Font.PLAIN,12));

amount_dep = new JTextField();
amount_dep.setBounds(110,100,150,20);

deposit_button = new JButton("deposit");
deposit_button.setFocusable(false);
deposit_button.setBackground(Color.green);
deposit_button.setBounds(110,200,150,30);
deposit_button.addActionListener(this);

deposit_frame = new JFrame();
deposit_frame.setTitle("ATM");
deposit_frame.getContentPane().setBackground(Color.black);
deposit_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
deposit_frame.setResizable(false);
deposit_frame.setSize(400,400);
deposit_frame.setVisible(true);
deposit_frame.setLayout(null);
deposit_frame.add(enter_amount);
deposit_frame.add(amount_dep);
deposit_frame.add(deposit_button);
}
public void change_pin()
{

Change = new JButton("Change");
Change.setFocusable(false);
Change.setBackground(Color.green);
Change.setBounds(110,150,150,30);
Change.addActionListener(this);

amount = new JTextField();
amount.setBounds(110,100,150,20);


JLabel enter_pin = new JLabel();
enter_pin.setText("Enter new PIN");
enter_pin.setHorizontalTextPosition(JLabel.CENTER);
enter_pin.setBounds(125,10,200,100);
enter_pin.setForeground(Color.white);
enter_pin.setFont(new Font("MV Boli",Font.PLAIN,12));


changePin = new JFrame();
changePin.setTitle("ATM");
changePin.getContentPane().setBackground(Color.black);
changePin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
changePin.setResizable(false);
changePin.setSize(400,400);
changePin.setVisible(true);
changePin.setLayout(null);
changePin.add(Change);
changePin.add(enter_pin);
changePin.add(amount);
}
public void error()
{
JLabel error_message = new JLabel();
error_message.setText("Error");
error_message.setHorizontalTextPosition(JLabel.CENTER);
error_message.setBounds(110,10,200,100);
error_message.setForeground(Color.white);
error_message.setFont(new Font("MV Boli",Font.PLAIN,12));


Return_withdraw_error = new JButton("Return");
Return_withdraw_error.setFocusable(false);
Return_withdraw_error.setBackground(Color.green);
Return_withdraw_error.setBounds(110,200,150,30);
Return_withdraw_error.addActionListener(this);

insufficent_funds = new JFrame();
insufficent_funds.setTitle("ATM");
insufficent_funds.getContentPane().setBackground(Color.black);
insufficent_funds.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
insufficent_funds.setResizable(false);
insufficent_funds.setSize(400,400);
insufficent_funds.setVisible(true);
insufficent_funds.setLayout(null);
insufficent_funds.add(Return_withdraw_error);
insufficent_funds.add(error_message);


}
public void withdraw()
{
JLabel enter_amount = new JLabel();
enter_amount.setText("Enter amount to withdraw");
enter_amount.setHorizontalTextPosition(JLabel.CENTER);
enter_amount.setBounds(110,10,200,100);
enter_amount.setForeground(Color.white);
enter_amount.setFont(new Font("MV Boli",Font.PLAIN,12));

amount = new JTextField();
amount.setBounds(110,100,150,20);

Withdraw_button = new JButton("Withdraw");
Withdraw_button.setFocusable(false);
Withdraw_button.setBackground(Color.green);
Withdraw_button.setBounds(110,200,150,30);
Withdraw_button.addActionListener(this);

Withdraw = new JFrame();
Withdraw.setTitle("ATM");
Withdraw.getContentPane().setBackground(Color.black);
Withdraw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
Withdraw.setResizable(false);
Withdraw.setSize(400,400);
Withdraw.setVisible(true);
Withdraw.setLayout(null);
Withdraw.add(enter_amount);
Withdraw.add(amount);
Withdraw.add(Withdraw_button);
}
public void print_receipt()
{
JLabel enter_amount = new JLabel();
enter_amount.setText("Print a Receipt?");
enter_amount.setHorizontalTextPosition(JLabel.CENTER);
enter_amount.setBounds(125,10,200,100);
enter_amount.setForeground(Color.white);
enter_amount.setFont(new Font("MV Boli",Font.PLAIN,12));

Yes = new JButton("Yes");
Yes.setFocusable(false);
Yes.setBackground(Color.green);
Yes.setBounds(60,200,100,30);
Yes.addActionListener(this);

No = new JButton("No");
No.setFocusable(false);
No.setBackground(Color.green);
No.setBounds(200,200,100,30);
No.addActionListener(this);

preceipt = new JFrame();
preceipt.setTitle("ATM");
preceipt.getContentPane().setBackground(Color.black);
preceipt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
preceipt.setResizable(false);
preceipt.setSize(400,400);
preceipt.setVisible(true);
preceipt.setLayout(null);
preceipt.add(enter_amount);
preceipt.add(Yes);
preceipt.add(No);
}
public void Check_balance()
{
Return_bal = new JButton("Return");
Return_bal.setFocusable(false);
Return_bal.setBackground(Color.green);
Return_bal.setBounds(110,100,150,30);
Return_bal.addActionListener(this);

JLabel bal = new JLabel();
bal.setText("Balance: "+ AvailableBalance.get(AccountNames.indexOf(username_loggedin)));
bal.setHorizontalTextPosition(JLabel.CENTER);
bal.setBounds(125,10,200,100);
bal.setForeground(Color.white);
bal.setFont(new Font("MV Boli",Font.PLAIN,12));

Check_balance = new JFrame();
Check_balance.setTitle("ATM");
Check_balance.getContentPane().setBackground(Color.black);
Check_balance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
Check_balance.setResizable(false);
Check_balance.setSize(400,400);
Check_balance.setVisible(true);
Check_balance.setLayout(null);
Check_balance.add(bal);
Check_balance.add(Return_bal);
}
public void HomePage()

{
JLabel welcome_user = new JLabel();
welcome_user.setText("Welcome "+ username_loggedin);
welcome_user.setHorizontalTextPosition(JLabel.CENTER);
welcome_user.setBounds(125,10,200,100);
welcome_user.setForeground(Color.white);
welcome_user.setFont(new Font("MV Boli",Font.PLAIN,12));


check_balance = new JButton("Check Balance");
check_balance.setFocusable(false);
check_balance.setBackground(Color.green);
check_balance.setBounds(40,100,150,30);
check_balance.addActionListener(this);;

withdraw = new JButton("Withdraw Money");
withdraw.setFocusable(false);
withdraw.setBackground(Color.green);
withdraw.setBounds(210,100,150,30);
withdraw.addActionListener(this);


change_pin = new JButton("Change PIN");
change_pin.setFocusable(false);
change_pin.setBackground(Color.green);
change_pin.setBounds(40,150,150,30);
change_pin.addActionListener(this);

deposit = new JButton("Deposit Money");
deposit.setFocusable(false);
deposit.setBackground(Color.green);
deposit.setBounds(210,150,150,30);
deposit.addActionListener(this);


transfer = new JButton("Transfer Money");
transfer.setFocusable(false);
transfer.setBackground(Color.green);
transfer.setBounds(40,200,150,30);
transfer.addActionListener(this);


log_out = new JButton("Log out");
log_out.setFocusable(false);
log_out.setBackground(Color.green);
log_out.setBounds(210,200,150,30);
log_out.addActionListener(this);


Home_frame = new JFrame();
Home_frame.setTitle("ATM");
Home_frame.getContentPane().setBackground(Color.black);
Home_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
Home_frame.setResizable(false);
Home_frame.setSize(400,400);
Home_frame.setVisible(true);
Home_frame.setLayout(null);
Home_frame.add(welcome_user);
Home_frame.add(check_balance);
Home_frame.add(withdraw);
Home_frame.add(change_pin);
Home_frame.add(deposit);
Home_frame.add(transfer);
Home_frame.add(log_out);
}
public void ReadFile()
{
String data = new String();
File Input_file = new File("ATM.csv");
Scanner in;
try {
in = new Scanner (Input_file);
data = in.nextLine();
while(in.hasNext())
{

data = in.nextLine();
saved_data = data.split(",");
AccountNames.add(saved_data[1]);
AccountNumber.add(saved_data[2]);
UserPIN.add(saved_data[3]);
AvailableBalance.add(saved_data[4]);

}
} catch (FileNotFoundException e) {
e.printStackTrace();
}
}
public void WriteFile()
{
try {
FileWriter writer = new FileWriter("ATM.csv", false);
writer.write("Index" + ",");
writer.write("AccountNumber" + ",");
writer.write("UserPIN"+ ",");
writer.write("AvailableBalance" + System.lineSeparator());
for(int i = 0; i < AvailableBalance.size();i++)
{
writer.write(Integer.toString(i) + ",");
writer.write(AccountNames.get(i)+ ",");
writer.write(AccountNumber.get(i)+ ",");
writer.write(UserPIN.get(i)+ ",");
writer.write(AvailableBalance.get(i) + System.lineSeparator());
}
writer.close();
writer.flush();
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}

}




