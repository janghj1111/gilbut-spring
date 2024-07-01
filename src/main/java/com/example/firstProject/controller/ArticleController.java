package com.example.firstProject.controller;

import com.example.firstProject.dto.ArticleForm;
import com.example.firstProject.entity.Article;
import com.example.firstProject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j // 로깅을 위한 어노테이션 추가 (sout 사용안해도 됨.)
@Controller
public class ArticleController {

    // repository 객체 생성
    @Autowired // 스프링 부트가 미리 생성한 리퍼지토리 객체 주입 (의존성 주입 DI)
    private ArticleRepository articleRepository;


    @GetMapping("/articles/new")
    public String newArticleForm(Model model){ //model 객체 받아오기(p.70)
        model.addAttribute("username", "짱호쩡");
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String crateArticle(ArticleForm formDto){
        log.info(formDto.toString()); //System.out.println(formDto.toString());
        // 1. DTO를 엔티티로 변환
        Article article = formDto.toEntity();
        log.info("잘 변환됐는지 확인");//System.out.println("잘 변환됐는지 확인");
        log.info(article.toString());//System.out.println(article.toString());
        // 2. 리포지토리로 엔티티를 DB에 저장.
        Article saved = articleRepository.save(article); // article 엔티티를 저장해 saved 객체에 반환
        log.info("article이 DB에 저장 잘 됐는지 확인");//System.out.println("article이 DB에 저장 잘 됐는지 확인");
        log.info(saved.toString());//System.out.println(saved.toString());   //

        return "redirect:/articles/" + saved.getId(); // 리다이렉트(재요청 지시)를 작성할 위치.
    }

    /* p.156 */
    @GetMapping("/articles/{id}") // 데이터 조회 요청 접수
    public String show(@PathVariable Long id, Model model){ // url의 {id}를 매개변수로 받아오기
        log.info("id = " + id);
        /* 1. id 조회해서 데이터 가져오기 */
        // DB데이터를 가져오는 주체는 리파지토리. (3장) @Autowired로 주입받음.
        Article articleEntity = articleRepository.findById(id).orElse(null); // articleEntity변수에 DB값이 저장됨.
        // optional<Article> articleEntity = articleRepository.findById(id);

        /* 2. 데이터를 모델에 붙이기 */
        model.addAttribute("article", articleEntity);

        /* 3. 뷰 페이지에 반환 */
        return "articles/show";
    }

    /* 5-3. 데이터 목록 조회하기 p.171 */
    @GetMapping("/articles")
    public String index( Model model){ // url의 {id}를 매개변수로 받아오기
        //log.info("id = " + id);
        /* 1. DB에서 모든 Article 데이터 가져오기  */

        //List<Article> articleEntityList = articleRepository.findAll();
        // findAll은 Iterable로 반환되어야 하는데 지금 List로 반환된다고 타입이 작성되있음. 위 주석 해제하면 아래처럼 경고가 뜸.
        // Required type: List <Article>    Provided: Iterable <Article>

        // 첫번쨰. (List<Article>)을 추가해서 아래처럼 형변환을 할 것임.
        //List<Article> articleEntityList = (List<Article>) articleRepository.findAll();
        // 두번쨰. Iterable<Article>로 타입을 새로 작성할 것임.
        //List<Article> articleEntityList = (List<Article>) articleRepository.findAll();
        // 세번째. ArrayList로 받는다. 레파지토리에서 상위클래스의 메소드인 findAll를 재정의한다. ArticleRepository 참고.
        ArrayList<Article> articleEntityList = articleRepository.findAll();


        /* 2. 데이터를 모델에 붙이기 */
        model.addAttribute("articleList", articleEntityList);


        /* 3. 뷰 페이지에 반환 */
        return "articles/index";
    }

    /* 7-2. 수정할 데이터 가져오기 p.203 */
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){ // url의 {id}를 매개변수로 받아오기

        Article articleEntity = articleRepository.findById(id).orElse(null); // DB에서 수정할 데이터 가져오기


        /* 2. 데이터를 모델에 붙이기 */
        model.addAttribute("article", articleEntity);


        /* 3. 뷰 페이지에 반환 */
        return "articles/edit";
    }

    /* 7-3-4. 수정힌 데이터 받아오기 p.214 */
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        /* 1. DTO를 엔티티로 변환 */
        Article articleEntity = form.toEntity();
        log.info("update() 잘 변환됐는지 확인");
        log.info(articleEntity.toString());

        /* 2. 리포지토리로 엔티티를 DB에 저장. */
        // 2-1 DB에서 데이터 찾기. target 객체에 반환
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2 DB에 기존 데이터값 갱신하기
        if(target != null){
            articleRepository.save(articleEntity);
        }

        /* 3. 수정 결과 페이지로 리다이렉트하기 */
        return "redirect:/articles/" + articleEntity.getId(); // 리다이렉트(재요청 지시)를 작성할 위치.
    }

    @GetMapping("/articles/{id}/delect")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("delete 메소드 실행");
        /* 1. 삭제대상 가져오기 */
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        /* 2. 대상 엔티티 삭제 */
        if( target != null ){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제됐습니다");
        }

        /* 3. 리다이렉트 */
         return "redirect:/articles";
    };


}
