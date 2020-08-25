package underdogs.devbie.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.chat.domain.StompMethodType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class StompMessageResponse<T extends StompMessageResponseData> {

    private StompMethodType stompMethodType;
    private T data;

    public static <T extends StompMessageResponseData> StompMessageResponse of(StompMethodType stompMethodType,
        T data) {
        return new StompMessageResponse(stompMethodType, data);
    }
}
