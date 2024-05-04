package com.northcastle.spring.jobs.data.entity;
import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="posting_name")
	private String name;

	@Column(name="posting_ref")
	private String referenceID;

	@Column(name="posting_rank")
	private String sort;

	@Column(name="posting_date")
	private Date date;

	@Column(name="company_name")
	private String companyName;

	@Column(name="company_address")
	private String companyAddress;
	
	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn(name="app_id")
	private Application appId;

}