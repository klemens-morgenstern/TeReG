package tereg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import tereg.vcrm.Vcrm;




public class Main 
{

	public static void main(String[] args) throws Exception 
	{
		if (args.length < 2)
		{
			System.out.print("java tereg.jar [INPUT] [OUTPUT] [PATH]");
			System.exit(-1);
		}
		
		
		Serializer serializer = new Persister();
		
		@SuppressWarnings("resource")
		BufferedReader in_reader = new BufferedReader(new FileReader(args[0]));

		String in = "";
		{
			StringWriter sw = new StringWriter();
			String line = null;
        	while ((line = in_reader.readLine()) != null) 
        		sw.append(line + "\n");
        	
        	in = sw.toString();
		}
		
		String out = args[1];
		
		//Review 
		try
		{

			Vcrm vcrm = new Vcrm(args[3]);
			Review insp = new Review();		
			StringReader rd = new StringReader(in);
			serializer.read(insp,  rd);
			
			String path = args[2];
			
			insp.writeDox(out, path);
			insp.buildVcrm(vcrm);
			vcrm.save();
			System.exit(0);
			
		}
		catch(org.simpleframework.xml.core.PersistenceException ex) 
		{

		}

		
		//Details
		try 
		{
			DetailsReportXml details = new DetailsReportXml();
			StringReader rd = new StringReader(in);
			serializer.read(details, rd);
			

			details.writeDox(out);

			System.exit(0);
		}
		catch(org.simpleframework.xml.core.PersistenceException ex)
		{
			//Details failed

		}
		
		//Overview 
		try
		{
			OverviewReportXml overview = new OverviewReportXml();
			StringReader rd = new StringReader(in);
			serializer.read(overview, rd);
			
			String path = args[2];

			overview.writeDox(out, path);

			System.exit(0);
		}
		catch(org.simpleframework.xml.core.PersistenceException ex) 
		{

		}
		
		//RequirementCoverageOverview
		try
		{
			

			RequirementCoverageOverview reqOvw = new RequirementCoverageOverview();
			StringReader rd = new StringReader(in);
			serializer.read(reqOvw, rd);	
			
			String path = args[2];

			
			reqOvw.writeDox(out, path);

			System.exit(0);
		}
		catch(org.simpleframework.xml.core.PersistenceException ex) 
		{
		}

		//RequirementCoverageDetailed
		try
		{
			Vcrm vcrm = new Vcrm(args[3]);

			RequirementCoverageDetailed reqOvw = new RequirementCoverageDetailed();
			StringReader rd = new StringReader(in);
			serializer.read(reqOvw, rd);	
			
			String path = args[2];

			
			reqOvw.writeDox(out, path);
			vcrm.save();

			System.exit(0);
		}
		catch(org.simpleframework.xml.core.PersistenceException ex) 
		{
		}
		
		//serializer.read(details, new File("Details2.xml"));
		System.exit(-1);
		
	}

}
