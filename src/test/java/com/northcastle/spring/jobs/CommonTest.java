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
import com.northcastle.spring.jobs.util.Convert;
import com.northcastle.spring.jobs.web.exception.BadRequestException;
import com.northcastle.spring.jobs.web.exception.NotFoundException;

public class CommonTest {
	
	/*
	 * Common values for all tests
	 */
	
	public static final String FAUX_STRING = "FAUX";
	public static final UUID FAUX_UUID = UUID.randomUUID();
	public static final Long FAUX_LONG = 685492945L;
	public static final Date FAUX_DATE = Date.valueOf("2024-01-01");
	
	public static final Map<String,String> FAUX_POSTING_DATA = Map.ofEntries(
			new AbstractMap.SimpleEntry<String, String>("postingName","Fish Truck Driver"),
			new AbstractMap.SimpleEntry<String, String>("postingRef","DR001"),
			new AbstractMap.SimpleEntry<String, String>("postingUrl","https://surfcitync.com"),
			new AbstractMap.SimpleEntry<String, String>("postingDate","2024-01-01"),
			new AbstractMap.SimpleEntry<String, String>("postingFolder","Folder 100"),
			new AbstractMap.SimpleEntry<String, String>("companyName","No Lobsters Ever Allowed"),
			new AbstractMap.SimpleEntry<String, String>("companyAddress","Surf City Beach, NC"),
			new AbstractMap.SimpleEntry<String, String>("comment","This is a faux comment"),
			new AbstractMap.SimpleEntry<String, String>("appDate","2024-01-02"),
			new AbstractMap.SimpleEntry<String, String>("appStatus",Posting.APPLIED),
			new AbstractMap.SimpleEntry<String, String>("appStatusUrl","http://faux.com")
			);
	
	public static final Map<String,String> FAUX_POSTINGFORM_DATA = Map.of(
			"postingName","Fresh Fish Truck Driver",
			"postingRef","DR001010101",
			"postingUrl","https://surfcityofficial.com",
			"postingFolder","Folder 10",
			"companyName","No Lobsters Wanted",
			"companyAddress","Topsail, NC",
			"comment","This is a faux form comment"
			);	
	
	public static final String VALID_UUID_STRING_1 = "ecf1bde1-3849-4b38-94e6-f8a43a89f816";
	public static final UUID VALID_UUID_1 = UUID.fromString(VALID_UUID_STRING_1);
	public static final String VALID_UUID_STRING_2 = "a3450c68-fd19-4a7d-b4f3-72119a4bf47d";
	public static final UUID VALID_UUID_2 = UUID.fromString(VALID_UUID_STRING_2);
	
	// these are not used by other tests
	public static final UUID VALID_PENDING_1 = UUID.fromString("613de372-c0f0-4d12-bb94-27d3320c59ec");
	public static final UUID VALID_APPLIED_1 = UUID.fromString("d2a3f02d-7929-41f0-b8d6-6a68af9d99e5");
	
	public static final String UNKNOWN_UUID_STRING =  "ecf1bde1-3849-4b38-94e6-f8a43a89f700";
	public static final UUID UNKNOWN_UUID =  UUID.fromString(UNKNOWN_UUID_STRING);
	public static final String INVALID_UUID_STRING = "This is an invalid UUID";	
	
	
	@Test
	public void convertConstructor() {
		assertNotNull(new Convert());
	}

	
	@Test
	public void convertStringUUID() {
		assertEquals(VALID_UUID_STRING_1,Convert.stringToUUID(VALID_UUID_STRING_1).toString());
		
	}

	@Test
	public void convertStringUUID_NullId() {
		assertThrows(BadRequestException.class, () -> Convert.stringToUUID(null));
	}
	
	@Test
	public void convertStringUUID_BlankId() {
		assertThrows(BadRequestException.class, () -> Convert.stringToUUID(""));
	}

	@Test
	public void convertStringUUID_BadId() {
		assertThrows(BadRequestException.class, () -> Convert.stringToUUID("Bad Wolf"));
	}
	
	
	@Test
	public void exceptionBadRequest() {
		assertThrows(BadRequestException.class, () -> {throw new BadRequestException();});
		assertThrows(BadRequestException.class, () -> {throw new BadRequestException("message");});
		assertThrows(BadRequestException.class, () -> {throw new BadRequestException("message",new Exception());});
		assertThrows(BadRequestException.class, () -> {throw new BadRequestException(new Exception());});
	}
	
	@Test
	public void exceptionNotFound() {
		assertThrows(NotFoundException.class, () -> {throw new NotFoundException();});
		assertThrows(NotFoundException.class, () -> {throw new NotFoundException("message");});
		assertThrows(NotFoundException.class, () -> {throw new NotFoundException("message",new Exception());});
		assertThrows(NotFoundException.class, () -> {throw new NotFoundException(new Exception());});
	}
}
