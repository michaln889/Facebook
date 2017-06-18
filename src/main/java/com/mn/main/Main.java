package com.mn.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.mn.entity.Facebook;
import com.mn.service.FacebookService;
import com.mn.service.FacebookServiceImpl;



public class Main {
	public static void main(String[] args) throws IOException
	{
		
		Scanner scanner = new Scanner(System.in);
		String id;
		String option;
		FacebookService service = new FacebookServiceImpl();
				
		while(true)
		{
			System.out.println("\n\nWybierz jedn¹ z opcji.");
			System.out.print("1 - Zwrot profilu Facebook na podstawie podanego ID\n2 - Zwrot statystyk odnosnie uzytych slow w wiadomosciach\n"
					+ "3 - Zwrot ID postów, które zawieraj¹ podane s³owo\n4 - Zwrot zbioru obiektów posortowanych wzglêdem imienia i nazwiska\n0 - koñczy dzia³anie programu\n"
					+ "Wybieram opcjê numer:");
			System.out.println();
			
			switch(option = scanner.next()) 
			{
				case "1":				
					System.out.print("Podaj ID poszukiwanego u¿ytkownika:");
					id = scanner.next();
					
					try {
						System.out.println(service.findById(id));
					}
					
					catch (FileNotFoundException e)
					{
						System.err.println("U¿ytkownik o podanym ID nie istnieje lub nie znaleziono plików!");
						e.printStackTrace();
					}
					break;
					
				case "2":
					try {
						Map<String, Long> resultMap = service.findMostCommonWords();
						for(Map.Entry<String, Long> el : resultMap.entrySet())
						{
							String key = el.getKey().toString();
							Long value = el.getValue();
							System.out.println("KEY: " +key+ " ; VALUE: " +value);
						}
					}
					catch (FileNotFoundException e)
					{
						System.err.println("Nie znaleziono plików!");
						e.printStackTrace();
					}
					break;
					
				case "3":
					System.out.print("Jakiego s³owa szukasz? ");
					String word = scanner.next();
					try {
						Set<String> result = service.findPostIdsByKeyword(word);
						System.out.println("Finded post ids by keyword: " +result);
					}
					catch (FileNotFoundException e)
					{
						System.err.println("Nie znaleziono plików!");
						e.printStackTrace();
					}
					break;
					
				case "4":
					try {
						List<Facebook> resultSet = service.findaAll();
						for(Facebook el : resultSet)
						{
							System.out.println(el);
						}	
					}
					catch (FileNotFoundException e)
					{
						System.err.println("Nie znaleziono plików!");
						e.printStackTrace();
					}
					
					break;
					
				case "0":
					System.exit(0);
					
				default:
					System.err.println("Niepoprawna opcja, spróbuj ponownie");
			}
		}
				
	}
}
