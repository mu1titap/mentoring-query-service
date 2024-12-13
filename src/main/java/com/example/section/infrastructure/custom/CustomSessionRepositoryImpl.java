package com.example.section.infrastructure.custom;

import com.example.section.dto.out.MentoringCoreInfoResponseDto;
import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.messagequeue.messageIn.AfterSessionUserOutDto;
import com.example.section.messagequeue.messageIn.CancelSessionUserMessage;
import com.example.section.messagequeue.messageIn.ReRegisterSessionUserMessage;
import com.example.section.entity.MentoringSession;
import com.example.section.entity.vo.SessionUser;
import com.example.section.messagequeue.messageIn.SessionConfirmedMessage;
import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
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
    public void cancelSessionUser(CancelSessionUserMessage dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sessionUuid").is(dto.getSessionUuid()));
        // 현재인원수, 세션 닫힘 상태, 세션유저리스트(only userUuid) 업데이트
        Update update = new Update();
        update.inc("nowHeadCount",-1);
        update.pull("sessionUsers", Query.query(Criteria.where("userUuid").is(dto.getMenteeUuid())));
        update.set("updatedAt", LocalDate.now());
        if (dto.getShouldOpenSession()) {
            update.set("isClosed",false);
        }
        mongoTemplate.updateFirst(query, update, MentoringSession.class);
    }

    @Override
    public void reRegisterSessionUser(ReRegisterSessionUserMessage dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sessionUuid").is(dto.getSessionUuid()));
        // 현재인원수, 세션 닫힘 상태, 세션유저리스트(only userUuid) 업데이트
        Update update = new Update();
        update.inc("nowHeadCount",1);
        update.push("sessionUsers", dto.toSessionUser());
        update.set("updatedAt", LocalDate.now());
        if (dto.getShouldCloseSession()) {
            update.set("isClosed",true);
        }
        mongoTemplate.updateFirst(query, update, MentoringSession.class);
    }


    @Override
    public List<MentoringSessionResponseDto> findAllByMentoringUuidAndDeadlineDateV2(String mentoringUuid, String userUuid) {

        Aggregation aggregation = Aggregation.newAggregation(
                // sessionUsers 배열을 펼침
                Aggregation.unwind("sessionUsers", true),

                Aggregation.match(Criteria.where("mentoringUuid").is(mentoringUuid)
                        .and("isDeleted").is(false)
                        .and("deadlineDate").gte(LocalDate.now())),
                // 참여 여부 필드 추가
                Aggregation.addFields().addField("isParticipating")
                        .withValue(
                                ConditionalOperators.when(Criteria.where("sessionUsers.userUuid").is(userUuid))
                                        .then(true)
                                        .otherwise(false)
                        ).build(),

                // sessionUsers를 배열로 다시 그룹화
                Aggregation.group("sessionUuid", "mentoringUuid", "startDate", "endDate", "startTime", "endTime",
                                "deadlineDate", "minHeadCount", "maxHeadCount", "nowHeadCount", "isParticipating", "price", "isClosed")
                        .push(new BasicDBObject()
                                .append("menteeImageUrl", "$sessionUsers.menteeImageUrl")
                                .append("nickName", "$sessionUsers.nickName"))
                                //.append("userUuid", "$sessionUsers.userUuid"))
                        .as("sessionUserList"),
                // 정렬
                Aggregation.sort(Sort.by(Sort.Order.asc("startDate"))
                        .and(Sort.by(Sort.Order.asc("startTime")))),

                // 필요한 필드만 반환
                Aggregation.project("sessionUuid", "mentoringUuid", "startDate", "endDate", "startTime", "endTime",
                        "deadlineDate", "minHeadCount", "maxHeadCount", "nowHeadCount", "isParticipating", "price",
                        "isClosed", "sessionUserList")
        );
        return mongoTemplate.aggregate(aggregation, "mentoring_session", MentoringSessionResponseDto.class)
                .getMappedResults();

    }

    @Override
    public void updateSessionConfirmed(SessionConfirmedMessage dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sessionUuid").is(dto.getSessionUuid()));
        // 현재인원수, 세션 닫힘 상태, 세션유저리스트(only userUuid) 업데이트
        Update update = new Update();
        update.set("updatedAt", LocalDate.now());
        update.set("isConfirmed",dto.getSessionIsConfirmed());
        mongoTemplate.updateFirst(query, update, MentoringSession.class);
    }

}
