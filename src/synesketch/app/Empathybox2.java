/**
 * @author Uros Krcadinac
 * 17.03.2008.
 * @version 0.1
 */
package synesketch.app;

import java.awt.event.*;
import javax.swing.*;

import synesketch.gui.EmpathyPanel;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

public class Empathybox2 {

	private JFrame jFrame = null; 

	private JPanel jContentPane = null;

	private EmpathyPanel appletPanel = null;

	private JScrollPane jScrollPane = null;

	private JTextArea jTextArea = null;
	
	private int dim = 500;
	
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//jFrame.setSize(dim, (int) Math.round(dim * 1.618));
			jFrame.setSize(dim, dim + 150);
			jFrame.setLocation(400, 100);
			FlowLayout flowLayout = new FlowLayout();
			jFrame.setLayout(flowLayout);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("Synesketch Empathybox");
			jFrame.addWindowListener(new WindowAdapter() {
			    @Override
			    public void windowClosing(WindowEvent e) {
			    	System.exit(1);
			    }
			});			
		}
		return jFrame;
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			BorderLayout layout = new BorderLayout();
			jContentPane = new JPanel();
			jContentPane.setLayout(layout);
			jContentPane.add(getAppletPanel(), BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private EmpathyPanel getAppletPanel() {
		if (appletPanel == null) {
			try {
				appletPanel = new EmpathyPanel(dim, "SynesketchPApplet", "synesketch.emotion.SynesthetiatorEmotionSemantic");
				//appletPanel = new EmpathyPanel(dim, "Synemania2", "synesketch.emotion.SynesthetiatorEmotion");
				//appletPanel = new EmpathyPanel(dim, "SketchParticles", "synesketch.emotion.SynesthetiatorEmotion");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return appletPanel;
	}

	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jScrollPane.setVisible(true);
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					try {
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							String text = jTextArea.getText();
							appletPanel.fireSynesthesiator(text);
							jTextArea.setText(null);
						}
					} catch (Exception e1) {
						try {
							appletPanel = new EmpathyPanel(dim, "Synemania", "synesketch.emotion.SynesthetiatorEmotion");
						} catch (Exception e3) {
							e3.printStackTrace();
						}
						e1.printStackTrace();
					}
				}
			});
		}
		return jTextArea;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Empathybox2 application = new Empathybox2();
				application.getJFrame().setVisible(true);
			}
		});
	}

}
