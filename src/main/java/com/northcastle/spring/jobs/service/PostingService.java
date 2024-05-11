package com.northcastle.spring.jobs.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.repository.PostingRepository;
import com.northcastle.spring.jobs.util.Convert;
import com.northcastle.spring.jobs.web.exception.NotFoundException;
import com.northcastle.spring.jobs.web.forms.PostingForm;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostingService {
	private final PostingRepository postingRepository;

	@Autowired
	public PostingService(PostingRepository postingRepository) {
		this.postingRepository = postingRepository;
	}

	/**
	 * Get posting by ID with Not Found Exception
	 * @param id
	 * @return
	 */
	public Posting getPosting(UUID id) {
		Optional<Posting> optionalEntity = this.postingRepository.findById(id);
		if(optionalEntity.isEmpty()){
			throw new NotFoundException("posting not found for id="+id);
		}
		return optionalEntity.get();
	}
	
	/**
	 * Get posting by ID with Not Found Exception
	 * @param id
	 * @return
	 */
	public Posting getPosting(String stringId) {
		UUID id = Convert.stringToUUID(stringId);
		Optional<Posting> optionalEntity = this.postingRepository.findById(id);
		if(optionalEntity.isEmpty()){
			throw new NotFoundException("posting not found for id="+id);
		}
		return optionalEntity.get();
	}	
	
	
	/**
	 * Convert from new form to posting and add to repository
	 * @param newPosting
	 * @return
	 */
	public Posting addPosting(PostingForm newPosting) {
		// copy new posting to posting
		Posting posting = new Posting(newPosting);
		
		if (posting.getId()==null) {
			posting.setId(UUID.randomUUID());
		}
		
		// verify that UUID is not currently used
		// if it is, just get a new one.  Limit this to 10 tries.
		boolean OK=false;
		int looplimit = 10;
		int loop = 0;
		while (!OK && loop<looplimit) {
			loop++;
			Optional<Posting> checkUUID = postingRepository.findById(posting.getId());
			if (checkUUID.isPresent()) {
				posting.setId(UUID.randomUUID());
			} else {
				OK=true;
			}
		}
		
		// peek into data structure
		log.info("addPostingService(): "+posting);
		
		// return
		return postingRepository.save(posting);
	}
	
	
	/**
	 * Update existing posting. Exception if posting does not exist.
	 * @return
	 */
	public Posting updatePosting(Posting update) {
		Optional<Posting> posting_ref = postingRepository.findById(update.getId());
		if (!posting_ref.isPresent()) {
			throw new NotFoundException("Posting ID "+update.getId()+" was not found.");
		}
		
		// existing record
		Posting existing = posting_ref.get();
		
		// copy data over from updated record to existing record
		// Note: this does not overwrite id 
		existing.setPostingName(update.getPostingName());
		existing.setPostingDate(update.getPostingDate());
		existing.setCompanyName(update.getCompanyName());
		existing.setCompanyAddress(update.getCompanyAddress());
		existing.setPostingPriority(update.getPostingPriority());
		existing.setPostingRef(update.getPostingRef());
		existing.setPostingUrl(update.getPostingUrl());
		
		// if status changed, special handling
		if (!existing.getAppStatus().equals(update.getAppStatus())) {
			// if previously Pending, set the application date to now
			if (existing.getAppStatus().equals(Posting.PENDING)) {
				existing.setAppDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
			}
			// status is always updated when changed
			existing.setAppStatus(update.getAppStatus());
		}
		// status url is always updated to latest
		existing.setAppStatusUrl(update.getAppStatusUrl());

		log.info("updatePostingService: "+existing);

		return postingRepository.save(existing);
	}
	
	
	/**
	 * Delete posting by ID
	 * @param id
	 */
	public void deletePosting(UUID id) {
		postingRepository.deleteById(id);
	}
}
