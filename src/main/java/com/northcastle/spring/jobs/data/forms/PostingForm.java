package com.northcastle.spring.jobs.data.forms;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class PostingForm {

	@NotNull(message = "Posting name cannot be empty")
	@Size(min = 2, max = 64)
	@EqualsAndHashCode.Include
	private String postingName;

	@Size(max = 20)
	private String postingRef;

	@NotNull(message = "Posting URL cannot be empty")
	@Size(max = 256)
	private String postingUrl;

	@Min(1)
	@Max(10)
	private Long postingPriority = 1L;

	@Size(max = 64)
	private String postingFolder;

	@NotNull
	@EqualsAndHashCode.Include
	private Date postingDate = java.sql.Date
			.valueOf(LocalDate.ofInstant(new java.util.Date().toInstant(), ZoneId.systemDefault()));

	@NotNull(message = "Company name cannot be empty")
	@Size(min = 2, max = 64)
	@EqualsAndHashCode.Include
	private String companyName;

	@NotNull(message = "Company location cannot be empty")
	@Size(min = 2, max = 64)
	private String companyAddress;

	@Size(min = 0, max = 512)
	private String comment;

	/**
	 * Constructor for auditing and testing. Date is set to today.
	 *
	 * @param postingName
	 * @param postingRef
	 * @param postingUrl
	 * @param postingPriority
	 * @param postingFolder
	 * @param companyName
	 * @param companyAddress
	 * @param comment
	 */
	public PostingForm(@NotNull(message = "Posting name cannot be empty") @Size(min = 2, max = 64) String postingName,
			@Size(max = 20) String postingRef,
			@NotNull(message = "Posting URL cannot be empty") @Size(max = 256) String postingUrl,
			@Min(1) @Max(10) Long postingPriority, @Size(max = 64) String postingFolder,
			@NotNull(message = "Company name cannot be empty") @Size(min = 2, max = 64) String companyName,
			@NotNull(message = "Company location cannot be empty") @Size(min = 2, max = 64) String companyAddress,
			@Size(min = 0, max = 512) String comment) {
		super();
		this.postingName = postingName;
		this.postingRef = postingRef;
		this.postingUrl = postingUrl;
		this.postingPriority = postingPriority;
		this.postingFolder = postingFolder;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.comment = comment;
	}

}
