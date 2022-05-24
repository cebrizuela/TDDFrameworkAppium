import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
public class TestOfCodigos {

	public static void main(String[] args) {
		 // Create a HashMap object called capitalCities
		 HashMap<String, String> capitalCities = new HashMap<String, String>();

		 // Add keys and values (Country, City)
		  Map<String, Integer> map = new HashMap<>();
	        map.put("One", 1);
	        map.put("Two", 2);
	        map.put("Three", 3);
	        System.out.println(map);
	        Map<String, Integer> tmap = new TreeMap<>(map);
	        System.out.println(tmap);

	}

}

