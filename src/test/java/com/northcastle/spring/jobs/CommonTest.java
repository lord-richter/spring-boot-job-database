package com.northcastle.spring.jobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.northcastle.spring.jobs.util.Convert;
import com.northcastle.spring.jobs.web.exception.BadRequestException;
import com.northcastle.spring.jobs.web.exception.NotFoundException;

public class CommonTest {
	
	public static final String testString = "ecf1bde1-3849-4b38-94e6-f8a43a89f816";
	public static final UUID testUUID = UUID.fromString(testString);
	
	
	@Test
	public void convertConstructor() {
		assertNotNull(new Convert());
	}

	
	@Test
	public void convertStringUUID() {
		assertEquals(testString,Convert.stringToUUID(testString).toString());
		
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
