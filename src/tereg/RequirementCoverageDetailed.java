
package tereg;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class RequirementCoverageDetailed 
{
	@Attribute public String description;
	@Attribute public String filter;
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

		}
	}
}
