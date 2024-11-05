package com.example.section.infrastructure.custom;

import com.example.section.dto.in.MentoringEditRequestOutDto;
import com.example.section.entity.Mentoring;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomMentoringRepositoryImpl implements CustomMentoringRepository {

    private final MongoTemplate mongoTemplate;
    @Override
    public void updateMentoring(MentoringEditRequestOutDto dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringUuid").is(dto.getUuid()));

        Update update = new Update();
        update.set("name", dto.getName());
        update.set("detail", dto.getDetail());
        update.set("isReusable", dto.getIsReusable());
        update.set("thumbnailUrl", dto.getThumbnailUrl());
        update.set("mentoringCategoryList", dto.getCategoryList());

        mongoTemplate.updateFirst(query, update, Mentoring.class);
    }

    @Override
    public List<Mentoring> getReusableMentoringListByMentorUuid(String mentorUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentorUuid").is(mentorUuid));
        query.addCriteria(Criteria.where("isReusable").is(true));
        query.addCriteria(Criteria.where("isDeleted").is(false));
        query.with(Sort.by(Sort.Direction.DESC, "updatedAt"));

        return new ArrayList<>(mongoTemplate.find(query, Mentoring.class));
    }

    @Override
    public List<Mentoring> findAllByMentorUuidAndIsDeletedFalse(String mentorUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentorUuid").is(mentorUuid));
        query.addCriteria(Criteria.where("isDeleted").is(false));
        query.with(Sort.by(Sort.Direction.DESC, "updatedAt"));

        return new ArrayList<>(mongoTemplate.find(query, Mentoring.class));
    }


}
