package com.hist.BysApp.dao_2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hist.BysApp.forum.Question;

@CrossOrigin("*")
@RepositoryRestResource
public interface QuestionDao extends JpaRepository<Question, Long> {
	


}
