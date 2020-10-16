package com.hist.BysApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hist.BysApp.entities.member.Article;
import com.hist.BysApp.entities.member.ArticleContrat;

@CrossOrigin("*")
@RepositoryRestResource
public interface ArticleDao  extends JpaRepository<Article, Long>   {

}
