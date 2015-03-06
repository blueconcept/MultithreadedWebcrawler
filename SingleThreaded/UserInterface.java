import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserInterface extends JFrame {

	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	private static final int FRAME_HEIGHT = 600;
	private static final int FRAME_WIDTH = 500;
	private static final int INITIAL_TEXT_SIZE = 10;

	private List<String> my_keywords_list;
	private JLabel my_page_retrieve = new JLabel("0");
	private JLabel my_ave_per_page = new JLabel("0");
	private JLabel my_ave_URL = new JLabel("0");
	private JLabel my_ave_parse_result;
	private JLabel my_total_time_result;
	private JLabel[] my_keywords, my_ave_list, my_total_list;

	private long my_total_time;
	private long my_average;

	private Retriever my_retriever;
	private Analyzer my_analyzer;

	private long my_page_count;

	private JTextField my_url_field;
	private JTextField my_keyword_field;
	private JTextField my_page_count_field;

	private ArrayList<Double> my_list_of_ave;
	private ArrayList<Integer> my_list_of_total;

	public UserInterface() {
		super("WEBCRAWLER");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setResizable(false);

		my_list_of_ave = new ArrayList<Double>();
		my_list_of_total = new ArrayList<Integer>();

		my_keywords_list = new ArrayList<String>();

	}

	private final JPanel createNorthPanel() {
		final JPanel north_panel = new JPanel(new GridLayout(0, 2));
		north_panel.setBackground(Color.white);

		final JLabel url_label = new JLabel("Enter the URL: ");
		my_url_field = new JTextField(INITIAL_TEXT_SIZE);

		north_panel.add(url_label);
		north_panel.add(my_url_field);

		final JLabel keyword_label = new JLabel("Enter the Keywords: ");
		my_keyword_field = new JTextField(INITIAL_TEXT_SIZE);

		north_panel.add(keyword_label);
		north_panel.add(my_keyword_field);

		final JLabel page_count_label = new JLabel(
				"Enter the number of pages: ");
		my_page_count_field = new JTextField(INITIAL_TEXT_SIZE);

		north_panel.add(page_count_label);
		north_panel.add(my_page_count_field);

		return north_panel;
	}

	// http://www.cplusplus.com/doc/tutorial/
	private final JPanel createCenterTopPanel() {
		final JPanel center_panel = new JPanel(new GridLayout(5, 2));

		// number result will be display only after the search and analyzing.
		my_ave_parse_result = new JLabel();
		my_total_time_result = new JLabel();

		// general labels:
		final JLabel page_retrieve_label = new JLabel("Pages Retrieved: ");
		final JLabel ave_per_page_label = new JLabel("Average word per page: ");
		final JLabel ave_url_label = new JLabel("Average URLs per page: ");
		final JLabel total_time = new JLabel("Total running time: ");
		final JLabel ave_time_per_page = new JLabel(
				"The average time per page was: ");

		// add everything to center panel.
		center_panel.add(page_retrieve_label);
		center_panel.add(my_page_retrieve);

		center_panel.add(ave_per_page_label);
		center_panel.add(my_ave_per_page);

		center_panel.add(ave_url_label);
		center_panel.add(my_ave_URL);

		center_panel.add(ave_time_per_page);
		center_panel.add(my_ave_parse_result);

		center_panel.add(total_time);
		center_panel.add(my_total_time_result);

		return center_panel;
	}

	/**
	 * The second half of center panel, which contain Keyword label, average
	 * hits per page, total hit label, and the results.
	 * 
	 * @return panel.
	 */
	private final JPanel createBottonCenterPanel() {
		// put these 3 small panels into a panel.
		final JPanel keyword_panel = new JPanel(new GridLayout(0, 3));

		keyword_panel.add(wordPanel());
		keyword_panel.add(avHitPanel());
		keyword_panel.add(totalHitPanel());
		return keyword_panel;

	}

	/**
	 * This will display all the key words in center panel (left with white
	 * background) under Keywords
	 * 
	 * @return a small panel
	 * @author Kon
	 */
	private JPanel wordPanel() {
		final JPanel word_panel = new JPanel(new GridLayout(11, 0));
		word_panel.setBackground(Color.white);

		JLabel label = new JLabel("Keyword");
		word_panel.add(label);

		createLabels();// start creating an array of Labels, then setText.
		for (int ik = 0; ik < my_keywords.length; ik++) {
			word_panel.add(my_keywords[ik]);

		}

		return word_panel;
	}

	/**
	 * The panel for average hit per page list.
	 * 
	 * @return a panel.
	 * @author Kon
	 */
	private JPanel avHitPanel() {
		final JPanel ave_hit_panel = new JPanel(new GridLayout(11, 0));
		JLabel hit_label = new JLabel("Avg. hits per page");
		ave_hit_panel.add(hit_label);

		ave_hit_panel.setBackground(Color.gray);

		// start creating an array of labels.
		createAveLabels();

		for (int ik = 0; ik < my_ave_list.length; ik++) {
			ave_hit_panel.add(my_ave_list[ik]);

		}

		return ave_hit_panel;
	}

	/**
	 * Panel for total hits.
	 * 
	 * @return a panel.
	 * @author Kon
	 */
	private JPanel totalHitPanel() {
		final JPanel total_panel = new JPanel(new GridLayout(11, 0));
		total_panel.setBackground(Color.white);
		JLabel total = new JLabel("Total Hits: ");

		total_panel.add(total);

		// start creating an array of labels.
		totalLabels();

		for (int ik = 0; ik < my_total_list.length; ik++) {
			total_panel.add(my_total_list[ik]);

		}

		return total_panel;
	}

	/**
	 * Helper method. create an array of labels.
	 * 
	 * @return an array of Labels
	 * @author Kon
	 */
	private JLabel[] createLabels() {
		my_keywords = new JLabel[10];
		for (int i = 0; i < 10; i++) {
			my_keywords[i] = new JLabel("");
		}
		return my_keywords;
	}

	/**
	 * Helper method for create ave hit labels array.
	 * 
	 * @return array of labels.
	 */
	private JLabel[] createAveLabels() {
		my_ave_list = new JLabel[10];
		for (int i = 0; i < 10; i++) {
			my_ave_list[i] = new JLabel("");
		}
		return my_ave_list;
	}

	/**
	 * Helper method, bad code, need it to test.
	 * 
	 * @return array of label.
	 * @author Kon
	 */
	private JLabel[] totalLabels() {
		my_total_list = new JLabel[10];
		for (int i = 0; i < 10; i++) {
			my_total_list[i] = new JLabel("");
		}
		return my_total_list;
	}

	/**
	 * the container panel to contain all the panels in the center. this panel
	 * is created so that it is easier to choose which layout for all the
	 * panels, since BorderLayout of center panel sucks.
	 * 
	 * @return container panel.
	 * @author Kon
	 */
	private final JPanel createCenterContainerPanel() {
		final JPanel container = new JPanel(new GridLayout(2, 0));
		container.add(createCenterTopPanel());
		container.add(createBottonCenterPanel());
		return container;
	}

	private final JPanel createSouthPanel() {
		final JPanel south_panel = new JPanel(new FlowLayout());

		final JButton go_button = new JButton("GO");

		go_button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent the_event) {
				storeKeywords();
				my_page_count = Long.parseLong(my_page_count_field.getText());

				final long start_time = System.currentTimeMillis();
				startWebCrawler();
				final long end_time = System.currentTimeMillis();
				printTimeResults(start_time, end_time);
			}
		});

		final JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent the_event) {
				System.exit(0);
			}
		});
		south_panel.add(go_button);
		south_panel.add(exit);

		return south_panel;
	}

	public void printTimeResults(final long the_start_time,
			final long the_end_time) {
		my_total_time = (the_end_time - the_start_time);
		my_average = my_total_time / my_page_count;
		my_ave_parse_result.setText("" + my_average + " milliseconds");
		my_page_retrieve.setText("" + my_page_count);

		my_ave_per_page.setText("" + my_analyzer.getTotalWordCount()
				/ my_page_count + " words");

		my_ave_URL.setText(String.valueOf(my_analyzer
				.getAverageNumberOfUrlPerPage(my_page_count)));
		my_total_time_result.setText("" + my_total_time + " milliseconds");

		for (int i = 0; i < my_keywords_list.size(); i++) {
			my_keywords[i].setText("" + my_keywords_list.get(i));
			my_list_of_ave
					.add(my_analyzer
							.getAverageKeywordOccurentPerPage(my_keywords[i]
									.getText(), my_page_count));
			my_list_of_total.add(my_analyzer
					.getTotalKeywordOccurence(my_keywords[i].getText()));
		}

		for (int j = 0; j < my_list_of_ave.size(); j++) {

			my_ave_list[j].setText("" + my_list_of_ave.get(j));

		}

		for (int j = 0; j < my_list_of_total.size(); j++) {

			my_total_list[j].setText("" + my_list_of_total.get(j));

		}

	}

	/**
	 * add all the keywords into a list.
	 */
	private void storeKeywords() {
		final String words = my_keyword_field.getText().toLowerCase();
		final String[] delimited_words = words.split(" ");

		for (int i = 0; i < delimited_words.length; i++) {
			my_keywords_list.add(delimited_words[i]);

		}
	}

	private void startWebCrawler() {
		my_retriever = new Retriever(my_url_field.getText(), my_keywords_list,
				Integer.parseInt(my_page_count_field.getText()));
		my_retriever.addPage(my_url_field.getText());
		my_analyzer = my_retriever.getParser().getAnalyzer();
	}

	public void run() {
		add(createNorthPanel(), BorderLayout.NORTH);
		add(createCenterContainerPanel(), BorderLayout.CENTER);
		add(createSouthPanel(), BorderLayout.SOUTH);

		layoutFrame();
	}

	public void layoutFrame() {
		pack();
		setVisible(true);
	}
}
