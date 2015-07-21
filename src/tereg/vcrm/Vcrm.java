package tereg.vcrm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;








public class Vcrm 
{
	
	public Map<String, JSONObject> map;
	public String filename;
	public Vcrm(String target) throws IOException, ParseException
	{
		
		filename = target;
		
		String text = new String(Files.readAllBytes(Paths.get(target)), StandardCharsets.UTF_8);

		JSONParser parser= new JSONParser();

		
		Object obj = parser.parse(text);

		JSONObject jo = (JSONObject)obj; 
		
		map = new HashMap<String, JSONObject>();
	
		for (Object key : jo.keySet())
		{
			if (key == null)
				continue;
			JSONObject ja = (JSONObject)jo.get(key);
			if (ja == null)
				ja = new JSONObject();
			
			map.put((String)key, ja);
		}
	}
	public void save() throws IOException
	{
		JSONObject obj=new JSONObject();
		
		for (String arr : map.keySet())
		{
			obj.put(arr, map.get(arr));
		}
		
		FileWriter fw = new FileWriter(filename);
		fw.write(obj.toString());
		
		fw.close();
	}
	
}
