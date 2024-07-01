package com.example.firstProject.dto;

import com.example.firstProject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {
    private Long id; // id 받을 필드
    private String title; // 제목받는 필드
    private String content; // 내용을 받을 필드


    /* 롬복의 @AllArgsConstructor 어노테이션으로 아래 생성자는 생략됨.
    public ArticleForm(String title, String content){
        this.title = title;
        this.content = content;
    }
    */

    // 데이터를 잘 받았는지 확인할 toString() 메서드 추가
    /* @ToString 어노테이션으로 아래 toString()은 생략됨.
    @Override
    public String toString(){
        return "ArticleForm{" + "title='" + title + '\''+ ", content='" + content +'\''+'}';
    }
    */

    public Article toEntity() {
        return new Article(id, title, content);
    }
}
