package ua.nure.kn.herasymov.usermanagement_me.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn.herasymov.usermanagement_me.User;
import ua.nure.kn.herasymov.usermanagement_me.db.DaoFactory;
import ua.nure.kn.herasymov.usermanagement_me.db.DatabaseException;
import ua.nure.kn.herasymov.usermanagement_me.db.UserDao;
import ua.nure.kn.herasymov.usermanagement_me.util.Messages;

public class EditPanel extends JPanel implements ActionListener {
	private MainFrame parent;
	private JPanel buttonPanel;
	private JPanel fieldPanel;
	private JButton cancelButton;
	private JButton okButton;
	private JTextField dayOfBirthField;
	private JTextField lastNameField;
	private JTextField firstNameField;
	private Color bgColor;
	private User user;
	private UserDao userDao;
	
	public EditPanel(MainFrame parent, User usr) {
		this.parent = parent;
		initialize(usr);
	}

	private void initialize(User usr) {
		// TODO Auto-generated method stub
		this.setName("editPanel");
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(), BorderLayout.NORTH);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
		userDao = DaoFactory.getInstance().getUserDao();
		this.user = usr;
		setFields();
	}
	private JPanel getButtonPanel() {
		// TODO Auto-generated method stub
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton(), null);
			buttonPanel.add(getCancelButton(), null);
		}
		return buttonPanel;
	}

	private JButton getCancelButton() {
		// TODO Auto-generated method stub
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.setName("cancelButton");
			cancelButton.setActionCommand("cancel");
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}

	private JButton getOkButton() {
		// TODO Auto-generated method stub
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("Ok");
			okButton.setName("okButton");
			okButton.setActionCommand("ok");
			okButton.addActionListener(this);
		}
		return okButton;
	}

	private JPanel getFieldPanel() {
		// TODO Auto-generated method stub
		if (fieldPanel == null) {
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new GridLayout(2, 3));
			addLabeledField(fieldPanel, "Name", getFirstNameField());
			addLabeledField(fieldPanel, "Surname", getLastNameField());
			addLabeledField(fieldPanel, Messages.getString("AddPanel.day_of_birth"), getDateOfBirthField());
		}
		return fieldPanel;
	}

	private JTextField getDateOfBirthField() {
		// TODO Auto-generated method stub
		if (dayOfBirthField == null) {
			dayOfBirthField = new JTextField();
			dayOfBirthField.setName("dayOfBirthField");
		}
		return dayOfBirthField;
	}

	private JTextField getLastNameField() {
		// TODO Auto-generated method stub
		if (lastNameField == null) {
			lastNameField = new JTextField();
			lastNameField.setName("lastNameField");
		}
		return lastNameField;
	}

	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
		// TODO Auto-generated method stub
		JLabel label = new JLabel(labelText);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);
	}

	private JTextField getFirstNameField() {
		// TODO Auto-generated method stub
		if (firstNameField == null) {
			firstNameField = new JTextField();
			firstNameField.setName("firstNameField");
		}
		return firstNameField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("ok".equalsIgnoreCase(e.getActionCommand())) {
			user.setFirstName(getFirstNameField().getText());
			user.setLastName(getLastNameField().getText());
			DateFormat format = DateFormat.getDateInstance();
			try {
				user.setDateOfBirthd(format.parse(getDateOfBirthField().getText()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				getDateOfBirthField().setBackground(Color.RED);
				return;
			}
			try {
				parent.getDao().update(user);
			} catch (DatabaseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		this.setVisible(false);
		parent.showBrowsePanel();
	}

	private void setFields() {
		// TODO Auto-generated method stub
		getFirstNameField().setText(this.user.getFirstName());
		getFirstNameField().setBackground(bgColor);
		
		getLastNameField().setText(this.user.getLastName());
		getLastNameField().setBackground(bgColor);
		
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		getDateOfBirthField().setText(formatter.format(this.user.getDateOfBirthd()));
		getDateOfBirthField().setBackground(bgColor);
	}
}
