package com.northcastle.spring.jobs.web.forms;

import java.sql.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostingForm {

	@NotNull(message = "Posting name cannot be empty")
	@Size(min=2,max=64)
	private String postingName;

	@Size(max=20)
	private String postingRef;

	@NotNull(message = "Posting URL cannot be empty")
	@Size(max=128)
	private String postingUrl;
	
	@Min(1)
	@Max(10)
	private Long postingPriority=1L;

	@PastOrPresent
	private Date postingDate = new Date(System.currentTimeMillis());

	@NotNull(message = "Company name cannot be empty")
	@Size(min=2,max=64)	
	private String companyName;

	@NotNull(message = "Company location cannot be empty")
	@Size(min=2,max=64)	
	private String companyAddress;

}
