package newsim;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathGraphicsJPanel;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;
import com.wolfram.jlink.*;

import java.awt.Color;

import javax.swing.*;
public class GraphDisplayPanel extends MathGraphicsJPanel {

	MathGraphicsJPanel dotPlot;
	MathGraphicsJPanel barPlot;
	MathGraphicsJPanel seedPlot;
	int size = 500;
	
	public GraphDisplayPanel() {
		super();
	}

	//need to redo this one using a non-null layout
	public GraphDisplayPanel(KernelLink ml, String fileName) {
		//also needs to somehow input the file to be graphed
		super(ml);
		
		//Setup this panel
		this.setSize(560,size);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(null);
		
		//Setup the dotPlot
		dotPlot = new MathGraphicsJPanel();
		dotPlot.setLink(ml);
		dotPlot.setBounds(700,20,size,size);
		dotPlot.setImageType(MathGraphicsJPanel.GRAPHICS);
		dotPlot.setMathCommand("graph[\"" + fileName.replace("\\", "\\\\\\\\") + "\"]");
		
		//Setup the barPlot
		//needs to be positionally reconfigured
		barPlot = new MathGraphicsJPanel();
		barPlot.setLink(ml);
		barPlot.setBounds(290,20,size,size);
		barPlot.setImageType(MathGraphicsJPanel.GRAPHICS);
		barPlot.setMathCommand("Plot[x^2,{x,-3,3}]");//need to actually write the barplot method
		
		/*//Setup the seedPlot
		seedPlot = new MathGraphicsJPanel();
		seedPlot.setLink(ml);
		seedPlot.setBounds(40 + size,20,size,size);
		seedPlot.setImageType(MathGraphicsJPanel.GRAPHICS);
		seedPlot.setMathCommand("seedPlot[\"" + fileName.replace("\\", "\\\\\\\\") + "\"]");*/
		
		//Add the two MathGraphicsJPanel's to this panel
		this.add(dotPlot);
		//this.add(barPlot);
		//this.add(seedPlot);
		dotPlot.setVisible(true);
		//barPlot.setVisible(true);
		//seedPlot.setVisible(true);
		this.setVisible(true);
		this.revalidate();
	}
	
	
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
            
            //Setting up the frame
        	JFrame f = new JFrame();
        	f.setLayout(null);
        	f.setSize(1000,1000);
    		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		f.setBackground(Color.white);
    		f.setVisible(true);
    		f.setTitle("this is supposed to display graphics");
        	
    		//Setting up the graphs
        	GraphDisplayPanel graphs = new GraphDisplayPanel(ml, "\\meiosis complete [1.0, 4.0, 5.0] [0.25, 0.5, 0.5] sexual1 .csv");
        	graphs.setLocation(100,100);
        	f.add(graphs);
        	
        	f.repaint();
        	f.revalidate();
        }
		catch (MathLinkException e) {
			System.out.println("MathLinkException occurred: " + e.getMessage());
		} finally {
			ml.close();
		}
	}
}
