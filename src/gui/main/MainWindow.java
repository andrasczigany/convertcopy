package gui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import convertcopy.ConvertCopy;
import gui.Settings;
import gui.selection.view.ListsPanel;

public class MainWindow extends JFrame implements Settings {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel mainPanel = null;
	private ListsPanel listsPanel = null;
	private JPanel buttonsPanel = null;
	private JButton copyButton = null;
	private JTextField destField = null;
	private JLabel freeSizeLabel = null;
	private JCheckBox convertCheckBox = null;
	private JCheckBox overwriteCheckBox = null;
	private JLabel selectedLabel = null;
	private JLabel estimatedLabel = null;
	private JTextField resultField = null;
	private ConvertCopy convertCopy = null;

	/**
	 * This is the default constructor
	 */

	public MainWindow() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ie) {
		}
		JFrame.setDefaultLookAndFeelDecorated(true);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(WINDOW_SIZE);
		this.setLocation(WINDOW_POS_X, WINDOW_POS_Y);
		this.setContentPane(getJContentPane());
		this.setTitle(TITLE);
		this.pack();
		
		getListsPanel().getToTree().setMainWindow(this);
	}

	private ConvertCopy getConvertCopy() {
		if (convertCopy == null)
			convertCopy = new ConvertCopy();
		return convertCopy;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getMainPanel(), java.awt.BorderLayout.NORTH); // Generated
		}
		return jContentPane;
	}

	/**
	 * This method initializes mainPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			mainPanel.add(getListsPanel(), java.awt.BorderLayout.CENTER); // Generated
			mainPanel.add(getButtonsPanel(), java.awt.BorderLayout.SOUTH); // Generated
		}
		return mainPanel;
	}

	/**
	 * This method initializes listsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	public ListsPanel getListsPanel() {
		if (listsPanel == null) {
			listsPanel = new ListsPanel();
		}
		return listsPanel;
	}

	/**
	 * This method initializes buttonsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));
			buttonsPanel.add(getCopyButton());
			buttonsPanel.add(getDestField());
			buttonsPanel.add(getFreeSizeLabel());
			buttonsPanel.add(getOverWriteCheckBox());
			buttonsPanel.add(getConvertCheckBox());
			buttonsPanel.add(getSelectedLabel());
			buttonsPanel.add(getEstimatedLabel());
			buttonsPanel.add(new JLabel("Result: "));
			buttonsPanel.add(getResultField());
			setResultField(Color.WHITE, "");
		}
		return buttonsPanel;
	}

	private JButton getCopyButton() {
		if (copyButton == null) {
			copyButton = new JButton("Copy to");
			copyButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (getListsPanel().getToTree().getNodeFiles().size() == 0) return;
					new MyWorker().execute();
				}

			});
		}
		return copyButton;
	}

	class MyWorker extends SwingWorker<Void, String> {
		@Override
		protected Void doInBackground() throws Exception {
			try {
				getMainPanel().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				publish("Start");
				int[] errors = getConvertCopy().convertCopy(getListsPanel().getToTree().getNodeFiles(),
						getDestField().getText(), getListsPanel().getToTree().getRemovedItems(), getConvertCheckBox().isSelected(),
						getOverWriteCheckBox().isSelected());
				publish(errors[0] == 0 && errors[1] == 0 ? "OK" : "NOK");
			} finally {
				getMainPanel().setCursor(Cursor.getDefaultCursor());
			}
			return null;
		}

		@Override
		protected void process(List<String> chunks) {
			for (String c : chunks) {
				switch(c) {
				case "Start":
					setResultField(Color.WHITE, "...");
					break;
				case "OK": {
					setResultField(Color.GREEN, "completed");
					getFreeSizeLabel().setText("Available: " + NumberFormat.getInstance().format(getAvailableSpace()) + " MB");
					break;
				}
				case "NOK":
					setResultField(Color.RED, "error");
					break;
				}
			}
		}
	}

	private JTextField getDestField() {
		if (destField == null) {
			destField = new JTextField(Settings.DEFAULT_TARGET_FOLDER, 12);
			destField.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					getFreeSizeLabel().setText("Available: " + NumberFormat.getInstance().format(getAvailableSpace()) + " MB");
					getEstimatedLabel().setText("After convert&copy: " + NumberFormat.getInstance().format(getAvailableSpace() - selectedSize) + " MB");
				}
				
				@Override
				public void focusGained(FocusEvent e) {
				}
			});
		}
		return destField;
	}

	private JCheckBox getConvertCheckBox() {
		if (convertCheckBox == null) {
			convertCheckBox = new JCheckBox("Convert", true);
		}
		return convertCheckBox;
	}

	private JCheckBox getOverWriteCheckBox() {
		if (overwriteCheckBox == null) {
			overwriteCheckBox = new JCheckBox("Overwrite", true);
		}
		return overwriteCheckBox;
	}
	
	private JLabel getFreeSizeLabel() {
		if (freeSizeLabel == null) {
			freeSizeLabel = new JLabel("Available: " + NumberFormat.getInstance().format(getAvailableSpace()) + " MB");
		}
		return freeSizeLabel;
	}
	
	private long getAvailableSpace() {
		try {
			return Files.getFileStore(Paths.get(getDestField().getText())).getUnallocatedSpace() / 1024 / 1024;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private JLabel getSelectedLabel() {
		if (selectedLabel == null) {
			selectedLabel = new JLabel("Selected: 0 MB");
		}
		return selectedLabel;
	}
	private long selectedSize = 0; 
	public void setSelectedSize(long size) {
		selectedSize = size;
		getSelectedLabel().setText("Selected: " + Long.toString(size) + " MB");
		getEstimatedLabel().setText("After convert&copy: " + NumberFormat.getInstance().format(getAvailableSpace() - size) + " MB");
	}
	
	private JLabel getEstimatedLabel() {
		if (estimatedLabel == null) {
			estimatedLabel = new JLabel("After convert&copy: " + NumberFormat.getInstance().format(getAvailableSpace()) + " MB");
		}
		return estimatedLabel;
	}
	
	private JTextField getResultField() {
		if (resultField == null) {
			resultField = new JTextField("");
			resultField.setEditable(false);
			resultField.setPreferredSize(RESULT_FIELD_SIZE);
		}
		return resultField;
	}

	private void setResultField(Color background, String text) {
		if (background != null) getResultField().setBackground(background);
		getResultField().setText(text);
	}

	public static void main(String[] args) {
		MainWindow application = new MainWindow();
		application.setVisible(true);
	}
}
