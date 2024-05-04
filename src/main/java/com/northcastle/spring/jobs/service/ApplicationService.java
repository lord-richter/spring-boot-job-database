package com.northcastle.spring.jobs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
