package com.northcastle.spring.jobs.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.forms.PostingForm;
import com.northcastle.spring.jobs.data.repository.PostingRepository;
import com.northcastle.spring.jobs.exception.NotFoundException;
import com.northcastle.spring.jobs.util.Convert;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostingService {
	private final PostingRepository postingRepository;

	public PostingService(PostingRepository postingRepository) {
		this.postingRepository = postingRepository;
	}

	/**
	 * Convert from new form to posting and add to repository
	 *
	 * @param newPosting
	 * @return
	 */
	public Posting addPosting(PostingForm newPosting) {
		// copy new posting to posting
		Posting posting = new Posting(newPosting);

		// set the UUID for the new posting
		posting.setId(UUID.randomUUID());
		posting.setUpdated(Date.valueOf(LocalDate.ofInstant(new java.util.Date().toInstant(), ZoneId.systemDefault())));

		// verify that UUID is not currently used
		// if it is, just get a new one. Limit this to 10 tries.
		// JUnit will have issues testing this due to the unlikely nature of a duplicate
		// happening during the test run.
		boolean OK = false;
		int looplimit = 10;
		int loop = 0;
		while (!OK && loop < looplimit) {
			loop++;
			Optional<Posting> checkUUID = postingRepository.findById(posting.getId());
			// unit testing note: this is branch is not checked by JUnit:
			if (checkUUID.isPresent()) {
				posting.setId(UUID.randomUUID());
			} else {
				OK = true;
			}
		}

		// peek into data structure
		log.info("Service.addPosting(): " + posting);

		// return
		return postingRepository.save(posting);
	}

	/**
	 * Delete posting by ID
	 *
	 * @param id
	 */
	public void deletePosting(UUID id) {
		postingRepository.deleteById(id);
	}

	/**
	 * Get posting by ID with Not Found Exception
	 *
	 * @param id
	 * @return
	 */
	public Posting getPosting(String stringId) {
		UUID id = Convert.stringToUUID(stringId);
		Optional<Posting> optionalEntity = postingRepository.findById(id);
		if (optionalEntity.isEmpty()) {
			throw new NotFoundException("posting not found for id=" + id);
		}
		return optionalEntity.get();
	}

	/**
	 * Get posting by ID with Not Found Exception
	 *
	 * @param id
	 * @return
	 */
	public Posting getPosting(UUID id) {
		Optional<Posting> optionalEntity = postingRepository.findById(id);
		if (optionalEntity.isEmpty()) {
			throw new NotFoundException("posting not found for id=" + id);
		}
		return optionalEntity.get();
	}

	/**
	 * Update existing posting. Exception if posting does not exist.
	 *
	 * @return
	 */
	public Posting updatePosting(Posting update) {
		Optional<Posting> posting_ref = postingRepository.findById(update.getId());
		if (!posting_ref.isPresent()) {
			throw new NotFoundException("Posting ID " + update.getId() + " was not found.");
		}

		// existing record
		Posting existing = posting_ref.get();

		// copy data over from updated record to existing record
		// Note: this does not overwrite id
		existing.setPostingName(update.getPostingName());
		existing.setPostingDate(update.getPostingDate());
		existing.setCompanyName(update.getCompanyName());
		existing.setCompanyAddress(update.getCompanyAddress());
		existing.setComment(update.getComment());
		existing.setPostingPriority(update.getPostingPriority());
		existing.setPostingFolder(update.getPostingFolder());
		existing.setPostingRef(update.getPostingRef());
		existing.setPostingUrl(update.getPostingUrl());
		existing.setRecruiterEmail(update.getRecruiterEmail());
		existing.setRecruiterName(update.getRecruiterName());

		// if status changed, special handling
		if (!existing.getAppStatus().equals(update.getAppStatus())) {
			log.info("Service.updatePosting(): Application status changed from " + existing.getAppStatus() + " to "
					+ update.getAppStatus());
			// if previously Pending, set the application date to now
			if (existing.getAppStatus().equals(Posting.PENDING)) {
				existing.setAppDate(
						Date.valueOf(LocalDate.ofInstant(new java.util.Date().toInstant(), ZoneId.systemDefault())));
				log.info("Service.updatePosting(): Application date is now: " + existing.getAppDate());
			}
			// status is always updated when changed
			existing.setAppStatus(update.getAppStatus());
			log.info("Service.updatePosting(): Application status is now: " + existing.getAppStatus());
		} else {
			// application date does not change
			existing.setAppDate(update.getAppDate());
			log.info("Service.updatePosting(): Application date is : " + existing.getAppDate());
		}

		// sanity check: clear app date if it gets set to pending
		// otherwise, make sure that the app date gets transferred over
		if (existing.getAppStatus().equals(Posting.PENDING)) {
			existing.setAppDate(null);
			log.info("Service.updatePosting(): Application date is: NULL");
		}

		// status url is always updated to latest
		existing.setAppStatusUrl(update.getAppStatusUrl());

		existing.setUpdated(
				Date.valueOf(LocalDate.ofInstant(new java.util.Date().toInstant(), ZoneId.systemDefault())));

		log.info("Service.updatePosting(): " + existing);

		return postingRepository.save(existing);
	}
}
