package goorm.spoco.domain.study.controller;

import goorm.spoco.domain.study.controller.request.CreateStudyReq;
import goorm.spoco.domain.study.controller.response.CreateStudyRes;
import goorm.spoco.domain.study.domain.Study;
import goorm.spoco.domain.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
public class StudyController {

    private final StudyService studyService;

    @PostMapping
    public ResponseEntity<CreateStudyRes> createStudy(@RequestBody CreateStudyReq createStudyReq){
        Study createStudy = studyService.createStudy(createStudyReq.getTitle());
        CreateStudyRes response = CreateStudyRes.fromEntity(createStudy);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateStudyRes> getStudy(@PathVariable Long id){
        Study study = studyService.getStudyById(id);
        CreateStudyRes response = CreateStudyRes.fromEntity(study);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CreateStudyRes>> getAllStudies(){
        List<Study> studies = studyService.getAllStudies();
        List<CreateStudyRes> response = studies.stream()
                .map(CreateStudyRes::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

}
