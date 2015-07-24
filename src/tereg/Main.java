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

			Review insp = new Review();		
			StringReader rd = new StringReader(in);
			
			serializer.read(insp,  rd);
			
			
			Vcrm vcrm = new Vcrm(args[3]);

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
			String path = args[2];

			details.writeDox(out, args[0], path);

			System.exit(0);
		}
		catch(org.simpleframework.xml.core.PersistenceException ex)
		{
			//Details failed
			//System.err.println(ex.toString());
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

			
			Vcrm vcrm = new Vcrm(args[3]);
			reqOvw.buildVcrm(vcrm);
			vcrm.save();
			
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
		
		//The compile test stuff
		try
		{
			Vcrm vcrm = new Vcrm(args[3]);

			CompileTests cts = new CompileTests();

			StringReader rd = new StringReader(in);
			

			
			serializer.read(cts, rd);	
			
			String path = args[2];

			cts.writeDox(out, path);
			cts.buildVcrm(vcrm, path);

			
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
