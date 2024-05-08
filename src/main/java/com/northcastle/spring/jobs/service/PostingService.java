package com.northcastle.spring.jobs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.repository.PostingRepository;
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
	public Posting getPosting(Long id) {
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
		posting.setId(9999L);
		
		// don't care if it already exists, as duplicates are allowed
		log.info("addPosting: "+posting);
		
		// return
		return postingRepository.save(posting);
	}
	
	
	/**
	 * Update existing posting. Exception if posting does not exist.
	 * @return
	 */
	public Posting updatePosting() {
		return null;
	}
	
	
	/**
	 * Delete posting by ID
	 * @param id
	 */
	public void deletePosting(Long id) {
		postingRepository.deleteById(id);
	}
}
