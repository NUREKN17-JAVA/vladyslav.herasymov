package ua.nure.kn.herasymov.usermanagement_me.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn.herasymov.usermanagement_me.User;
import ua.nure.kn.herasymov.usermanagement_me.db.DaoFactory;
import ua.nure.kn.herasymov.usermanagement_me.db.DatabaseException;
import ua.nure.kn.herasymov.usermanagement_me.db.UserDao;

public class DeletePanel extends JPanel implements ActionListener {

  private MainFrame parent;
  private JPanel buttonPanel;
  private JButton cancelButton;
  private JButton okButton;
  private User user;
  private UserDao userDao;
  private JLabel sureText;
  
  public DeletePanel(MainFrame parent, User usr) {
    this.parent = parent;
    initialize(usr);
  }
  
  private void initialize(User usr) {
    // TODO Auto-generated method stub
    this.setName("deletePanel");
    this.setLayout(new BorderLayout());
    this.add(getTextPanel(), BorderLayout.NORTH);
    this.add(getButtonPanel(), BorderLayout.SOUTH);
    userDao = DaoFactory.getInstance().getUserDao();
    this.user = usr;
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
  
  private JPanel getTextPanel() {
    // TODO Auto-generated method stub
    if (buttonPanel == null) {
      buttonPanel = new JPanel();
      buttonPanel.add(getSureText(), null);
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
  
  private JLabel getSureText() {
    // TODO Auto-generated method stub
    if (sureText == null) {
      sureText = new JLabel();
      sureText.setText("Are you sure to delete this user?");
      sureText.setName("sureText");
    }
    return sureText;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    if ("ok".equalsIgnoreCase(e.getActionCommand())) {
      try {
        userDao.delet(this.user);
      } catch (DatabaseException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }
    this.setVisible(false);
    parent.showBrowsePanel();
  }
}
