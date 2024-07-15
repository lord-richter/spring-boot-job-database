package com.northcastle.spring.jobs.data.entity;

import java.sql.Date;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.northcastle.spring.jobs.data.forms.PostingForm;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posting")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Posting {

	// master location of status values
	public static final String PENDING = "Pending";
	public static final String APPLIED = "Applied";
	public static final String CONSIDER = "Consider";
	public static final String INTERVIEW = "Interview";
	public static final String OFFER = "Offer";
	public static final String REJECTED = "Rejected";
	public static final String ACCEPTED = "Accepted";
	public static final String CLOSED = "Closed";

	@Id
	@Column(name = "posting_id")
	@EqualsAndHashCode.Include
	private UUID id;

	@Column(name = "posting_name")
	@NotNull(message = "Posting name cannot be empty")
	@Size(min = 2, max = 64)
	private String postingName;

	@Column(name = "posting_ref", nullable = true)
	@Size(max = 20)
	private String postingRef;

	@Column(name = "posting_url")
	@NotNull(message = "Posting URL cannot be empty")
	@Size(max = 256)
	private String postingUrl;

	@Column(name = "posting_priority")
	@Min(1)
	@Max(10)
	private Long postingPriority;

	@Column(name = "posting_date")
	private Date postingDate;

	@Column(name = "posting_folder")
	@Size(max = 64)
	private String postingFolder;

	@Column(name = "company_name")
	@NotNull(message = "Company name cannot be empty")
	@Size(min = 2, max = 64)
	private String companyName;

	@Column(name = "company_address")
	@NotNull(message = "Company location cannot be empty")
	@Size(min = 2, max = 64)
	private String companyAddress;

	@Column(name = "comment")
	@Size(max = 512)
	private String comment;

	@Column(name = "app_date", nullable = true)
	private Date appDate;

	@Column(name = "app_status")
	@NotNull(message = "Status cannot be empty")
	private String appStatus = PENDING;

	@Column(name = "app_status_url")
	@Size(max = 256)
	private String appStatusUrl;

	@Column(name = "recruiter_name")
	@Size(max = 64)
	private String recruiterName;

	@Column(name = "recruiter_email")
	@Size(max = 128)
	private String recruiterEmail;

	@Column(name = "updated", nullable = true)
	private Date updated;

	/**
	 * Copy new posting form to posting format
	 *
	 * @param newPosting
	 */
	public Posting(PostingForm posting) {
		id = null;
		BeanUtils.copyProperties(posting, this);
		appDate = null;
		appStatus = PENDING;
		appStatusUrl = null;
		recruiterEmail = null;
		recruiterName = null;
	}
}