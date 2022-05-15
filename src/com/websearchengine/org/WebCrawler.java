package com.websearchengine.org;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler 
{
	int count = 0;
	private HashSet<String> links;
	private static final int MAX_DEPTH = 600;
	ArrayList<String> arr1 = new ArrayList<String>();
	
	public WebCrawler()
	{
		links = new HashSet<String>();
	}
	
	public void get_page_links(String myURL)
	{
		if((!links.contains(myURL)))
		{
			try {
					if(links.add(myURL)) 
					{
						System.out.println(myURL);
						arr1.add(myURL);
						int i = arr1.indexOf(myURL);
						String myString = Integer.toString(i);
						String str = myURL;
						saveUrl(myString, str);
					}
					Document my_document = Jsoup.connect(myURL).get();
					Elements link_on_page = my_document.select("a[href]");
					for(Element page : link_on_page) 
					{
						if(count != MAX_DEPTH)
						{
							count++;
							get_page_links(page.attr("abs:href"));
					
						}
					}
				}
				catch(IOException e) 
				{
					System.err.println("For '"+myURL+ "': "+e.getMessage());
				}
		}
	}

	public void saveUrl(final String filename, final String urlString) 
	{
		{
			try 
			{
				URL url = new URL(urlString);
				BufferedReader my_readr = new BufferedReader(new InputStreamReader(url.openStream()));
				
				String str = filename + ".html";
		
				BufferedWriter my_writer = new BufferedWriter(new FileWriter("D:\\Advanced Computing\\Accproject3\\html-files\\" + str));
		
				String line;
				while((line = my_readr.readLine()) != null) 
				{
					my_writer.write(line);
				}
				my_readr.close();
				my_writer.close();
				System.out.println("Successfully Downloaded.");
		
			}
	
			catch(MalformedURLException mue) 
			{
				System.out.println("Malformed URL Exception raised");
			}
			catch(IOException ie) 
			{
				System.out.println("IOException raised");
			}
		}
	}
	
	public void getPageLinks(String link) {
		get_page_links(link);
	}

	public static void main(String[] asd) {
		new WebCrawler().get_page_links("https://www.CNN.com/");
	}
}