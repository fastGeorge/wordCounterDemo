package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BookReaderApplication {

	public static void main(String[] args) throws FileNotFoundException {
		File f = null;
		//Läs in undantagsord:
		Scanner scan = new Scanner(new File("undantagsord.txt"));
		Set<String> exceptionWords = new HashSet<>();
		
		while(scan.hasNext()) {
			exceptionWords.add(scan.next());
		}
		scan.close();
		
		//Skapa filinläsning:
		JFileChooser chooser = new JFileChooser(".");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		chooser.setFileFilter(filter);
		int response = chooser.showOpenDialog(null);
		if(response == JFileChooser.APPROVE_OPTION) {
			f = chooser.getSelectedFile();
		}
		
		//Skapa GWC-objekt
		GeneralWordCounter counter = new GeneralWordCounter(exceptionWords);
		
		//Läser in Nils Holgerssons Fantastiska Resa
		scan = new Scanner(f);//new File("nilsholg.txt"));
		scan.findWithinHorizon("\uFEFF", 1);
		scan.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning
		
		while(scan.hasNext()) {
			String word = scan.next();
			counter.process(word.toLowerCase());
		}
		scan.close();
		
		//skriver ut resultatet
		//counter.report();
		
		BookReaderController controller = new BookReaderController(counter);
		
	}

}
