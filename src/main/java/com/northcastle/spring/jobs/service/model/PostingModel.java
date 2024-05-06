package com.northcastle.spring.jobs.service.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostingModel {
	private Long id;
	private String name;
	private String referenceID;
	private String sort;
	private String url;
	private Date date;
	private String companyName;
	private String companyAddress;
	private Date appDate;
	private String appStatus;
}
