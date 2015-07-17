package tereg;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import tereg.RequirementCoverageOverview.Requirementdocument;

@Root
public class OverviewReportXml 
{
	@Attribute public String success;
	@Attribute public String tessy_version;
	@Attribute public String xml_version;
	
	@Element Statistic statistic;
	
	@Element Info info;
	
	@Element Settings settings;
	
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
		FileWriter fw = new FileWriter(filename);
		fw.write("/**");
		/*fw.write("@page " + info.projectname + "\n\n");
		

		fw.write(getInfo());
		
		
		for (Requirementdocument doc : requirementdocuments)
		{
			fw.write(doc.getDox() + "\n\n");
		}*/
		fw.write("*/");
		fw.close();
	}
	
}


