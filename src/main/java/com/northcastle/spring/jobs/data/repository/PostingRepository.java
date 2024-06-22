package com.northcastle.spring.jobs.data.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.northcastle.spring.jobs.data.entity.Posting;

@Repository
public interface PostingRepository extends JpaRepository<Posting, UUID> {

	public final List<String> allActiveStatus = new ArrayList<>(
			Arrays.asList(Posting.APPLIED, Posting.INTERVIEW, Posting.OFFER, Posting.ACCEPTED));

	List<Posting> findByAppStatus(String appStatus, Sort sort);

	List<Posting> findByAppStatusIn(List<String> appStatus, Sort sort);

}