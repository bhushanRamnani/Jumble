import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Given an input string and a dictionary file path, prints a list of all words
 * that can be obtained by jumbling it up.
 * 
 * Usage: Jumble <Word List Path> <Input String>
 * 
 * @author Bhushan Ramnani
 * 
 */
public class Jumble {

	private static Set<String> dictionary = new HashSet<String>();
	private static Set<String> output = new HashSet<String>();

	/**
	 * @param args
	 *            args[0]: Dictionary file/folder path. If the path is a folder,
	 *            args[1]: input string whose anagrams are required
	 */
	public static void main(String[] args) {
		updateDictionary(args[0]);
		getJumbleWords(args[1]);
		printOutput();
	}

	/**
	 * 
	 * 
	 * Algorithm: Use dynamic programming to generate permutations the string
	 * and all strings of smaller lengths. If any permutation is a valid word in
	 * the dictionary add the word to the final output. The permutations of
	 * string of length l can be obtained by inserting the last character at all
	 * positions of all permutations of substring from 0 to (l-1). Runtime space
	 * and time complexity of this algorithm would be O(n!), since the number of
	 * permutations to be generated are.. n! + (n-1)! + (n-2)! + ....
	 * Do this for all substrings (i,l) such that 0<=i<=l. So the overall 
	 * time complexity will still be O(n!)
	 * 
	 * @param input
	 *            string whose jumbled words are required as output
	 * @return updates the list of jumbled words.
	 */
	public static void getJumbleWords(String input) {
		input = input.toLowerCase();
		if (input == null) {
			return;
		}
		for (int i=0; i<input.length(); i++) {
			getJumbleHelper(input.substring(i,input.length()));
		}
	}

	private static ArrayList<String> getJumbleHelper(String input) {
		int n = input.length();
		ArrayList<String> permutations = new ArrayList<String>();
		if (n == 1) {
			// Base Case
			permutations.add(Character.toString(input.charAt(0)));
			return permutations;
		}
		ArrayList<String> prevPerms = getJumbleHelper(input.substring(0, n - 1));
		char lastChar = input.charAt(n - 1);
		for (String perm : prevPerms) {
			// Insert this character at all positions of each permutation
			for (int i = 0; i <= perm.length(); i++) {
				String newPerm = perm.substring(0, i) + lastChar
						+ perm.substring(i, perm.length());
				if (dictionary.contains(newPerm)) {
					output.add(newPerm);
				}
				permutations.add(newPerm);
			}
		}
		return permutations;
	}

	/**
	 * 
	 * @param path
	 *            path of the stored word list file. Can be the full file path
	 *            or the folder in which the files are stored.
	 * 
	 *            Adds all the words in the word list to the word dictionary.
	 */
	private static void updateDictionary(String path) {
		File dictPath = new File(path);
		if (dictPath.isDirectory()) {
			for (File entry : dictPath.listFiles()) {
				updateDictionary(entry);
			}
			return;
		}
		updateDictionary(dictPath);
	}
	
	/**
	 * Prints out the jumbled words, with each word on one line.
	 */
	private static void printOutput() {
		for (String word : output) {
			System.out.println(word);
		}
	}

	private static void updateDictionary(File dictFile) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(dictFile));
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.toLowerCase();
				dictionary.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("No such file present.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem with reading file.");
		}
	}

}
