package tereg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import tereg.Review.ReviewIssue;
import tereg.graph.PassBar;
import tereg.graph.PassPie;
import tereg.graph.ReqPie;


public class Main 
{

	public static void main(String[] args) throws Exception 
	{
		if (args.length < 3)
		{
			System.out.print("java tereg.jar [INPUT] [OUTPUT] [PATH]");
			System.exit(-1);
		}
		
		Serializer serializer = new Persister();
		
		BufferedReader in_reader = new BufferedReader(new FileReader(args[1]));

		String in = "";
		{
			StringWriter sw = new StringWriter();
			String line = null;
        	while ((line = in_reader.readLine()) != null) 
        		sw.append(line);
        	
        	in = sw.toString();
		}
		
		String out = args[2];
		
		//Review 
		try
		{
			Review insp = new Review();		
			StringReader rd = new StringReader(in);
			serializer.read(insp,  rd);
			
			String path = args[3];
			
			insp.writeDox(out, path);
			
			System.exit(0);
		}
		catch(org.simpleframework.xml.core.AttributeException ex) {}

		
		//Details
		try 
		{
			DetailsReportXml details = new DetailsReportXml();
			StringReader rd = new StringReader(in);
			serializer.read(details, rd);
			

			details.writeDox(out);

			System.exit(0);
		}
		catch(org.simpleframework.xml.core.AttributeException ex)
		{
			//Details failed
		}
		
		//Overview 
		try
		{
			OverviewReportXml overview = new OverviewReportXml();
			StringReader rd = new StringReader(in);
			serializer.read(overview, rd);
			
			String path = args[3];

			overview.writeDox(out, path);

			System.exit(0);
		}
		catch(org.simpleframework.xml.core.AttributeException ex) {}
		
		//RequirementCoverageOverview
		try
		{
			RequirementCoverageOverview reqOvw = new RequirementCoverageOverview();
			StringReader rd = new StringReader(in);
			serializer.read(reqOvw, rd);	
			
			String path = args[3];

			
			reqOvw.writeDox(out, path);

			System.exit(0);
		}
		catch(org.simpleframework.xml.core.AttributeException ex) {}

		
		//serializer.read(details, new File("Details2.xml"));
		System.exit(-1);
		
	}

}
