package ridwanadit.cs.ugm.skripsi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import android.R.string;
import android.util.Log;

public class Jackson implements testJson {
	long start,finish;
	static JsonFactory f = new JsonFactory();
//	Map hello = new HashMap();
//	
//	public List<Map> parsed (JsonParser jp){
//		List<Map> result = new ArrayList<Map>();
//		try {
//			if (jp.getCurrentToken().equals(JsonToken.START_ARRAY)){
//				while (jp.nextToken()!=JsonToken.END_ARRAY){
//					parsed(jp);
//				}
//			} else if (jp.getCurrentToken().equals(JsonToken.START_OBJECT)){
//				while (jp.nextToken()!=JsonToken.END_OBJECT){
//					parsed(jp);
//				}
//			} else {
//				String key = jp.getCurrentName();
//				hello.put(key, jp.getText());
//				//Log.d(key,jp.getText());
//				Log.d("Coba", hello.get("text").toString());
//				result.add(hello);
//			}
//			
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//		return result;
//	}
//	
//	public List<Map> parseTL(InputStream is) {
//        List<Map> result = new ArrayList<Map>();
//		try {
//    		JsonParser jp = f.createJsonParser(is);
//    		jp.nextToken();
//    		result=parsed(jp);
//    		jp.close();
//
//		}
//    	catch (Exception e) {
//    		e.printStackTrace();
//    	}
//		return result;
//    }
	
	public List<Map> parseTL(InputStream is) {
        List<Map> result = new ArrayList<Map>();
		try {
    		JsonParser jp = f.createJsonParser(is);
    		jp.nextToken();
    		Map map = new HashMap();
    		while (jp.nextToken()!=JsonToken.END_ARRAY){
    			while (jp.nextToken()!=JsonToken.END_OBJECT){
    				String key = jp.getCurrentName();
    				jp.nextToken();
    				if (jp.getCurrentToken().equals(JsonToken.START_OBJECT)){
    					while (jp.nextToken()!=JsonToken.END_OBJECT){
    						String key2 = jp.getCurrentName();
    						jp.nextToken();
    						map.put("user." + key2, jp.getText());
    					}
    				} else {
    					map.put(key, jp.getText());
    				} 
    			}
    			result.add(map);
    		}
    		jp.close();

		}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
		return result;
    }
	
	@Override
	public String name() {
		return "Jackson";
	}
}
