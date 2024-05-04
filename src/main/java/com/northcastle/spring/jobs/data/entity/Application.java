package com.northcastle.spring.jobs.data.entity;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;


@Entity
@Table(name="application")
@Data
@ToString
public class Application {
 
	public static final String PENDING = "Pending";
	public static final String APPLIED = "Applied";
	public static final String INTERVIEW = "Interview";
	public static final String REJECTED = "Rejected";
	public static final String CLOSED = "Closed";
	
	public static List<String> STATUSLIST = List.of(
				PENDING,APPLIED,INTERVIEW,REJECTED,CLOSED
			);
			
	@Id
	@Column(name="app_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="app_date")
	private Date date;

	@Column(name="app_status")
	private String status;

	@Column(name="posting_id")
	private Long postingId;

}
