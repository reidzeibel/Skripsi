package ridwanadit.cs.ugm.skripsi;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import android.util.Log;

public class JacksonYoutube implements testJson {
	long start,finish;
	static JsonFactory f = new JsonFactory();
	
	public List<Map> parseTL(InputStream is) {
        List<Map> result = new ArrayList<Map>();
        try {
        	JsonParser jp = f.createJsonParser(is);
        	jp.nextToken();
        	Map map = new HashMap();
        	while (jp.nextToken()!=JsonToken.END_OBJECT){
        		String key = jp.getCurrentName();
        		jp.nextToken();
        		if (jp.getCurrentToken().equals(JsonToken.START_OBJECT)){
        			while (jp.nextToken()!=JsonToken.END_OBJECT){
        				//version, encoding, feed
        				String key2 = jp.getCurrentName();
        				jp.nextToken();
        				if (jp.getCurrentToken().equals(JsonToken.START_OBJECT)){
        					//OBJECT inside feed
        					while (jp.nextToken()!=JsonToken.END_OBJECT){
        						String key3 = jp.getCurrentName();
        						jp.nextToken();
        						map.put("feed."+ key2 +"."+ key3, jp.getText());
        						Log.d("feed."+ key2 +"."+ key3, jp.getText());
        					}
        				} else if (jp.getCurrentToken().equals(JsonToken.START_ARRAY)){
        					//ARRAY inside feed 
        					while (jp.nextToken()!=JsonToken.END_ARRAY){
        						while (jp.nextToken()!=JsonToken.END_OBJECT) {
        							String key3 = jp.getCurrentName();
        							jp.nextToken();
        							if (jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {
        								//OBJECT inside ARRAY inside feed
        								while (jp.nextToken()!=JsonToken.END_OBJECT){
        									jp.nextToken();
        									map.put("feed."+ key2 + "." + key3, jp.getText());
        									Log.d("feed."+ key2 + "." + key3, jp.getText());
        								}
        							} else if (jp.getCurrentToken().equals(JsonToken.START_ARRAY)){
        								//ARRAY inside ARRAY inside feed
        								while (jp.nextToken()!=JsonToken.END_ARRAY) {
        									while (jp.nextToken()!=JsonToken.END_OBJECT) {
        										String key4 = jp.getCurrentName();
        										jp.nextToken();
        										map.put("feed."+ key2 + "." + key3 + "." + key4, jp.getText());
        										Log.d("feed."+ key2 + "." + key3 + "." + key4, jp.getText());
        									}
        								}
        							} else{
        								map.put("feed."+ key2 + "." + key3, jp.getText());
    									Log.d("feed."+ key2 + "." + key3, jp.getText());
        							}
        						}
        					}
        				} else {
        					map.put("feed." + key2, jp.getText());
        					Log.d("feed." + key2, jp.getText());
        				}
        			}
        		} else {
        			map.put(key, jp.getText());
        			Log.d(key, jp.getText());
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
		return "JacksonYoutube";
	}
}
