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
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RunSimApp extends JFrame implements ActionListener, WindowListener{

	KernelLink ml;
	String dateTime;
	
	JScrollPane scrollPane;
	ArrayList<GraphDisplayPanel> panels;
	JPanel graphsPanel;
	int numOfPanels;
	
	JPanel runSimSettingsPanel;
	JButton runSimButton;
	JComboBox selectionTypeComboBox;
	JLabel meansLabel;
	JTextField meansTextField;
	JLabel stDevsLabel;
	JTextField stDevsTextField;
	JComboBox inheritanceMethodComboBox;
	JComboBox geneTypeComboBox;
	JLabel numberOfSimsLabel;
	JTextField numberOfSimsTextField;
	
	public RunSimApp(KernelLink link) {
		super();
		
		ml = link;
		panels = new ArrayList<GraphDisplayPanel>();
		numOfPanels = 0;
		dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")
				.format(LocalDateTime.now());
		
		//Setting up this
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("GraphPanelOrganizer: " + dateTime);
		this.setSize(new Dimension(900,600));
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS));
		this.setVisible(true);
		
		//Setting up the settingsPanel
		runSimSettingsPanel = new JPanel();
		runSimSettingsPanel.setPreferredSize(new Dimension(900,30));
		runSimSettingsPanel.setMaximumSize(new Dimension(900,30));
		runSimSettingsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		runSimSettingsPanel.setLayout(new BoxLayout(runSimSettingsPanel,BoxLayout.LINE_AXIS));
		runSimSettingsPanel.setVisible(true);
		
		//Setting up the runSimButton
		runSimButton = new JButton();
		runSimButton.setText("Click to make graphs");
		runSimButton.setPreferredSize(new Dimension(170,30));
		runSimButton.setVisible(true);
		runSimButton.addActionListener(this);
		runSimSettingsPanel.add(runSimButton);
		
		//Setting up the inheritanceMethodComboBox
		inheritanceMethodComboBox = new JComboBox(new String[] {
				Bird.InheritanceMethod.Meiosis.toString(), 
				Bird.InheritanceMethod.Pseudomeiosis.toString(), 
				Bird.InheritanceMethod.Averaging.toString()});
		inheritanceMethodComboBox.setPreferredSize(new Dimension(70,30));
		inheritanceMethodComboBox.setVisible(true);
		runSimSettingsPanel.add(inheritanceMethodComboBox);
		
		//Setting up the selectionTypeComboBox
		selectionTypeComboBox = new JComboBox(new String[] {
				Bird.SelectionType.SexualSelection.toString(), 
				Bird.SelectionType.RandomSelection.toString()});
		selectionTypeComboBox.setPreferredSize(new Dimension(70,30));
		selectionTypeComboBox.setVisible(true);
		runSimSettingsPanel.add(selectionTypeComboBox);
		
		//Setting up the geneTypeComboBox
		geneTypeComboBox = new JComboBox(new String[] {
				Gene.Type.CompleteDominance.toString(), 
				Gene.Type.IncompleDominance.toString()});
		geneTypeComboBox.setPreferredSize(new Dimension(100,30));
		geneTypeComboBox.setVisible(true);
		runSimSettingsPanel.add(geneTypeComboBox);

		//Setting up the meansLabel
		meansLabel = new JLabel();
		meansLabel.setText("Means: ");
		meansLabel.setPreferredSize(new Dimension(50,30));
		meansLabel.setVisible(true);
		runSimSettingsPanel.add(meansLabel);
		
		//Setting up the meansTextField
		meansTextField = new JTextField();
		meansTextField.setPreferredSize(new Dimension(70,30));
		meansTextField.setVisible(true);
		runSimSettingsPanel.add(meansTextField);
		
		//Setting up the stDevsLabel
		stDevsLabel = new JLabel();
		stDevsLabel.setText("St Devs: ");
		stDevsLabel.setPreferredSize(new Dimension(50,30));
		stDevsLabel.setVisible(true);
		runSimSettingsPanel.add(stDevsLabel);
		
		//Setting up the stDevsTextField
		stDevsTextField = new JTextField();
		stDevsTextField.setPreferredSize(new Dimension(70,30));
		stDevsTextField.setVisible(true);
		runSimSettingsPanel.add(stDevsTextField);
		
		//Setting up the numberOfSimsLabel
		numberOfSimsLabel = new JLabel();
		numberOfSimsLabel.setText("Num of Sims: ");
		numberOfSimsLabel.setPreferredSize(new Dimension(80,30));
		numberOfSimsLabel.setVisible(true);
		runSimSettingsPanel.add(numberOfSimsLabel);
		
		//Setting up the numberOfSimsTextField
		numberOfSimsTextField = new JTextField();
		numberOfSimsTextField.setPreferredSize(new Dimension(50,30));
		numberOfSimsTextField.setVisible(true);
		runSimSettingsPanel.add(numberOfSimsTextField);
		
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
		
		this.add(runSimSettingsPanel);
		this.add(scrollPane);
		this.repaint();
		this.revalidate();
	}
	
	public void actionPerformed(ActionEvent event) {
		//runs a new sim using the GUI inputs
		//somehow makes a GraphDisplayPanel using that data
		//adds that GraphDisplayPanel to graphsPanel
		int numOfRuns = getNumOfRuns();
		for(int i = 0; i < numOfRuns; i++) {
			Simulation sim = null;
			try {
				sim = new Simulation(getSelectionType(), getInheritanceMethod(), getGeneType(), 
						getMeans(), getStDevs(), dateTime);
			} catch (IOException exception) {
				exception.printStackTrace();
			}
			//System.out.println(sim.getFullFileName());
			//System.out.println(sim.getFullFileName().replace("\\", "\\\\\\\\"));
			
			numOfPanels++;
			GraphDisplayPanel g = new GraphDisplayPanel(ml,sim.getFullFileName());
			g.setPreferredSize(new Dimension(500,g.size));
			graphsPanel.setPreferredSize(new Dimension(800,20 + g.size * numOfPanels));
			graphsPanel.add(g);
			this.repaint();
			this.revalidate();
		}
	}
	
	public int getNumOfRuns() {
		if(numberOfSimsTextField.getText().equals(""))
			return 1;
		return Integer.parseInt(numberOfSimsTextField.getText());
	}
	
	public Bird.SelectionType getSelectionType(){
		String t = (String) selectionTypeComboBox.getSelectedItem();
		Bird.SelectionType r = null;
		if (t.equals(Bird.SelectionType.RandomSelection.toString()))
			r = Bird.SelectionType.RandomSelection;
		if (t.equals(Bird.SelectionType.SexualSelection.toString()))
			r = Bird.SelectionType.SexualSelection;
		return r;
	}
	
	public double[] getMeans() {
		if(meansTextField.getText().equals(""))
			return new double[] {5};
		String[] strings = meansTextField.getText().split(" ");
		double[] temp = new double[strings.length];
		for(int i=0;i<strings.length;i++)
			temp[i] = Double.parseDouble(strings[i]);
		return temp;
	}
	
	public double[] getStDevs() {
		if(stDevsTextField.getText().equals(""))
			return new double[] {.5};
		String[] strings = stDevsTextField.getText().split(" ");
		double[] temp = new double[strings.length];
		for(int i=0;i<strings.length;i++)
			temp[i] = Double.parseDouble(strings[i]);
		return temp;
	}
	
	public Bird.InheritanceMethod getInheritanceMethod(){
		String t = (String) inheritanceMethodComboBox.getSelectedItem();
		Bird.InheritanceMethod r = null;
		if(t.equals(Bird.InheritanceMethod.Averaging.toString()))
			r = Bird.InheritanceMethod.Averaging;
		if(t.equals(Bird.InheritanceMethod.Meiosis.toString()))
			r = Bird.InheritanceMethod.Meiosis;
		if(t.equals(Bird.InheritanceMethod.Pseudomeiosis.toString()))
			r = Bird.InheritanceMethod.Pseudomeiosis;
		return r;
	}
	
	public Gene.Type getGeneType(){
		String t = (String) geneTypeComboBox.getSelectedItem();
		Gene.Type r = null;
		if(t.equals(Gene.Type.CompleteDominance.toString()))
			r = Gene.Type.CompleteDominance;
		if(t.equals(Gene.Type.IncompleDominance.toString()))
			r = Gene.Type.IncompleDominance;
		return r;
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
        	RunSimApp f = new RunSimApp(ml);
        	
        }
		catch (MathLinkException e) {
			System.out.println("MathLinkException occurred: " + e.getMessage());
		}
	}
}