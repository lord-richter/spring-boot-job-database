package com.northcastle.spring.jobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.exception.BadRequestException;
import com.northcastle.spring.jobs.exception.NotFoundException;
import com.northcastle.spring.jobs.util.Convert;

public class CommonTest {

	/*
	 * Common values for all tests
	 */

	public static final String FAUX_STRING = "FAUX";
	public static final UUID FAUX_UUID = UUID.randomUUID();
	public static final Long FAUX_LONG = 685492945L;
	public static final Date FAUX_DATE = Date.valueOf("2024-01-01");
	public static final String FAUX_EMAIL = "george@email.com";
	public static final String FAUX_NAME = "George Jetson";

	public static final Map<String, String> FAUX_POSTING_DATA = Map.ofEntries(
			new AbstractMap.SimpleEntry<>("postingName", "Fish Truck Driver"),
			new AbstractMap.SimpleEntry<>("postingRef", "DR001"),
			new AbstractMap.SimpleEntry<>("postingUrl", "https://surfcitync.com"),
			new AbstractMap.SimpleEntry<>("postingDate", "2024-01-01"),
			new AbstractMap.SimpleEntry<>("postingFolder", "Folder 100"),
			new AbstractMap.SimpleEntry<>("companyName", "No Lobsters Ever Allowed"),
			new AbstractMap.SimpleEntry<>("companyAddress", "Surf City Beach, NC"),
			new AbstractMap.SimpleEntry<>("comment", "This is a faux comment"),
			new AbstractMap.SimpleEntry<>("appDate", "2024-01-02"),
			new AbstractMap.SimpleEntry<>("appStatus", Posting.APPLIED),
			new AbstractMap.SimpleEntry<>("appStatusUrl", "http://faux.com"),
			new AbstractMap.SimpleEntry<>("recruiterName", "Bob Bones"),
			new AbstractMap.SimpleEntry<>("recruiterEmail", "bones@noreply.com"));

	public static final Map<String, String> FAUX_POSTINGFORM_DATA = Map.of("postingName", "Fresh Fish Truck Driver",
			"postingRef", "DR001010101", "postingUrl", "https://surfcityofficial.com", "postingFolder", "Folder 10",
			"companyName", "No Lobsters Wanted", "companyAddress", "Topsail, NC", "comment",
			"This is a faux form comment");

	// list of all valid IDs
	private static final String VALID_POSTING_1_STRING = "ecf1bde1-3849-4b38-94e6-f8a43a89f816";
	public static final UUID VALID_POSTING_1 = UUID.fromString("ecf1bde1-3849-4b38-94e6-f8a43a89f816");
	public static final UUID VALID_POSTING_2 = UUID.fromString("79aeeb2f-721e-4c0a-8a24-063e328a097d");
	public static final UUID VALID_POSTING_3 = UUID.fromString("a3450c68-fd19-4a7d-b4f3-72119a4bf47d");
	public static final UUID VALID_POSTING_4 = UUID.fromString("d2a3f02d-7929-41f0-b8d6-6a68af9d99e5");
	public static final UUID VALID_POSTING_5 = UUID.fromString("613de372-c0f0-4d12-bb94-27d3320c59ec");
	public static final UUID VALID_POSTING_6 = UUID.fromString("e6914c5f-ba77-4b5d-a89a-2f01d9d4d5a7");
	public static final UUID VALID_POSTING_7 = UUID.fromString("3f6c7e9a-f214-47d3-b6c8-8f43b7205d04");

	// these are not used by other tests
	public static final UUID VALID_PENDING_1 = UUID.fromString("613de372-c0f0-4d12-bb94-27d3320c59ec");
	public static final UUID VALID_APPLIED_1 = UUID.fromString("d2a3f02d-7929-41f0-b8d6-6a68af9d99e5");
	public static final UUID VALID_REJECTED_1 = UUID.fromString("79aeeb2f-721e-4c0a-8a24-063e328a097d");
	public static final UUID VALID_INTERVIEW_1 = UUID.fromString("e6914c5f-ba77-4b5d-a89a-2f01d9d4d5a7");
	public static final UUID VALID_OFFER_1 = UUID.fromString("3f6c7e9a-f214-47d3-b6c8-8f43b7205d04");

	public static final UUID UNKNOWN_POSTING_UUID = UUID.fromString("ecf1bde1-3849-4b38-94e6-f8a43a89f700");

	@Test
	public void convertConstructor() {
		assertNotNull(new Convert());
	}

	@Test
	public void convertStringUUID() {
		assertEquals(VALID_POSTING_1_STRING, Convert.stringToUUID(VALID_POSTING_1_STRING).toString());

	}

	@Test
	public void convertStringUUID_BadId() {
		assertThrows(BadRequestException.class, () -> Convert.stringToUUID("Bad Wolf"));
	}

	@Test
	public void convertStringUUID_BlankId() {
		assertThrows(BadRequestException.class, () -> Convert.stringToUUID(""));
	}

	@Test
	public void convertStringUUID_NullId() {
		assertThrows(BadRequestException.class, () -> Convert.stringToUUID(null));
	}

	@Test
	public void exceptionBadRequest() {
		assertThrows(BadRequestException.class, () -> {
			throw new BadRequestException();
		});
		assertThrows(BadRequestException.class, () -> {
			throw new BadRequestException("message");
		});
		assertThrows(BadRequestException.class, () -> {
			throw new BadRequestException("message", new Exception());
		});
		assertThrows(BadRequestException.class, () -> {
			throw new BadRequestException(new Exception());
		});
	}

	@Test
	public void exceptionNotFound() {
		assertThrows(NotFoundException.class, () -> {
			throw new NotFoundException();
		});
		assertThrows(NotFoundException.class, () -> {
			throw new NotFoundException("message");
		});
		assertThrows(NotFoundException.class, () -> {
			throw new NotFoundException("message", new Exception());
		});
		assertThrows(NotFoundException.class, () -> {
			throw new NotFoundException(new Exception());
		});
	}
}
