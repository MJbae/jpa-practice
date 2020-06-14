package jpabook2.jpashop.controller;

import jpabook2.jpashop.domain.Address;
import jpabook2.jpashop.domain.Member;
import jpabook2.jpashop.service.MemberService;
import jpabook2.jpashop.web.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        //controller에서 뷰로 넘어갈 때, 아래 데이터를 실어서 넘긴다
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    //@Valid에 따라 MemberForm의 name을 empty할 수 없는 기능이 활성화 됨
    //@Valid 뒤에 BindingRewult가 붙으면 result에 오류 담겨서 아래 코드가 실행됨.(BindingResult가 없으면 튕긴다. 아래 코드 실행없이
    public String create(@Valid MemberForm form, BindingResult result){

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
