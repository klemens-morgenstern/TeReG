package tereg.graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;

public class ReqPie
{
	private double not_available;
	private double planned;
	private double some_failed;
	private double some_passed;
	private double all_failed;
	private double all_passed;
	private boolean planning_only;
	private String title;
	
	//For the planing only version
	
	public void set_not_availabe(double value) 	{this.not_available = value;}
	public void set_planned(double value) 		{this.planned = value;}
	public void set_some_failed(double value) 	{this.some_failed = value;	this.planning_only = false;}
	public void set_some_passed(double value) 	{this.some_passed = value;	this.planning_only = false;}
	public void set_all_failed(double value) 	{this.all_failed = value;	this.planning_only = false;}
	public void set_all_passed(double value) 	{this.all_passed = value;	this.planning_only = false;}
	
	public ReqPie(String title)
	{
		this.title = title;
		this.planning_only = true;
	}

	
    private PieDataset createDataset() 
    {
    	double all = not_available
				+ planned
				+ some_failed
				+ some_passed
				+ all_failed
				+ all_passed;
    	
    	
        final DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("No test available", not_available / all);
        result.setValue("One or more tests planned", planned / all);
        if (planning_only == false)
        {
        	result.setValue("Some Test failed", some_failed / all);
        	result.setValue("Some Test passed", some_passed / all);
        	result.setValue("All Tests executed, some failed", all_failed / all);
        	result.setValue("All Tests passed", all_passed / all);
        }
        return result;
    }
    
    private JFreeChart createChart(final PieDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createPieChart3D(
            title,  // chart title
            dataset,                // data
            false,                   // include legend
            false,
            false
        );

        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setBackgroundAlpha(0.0f);
        //plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("No data to display");
        
        Color trans = new Color(0x0, 0x0, 0x0, 0);
        chart.setBorderVisible(false);
        chart.setBackgroundPaint(trans);
        plot .setBackgroundPaint(trans);
        
        plot.setSectionPaint("No test available", 		 Color.gray);
        plot.setSectionPaint("One or more tests planned", Color.blue.brighter());
        if (planning_only == false)
        {
            plot.setSectionPaint("Some Test failed",  Color.red.brighter());
            plot.setSectionPaint("Some Test passed",  Color.green.brighter());
            plot.setSectionPaint("All Tests executed",Color.red);
            plot.setSectionPaint("All Tests passed",  Color.green.darker());
        }
        

        return chart;
    }
    
    public void chartToFile(String filename) throws IOException
    {
    	JFreeChart ch = createChart(createDataset());
    	
    	BufferedImage bImg = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
    	Graphics2D cg = bImg.createGraphics();
    	cg.setBackground(new Color(0, 0, 0, 0) );
    	cg.fillRect(0, 0, 600, 400);
    	ch.draw(cg, new Rectangle2D.Double(0,0, 600, 400));
    	
    	ImageIO.write(bImg, "png", new File(filename));

    }

    
}
