package goorm.spoco.domain.study.service;

import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.join.repository.JoinRepository;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.domain.Role;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.domain.study.controller.request.StudyModifyDto;
import goorm.spoco.domain.study.controller.request.StudyRequestDto;
import goorm.spoco.domain.study.controller.response.StudyResponseDto;
import goorm.spoco.domain.study.domain.Study;
import goorm.spoco.domain.study.repository.StudyRepository;
import goorm.spoco.global.common.auth.SpocoUserDetails;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import goorm.spoco.global.util.RandomCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final JoinRepository joinRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public StudyResponseDto createStudy(String title, Long memberId) {
        Member owner = existsByMemberId(memberId);

        String joinCode = RandomCodeGenerator.generateCode();
        while (studyRepository.findByJoinCode(joinCode).isPresent()) {
            joinCode = RandomCodeGenerator.generateCode();
        }

        // 스터디 생성
        Study study = studyRepository.save(Study.create(title, owner, joinCode));

        // 스터디 접속
        joinRepository.save(Join.join(owner, study));

        return StudyResponseDto.from(study);
    }

    @Transactional
    public StudyResponseDto modifyStudy(StudyModifyDto studyModifyDto, Long memberId) {
        Member member = existsByMemberId(memberId);
        Study study = existByStudyId(studyModifyDto.studyId());

        if (member.getMemberId().equals(study.getOwner().getMemberId())
                || member.getRole().equals(Role.ADMIN)) {
            study.updateInfo(studyModifyDto);
        }

        return StudyResponseDto.from(study);
    }

    @Transactional
    public StudyResponseDto deleteStudy(Long studyId, Long memberId) {
        Member member = existsByMemberId(memberId);
        Study study = existByStudyId(studyId);

        if (member.getMemberId().equals(study.getOwner().getMemberId())
                || member.getRole().equals(Role.ADMIN)) {
            study.delete();
        }

        return StudyResponseDto.from(study);
    }

    @Transactional
    public void joinStudy(String joinCode, Long memberId) {
        Member member = existsByMemberId(memberId);
        Study study = studyRepository.findByJoinCode(joinCode)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 스터디는 존재하지 않습니다."));

        joinRepository.save(Join.join(member, study));
    }

    @Transactional
    public void leaveStudy(Long studyId, Long memberId) {
        Member member = existsByMemberId(memberId);

        Join join = joinRepository.findByMember_MemberIdAndStudy_StudyId(member.getMemberId(), studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 스터디에 존재하지 않습니다."));

        join.delete();
    }

    public StudyResponseDto getStudyById(Long studyId) {
        Study study = existByStudyId(studyId);

        return StudyResponseDto.from(study);
    }

    public List<StudyResponseDto> getJoinStudyList(Long memberId) {
        Member member = existsByMemberId(memberId);

        return studyRepository.findAllByJoinStudy(member.getMemberId())
                        .stream().map(StudyResponseDto::from).collect(Collectors.toList());
    }

    private Member existsByMemberId(Long memberId) {
        return memberRepository.findByMemberIdAndStatus(memberId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 회원입니다."));
    }
    private Study existByStudyId(Long studyId) {
        return studyRepository.findByStudyIdAndStatus(studyId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 스터디가 존재하지 않습니다."));
    }
}
