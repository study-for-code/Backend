package goorm.spoco.domain.member.controller;

import goorm.spoco.domain.member.controller.response.MemberCreateDTO;
import goorm.spoco.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;


    // 회원가입 페이지 출력 요청
    @GetMapping("/signup")
    public String signup(MemberCreateDTO memberCreateDTO) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberCreateDTO memberCreateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!memberCreateDTO.getPasswordA().equals(memberCreateDTO.getPasswordB())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        // 중복 회원 가입 방지하기
        try {
            memberService.create(memberCreateDTO.getUsername(),
                    memberCreateDTO.getEmail(), memberCreateDTO.getPasswordA());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
}
