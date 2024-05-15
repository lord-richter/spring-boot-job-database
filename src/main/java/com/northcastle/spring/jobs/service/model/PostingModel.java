package com.northcastle.spring.jobs.service.model;

import java.sql.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostingModel {
	private UUID id;
	private String postingName;
	private String postingRef;
	private Long   postingPriority;
	private String postingUrl;
	private Date   postingDate;
	private String postingFolder;
	private String companyName;
	private String companyAddress;
	private String comment;
	private Date   appDate;
	private String appStatus;
	private String appStatusUrl;
	private Date   updated;
}
