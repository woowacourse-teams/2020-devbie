package underdogs.devbie.question.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.question.domain.Question;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class QuestionResponse {

    private Long questionId;
    private Long userId;
    private Long visits;
    private String title;
    private String content;

    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
            .questionId(question.getId())
            .userId(question.getUserId())
            .visits(question.getVisits().getVisitCount())
            .title(question.getTitle().getTitle())
            .content(question.getContent().getContent())
            .build();
    }
}
