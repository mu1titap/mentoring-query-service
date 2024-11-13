package com.example.section.infrastructure.custom;

import com.example.section.dto.messageIn.AfterSessionUserOutDto;
import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.entity.MentoringSession;
import com.example.section.entity.vo.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Log4j2
public class CustomSessionRepositoryImpl implements CustomSessionRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public void updateSessionToSessionUserRegister(AfterSessionUserOutDto dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sessionUuid").is(dto.getSessionUuid()));
        // 현재인원수, 세션 닫힘 상태, 세션유저리스트(only userUuid) 업데이트
        Update update = new Update();
        update.inc("nowHeadCount",1);
        update.push("sessionUsers", dto.toSessionUserValueObject());
        update.set("updatedAt", LocalDate.now());
        if (dto.getIsClosed()) {
            update.set("isClosed",true);
        }
        mongoTemplate.updateFirst(query, update, MentoringSession.class);
    }

    @Override
    public List<MentoringSession> findAllByMentoringUuidAndDeadlineDate(String mentoringUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringUuid").is(mentoringUuid)
                .and("isDeleted").is(false) // 삭제되지 않은 세션
                .and("deadlineDate").gte(LocalDate.now()) // 오늘 까지의 데이터 조회 >=
        );
        return mongoTemplate.find(query, MentoringSession.class);
    }

}
