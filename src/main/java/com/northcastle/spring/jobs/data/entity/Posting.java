package com.northcastle.spring.jobs.data.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	@NotNull(message = "Posting name cannot be empty")
	@Size(min=2,max=64)	
	private String postingName;

	@Column(name="posting_ref",nullable = true)
	@Size(max=20)
	private String postingRef;

	@Column(name="posting_url")
	@NotNull(message = "Posting URL cannot be empty")
	@Size(max=128)	
	private String postingUrl;
	
	@Column(name="posting_priority")
	@Min(1)
	@Max(10)	
	private Long postingPriority;

	@Column(name="posting_date")
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String postingDate;

	@Column(name="company_name")
	@NotNull(message = "Company name cannot be empty")
	@Size(min=2,max=64)	
	private String companyName;

	@Column(name="company_address")
	@NotNull(message = "Company location cannot be empty")
	@Size(min=2,max=64)		
	private String companyAddress;
	
	@Column(name="app_date",nullable = true)
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String appDate;

	@Column(name="app_status")
	@NotNull(message = "Status cannot be empty")
	private String appStatus;
	
	@Column(name="app_status_url")
	@Size(max=128)	
	private String appStatusUrl;

}