package com.northcastle.spring.jobs.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.util.Convert;
import com.northcastle.spring.jobs.web.forms.PostingForm;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Slf4j
public class PostingControllerIntegrationTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void getAllPostings() throws Exception {
		log.debug("TEST.getAllPostings(): "+mockMvc.perform(get("/postings"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Fisherman")))
		.andExpect(content().string(containsString("Pilot")))
		.andExpect(content().string(containsString("Professional Fish Cleaner")))
		.andExpect(content().string(containsString("All Job Postings")))
		.andReturn().getResponse().getContentAsString());
	}

	@Test
	void getPosting() throws Exception {
		log.debug("TEST.getPosting(): "+ mockMvc.perform(get("/postings/view/ecf1bde1-3849-4b38-94e6-f8a43a89f816"))		
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Fisherman")))
		.andExpect(content().string(containsString("Job Posting Details")))
		.andReturn().getResponse().getContentAsString());
	}

	@Test
	void getPosting_NotFound() throws Exception {
		log.debug("TEST.getPosting_notFound(): "+ mockMvc.perform(get("/postings/view/ecf1bde1-3849-4b38-94e6-f8a43a89f700"))
		.andExpect(status().isNotFound())
		.andReturn().getResponse().getContentAsString());
	}

	@Test
	void getNewPosting() throws Exception {
		log.debug("TEST.getNewPosting(): "+mockMvc.perform(get("/postings/new"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("New Job Posting")))
		.andReturn().getResponse().getContentAsString());
	}
	
	
	@Test
	void putNewPosting() throws Exception {
		PostingForm posting = new PostingForm("Secretary","SEC001","http://www.facebook.com",3L,"Lobster Receptionist Service","Southport, NC");
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(posting);
		log.info("TEST.putNewPosting(): "+jsonString);
		
		log.debug("TEST.putNewPosting(): "+mockMvc.perform(post("/postings/new").content(jsonString).contentType("application/json"))
		.andExpect(status().isCreated())
		.andExpect(content().string(containsString(posting.getCompanyAddress())))
		.andExpect(content().string(containsString("New Posting Succeeded!")))
		.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	void putNewPosting_BadRequest() throws Exception {
		PostingForm posting = new PostingForm("Secretary","SEC001","http://www.facebook.com",300L,"Lobster Receptionist Service","Southport, NC");
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(posting);
		log.info("TEST.putNewPosting_Bad(): "+jsonString);
		
		log.debug("TEST.putNewPosting_Bad(): "+mockMvc.perform(post("/postings/new").content(jsonString).contentType("application/json"))
		.andExpect(status().isCreated())
		.andExpect(content().string(containsString(posting.getCompanyAddress())))
		.andExpect(content().string(containsString("New Job Posting")))
		.andReturn().getResponse().getContentAsString());
	}
	

	@Test
	void getEditPosting() throws Exception {
		log.debug("TEST.getEditPosting(): "+mockMvc.perform(get("/postings/edit/a3450c68-fd19-4a7d-b4f3-72119a4bf47d"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Update Job Posting")))
		.andReturn().getResponse().getContentAsString());
	}	

	@Test
	void getEditPosting_NotFound() throws Exception {
		log.debug("TEST.getEditPosting_NotFound(): "+ mockMvc.perform(get("/postings/edit/ecf1bde1-3849-4b38-94e6-f8a43a89f700"))
		.andExpect(status().isNotFound())
		.andReturn().getResponse().getContentAsString());
	}	
	
	@Test
	void putEditPosting() throws Exception {
		Posting existing = new Posting();
		existing.setId(Convert.stringToUUID("a3450c68-fd19-4a7d-b4f3-72119a4bf47d"));
		existing.setPostingName("Mechanic");
		existing.setCompanyName("The Lobster Claw");
		existing.setCompanyAddress("Wilmington, NC");
		existing.setPostingRef("P0023");
		existing.setPostingPriority(1L);
		existing.setPostingUrl("http://www.google.com");
		existing.setPostingDate("2024-05-05");
		existing.setAppDate(null);
		existing.setAppStatusUrl(null);
		existing.setAppStatus(Posting.PENDING);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(existing);
		log.info("TEST.putEditPosting(): "+jsonString);
		log.debug("TEST.putEditPosting(): "+mockMvc.perform(put("/postings/edit/a3450c68-fd19-4a7d-b4f3-72119a4bf47d").content(jsonString).contentType("application/json"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Mechanic")))
		.andExpect(content().string(containsString("Update Posting Succeeded!")))
		.andExpect(content().string(containsString("Pending")))
		.andReturn().getResponse().getContentAsString());
	}

	@Test
	void putEditPosting_BadRequest() throws Exception {
		Posting existing = new Posting();
		existing.setId(Convert.stringToUUID("a3450c68-fd19-4a7d-b4f3-72119a4bf47d"));
		existing.setPostingName("Mechanic");
		existing.setCompanyName("The Lobster Claw");
		existing.setCompanyAddress("Wilmington, NC");
		existing.setPostingRef("P0023");
		existing.setPostingPriority(1L);
		existing.setPostingUrl("http://www.google.com");
		existing.setPostingDate("2024-05-05");
		existing.setAppDate(null);
		existing.setAppStatusUrl(null);
		existing.setAppStatus(null);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(existing);
		log.info("TEST.putEditPosting_Bad(): "+jsonString);
		log.debug("TEST.putEditPosting_Bad(): "+mockMvc.perform(put("/postings/edit/a3450c68-fd19-4a7d-b4f3-72119a4bf47d").content(jsonString).contentType("application/json"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Mechanic")))
		.andExpect(content().string(containsString("Update Job Posting")))
		.andExpect(content().string(containsString("Pending")))
		.andReturn().getResponse().getContentAsString());

	}

//	@Test
//	void deletePosting() throws Exception {
//		// unsupported operation in this version
//	}
}


