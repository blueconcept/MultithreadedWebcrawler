import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Retrieves Pages from Web
 * @author team
 *
 */
public class Retriever {
	
	private List<Document> my_webpages;
	private Parser my_parser;
	
	/**
	 * Constructor
	 * @param the_initial_page
	 * @param the_keywords
	 * @param the_max
	 */
	public Retriever(final String the_initial_page,
			final List<String> the_keywords, final int the_max) {
		my_webpages = new LinkedList<Document>();
		my_parser = new Parser(this, the_initial_page, the_keywords, the_max);
	}

	/**
	 * get a Page for Parser
	 * @param the_page
	 * @return
	 */
	public Document addPage(final String the_page) {
		Document doc = null;
		if (!my_webpages.contains(the_page)) {
			try {
				doc = Jsoup.connect(the_page).get();
				my_webpages.add(doc);

			} catch (final IOException the_exception) {
				System.out.println("Either the file doesn't exist, or you "
						+ "do not have permission to access the file :(");
			}
		} else {
			System.out.println("THat page already exists!!!");
		}

		return doc;
	}
	
	/**
	 * Get the parser
	 * @return
	 */
	public Parser getParser() {
		return my_parser;
	}
}
