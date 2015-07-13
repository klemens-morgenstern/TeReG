package tereg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.simpleframework.xml.*;
import org.stringtemplate.v4.ST;


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
				lineBegin = line;
				lineEnd = line;
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
					lineBegin 	= line + Integer.parseInt(m.group(1));
					lineEnd 	= line + Integer.parseInt(m.group(2));
					return;
				} catch (IllegalStateException e) {}

				try {
					Pattern pat = Pattern.compile("@linespan\\{\\s*(-?\\d*)\\s*\\}");
					Matcher m = pat.matcher(Description.value);
					
					m.find();
					Description.value = Description.value.replace(m.group(0),  "");
					lineEnd 	= line + Integer.parseInt(m.group(1));
					return;
				} catch (IllegalStateException e) {}

				
			}
		}
		public void writeDox(Writer fw, String path) throws IOException
		{
			calcLines();
			
			//fw.write("@section " + Type.value + " " + Type.value + "\n\n");
			fw.write("@section " + id + " " + Summary.value + "\n\n");
			
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
			
			
			
			int i = 0;
			for (; i < lineBegin; i++)
				in.readLine();
			
			for (;i <= (lineEnd+1); i++)
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
		FileWriter fw = new FileWriter(filename);
		fw.write("/**");
		fw.write("@page inspection Inspection\n\n");
		
		
		for (ReviewIssue issue : reviewIssue)
		{
			issue.writeDox(fw, path);
		}
		fw.write("*/");
		fw.close();
	}
	
}
