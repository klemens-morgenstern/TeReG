
package tereg;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import tereg.Review.ReviewIssue;
import tereg.vcrm.Vcrm;

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
		java.io.File f = new java.io.File(out);
		f.getParentFile().mkdir();
		
		FileWriter fw = new FileWriter(f);
		
		

		fw.close();
	}
	
	public void buildVcrm(Vcrm vcrm) 
	{
		
	}
	
}
