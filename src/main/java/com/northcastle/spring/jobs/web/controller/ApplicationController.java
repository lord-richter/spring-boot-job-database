package com.northcastle.spring.jobs.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.northcastle.spring.jobs.data.repository.ApplicationRepository;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

  private final ApplicationRepository appRepository;

  public ApplicationController(ApplicationRepository appRepository) {
    this.appRepository = appRepository;
  }

  @GetMapping
  public String getApplications(Model model){
    model.addAttribute("applications", this.appRepository.findAll());
    return "applications";
  }
}
