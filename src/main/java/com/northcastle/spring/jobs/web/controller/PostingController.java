package com.northcastle.spring.jobs.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.northcastle.spring.jobs.data.entity.Application;
import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.repository.ApplicationRepository;
import com.northcastle.spring.jobs.data.repository.PostingRepository;

@Controller
@RequestMapping("/postings")
public class PostingController {

  private final PostingRepository postingRepository;
  private final ApplicationRepository applicationRepository;

  public PostingController(PostingRepository postingRepository, ApplicationRepository applicationRepository) {
    this.postingRepository = postingRepository;
    this.applicationRepository = applicationRepository;
  }

  @GetMapping
  public String getPostings(Model model){
    model.addAttribute("postings", this.postingRepository.findAll());
    model.addAttribute("applications", this.applicationRepository.findAll());
    model.addAttribute("statuslist",new ArrayList<String>(Application.STATUSLIST));
    System.out.println("getPostings");
    model.asMap().forEach((k,v) -> {System.out.println(k+" = "+v);});
    return "postings";
  }
  
  @GetMapping(path = "/{id}")
  public String getPosting(@PathVariable("id") long postingId, Model model) {
      Optional<Posting> posting = this.postingRepository.findById(postingId);
      if (posting.isEmpty()) {
          throw new ResponseStatusException(
                  HttpStatus.NOT_FOUND, "entity not found"
          );
      }
      
      model.addAttribute("posting", posting.get());
      
      List<Application> applications = new ArrayList<>();
      Iterable<Application> appIterable = this.applicationRepository.findAllByPostingId(posting.get().getId());
      appIterable.forEach(applications::add);
  
      model.addAttribute("application", applications);
      model.addAttribute("module", "postings");
      return "posting";
  }
}