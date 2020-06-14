package jpabook2.jpashop.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입력값입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
