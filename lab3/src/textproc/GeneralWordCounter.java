package textproc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class GeneralWordCounter implements TextProcessor {
	private Set<String> removedWords;
	private Map<String, Integer> counter;
	
	public GeneralWordCounter(Set<String> removedWords) {
		this.removedWords = removedWords;
		counter = new HashMap<String, Integer>();
		}

	
	public List<Map.Entry<String, Integer>> getWordList(){
		return new ArrayList<>(counter.entrySet());
	}
	
	@Override
	public void process(String w) {
		if(!removedWords.contains(w)) {
			if(!counter.containsKey(w)) {
				counter.put(w, 1);
			} else {
				counter.replace(w, counter.get(w) + 1);
			}
		}
		
	}

	@Override
	public void report() {
		Set<Map.Entry<String, Integer>> wordSet = counter.entrySet();
		List<Map.Entry<String, Integer>> wordList = new ArrayList<>(wordSet);
		wordList.sort(new WordCountComparator());
		
		//Skriver ut de 20 vanligaste orden
		for(int i = 0; i < 20; i++) {
			System.out.println(wordList.get(i).getKey() + ": " + wordList.get(i).getValue());
		}
		
		//Skriver ut alla ord med förekomst >= 200
		//for(String key : counter.keySet()) {
		//	if(counter.get(key) >= 200)
		//		System.out.println(key + ": " + counter.get(key));
		//}
		
	}

}
