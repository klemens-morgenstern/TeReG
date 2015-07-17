package tereg;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;

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
	public void writeDox(String out) 
	{
		
	}
}
