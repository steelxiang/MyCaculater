package 计算器;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class MyCalculator extends JFrame {
	JFrame frame = new JFrame("计算器");
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	JTextField txt = new JTextField();
	JButton[] bu = new JButton[19];
	String[] str = { "CE", "C", "DEL", "+", "7", "8", "9", "-", "4", "5", "6", "*", "1", "2", "3", "/", ".", "0", "=" };

	public void addCom(Component com) {
		gbl.setConstraints(com, gbc);
		frame.add(com);
	}

	public void init() {
		for (int i = 0; i < 19; i++) {
			bu[i] = new JButton(str[i]);
			bu[i].setFont(new Font("宋体", Font.BOLD, 30));
		}
		frame.setLayout(gbl);
		;
		frame.setSize(400, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		txt.setHorizontalAlignment(JTextField.RIGHT);
		txt.setFont(new Font("宋体", Font.BOLD, 30));
		addCom(txt);
		gbc.gridwidth = 1;
		for (int i = 0; i < 18; i++) {
			if ((i) % 4 == 3) {
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				addCom(bu[i]);
				gbc.gridwidth = 1;
			} else {
				addCom(bu[i]);
			}
		}
		gbc.gridwidth = 2;
		addCom(bu[18]);

	}

	/**
	 * 每个按钮按下的输入；
	 */
	public void type() {
		txt.addKeyListener(new KeyAdapter() { // 回车键等同=按钮
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					String txt4 = String.valueOf(calcu(txt.getText()));
					txt.setText(txt4);
					System.out.println(txt4);
				}
			}
		});

		for (int i = 0; i < 19; i++) {
			String s = str[i];
			if (i == 0) {
				bu[i].addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						String txt1 = deletLastInput(txt.getText());
						txt.setText(txt1);
					}

				});
			} else if (i == 2) {
				bu[i].addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						String txt2 = deletLastnum(txt.getText());
						txt.setText(txt2);
					}

				});
			} else if (i == 1) {
				bu[i].addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						txt.setText(null);
					}

				});
			} else if (i == 18) {
				bu[i].addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						String txt3 = String.valueOf(calcu(txt.getText()));
						txt.setText(txt3);
					}

				});
			} else if (i == 3 || i == 7 || i == 11 || i == 15) {
				bu[i].addActionListener(new ActionListener() { // 运算符不能连续出现；
					public void actionPerformed(ActionEvent e) {
						if (txt.getText().endsWith("/") || txt.getText().endsWith("-") || txt.getText().endsWith("*")
								|| txt.getText().endsWith(".") || txt.getText().endsWith("+")) {
							return;
						} else {
							txt.setText(txt.getText() + s);
						}

					}
				});
			} else if (i == 16) {
				bu[i].addActionListener(new ActionListener() { // 数字开头不能出现小数点，同一个数字不能出现两个小数点

					@Override
					public void actionPerformed(ActionEvent e) {

						String[] str = txt.getText().split("/|\\*|-|\\+");

						if (str[str.length - 1].contains(".") || txt.getText().isEmpty()) {
							return;
						} else if (txt.getText().endsWith("/") || txt.getText().endsWith("-")
								|| txt.getText().endsWith("*") || txt.getText().endsWith(".")
								|| txt.getText().endsWith("+")) {
							return;

						} else
							txt.setText(txt.getText() + s);
					}
				});
			} else {
				bu[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						txt.setText(txt.getText() + s);
					}
				});
			}
		}
	}

	public String deletLastInput(String str) { // 删除上次输入
		String[] s = str.split("/|\\*|-|\\+");
		String s1;
		if (s.length <= 2) {
			s1 = str;
		} else {
			s1 = str.substring(0, str.length() - s[s.length - 1].length() - 1);
		}

		return s1;

	}

	public String deletLastnum(String str) { // 删除最后个数字
		String s2 = null;
		if (str.length() == 0) {
			// s2="不能再删了";
		} else {
			s2 = new String(str.getBytes(), 0, str.length() - 1);
		}
		;
		return s2;
	}

	public double calcu(String str) { // 算出文本框中的结果；
		/*
		 * 先将文本分割，得到要计算的数组； 在通过角标找到运算符， 判断运算符 计算出结果
		 */
		String[] number = str.split("/|\\*|-|\\+");
		double[] num = new double[number.length];
		for (int i = 0; i < number.length; i++) { // 将字符转成 数字；
			num[i] = Double.valueOf(number[i]);
		}
		double result = num[0];
		int k = -1;
		for (int i = 0; i < number.length - 1; i++) {
			k += number[i].length() + 1;
			if (str.charAt(k) == '/') {
				result = result / num[i + 1];

			} else if (str.charAt(k) == '*') {
				result = result * num[i + 1];

			} else if (str.charAt(k) == '-') {
				result = result - num[i + 1];

			} else if (str.charAt(k) == '+') {
				result = result + num[i + 1];

			}

		}

		return result;
	}

	public static void main(String[] agrs) {
		MyCalculator ca = new MyCalculator();
		ca.init();
		ca.type();

	}
}
