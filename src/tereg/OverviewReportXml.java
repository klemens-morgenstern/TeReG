package tereg;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import tereg.RequirementCoverageOverview.Requirementdocument;
import tereg.graph.PassBar;
import tereg.graph.PassPie;
import tereg.vcrm.Vcrm;

@Root
public class OverviewReportXml 
{
	@Attribute public String success;
	@Attribute public String tessy_version;
	@Attribute public String xml_version;
	
	@Element Statistic statistic;
	
	@Element Info info;
	
	@Element(required=false) Settings settings;
	
	@Element Selected_Objects selected_objects;
	
	@Element Used_Environments used_environments;

	@ElementList(inline=true, required=false) List<Tessyobject> tessyobject;
	
	@Root static public class Statistic
	{
		@Attribute public String notexecuted;
		@Attribute public String notok;
		@Attribute public String ok;
		@Attribute public String total;
		@Root static public class Category
		{
			@Attribute
			public String count;
			@Attribute
			public String name;
		}
		
		@ElementList(inline=true, required=false)
		public List<Category> category;
	}
	
	@Root static public class Info
	{
		@Attribute public String date;
		@Attribute public String time;
	}
	
	@Root static public class Settings
	{
		@Element public Instrumentation instrumentation;
		@Element public Actions actions;
		
		@Root static public class Instrumentation
		{
			@Attribute public String instrumentation_type;
		}
		@Root static public class Actions
		{
			@Attribute public String check_interface;
			@Attribute public String create_new_testrun;
			@Attribute public String execute_test;
			@Attribute public String generate_driver;
		}
	}
	
	@Root static public class Selected_Objects
	{
		@Element public Selected_Object selected_object;
		
		@Root static public class Selected_Object
		{
			@Attribute public String name;
			@Attribute public String type;
		}
	}
	
	@Root static public class Used_Environments
	{
		@Element public Used_Environment used_environment;
		
		@Root static public class Used_Environment
		{
			@Attribute public String name;
		}
	}
	@Root(name="tessyobject") static public class Tessyobject
	{
		@Attribute public String id;
		@Attribute public String level;
		@Attribute(required=false) public String modulepath;
		@Attribute public String name;
		@Attribute(required=false) public String number;
		@Attribute public String success;
		@Attribute public String type;
		
		@Element Testcase_Statistics testcase_statistics;
		@ElementList(inline=true, required=false) List<Tessyobject> tessyobject;
		
		@Element(required=false) public Coverage coverage;
		
		@Root static public class Testcase_Statistics
		{
			@Attribute public String notexecuted;
			@Attribute public String notok;
			@Attribute public String ok;
			@Attribute public String success;
			@Attribute public String total;
		}
		
		
	}
	
	@Root static public class Coverage
	{
		@Element(required=false) public CoverageEntry c0;
		@Element(required=false) public CoverageEntry c1;
		@Element(required=false) public CoverageEntry dc;
		@Element(required=false) public CoverageEntry mcdc;
		@Element(required=false) public CoverageEntry mcc;
		@Element(required=false) public CoverageEntry epc;
		
		@Root static public class CoverageEntry
		{
			@Attribute public String success; 
			@Attribute public double total; 
			@Attribute public double reached; 
			@Attribute public double notreached; 
			@Attribute public double percentage;
		}
	}
	public void writeDox(String filename, String pic_path) throws IOException
	{
		StringWriter fw = new StringWriter();
		fw.write("/**");
		String id_ = selected_objects.selected_object.name.replace("/", "_");
		fw.write("@page " + id_ + " Overview of " + selected_objects.selected_object.name + "\n");
		
		fw.write("@section " + id_ + "-Summ Summary\n");
		
		fw.write("<table>\n");
		fw.write("<tr><td><b>Date</b></td><td>" + info.date + "</td></tr>\n");
		fw.write("<tr><td><b>Time</b></td><td>" + info.time + "</td></tr>\n");
		
		fw.write("<tr><td><b>Successful</b></td><td>" + 	statistic.ok + "</td></tr>\n");
		fw.write("<tr><td><b>Failed</b></td><td>" + 		statistic.notok + "</td></tr>\n");
		fw.write("<tr><td><b>Not Executed</b></td><td>" + 	statistic.notexecuted + "</td></tr>\n");
		fw.write("<tr><td><b>Total test objects</b></td><td>" + statistic.total + "</td></tr>\n");
		fw.write("</table>");

		
		fw.write("@image html " + id_ + "_overall.png\n<center><i>Overall test results</i></center>\n\n");
		
		fw.write("@section " + id_ + "-Cov Satistics\n");
		
		PassPie.makeChart(pic_path + "\\" + id_ + "_execution.png", 
				Double.parseDouble(statistic.ok), 
				Double.parseDouble(statistic.notexecuted), 
				Double.parseDouble(statistic.notok));
		
		fw.write("@image html " + id_ + "_execution.png\n<center><i>Test Case Results for Each Test Object (without Coverage)</i></center>\n\n");
		fw.write("@image html " + id_ + "_c0.png\n<center><i>Statement (C0) Coverage: Total Statements for Each Test Object</i></center>\n\n");
		fw.write("@image html " + id_ + "_c1.png\n<center><i>Statement (C0) Coverage: Total Statements for Each Test Object</i></center>\n\n");
		fw.write("@image html " + id_ + "_decision.png\n<center><i>Decision Coverage: Total Decision Outcomes for Each Test Object</i></center>\n\n");
		fw.write("@image html " + id_ + "_mcdc.png\n<center><i>MC/DC Coverage: Total Condition Combinations for Each Test Objec</i></center>\n\n");
		fw.write("@image html " + id_ + "_mcc.png\n<center><i>MCC Coverage: Total Condition Combinations for Each Test Object</i></center>\n\n");




		fw.write("@section " + id_ + "-Obj Test Objects\n");

		
		StatisticBuilder sb = new StatisticBuilder();
		
		fw.write("<table class=\"fieldtable\">\n<tr><th>Number</th>"
				+ "<th>Name</th>"
				+ "<th>C0</th>"
				+ "<th>C1</th>"
				+ "<th>DC</th>"
				+ "<th>MC/DC</th>"
				+ "<th>MCC</th>"
				+ "<th>EPC</th>"
				+ "<th>Test cases</th>"
				+ "<th>Result</th>"
				+ "</tr>\n");
		
		Integer t[] = new Integer[1];
		t[0] = 0;
		sb.execute(fw,  tessyobject, t);
		sb.writePlots(pic_path + "\\" + id_);		
				
		fw.write("</table>");
		fw.write("@page overviews Overview Reports\n\n");
		fw.write(" + @subpage " + selected_objects.selected_object.name.replace("/", "_") + "\n");
		
		fw.write("*/");
		
		java.io.File f = new java.io.File(filename);
		f.getParentFile().mkdir();
		FileWriter ff = new FileWriter(f);
		ff.write(fw.toString());
		ff.close();
		fw.close();
		
	}

	static class StatisticBuilder 
	{
		PassBar execution = new PassBar();
		PassBar c0 		 = new PassBar();
		PassBar c1 		 = new PassBar();
		PassBar decision = new PassBar();
		PassBar mcdc 	 = new PassBar();
		PassBar mcc 	 = new PassBar();
		
		int overall_pass 	 = 0;
		int overall_fail 	 = 0;
		int overall_not_exec = 0;
		void writePlots(String prefix) throws IOException
		{
			execution.	chartToFile("", prefix + "_execution.png");
			c0.			chartToFile("", prefix + "_c0.png");
			c1.			chartToFile("", prefix + "_c1.png");
			decision.	chartToFile("", prefix + "_decision.png");
			mcdc.		chartToFile("", prefix + "_mcdc.png");
			mcc.		chartToFile("", prefix + "_mcc.png");
			
			PassPie.makeChart(prefix + "_overall.png", overall_pass, overall_not_exec, overall_fail);
			
			
		}
		
		void execute(Writer wr, List<Tessyobject> tos, Integer[] cnt) throws IOException
		{
			if (tos == null)
				return;

			
			for (Tessyobject to : tos)
			{


				wr.write("<tr><td class=\"fieldname\">");
				if (to.type.equals("testobject"))
				{
					++cnt[0];
					wr.write(cnt[0].toString());
					//add the thingies to the statistic.
					execution.addBar(cnt[0].toString(), 
								Double.parseDouble(to.testcase_statistics.ok), 
								Double.parseDouble(to.testcase_statistics.notexecuted), 
								Double.parseDouble(to.testcase_statistics.notok));

					overall_pass += 	Integer.parseInt(to.testcase_statistics.ok);
					overall_not_exec += Integer.parseInt(to.testcase_statistics.notexecuted); 
					overall_fail +=		Integer.parseInt(to.testcase_statistics.notok);
					
					c0.addBar(cnt[0].toString(), to.coverage.c0.reached, 0, to.coverage.c0.notreached);
					c1.addBar(cnt[0].toString(), to.coverage.c1.reached, 0, to.coverage.c1.notreached);
					decision.addBar(cnt[0].toString(), to.coverage.dc.reached, 0, to.coverage.dc.notreached);
					mcdc.addBar(cnt[0].toString(), to.coverage.mcdc.reached, 0, to.coverage.mcdc.notreached);
					mcc.addBar(cnt[0].toString(), to.coverage.mcc.reached, 0, to.coverage.mcc.notreached);
					
				}
				

				if (!to.type.equals("testobject"))
					wr.write("</td>\n<td class=\"fieldname\">" + s(Integer.parseInt(to.level)) + to.name + "</td>\n");
				else
					wr.write("</td>\n<td class=\"fieldname\">" + s(Integer.parseInt(to.level)) + "@ref TestObject-" + to.name + " \"" + to.name + "\"" +  "</td>\n");

				wr.write("<td class=\"fieldname\">" + to.coverage.c0.percentage + "%</td>\n"); 					
				wr.write("<td class=\"fieldname\">" + to.coverage.c1.percentage + "%</td>\n"); 					
				wr.write("<td class=\"fieldname\">" + to.coverage.dc.percentage + "%</td>\n"); 					
				wr.write("<td class=\"fieldname\">" + to.coverage.mcdc.percentage + "%</td>\n"); 				
				wr.write("<td class=\"fieldname\">" + to.coverage.mcc.percentage + "%</td>\n");
				if (!to.type.equals("testobject"))
					wr.write("<td class=\"fieldname\">" + to.coverage.epc.percentage + "%</td>\n");
				else
					wr.write("<td class=\"fieldname\"></td>\n");
				wr.write("<td class=\"fieldname\">" + to.testcase_statistics.ok + " of " + to.testcase_statistics.total + " passed</td>\n");
				


				
				if (to.success.equals("ok"))
					wr.write("<td class=\"fieldname\">@image html success.png\n</td>");
				else
					wr.write("<td class=\"fieldname\">@image html fail.png\n</td>");

				wr.write("</tr>\n");
				
				execute(wr, to.tessyobject, cnt);
				


			}
			
		}
		static String s(int i)
		{
			String s = "";
			for (int c = 0; c<(i*4); c++)
			{
				s+= "&nbsp;";
			}
			return s;
		}
		
	};
}


