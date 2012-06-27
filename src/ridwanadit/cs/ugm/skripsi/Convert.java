package ridwanadit.cs.ugm.skripsi;

import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

public class Convert implements ConvertString {

	@Override
	public String result(InputStream is) {
		String jsontext = null;
		try {
			byte [] buffer = new byte[is.available()];
			while (is.read(buffer) != -1);
			jsontext = new String(buffer);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return jsontext;
	}

}
