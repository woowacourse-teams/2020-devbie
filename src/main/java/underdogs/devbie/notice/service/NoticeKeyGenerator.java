package underdogs.devbie.notice.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import underdogs.devbie.notice.dto.NoticeReadRequest;

@Component
public class NoticeKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        NoticeReadRequest noticeReadRequest = (NoticeReadRequest)params[0];
        PageRequest pageRequest = (PageRequest)params[1];

        List<String> keyParams = createParams(noticeReadRequest, pageRequest.getPageNumber());

        return String.join(".", keyParams);
    }

    private List<String> createParams(NoticeReadRequest noticeReadRequest, int pageNumber) {
        List<String> params = new ArrayList<>();
        if (Objects.nonNull(noticeReadRequest.getNoticeType())) {
            params.add(noticeReadRequest.getNoticeType().toString());
        }
        if (Objects.nonNull(noticeReadRequest.getJobPosition())) {
            params.add(noticeReadRequest.getJobPosition().toString());
        }
        if (Objects.nonNull(noticeReadRequest.getLanguage())) {
            params.add(noticeReadRequest.getLanguage().toString());
        }
        if (Objects.nonNull(noticeReadRequest.getKeyword())) {
            params.add(noticeReadRequest.getKeyword());
        }
        params.add(String.valueOf(pageNumber));
        return params;
    }
}
