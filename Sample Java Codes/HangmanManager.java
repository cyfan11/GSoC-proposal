import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class HangmanManager {

/**
 * This HangmanManager code creates a player that is able to cheat at Hangman by 
 * changing the correct word. This code implements lists, maps and sets to keep 
 * track of the letters guessed, the potential correct words and the chances to guess
 * that left.
 * 
 * @author Cynthia Fan
 */
	private int myMax;
	private List<String> myDictionary;
	private SortedSet<Character> lettersGuessed;
	
	/**
	 * This constructs a initialization of a set of words for the dictionary by 
	 * filtering by the word length.
	 * @param dictionary - the ArrayList of words
	 * @param length - the length each words should be
	 * @param max - the maximum number of wrong guesses
	 * @throws IllegalArgumentException if if the number of guesses is less
	 * than 0 or if the length of word is less than 1
	 */
	public HangmanManager(List<String> dictionary, int length, int max){
		if (max<0 || length <1) {
			throw new IllegalArgumentException();
		}
		
		myMax = max;
		myDictionary = new ArrayList<String>();
		for(int i=0; i<dictionary.size();i++) {
			String word = dictionary.get(i);
			if (word.length() == length) {
				myDictionary.add(word);
			}
		}
		lettersGuessed = new TreeSet<Character>();
	}
	
	/**
	 * This returns a set of all the words in the dictionary
	 * @return - the dictionary set of all the words
	 */
	public Set<String> words(){
		Set<String> dictionarySet = new HashSet<String>();
		for (int i=0;i<myDictionary.size(); i++) {
			dictionarySet.add(myDictionary.get(i));
		}
		return dictionarySet;
	}
	
	/**
	 * @return - the number of wrong guesses left
	 */
	public int guessesLeft() {
		return myMax;
	}
	
	/**
	 * @return - the set of letters that have been guessed so far 
	 */
	public SortedSet<Character> guesses(){
		return lettersGuessed;	
	}
	
	/**
	 * This returns the pattern to be displayed to the user for showing 
	 * the correct and unknown letters
	 * @throws - the IllegalStateException of the dictionary is empty
	 * @return - pattern, the pattern of the game
	 */
	public String pattern() {
		String pattern = "";
		if (myDictionary.size() == 0) {
			throw new IllegalStateException();
		}
		
		String word = myDictionary.get(0);
		for (int i=0; i<word.length();i++) {
			if (lettersGuessed.contains(word.charAt(i))){
				pattern = pattern + word.charAt(i);	
			}
			else {
				pattern = pattern +"-";
			}
			if (i != word.length()-1) {
				pattern = pattern + " ";
			}
		}
		return pattern;	
	}
	
	/**
	 * This helper method checks how many of each unique combination of letters
	 * guessed and unknown letters exist
	 * @param guess - the character of current guess
	 * @return uniqueScore, the map of each combination and its frequency
	 */
	private Map<String, Integer> getUnique(char guess){
		Map<String, Integer> uniqueScore = new HashMap<String, Integer>();
		
		for(int i=0;i<myDictionary.size();i++) {
			String unique = "";
			String word = myDictionary.get(i);
			for(int j=0; j<word.length();j++) {
				if (word.charAt(j) == guess) {
					unique += guess;
				}
				else if (lettersGuessed.contains(word.charAt(j))) {
					unique += "-";
				}
				else {
					unique += ".";
				}
			}
			if (uniqueScore.containsKey(unique)) {
				int numberBefore = uniqueScore.get(unique);
				numberBefore++;
				
				uniqueScore.put(unique, numberBefore);
			}
			else {
				uniqueScore.put(unique, 1);
			}
		}
		return uniqueScore;
	}
	
	/**
	 * This helper method returns the maximum number found in the uniqueScore map
	 * @param uniqueScore - the map of each combination and its frequency
	 * @param keys - a set of each key in uniqueScore
	 * @return - mapMax, the maximum number found in the uniqueScore map
	 */
	private int mapMax(Map<String, Integer> uniqueScore, Set<String> keys) {
		int mapMax = 1;
		for (String i: keys) {
			if (uniqueScore.get(i) > mapMax) {
				mapMax = uniqueScore.get(i);
			}
		}
		return mapMax;
	}
	
	/**
	 * This helper method makes a shorter dictionary only containing the 
	 * words of the most frequent combination of known and unknown letters
	 * @param guess - the character of current guess
	 * @param maxKey - the pattern of the combination
	 * @return - shorterDictionary, the new dictionary
	 */
	private List<String> makeDictionary(char guess, String maxKey){
		List<String> shorterDictionary = new ArrayList<String>();
		for (int i=0; i<myDictionary.size();i++) {
			int wrongLetter = 0;
			String word = myDictionary.get(i);
			for (int j=0; j<maxKey.length();j++) {
				if ((word.charAt(j) == guess && maxKey.charAt(j) != guess) 
						|| (word.charAt(j) != guess && maxKey.charAt(j) == guess) ) {
					wrongLetter++;
				}
			}
			if (wrongLetter == 0) {
				shorterDictionary.add(word);
			}
		}
		return shorterDictionary;
	}
	
	/**
	 * This updates the game with the new guess and decides what words to use
	 * in the future
	 * @param guess - the character of current guess
	 * @throws - IllegalStateException if the there is no guesses left or if
	 * the dictionary is empty
	 * @throws - IllegalArgumentException if the letter has been guessed before
	 * @return - record, the frequency of the guessed letter in the new pattern
	 */
	public int record(char guess) {
		if (myMax <1 || myDictionary.size() == 0) {
			throw new IllegalStateException();
		}
		if (lettersGuessed.contains(guess)) {
			throw new IllegalArgumentException();
		}
		Map<String, Integer> uniqueScore = getUnique(guess);
		Set<String> keys = uniqueScore.keySet();
		String maxKey = "";
		int mapMax = mapMax(uniqueScore, keys);
		for (String i: keys) {
			if (uniqueScore.get(i) == mapMax) {
				maxKey = i;
			}
		}
		int record = 0;
		for (int i=0; i<maxKey.length();i++) {
			if (maxKey.charAt(i)==guess) {
				record++;		
			}
		}
		myDictionary = makeDictionary(guess,maxKey);
		lettersGuessed.add(guess);
		if (record == 0) {
			myMax--;
		}
		return record;
	}
}
