package com.northcastle.spring.jobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.forms.PostingForm;
import com.northcastle.spring.jobs.exception.NotFoundException;
import com.northcastle.spring.jobs.service.PostingService;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
public class PostingServiceIntegrationTest {

	@Autowired
	private PostingService postingService;

	@Test
	void getPostingUUID(){
		Posting posting = this.postingService.getPosting(CommonTest.VALID_POSTING_1);
		log.info("TEST.getPostingUUID(): "+posting);
		assertNotNull(posting);
		assertEquals("Fisherman", posting.getPostingName());
	}
	
	@Test
	void getPostingString(){
		Posting posting = this.postingService.getPosting(CommonTest.VALID_POSTING_1.toString());
		log.info("TEST.getPostingString(): "+posting);
		assertNotNull(posting);
		assertEquals("Fisherman", posting.getPostingName());
	}	

	@Test
	void getPostingUUID_NotFound(){
		assertThrows(NotFoundException.class, () -> this.postingService.getPosting(CommonTest.UNKNOWN_POSTING_UUID), "should have thrown an exception");
	}
	
	@Test
	void getPostingString_NotFound(){
		assertThrows(NotFoundException.class, () -> this.postingService.getPosting(CommonTest.UNKNOWN_POSTING_UUID.toString()), "should have thrown an exception");
	}

	@Test
	void addPosting(){
		// faux results from web form
		PostingForm postingForm = new PostingForm(
				CommonTest.FAUX_POSTINGFORM_DATA.get("postingName"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("postingRef"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("postingUrl"),
				1L,
				CommonTest.FAUX_POSTINGFORM_DATA.get("postingFolder"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("companyName"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("companyAddress"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("comment")
				);
		
		Posting posting = this.postingService.addPosting(postingForm);
		
		// verify all fields
		assertNotNull(posting);
		assertNotNull(posting.getId());
		assertNotNull(posting.getPostingDate());
		assertNull(posting.getAppDate());
		assertNull(posting.getAppStatusUrl());
		assertNull(posting.getRecruiterName());
		assertNull(posting.getRecruiterEmail());
		assertEquals(CommonTest.FAUX_POSTINGFORM_DATA.get("postingRef"), posting.getPostingRef());
		assertEquals(CommonTest.FAUX_POSTINGFORM_DATA.get("postingName"), posting.getPostingName());
		assertEquals(CommonTest.FAUX_POSTINGFORM_DATA.get("postingUrl"), posting.getPostingUrl());
		assertEquals(CommonTest.FAUX_POSTINGFORM_DATA.get("postingFolder"), posting.getPostingFolder());
		assertEquals(CommonTest.FAUX_POSTINGFORM_DATA.get("companyName"), posting.getCompanyName());
		assertEquals(CommonTest.FAUX_POSTINGFORM_DATA.get("companyAddress"), posting.getCompanyAddress());
		assertEquals(CommonTest.FAUX_POSTINGFORM_DATA.get("comment"), posting.getComment());
		assertEquals(Posting.PENDING, posting.getAppStatus());
		
		log.info("TEST.addPosting(): "+posting);
		
		//cleaning up
		this.postingService.deletePosting(posting.getId());
	}

	@Test 
	void updatePosting_PendingToApplied(){
		// id matches existing record from data.sql
		Posting existing = postingService.getPosting(CommonTest.VALID_POSTING_3);
		// make changes
		existing.setPostingName(CommonTest.FAUX_POSTING_DATA.get("postingName"));
		existing.setCompanyName(CommonTest.FAUX_POSTING_DATA.get("companyName"));
		existing.setCompanyAddress(CommonTest.FAUX_POSTING_DATA.get("companyAddress"));
		existing.setComment(CommonTest.FAUX_POSTING_DATA.get("comment"));
		existing.setPostingFolder(CommonTest.FAUX_POSTING_DATA.get("postingFolder"));
		existing.setPostingRef(CommonTest.FAUX_POSTING_DATA.get("postingRef"));
		existing.setPostingPriority(2L);
		existing.setPostingUrl(CommonTest.FAUX_POSTING_DATA.get("postingUrl"));
		existing.setPostingDate(Date.valueOf(CommonTest.FAUX_POSTING_DATA.get("postingDate")));
		existing.setAppStatusUrl(CommonTest.FAUX_POSTING_DATA.get("appStatusUrl"));
		existing.setRecruiterName(CommonTest.FAUX_NAME);
		existing.setRecruiterEmail(CommonTest.FAUX_EMAIL);
		existing.setAppStatus(Posting.APPLIED);
		
		// update
		Posting posting = postingService.updatePosting(existing);
		
		// check all fields, everything changed
		assertNotNull(posting);
		assertNotNull(posting.getId());
		assertNotNull(posting.getPostingDate());
		assertNotNull(posting.getAppDate());
		assertNotNull(posting.getAppStatusUrl());
		assertNotNull(posting.getUpdated());
		assertEquals(existing.getId(), posting.getId());
		assertEquals(existing.getPostingDate(), posting.getPostingDate());
		assertEquals(existing.getPostingRef(), posting.getPostingRef());
		assertEquals(existing.getPostingName(), posting.getPostingName());
		assertEquals(existing.getPostingUrl(), posting.getPostingUrl());
		assertEquals(existing.getPostingFolder(), posting.getPostingFolder());
		assertEquals(existing.getCompanyName(), posting.getCompanyName());
		assertEquals(existing.getCompanyAddress(), posting.getCompanyAddress());
		assertEquals(existing.getComment(), posting.getComment());
		assertEquals(existing.getAppStatus(), posting.getAppStatus());
		assertEquals(existing.getAppStatusUrl(), posting.getAppStatusUrl());
		assertEquals(existing.getRecruiterEmail(), posting.getRecruiterEmail());
		assertEquals(existing.getRecruiterName(), posting.getRecruiterName());

	}
	

	@Test
	void updatePosting_AppliedToRejected(){
		// id matches existing record from data.sql
		Posting existing = postingService.getPosting(CommonTest.VALID_APPLIED_1);
		// make changes
		existing.setAppStatus(Posting.REJECTED);
		
		// update
		Posting posting = postingService.updatePosting(existing);

		// check all fields, app field is the only change
		assertNotNull(posting);
		assertNotNull(posting.getId());
		assertNotNull(posting.getPostingDate());
		assertNotNull(posting.getAppDate());
		assertNotNull(posting.getAppStatusUrl());
		assertNotNull(posting.getUpdated());
		assertEquals(existing.getId(), posting.getId());
		assertEquals(existing.getPostingDate(), posting.getPostingDate());
		assertEquals(existing.getPostingRef(), posting.getPostingRef());
		assertEquals(existing.getPostingName(), posting.getPostingName());
		assertEquals(existing.getPostingUrl(), posting.getPostingUrl());
		assertEquals(existing.getPostingFolder(), posting.getPostingFolder());
		assertEquals(existing.getCompanyName(), posting.getCompanyName());
		assertEquals(existing.getCompanyAddress(), posting.getCompanyAddress());
		assertEquals(existing.getComment(), posting.getComment());
		assertEquals(existing.getAppStatus(), posting.getAppStatus());
		assertEquals(existing.getAppDate(), posting.getAppDate());
		assertEquals(existing.getAppStatusUrl(), posting.getAppStatusUrl());
		assertEquals(existing.getRecruiterEmail(), posting.getRecruiterEmail());
		assertEquals(existing.getRecruiterName(), posting.getRecruiterName());		
	}
	
	@Test
	void updatePosting_PendingToPending(){
		// id matches existing record from data.sql
		Posting existing = postingService.getPosting(CommonTest.VALID_PENDING_1);
		// make changes
		existing.setPostingName("Apprentice Fish Cleaner (Remote)");
		
		// update
		Posting posting = postingService.updatePosting(existing);

		
		// check all fields, app fields will still be null
		assertNotNull(posting);
		assertNotNull(posting.getId());
		assertNotNull(posting.getPostingDate());
		assertNull(posting.getAppDate());
		assertNull(posting.getAppStatusUrl());
		assertNotNull(posting.getUpdated());
		assertEquals(existing.getId(), posting.getId());
		assertEquals(existing.getPostingDate(), posting.getPostingDate());
		assertEquals(existing.getPostingRef(), posting.getPostingRef());
		assertEquals(existing.getPostingName(), posting.getPostingName());
		assertEquals(existing.getPostingUrl(), posting.getPostingUrl());
		assertEquals(existing.getPostingFolder(), posting.getPostingFolder());
		assertEquals(existing.getCompanyName(), posting.getCompanyName());
		assertEquals(existing.getCompanyAddress(), posting.getCompanyAddress());
		assertEquals(existing.getComment(), posting.getComment());
		assertEquals(existing.getRecruiterEmail(), posting.getRecruiterEmail());
		assertEquals(existing.getRecruiterName(), posting.getRecruiterName());
		assertEquals(Posting.PENDING, posting.getAppStatus());
		
	}

	@Test
	void updatePosting_NotFound(){
		// id matches existing record from data.sql
		Posting existing = postingService.getPosting(CommonTest.VALID_POSTING_1);
		// new ID to force mismatch
		existing.setId(CommonTest.UNKNOWN_POSTING_UUID);
		
		// update
		assertThrows(NotFoundException.class, () -> this.postingService.updatePosting(existing));
		
	}

}


