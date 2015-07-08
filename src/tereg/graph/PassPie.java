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

public class PassPie
{
	private double passed;
	private double not_executed;
	private double failed;
	private String title;
	
	public PassPie(double passed, double not_executed, double failed)
	{
		title = new String("Test Overview");
		double all = passed + not_executed + failed;
		this.passed = passed / all;
		this.not_executed = not_executed / all;
		this.failed = failed / all;
	}
	
    private PieDataset createDataset() 
    {
        final DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Passed", passed);
        result.setValue("Not executed", not_executed);
        result.setValue("Failed", failed);
        return result;
    }
    
    private JFreeChart createChart(final PieDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createPieChart3D(
            "Test execution overview",  // chart title
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
        
        plot.setSectionPaint("Passed", 		 Color.green);
        plot.setSectionPaint("Not executed", Color.yellow);
        plot.setSectionPaint("Failed", 		 Color.red);
        
        return chart;
    }
    
    private void chartToFile(String filename) throws IOException
    {
    	JFreeChart ch = createChart(createDataset());
    	
    	BufferedImage bImg = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
    	Graphics2D cg = bImg.createGraphics();
    	cg.setBackground(new Color(0, 0, 0, 0) );
    	cg.fillRect(0, 0, 600, 400);
    	ch.draw(cg, new Rectangle2D.Double(0,0, 600, 400));
    	
    	ImageIO.write(bImg, "png", new File(filename));
    	
  
    	
    }
    public static void makeChart(String filename, double passed, double not_executed, double failed) throws IOException
    {
    	PassPie p = new PassPie(passed, not_executed, failed);
    	p.chartToFile(filename);
    	
    }
    
}
