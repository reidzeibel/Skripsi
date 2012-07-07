package ridwanadit.cs.ugm.skripsi;

import java.io.IOException;
import java.io.InputStream;
import java.lang.System;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
	public TextView time,text1,text2;
	String str;
	long start,finish1, finish2;
	private ScrollView mScroll, outer;
	private LinearLayout mLayout, mInner;
    private LinearLayout.LayoutParams mLayoutParams, upper;
	
	 private final Runnable mTestTask = new Runnable() {
	        public void run() {

	            final Map<String, Long> results = new HashMap<String, Long>();

	            activate(new Android(),results);
	            activate(new GSON(), results);
	            activate(new Jackson(), results);
	            split(new AndroidSplit(), results);
	            split(new JacksonStr(), results);
	            split(new GSONStr(),results);
	            test(new Convert(),results);
	            final String a = strdisplay();
	            
	            runOnUiThread(new Runnable() {
	                public void run() {
	                	
	                	mInner.removeAllViews();
	                	mScroll.removeAllViews();
	                	outer.removeAllViews();
	                	mLayout.removeAllViews();
	                	
	                    List<String> keys = new ArrayList<String>(results.keySet());
	                    Collections.sort(keys);
	                    
	                    
	                    for (String key: keys) {
	                    	TextView display = new TextView(MainActivity.this);
	                        display.setText(key + ": " + results.get(key) + "ms");
	                        mInner.addView(display);
	                    }
	                    mScroll.addView(mInner);
	                    mLayout.addView(mScroll);
	                    //ScrollView out = new ScrollView(MainActivity.this);
	                    TextView showtext = new TextView(MainActivity.this);
	                    outer.addView(showtext);
	                    showtext.setText(a);
	                    mLayout.addView(outer);

	                }
	            });
	        }
	    };
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mLayout = (LinearLayout) findViewById(R.id.layout);
        mInner = (LinearLayout) findViewById(R.id.inner);
        mScroll = (ScrollView) findViewById(R.id.scrollView);
        outer = (ScrollView) findViewById(R.id.outer);
        mLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        
        TextView textView = new TextView(MainActivity.this);
        textView.setText("Running tests...");
        mLayout.removeAllViews();
        mLayout.addView(textView, mLayoutParams);
        
        new Thread(mTestTask).start();
    }

	private void activate(final testJson input, Map<String,Long> result){
		maintest(input,20);
    	long dur = 0;
    	dur = maintest (input,10);
    	result.put("[10 run] " + input.name() + " ", dur);
    	dur = maintest (input,20);
    	result.put("[20 run] " + input.name() + " ", dur);
    	dur = maintest (input,30);
    	result.put("[30 run] " + input.name() + " ", dur);
    	dur = maintest (input,40);
    	result.put("[40 run] " + input.name() + " ", dur);
    	dur = maintest (input,50);
    	result.put("[50 run] " + input.name() + " ", dur);
    }
    
	private void split(final TestStr input, Map<String,Long> result){
		splittest(input,20);
		long dur = 0;
    	dur = splittest (input,10);
    	result.put("Splitted [10 run] " + input.name() + " ", dur);
    	dur = splittest (input,20);
    	result.put("Splitted [20 run] " + input.name() + " ", dur);
    	dur = splittest (input,30);
    	result.put("Splitted [30 run] " + input.name() + " ", dur);
    	dur = splittest (input,40);
    	result.put("Splitted [40 run] " + input.name() + " ", dur);
    	dur = splittest (input,50);
    	result.put("Splitted [50 run] " + input.name() + " ", dur);
	}
	
	private void test (final ConvertString input, Map<String,Long> result) {
		stringer(input,20);
		long dur = 0;
    	dur = stringer(input,1);
    	result.put("String Splitting took [ms]", dur);
	}
	private long stringer (final ConvertString input, int repeats){
		InputStream is = this.getResources().openRawResource(R.raw.ridwanadit);
		long duration = 0;
		for (int i =0; i < repeats;i++) {
			is = this.getResources().openRawResource(R.raw.ridwanadit);
			long start = System.currentTimeMillis();
			input.result(is);
			duration += System.currentTimeMillis()-start;
		}
		return duration;
	}
	
    private long maintest(final testJson input, int repeats) {
    	InputStream is = this.getResources().openRawResource(R.raw.ridwanadit);
    	
        long duration = 0;
        for (int i = 0; i < repeats; i++) {
        	is = this.getResources().openRawResource(R.raw.ridwanadit);
            long start = System.currentTimeMillis();
            input.parseTL(is);
            duration += (System.currentTimeMillis() - start);
        }

        return duration;
    }
    
    private String strdisplay() {
    	String output = null;
    	try {
			output = convertStream(this.getResources().openRawResource(R.raw.ridwanadit));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
    }
    
    public static String convertStream(InputStream is) throws IOException {
		byte [] buffer = new byte[is.available()];
		while (is.read(buffer) != -1);
		String jsontext = new String(buffer);
		return jsontext;
	}
    
    private long splittest(final TestStr input, int repeats) {
    	InputStream is = this.getResources().openRawResource(R.raw.ridwanadit);
    	long duration = 0;
    	try {
			String jsonstr = convertStream (is);
	        for (int i = 0; i < repeats; i++) {
	            long start = System.currentTimeMillis();
	            input.parseTL(jsonstr);
	            duration += (System.currentTimeMillis() - start);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return duration;
    }
    
}