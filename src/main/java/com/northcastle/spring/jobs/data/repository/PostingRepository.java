package com.northcastle.spring.jobs.data.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.northcastle.spring.jobs.data.entity.Posting;

@Repository
public interface PostingRepository extends JpaRepository<Posting, UUID> {
	
	@Query(value="SELECT * FROM job.posting p WHERE p.app_status IS NOT 'Pending'",nativeQuery = true)
	List<Posting> findAllApplied();
	
}