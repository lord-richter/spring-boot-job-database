package com.northcastle.spring.jobs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.northcastle.spring.jobs.data.repository.PostingRepository;

@Service
public class PostingService {
	private final PostingRepository postingRepository;

	@Autowired
	public PostingService(PostingRepository postingRepository) {
		this.postingRepository = postingRepository;
	}


}
