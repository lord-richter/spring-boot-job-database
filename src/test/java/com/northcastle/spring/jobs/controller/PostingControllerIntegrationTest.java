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
import org.springframework.security.test.context.support.WithMockUser;
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
	@WithMockUser(username = "user1", password = "pwd", roles = "USER")
	void getAllPostings() throws Exception {
		mockMvc.perform(get("/postings"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Fisherman")))
		.andExpect(content().string(containsString("Pilot")))
		.andExpect(content().string(containsString("Professional Fish Cleaner")));
	}

	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "USER")
	void getPosting() throws Exception {
		mockMvc.perform(get("/postings/view/ecf1bde1-3849-4b38-94e6-f8a43a89f816"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Fisherman")));
	}

	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "USER")
	void getPosting_notFound() throws Exception {
		mockMvc.perform(get("/postings/view/ecf1bde1-3849-4b38-94e6-f8a43a89f700"))
		.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void addPosting() throws Exception {
		PostingForm posting = new PostingForm("Secretary","SEC001","http://www.facebook.com",3L,"Lobster Receptionist Service","Southport, NC");
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(posting);
		log.info("addPostingTest(): "+jsonString);
		
		mockMvc.perform(post("/postings/new").content(jsonString).contentType("application/json"))
		.andExpect(status().isCreated())
		.andExpect(content().string(containsString(posting.getCompanyAddress())));
	}

	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void updatePosting() throws Exception {
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
		log.info("updatePostingTest(): "+jsonString);
		mockMvc.perform(put("/postings/edit/a3450c68-fd19-4a7d-b4f3-72119a4bf47d").content(jsonString).contentType("application/json"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Mechanic")))
		.andExpect(content().string(containsString("Pending")));
	}

	@Test
	void updateCustomer_badRequest() throws Exception {
		//    Customer customer = new Customer("c04ca077-8c40-4437-b77a-41f510f3f185","Jack","Bower","quam.quis.diam@facilisisfacilisis.org","(831) 996-1240","2 Rockefeller Avenue, Waco, TX 76796");
		//    ObjectMapper mapper = new ObjectMapper();
		//    String jsonString = mapper.writeValueAsString(customer);
		//    this.mockMvc.perform(put("/customers/2b31469c-da3d-469f-9900-d00b5e4e352f").content(jsonString).contentType("application/json")).andExpect(status().isBadRequest());
	}

	@Test
	void deleteCustomer() throws Exception {
		//    this.mockMvc.perform(delete("/customers/3b6c3ecc-fad7-49db-a14a-f396ed866e50")).andExpect(status().isResetContent());
	}
}


