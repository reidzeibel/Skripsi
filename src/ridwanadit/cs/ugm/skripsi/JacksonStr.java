package ridwanadit.cs.ugm.skripsi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class JacksonStr implements TestStr {
	long start,finish;
	static JsonFactory f = new JsonFactory();

	public List<Map> parseTL(String is) {
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
		return "JacksonString";
	}
}
