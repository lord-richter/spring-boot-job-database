package com.northcastle.spring.jobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.forms.PostingForm;
import com.northcastle.spring.jobs.service.model.PostingModel;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class PostingTest {

	@Test
	public void postingConstructorCopy() {
		PostingForm postingForm = new PostingForm(CommonTest.FAUX_POSTING_DATA.get("postingName"),
				CommonTest.FAUX_POSTING_DATA.get("postingRef"), CommonTest.FAUX_POSTING_DATA.get("postingUrl"),
				CommonTest.FAUX_LONG, CommonTest.FAUX_POSTING_DATA.get("postingFolder"),
				CommonTest.FAUX_POSTING_DATA.get("companyName"), CommonTest.FAUX_POSTING_DATA.get("companyAddress"),
				CommonTest.FAUX_POSTING_DATA.get("comment"));

		assertNotNull(postingForm);

		Posting posting = new Posting(postingForm);

		assertNotNull(posting);

		System.out.println(posting);

		assertNull(posting.getId());

		assertNotNull(posting.getPostingDate());

		assertNull(posting.getAppDate());

		assertNull(posting.getAppStatusUrl());

		assertNull(posting.getRecruiterEmail());

		assertNull(posting.getRecruiterName());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("postingRef"), posting.getPostingRef());

		assertEquals(CommonTest.FAUX_LONG, posting.getPostingPriority());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("postingName"), posting.getPostingName());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("postingUrl"), posting.getPostingUrl());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("postingFolder"), posting.getPostingFolder());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("companyName"), posting.getCompanyName());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("companyAddress"), posting.getCompanyAddress());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("comment"), posting.getComment());

		assertEquals(Posting.PENDING, posting.getAppStatus());

	}

	@Test
	public void postingConstructorDefault() {

		Posting posting = new Posting();

		assertNotNull(posting);

		assertNull(posting.getId());
		posting.setId(CommonTest.FAUX_UUID);
		assertEquals(CommonTest.FAUX_UUID, posting.getId());

		assertNull(posting.getPostingDate());
		posting.setPostingDate(CommonTest.FAUX_DATE);
		assertEquals(CommonTest.FAUX_DATE, posting.getPostingDate());

		assertNull(posting.getAppDate());
		posting.setAppDate(CommonTest.FAUX_DATE);
		assertEquals(CommonTest.FAUX_DATE, posting.getAppDate());

		assertNull(posting.getAppStatusUrl());
		posting.setAppStatusUrl(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getAppStatusUrl());

		assertNull(posting.getPostingRef());
		posting.setPostingRef(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getPostingRef());

		assertNull(posting.getPostingPriority());
		posting.setPostingPriority(CommonTest.FAUX_LONG);
		assertEquals(CommonTest.FAUX_LONG, posting.getPostingPriority());

		assertNull(posting.getPostingName());
		posting.setPostingName(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getPostingName());

		assertNull(posting.getPostingUrl());
		posting.setPostingUrl(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getPostingUrl());

		assertNull(posting.getPostingFolder());
		posting.setPostingFolder(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getPostingFolder());

		assertNull(posting.getCompanyName());
		posting.setCompanyName(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getCompanyName());

		assertNull(posting.getCompanyAddress());
		posting.setCompanyAddress(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getCompanyAddress());

		assertNull(posting.getComment());
		posting.setComment(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getComment());

		assertEquals(Posting.PENDING, posting.getAppStatus());
		posting.setAppStatus(Posting.APPLIED);
		assertEquals(Posting.APPLIED, posting.getAppStatus());

		assertTrue(posting.toString().startsWith("Posting"));
		assertTrue(posting.equals(posting));
		assertFalse(posting.equals(new Posting()));
		assertTrue(posting.hashCode() != (new Object()).hashCode());
	}

	@Test
	public void postingContstructorAllFields() throws ParseException {

		Posting posting = new Posting(CommonTest.FAUX_UUID, CommonTest.FAUX_POSTING_DATA.get("postingName"),
				CommonTest.FAUX_POSTING_DATA.get("postingRef"), CommonTest.FAUX_POSTING_DATA.get("postingUrl"),
				CommonTest.FAUX_LONG, Date.valueOf(CommonTest.FAUX_POSTING_DATA.get("postingDate")),
				CommonTest.FAUX_POSTING_DATA.get("postingFolder"), CommonTest.FAUX_POSTING_DATA.get("companyName"),
				CommonTest.FAUX_POSTING_DATA.get("companyAddress"), CommonTest.FAUX_POSTING_DATA.get("comment"),
				Date.valueOf(CommonTest.FAUX_POSTING_DATA.get("appDate")),
				CommonTest.FAUX_POSTING_DATA.get("appStatus"), CommonTest.FAUX_POSTING_DATA.get("appStatusUrl"),
				CommonTest.FAUX_POSTING_DATA.get("recruiterName"), CommonTest.FAUX_POSTING_DATA.get("recruiterEmail"),
				null);

		assertNotNull(posting);

		assertEquals(CommonTest.FAUX_UUID, posting.getId());

		assertEquals(Date.valueOf(CommonTest.FAUX_POSTING_DATA.get("postingDate")), posting.getPostingDate());

		assertEquals(Date.valueOf(CommonTest.FAUX_POSTING_DATA.get("appDate")), posting.getAppDate());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("appStatusUrl"), posting.getAppStatusUrl());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("postingRef"), posting.getPostingRef());

		assertEquals(CommonTest.FAUX_LONG, posting.getPostingPriority());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("postingName"), posting.getPostingName());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("postingUrl"), posting.getPostingUrl());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("postingFolder"), posting.getPostingFolder());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("companyName"), posting.getCompanyName());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("companyAddress"), posting.getCompanyAddress());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("comment"), posting.getComment());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("recruiterName"), posting.getRecruiterName());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("recruiterEmail"), posting.getRecruiterEmail());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("appStatus"), posting.getAppStatus());
	}

	@Test
	public void postingFormConstructorDefault() {

		PostingForm postingForm = new PostingForm();

		assertNotNull(postingForm);

		assertNotNull(postingForm.getPostingDate());
		postingForm.setPostingDate(CommonTest.FAUX_DATE);
		assertEquals(CommonTest.FAUX_DATE, postingForm.getPostingDate());

		assertNull(postingForm.getPostingRef());
		postingForm.setPostingRef(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, postingForm.getPostingRef());

		assertNull(postingForm.getPostingName());
		postingForm.setPostingName(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, postingForm.getPostingName());

		assertNull(postingForm.getPostingUrl());
		postingForm.setPostingUrl(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, postingForm.getPostingUrl());

		assertNull(postingForm.getPostingFolder());
		postingForm.setPostingFolder(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, postingForm.getPostingFolder());

		assertNull(postingForm.getCompanyName());
		postingForm.setCompanyName(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, postingForm.getCompanyName());

		assertNull(postingForm.getCompanyAddress());
		postingForm.setCompanyAddress(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, postingForm.getCompanyAddress());

		assertNull(postingForm.getComment());
		postingForm.setComment(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, postingForm.getComment());

		assertEquals(1L, postingForm.getPostingPriority().longValue());
		postingForm.setPostingPriority(CommonTest.FAUX_LONG);
		assertEquals(CommonTest.FAUX_LONG.longValue(), postingForm.getPostingPriority().longValue());

		assertTrue(postingForm.toString().startsWith("PostingForm"));
		assertTrue(postingForm.equals(postingForm));
		assertFalse(postingForm.equals(new PostingForm()));
		assertTrue(postingForm.hashCode() != (new Object()).hashCode());
	}

	@Test
	public void postingFormContstructorAllFields() {
		PostingForm postingForm = new PostingForm(CommonTest.FAUX_POSTING_DATA.get("postingName"),
				CommonTest.FAUX_POSTING_DATA.get("postingRef"), CommonTest.FAUX_POSTING_DATA.get("postingUrl"),
				CommonTest.FAUX_LONG, CommonTest.FAUX_POSTING_DATA.get("postingFolder"),
				CommonTest.FAUX_POSTING_DATA.get("companyName"), CommonTest.FAUX_POSTING_DATA.get("companyAddress"),
				CommonTest.FAUX_POSTING_DATA.get("comment"));

		assertNotNull(postingForm);

		assertNotNull(postingForm.getPostingDate());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("postingRef"), postingForm.getPostingRef());

		assertEquals(CommonTest.FAUX_LONG, postingForm.getPostingPriority());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("postingName"), postingForm.getPostingName());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("postingUrl"), postingForm.getPostingUrl());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("postingFolder"), postingForm.getPostingFolder());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("companyName"), postingForm.getCompanyName());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("companyAddress"), postingForm.getCompanyAddress());

		assertEquals(CommonTest.FAUX_POSTING_DATA.get("comment"), postingForm.getComment());

	}

	@Test
	public void postingModelConstructorDefault() {

		PostingModel posting = new PostingModel();

		assertNotNull(posting);

		assertNull(posting.getId());
		posting.setId(CommonTest.FAUX_UUID);
		assertEquals(CommonTest.FAUX_UUID, posting.getId());

		assertNull(posting.getPostingDate());
		posting.setPostingDate(CommonTest.FAUX_DATE);
		assertEquals(CommonTest.FAUX_DATE, posting.getPostingDate());

		assertNull(posting.getAppDate());
		posting.setAppDate(CommonTest.FAUX_DATE);
		assertEquals(CommonTest.FAUX_DATE, posting.getAppDate());

		assertNull(posting.getAppStatusUrl());
		posting.setAppStatusUrl(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getAppStatusUrl());

		assertNull(posting.getPostingRef());
		posting.setPostingRef(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getPostingRef());

		assertNull(posting.getPostingPriority());
		posting.setPostingPriority(CommonTest.FAUX_LONG);
		assertEquals(CommonTest.FAUX_LONG, posting.getPostingPriority());

		assertNull(posting.getPostingName());
		posting.setPostingName(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getPostingName());

		assertNull(posting.getPostingUrl());
		posting.setPostingUrl(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getPostingUrl());

		assertNull(posting.getPostingFolder());
		posting.setPostingFolder(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getPostingFolder());

		assertNull(posting.getCompanyName());
		posting.setCompanyName(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getCompanyName());

		assertNull(posting.getCompanyAddress());
		posting.setCompanyAddress(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getCompanyAddress());

		assertNull(posting.getComment());
		posting.setComment(CommonTest.FAUX_STRING);
		assertEquals(CommonTest.FAUX_STRING, posting.getComment());

		assertNull(posting.getAppStatus());
		posting.setAppStatus(Posting.APPLIED);
		assertEquals(Posting.APPLIED, posting.getAppStatus());

		assertTrue(posting.toString().startsWith("PostingModel"));
		assertTrue(posting.equals(posting));
		assertFalse(posting.equals(new PostingModel()));
		assertTrue(posting.hashCode() != (new Object()).hashCode());
	}

}
