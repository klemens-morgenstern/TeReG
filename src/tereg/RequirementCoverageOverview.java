package tereg;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.stringtemplate.v4.*;

import tereg.Review.ReviewIssue;
import tereg.graph.ReqPie;
import tereg.vcrm.Vcrm;

@Root
public class RequirementCoverageOverview 
{
	@Element public Info info;

	@ElementList public List<Requirementdocument> requirementdocuments;
	
	
	@Root
	static public class Info
	{
		@Attribute public String tessyversion;
		@Attribute public String projectname;
		@Attribute public String date;
		@Attribute public String time;
		@Attribute(name="kind-of-coverage") public String kind_of_coverage;
	}
	
	@Root static public class Requirementdocument
	{
		@Attribute public String title;
		@Attribute public String alias;
		@ElementList public List<State> overview;
		@ElementList public List<Requirement> requirements;
		

		
		@Root
		static public  class State
		{
			@Attribute public String name;
			@Attribute public int count;
		}
		
		@Root
		static public class Requirement
		{
			@Attribute public String identifier;
			@Attribute public int id;
			@Attribute public String version;
			@Attribute public String contentType;
			@Attribute public String shortdescription;
			@Attribute public String text;
			@Attribute public int linkedtests;
			@Attribute public int notexecutedtests;
			@Attribute public int passedtests;
			@Attribute public int failedtests;
			@Attribute public String state;
	
			@ElementList public List<Testcase> testcases;
			
			@Root static public class Testcase 
			{
				@Attribute public String module;
				@Attribute public String testobject;
				@Attribute public String tc;
				@Attribute public String state;
				
			};
			
			
			public String getRow()
			{
				StringWriter sw = new StringWriter();

				//TODO : Here's still stuff to do.
				sw.write("<tr>");
				sw.write("<td class=\"fieldname\">" + shortdescription + "</td>\n");
				sw.write(getStateDescr(state));
				sw.write("<td class=\"fieldname\" " + getPassed(passedtests) + "align=\"right\">" + passedtests	    + "</td>\n");          
				sw.write("<td class=\"fieldname\" " + getFailed(failedtests) + "align=\"right\">" + failedtests       + "</td>\n");
				sw.write("<td class=\"fieldname\" " + getNotExe(notexecutedtests) + "align=\"right\">" + notexecutedtests  + "</td>\n");
				sw.write("<td class=\"fieldname\" " + getLinked(linkedtests) + "align=\"right\">" + linkedtests       + "</td>\n");
				sw.write("</tr>\n");
				
				return sw.toString();
			}
			
			static String getLinked(int i)
			{
				if (i == 0)
					return " bgcolor=\"#3399FF\" ";
				else 
					return "";
			}
			static String getPassed(int i)
			{
				if (i != 0)
					return " bgcolor=\"#00FF00\" ";
				else 
					return " bgcolor=\"#FF0000\" ";
			}
			static String getFailed(int i)
			{
				if (i == 0)
					return " bgcolor=\"#00FF00\" ";
				else 
					return " bgcolor=\"#FF0000\" ";
			}
			
			static String getNotExe(int i)
			{
				if (i != 0)
					return " bgcolor=\"#3399FF\" ";
				else 
					return "";
			}
			
			static String getStateDescr(String abbr)
			{
				if (abbr.compareTo("UNLINKED") == 0)
					return "<td class=\"fieldname\" align=\"right\" bgcolor=\"#FF0000\">No Tests available</td>\n";
				else if (abbr.compareTo("NONE_EXECUTED") == 0)
					return "<td class=\"fieldname\" align=\"right\" bgcolor=\"#3399FF\">No Tests available</td>\n";
				else if (abbr.compareTo("SOME_PASSED_OR_FAILED") == 0)
					return "<td class=\"fieldname\" align=\"right\" bgcolor=\"#8F0000\">No Tests available</td>\n";
				else if (abbr.compareTo("SOME_PASSED") == 0)
					return "<td class=\"fieldname\" align=\"right\" bgcolor=\"#008F00\">No Tests available</td>\n";
				else if (abbr.compareTo("ALL_PASSED_OR_FAILED") == 0)
					return "<td class=\"fieldname\" align=\"right\" bgcolor=\"#FF0000\">No Tests available</td>\n";
				else if (abbr.compareTo("ALL_PASSED") == 0)
					return "<td class=\"fieldname\" align=\"right\" bgcolor=\"#00FF00\">No Tests available</td>\n";
				else
					return abbr;
			}

		}
		
		public String getDox() throws IOException
		{
			StringWriter sw = new StringWriter();
			
			String tl = title.replace(" ", "_");
			
			
			sw.write("@section ReqDoc-" + tl +  " " + title + "\n\n");
			
			ReqPie rp = new ReqPie(title);
			
			for (State s : overview)
			{
				if (s.name.compareTo("UNLINKED") == 0)
					rp.set_not_availabe(s.count);
				else if (s.name.compareTo("NONE_EXECUTED") == 0)
					rp.set_planned(s.count);
				else if (s.name.compareTo("SOME_PASSED_OR_FAILED") == 0)
					rp.set_some_failed(s.count);
				else if (s.name.compareTo("SOME_PASSED") == 0)
					rp.set_some_passed(s.count);
				else if (s.name.compareTo("ALL_PASSED_OR_FAILED") == 0)
					rp.set_all_failed(s.count);
				else if (s.name.compareTo("ALL_PASSED") == 0)
					rp.set_all_passed(s.count);
			}
			
			rp.chartToFile("ReqPie_" + tl.replace("-",  "_") + ".png");
			
			sw.write("@image html ReqPie_" + tl + ".png\n");
			sw.write("@image latex ReqPie_" + tl + ".png\n\n");
			
			sw.write("<table class=\"fieldtable\">");
			sw.write("<tr>");
			sw.write("<th>Identifier</th>");
			sw.write("<th>State</th>");
			sw.write("<th>Passed Tests</th>");
			sw.write("<th>Failed Tests</th>");
			sw.write("<th>Not executed Tests</th>");
			sw.write("<th>Number of Tests</th>");
			sw.write("</tr>\n");

			
			
			for (Requirement req : requirements)
				sw.write(req.getRow());
			
			sw.write("</table>");
			
			return sw.toString();
		}
	}
	
	
	
	void writeDox(String filename, String path) throws IOException
	{
		FileWriter fw = new FileWriter(filename);
		fw.write("/**");
		fw.write("@page ReqCovRep Requirement Coverage Report\n\n");
		fw.write("@brief Overview of the Requirement Coverage");
		fw.write("@details\n");
		
		fw.write(getInfo());
		
		
		for (Requirementdocument doc : requirementdocuments)
		{
			fw.write(doc.getDox() + "\n\n");
		}
		fw.write("*/");
		fw.close();
	}
	
	private String getInfo()
	{
		ST inf = null;
		
		if (info.kind_of_coverage != null)
		{
			inf = new ST(
					"| Tessy Version  | Project Name | Date   |  Time  | Kind of coverage |\n" + 
					"|----------------|--------------|--------|--------|------------------|\n" +
					"| <tessyversion> | <project>    | <date> | <time> | <coverage> |\n\n");
		
			inf.add("coverage", info.kind_of_coverage);
		}
		else
		{
			inf = new ST(
				"| Tessy Version  | Project Name | Date   |  Time  |\n" + 
				"|----------------|--------------|--------|--------|\n" +
				"| <tessyversion> | <project>    | <date> | <time> |\n\n");
		
		}
		inf.add("tessyversion",  info.tessyversion);
		inf.add("project",  info.projectname);
		inf.add("date",  info.date);
		inf.add("time",  info.time);

		return inf.render();
	}

	public void buildVcrm(Vcrm vcrm) 
	{
		for (Requirementdocument doc : requirementdocuments)
		{
			for (Requirementdocument.Requirement req : doc.requirements)
			{
				JSONObject obj = vcrm.map.get(req.shortdescription);
				if (obj == null)
					continue;
				
				
				if (req.state.compareTo("ALL_PASSED") == 0)
				{
					if (!obj.containsKey("unit_test"))
					{
						obj.put("unit_test", new Boolean(true));
					}
				}
				else
				{
					if (!obj.containsKey("unit_test"))
					{
						obj.put("unit_test", new Boolean(false));
					}
					else
					{
						if ((boolean)obj.get("unit_test") == true)
							obj.put("unit_test", new Boolean(false));

					}
				}
			}
		}
	}
}