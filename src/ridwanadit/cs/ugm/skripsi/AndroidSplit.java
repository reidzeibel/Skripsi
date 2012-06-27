package ridwanadit.cs.ugm.skripsi;

import org.json.*;

import android.util.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AndroidSplit implements TestStr {
	
	public List<Map> parseTL (String str) {
		List<Map> result = new ArrayList<Map>();
		
		try {
			JSONArray entry = new JSONArray(str);
			int length = entry.length();
			
			for (int i = 0; i < length; i++ ){
				Map map = new HashMap();
				
				JSONObject post = entry.getJSONObject(i);
				Iterator ite = post.keys();
				while (ite.hasNext()) {
					String key = (String) ite.next();
					if ("user".equals(key)){
						JSONObject user = post.getJSONObject(key);
						Iterator ite2 = user.keys();
						while (ite2.hasNext()) {
							String key2 = (String) ite2.next();
							map.put("user."+key2, user.get(key2));							
						}
					} else {
						map.put(key, post.get(key));
						//Log.d(key,post.getString(key));
					}
				}
				result.add(map);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public String name() {
		return "org.json";
	}
}
