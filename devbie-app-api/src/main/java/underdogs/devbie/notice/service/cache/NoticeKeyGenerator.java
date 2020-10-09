package underdogs.devbie.notice.service.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import underdogs.devbie.notice.dto.NoticeReadRequest;
import underdogs.devbie.notice.vo.NoticeCacheVo;

@Component
public class NoticeKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        NoticeReadRequest noticeReadRequest = (NoticeReadRequest)params[0];
        PageRequest pageRequest = (PageRequest)params[1];

        NoticeCacheVo noticeCacheVo = new NoticeCacheVo(noticeReadRequest, pageRequest.getPageNumber());

        return noticeCacheVo.hashCode();
    }
}
