package register_form.gui_register;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class UserRegistration extends JFrame {
	protected static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	protected JTextField firstname;
	protected JTextField lastname;
	protected JTextField email;
	protected JTextField username;
	protected JTextField mobilenumber;
	protected JPasswordField passwordField;
	protected JButton btn;
	protected passwordUtil passUtil = new passwordUtil();
	protected DAOsearch search = new DAOsearch();
	protected List<String> searchResult = new ArrayList<String>();

	/**
	 *   applicationの立ち上げ
	 *
	 */
	public static void main(String[] args) {
		//スレッド並行処理
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserRegistration frame = new UserRegistration();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * panelの設定
	 */
	public UserRegistration() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//ラベルの追加
		JLabel lblNewUserRegister = new JLabel("New Register Form");
		lblNewUserRegister.setFont(new Font("Times New Roman", Font.PLAIN, 35));
		lblNewUserRegister.setBounds(362, 52, 325, 50);
		contentPane.add(lblNewUserRegister);

		JLabel lblName = new JLabel("First Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblName.setBounds(58, 152, 99, 43);
		contentPane.add(lblName);

		JLabel lblNewLabel = new JLabel("Last Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(58, 243, 110, 29);
		contentPane.add(lblNewLabel);

		JLabel lblEmailAddress = new JLabel("Email");
		lblEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEmailAddress.setBounds(58, 324, 124, 36);
		contentPane.add(lblEmailAddress);

		JLabel lblUsername = new JLabel("User Name");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setBounds(542, 159, 99, 29);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(542, 245, 99, 24);
		contentPane.add(lblPassword);

		JLabel lblMobileNumber = new JLabel("Mobile Number");
		lblMobileNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMobileNumber.setBounds(542, 329, 139, 26);
		contentPane.add(lblMobileNumber);

		//追加したラベルに入力欄を設定
		firstname = new JTextField();
		firstname.setFont(new Font("Tahoma", Font.PLAIN, 32));
		firstname.setBounds(214, 151, 228, 50);
		contentPane.add(firstname);
		firstname.setColumns(10);

		lastname = new JTextField();
		lastname.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lastname.setBounds(214, 235, 228, 50);
		contentPane.add(lastname);
		lastname.setColumns(10);

		email = new JTextField();
		email.setFont(new Font("Tahoma", Font.PLAIN, 32));
		email.setBounds(214, 320, 228, 50);
		contentPane.add(email);
		email.setColumns(10);

		username = new JTextField();
		username.setFont(new Font("Tahoma", Font.PLAIN, 32));
		username.setBounds(707, 151, 228, 50);
		contentPane.add(username);
		username.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		passwordField.setBounds(707, 235, 228, 50);
		contentPane.add(passwordField);

		mobilenumber = new JTextField();
		mobilenumber.setFont(new Font("Tahoma", Font.PLAIN, 32));
		mobilenumber.setBounds(707, 320, 228, 50);
		contentPane.add(mobilenumber);
		mobilenumber.setColumns(10);

		//buttonの設定
		btn = new JButton("Register");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstName = firstname.getText();
				String lastName = lastname.getText();
				String emailId = email.getText();
				String userName = username.getText();
				String mobileNumber = mobilenumber.getText();
				int len = mobileNumber.length();
				char[] password = passwordField.getPassword();
				String pass = new String(password);
				//passwordの暗号化
				String sha = passUtil.execute(pass);

				String msg = "" + firstName;
				msg += " \n";
				if (len > 11) {
					JOptionPane.showMessageDialog(btn, "ハイフンなしで入力してください");
					mobilenumber.setText("");
					return;
				} else if (len < 11) {
					JOptionPane.showMessageDialog(btn, "11桁の電話番号を入力してください");
					mobilenumber.setText("");
					return;
				}

				//DB接続 パスワードがすでにあるか確認
				try {
					searchResult = search.findAll();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				for (String rs : searchResult) {
					if (rs.equals(sha)) {
						JOptionPane.showMessageDialog(btn, "別のパスワードにしてください");
						passwordField.setText("");
						return;
					} else {

					}
				}
				//DBに追加
				try {
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/register_form",
							"", "");

					String query = "INSERT INTO account values('" + firstName + "','" + lastName + "','" + userName
							+ "','" +
							sha + "','" + emailId + "','" + mobileNumber + "')";

					Statement sta = connection.createStatement();
					int x = sta.executeUpdate(query);
					if (x == 0) {
						JOptionPane.showMessageDialog(btn, "エラーが発生しました");
						return;
					} else {
						JOptionPane.showMessageDialog(btn, msg + "さん、アカウント作成が完了しました");
					}
					connection.close();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
		btn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btn.setBounds(399, 447, 259, 74);
		contentPane.add(btn);
	}
}
