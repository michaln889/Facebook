package com.mn.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mn.entity.Facebook;

public interface FacebookService 
{
	Facebook findById(String id) throws IOException;
	
	Map<String, Long> findMostCommonWords() throws IOException;
	
	Set<String> findPostIdsByKeyword(String word) throws IOException;
	
	List<Facebook> findaAll() throws IOException;
	
}
