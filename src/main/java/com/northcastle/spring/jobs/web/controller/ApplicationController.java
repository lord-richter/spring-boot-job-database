package com.northcastle.spring.jobs.web.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.repository.PostingRepository;
import com.northcastle.spring.jobs.service.PostingService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/applications")
public class ApplicationController {

	@Autowired
	private final PostingRepository postingRepository;
	
	@Autowired
	private final PostingService postingService;

	public ApplicationController(PostingRepository postingRepository, PostingService postingService) {
		this.postingRepository = postingRepository;
		this.postingService = postingService;
	}

	@GetMapping
	public String getApplications(Model model){
		model.addAttribute("applications", this.postingRepository.findByAppStatusIn(PostingRepository.allActiveStatus, Sort.by(Direction.DESC,"appDate")));
		model.addAttribute("module", "applications");
		model.asMap().forEach((k,v)->{log.info("Controller.startEditPosting(): "+k+" = "+v);});
		return "applications";
	}

	@GetMapping(path = "/view/{id}")
	public String getSinglePosting(@PathVariable("id") UUID postingId, Model model) {
		Posting posting = postingService.getPosting(postingId);
		model.addAttribute("posting", posting);
		model.addAttribute("statuslist",new ArrayList<String>(PostingController.STATUSLIST));
		model.addAttribute("module", "applications");
		return "posting";
	}
	
}
