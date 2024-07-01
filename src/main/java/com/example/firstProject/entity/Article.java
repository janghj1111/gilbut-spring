package com.example.firstProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor // 롬복의 기본생성자 어노테이션
@ToString
@Entity // DB가 해당 객체를 인식하도록 붙임. (해당 클래스명으로 테이블을 만들라는 뜻.)
@Getter
public class Article {

    @Id // 엔티티 대표값
    //@GeneratedValue // 자동 생성(숫자가 자동으로 채워짐)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB id 자동생성 전략
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

    // 수정할 내용이 있는 경우에만 동작. 없을 경우는 ..?
    public void patch(Article article) {
        if(article.title != null){
            this.title = article.title;
        }else if(article.content != null){
            this.content = article.content;
        }
    }


    // Article 테이블의 title content 컬럼이라고 생각하면 됨.

    /* 롬복어노테이션으로 대체
    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
     */

}
