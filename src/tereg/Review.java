package tereg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.simpleframework.xml.*;
import org.stringtemplate.v4.ST;

import tereg.vcrm.Vcrm;


@Root
public class Review 
{
	@Attribute public String id;
	@ElementList(inline=true, entry="ReviewIssue") List<ReviewIssue> reviewIssue;
	
		
	@Root
	public static class ReviewIssue
	{
		@Attribute public String id;
		@Element(name="ReviewIssueMeta") public ReviewIssueMeta reviewIssueMeta;
		@Element public OptText ReviewerId;
		@Element public OptText AssignedTo;
		@Element(name="File") public File file;
		@Element public OptText Type;
		@Element public OptText Severity;
		@Element public OptText Summary;
		@Element public OptText Description;
		@Element public OptText Annotation;
		@Element public OptText Revision;
		@Element public OptText Resolution;
		@Element public OptText Status;
		
		
		public int lineBegin;
		public int lineEnd;
		public void calcLines()
		{
			int line = file.line;
			if (Description.value == null)
			{
				lineBegin = line-1;
				lineEnd = line-1;
			}
			else
			{
				lineBegin 	= line;
				lineEnd 	= line;
				try {
					Pattern pat = Pattern.compile("@linespan\\{\\s*(-?\\d*)\\s*,\\s*(-?\\d*)\\s*\\}");
					Matcher m = pat.matcher(Description.value);
					
					m.find();
					Description.value = Description.value.replace(m.group(0),  "");
					lineBegin 	= line + Integer.parseInt(m.group(1)) - 1;
					lineEnd 	= line + Integer.parseInt(m.group(2));
					return;
				} catch (IllegalStateException e) {}

				try {
					Pattern pat = Pattern.compile("@linespan\\{\\s*(-?\\d*)\\s*\\}");
					Matcher m = pat.matcher(Description.value);
					
					m.find();
					Description.value = Description.value.replace(m.group(0),  "");
					lineEnd 	= line + Integer.parseInt(m.group(1)) - 1;
					return;
				} catch (IllegalStateException e) {}

			}
		}
		public void writeDox(Writer fw, String path) throws IOException
		{
			calcLines();
			
			//fw.write("@section " + Type.value + " " + Type.value + "\n\n");
			fw.write("@subsection " + id + " " + Summary.value + "\n\n");
			
			ST st = new ST(	"|  Type  |  Creation Date | Last Modificatied |  Reviewer  |  Annotation  |  Revision  |  Resolution  |\n"
						+	"|--------|----------------|-------------------|------------|--------------|------------|--------------|\n"
						+	"| <Type> | <CreationDate> | <LastModified>    | <Reviewer> | <Annotation> | <Revision> | <Resolution> |\n");

			st.add("CreationDate", reviewIssueMeta.CreationDate.value.replace("::",  ""));
			st.add("LastModified", reviewIssueMeta.LastModificationDate.value.replace("::",  ""));
			st.add("Reviewer", 		ReviewerId.value);
			st.add("Annotation", 	Annotation.value);
			st.add("Revision", 		Revision.value);
			st.add("Resolution", 	Resolution.value);
			st.add("Type", 			Type.value);
			
			fw.write(st.render() + "\n\n");
			
			fw.write("\\code{.c} \n");
			
			
			BufferedReader in
			   = new BufferedReader(new FileReader(path + file.value.replace("/","\\")));
			
			
			
			int i = 1;
			for (; i < lineBegin; i++)
				in.readLine();
			
			for (;i <= (lineEnd); i++)
				fw.write(in.readLine() + "\n");

			fw.write("\\endcode\n");
			fw.write("<center><i>File " + file.value + 
					" Lines " + lineBegin + ":" + lineEnd + "</i></center>\n");
			
			
			fw.write(Description.value + "\n\n");
			in.close();
		}
	}
	
	@Root
	public static class ReviewIssueMeta
	{
		
		@Element public Date CreationDate;
		@Element public Date LastModificationDate;
		@Root
		public static class Date
		{
			@Attribute public String format;
			@Text(required=false) public String value;
		}
	}
	@Root
	public static class File
	{
		@Attribute public int line;
		@Text(required=false) public String value;
	}
	public static class OptText
	{
		@Text(required=false) public String value;
	}

	void writeDox(String filename, String path) throws IOException
	{
		java.io.File f = new java.io.File(filename);
		f.getParentFile().mkdir();
		
		FileWriter fw = new FileWriter(f);
		
		
		fw.write("/**");
		fw.write("@page inspection Inspection\n\n");
		
		Collections.sort(reviewIssue, 
				new Comparator<ReviewIssue>() {
					@Override
					public int compare(ReviewIssue p1, ReviewIssue p2) {
						return p1.Type.value.compareTo(p2.Type.value);
					}
				});
		String current = "";
		for (ReviewIssue issue : reviewIssue)
		{
			if (current.compareTo(issue.Type.value) != 0)
			{
				current = issue.id;
				fw.write("@section " + current + " " + current + "\n\n");
			}
			issue.writeDox(fw, path);
		}
		fw.write("*/");
		fw.close();
	}

	public void buildVcrm(Vcrm vcrm) 
	{
		for (ReviewIssue ri : reviewIssue)
		{
			
			//get the id
			try {
				Pattern pat = Pattern.compile("@insp\\{([^}]*)\\}");
				Matcher m = pat.matcher(ri.Description.value);
				
				m.find();
				String id = m.group(1);
				
				
				JSONArray arr = vcrm.map.get(id);
				if (arr != null)
				{
					if (!arr.contains("inspection"))
					{
						arr.add("inspection");
					}
				}
				
			} catch (IllegalStateException e) {}
			
			
			

		}
	}
	
}
