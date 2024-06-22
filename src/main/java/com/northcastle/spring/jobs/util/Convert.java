package com.northcastle.spring.jobs.util;

import java.util.UUID;

import com.northcastle.spring.jobs.exception.BadRequestException;

import io.micrometer.common.util.StringUtils;

public class Convert {
	public static UUID stringToUUID(String id) {
		if (StringUtils.isBlank(id)) {
			throw new BadRequestException("id cannot be null or empty");
		}
		try {
			return UUID.fromString(id);
		} catch (IllegalArgumentException iae) {
			throw new BadRequestException("cannot convert string to uuid");
		}
	}

}
