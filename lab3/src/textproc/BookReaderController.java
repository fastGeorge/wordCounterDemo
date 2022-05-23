package textproc;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class BookReaderController {
	private JButton searchButton;
	
	public BookReaderController(GeneralWordCounter counter) {
		SwingUtilities.invokeLater(() -> createWindow(counter, "BookReader", 100, 300));
		}
	
		private void createWindow(GeneralWordCounter counter, String title,
													int width, int height) {
			JFrame frame = new JFrame(title);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Container pane = frame.getContentPane();
			// pane är en behållarkomponent till vilken de övriga komponenterna
			//(listvy, knappar etc.) ska läggas till.
			
			//Skapa layout
			pane.setLayout(new BorderLayout());
			
			//listvy
			SortedListModel<Map.Entry<String, Integer>> listModel = new SortedListModel<>(counter.getWordList());
			JList<Map.Entry<String, Integer>> list = new JList<>(listModel);
			JScrollPane scroll = new JScrollPane(list);
			pane.add(scroll, BorderLayout.CENTER);
			
			//panel längst ner med två knappar
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 5, 10, 10));
			pane.add(panel, BorderLayout.SOUTH);
			
			
			
			//radioknappar
			ButtonGroup buttonGroup = new ButtonGroup();
			
			JRadioButton alphaRadio = new JRadioButton("Alphabetical: ");
			alphaRadio.addActionListener(e -> {
				listModel.sort((m1, m2) -> m1.getKey().compareTo(m2.getKey()));
			});
			
			JRadioButton freqRadio = new JRadioButton("Frequency: ");
			freqRadio.addActionListener(e -> {
				//listModel.sort((m1, m2) -> m1.getValue() - m2.getValue());
				listModel.sort(new WordCountComparator());
			});
			
			buttonGroup.add(alphaRadio);
			buttonGroup.add(freqRadio);
			
			alphaRadio.doClick();
			
			panel.add(alphaRadio);
			panel.add(freqRadio);
			
			/* Här är koden för "vanliga knappar"

			//knappar
			JButton alphaButton = new JButton("Alphabetical");
			JButton freqButton = new JButton("Frequency");
			
			//Lägg till actionlistener
			alphaButton.addActionListener(e -> {
				listModel.sort((m1, m2) -> m1.getKey().compareTo(m2.getKey()));
			});
			
			freqButton.addActionListener(e -> {
				listModel.sort(new WordCountComparator());
			});
			
			//Lägg till komponenterna i panel
			panel.add(alphaButton);
			panel.add(freqButton);
			*/
			
			
			//Sökfönster + knapp
			JTextField textField = new JTextField("Search for word here", 20);
			panel.add(textField);
			
			
			textField.addKeyListener(new EnterKeyListener());
			
			searchButton = new JButton("Search!");
			panel.add(searchButton);
			
			JLabel noWordText = new JLabel();
			panel.add(noWordText);
			
			
			
			searchButton.addActionListener(e -> {
				Boolean check = false;
				String word = textField.getText();
				if(word != null) {
					
					//plocka bort mellanslag
					String[] wordArr = word.split(" ");
					for(int i = 0; i < wordArr.length; i++) {
						if(!wordArr[i].equals("")) {
							word = wordArr[i];
							break;
						}
					}
					
					//Leta upp ordet
					for(int i = 0; i < listModel.getSize(); i++) {
						if(word.equalsIgnoreCase(listModel.getElementAt(i).getKey())) {
							list.ensureIndexIsVisible(i);
							list.setSelectedIndex(i);
							check = true;
							noWordText.setText("");
							break;
						}
						
					}
					
					//Skriv ut om ordet inte fanns
					if(!check) {
						noWordText.setText("No word found!");
					}
				}
			});
			
			
			
			frame.pack();
			frame.setVisible(true);

		}
		// hade kunnat ersättas av frame.getRootPane().setDefaultButton(sök);
		private class EnterKeyListener implements KeyListener{

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getExtendedKeyCode();
				if(key == KeyEvent.VK_ENTER) {
				searchButton.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
}

		
