package com.example.section.infrastructure.custom;

import com.example.section.messagequeue.messageIn.MentoringEditRequestOutDto;
import com.example.section.entity.Mentoring;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Log4j2
public class CustomMentoringRepositoryImpl implements CustomMentoringRepository {

    private final MongoTemplate mongoTemplate;
    @Override
    public void updateMentoring(MentoringEditRequestOutDto dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringUuid").is(dto.getUuid()));

        Update update = new Update();
        update.set("name", dto.getName());
        update.set("description", dto.getDescription());
        update.set("detail", dto.getDetail());
        update.set("isReusable", dto.getIsReusable());
        update.set("thumbnailUrl", dto.getThumbnailUrl());
        update.set("mentoringCategoryList", dto.getCategoryList());
        update.set("updatedAt", LocalDateTime.now());
        //log.info("현재시간 : "+ LocalDateTime.now());

        mongoTemplate.updateFirst(query, update, Mentoring.class);
    }

    @Override
    public List<Mentoring> getReusableMentoringListByMentorUuid(String userUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentorUuid").is(userUuid));
        query.addCriteria(Criteria.where("isReusable").is(true));
        query.addCriteria(Criteria.where("isDeleted").is(false));
        query.with(Sort.by(Sort.Direction.DESC, "updatedAt"));

        return new ArrayList<>(mongoTemplate.find(query, Mentoring.class));
    }

    @Override
    public List<Mentoring> findAllByMentorUuidAndIsDeletedFalse(String userUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentorUuid").is(userUuid));
        query.addCriteria(Criteria.where("isDeleted").is(false));
        query.with(Sort.by(Sort.Direction.DESC, "updatedAt"));

        return new ArrayList<>(mongoTemplate.find(query, Mentoring.class));
    }

    @Override
    public List<Mentoring> findAllByCategoryCodes(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringCategoryList.topCategoryCode").is(topCategoryCode));
        if(middleCategoryCode != null) query.addCriteria(Criteria.where("mentoringCategoryList.middleCategoryCode").is(middleCategoryCode));
        if(bottomCategoryCode != null) query.addCriteria(Criteria.where("mentoringCategoryList.topCategoryName").is(bottomCategoryCode));
        query.addCriteria(Criteria.where("isDeleted").is(false));

        return new ArrayList<>(mongoTemplate.find(query, Mentoring.class));
    }


}
