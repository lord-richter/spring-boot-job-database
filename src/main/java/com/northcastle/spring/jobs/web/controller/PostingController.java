package com.northcastle.spring.jobs.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.repository.PostingRepository;
import com.northcastle.spring.jobs.web.forms.PostingForm;
import com.northcastle.spring.jobs.web.forms.UpdateForm;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/postings")
public class PostingController {

	private final PostingRepository postingRepository;

	public static final String PENDING = "Pending";
	public static final String APPLIED = "Applied";
	public static final String INTERVIEW = "Interview";
	public static final String REJECTED = "Rejected";
	public static final String CLOSED = "Closed";

	public static List<String> STATUSLIST = Arrays.asList(PENDING,APPLIED,INTERVIEW,REJECTED,CLOSED);

	public PostingController(PostingRepository postingRepository) {
		this.postingRepository = postingRepository;
	}

	@GetMapping
	public String getPostings(Model model){
		model.addAttribute("postings", this.postingRepository.findAll());
		model.addAttribute("module", "postings");
		System.out.println("getPostings");
		model.asMap().forEach((k,v) -> {System.out.println(k+" = "+v);});
		return "postings";
	}

	@GetMapping(path = "/view/{id}")
	public String getPosting(@PathVariable("id") long postingId, Model model) {
		Optional<Posting> posting = this.postingRepository.findById(postingId);
		if (posting.isEmpty()) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "entity not found"
					);
		}

		model.addAttribute("posting", posting.get());
		model.addAttribute("statuslist",new ArrayList<String>(STATUSLIST));
		model.addAttribute("module", "postings");
		return "posting";
	}

	@GetMapping("/new")
	public String newPosting(PostingForm postingForm, Model model) {
		System.out.println("newPosting");
		System.out.println(postingForm);
		model.addAttribute("posting", postingForm);
		return "newposting";
	}

	@PostMapping("/new")
	public String submitPosting(@Valid PostingForm posting, BindingResult bindingResult, Model model) {
		System.out.println("submitPosting");
		System.out.println(posting);
		// circle back on errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("posting",posting);
			return "newposting";
		}
		
		// save
		Posting newPosting = new Posting();
		newPosting.setPostingName(posting.getPostingName());
		newPosting.setCompanyName(posting.getCompanyName());
		newPosting.setCompanyAddress(posting.getCompanyAddress());
		newPosting.setPostingDate(posting.getPostingDate());
		newPosting.setPostingUrl(posting.getPostingUrl());
		newPosting.setPostingPriority(posting.getPostingPriority());
		newPosting.setPostingRef(posting.getPostingRef());
		newPosting.setAppDate(null);
		newPosting.setAppStatus(PENDING);
		
		model.addAttribute("posting",newPosting);

		model.asMap().forEach((k,v)->{System.out.println(k+" = "+v);});
		
		postingRepository.save(newPosting);
		return "newpostingsuccess";
	}
	
	@GetMapping("/edit/{id}")
	public String editPosting(@PathVariable("id") long postingId, Model model) {
		Optional<Posting> posting_reference = this.postingRepository.findById(postingId);
		if (posting_reference.isEmpty()) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "entity not found"
					);
		}
		
		Posting posting = posting_reference.get();
		UpdateForm updateForm = new UpdateForm();
		
		updateForm.setPostingName(posting.getPostingName());
		updateForm.setCompanyName(posting.getCompanyName());
		updateForm.setCompanyAddress(posting.getCompanyAddress());
		updateForm.setPostingPriority(posting.getPostingPriority());
		updateForm.setPostingRef(posting.getPostingRef());
		updateForm.setPostingUrl(posting.getPostingUrl());
		updateForm.setPostingDate(posting.getPostingDate());
		updateForm.setAppDate(posting.getAppDate());
		updateForm.setAppStatus(posting.getAppStatus());
		
		model.addAttribute("posting",updateForm);
		model.addAttribute("statuslist",new ArrayList<String>(STATUSLIST));
		model.addAttribute("module", "postings");
		model.asMap().forEach((k,v)->{System.out.println(k+" = "+v);});
		return "editposting";
	}

	@PostMapping("/edit/{id}")
	public String updatePosting(@PathVariable("id") long postingId, @Valid UpdateForm posting, BindingResult bindingResult, Model model) {
		System.out.println("submitPosting");
		System.out.println(posting);
		// circle back on errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("posting",posting);
			return "newposting";
		}
		
		// save
		Posting newPosting = new Posting();
		newPosting.setPostingName(posting.getPostingName());
		newPosting.setCompanyName(posting.getCompanyName());
		newPosting.setCompanyAddress(posting.getCompanyAddress());
		newPosting.setPostingDate(posting.getPostingDate());
		newPosting.setPostingUrl(posting.getPostingUrl());
		newPosting.setPostingPriority(posting.getPostingPriority());
		newPosting.setPostingRef(posting.getPostingRef());
		newPosting.setAppDate(null);
		newPosting.setAppStatus(PENDING);
		
		model.addAttribute("posting",newPosting);

		model.asMap().forEach((k,v)->{System.out.println(k+" = "+v);});
		
		postingRepository.save(newPosting);
		return "newpostingsuccess";
	}
	
}
