package ridwanadit.cs.ugm.skripsi;


import java.io.InputStream;
import java.lang.System;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
	public TextView time,text1,text2;
	String str;
	long start,finish1, finish2;
	private LinearLayout mLayout;
    private LinearLayout.LayoutParams mLayoutParams;
	
	 private final Runnable mTestTask = new Runnable() {
	        public void run() {

	            final Map<String, Long> results = new HashMap<String, Long>();

	            activate(new Android(),results);
	            activate(new GSON(), results);
	            activate(new Jackson(), results);
	            //activate (new JacksonYoutube(),results);

	            runOnUiThread(new Runnable() {
	                public void run() {
	                	
	                	mLayout.removeAllViews();
	                	
	                    List<String> keys = new ArrayList<String>(results.keySet());
	                    Collections.sort(keys);

	                    for (String key: keys) {
	                    	TextView display = new TextView(MainActivity.this);
	                        display.setText(key + ": " + results.get(key) + "ms");
	                        mLayout.addView(display,mLayoutParams);
	                    }

	                }
	            });
	        }
	    };
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mLayout = (LinearLayout) findViewById(R.id.layout);
        mLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        
        TextView textView = new TextView(MainActivity.this);
        textView.setText("Running tests...");
        mLayout.addView(textView, mLayoutParams);
        
        new Thread(mTestTask).start();
    }

	private void activate(final testJson input, Map<String,Long> result){
    	warmuptest(input);
    	long dur = maintest (input,1);
    	result.put("[1 run] " + input.name() + " ", dur);
    	dur = maintest (input,5);
    	result.put("[20 run] " + input.name() + " ", dur);
    	dur = maintest (input,20);
    	result.put("[100 run] " + input.name() + " ", dur);
    }
    
    private void warmuptest(final testJson input) {
    	for (int i = 0; i < 5; i++) {
        	InputStream is = this.getResources().openRawResource(R.raw.ridwanadit);
            input.parseTL(is);
        }
    }
    
    private long maintest(final testJson input, int repeats) {
    	InputStream is = this.getResources().openRawResource(R.raw.ridwanadit);
    	
//    	List<Map> result = input.parseTL(is);
//    	verify(result);
    	
        long duration = 0;
        for (int i = 0; i < repeats; i++) {
        	is = this.getResources().openRawResource(R.raw.ridwanadit);
            long start = System.currentTimeMillis();
            input.parseTL(is);
            duration += (System.currentTimeMillis() - start);
        }

        return duration;
    }
    
//    private static void verify(List<Map> result) {
//        if (result.size() != 1000) {
//            throw new IllegalStateException("Expected 20 but was " + result.size());
//        }
//        for (Map map: result) {
//            if (map.size()!=56) {
//                throw new IllegalStateException("Expected 56 but was " + map.size());
//            }
//
//        }
//    }
}