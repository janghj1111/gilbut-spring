package com.example.firstProject.api;

import com.example.firstProject.dto.ArticleForm;
import com.example.firstProject.entity.Article;
import com.example.firstProject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired
    private ArticleRepository articleRepository;

    //get
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    //post
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto) {
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    //patch
    @PatchMapping("api/articles/{id}")
    //public Article이 아닌 이유는 HttpStatus.BAD_REQUEST같은 상태코드를 반환하기 위해서
    public ResponseEntity update(@PathVariable Long id, @RequestBody ArticleForm dto){
        // 수정용 엔티티 생성해야하니까 dto를 엔티티로 변환
        Article article = dto.toEntity();
        log.info("id:{}, article: {}", id, article.toString() );

        // DB에 대상 엔티티가 있는지 조회하기
        Article target = articleRepository.findById(id).orElse(null);

        // 대상 엔티티가 없거나 수정하려는 id가 잘못됐을 경우 처리하기
        if(target == null || id != article.getId()){
            // 400, 잘못된 요청!!
            log.info("잘못된 요청!! id:{}, article: {}", id, article.toString() );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 대상 엔티티가 있으면 수정 내용으로 업데이트하고 정상 응답(200) 보내기
        target.patch(article); // 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target); // 엔티티를 DB에 저장
        return ResponseEntity.status(HttpStatus.OK).body(updated);

    }

    //delete
    //patch
    @DeleteMapping("api/articles/{id}")
    // 반환형이 ResponseEntity에 <Article>을 실어 보내는 delete 메소드
    public ResponseEntity<Article> delete(@PathVariable Long id){
        // DB에 대상 엔티티가 있는지 조회하기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리하기
        if(target == null){
            // 400, 잘못된 요청!!
            // log.info("잘못된 요청!! id:{}, article: {}", id, article.toString() );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 대상 삭제하고 200보내기
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).body(null); // .body(null) 대신 build() 가능

    }
}
