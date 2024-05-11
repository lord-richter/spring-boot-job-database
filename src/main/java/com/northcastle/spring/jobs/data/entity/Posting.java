package com.northcastle.spring.jobs.data.entity;
import java.util.UUID;

import com.northcastle.spring.jobs.web.forms.PostingForm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Entity
@Table(name="posting")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Posting {
	
	public static final String PENDING = "Pending";
	public static final String APPLIED = "Applied";
	public static final String INTERVIEW = "Interview";
	public static final String REJECTED = "Rejected";
	public static final String CLOSED = "Closed";
	
	/**
	 * Copy new posting to posting format
	 * @param newPosting
	 */
	public Posting(PostingForm posting) {
		this.id = null;
		this.postingName = posting.getPostingName();
		this.postingRef = posting.getPostingRef();
		this.postingUrl = posting.getPostingUrl();
		this.postingPriority = posting.getPostingPriority();
		this.postingDate = posting.getPostingDate();
		this.companyName = posting.getCompanyName();
		this.companyAddress = posting.getCompanyAddress();
		this.appDate = null;
		this.appStatus = PENDING;
		this.appStatusUrl = null;
	}

	@Id
	@Column(name="posting_id")
	private UUID id;

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