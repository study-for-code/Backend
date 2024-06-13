package goorm.spoco.domain.member.service;

import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.domain.MemberEnum;
import goorm.spoco.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        Optional<Member> siteMember = this.memberRepository.findByEmail(memberEmail);
        if (siteMember.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        Member member = siteMember.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(memberEmail)) {
            authorities.add(new SimpleGrantedAuthority(MemberEnum.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberEnum.ACTIVE.getValue()));
        }
        return new User(member.getEmail(), member.getPassword(), authorities);
    }

}
