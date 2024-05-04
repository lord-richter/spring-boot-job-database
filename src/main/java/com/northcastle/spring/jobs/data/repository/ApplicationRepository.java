package com.northcastle.spring.jobs.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.northcastle.spring.jobs.data.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
