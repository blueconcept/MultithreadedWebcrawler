import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Analyzers text
 * @author team
 *
 */
public class Analyzer {

	private Map<String, Integer> my_word_count;

	private int my_total_word_count;
	private int my_total_web_crawled;
	private HashMap<String, Integer> my_map;

	/**
	 * Constructor
	 */
	public Analyzer() {
		my_word_count = new LinkedHashMap<String, Integer>();
		my_total_word_count = 0;
		my_total_web_crawled = 0;
	}

	/**
	 * Gets the total word count.
	 * @return
	 */
	public int getTotalWordCount() {
		my_total_word_count = my_map.size();
		System.out.println(my_total_word_count);
		return my_total_word_count;
	}

	/**
	 * Gets total pages crawled.
	 * @return
	 */
	public int getTotalCrawled() {
		return my_total_web_crawled;
	}

	/**
	 * Calculates average number of url's per page.
	 * @param the_max
	 * @return
	 */
	public double getAverageNumberOfUrlPerPage(double the_max) {
		return my_total_web_crawled / the_max;
	}

	/**
	 * Gets the total keywords.
	 * @param the_keyword
	 * @return
	 */
	public int getTotalKeywordOccurence(String the_keyword) {
		int total = 0;

		if (my_map.containsKey(the_keyword)) {
			total = my_map.get(the_keyword);
		}

		return total;
	}

	/**
	 * Gets the average keyword occured.
	 * @param the_keyword
	 * @param max
	 * @return
	 */
	public double getAverageKeywordOccurentPerPage(String the_keyword, double max) {
		double ave = 0.0;

		if (my_map.containsKey(the_keyword)) {
			ave = my_map.get(the_keyword);
		}

		ave = ave / max;

		return ave;

	}

	/**
	 * Makes a hashmap for frequency
	 * @param the_list
	 * @param the_web_crawl_size
	 */
	public void mapping(ArrayList<String> the_list, int the_web_crawl_size) {
		StringBuilder sb = new StringBuilder();
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

		for (String x : the_list) {
			final String new_string = x;
			final Integer count = hashMap.get(new_string);

			if (count == null) {
				hashMap.put(new_string, 1);
			} else {
				hashMap.put(new_string, count + 1);
			}
		}

		HashMap<String, Integer> map = (HashMap<String, Integer>) sort(hashMap);
		Iterator<String> it = map.keySet().iterator();

		System.out.println(map.size());

		int i = 0;

		while (i != map.size()) {
			final String key = it.next().toString();
			final String value = map.get(key).toString();

			sb.append(key + "\t\t\t" + value + "\n");
			i++;
		}

		my_map = map;
		my_total_web_crawled = the_web_crawl_size;

		System.out.println(sb.toString());
		System.out.println("Total websites crawled: " + the_web_crawl_size);
	}

	/**
	 * merges maps
	 * @param unsortMap
	 * @return
	 */
	private static Map<String, Integer> sort(Map unsortMap) {

		LinkedList list = new LinkedList(unsortMap.entrySet());

		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
						.compareTo(((Map.Entry) (o1)).getValue());
			}
		});

		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
