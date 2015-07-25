package tereg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.ObjectInputStream.GetField;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;

import tereg.DetailsReportXml.Testobject.Testcase;
import tereg.DetailsReportXml.Testobject.Teststep;
import tereg.DetailsReportXml.Testobject.Teststep.Call_Trace.Trace_Entry;

@Root
public class DetailsReportXml 
{
	@Attribute public String xml_version;
	@Attribute public String tessy_version;
	@Attribute public String testobject_name;
	@Attribute public String success;
	
	@Element public Summary summary;
	@Element public Module module;
	@Element(required=false) public Usercode usercode;
	@Element public Testobject testobject;
	@Root static public class Summary
	{
		@Element public Info info;
		@Element public Comments comments;
		@Element public Statistic statistic;
		@Element(name="interface") public Interface interface_;
		@Element public Properties properties;
		@ElementList public List<Attribute_> attributes;
		
		@Element(required=false) public String cte;
		@Attribute(required=false, name="cte_file") public String cte_file_attr;
		
		@Element(required=false) public String cte_file;
		@ElementList(required=false) public List<Cte_Node> cte_data;
	
		@ElementList(required=false) public List<Coverage> coverage;
		@ElementList(required=false) public List<Requirement> requirements;
		
		@Root static public class Cte_Node
		{
			@Attribute public int id;
			@ElementList public List<Input> inputs;
			@ElementList public List<Result> results;
			@Root static public class Input 
			{
				@Attribute public String name;
				@Attribute(required=false) public String unit;
				@Attribute public String id;
				@Attribute(required=false) public String value;
				@Attribute(required=false) public String use;
				@Attribute(required=false) public String dynamic;
				@Attribute(required=false) public String size;

			}
			@Root static public class Result 
			{
				@Attribute public String name;
				@Attribute(required=false) public String unit;
				@Attribute public String id;
				@Attribute(required=false) public String expected_value;
				@Attribute(required=false) public String actual_value;
				@Attribute(required=false) public String success;
				@Attribute(required=false) public String use;
				@Attribute(required=false) public String dynamic;
				@Attribute(required=false) public String size;

			}
		}
		@Element(required=false) public String cte_node;
		
		@Root static public class Info
		{
			@Attribute public String project_name;
			@Attribute public String module_name;
			@Attribute public String testobject_name;
			@Attribute public String date;
			@Attribute public String time;
			@Attribute public String user;
			@Attribute public String host;

			@Attribute(required=false) public String cte_file;

		}
		@Root static public class Comments
		{
			
		}
		@Root static public class Statistic
		{
			@Attribute public String total;
			@Attribute public String ok;
			@Attribute public String ok_list;
			@Attribute public String notok;
			@Attribute public String notok_list;
			@Attribute public String notexecuted;
			@Attribute public String notexecuted_list;
		}
		
		@Root static public class Coverage
		{
			@Attribute public String name; 
			@Attribute public String success; 
			@Attribute public double total; 
			@Attribute public double reached; 
			@Attribute public double notreached; 
			@Attribute public double percentage;
		}
	}
	
	@Root static public class Interface
	{
		@Element public Mod mod;
		@ElementList public List<Func> funcs;

		@Root static public class Mod
		{
			@Attribute public String id;
			@Attribute public String lid;
			@Attribute public String opt;
			@Attribute public String ver;
			@ElementList(required=false) public List<Func> funcs;
			@Element(required=false) public Types types;
			
			
			@ElementList(required=false) public List<Var> vars;
			@ElementList(required=false) public List<Define> defines;
			
			@Root static public class Func
			{
				@Attribute(required=false) public String cmp;
				@Attribute(required=false) public String empty;
				@Attribute 				   public String id;
				@Attribute(required=false) public String nam;
				@Attribute(required=false) public String scp;
				@Attribute(required=false) public String src;
				@Attribute(required=false) public String use;
				@Attribute(required=false) public String tmd;
				@Attribute(required=false) public String cpl;

			}
			@Root static public class Types
			{
				@ElementListUnion({ @ElementList(entry="comp",   inline=true, required=false, type=Comp.class),
									@ElementList(entry="enum",   inline=true, required=false, type=Enum.class),
									@ElementList(entry="struct", inline=true, required=false, type=Struct.class),
									@ElementList(entry="union",  inline=true, required=false, type=Union.class)
								}) public List<Type> types;
				
				@Root static public class Type 
				{
					@Attribute public int id;
					@Attribute public String nam;
				}
				@Root static public class Comp extends Type
				{
					@Attribute(required=false) public String val;
					@Attribute(required=false) public String vdf;
					@Attribute(required=false) public int siz;
					@Attribute(required=false) public int tc;
					@Attribute(required=false) public String tmd;
					@Attribute(required=false) public String trf;
					@Attribute(required=false) public String hix;
					@Attribute(required=false) public String lox;
					
					@Element(required=false, name="type") public CompType type;
					
					@Root
					static public class CompType
					{
						@Attribute public String tc;
						@Attribute(required=false) public String trf;
						@Attribute(required=false) public String tmd;
						@ElementList(inline=true, required=false) public List<Fpr> fpr;
						@ElementList(inline=true, required=false) public List<Fpp> fpp;
						@Root static public class Fpr
						{
							@Attribute(required=false) public String trf;
							@Attribute public String tc;
							@Attribute(required=false) public String tmd;

						}
						@Root static public class Fpp
						{
							@Attribute public String nam;
							@Attribute public String tc;
							@Attribute(required=false) public String trf;
							@Attribute(required=false) public String tmd;
							@Element(required=false) public FppType type;
							
							static public class FppType
							{
								@Attribute(required=false) public String name;
								@Attribute(required=false) public String tc;
								@Attribute(required=false) public String trf;

							}
						}
					}
					

				}
				@Root static public class Enum extends Type
				{
					@Attribute public String ttg;
					@ElementList(inline=true, required=false) public List<Comp> comp;
				}
				@Root static public class Struct extends Type
				{
					@Attribute public int ttg;
					@ElementList(inline=true, required=false) public List<Comp> comp;
				}
				@Root static public class Union extends Type
				{
					@Attribute public int ttg;
					@ElementList(inline=true, required=false) public List<Comp> comp;
				}
			}
		
			
			@Root static public class Var 
			{
				@Attribute public int id;
				@Attribute public String nam;
				@Attribute public String scp;
				@Attribute public String src;
				@Attribute public String tc;
				@Attribute(required=false) public String hix;
				@Attribute(required=false) public String lox;
				@Attribute(required=false) public String trf;
				@Attribute(required=false) public String tmd;
				@Element(required=false) public VarType type;
				
				@Root static public class VarType
				{
					@Attribute(required=false) public String tc;
					@Attribute(required=false) public String trf;
					@Attribute(required=false) public String tmd;
					
					
					@Element(required=false) public Fpr fpr;
					@Element(required=false) public Fpp fpp;

					@Root static public class Fpr
					{
						@Attribute(required=false) public String tc;
					}
					@Root static public class Fpp
					{
						@Attribute(required=false) public String nam;
						@Attribute(required=false) public String tc;
						@Attribute(required=false) public String tmd;
					}
				}

			}
			@Root static public class Define 
			{
				@Attribute public String id;
				@Attribute public String nam;
				@Attribute(required=false) public String val;
			}
		}
		
		@Root static public class Func
		{
			@Attribute public String id;
			@Attribute public String nam;
			@Attribute(required=false) public String tmd;

			@Element public Ret ret;
			@ElementList(required=false) public List<Call> calls;
			@ElementList(required=false) public List<Parm> parms;
			@ElementList(required=false) public List<Var> vars;
			@Attribute(required=false) public String cpl;
			
			@Root static public class Ret 
			{
				@Attribute public String id;
				@Attribute(required=false) public String ips;
				@Attribute(required=false) public String pas;
				@Attribute public String tc;
				@Attribute(required=false) public String tmd;
				@Attribute(required=false) public String trf;
				@ElementList(required=false, inline=true) public List<Comp> comp;
				@Element(required=false) public Type type;
				@Attribute(required=false) public String df;
		}
			@Root static public class Comp
			{
				@Attribute public int id;
				@Attribute(required=false) public int ips;
				@Attribute(required=false) public int pas;
				@ElementList(required=false, inline=true) List<Comp> comp;
				@Element(required=false) public Type type;
				@Attribute(required=false) public String df;

			}
			@Root static public class Type
			{
				@Attribute(required=false) public String tc;
				@Attribute(required=false) public String trf;
				@Attribute(required=false) public String pas;
				@Attribute(required=false) public String ips;
				@Attribute(required=false) public String scp;
				@Attribute(required=false) public String tmd;
				@Attribute(required=false) public String df;

				@Element(required=false) public Fpr fpr;
				@Element(required=false) public Fpp fpp;
				@ElementList(required=false, inline=true) public List<Comp> comp;
				
			}
			@Root static public class Call 
			{
				@Attribute(required=false) public String comp;
				@Attribute public String id;
				@Attribute(required=false) public String pas;
				@Attribute public String use;
			}
			@Root static public class Parm 
			{
				@Attribute public int id;
				@Attribute(required=false) public String tmd;
				@Attribute(required=false) public String ips;
				@Attribute public String nam;
				@Attribute(required=false) public String pas;
				@Attribute public String tc;
				@Attribute(required=false) public String trf;
				@Attribute(required=false) public String hix;
				@Attribute(required=false) public String lox;
				@Attribute(required=false) public String siz;
				@Attribute(required=false) public String df;
				@Element(required=false) public Type type;
			}
			@Root static public class Var 
			{
				@Attribute public int id;
				@Attribute(required=false) public String ips;
				@Attribute(required=false) public String pas;
				@Attribute public String scp;
				@Attribute(required=false) public String crf;
				@ElementList(required=false, inline=true) public List<Comp> comp;
				@Element(required=false) public Type type;

			}
		}
	}
	@Root static public class Properties
	{
		@Attribute public String directory;
		@Attribute public String config;
		@Attribute public String project_root;
		@Attribute public String source_root;
		@Attribute public String project_description;
		@Attribute public String environment;
		@Attribute public String kind_of_test;
		
		@ElementList(inline=true) public List<Source> source;
		@ElementList(inline=true) public List<Header> header;
		
		@Root static public class Source
		{
			@Attribute public String file;
			@Attribute public String compiler_options;
			@Attribute public String sha1;

		}		
		@Root static public class Header
		{
			@Attribute public String file;
			@Attribute public String sha1;
		}
	}
	@Root static public class Attribute_
	{
		@Attribute public String name;
		@Attribute public String value;
	}
	@Root static public class Module 
	{
		@ElementList(required=false) public List<Requirement> requirements;
	}
	@Root static public class Usercode
	{
		@ElementList public List<Stub> stubs;
		

	}
	@Root static public class Stub
	{
		@Attribute public String name;
		@Attribute public String head;
		@Attribute public String body;
	}
	
	@Root static public class Testobject
	{
		@Attribute public String success;
		@ElementList(inline=true, required=false) public List<Testcase> testcase;
		@ElementList(required=false) public List<Stub> stubs;
		@ElementList(required=false) public List<Requirement> requirements;

		
		@Root static public class Testcase
		{
			@Attribute public int id;
			@Attribute public int dbid;
			@Attribute(required=false) public String name;

			@Attribute(required=false) public String success;
			@Attribute(required=false) public String testdata_success;
			@Attribute(required=false) public String calltrace_success;
			@Attribute(required=false) public String evalmacro_success;
			@Attribute(required=false) public String specification;
			
			@ElementList(inline=true) public List<Teststep> teststep;
			@ElementList(required=false) public List<Requirement> requirements;

		}
		@Root static public class Teststep
		{
			@Attribute public String id;
			@Attribute public int dbid;
			@Attribute(required=false) public String dynamic;

			@Attribute(required=false) public String testdata_success;
			@Attribute(required=false) public String calltrace_success;
			@Attribute(required=false) public String evalmacro_success;

			@Attribute public int repeat_count;
			@Attribute(required=false) public String success;

			@Element(required=false) public Call_Trace call_trace;
			@ElementList public List<Input> inputs;
			@ElementList(required=false) public List<Result> results;
			
			@Element(required=false) public Prologs_Epilogs prologs_epilogs;
			@ElementList(required=false) public List<Stub> stubs;
			@ElementList(required=false) public List<Requirement> requirements;

			@Root static public class Call_Trace 
			{
				@Attribute public int teststep_id;
				@Attribute public String success;
				@ElementList(inline=true) public List<Trace_Entry> trace_entry;
				
				@Root(name="trace_entry") static public class Trace_Entry
				{
					@Attribute public String actual;
					@Attribute public int actual_count;
					@Attribute public String expected;
					@Attribute public int expected_count;
					@Attribute public String success;
				}
			}
			@Root static public class Input 
			{
				@Attribute public String name;
				@Attribute(required=false) public String unit;
				@Attribute public String id;
				@Attribute(required=false) public String value;
				@Attribute public String use;
				@Attribute(required=false) public String dynamic;
				@Attribute(required=false) public String size;


			}
			@Root static public class Result 
			{
				@Attribute public String name;
				@Attribute(required=false) public String unit;
				@Attribute(required=false) public String id;
				@Attribute(required=false) public String expected_value;
				@Attribute(required=false) public String actual_value;
				@Attribute(required=false) public String success;
				@Attribute(required=false) public String dynamic;
				@Attribute(required=false) public String size;
				@Attribute(required=false) public String evalmacro;
				@Attribute(required=false) public String use;
			}
			
			@Root static public class Prologs_Epilogs
			{
				@Element(required=false) public Log prolog;
				@Element(required=false) public Log epilog;
				@Root static public class Log
				{
					@Attribute public String type;
					@Attribute public String text;
				}
			}
		}
	}
	@Root static public class Fpr
	{
		@Attribute(required=false) public String tc;
	}
	@Root static public class Fpp
	{
		@Attribute(required=false) public String nam;
		@Attribute(required=false) public String tc;
		@Attribute(required=false) public String tmd;
	}
	@Root static public class Requirement
	{
		@Attribute public String doc_name;
		@Attribute public String doc_alias;
		@Attribute public String content_type;
		@Attribute public String identifier;
		@Attribute public String id;
		@Attribute public String version; 
		@Attribute public String minor_version;
		@Attribute(name="short") public String _short;
		@Attribute public String text;
		@Attribute public String order_no;
	}
	
	static String CovTableEntry(double value)
	{
		if (value == 100)
			return "<td class=\"fieldname\" bgcolor=\"#00FF00\">" + value +  "%</td>\n";
		else
			return "<td class=\"fieldname\" bgcolor=\"#FF0000\">" + value + "%</td>\n";

	}
	
	public void writeDox(String filename, String sourceFile, String Imagepath) throws IOException
	{

		StringWriter fw = new StringWriter();
		fw.write("/**\n");
		fw.write("@page TestObject-" + testobject_name + " Testreport for " + testobject_name + "\n");
		fw.write("@brief Test of @ref " + testobject_name + "\n");

		fw.write("@details\n");
		
		fw.write("@section TestObject-" + testobject_name + "-summary Summary\n\n");
		
		fw.write("<table><tr><td>Project</td><td>" + summary.info.project_name + "</td></tr>\n");
		fw.write("<tr><td>Module</td><td>@ref " + summary.info.module_name + "</td></tr>\n");
		fw.write("<tr><td>Test Object</td><td>@ref " + summary.info.testobject_name + "</td></tr></table>\n");
		
	
		
		fw.write("<table class=\"fieldtable\"><tr><th colspan=\"2\">Instrumentation: Test Object Only</th></tr>\n");
		//dangerous, because it is hard coded. but should work.
		fw.write("<tr><td class=\"fieldname\">Statement (C0) Coverage</td>" + CovTableEntry(summary.coverage.get(0).percentage) + "</tr>\n");
		fw.write("<tr><td class=\"fieldname\">Decision Coverage</td>" 		+ CovTableEntry(summary.coverage.get(2).percentage) + "</tr>\n");
		fw.write("<tr><td class=\"fieldname\">Branch (C1) Coverage</td>" 	+ CovTableEntry(summary.coverage.get(1).percentage) + "</tr>\n");
		fw.write("<tr><td class=\"fieldname\">MCC Coverage</td>" 			+ CovTableEntry(summary.coverage.get(4).percentage) + "</tr>\n");
		fw.write("<tr><td class=\"fieldname\">MC/DC Coverage</td>" 			+ CovTableEntry(summary.coverage.get(3).percentage) + "</tr>\n");
		fw.write("</table>\n\n");
		

		
		fw.write("<table class=\"fieldtable\"><tr><th colspan=\"2\">Statistics</th></tr>\n");
		fw.write("<tr><td class=\"fieldname\">Total Successful</td><td class=\"fieldname\">" 	+ summary.statistic.total + "</td></tr>\n");
		if (Integer.parseInt(summary.statistic.ok) == Integer.parseInt(summary.statistic.total))
			fw.write("<tr><td class=\"fieldname\">Successful</td><td class=\"fieldname\" bgcolor=\"#00FF00\">" 			+ summary.statistic.ok + "</td></tr>\n");
		else
			fw.write("<tr><td class=\"fieldname\" >Successful</td><td class=\"fieldname\"bgcolor=\"#FF0000\">" 			+ summary.statistic.ok + "</td></tr>\n");
		if (Integer.parseInt(summary.statistic.notok) >= 0)
			fw.write("<tr><td class=\"fieldname\">Failed</td><td class=\"fieldname\" bgcolor=\"#00FF00\">" 				+ summary.statistic.notok + "</td></tr>\n");
		else
			fw.write("<tr><td class=\"fieldname\">Failed</td><td class=\"fieldname\" bgcolor=\"#FF0000\">" 				+ summary.statistic.notok + "</td></tr>\n");
		
		
		if (summary.statistic.notexecuted != "0")
			fw.write("<tr><td class=\"fieldname\">Not executed</td><td class=\"fieldname\" bgcolor=\"#3399FF\">" 		+ summary.statistic.notexecuted + "</td></tr>\n");
		else
			fw.write("<tr><td class=\"fieldname\">Not executed</td><td class=\"fieldname\">" 		+ summary.statistic.notexecuted + "</td></tr>\n");
		
		fw.write("</table>\n\n");
		
		
		if (summary.requirements != null)
		{
			fw.write("Linked Requirements:\n");
			for (Requirement req : summary.requirements)
			{
				fw.write(" + @reqf{" + req._short + "}\n");
			}
		}
		
		if (summary.info.cte_file != null)
		{
		
			fw.write("@section TestObject-" + testobject_name + "-cte Classification Tree Specification\n");
			String sc[] = sourceFile.split("/");
		
			//it only writes after the CTE image.
			boolean write = false;
			
			BufferedReader ht_r = new BufferedReader(new FileReader(find_file(".", sc[sc.length-1].replace(".xml", ".html"))));

			String line = null;
			while ((line = ht_r.readLine()) != null)
			{
				if (line.matches("<IMG SRC=\\\"+([^\"]+)\\\">"))
				{
					try {
						Pattern pat = Pattern.compile("<IMG SRC=\\\"+([^\"]+)\\\">");
						Matcher m = pat.matcher(line);
						
						if (m.find())
						{
							String id = m.group(1); //thats the filename
						
							
							File cte_img = find_file(".", id);
							
							Files.copy(cte_img.toPath(), new File(Imagepath + "/" + cte_img.getName()).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
							
							fw.write("@image html " + cte_img.getName() + "\n");
							write = true;
							fw.write("@htmlonly\n");

						}
						
					} catch (IllegalStateException e) {}
					
				}
				else if (write)
				{
					fw.write(line + "\n");
				}
			}
			fw.write("@endhtmlonly\n");
		}
		if (usercode != null)
		{
		
			fw.write("@section TestObject-" + testobject_name + "-usercode Usercode\n\n");
			
			for (Stub s : usercode.stubs)
			{
				fw.write("Stub of function @ref " + s.name + "\n\n");
				
				
				if (s.head != null)
				{
					fw.write("@code{.c}\n");
					fw.write(s.head);
					fw.write("\n@endcode\n");
				}
				if (s.body != null)
				{
					fw.write("@code{.c}\n");
					fw.write(s.body);
					fw.write("\n@endcode\n");
				}
		}
		
		}
		fw.write("@section TestObject-" + testobject_name + "-details Test Execution Details\n\n");
		
		
		for (Testcase tc : testobject.testcase)
		{
			fw.write("@subsection TestCase-" + testobject_name + "-" +  tc.id + " Testcase " + tc.id );
			
			if (tc.name == null)
				fw.write("\n");
			else
				fw.write(" : " + tc.name + "\n");
			
			fw.write("<table>\n");
			
			if (tc.specification != null)
			{
				fw.write("<tr><td><b>Specification</b></td><td>\n" + tc.specification + "</td></tr>");
			}
			
			if (tc.requirements != null)
			{
				fw.write("<tr><td><b>Linked Requirements</b></td><td>\n");
				
				
				
				for (Requirement r : tc.requirements)
				{
					fw.write("\n  - @reqf{" + r._short + "}</td></tr>\n");
				}
			}
			fw.write("</table>\n");
			
			
			for (Teststep ts : tc.teststep)
			{
				fw.write("<table class=\"fieldtable\"><tr><th colspan=4>");
				fw.write("Test Step " + ts.id + " (Repeat Count = " + ts.repeat_count + ")</th></tr>\n");
				fw.write("<tr><td bgcolor=\"#CCCCCE\"><b>Name </b></td><td colspan=\"3\" bgcolor=\"#CCCCCE\"><b>Input Value</b></td></tr>\n");
				
			
				for (Teststep.Input i : ts.inputs)
				{
					fw.write("<tr><td class=\"fieldname\">" + i.name + "</td>");
					fw.write("<td colspan=\"3\" class=\"fieldname\">" + i.value + "</td></tr>");
				}
				fw.write("<tr><td bgcolor=\"#CCCCCE\"><b>Name </b></td>");
				fw.write("<td bgcolor=\"#CCCCCE\"><b>Actual Value</b></td>\n");
				fw.write("<td bgcolor=\"#CCCCCE\"><b>Expected Value</b></td>\n");
				fw.write("<td bgcolor=\"#CCCCCE\"><b>Result</b></td></tr>\n");

				
				for (Teststep.Result r : ts.results)
				{
					fw.write("<tr><td class=\"fieldname\">" + r.name + "</td>"); 		
					fw.write("<td class=\"fieldname\">" + r.actual_value + "</td>");	
					fw.write("<td class=\"fieldname\">" + r.expected_value + "</td>");	
					if (r.success != null)
					{
						if (r.success.equals("ok"))					
							fw.write("<td  class=\"fieldname\">@image html success.png\n</td>");
						else
							fw.write("<td  class=\"fieldname\">@image html fail.png\n</td>");
					}
					else
						fw.write("<td  class=\"fieldname\"></td>");

					fw.write("</tr>\n");
				}
				
				fw.write("</table>\n\n");
				
				
				///calltrace
				if (ts.call_trace != null)
				{
					fw.write("<table class=\"fieldtable\"><tr><th colspan=5>Test Step Call Trace</th></tr>\n");
					
					fw.write("<tr><td bgcolor=\"#CCCCCE\"><b>Actual Function</b></td>\n");
					fw.write("<td bgcolor=\"#CCCCCE\"><b>Count</b></td>\n");
					fw.write("<td bgcolor=\"#CCCCCE\"><b>Expected Function </b></td>\n");
					fw.write("<td bgcolor=\"#CCCCCE\"><b>Count</b></td>\n");
					fw.write("<td bgcolor=\"#CCCCCE\"><b>Result</b></td></tr>\n");
					
					for (Trace_Entry ct : ts.call_trace.trace_entry)
					{
						fw.write("<tr><td class=\"fieldname\">" + ct.actual + "</td>\n");
						fw.write("<td class=\"fieldname\">" + ct.actual_count + "</td>\n");
						fw.write("<td class=\"fieldname\">" + ct.expected + "</td>\n");
						fw.write("<td class=\"fieldname\">" + ct.expected_count + "</td>\n");
						if (ct.success.equals("ok"))
							fw.write("<td class=\"fieldname\">@image html success.png\n</td>");
						else
							fw.write("<td class=\"fieldname\">@image html fail.png\n</td>");
	
					}
					fw.write("</table>\n");
				}
			}
			
		}

		
		fw.write("@page test-detail Detailed Test Reports\n");
		fw.write(" + @subpage TestObject-" + testobject_name + "\n\n");
		fw.write("*/");
		
		
		
		java.io.File f = new java.io.File(filename);
		f.getParentFile().mkdir();
		FileWriter ff = new FileWriter(f);
		ff.write(fw.toString());
		ff.close();
		fw.close();
	}
	
    static File find_file( String path, String match ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return null;

        for ( File f : list ) {
            if ( f.isDirectory() ) 
            {
            	File ret = find_file( f.getAbsolutePath() , match);
            	if (ret != null)
            		return ret;
            }
            else 
            {
            	if (f.getName().equals(match))
            		return f;
            		
            }
        }
        return null;
    }

}
