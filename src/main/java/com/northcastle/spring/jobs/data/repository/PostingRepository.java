package com.northcastle.spring.jobs.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.northcastle.spring.jobs.data.entity.Posting;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
	
	@Query(value="SELECT * FROM posting p WHERE p.app_date IS NOT NULL",nativeQuery = true)
	List<Posting> findAllApplied();
	

}