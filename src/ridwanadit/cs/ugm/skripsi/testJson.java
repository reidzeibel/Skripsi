package ridwanadit.cs.ugm.skripsi;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface testJson {
	String name();
	List<Map> parseTL(InputStream is);
}
