package com.northcastle.spring.jobs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.northcastle.spring.jobs.data.entity.Posting;
import com.northcastle.spring.jobs.data.repository.PostingRepository;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
public class PostingRepositoryTest {

	@Autowired
	private PostingRepository postingRepository;

	@Test
	void getAllPostingsDefault(){
		int count = 7;  // depends on data.sql
		UUID[] allPostingIds = {
				CommonTest.VALID_POSTING_1,
				CommonTest.VALID_POSTING_2,
				CommonTest.VALID_POSTING_3,
				CommonTest.VALID_POSTING_4,
				CommonTest.VALID_POSTING_5,
				CommonTest.VALID_POSTING_6,
				CommonTest.VALID_POSTING_7
		};
		
		
		// default sort is undefined for this query
		List<Posting> postings = postingRepository.findAll();
		log.info("TEST.getAllPostingsDefault(): Count = "+postingRepository.count()+"  Actual = "+postings.size());
		postings.forEach((v)->{log.debug("TEST.getAllPostingsDefault(): "+v);});
		
		assertEquals(count, postings.size());
		// this is a test of the test data to verify that we have not broken test
		// each UUID should come up, and be in the proper order, with no changes
		// additionally, it verifies that findAll() returns all items from the database properly.
		for (int index=0;index<count;index++) {
			assertEquals(allPostingIds[index].toString(),postings.get(index).getId().toString());
		}
	}
	
	@Test
	void getAllPostingsSorted(){
		int count = 7;  // depends on data.sql
		UUID[] sortedPostingIds = {
				CommonTest.VALID_POSTING_4,
				CommonTest.VALID_POSTING_2,
				CommonTest.VALID_POSTING_7,
				CommonTest.VALID_POSTING_3,
				CommonTest.VALID_POSTING_1,
				CommonTest.VALID_POSTING_6,
				CommonTest.VALID_POSTING_5
		};		
		// specify a sort of postingName and DESC
		// validate that Spring handles properly and that the results are as expected
		List<Posting> postings = postingRepository.findAll(Sort.by(Direction.DESC,"postingName"));
		log.info("TEST.getAllPostingsSorted(postingName,DESC): Count = "+postingRepository.count()+"  Actual = "+postings.size());
		postings.forEach((v)->{log.debug("TEST.getAllPostingsSorted(postingName,DESC): "+v);});
		assertEquals(count, postings.size());
		// verify expected sort order
		for (int index=0;index<count;index++) {
			assertEquals(sortedPostingIds[index].toString(),postings.get(index).getId().toString());
		}
		
		// specify a sort of postingName and ASC
		// validate that Spring handles properly and that the results are as expected
		postings = postingRepository.findAll(Sort.by(Direction.ASC,"postingName"));
		log.info("TEST.getAllPostingsSorted(postingName,ASC): Count = "+postingRepository.count()+"  Actual = "+postings.size());
		postings.forEach((v)->{log.debug("TEST.getAllPostingsSorted(postingName,ASC): "+v);});
		assertEquals(count, postings.size());
		// verify expected sort order
		for (int index=0;index<count;index++) {
			assertEquals(sortedPostingIds[count-index-1].toString(),postings.get(index).getId().toString());
		}
		
	}
	

	@Test
	void getAllApplications(){
		int count = 4;  // depends on data.sql
		UUID[] sortedApplicationIds = {
				CommonTest.VALID_POSTING_7,
				CommonTest.VALID_POSTING_6,
				CommonTest.VALID_POSTING_1,
				CommonTest.VALID_POSTING_4
		};			
		// sorts by appDate, DESC
		List<Posting> postings = postingRepository.findByAppStatusIn(PostingRepository.allActiveStatus,Sort.by(Direction.DESC,"appDate"));
		log.info("TEST.getAllApplicationsDefault(): Count = "+postingRepository.count()+"  Actual = "+postings.size());
		postings.forEach((v)->{log.info("TEST.getAllApplicationsDefault(): "+v);});
		assertEquals(count, postings.size());
		// verify expected sort order
		for (int index=0;index<count;index++) {
			assertEquals(sortedApplicationIds[index].toString(),postings.get(index).getId().toString());
		}		
	}

	@Test
	void getAllByStatus(){
		int count = 2;  // depends on data.sql
		UUID[] sortedApplicationIds = {
				CommonTest.VALID_POSTING_4,
				CommonTest.VALID_POSTING_1
		};			
		List<Posting> postings = postingRepository.findByAppStatus(Posting.APPLIED,Sort.by(Direction.DESC,"postingName"));
		postings.forEach((v)->{log.info("TEST.getAllByStatus(APPLIED): "+v);});
		assertEquals(count, postings.size());
		// verify expected sort order
		for (int index=0;index<count;index++) {
			assertEquals(sortedApplicationIds[index].toString(),postings.get(index).getId().toString());
		}		
	}

}


