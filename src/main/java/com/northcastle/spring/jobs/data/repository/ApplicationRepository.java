package com.northcastle.spring.jobs.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.northcastle.spring.jobs.data.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
	Optional<Application> findByPostingId(Long postingId);
	Iterable<Application> findAllByPostingId(long postingId);
}
