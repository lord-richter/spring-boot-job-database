package com.northcastle.spring.jobs.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostingModel {
	private Long id;
	private String postingName;
	private String postingRef;
	private String postingPriority;
	private String postingUrl;
	private String postingDate;
	private String companyName;
	private String companyAddress;
	private String appDate;
	private String appStatus;
	private String appStatusUrl;
}
