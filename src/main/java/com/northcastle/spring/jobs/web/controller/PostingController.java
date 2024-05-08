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
import com.northcastle.spring.jobs.service.PostingService;
import com.northcastle.spring.jobs.web.forms.PostingForm;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/postings")
public class PostingController {

	private final PostingRepository postingRepository;
	private final PostingService postingService;

	public static List<String> STATUSLIST = Arrays.asList(Posting.PENDING,Posting.APPLIED,Posting.INTERVIEW,Posting.REJECTED,Posting.CLOSED);
	
	@Value(value = "New Posting Succeeded!")
	String newPostingMessage;

	@Value(value = "Update Posting Succeeded!")
	String updatePostingMessage;
	
	public PostingController(PostingRepository postingRepository, PostingService postingService) {
		this.postingRepository = postingRepository;
		this.postingService = postingService;
	}

	@GetMapping
	public String getPostings(Model model){
		model.addAttribute("postings", this.postingRepository.findAll());
		model.addAttribute("module", "postings");
		log.info("getPostings");
		model.asMap().forEach((k,v) -> {log.info(k+" = "+v);});
		return "postings";
	}

	@GetMapping(path = "/view/{id}")
	public String getPosting(@PathVariable("id") long postingId, Model model) {
		Posting posting = postingService.getPosting(postingId);
		model.addAttribute("posting", posting);
		model.addAttribute("statuslist",new ArrayList<String>(STATUSLIST));
		model.addAttribute("module", "postings");
		return "posting";
	}

	@GetMapping("/new")
	public String newPosting(PostingForm postingForm, Model model) {
		log.info("newPosting");
		log.info(postingForm.toString());
		model.addAttribute("posting", postingForm);
		return "newposting";
	}

	@PostMapping("/new")
	public String submitNewPosting(@Valid PostingForm posting, BindingResult bindingResult, Model model) {
		log.info("submitPosting");
		log.info(posting.toString());
		// circle back on form errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("posting",posting);
			model.addAttribute("statuslist",new ArrayList<String>(STATUSLIST));
			model.addAttribute("module", "postings");
			return "newposting";
		}
		
		model.addAttribute("posting",postingService.addPosting(posting));
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
		log.info("editPosting");
		log.info(posting.toString());
		BeanUtils.copyProperties(posting, editposting);
		log.info(editposting.toString());
		
		model.addAttribute("posting",editposting);
		model.addAttribute("statuslist",new ArrayList<String>(STATUSLIST));
		model.addAttribute("module", "postings");
		log.info("model:");
		model.asMap().forEach((k,v)->{log.info(k+" = "+v);});
		return "editposting";
	}

	@PostMapping("/edit/{id}")
	public String updatePosting(@PathVariable("id") Long postingId, @Valid @ModelAttribute("Posting") Posting posting, BindingResult bindingResult, Model model) {
		log.info("updatePosting");
		log.info(postingId.toString());
		log.info(posting.toString());
		log.info(bindingResult.toString());
		
		
		
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
			log.info("Found: "+existing);
			
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
				if (existing.getAppStatus().equals(Posting.PENDING)) {
					existing.setAppDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
				}
				existing.setAppStatus(posting.getAppStatus());
			}
			existing.setAppStatusUrl(posting.getAppStatusUrl());
			
			log.info("Updated record:");
			log.info(existing.toString());
			
			model.addAttribute("message",updatePostingMessage);
			
			model.asMap().forEach((k,v)->{log.info(k+" = "+v);});
			
			model.addAttribute("posting",postingRepository.save(existing));
			return "newpostingsuccess";

		}
		
		return "error";
	}
	
}
