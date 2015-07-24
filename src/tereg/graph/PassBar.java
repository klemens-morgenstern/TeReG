package tereg.graph;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;

public class PassBar
{
	
	static class Bar
	{
		private double passed;
		private double not_executed;
		private double failed;
		private String title;
	}

	List<Bar> bars = new ArrayList<Bar>();
	
	public void addBar(String title, double passed, double not_executed, double failed)
	{
		Bar b = new Bar();
		b.title = title;
		b.passed = passed;
		b.not_executed = not_executed;
		b.failed = failed;
		
		bars.add(b);
	}
	
	public PassBar()
	{
	}
	
    private CategoryDataset createDatasets()
    {
    	final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	
    	for (Bar b : bars)
    	{
    		dataset.addValue(b.failed, 		 "Failed", 		 b.title);
    		dataset.addValue(b.not_executed, "Not executed", b.title);
    		dataset.addValue(b.passed, 		 "Passed", 		 b.title);
    	}
    	
    	return dataset;
    }

    
    private JFreeChart createChart(String title) {
        
        JFreeChart chart = ChartFactory.createStackedBarChart(title, "Executed Test", "Test objects",
        		createDatasets(), PlotOrientation.VERTICAL, false, false, false);
        
        final CategoryPlot plot = (CategoryPlot)chart.getCategoryPlot();

        
        Color trans = new Color(0x0, 0x0, 0x0, 0);
        chart.setBorderVisible(false);
        chart.setBackgroundPaint(trans);
        plot .setBackgroundPaint(trans);
        
        
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        

        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesPaint(1, Color.yellow);
        renderer.setSeriesPaint(2, Color.green);
        
        
     /*   plot.setSectionPaint("Passed", 		 Color.green);
        plot.setSectionPaint("Not executed", Color.yellow);
        plot.setSectionPaint("Failed", 		 Color.red);
*/
        return chart;
    }
    
    public void chartToFile(String title, String filename) throws IOException
    {
    	JFreeChart ch = createChart(title);
    	
    	int width = (bars.size() * 25);
    	if (width > 1400)
    		width = 1400;
    	else if (width < 400)
    		width = 400;
    	
    	BufferedImage bImg = new BufferedImage(width, 400, BufferedImage.TYPE_INT_RGB);
    	Graphics2D cg = bImg.createGraphics();
    	cg.setBackground(new Color(0, 0, 0, 0) );
    	cg.fillRect(0, 0, width, 400);
    	ch.draw(cg, new Rectangle2D.Double(0,0, width, 400));
    	
    	ImageIO.write(bImg, "png", new File(filename));
    	
  
    	
    }
   
}
