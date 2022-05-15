package com.websearchengine.org;

import java.util.Scanner;

public class SearchEngine {
			
	public static void main(String[] args) throws Exception {
		System.out.println("Welcome to the web search engine");
		System.out.println();
		System.out.println("Press 1 to crawl the web \nPress 2 to search the word");
		Scanner sc = new Scanner(System.in);
		int number = sc.nextInt();
		switch(number) {
			case 1:
				Scanner sc2 = new Scanner(System.in);
				WebCrawler webCrawler = new WebCrawler();
				System.out.println("Please enter the link:");
				String link = sc2.nextLine();
				webCrawler.getPageLinks(link);
				HTMLtoText.generateTextFiles();
				System.out.println("---------------------");
				WebSearch.searchWords();
			case 2:
				WebSearch.searchWords();
		}
		sc.close();
	}
}
