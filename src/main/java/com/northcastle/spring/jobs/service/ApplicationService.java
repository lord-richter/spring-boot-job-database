package com.northcastle.spring.jobs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.northcastle.spring.jobs.data.entity.Application;
import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.repository.ApplicationRepository;
import com.northcastle.spring.jobs.data.repository.PostingRepository;

@Service
public class ApplicationService {
  private final ApplicationRepository appRepository;
  private final PostingRepository postingRepository;

  @Autowired
  public ApplicationService(PostingRepository postingRepository, ApplicationRepository appRepository) {
    this.appRepository = appRepository;
    this.postingRepository = postingRepository;
  }

  public String getCompanyNameForApplication(Long app_id) {
	  Optional<Application> application = appRepository.findById(app_id);
	  if (application.isPresent()) {
		  Application thisApplication = application.get();
		  Long postingId = thisApplication.getPostingId();
		  Optional<Posting> posting = postingRepository.findById(postingId);
		  if (posting.isPresent()) {
			  Posting appPosting = posting.get();
			  return appPosting.getName();
		  }
	  }
	  return "Unknown";
  }
  
  public String getPositionNameForApplication(Long app_id) {
	  Optional<Application> application = appRepository.findById(app_id);
	  if (application.isPresent()) {
		  Application thisApplication = application.get();
		  Long postingId = thisApplication.getPostingId();
		  Optional<Posting> posting = postingRepository.findById(postingId);
		  if (posting.isPresent()) {
			  Posting appPosting = posting.get();
			  return appPosting.getName();
		  }
	  }
	  return null;
  }

}
