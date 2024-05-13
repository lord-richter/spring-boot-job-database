package com.northcastle.spring.jobs.web.forms;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	@Size(min=2,max=64)
	@EqualsAndHashCode.Include
	private String postingName;

	@Size(max=20)
	private String postingRef;

	@NotNull(message = "Posting URL cannot be empty")
	@Size(max=128)
	private String postingUrl;
	
	@Min(1)
	@Max(10)
	private Long postingPriority=1L;

	@NotNull
	@EqualsAndHashCode.Include
	private String postingDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));

	@NotNull(message = "Company name cannot be empty")
	@Size(min=2,max=64)	
	@EqualsAndHashCode.Include
	private String companyName;

	@NotNull(message = "Company location cannot be empty")
	@Size(min=2,max=64)	
	private String companyAddress;

	/**
	 * Constructor for auditing and testing.
	 * Date is set to today.
	 * @param postingName
	 * @param postingRef
	 * @param postingUrl
	 * @param postingPriority
	 * @param companyName
	 * @param companyAddress
	 */
	public PostingForm(@NotNull(message = "Posting name cannot be empty") @Size(min = 2, max = 64) String postingName,
			@Size(max = 20) String postingRef,
			@NotNull(message = "Posting URL cannot be empty") @Size(max = 128) String postingUrl,
			@Min(1) @Max(10) Long postingPriority,
			@NotNull(message = "Company name cannot be empty") @Size(min = 2, max = 64) String companyName,
			@NotNull(message = "Company location cannot be empty") @Size(min = 2, max = 64) String companyAddress) {
		super();
		this.postingName = postingName;
		this.postingRef = postingRef;
		this.postingUrl = postingUrl;
		this.postingPriority = postingPriority;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
	}

	
	
}
