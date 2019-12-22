package ua.nure.kn.herasymov.usermanagement_me.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ua.nure.kn.herasymov.usermanagement_me.User;
import ua.nure.kn.herasymov.usermanagement_me.db.DaoFactory;
import ua.nure.kn.herasymov.usermanagement_me.db.UserDao;
import ua.nure.kn.herasymov.usermanagement_me.util.Messages;

public class MainFrame extends JFrame {
	
	private static final int FRAME_HEIGHT = 600;
	private static final int FRAME_WIDTH = 800;
	private JPanel contentPanel;
	private JPanel browsePanel;
	private AddPanel addPanel;
	private EditPanel editPanel;
	private DeletePanel deletePanel;
	private DetailsPanel detailsPanel;
	private UserDao dao;

	public MainFrame() {
		super();
		dao = DaoFactory.getInstance().getUserDao();
		initialize();
	}
	
	public UserDao getDao() {
		return dao;
	}

	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle(Messages.getString("MainFrame.user_management_me")); //$NON-NLS-1$
		this.setContentPane(getConentPanel());
	}


	private JPanel getConentPanel() {
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
		}
		return contentPanel;
	}

	private JPanel getBrowsePanel() {
		if (browsePanel == null) {
			browsePanel = new BrowsePanel(this);
		}
		((BrowsePanel) browsePanel).initTable();
		return browsePanel;
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	public void showAddPanel() {
		showPanel(getAddPanel());
		
	}

	private void showPanel(JPanel jPanel) {
		getContentPane().add(jPanel, BorderLayout.CENTER);
		jPanel.setVisible(true);
		jPanel.repaint();
		
	}

	private AddPanel getAddPanel() {
		if (addPanel == null) {
			addPanel = new AddPanel(this);
		}
		return addPanel;
	}

	private EditPanel getEditPanel() {
		if (editPanel == null) {
			editPanel = new EditPanel(this, null);
		}
		return editPanel;
	}
	
	private DeletePanel getDeletePanel() {
		if (deletePanel == null) {
			deletePanel = new DeletePanel(this, null);
		}
		return deletePanel;
	}
	
	private DetailsPanel getDetailsPanel() {
		// TODO Auto-generated method stub
		if (detailsPanel == null) {
			detailsPanel = new DetailsPanel(this, null);
		}
		return detailsPanel;
	}
	
	public void showEditPanel(int selectedRow) {
		showPanel(getEditPanel());
	}
	
	public void showDeletePanel(int selectedRow) {
		showPanel(getDeletePanel());
	}
	
	public void showDetailsPanel(int selectedRow) {
		// TODO Auto-generated method stub
		showPanel(getDetailsPanel());
	}
	
	public void showBrowsePanel() { 
		showPanel(getBrowsePanel());
	}

	
}
