/**
* @author Manojkumar V
*/
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
public class Dictionary implements KeyListener {
	public static JFrame window = new JFrame();
	private static JFrame window2 = new JFrame();
	private static JLabel label = new JLabel("Enter the word to find the meaning");
	private static JLabel label2 = new JLabel("Enter password");
	private static JLabel label3 = new JLabel();
	private static JLabel label4 = new JLabel();
	private static JPasswordField password = new JPasswordField();
	private static JButton ok = new JButton("Ok");
	private static JButton ok2 = new JButton("Ok");
	private static JButton ok3 = new JButton("Ok");
	private static JButton ok4 = new JButton("Delete");
	private static JButton ok5 = new JButton("Find");
	private static JButton ok6 = new JButton("Replace");
	private static JButton back = new JButton("Back");
	private static JButton cancel = new JButton("Cancel");
	private static JButton word[] = new JButton[10];
	private static JPanel panel = new JPanel();
	private static JPanel panel2 = new JPanel();
	private static JTextField input = new JTextField();
	private static JTextField atext1 = new JTextField();
	private static JTextField atext2 = new JTextField();
	private static JTextArea atext3 = new JTextArea();
	private static JButton find = new JButton("Find");
	private static JTextArea output = new JTextArea();
	private static JMenu file = new JMenu("File");
	private static JMenuItem update = new JMenuItem("Update");
	private static JMenuItem exit = new JMenuItem("Exit");
	private static JMenuBar bar = new JMenuBar();
	private static JRadioButton add = new JRadioButton("Add");
	private static JRadioButton delete = new JRadioButton("Remove");
	private static JRadioButton change = new JRadioButton("Update");
	private static ButtonGroup group = new ButtonGroup();
	private static JScrollPane scroll1 = new JScrollPane();
	private static int choice = 0;
	private static String in , adminin, adminmean, adminin1, adminin2, pass = "";
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static Connection connection = null;
	static final String DATABASE_URL = "jdbc:mysql://localhost/dictionary";
	public static void meaning() {
		for (int i = 0; i < word.length; i++) {
			word[i].setBounds(-20, -20, 0, 0);
		}
		try {
			output.setText("");
			output.setBounds(60, 350, 700, 265);
			statement = connection.createStatement();
			String s = "SELECT meaning FROM data where word='" + input.getText() + "'";
			resultSet = statement.executeQuery(s);
			String answer = "";
			Font font;
			font = new Font(answer, Font.PLAIN, 15);
			output.setFont(font);
			if (resultSet.next()) {
				answer = "" + resultSet.getObject(1);
				output.setText(answer);
			} else {
				output.setText("Sorry Entered Word not found");
			}
			if (input.getText().length() < 1) {
				output.setText("");
				output.setBounds(-1000, -1000, 5, 1);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
	public static void wordaction(String str) {
		input.setText(str);
		for (int i = 0; i < word.length; i++) {
			word[i].setBounds(-20, -20, 0, 0);
		}
		try {
			output.setText("");
			output.setBounds(60, 350, 700, 265);
			statement = connection.createStatement();
			String s = "SELECT meaning FROM data where word='" + str + "'";
			resultSet = statement.executeQuery(s);
			String answer = "";
			Font font;
			font = new Font(answer, Font.PLAIN, 15);
			output.setFont(font);
			while (resultSet.next()) {
				answer = "" + resultSet.getObject(1);
				output.setText(answer);
			}
		} catch (SQLException sqlException) {
		}
	}
	public void keyReleased(KeyEvent e) {

		for (int i = 0; i < word.length; i++) {
			word[i].setBounds(-20, -20, 0, 0);
		}
		if (e.getKeyCode() != 10) {
			if (input.getText().length() > 0) {
				String s = "SELECT word FROM data where word like'" + input.getText() + "%'";
				try {
					statement = connection.createStatement();
					resultSet = statement.executeQuery(s);
					ResultSetMetaData metaData = resultSet.getMetaData();
					int numberOfColumns = metaData.getColumnCount();
					int j = 0;
					String answer = "";
					int height = 70;
					while (resultSet.next()) {
						for (int i = 1; i <= numberOfColumns; i++) {
							answer = "" + resultSet.getObject(i);
							word[j].setBounds(60, height, 600, 20);
							word[j].setText(answer);
							height = height + 20;
							j++;
						}
						if (j >= 10) {
							break;
						}
					}
				} catch (SQLException sqlException) {
				}
			}
		}
	}
	public void keyTyped(KeyEvent e) {}

	public static void main(String args[]) {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, "DBUserName", "DBPassword");
		} catch (Exception e) {}
		int height = 70;
		for (int i = 0; i < word.length; i++) {
			word[i] = new JButton();
			panel.add(word[i]);
			word[i].setBackground(Color.LIGHT_GRAY);
		}
		Dictionary eventHandler = new Dictionary();
		input.setFocusable(true);
		input.addKeyListener(eventHandler);
		group.add(add);
		group.add(delete);
		group.add(change);
		window.setContentPane(panel);
		panel.setLayout(null);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.add(label);
		panel.add(input);
		panel.add(output);
		output.setBackground(Color.LIGHT_GRAY);
		panel.add(find);
		panel2.setLayout(null);
		panel2.add(label2);
		panel2.add(label3);
		panel2.add(label4);
		panel2.add(ok);
		panel2.add(ok2);
		panel2.add(ok3);
		panel2.add(ok4);
		panel2.add(ok5);
		panel2.add(ok6);
		panel2.add(back);
		panel2.add(cancel);
		panel2.add(password);
		panel2.add(add);
		panel2.add(delete);
		panel2.add(change);
		panel2.add(atext1);
		panel2.add(atext2);
		panel2.add(atext3);
		find.setToolTipText("Click to find the meaning");
		label.setBounds(60, 15, 200, 20);
		input.setBounds(60, 40, 600, 30);
		find.setBounds(700, 40, 60, 30);
		output.setLineWrap(true);
		atext3.setLineWrap(true);
		output.setEditable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1300, 700);
		window.setVisible(true);
		window.setResizable(false);
		window.setLocation(10, 15);
		window.setJMenuBar(bar);
		bar.add(file);
		file.add(update);
		file.add(exit);
		file.setMnemonic('F');
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label2.setText("Enter password");
				window2.setContentPane(panel2);
				label2.setBounds(20, 10, 200, 30);
				password.setBounds(20, 50, 260, 30);
				ok.setBounds(20, 130, 80, 25);
				cancel.setBounds(200, 130, 80, 25);
				window2.setResizable(false);
				window2.setVisible(true);
				window2.setSize(300, 200);
				window2.setLocation(540, 250);
				ok2.setBounds(-20, 20, 0, 0);
				label3.setBounds(-20, -20, 0, 0);
				label4.setBounds(-20, -20, 0, 0);
				add.setBounds(-20, -20, 0, 0);
				delete.setBounds(-20, -20, 0, 0);
				change.setBounds(-20, -20, 0, 0);
				atext1.setBounds(-20, -20, 0, 0);
				atext2.setBounds(-20, -20, 0, 0);
				atext3.setBounds(-20, -20, 0, 0);
				back.setBounds(-20, -20, 0, 0);
				ok3.setBounds(-20, -20, 0, 0);
				ok4.setBounds(-20, -20, 0, 0);
				ok5.setBounds(-20, -20, 0, 0);
				ok6.setBounds(-20, -20, 0, 0);
				password.setText("");
				atext1.setEditable(true);
				atext2.setEditable(true);
				atext3.setEditable(true);
			}
		});

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label3.setText("");
				password.setText("");
				ok.setBounds(-30, 0, 0, 0);
				label2.setText("Select your Option");
				label2.setBounds(20, 10, 200, 30);
				ok2.setBounds(200, 50, 80, 25);
				cancel.setBounds(200, 120, 80, 25);
				add.setBounds(20, 50, 100, 20);
				delete.setBounds(20, 90, 100, 20);
				change.setBounds(20, 130, 100, 20);
				atext1.setEditable(true);
				atext2.setEditable(true);
				atext3.setEditable(true);
				label3.setBounds(-20, -20, 0, 0);
				label4.setBounds(-20, -20, 0, 0);
				atext1.setBounds(-20, -20, 0, 0);
				atext2.setBounds(-20, -20, 0, 0);
				atext3.setBounds(-20, -20, 0, 0);
				back.setBounds(-20, -20, 0, 0);
				ok3.setBounds(-20, -20, 0, 0);
				ok4.setBounds(-20, -20, 0, 0);
				ok5.setBounds(-20, -20, 0, 0);
				ok6.setBounds(-20, -20, 0, 0);
			}
		});

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window2.dispose();
			}
		});

		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label3.setText("");
				label3.setBounds(-20, -20, 0, 0);
				pass = password.getText();
				int check = 0;
				char originalpassword[] = {
					'm', 'a', 'n', 'o', 'j'
				};
				char passarray[] = pass.toCharArray();
				try {
					for (int i = 0; i < pass.length(); i++) {
						if (passarray[i] != originalpassword[i]) {
							check = 1;
						}
					}
					if (check == 1 || pass.length() != 5) {
						label3.setBounds(20, 80, 260, 30);
						label3.setText("Wrong Password!");
						password.setText("");
					} else if (check == 0 && pass.length() == 5) {
						label3.setText("");
						label3.setBounds(-20, 0, 0, 0);
						password.setText("");
						password.setBounds(-10, 0, 0, 0);
						ok.setBounds(-30, 0, 0, 0);
						label2.setText("Select your Option");
						ok2.setBounds(200, 50, 80, 25);
						cancel.setBounds(200, 120, 80, 25);
						add.setBounds(20, 50, 100, 20);
						delete.setBounds(20, 90, 100, 20);
						change.setBounds(20, 130, 100, 20);
					}
				} catch (Exception ext) {
					label3.setText("");
					label3.setBounds(-20, 0, 0, 0);
					if (pass.length() != 5) {
						label3.setBounds(20, 80, 260, 30);
						label3.setText("Wrong Password!");
						password.setText("");
					}
				}
			}
		});

		password.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (password.getText().length() > 0) {
					label3.setText("");
					label3.setBounds(-20, -20, 0, 0);
					pass = password.getText();
					int check = 0;
					char originalpassword[] = {
						'm', 'a', 'n', 'o', 'j'
					};
					char passarray[] = pass.toCharArray();
					try {
						for (int i = 0; i < pass.length(); i++) {
							if (passarray[i] != originalpassword[i]) {
								check = 1;
							}
						}
						if (check == 1 || pass.length() != 5) {
							label3.setBounds(20, 80, 260, 30);
							label3.setText("Wrong Password!");
							password.setText("");
						} else if (check == 0 && pass.length() == 5) {
							label3.setText("");
							label3.setBounds(-20, 0, 0, 0);
							password.setText("");
							password.setBounds(-10, 0, 0, 0);
							ok.setBounds(-30, 0, 0, 0);
							label2.setText("Select your Option");
							ok2.setBounds(200, 50, 80, 25);
							cancel.setBounds(200, 120, 80, 25);
							add.setBounds(20, 50, 100, 20);
							delete.setBounds(20, 90, 100, 20);
							change.setBounds(20, 130, 100, 20);
						}
					} catch (Exception ext) {
						label3.setText("");
						label3.setBounds(-20, 0, 0, 0);
						if (pass.length() != 5) {
							label3.setBounds(20, 80, 260, 30);
							label3.setText("Wrong Password!");
							password.setText("");
						}
					}
				}
			}
		});


		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choice = 1;
			}
		});
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choice = 2;
			}
		});
		change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choice = 3;
			}
		});

		ok2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (choice != 0) {
					add.setBounds(-10, 0, 0, 0);
					delete.setBounds(-10, 0, 0, 0);
					change.setBounds(-10, 0, 0, 0);
					ok2.setBounds(-10, 0, 0, 0);
				}
				if (choice == 1) {
					label2.setBounds(10, 5, 240, 15);
					label2.setText("Enter word");
					label3.setBounds(10, 55, 240, 15);
					label3.setText("Enter meaning");
					atext1.setBounds(20, 25, 250, 25);
					atext3.setBounds(20, 75, 250, 55);
					ok3.setBounds(20, 140, 75, 25);
					back.setBounds(105, 140, 75, 25);
					cancel.setBounds(190, 140, 80, 25);
					atext1.setText("");
					atext3.setText("");
				} else if (choice == 2) {
					label2.setText("Enter word to be deleted");
					label2.setBounds(20, 20, 240, 15);
					atext1.setBounds(20, 45, 240, 25);
					label3.setText("");
					label3.setBounds(20, 70, 240, 25);
					ok4.setBounds(20, 130, 75, 25);
					back.setBounds(105, 130, 75, 25);
					cancel.setBounds(190, 130, 80, 25);
					atext1.setText("");
					atext3.setText("");
				} else if (choice == 3) {
					label2.setText("Enter old word");
					label2.setBounds(20, 5, 150, 15);
					atext1.setBounds(20, 20, 240, 25);
					atext2.setBounds(-20, -20, 0, 0);
					label3.setText("");
					label3.setBounds(140, 5, 110, 15);
					ok5.setBounds(20, 140, 75, 25);
					back.setBounds(110, 140, 75, 25);
					cancel.setBounds(195, 140, 80, 25);
					atext1.setText("");
					atext2.setText("");
					atext3.setText("");
				}
			}
		});

		ok3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					statement = connection.createStatement();
					String s1 = "SELECT word FROM data where word='" + atext1.getText() + "'";
					resultSet = statement.executeQuery(s1);
					if (resultSet.next()) {
						JOptionPane.showMessageDialog(null, "Entered word already exists", "Cannot update", JOptionPane.PLAIN_MESSAGE);
						atext1.setText("");
						atext3.setText("");
					} else if (atext1.getText().length() < 1 || atext3.getText().length() < 3) {
						JOptionPane.showMessageDialog(null, "Enter proper word and meaning to update", "Improper Input", JOptionPane.PLAIN_MESSAGE);
						atext1.setText("");
						atext3.setText("");
					} else {
						String s2 = "INSERT INTO DATA(word,meaning) values('" + atext1.getText() + "','" + atext3.getText() + "');";
						statement.execute(s2);
						JOptionPane.showMessageDialog(null, "Database Updated!", "Success", JOptionPane.PLAIN_MESSAGE);
						atext1.setText("");
						atext3.setText("");
					}
				} catch (SQLException sqlException) {
					atext1.setText("");
					atext3.setText("");
				}
			}
		});


		ok4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					statement = connection.createStatement();
					String s3 = "SELECT word FROM data where word='" + atext1.getText() + "'";
					resultSet = statement.executeQuery(s3);
					if (resultSet.next()) {
						int reply = JOptionPane.showConfirmDialog(null, "Are You Sure. Do you want to remove " + atext1.getText() + " from the database", "Delete?", JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							String s4 = "DELETE FROM data where word='" + atext1.getText() + "';";
							statement.execute(s4);
							atext1.setText("");
							JOptionPane.showMessageDialog(null, "Entered word deleted!", "Deleted", JOptionPane.PLAIN_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Entered word not found!", "Improper Input", JOptionPane.PLAIN_MESSAGE);
						atext1.setText("");
					}
				} catch (SQLException sqlException) {
					atext1.setText("");
				}
			}
		});

		ok5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					statement = connection.createStatement();
					String s5 = "SELECT word FROM data where word='" + atext1.getText() + "'";
					resultSet = statement.executeQuery(s5);
					if (resultSet.next()) {
						ok5.setBounds(-20, -20, 0, 0);
						atext1.setBounds(20, 20, 100, 25);
						atext1.setEditable(false);
						atext2.setBounds(125, 20, 150, 25);
						atext2.setEditable(true);
						label3.setText("Enter new word");
						atext3.setBounds(20, 65, 250, 70);
						label4.setBounds(20, 45, 300, 20);
						label4.setText("Change the meaning here");
						ok6.setBounds(20, 140, 80, 25);
					} else {
						JOptionPane.showMessageDialog(null, "Entered word not found!", "Improper Input", JOptionPane.PLAIN_MESSAGE);
						atext1.setText("");
					}
				} catch (SQLException sqlException) {
					atext1.setEditable(true);
					atext1.setText("");
					sqlException.printStackTrace();
				}
			}
		});

		ok6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					statement = connection.createStatement();
					String s6 = "DELETE FROM data where word='" + atext1.getText() + "';";
					statement.execute(s6);
					String s7 = "SELECT word FROM data where word='" + atext2.getText() + "'";
					resultSet = statement.executeQuery(s7);
					if (resultSet.next()) {
						JOptionPane.showMessageDialog(null, "Entered word already exists", "Cannot update", JOptionPane.PLAIN_MESSAGE);
						atext2.setText("");
						atext3.setText("");
					} else if (atext2.getText() == "" || atext3.getText().length() < 3) {
						JOptionPane.showMessageDialog(null, "Enter proper word and meaning to update", "Improper Input", JOptionPane.PLAIN_MESSAGE);
						atext2.setText("");
						atext3.setText("");
					} else {
						String s8 = "INSERT INTO DATA(word,meaning) values('" + atext2.getText() + "','" + atext3.getText() + "');";
						statement.execute(s8);
						ok6.setBounds(-20, -20, 0, 0);
						ok5.setBounds(20, 140, 75, 25);
						JOptionPane.showMessageDialog(null, "Database Updated!", "Success", JOptionPane.PLAIN_MESSAGE);
						atext1.setText("");
						atext2.setText("");
						atext3.setText("");
						label2.setText("Enter old word");
						label2.setBounds(20, 5, 150, 15);
						atext1.setBounds(20, 20, 240, 25);
						atext1.setEditable(true);
						atext3.setBounds(-20, -20, 0, 0);
						label3.setBounds(-20, -20, 0, 0);
						label4.setBounds(-20, -20, 0, 0);
						atext2.setBounds(-20, -20, 0, 0);
						label3.setText("");
						label3.setBounds(140, 5, 110, 15);
					}
				} catch (SQLException sqlException) {
					atext2.setText("");
					atext3.setText("");
					sqlException.printStackTrace();
				}
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meaning();
			}
		});

		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meaning();
			}
		});

		word[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordaction(word[0].getText());
			}
		});
		word[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordaction(word[1].getText());
			}
		});
		word[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordaction(word[2].getText());
			}
		});
		word[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordaction(word[3].getText());
			}
		});
		word[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordaction(word[4].getText());
			}
		});
		word[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordaction(word[5].getText());
			}
		});
		word[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordaction(word[6].getText());
			}
		});
		word[7].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordaction(word[7].getText());
			}
		});
		word[8].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordaction(word[8].getText());
			}
		});
		word[9].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordaction(word[9].getText());
			}
		});

	}
}
