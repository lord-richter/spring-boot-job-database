package com.northcastle.spring.jobs.service.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApplicationModel {
	private Long id;
	private Date date;
	private String status;
	private Long postingId;
}
