package com.example.firstProject.controller;

import com.example.firstProject.dto.MemberForm;
import com.example.firstProject.entity.Member;
import com.example.firstProject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j
@Controller
public class MemberController {

    // repository 객체
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members/new")
    public String newMemberForm(){
        return "/members/new";
    }

    @GetMapping("/signup")
    public String memberFormOpen(){
        return "/members/new";
    }

    @PostMapping("/join")
    public String join(MemberForm memberDto){
        log.info(memberDto.toString());
        Member member = memberDto.toEntity();
        // DTO를 Member라는 엔티티로 변환.
        // DTO의 이메일,비번을 new Member(null, email, password)로 리턴함
        log.info(member.toString());
        Member saved = memberRepository.save(member);
        log.info(saved.toString());
        // 엔티티를 DB에 저장. (레파지토리 save메소드)
        return "redirect:/members/" + saved.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model){
        ArrayList<Member> memberEntityList = memberRepository.findAll();
        model.addAttribute("memberList", memberEntityList);
        return "members/index";
    }

    /*
    *  회원 상세에서 수정하기 기능
    * */
    @GetMapping("/members/{id}/edit")
    public String editOpen(@PathVariable Long id, Model model){
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/edit";
    }

    /*
     *  회원 상세에서 수정하기 기능
     * */
    @PostMapping("/members/update")
    public String updateMmber(MemberForm form){
        log.info(form.toString());
        Member memberEntity = form.toEntity();
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        if( target != null ){
            memberRepository.save(memberEntity);
        }
        return "redirect:/members/" + memberEntity.getId();
    }

    /*
     *  회원 상세에서 삭제하기 기능
     * */
    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr, Model model){
        log.info("삭제요청이 들어왔습니다!");
        Member target = memberRepository.findById(id).orElse(null);
        log.info(target.toString());
        if( target != null ){
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제됐습니다.");
        }
        return "redirect:/members";
    }

}
