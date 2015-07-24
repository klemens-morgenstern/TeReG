package tereg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.simpleframework.xml.*;
import org.stringtemplate.v4.ST;

import tereg.vcrm.Vcrm;

@Root
public class CompileTests 
{
	@ElementList(inline=true, entry="CompileTest") List<CompileTest> compileTests;
	
	//@Root	
	public static class CompileTest
	{
		@Attribute public String id;
		@Attribute public String title;
		@Attribute public String type;
		@Attribute public String source;
		@Attribute public String log;
		@Text(required=false) public String description;

	
		public void writeDox(Writer fw, String path) throws IOException
		{
			
			//fw.write("@section " + Type.value + " " + Type.value + "\n\n");
			fw.write("@section " + id + " " + title + "\n\n");
			
						
			BufferedReader in
			   = new BufferedReader(new FileReader(path + "\\" + source.replace("/","\\")));
			
			fw.write("\\code{.c} \n");
			
			String buf = null;
			while ((buf = in.readLine()) != null)
			{
				fw.write(buf + "\n");
			}

			fw.write("\\endcode\n");

			
			fw.write("<center><i>Test source: " + source + "</i></center>\n");
			
			fw.write(description);
				

			fw.write("\n");
			
			in.close();
			try 
			{
				in = new BufferedReader(new FileReader(path + "\\" +  log.replace("/","\\")));
			
				fw.write("\\code\n");
				while ((buf = in.readLine()) != null)
				{
					fw.write(buf + "\n");
				}
				fw.write("\\endcode\n");
				
				fw.write("<center><i>Test result: " + source + "</i></center>\n");
				
				in.close();

			}
			catch (FileNotFoundException fn)
			{
				fw.write("<center> @image html fail.png \n Test failed </center>\n");
			}
			
			
		}
	}
	
	void writeDox(String filename, String path) throws IOException
	{
	
		StringWriter fw = new StringWriter();
		
		
		fw.write("/**");
		fw.write("@page compile-test Compile Test\n\n");
		fw.write("@brief Descriptions of the all compile tests.\n");
		fw.write("@details @tableofcontents\n");
		
		
		Collections.sort(compileTests, 
				new Comparator<CompileTest>() {
					@Override
					public int compare(CompileTest p1, CompileTest p2) {
						return p1.id.compareTo(p2.id);
					}
				});

		for (CompileTest issue : compileTests)
		{
			issue.writeDox(fw, path);
		}
		fw.write("*/");
		
		
		java.io.File f = new java.io.File(filename);
		f.getParentFile().mkdir();
		FileWriter ff = new FileWriter(f);
		ff.write(fw.toString());
		ff.close();
		fw.close();
		
	}

	public void buildVcrm(Vcrm vcrm, String path) 
	{
		for (CompileTest ri : compileTests)
		{
			boolean success = false;
			
			String pt = path + "\\"+ ri.log.replace("/","\\");
			
			System.err.println(pt);
			
			File f = new File(path + "\\"+ ri.log.replace("/","\\"));
			
			success = f.isFile() && !f.isDirectory();
			//get the id
			try {
				Pattern pat = Pattern.compile("@comptest\\{([^}]*)\\}");
				Matcher m = pat.matcher(ri.description);
				
				while (m.find())
				{
					String id = m.group(1);
				
					//TODO: No fail is possible atm. 
				
					JSONObject obj = vcrm.map.get(id);
					if (obj != null)
					{
						if (!obj.containsKey("compile_test"))
						{
							obj.put("compile_test", success);
						}
					}
				}
				
			} catch (IllegalStateException e) {}
			
			
			

		}
	}
	
}
