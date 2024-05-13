package com.northcastle.spring.jobs.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.repository.PostingRepository;
import com.northcastle.spring.jobs.util.Convert;
import com.northcastle.spring.jobs.web.exception.NotFoundException;
import com.northcastle.spring.jobs.web.forms.PostingForm;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Slf4j
public class PostingServiceIntegrationTest {

	

	@Autowired
	private PostingService postingService;

	@Autowired
	private PostingRepository postingRepository;

	@Test
	void getAllPostings(){
		int count = 5;  // depends on data.sql
		log.info("TEST.getAllPostings(): Count = "+postingRepository.count());
		List<Posting> postings = postingRepository.findAll();
		postings.forEach((v)->{log.info("TEST.getAllPostings(): "+v);});
		assertEquals(count, postings.size());
	}

	@Test
	void getPostingUUID(){
		Posting posting = this.postingService.getPosting(Convert.stringToUUID("ecf1bde1-3849-4b38-94e6-f8a43a89f816"));
		log.info("TEST.getPostingUUID(): "+posting);
		assertNotNull(posting);
		assertEquals("Fisherman", posting.getPostingName());
	}
	
	@Test
	void getPostingString(){
		Posting posting = this.postingService.getPosting("ecf1bde1-3849-4b38-94e6-f8a43a89f816");
		log.info("TEST.getPostingString(): "+posting);
		assertNotNull(posting);
		assertEquals("Fisherman", posting.getPostingName());
	}	

	@Test
	void getPostingUUID_NotFound(){
		assertThrows(NotFoundException.class, () -> this.postingService.getPosting(Convert.stringToUUID("613de372-c0f0-4d12-bb94-27d3320c7000")), "should have thrown an exception");
	}
	
	@Test
	void getPostingString_NotFound(){
		assertThrows(NotFoundException.class, () -> this.postingService.getPosting("613de372-c0f0-4d12-bb94-27d3320c7000"), "should have thrown an exception");
	}

	@Test
	void addPosting(){
		Map<String,String> fauxData = Map.of(
				"postingName","Fish Truck Driver",
				"postingRef","DR001",
				"postingUrl","https://surfcity.com",
				"companyName","No Lobsters Allowed",
				"companyAddress","Surf City, NC"
				);
				
		
		// faux results from web form
		PostingForm postingForm = new PostingForm(fauxData.get("postingName"),
				fauxData.get("postingRef"),
				fauxData.get("postingUrl"),
				1L,
				fauxData.get("companyName"),
				fauxData.get("companyAddress")
				);
		
		Posting posting = this.postingService.addPosting(postingForm);
		
		// verify all fields
		assertNotNull(posting);
		assertNotNull(posting.getId());
		assertNotNull(posting.getPostingDate());
		assertNull(posting.getAppDate());
		assertNull(posting.getAppStatusUrl());
		assertEquals(fauxData.get("postingRef"), posting.getPostingRef());
		assertEquals(fauxData.get("postingName"), posting.getPostingName());
		assertEquals(fauxData.get("postingUrl"), posting.getPostingUrl());
		assertEquals(fauxData.get("companyName"), posting.getCompanyName());
		assertEquals(fauxData.get("companyAddress"), posting.getCompanyAddress());
		assertEquals(Posting.PENDING, posting.getAppStatus());
		
		log.info("TEST.addPosting(): "+posting);
		
		//cleaning up
		this.postingService.deletePosting(posting.getId());
	}

	@Test
	void updatePosting_PendingToApplied(){
		// id matches existing record from data.sql
		Posting existing = postingService.getPosting(Convert.stringToUUID("a3450c68-fd19-4a7d-b4f3-72119a4bf47d"));
		// make changes
		existing.setPostingName("Mechanic II");
		existing.setCompanyName("The Lobster Tank");
		existing.setCompanyAddress("Southport, NC");
		existing.setPostingRef("P0023A");
		existing.setPostingPriority(2L);
		existing.setPostingUrl("http://www.openai.com");
		existing.setPostingDate("2024-05-01");
		existing.setAppStatusUrl("http://www.openai.com/status");
		existing.setAppStatus(Posting.APPLIED);
		
		// update
		Posting posting = postingService.updatePosting(existing);
		
		// check all fields, everything changed
		assertNotNull(posting);
		assertNotNull(posting.getId());
		assertNotNull(posting.getPostingDate());
		assertNotNull(posting.getAppDate());
		assertNotNull(posting.getAppStatusUrl());
		assertEquals(existing.getId(), posting.getId());
		assertEquals(existing.getPostingDate(), posting.getPostingDate());
		assertEquals(existing.getPostingRef(), posting.getPostingRef());
		assertEquals(existing.getPostingName(), posting.getPostingName());
		assertEquals(existing.getPostingUrl(), posting.getPostingUrl());
		assertEquals(existing.getCompanyName(), posting.getCompanyName());
		assertEquals(existing.getCompanyAddress(), posting.getCompanyAddress());
		assertEquals(existing.getAppStatus(), posting.getAppStatus());
		assertEquals(existing.getAppStatusUrl(), posting.getAppStatusUrl());

	}
	

	@Test
	void updatePosting_AppliedToRejected(){
		// id matches existing record from data.sql
		Posting existing = postingService.getPosting(Convert.stringToUUID("ecf1bde1-3849-4b38-94e6-f8a43a89f816"));
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
		assertEquals(existing.getId(), posting.getId());
		assertEquals(existing.getPostingDate(), posting.getPostingDate());
		assertEquals(existing.getPostingRef(), posting.getPostingRef());
		assertEquals(existing.getPostingName(), posting.getPostingName());
		assertEquals(existing.getPostingUrl(), posting.getPostingUrl());
		assertEquals(existing.getCompanyName(), posting.getCompanyName());
		assertEquals(existing.getCompanyAddress(), posting.getCompanyAddress());
		assertEquals(existing.getAppStatus(), posting.getAppStatus());
		assertEquals(existing.getAppDate(), posting.getAppDate());
		assertEquals(existing.getAppStatusUrl(), posting.getAppStatusUrl());
	}
	
	@Test
	void updatePosting_PendingToPending(){
		// id matches existing record from data.sql
		Posting existing = postingService.getPosting(Convert.stringToUUID("613de372-c0f0-4d12-bb94-27d3320c59ec"));
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
		assertEquals(existing.getId(), posting.getId());
		assertEquals(existing.getPostingDate(), posting.getPostingDate());
		assertEquals(existing.getPostingRef(), posting.getPostingRef());
		assertEquals(existing.getPostingName(), posting.getPostingName());
		assertEquals(existing.getPostingUrl(), posting.getPostingUrl());
		assertEquals(existing.getCompanyName(), posting.getCompanyName());
		assertEquals(existing.getCompanyAddress(), posting.getCompanyAddress());
		assertEquals(Posting.PENDING, posting.getAppStatus());
		
	}

	@Test
	void updatePosting_NotFound(){
		// id matches existing record from data.sql
		Posting existing = postingService.getPosting(Convert.stringToUUID("613de372-c0f0-4d12-bb94-27d3320c59ec"));
		// new ID to force mismatch
		existing.setId(Convert.stringToUUID("613de372-c0f0-4d12-bb94-27d3320c5700"));
		
		// update
		assertThrows(NotFoundException.class, () -> this.postingService.updatePosting(existing));
		
	}

}


