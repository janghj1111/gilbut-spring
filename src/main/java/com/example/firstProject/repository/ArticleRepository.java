package com.example.firstProject.repository;

import com.example.firstProject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    //alt+ insert로 오버라이드 메소드 => findAll()추가
    @Override // 오버라이드 : 상위메소드(CrudRepository.findAll())를 재정의한다. ArrayList 타입으로 반환되게.
    ArrayList<Article> findAll(); // Iterable => ArrayList로 수정
}
