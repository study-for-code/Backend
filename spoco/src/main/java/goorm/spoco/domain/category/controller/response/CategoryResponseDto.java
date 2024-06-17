package goorm.spoco.domain.category.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.algorithm.dto.AlgorithmDTO;
import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.subscribe.controller.response.SubscribeResponseDto;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record CategoryResponseDto(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long categoryId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String title,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<SubscribeResponseDto> subscribes
) {
    public static CategoryResponseDto from(Category category) {
        return new CategoryResponseDto(
                category.getCategoryId(),
                category.getTitle(),
                category.getSubscribes().stream().map(SubscribeResponseDto::from).collect(Collectors.toList())
        );
    }
}
