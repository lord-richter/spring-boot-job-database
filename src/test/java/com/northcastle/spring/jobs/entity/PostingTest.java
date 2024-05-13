package com.northcastle.spring.jobs.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.service.model.PostingModel;
import com.northcastle.spring.jobs.web.forms.PostingForm;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class PostingTest {
	
	static String fauxString = "FAUX";
	static UUID fauxUUID = UUID.randomUUID();
	static Long fauxLong = 685492945L;
	static Date fauxDate = Date.valueOf("2024-01-01");
	static Map<String,String> fauxData = Map.ofEntries(
			new AbstractMap.SimpleEntry<String, String>("postingName","Fish Truck Driver"),
			new AbstractMap.SimpleEntry<String, String>("postingRef","DR001"),
			new AbstractMap.SimpleEntry<String, String>("postingUrl","https://surfcity.com"),
			new AbstractMap.SimpleEntry<String, String>("postingDate","2024-01-01"),
			new AbstractMap.SimpleEntry<String, String>("companyName","No Lobsters Allowed"),
			new AbstractMap.SimpleEntry<String, String>("companyAddress","Surf City, NC"),
			new AbstractMap.SimpleEntry<String, String>("appDate","2024-01-02"),
			new AbstractMap.SimpleEntry<String, String>("appStatus",Posting.APPLIED),
			new AbstractMap.SimpleEntry<String, String>("appStatusUrl","http://"),
			new AbstractMap.SimpleEntry<String, String>("comment","This is a comment")
			);

	@Test
	public void postingConstructorDefault() {
	
		Posting posting = new Posting();

		assertNotNull(posting);
		
		assertNull(posting.getId());
		posting.setId(fauxUUID);
		assertEquals(fauxUUID, posting.getId());
		
		assertNull(posting.getPostingDate());
		posting.setPostingDate(fauxDate);
		assertEquals(fauxDate, posting.getPostingDate());
		
		
		assertNull(posting.getAppDate());
		posting.setAppDate(fauxDate);
		assertEquals(fauxDate, posting.getAppDate());
		
		
		assertNull(posting.getAppStatusUrl());
		posting.setAppStatusUrl(fauxString);
		assertEquals(fauxString, posting.getAppStatusUrl());
		
		
		assertNull(posting.getPostingRef());
		posting.setPostingRef(fauxString);
		assertEquals(fauxString, posting.getPostingRef());
		
		assertNull(posting.getPostingPriority());
		posting.setPostingPriority(fauxLong);
		assertEquals(fauxLong, posting.getPostingPriority());
				
		assertNull(posting.getPostingName());
		posting.setPostingName(fauxString);
		assertEquals(fauxString, posting.getPostingName());
		
		assertNull(posting.getPostingUrl());
		posting.setPostingUrl(fauxString);
		assertEquals(fauxString, posting.getPostingUrl());

		assertNull(posting.getCompanyName());
		posting.setCompanyName(fauxString);
		assertEquals(fauxString, posting.getCompanyName());

		assertNull(posting.getCompanyAddress());
		posting.setCompanyAddress(fauxString);
		assertEquals(fauxString, posting.getCompanyAddress());
		
		assertNull(posting.getComment());
		posting.setComment(fauxString);
		assertEquals(fauxString, posting.getComment());		
		
		assertEquals(Posting.PENDING, posting.getAppStatus());
		posting.setAppStatus(Posting.APPLIED);
		assertEquals(Posting.APPLIED, posting.getAppStatus());

		assertTrue(posting.toString().startsWith("Posting"));
		assertTrue(posting.equals(posting));
		assertFalse(posting.equals(new Posting()));
		assertTrue(posting.hashCode()!=(new Object()).hashCode());
	}

	@Test
	public void postingModelConstructorDefault() {
	
		PostingModel posting = new PostingModel();

		assertNotNull(posting);
		
		assertNull(posting.getId());
		posting.setId(fauxUUID);
		assertEquals(fauxUUID, posting.getId());
		
		assertNull(posting.getPostingDate());
		posting.setPostingDate(fauxDate);
		assertEquals(fauxDate, posting.getPostingDate());
		
		
		assertNull(posting.getAppDate());
		posting.setAppDate(fauxDate);
		assertEquals(fauxDate, posting.getAppDate());
		
		
		assertNull(posting.getAppStatusUrl());
		posting.setAppStatusUrl(fauxString);
		assertEquals(fauxString, posting.getAppStatusUrl());
		
		
		assertNull(posting.getPostingRef());
		posting.setPostingRef(fauxString);
		assertEquals(fauxString, posting.getPostingRef());
		
		assertNull(posting.getPostingPriority());
		posting.setPostingPriority(fauxLong);
		assertEquals(fauxLong, posting.getPostingPriority());
				
		assertNull(posting.getPostingName());
		posting.setPostingName(fauxString);
		assertEquals(fauxString, posting.getPostingName());
		
		assertNull(posting.getPostingUrl());
		posting.setPostingUrl(fauxString);
		assertEquals(fauxString, posting.getPostingUrl());

		assertNull(posting.getCompanyName());
		posting.setCompanyName(fauxString);
		assertEquals(fauxString, posting.getCompanyName());
		
		assertNull(posting.getCompanyAddress());
		posting.setCompanyAddress(fauxString);
		assertEquals(fauxString, posting.getCompanyAddress());
		
		assertNull(posting.getComment());
		posting.setComment(fauxString);
		assertEquals(fauxString, posting.getComment());	
		
		assertNull(posting.getAppStatus());
		posting.setAppStatus(Posting.APPLIED);
		assertEquals(Posting.APPLIED, posting.getAppStatus());

		assertTrue(posting.toString().startsWith("PostingModel"));
		assertTrue(posting.equals(posting));
		assertFalse(posting.equals(new PostingModel()));
		assertTrue(posting.hashCode()!=(new Object()).hashCode());
	}

	
	
	@Test
	public void postingFormConstructorDefault() {
		
		PostingForm postingForm = new PostingForm();

		assertNotNull(postingForm);
		
		assertNotNull(postingForm.getPostingDate());
		postingForm.setPostingDate(fauxDate);
		assertEquals(fauxDate, postingForm.getPostingDate());
				
		assertNull(postingForm.getPostingRef());
		postingForm.setPostingRef(fauxString);
		assertEquals(fauxString, postingForm.getPostingRef());
		
		assertNull(postingForm.getPostingName());
		postingForm.setPostingName(fauxString);
		assertEquals(fauxString, postingForm.getPostingName());
		
		assertNull(postingForm.getPostingUrl());
		postingForm.setPostingUrl(fauxString);
		assertEquals(fauxString, postingForm.getPostingUrl());

		assertNull(postingForm.getCompanyName());
		postingForm.setCompanyName(fauxString);
		assertEquals(fauxString, postingForm.getCompanyName());
		
		assertNull(postingForm.getCompanyAddress());
		postingForm.setCompanyAddress(fauxString);
		assertEquals(fauxString, postingForm.getCompanyAddress());
		
		assertNull(postingForm.getComment());
		postingForm.setComment(fauxString);
		assertEquals(fauxString, postingForm.getComment());			
		
		assertEquals(1L, postingForm.getPostingPriority().longValue());
		postingForm.setPostingPriority(fauxLong);
		assertEquals(fauxLong.longValue(), postingForm.getPostingPriority().longValue());
		
		assertTrue(postingForm.toString().startsWith("PostingForm"));
		assertTrue(postingForm.equals(postingForm));
		assertFalse(postingForm.equals(new PostingForm()));
		assertTrue(postingForm.hashCode()!=(new Object()).hashCode());
	}
	
	@Test
	public void postingContstructorAllFields() throws ParseException  {
	
		Posting posting = new Posting(fauxUUID,
				fauxData.get("postingName"),
				fauxData.get("postingRef"),
				fauxData.get("postingUrl"),
				fauxLong,
				Date.valueOf(fauxData.get("postingDate")),
				fauxData.get("companyName"),
				fauxData.get("companyAddress"),
				fauxData.get("comment"),
				Date.valueOf(fauxData.get("appDate")),
				fauxData.get("appStatus"),
				fauxData.get("appStatusUrl"));

		assertNotNull(posting);
		
		assertEquals(fauxUUID, posting.getId());
		
		assertEquals(Date.valueOf(fauxData.get("postingDate")), posting.getPostingDate());
		
		
		assertEquals(Date.valueOf(fauxData.get("appDate")), posting.getAppDate());
		
		
		assertEquals(fauxData.get("appStatusUrl"), posting.getAppStatusUrl());
		
		
		assertEquals(fauxData.get("postingRef"), posting.getPostingRef());
		
		assertEquals(fauxLong, posting.getPostingPriority());
				
		assertEquals(fauxData.get("postingName"), posting.getPostingName());
		
		assertEquals(fauxData.get("postingUrl"), posting.getPostingUrl());

		assertEquals(fauxData.get("companyName"), posting.getCompanyName());
		
		assertEquals(fauxData.get("companyAddress"), posting.getCompanyAddress());
		
		assertEquals(fauxData.get("comment"), posting.getComment());
		
		assertEquals(fauxData.get("appStatus"), posting.getAppStatus());
	}
		
	
	@Test
	public void postingFormContstructorAllFields() {
		PostingForm postingForm = new PostingForm(
				fauxData.get("postingName"),
				fauxData.get("postingRef"),
				fauxData.get("postingUrl"),
				fauxLong,
				fauxData.get("companyName"),
				fauxData.get("companyAddress"),
				fauxData.get("comment")
		);
		
		assertNotNull(postingForm);
		
		assertNotNull(postingForm.getPostingDate());
		
		assertEquals(fauxData.get("postingRef"), postingForm.getPostingRef());
		
		assertEquals(fauxLong, postingForm.getPostingPriority());
				
		assertEquals(fauxData.get("postingName"), postingForm.getPostingName());
		
		assertEquals(fauxData.get("postingUrl"), postingForm.getPostingUrl());

		assertEquals(fauxData.get("companyName"), postingForm.getCompanyName());
		
		assertEquals(fauxData.get("companyAddress"), postingForm.getCompanyAddress());
		
		assertEquals(fauxData.get("comment"), postingForm.getComment());
	
	}
	
	@Test
	public void postingConstructorCopy() {
		PostingForm postingForm = new PostingForm(
				fauxData.get("postingName"),
				fauxData.get("postingRef"),
				fauxData.get("postingUrl"),
				fauxLong,
				fauxData.get("companyName"),
				fauxData.get("companyAddress"),
				fauxData.get("comment")
		);
		
		assertNotNull(postingForm);
		
		Posting posting = new Posting(postingForm);
		
		assertNotNull(posting);
		
		System.out.println(posting);
		
		assertNull(posting.getId());
		
		assertNotNull(posting.getPostingDate());
		
		assertNull(posting.getAppDate());
		
		assertNull(posting.getAppStatusUrl());
		
		assertEquals(fauxData.get("postingRef"), posting.getPostingRef());
		
		assertEquals(fauxLong, posting.getPostingPriority());
				
		assertEquals(fauxData.get("postingName"), posting.getPostingName());
		
		assertEquals(fauxData.get("postingUrl"), posting.getPostingUrl());

		assertEquals(fauxData.get("companyName"), posting.getCompanyName());
		
		assertEquals(fauxData.get("companyAddress"), posting.getCompanyAddress());
		
		assertEquals(fauxData.get("comment"), posting.getComment());
		
		assertEquals(Posting.PENDING, posting.getAppStatus());

	}

}
