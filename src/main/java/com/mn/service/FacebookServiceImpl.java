package com.mn.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mn.entity.Facebook;
import com.mn.entity.Post;


public class FacebookServiceImpl implements FacebookService
{

	public Facebook findById(String id) throws IOException {
		Facebook facebook = new Facebook();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src\\main\\resources\\files\\f" +id+ ".json");
		facebook = mapper.readValue(file, Facebook.class);

		return facebook;
	}

	public Map<String, Long> findMostCommonWords() throws IOException {
		
		Facebook facebook = new Facebook();
		ObjectMapper mapper = new ObjectMapper();
		File directory = new File("src\\main\\resources\\files");
		File[] files = directory.listFiles();
		StringBuilder allPosts = new StringBuilder();
		List<String> listOfWords = new ArrayList<>();
		
		for(File file : files){
			facebook = mapper.readValue(file, Facebook.class);
			allPosts.append(facebook.getPosts());
		}
		
		String messages = allPosts.toString();
		String messagesRegex = messages.replaceAll("Post\\s\\[id\\=\\d+\\,\\smessage\\=|[^a-zA-Z0-9'`\\-\\d]", " ");
				
		String[] messagesArray = messagesRegex.toLowerCase().split("\\s+");
		
		listOfWords = Arrays.asList(messagesArray);
		
		Map<String, Long> countedWords = listOfWords.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		
		return countedWords;
	}

	public Set<String> findPostIdsByKeyword(String word) throws IOException {
		Facebook facebook = new Facebook();
		ObjectMapper mapper = new ObjectMapper();
		List<Post> postsList = new ArrayList<>();
		File directory = new File("src\\main\\resources\\files");
		File[] files = directory.listFiles();
		Set<String> postsId = new HashSet<>();
		
		for(File file : files)
		{
			facebook = mapper.readValue(file, Facebook.class);
			postsList.addAll(facebook.getPosts());
		}
	
		for(Post post : postsList)
		{
			String[] postsArray = post.getMessage().replaceAll("Post\\s\\[id\\=\\d+\\,\\smessage\\=|[^a-zA-Z0-9'`\\d]", " ").trim().split("\\s+");
			for(String el : postsArray)
			{
				if(el.equalsIgnoreCase(word.trim()))
				{
					postsId.add(post.getId());
					break;
				}
			}
		}
		return postsId;
	}

	public List<Facebook> findaAll() throws IOException {
		
		List<Facebook> list = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		File directory = new File("src\\main\\resources\\files\\");
		File[] files = directory.listFiles();
		
		for(File el : files)
		{
			list.add(mapper.readValue(el, Facebook.class));
		}
		
		Collections.sort(list, new Comparator<Facebook>() {

			@Override
			public int compare(Facebook o1, Facebook o2) {
				
				int nameComparison = o1.getFirstname().compareTo(o2.getFirstname());
				if(nameComparison == 0)
				{
					return o1.getLastname().compareTo(o2.getLastname());
				}
				else {
					return nameComparison;
				}
			}
		});
		return list;
	}
}
