package com.northcastle.spring.jobs.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	public String getAllPostings(Model model){
		model.addAttribute("postings", this.postingRepository.findAll());
		model.addAttribute("module", "postings");
		log.info("Controller.getPostings()");
		model.asMap().forEach((k,v) -> {log.debug(k+" = "+v);});
		return "postings";
	}

	@GetMapping(path = "/view/{id}")
	public String getSinglePosting(@PathVariable("id") UUID postingId, Model model) {
		Posting posting = postingService.getPosting(postingId);
		model.addAttribute("posting", posting);
		model.addAttribute("statuslist",new ArrayList<String>(STATUSLIST));
		model.addAttribute("module", "postings");
		return "posting";
	}

	@GetMapping("/new")
	public String startNewPosting(PostingForm postingForm, Model model) {
		log.info("Controller.startNewPosting(): "+postingForm);
		model.addAttribute("posting", postingForm);
		return "newposting";
	}

	@PostMapping("/new")
	@ResponseStatus(HttpStatus.CREATED)
	public String submitNewPosting(@Valid @RequestBody PostingForm posting, BindingResult bindingResult, Model model) {
		log.info("Controller.submitNewPosting(): "+posting);
		// circle back on form errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("posting",posting);
			model.addAttribute("statuslist",new ArrayList<String>(STATUSLIST));
			model.addAttribute("module", "postings");
			log.warn("Controller.submitNewPosting(): Has errors = "+bindingResult);
			return "newposting";
		}
		
		model.addAttribute("posting",postingService.addPosting(posting));
		model.addAttribute("message",newPostingMessage);
		return "newpostingsuccess";
	}
	
	@GetMapping("/edit/{id}")
	public String startEditPosting(@PathVariable("id") UUID postingId, Model model) {
		Optional<Posting> posting_reference = this.postingRepository.findById(postingId);
		if (posting_reference.isEmpty()) {
			log.info("Controller.startEditPosting(): Posting not found");
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Posting not found"
					);
		}
		
		Posting posting = posting_reference.get();
		Posting editposting = new Posting();
		log.info("Controller.startEditPosting(): "+posting);
		BeanUtils.copyProperties(posting, editposting);
		log.info("Controller.startEditPosting(): "+editposting);
		
		model.addAttribute("posting",editposting);
		model.addAttribute("statuslist",new ArrayList<String>(STATUSLIST));
		model.addAttribute("module", "postings");
		model.asMap().forEach((k,v)->{log.info("Controller.startEditPosting(): "+k+" = "+v);});
		return "editposting";
	}

	@PutMapping("/edit/{id}")
	public String submitEditPosting(@PathVariable("id") UUID postingId, @Valid @RequestBody Posting posting, BindingResult bindingResult, Model model) {
		log.info("Controller.submitEditPosting() :"+postingId);
		log.info("Controller.submitEditPosting() :"+posting);
		log.info("Controller.submitEditPosting() :"+bindingResult);
		
		// circle back on errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("posting",posting);
			model.addAttribute("statuslist",new ArrayList<String>(STATUSLIST));
			model.addAttribute("module", "postings");	
			log.warn("Controller.submitEditPosting(): Has errors = "+bindingResult);
			return "editposting";
		}
		
		model.addAttribute("message",updatePostingMessage);
		model.addAttribute("posting",postingService.updatePosting(posting));
		model.asMap().forEach((k,v)->{log.info("Controller.submitEditPosting() : "+k+" = "+v);});
		return "newpostingsuccess";
	}
	
}
