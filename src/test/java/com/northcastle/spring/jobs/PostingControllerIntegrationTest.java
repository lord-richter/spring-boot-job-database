package com.northcastle.spring.jobs;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.forms.PostingForm;

import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.ANY)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
public class PostingControllerIntegrationTest {
	
	@Autowired
	MockMvc mockMvc;

	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void getAllPostings() throws Exception {
		log.debug("TEST.getAllPostings(): "+mockMvc.perform(get("/postings"))
		.andExpect(status().isOk())
		// we are getting a webpage back from all of these queries
		.andExpect(content().string(containsString("Fisherman")))
		.andExpect(content().string(containsString("Pilot")))
		.andExpect(content().string(containsString("Professional Fish Cleaner")))
		.andExpect(content().string(containsString("All Job Postings")))
		// we are returning all of this as a string for debug purposes
		.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void getPosting() throws Exception {
		log.debug("TEST.getPosting(): "+ mockMvc.perform(get("/postings/view/"+CommonTest.VALID_POSTING_1))		
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Fisherman")))
		.andExpect(content().string(containsString("Job Posting Details")))
		.andExpect(content().string(containsString("Folder 1")))
		.andReturn().getResponse().getContentAsString());
	}

	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void getPosting_NotFound() throws Exception {
		log.debug("TEST.getPosting_notFound(): "+ mockMvc.perform(get("/postings/view/"+CommonTest.UNKNOWN_POSTING_UUID))
		.andExpect(status().isNotFound())
		.andReturn().getResponse().getContentAsString());
	}

	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void getNewPosting() throws Exception {
		log.debug("TEST.getNewPosting(): "+mockMvc.perform(get("/postings/new"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("New Job Posting")))
		.andReturn().getResponse().getContentAsString());
	}
	
	
	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void postNewPosting() throws Exception {
		PostingForm posting = new PostingForm(
				CommonTest.FAUX_POSTINGFORM_DATA.get("postingName"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("postingRef"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("postingUrl"),
				3L,
				CommonTest.FAUX_POSTINGFORM_DATA.get("postingFolder"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("companyName"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("companyAddress"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("comment"));
		
		log.debug("TEST.postNewPosting(): "+mockMvc.perform(post("/postings/new").with(MockMvcRequestBuilderUtils.form(posting)))
		.andExpect(status().isCreated())
		.andExpect(content().string(containsString(posting.getCompanyAddress())))
		.andExpect(content().string(containsString("New Posting Succeeded!")))
		.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void postNewPosting_BadRequest() throws Exception {
		PostingForm posting = new PostingForm(
				CommonTest.FAUX_POSTINGFORM_DATA.get("postingName"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("postingRef"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("postingUrl"),
				300L,   // invalidates form
				CommonTest.FAUX_POSTINGFORM_DATA.get("postingFolder"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("companyName"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("companyAddress"),
				CommonTest.FAUX_POSTINGFORM_DATA.get("comment"));
		
		log.debug("TEST.postNewPosting_Bad(): "+mockMvc.perform(post("/postings/new").with(MockMvcRequestBuilderUtils.form(posting)))
		.andExpect(status().isCreated())
		.andExpect(content().string(containsString(CommonTest.FAUX_POSTINGFORM_DATA.get("companyAddress"))))
		.andExpect(content().string(containsString(CommonTest.FAUX_POSTINGFORM_DATA.get("postingName"))))
		.andExpect(content().string(containsString("New Job Posting")))
		.andReturn().getResponse().getContentAsString());
	}
	

	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void getEditPosting() throws Exception {
		log.debug("TEST.getEditPosting(): "+mockMvc.perform(get("/postings/edit/postings/"+CommonTest.VALID_POSTING_3))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Update Job Posting")))
		.andReturn().getResponse().getContentAsString());
	}	

	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void getEditPosting_NotFound() throws Exception {
		log.debug("TEST.getEditPosting_NotFound(): "+ mockMvc.perform(get("/postings/edit/postings/"+CommonTest.UNKNOWN_POSTING_UUID))
		.andExpect(status().isNotFound())
		.andReturn().getResponse().getContentAsString());
	}	
	
	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void putEditPosting() throws Exception {
		Posting existing = new Posting();
		existing.setId(CommonTest.VALID_POSTING_3);
		existing.setPostingName(CommonTest.FAUX_POSTING_DATA.get("postingName"));
		existing.setCompanyName(CommonTest.FAUX_POSTING_DATA.get("companyName"));
		existing.setCompanyAddress(CommonTest.FAUX_POSTING_DATA.get("companyAddress"));
		existing.setPostingRef(CommonTest.FAUX_POSTING_DATA.get("postingRef"));
		existing.setPostingPriority(1L);
		existing.setPostingUrl(CommonTest.FAUX_POSTING_DATA.get("postingUrl"));
		existing.setPostingDate(Date.valueOf(CommonTest.FAUX_POSTING_DATA.get("postingDate")));
		existing.setAppDate(null);
		existing.setAppStatusUrl(null);
		existing.setAppStatus(Posting.PENDING);
		
		log.debug("TEST.putEditPosting(): "+mockMvc.perform(put("/postings/edit/postings/"+CommonTest.VALID_POSTING_3).with(MockMvcRequestBuilderUtils.form(existing)))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString(CommonTest.FAUX_POSTING_DATA.get("postingName"))))
		.andExpect(content().string(containsString("Update Job Posting")))
		.andExpect(content().string(containsString("Pending")))
		.andReturn().getResponse().getContentAsString());
	}

	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void putEditPosting_BadRequest() throws Exception {
		Posting existing = new Posting();
		existing.setId(CommonTest.VALID_POSTING_3);
		existing.setPostingName(CommonTest.FAUX_POSTING_DATA.get("postingName"));
		existing.setCompanyName(null);    // invalidates request
		existing.setCompanyAddress(CommonTest.FAUX_POSTING_DATA.get("companyAddress"));
		existing.setPostingRef(CommonTest.FAUX_POSTING_DATA.get("postingRef"));
		existing.setPostingPriority(1L);
		existing.setPostingUrl(CommonTest.FAUX_POSTING_DATA.get("postingUrl"));
		existing.setPostingDate(Date.valueOf(CommonTest.FAUX_POSTING_DATA.get("postingDate")));
		existing.setAppDate(null);
		existing.setAppStatusUrl(null);
		existing.setAppStatus(Posting.PENDING);
		
		log.debug("TEST.putEditPosting_Bad(): "+mockMvc.perform(put("/postings/edit/postings/"+CommonTest.VALID_POSTING_3).with(MockMvcRequestBuilderUtils.form(existing)))		
		.andExpect(status().isOk())
		.andExpect(content().string(containsString(CommonTest.FAUX_POSTING_DATA.get("postingName"))))
		.andExpect(content().string(containsString("Update Job Posting")))
		.andExpect(content().string(containsString("Pending")))
		.andReturn().getResponse().getContentAsString());

	}

//	@Test
//	void deletePosting() throws Exception {
//		// unsupported operation in this version
//	}
}


