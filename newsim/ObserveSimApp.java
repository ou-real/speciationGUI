package newsim;

import javax.swing.*;
import com.wolfram.jlink.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;

public class ObserveSimApp extends JFrame implements ActionListener, WindowListener{

	KernelLink ml;
	
	JScrollPane scrollPane;
	ArrayList<GraphDisplayPanel> panels;
	JPanel graphsPanel;
	int numOfPanels;
	
	JPanel observeSimSettingsPanel;
	JButton newPanelButton;
	JTextField fileNameInput;
	
	public ObserveSimApp(KernelLink link) {
		super();
		
		ml = link;
		panels = new ArrayList<GraphDisplayPanel>();
		numOfPanels = 0;
		
		//Setting up this
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("GraphPanelOrganizer");
		this.setSize(new Dimension(700,1000));
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS));
		this.setVisible(true);
		
		//Setting up the settingsPanel
		observeSimSettingsPanel = new JPanel();
		observeSimSettingsPanel.setPreferredSize(new Dimension(800,50));
		observeSimSettingsPanel.setMaximumSize(new Dimension(800,50));
		observeSimSettingsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		observeSimSettingsPanel.setLayout(new BoxLayout(observeSimSettingsPanel,BoxLayout.LINE_AXIS));
		observeSimSettingsPanel.setVisible(true);
		
		//Setting up the button
		newPanelButton = new JButton();
		newPanelButton.setText("Click to make graphs");
		newPanelButton.setPreferredSize(new Dimension(180,30));
		newPanelButton.setVisible(true);
		newPanelButton.addActionListener(this);
		observeSimSettingsPanel.add(newPanelButton);
		
		//Setting up the textField
		fileNameInput = new JTextField();
		fileNameInput.setPreferredSize(new Dimension(350,30));
		fileNameInput.setVisible(true);
		observeSimSettingsPanel.add(fileNameInput);
		
		//Setting up the graphsPanel
		graphsPanel = new JPanel();
		graphsPanel.setPreferredSize(new Dimension(800,100));
		graphsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		graphsPanel.setLayout(new BoxLayout(graphsPanel,BoxLayout.PAGE_AXIS));
		
		//Setting up the scrollPane
		scrollPane = new JScrollPane(graphsPanel);
		//scrollPane.setPreferredSize(new Dimension(500,500));
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setVisible(true);
		
		this.add(observeSimSettingsPanel);
		this.add(scrollPane);
		this.repaint();
		this.revalidate();
	}
	
	public void actionPerformed(ActionEvent e) {
		//enter this in to the textField as a test
		//meiosis complete [1.0, 4.0, 5.0] [0.25, 0.5, 0.5] sexual1 .csv
		numOfPanels++;
		GraphDisplayPanel g = new GraphDisplayPanel(ml,fileNameInput.getText());
		g.setPreferredSize(new Dimension(400,270));
		graphsPanel.setPreferredSize(new Dimension(800,20 + 270 * numOfPanels));
		graphsPanel.add(g);
		this.repaint();
		this.revalidate();
	}
	
	public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {
    	ml.close();
    }
	public void windowClosed(WindowEvent e) {}

	public static void main(String[] args) {
		KernelLink ml = null;

        try {
            ml = MathLinkFactory.createKernelLink(args);
        } catch (MathLinkException e) {
            System.out.println("Fatal error opening link: " + e.getMessage());
            return;
        }
        
        try {
        	//Setting up the Kernel
        	ml.discardAnswer();
        	ml.evaluate("<< graphDefinitionPackage`");
        	ml.discardAnswer();
            
            //Create and setup the GraphPanelOrganizer
        	ObserveSimApp f = new ObserveSimApp(ml);
        	
        }
		catch (MathLinkException e) {
			System.out.println("MathLinkException occurred: " + e.getMessage());
		}
	}
}