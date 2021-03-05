package com.hist.BysApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hist.BysApp.forum.Answer;
import com.hist.BysApp.forum.Question;

@CrossOrigin("*")
@RepositoryRestResource
public interface AnswerDao extends JpaRepository<Answer, Long> {
	
}
