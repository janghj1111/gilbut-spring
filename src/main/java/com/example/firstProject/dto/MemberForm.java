package com.example.firstProject.dto;

import com.example.firstProject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MemberForm {

    public Long id;
    public String email;
    public String password;

    /*public MemberForm(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "MemberForm{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }*/

    public Member toEntity(){
        return new Member(id, email, password);
    }
}
