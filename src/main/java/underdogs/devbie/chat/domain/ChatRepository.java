package underdogs.devbie.chat.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select c from Chat c join ChatRoom r on c.chatRoom.id=r.id where r.noticeId=:noticeId")
    List<Chat> findByNoticeId(@Param("noticeId") Long noticeId);
}
