package com.northcastle.spring.jobs.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.repository.PostingRepository;
import com.northcastle.spring.jobs.web.forms.PostingForm;

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
	
	@Value(value = "New Posting Succeeded!")
	String newPostingMessage;

	@Value(value = "Update Posting Succeeded!")
	String updatePostingMessage;
	
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
			model.addAttribute("statuslist",new ArrayList<String>(STATUSLIST));
			model.addAttribute("module", "postings");
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
		newPosting.setAppStatusUrl(null);
		newPosting.setAppStatus(PENDING);
		
		model.asMap().forEach((k,v)->{System.out.println(k+" = "+v);});
		
		postingRepository.save(newPosting);
		model.addAttribute("posting",newPosting);
		model.addAttribute("message",newPostingMessage);
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
		Posting editposting = new Posting();
		System.out.println("editPosting");
		System.out.println(posting);
		BeanUtils.copyProperties(posting, editposting);
		System.out.println(editposting);
		
		model.addAttribute("posting",editposting);
		model.addAttribute("statuslist",new ArrayList<String>(STATUSLIST));
		model.addAttribute("module", "postings");
		System.out.println("model:");
		model.asMap().forEach((k,v)->{System.out.println(k+" = "+v);});
		return "editposting";
	}

	@PostMapping("/edit/{id}")
	public String updatePosting(@PathVariable("id") Long postingId, @Valid @ModelAttribute("Posting") Posting posting, BindingResult bindingResult, Model model) {
		System.out.println("updatePosting");
		System.out.println(postingId);
		System.out.println(posting);
		System.out.println(bindingResult);
		
		
		
		// circle back on errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("posting",posting);
			model.addAttribute("statuslist",new ArrayList<String>(STATUSLIST));
			model.addAttribute("module", "postings");			
			return "editposting";
		}
		
		Optional<Posting> existing_ref = postingRepository.findById(postingId);
		if (existing_ref.isPresent()) {
			Posting existing = existing_ref.get();
			System.out.println("Found: "+existing);
			
			// copy date over
			// Note: this does not overwrite id or postingDate
			existing.setPostingName(posting.getPostingName());
			existing.setCompanyName(posting.getCompanyName());
			existing.setCompanyAddress(posting.getCompanyAddress());
			existing.setPostingPriority(posting.getPostingPriority());
			existing.setPostingRef(posting.getPostingRef());
			existing.setPostingUrl(posting.getPostingUrl());
			existing.setAppStatusUrl(posting.getAppStatusUrl());
			// if status changed
			if (!existing.getAppStatus().equals(posting.getAppStatus())) {
				// if previously Pending, set the application date to now
				if (existing.getAppStatus().equals(PENDING)) {
					existing.setAppDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
				}
				existing.setAppStatus(posting.getAppStatus());
			}
			existing.setAppStatusUrl(posting.getAppStatusUrl());
			
			System.out.println("Updated record:");
			System.out.println(existing);
			
			model.addAttribute("message",updatePostingMessage);
			
			model.asMap().forEach((k,v)->{System.out.println(k+" = "+v);});
			
			model.addAttribute("posting",postingRepository.save(existing));
			return "newpostingsuccess";

		}
		
		return "error";
	}
	
}
