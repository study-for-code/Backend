package goorm.spoco.domain.algorithm.service;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.domain.AlgorithmStatus;
import goorm.spoco.domain.algorithm.dto.AlgorithmDTO;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlgorithmService {

    private final AlgorithmRepository algorithmRepository;

    public Algorithm save(Algorithm algorithm) {
        // == 문제를 검색할 때, 번호와 타이틀을 나눌 때, title 의 중복여부를 체크... 하지만 일단은 관리자의 책임으로... == //
        // == + 이런식으로하면 알고리즘 문제 번호 생성과 ID 생성의 구조를 바꿔야해서 추후 논의가 필요함... == //
//        Optional<Algorithm> optionalAlgorithm = algorithmRepository.findByTitle(algorithm.getTitle());
//        if(optionalAlgorithm.isPresent()){
//            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, algorithm.getTitle()+"은/는 이미 존재하는 타이틀입니다. ");
//        }

        return algorithmRepository.save(algorithm);
    }

    public void delete(Long id) {
        Algorithm algorithm = algorithmRepository.findAlgorithmByAlgorithmIdAndAlgorithmStatus(id, AlgorithmStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, id + "에 해당하는 알고리즘이 존재하지 않습니다."));

        algorithm.delete();
        algorithmRepository.save(algorithm);
    }

    public Algorithm update(Long algorithmId, AlgorithmDTO algorithmDTO) {
        Algorithm algorithm = algorithmRepository.findAlgorithmByAlgorithmIdAndAlgorithmStatus(algorithmId, AlgorithmStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, algorithmId + "에 해당하는 알고리즘이 존재하지 않습니다."));

        algorithm.setTitle(algorithmDTO.getTitle());
        algorithm.setExplanation(algorithmDTO.getExplanation());

        return algorithm;
    }

    public Algorithm get(Long id) {
        return algorithmRepository.findAlgorithmByAlgorithmIdAndAlgorithmStatus(id, AlgorithmStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, id + "에 해당하는 알고리즘이 존재하지 않습니다."));
    }

    //== 숫자로 면 문제번호로 검색. 제목이면 제목% 검색 이후 없다면, %제목% 으로 검색 ==//
    public List<Algorithm> searchAlgorithms(String title) {
        // 들어온 title 이 숫자로만 이루어져 있으면 true
        if (title.matches("\\d+")) {
            return algorithmRepository.findAlgorithmsByTitleLikeAndAlgorithmStatus(title + "%", AlgorithmStatus.ACTIVE);
        } else {
            // title% 이 존재하지 않는다면 %title% 로 반환. 그래도 없으면 빈 List 반환
            if (algorithmRepository.findAlgorithmsByOnlyTitle(title + "%", "ACTIVE").isEmpty()) {
                return algorithmRepository.findAlgorithmsByTitleLikeAndAlgorithmStatus("%" + title + "%", AlgorithmStatus.ACTIVE);
            } else {
                return algorithmRepository.findAlgorithmsByOnlyTitle(title + "%", "ACTIVE");
            }
        }
    }

    public List<Algorithm> searchAlgorithmsByNum(String num) {
        return algorithmRepository.findAlgorithmsByTitleLikeAndAlgorithmStatus(num + "%", AlgorithmStatus.ACTIVE);
    }

}
