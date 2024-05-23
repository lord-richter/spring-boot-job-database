package com.northcastle.spring.jobs;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.ANY)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
public class ApplicationControllerIntegrationTest {
	
	@Autowired
	MockMvc mockMvc;

	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void getAllApplications() throws Exception {
		log.debug("TEST.getAllApplications(): "+mockMvc.perform(get("/applications"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Professional Fish Cleaner")))
		.andExpect(content().string(containsString("Applications")))
		.andReturn().getResponse().getContentAsString());
	}	
	
	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
	void getApplication() throws Exception {
		log.debug("TEST.getApplication(): "+ mockMvc.perform(get("/applications/view/"+CommonTest.VALID_POSTING_1.toString()))		
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Fisherman")))
		.andExpect(content().string(containsString("Job Posting Details")))
		.andExpect(content().string(containsString("Folder 1")))
		.andReturn().getResponse().getContentAsString());
	}

}


