/**
 * 
 */
package pete.study.practice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

/**
 * @author Wenping
 * This class includes the main method for the whole projects.
 */
class SetupDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	private static String shapeList [] = {"Line", "Circle"};
	private static String testTypeList [] = {"no restriction", "To Left", "To Right", "Stay Center"};
	private ShootingGame topFrame;
	
	private JLabel emptyLabel;
	private JRadioButton isRandomRadioBtn;
	private JRadioButton isLeftRadioBtn;
	private JRadioButton isRightRadioBtn;
	private ButtonGroup clickRBG;
	private JLabel styleLabel;
	private JComboBox<String> styleCB;
	private JLabel shapeLabel;
	private JComboBox<String> shapeCB;
	
	private JLabel lengthBoundLabel;
	private JPanel lengthBoundPanel;
	private JTextField lowTxt;
	private JLabel toLabel;
	private JTextField upTxt;
	private JTextField lengthTxt;

	private JLabel numLabel;
	private JTextField numTxt;
	private JButton okBtn;
	private JButton cancelBtn;
	private JPanel setupPanel;
	
	public SetupDialog (ShootingGame topFrame, String title) {
		super(title);
		this.topFrame = topFrame;
		emptyLabel = new JLabel("    ");
		isRandomRadioBtn = new JRadioButton("Random In Group");
		isRandomRadioBtn.setSelected(true);
		isLeftRadioBtn = new JRadioButton("Mouse Left Click");
		isRightRadioBtn = new JRadioButton("Mouse Right Click");
		clickRBG = new ButtonGroup();
		clickRBG.add(isLeftRadioBtn);
		clickRBG.add(isRightRadioBtn);
		isLeftRadioBtn.setSelected(true);
		styleLabel = new JLabel("Testing Type: ");
		styleCB = new JComboBox<String>(testTypeList);
		shapeLabel = new JLabel("Shape Selection: ");
		shapeCB = new JComboBox<String>(shapeList);
		shapeCB.setSelectedIndex(1);
		lengthBoundLabel = new JLabel("Radius Length Bounds(mm)");
		lengthBoundPanel = new JPanel();
		lengthBoundPanel.setLayout(new GridLayout(1, 0));
		lowTxt = new JTextField();
		lowTxt.setText("20");
		toLabel = new JLabel(" - ");
		toLabel.setHorizontalAlignment(SwingConstants.CENTER);
		upTxt = new JTextField();
		upTxt.setText("50");
		lengthBoundPanel.add(lowTxt);
		lengthBoundPanel.add(toLabel);
		lengthBoundPanel.add(upTxt);
		// Prepared for fixed length testing.
		lengthTxt = new JTextField();
		lengthTxt.setText("30");
		
		numLabel = new JLabel("Shape Number: ");
		numTxt = new JTextField();
		numTxt.setText("25");
		okBtn = new JButton("Setup");
		cancelBtn = new JButton("Cancel");
		setupPanel = new JPanel();
		setupPanel.setLayout(new GridLayout(7, 2));		
		setupPanel.add(emptyLabel);		
		setupPanel.add(isRandomRadioBtn);
		setupPanel.add(isLeftRadioBtn);
		setupPanel.add(isRightRadioBtn);
		setupPanel.add(styleLabel);
		setupPanel.add(styleCB);
		setupPanel.add(shapeLabel);
		setupPanel.add(shapeCB);
		setupPanel.add(lengthBoundLabel);
		setupPanel.add(lengthBoundPanel);
		setupPanel.add(numLabel);
		setupPanel.add(numTxt);
		setupPanel.add(okBtn);
		setupPanel.add(cancelBtn);
		setupPanel.setBorder(BorderFactory.createTitledBorder("Parameter Setup"));
		this.getContentPane().setLayout(new BorderLayout());
		this.add(setupPanel, BorderLayout.CENTER);
		
		this.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				SetupDialog dialog = (SetupDialog)e.getSource();
				dialog.setVisible(false);
			}
		});
		
		isRandomRadioBtn.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean isSelected = SetupDialog.this.isRandomRadioBtn.isSelected();
				int shapeIndex = shapeCB.getSelectedIndex();
				setLengthLabel(isSelected, shapeIndex);
				
				lengthBoundPanel.removeAll();
				lengthBoundPanel.setLayout(new GridLayout(1, 0));
				if (isSelected) {
					lengthBoundPanel.add(lowTxt);
					lengthBoundPanel.add(toLabel);
					lengthBoundPanel.add(upTxt);				
				}
				else {
					lengthBoundPanel.add(lengthTxt);
				}
				lengthBoundPanel.revalidate();
				lengthBoundPanel.repaint();
				//lengthBoundPanel.updateUI();
			}	
		});
		
		shapeCB.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isSelected = isRandomRadioBtn.isSelected();
				int shapeIndex = shapeCB.getSelectedIndex();
				setLengthLabel(isSelected, shapeIndex);
			}
			
		});
		
		okBtn.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				boolean isSelected = SetupDialog.this.isRandomRadioBtn.isSelected();
				int clickTypeIndex = isLeftRadioBtn.isSelected() ? 0 : 1;
				int testTypeIndex = SetupDialog.this.styleCB.getSelectedIndex();
				int shapeIndex = SetupDialog.this.shapeCB.getSelectedIndex();
				int lowPara = Integer.parseInt(SetupDialog.this.lowTxt.getText().trim());
				int upPara = Integer.parseInt(SetupDialog.this.upTxt.getText().trim());
				if (!isSelected) {
					lowPara = upPara = Integer.parseInt(SetupDialog.this.lengthTxt.getText().trim());
				}
				int numPara = Integer.parseInt(SetupDialog.this.numTxt.getText().trim());
				if (upPara < lowPara) {
					JOptionPane.showConfirmDialog(SetupDialog.this, "Up Bound should not be smaller than Low Bound.", "Inform", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				if (numPara < 1) {
					JOptionPane.showConfirmDialog(SetupDialog.this, "Shape Number must be positive integer.", "Inform", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				
				SetupDialog.this.setVisible(false);
				SetupDialog.this.topFrame.para.setParameters(isSelected, clickTypeIndex, testTypeIndex, shapeIndex, lowPara, upPara, numPara);
			}
			
		});
		
		cancelBtn.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SetupDialog.this.setVisible(false);
			}
			
		});
	}

	private void setLengthLabel(boolean isSelected, int shapeIndex) {
		if (isSelected) {
			if (shapeIndex == 1) {
				lengthBoundLabel.setText("Radius Length Bounds(mm): ");
			}
			else {
				lengthBoundLabel.setText("Line Length Bounds(mm): ");
			}
		}
		else {
			if (shapeIndex == 1) {
				lengthBoundLabel.setText("Circle Radius Length(mm): ");
			}
			else {
				lengthBoundLabel.setText("Straight Line Length(mm): ");
			}
		}
	}
	
	public void updateParameter(GameParameters para) {
		boolean isSelected = para.getIsRandomInGroup();
		int shapeIndex = para.getShapeFlag();
		
		isRandomRadioBtn.setSelected(isSelected);
		if (para.getClickTypeFlag() == 0) {
			isLeftRadioBtn.setSelected(true);
		}
		else
			isRightRadioBtn.setSelected(true);
		styleCB.setSelectedIndex(para.getTestTypeFlag());
		shapeCB.setSelectedIndex(shapeIndex);
		int lowValue = para.getLowParameter();
		int upValue = para.getUpParameter();
		lowTxt.setText(para.getLowParameter() + "");
		upTxt.setText(para.getUpParameter() + "");
		lengthTxt.setText((lowValue + upValue) / 2 + "");
		numTxt.setText(para.getNumParameter() + "");
		
		setLengthLabel(isSelected, shapeIndex);
	}
}
public class ShootingGame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private static final String lineSeparater = System.getProperty("line.separator");
	private JButton setupBtn, startBtn, stopBtn, saveBtn, resetBtn, calibrationBtn, exitBtn;
	private JLabel emptyLabel1, emptyLabel2, emptyLabel3, emptyLabel4;
	private JLabel statusLabel; 
	private JPanel toolPanel;
	private CanvasPanel canvasPanel;
	private JPanel bottomPanel;
	private JFileChooser saveDialog;
	private SetupDialog setupDialog;
	public GameParameters para;
	
	ShootingGame(String title) {
		super(title);
		
		para = new GameParameters();
		para.init();
		
		setupBtn = new JButton("Setup");
		setupBtn.addActionListener(this);
		startBtn = new JButton("Start");
		startBtn.addActionListener(this);
		stopBtn = new JButton("Stop");
		stopBtn.addActionListener(this);
		saveBtn = new JButton("Save");
		saveBtn.addActionListener(this);
		resetBtn = new JButton("Reset");
		resetBtn.addActionListener(this);
		calibrationBtn = new JButton("Calibration");
		calibrationBtn.addActionListener(this);
		exitBtn = new JButton("exit");
		exitBtn.addActionListener(this);
		emptyLabel1 = new JLabel();
		emptyLabel2 = new JLabel();
		emptyLabel3 = new JLabel();
		emptyLabel4 = new JLabel();
		
		toolPanel = new JPanel();
		toolPanel.setLayout(new GridLayout(1,0));
		toolPanel.add(setupBtn);
		toolPanel.add(startBtn);
		toolPanel.add(stopBtn);
		toolPanel.add(saveBtn);
		toolPanel.add(resetBtn);
		toolPanel.add(calibrationBtn);
		toolPanel.add(exitBtn);
		toolPanel.add(emptyLabel1);
		toolPanel.add(emptyLabel2);
		toolPanel.add(emptyLabel3);
		toolPanel.add(emptyLabel4);
		toolPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		
		canvasPanel = new CanvasPanel();
		//new Color(149, 202, 202)
		canvasPanel.setBackground(new Color(194, 214, 155));
		canvasPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());

		statusLabel = new JLabel("          Program Status");
		
		bottomPanel = new JPanel();
		bottomPanel.add(statusLabel);
		bottomPanel.setLayout(new GridLayout(1, 0));
		
		bottomPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		
		this.getContentPane().setLayout(new BorderLayout());
		this.add(toolPanel, BorderLayout.NORTH);
		this.add(canvasPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setLocation(100, 100);
		this.setSize(800, 600);
		this.setResizable(false);
		
		setupDialog = new SetupDialog(this, "Setup");
		setupDialog.updateParameter(para);
		setupDialog.setSize(350, 260);
		setupDialog.setResizable(false);
		setupDialog.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.setupBtn) {
			setupDialog.updateParameter(para);
			setupDialog.setLocationRelativeTo(this);
			setupDialog.setVisible(true);
		}
		else if (e.getSource() == this.startBtn) {
			canvasPanel.resetDrawParameters(para);
			if (para.getClickTypeFlag() == 0) {
				statusLabel.setText("          Click left button to shoot");
			}
			else {
				statusLabel.setText("          Click right button to shoot");
			}
		}
		else if (e.getSource() == this.stopBtn) {
			// TODO(ShootingTest)
			JOptionPane.showConfirmDialog(this, "Not yet implemented.", "Inform", JOptionPane.PLAIN_MESSAGE);
		}
		else if (e.getSource() == this.saveBtn) {
			boolean dataState = canvasPanel.dataHasChanged();
			if (!dataState) {
				JOptionPane.showConfirmDialog(this, "No need to save file.", "Inform", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			saveDialog = new JFileChooser();
			saveDialog.setDialogTitle("Save File ...");
			saveDialog.setDialogType(JFileChooser.SAVE_DIALOG);
			saveDialog.addChoosableFileFilter( new FileFilter() {

				@Override
				public boolean accept(File f) {
					// TODO Auto-generated method stub
					String fileName = f.getName();
					return fileName.toLowerCase().endsWith(".txt");
				}

				@Override
				public String getDescription() {
					// TODO Auto-generated method stub
					return "*.txt(Text File)";
				}
				
			});
			
			int flag = saveDialog.showSaveDialog(this);
			if (flag == JFileChooser.APPROVE_OPTION) {
				File saveFile = saveDialog.getSelectedFile();
				Vector<ShapeInfo> shapeList = canvasPanel.getShapeList();
				StringBuilder dataBuffer = new StringBuilder();
				if (!shapeList.isEmpty()) {
					dataBuffer.append(shapeList.get(0).getHeadLine());
				}
				dataBuffer.append(lineSeparater);
				for (ShapeInfo shape : shapeList) {
					dataBuffer.append(shape.toString(canvasPanel.GetCalibrationError()));
					dataBuffer.append(';');
					dataBuffer.append(lineSeparater);
				}
				PrintWriter pw = null;
				try {
					pw = new PrintWriter(new BufferedWriter(new FileWriter(saveFile)));
					pw.write(dataBuffer.toString());
					pw.flush();
					pw.close();
					
					// show confirm dialog
					JOptionPane.showConfirmDialog(this, "Save file successfully.", "Inform", JOptionPane.PLAIN_MESSAGE);
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
				finally {
					if (pw != null) {
						pw.close();
					}
				}
				
			}
		}
		else if (e.getSource() == this.resetBtn) {
			// TODO(ShootingTest)
			JOptionPane.showConfirmDialog(this, "Not yet implemented.", "Inform", JOptionPane.PLAIN_MESSAGE);
		}
		else if (e.getSource() == this.calibrationBtn) {
			JOptionPane.showConfirmDialog(this, "Please click points on the screen.", "Inform", JOptionPane.PLAIN_MESSAGE);
			canvasPanel.resetCalibrationParameters();
			if (para.getClickTypeFlag() == 0) {
				statusLabel.setText("          Click left button to calibration");
			}
			else {
				statusLabel.setText("          Click right button to calibration");
			}
		}
		else if (e.getSource() == this.exitBtn) {
			System.exit(0);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShootingGame littleGame = new ShootingGame("Data Collection");
		//littleGame.setUndecorated(true);
		// This can not have the overlapped windows, so this is not suitable.
		// littleGame.getGraphicsConfiguration().getDevice().setFullScreenWindow(littleGame);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	    Rectangle bounds = new Rectangle(screenSize);
	    littleGame.setBounds(bounds); 
		littleGame.setVisible(true);
	}

}
