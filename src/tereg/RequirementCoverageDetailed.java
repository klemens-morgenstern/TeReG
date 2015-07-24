
package tereg;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import tereg.RequirementCoverageDetailed.Module.Result;
import tereg.Review.ReviewIssue;
import tereg.vcrm.Vcrm;

@Root
public class RequirementCoverageDetailed 
{
	@Attribute public String description;
	@Attribute(required=false) public String filter;
	@Attribute public String date;
	@Attribute public String time;
	
	@ElementList public List<Module> modules;
	
	@Root static public class Module
	{
		@Attribute public String name;
		@Attribute public int orderNo;
		@Attribute public String parentFolder;
		@ElementList public List<TestObject> testObjects;
		@ElementList public List<Result> results;
		
		@Root public static class TestObject
		{
			@Attribute public String name;
			@Attribute public int orderNo;
			@ElementList public List<Result> results;

		}
		
		@Root public static class Result
		{
			@Attribute public String name;
			@Attribute public String type;
			@Attribute public int orderNo;
			
			@ElementList(inline=true, entry="requirement", required=false) List<Requirement> requirements;

		}
	}

	@Root
	public static class Requirement
	{
		@Attribute public String contentType;
		@Attribute public String identifier;
		@Attribute public String id;
		@Attribute public String version;
		@Attribute public String text;
		@Attribute public String description;
		@Attribute public String orderNo;
		@Attribute public String document;
		@Attribute public String isDeleted;
		@Attribute(required=false) public String status;
	}
	
	public void writeDox(String out, String path) throws IOException
	{

		
		StringWriter fw = new StringWriter();
		
		fw.write("/**");
		fw.write("@page ReqCovDet Linked Requirement Coverage Report\n\n");
		fw.write("@brief The detailed coverage report for linked requirements.\n");
		fw.write("@details @tableofcontents\n");


		TreeMap<String, TreeMap<String, TreeMap<String, Boolean>>> hmp = 
				new TreeMap<String, TreeMap<String, TreeMap<String, Boolean>>>();
		
		for (Module mod : modules)
		{
			if (mod.testObjects != null)
			for (Module.TestObject to : mod.testObjects)
			{
				if (to.results != null)
				for (Result res : to.results)
				{
					if (res.requirements != null)
					for (Requirement req : res.requirements)
					{

						TreeMap<String, TreeMap<String, Boolean>> t = hmp.get(req.description);
						if (t == null)
						{
							t = new TreeMap<String, TreeMap<String, Boolean>>();
							hmp.put(req.description, t);
						}

						if (!t.containsKey(to.name))
							t.put(to.name, new TreeMap<String, Boolean>());
						

						
						if (res.type.compareTo("testcase") == 0)
						{
							t.get(to.name).put(res.name, req.status.equals("PASSED"));
						}
					}
				}
			}
		}
		
		for (String reqId : hmp.keySet())
		{
			///the thing is utterly slow...
			boolean req_pass = true;
			TreeMap<String, TreeMap<String, Boolean>> sub = hmp.get(reqId);
			for (String in : sub.keySet())
				for (String in2 : sub.get(in).keySet())
					req_pass &= sub.get(in).get(in2);

			
			
			fw.write("@section UnitTest-" + reqId + " Unit tests for " + reqId + "\n\n");
			
			fw.write("<table class=\"fieldtable\"><tr><th colspan=\"2\"> Testobject</th><th> Status </th></tr>\n"
					+ "<tr><td>\n");
			
			
			for (String toid : sub.keySet())
			{
				TreeMap<String, Boolean> to = sub.get(toid);
				
				boolean passed = to.size() > 0;
				for (String tcid : to.keySet())
					passed &= to.get(tcid);
				
				
				fw.write("<tr><td class=\"fieldname\" colspan=\"2\">@ref TestObject-" + toid + " \"" + toid + "\"</td>");
				
				if (passed)
					fw.write("<td class=\"fieldname\">@image html success.png\n</td></tr>\n");
				else
					fw.write("<td class=\"fieldname\">@image html fail.png\n</td></tr>\n");

				for (String tcid : to.keySet())
				{
					
					//get the tc id.
					String tcnum = tcid.split(":")[0].split(" ")[2];
					
					fw.write("<tr><td class=\"fieldname\"></td><td class=\"fieldname\">@ref TestCase-" + toid + "-" + tcnum + " \"" + tcid + "\"</td>");

					if (to.get(tcid))
						fw.write("<td class=\"fieldname\">@image html success.png\n</td></tr>\n");
					else
						fw.write("<td class=\"fieldname\">@image html fail.png\n</td></tr>\n");

				}
				
				
			}
			
			if (req_pass)
				fw.write("<tr><td class=\"fieldname\" colspan=\"2\">@ref Req- " + reqId + " Requirement</td><td class=\"fieldname\">@image html success.png\n</td></tr></table>\n\n");
			else
				fw.write("<tr><td class=\"fieldname\" colspan=\"2\">@ref Req- " + reqId + " Requirement</td><td class=\"fieldname\">@image html fail.png\n</td></tr></table>\n\n");

			
		}
		
		fw.write("*/");

		
		java.io.File f = new java.io.File(out);
		f.getParentFile().mkdir();
		FileWriter ff = new FileWriter(f);
		ff.write(fw.toString());
		ff.close();
		fw.close();
		
	}

	
}
