package com.websearchengine.org;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class WebSearch {
	public static void searchWords() throws Exception {
		WebSearch websearch = new WebSearch();
		Hashtable<String, Integer> hashTable = new Hashtable<String, Integer>();		
		Scanner sc = new Scanner(System.in);
		String option = "y";
		while(option.equals("y")) {	
			System.out.println("Please enter the word you want to search: ");
			String searchWord = sc.nextLine();
			int occurance = 0;
			int number = 0; 
			try {
				File my_dir = new File("D:\\Advanced Computing\\Accproject3\\text-files");
				File[] fileArray = my_dir.listFiles();
				int i=0;
				while(i < fileArray.length) {
					occurance = websearch.searchWord(fileArray[i], searchWord);
					hashTable.put(fileArray[i].getName(), occurance); 
					if (occurance != 0) {
						number++;
					}
					i++;
				}
				System.out.println("\nTotal No. of Files for input " + searchWord + " is : " + number);
				if (number == 0) {
					System.out.println("\nPlease wait while we give you suggestions\n");
					Dictionary.createDictionary();
					websearch.suggestions(searchWord);
				}
				Sorting.rankFiles(hashTable, number);
				System.out.println("\n\nDo you want to continue?");
				System.out.println("If yes please enter y if 'Yes'");
				System.out.println("If you want to exit hit any key");
				option = sc.nextLine();	
				if(!option.equals("y")) {
					sc.close();
					System.out.println("Thanks for using the Web Search Engine");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int searchWord(File pathOfFile, String wordToBeSearched) throws IOException {
		String my_data = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(pathOfFile));
			String line = null;
			while ((line = br.readLine()) != null) {
				my_data = my_data + line;
			}
			br.close();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		char txt[] = my_data.toCharArray();
		char pat[] = wordToBeSearched.toCharArray();
		int result = BoyerMoore.search(txt, pat);
		if (result != 0) {
			System.out.println("\nThe file that contains all the above words" + pathOfFile.getName());
			System.out.println("--------------------------------------\n");
		}
		return result;
	}
	
	public static void spellCheck(String pattern) throws IOException {
		String filename="dictionary.txt";
		File file = new File(filename);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			ArrayList<String> dictWords = new ArrayList<>();
			String str=null;
			while((str= br.readLine())!=null) {
				dictWords.add(str);
			}
			int editDistance = 10, editDistanceFirstWord = 10, editDistanceSecondWord = 10;
			int firstSuggestion = 0;
			int secondSuggestion = 0;
			for(int i = 0; i < dictWords.size(); i++){
				String dictionaryWord = dictWords.get(i);
				editDistance = EditDistance.minDistance(dictionaryWord, pattern);
					if(editDistance < editDistanceFirstWord) {
						editDistanceFirstWord=editDistance;
						firstSuggestion = i;
					}
			}
			for(int i = 0; i<dictWords.size();i++){
				String dictionaryWord = dictWords.get(i);
				editDistance = EditDistance.minDistance(dictionaryWord, pattern);
				if(editDistance < editDistanceSecondWord) {
					editDistanceSecondWord = editDistance;
					if(i != firstSuggestion) secondSuggestion = i;
				}
			}
			System.out.println("You can try "+dictWords.get(firstSuggestion)+" or "+dictWords.get(secondSuggestion));
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void suggestions(String pattern) {
		try {
			spellCheck(pattern);
		}catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}
}
