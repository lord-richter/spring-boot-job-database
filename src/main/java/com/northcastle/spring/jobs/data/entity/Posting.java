package com.northcastle.spring.jobs.data.entity;
import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;



@Entity
@Table(name="posting")
@Data
@ToString
public class Posting {

	@Id
	@Column(name="posting_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posting_generator")
	@SequenceGenerator(name = "posting_generator", sequenceName = "posting_seq", allocationSize = 50, initialValue = 10)
	private Long id;

	@Column(name="posting_name")
	private String postingName;

	@Column(name="posting_ref",nullable = true)
	private String postingRef;

	@Column(name="posting_url")
	private String postingUrl;
	
	@Column(name="posting_priority")
	private Long postingPriority;

	@Column(name="posting_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date postingDate;

	@Column(name="company_name")
	private String companyName;

	@Column(name="company_address")
	private String companyAddress;
	
	@Column(name="app_date",nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date appDate;

	@Column(name="app_status")
	private String appStatus;

}