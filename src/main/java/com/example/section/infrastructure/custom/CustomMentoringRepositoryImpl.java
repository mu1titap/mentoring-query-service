package com.example.section.infrastructure.custom;

import com.example.section.dto.out.MentoringCoreInfoResponseDto;
import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.messagequeue.messageIn.MentoringEditRequestOutDto;
import com.example.section.entity.Mentoring;
import com.example.section.messagequeue.messageIn.ReviewStarDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if(dto.getCategoryList() != null)
            update.set("mentoringCategoryList", dto.getCategoryList()); // 카테고리 업데이트
        if(dto.getHashTag()!=null)
            update.set("mentoringHashTagList", dto.getHashTag().getAfterHashtagList()); // 해시태그 업데이트
        update.set("updatedAt", LocalDateTime.now());

        mongoTemplate.updateFirst(query, update, Mentoring.class);

        log.info("멘토링 업데이트 완료");
    }

    @Override
    public void increaseNowSessionCount(String mentoringUuid, int count) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringUuid").is(mentoringUuid));
        Update update = new Update();
        update.inc("nowSessionCount", count);
        mongoTemplate.updateFirst(query, update, Mentoring.class);
    }

    @Override
    public void decreaseNowSessionCountByUuid(String mentoringUuid, int count) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringUuid").is(mentoringUuid));
        Update update = new Update();
        update.inc("nowSessionCount", -count);
        mongoTemplate.updateFirst(query, update, Mentoring.class);
    }

    @Override
    public void decreaseNowSessionCountById(String mentoringId, int count) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringId").is(mentoringId));
        Update update = new Update();
        update.inc("nowSessionCount", -count);
        mongoTemplate.updateFirst(query, update, Mentoring.class);
    }

    @Override
    public void updateReviewStar(ReviewStarDto dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringUuid").is(dto.getMentoringUuid()));
        Update update = new Update();
        update.set("reviewCount", dto.getReviewCount());
        update.set("averageStar", dto.getAverageStar());
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
    public List<MentoringCoreInfoResponseDto> findAllByMentorUuidAndIsDeletedFalse(String userUuid, Boolean isMentor) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("mentorUuid").is(userUuid)
                    .and("isDeleted").is(false)),
            // prioritySort 필드 추가
            Aggregation.addFields().addField("prioritySort")
                    .withValue(
                        ConditionalOperators.when(Criteria.where("nowSessionCount").gte(1))
                                .then(true)
                                .otherwise(false)
                    ).build(),
                Aggregation.sort(isMentor ? Sort.by(Sort.Order.desc("updatedAt"))
                        : Sort.by(Sort.Order.desc("prioritySort")).and(Sort.by(Sort.Order.desc("updatedAt")))),
            Aggregation.project("mentoringUuid", "name", "description" , "thumbnailUrl", "prioritySort", "nowSessionCount")
                    .and("mentoringUuid").as("mentoringUuid")
                    .and("name").as("name")
                    .and("description").as("description")
                    .and("thumbnailUrl").as("thumbnailUrl")
                    .and("prioritySort").as("isAvailable")
                    .and("nowSessionCount").as("nowSessionCount")
        );

        return mongoTemplate.aggregate(aggregation, "mentoring", MentoringCoreInfoResponseDto.class)
                .getMappedResults();

    }

    @Override
    public Page<MentoringCoreInfoResponseDto> searchMentoringByMentorUuidPagination(String userUuid, Boolean isMentor, Pageable pageable) {
        Criteria criteria = Criteria.where("mentorUuid").is(userUuid)
                .and("isDeleted").is(false);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.addFields().addField("prioritySort")
                        .withValue(
                                ConditionalOperators.when(Criteria.where("nowSessionCount").gte(1))
                                        .then(true)
                                        .otherwise(false)
                        ).build(),
                Aggregation.sort(isMentor ? Sort.by(Sort.Order.desc("updatedAt"))
                        : Sort.by(Sort.Order.desc("prioritySort")).and(Sort.by(Sort.Order.desc("updatedAt")))),
                Aggregation.project("mentoringUuid", "name", "description", "thumbnailUrl", "prioritySort", "nowSessionCount")
                        .and("mentoringUuid").as("mentoringUuid")
                        .and("name").as("name")
                        .and("description").as("description")
                        .and("thumbnailUrl").as("thumbnailUrl")
                        .and("prioritySort").as("isAvailable")
                        .and("nowSessionCount").as("nowSessionCount"),
                // 페이지네이션 처리
                Aggregation.skip(pageable.getOffset()),
                Aggregation.limit(pageable.getPageSize())
        );
        List<MentoringCoreInfoResponseDto> content = mongoTemplate.aggregate(aggregation, "mentoring", MentoringCoreInfoResponseDto.class)
                .getMappedResults();

        // 토탈 카운트
        Aggregation countAggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.count().as("total")
        );

        // Total count 계산
        long total = Optional.ofNullable(mongoTemplate.aggregate(countAggregation, "mentoring", Document.class)
                        .getUniqueMappedResult())
                .map(result -> result.get("total"))
                .map(totalCount -> totalCount instanceof Number ? ((Number) totalCount).longValue() : 0L)
                .orElse(0L);

        return PageableExecutionUtils.getPage(content, pageable, () -> total);
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

    @Override
    public Page<MentoringCoreInfoResponseDto> searchByCategoryCodesPagination(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode,
                                                                      Pageable pageable) {
        // 카테고리 검색조건
        Criteria categoryCriteria = Criteria.where("mentoringCategoryList.topCategoryCode").is(topCategoryCode)
                .and("isDeleted").is(false);
        if (middleCategoryCode != null) {
            categoryCriteria.and("mentoringCategoryList.middleCategoryCode").is(middleCategoryCode);
        }
        if (bottomCategoryCode != null) {
            categoryCriteria.and("mentoringCategoryList.bottomCategoryCode").is(bottomCategoryCode);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(categoryCriteria),
                Aggregation.addFields().addField("prioritySort")
                        .withValue(
                                ConditionalOperators.when(Criteria.where("nowSessionCount").gte(1))
                                        .then(true)
                                        .otherwise(false)
                        ).build(),
                Aggregation.sort(Sort.by(Sort.Order.desc("prioritySort")).and(Sort.by(Sort.Order.desc("updatedAt")))),

                Aggregation.project("mentoringUuid", "name", "description", "thumbnailUrl", "prioritySort", "nowSessionCount")
                        .and("mentoringUuid").as("mentoringUuid")
                        .and("name").as("name")
                        .and("description").as("description")
                        .and("thumbnailUrl").as("thumbnailUrl")
                        .and("prioritySort").as("isAvailable")
                        .and("nowSessionCount").as("nowSessionCount"),
                // 페이지네이션 처리
                Aggregation.skip(pageable.getOffset()),
                Aggregation.limit(pageable.getPageSize())
        );
        List<MentoringCoreInfoResponseDto> content = mongoTemplate.aggregate(aggregation, "mentoring", MentoringCoreInfoResponseDto.class)
                .getMappedResults();

        // 토탈 카운트
        Aggregation countAggregation = Aggregation.newAggregation(
                Aggregation.match(categoryCriteria),
                Aggregation.count().as("total")
        );

        // Total count 계산
        long total = Optional.ofNullable(mongoTemplate.aggregate(countAggregation, "mentoring", Document.class)
                        .getUniqueMappedResult())
                .map(result -> result.get("total"))
                .map(totalCount -> totalCount instanceof Number ? ((Number) totalCount).longValue() : 0L)
                .orElse(0L);

        return PageableExecutionUtils.getPage(content, pageable, () -> total);

    }

    @Override
    public Page<MentoringCoreInfoResponseDto> searchByNamePagination(String name, Pageable pageable) {
        Criteria nameCriteria = Criteria.where("name").regex(name, "i")
                .and("isDeleted").is(false);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(nameCriteria),
                Aggregation.addFields().addField("prioritySort")
                        .withValue(
                                ConditionalOperators.when(Criteria.where("nowSessionCount").gte(1))
                                        .then(true)
                                        .otherwise(false)
                        ).build(),
                Aggregation.sort(Sort.by(Sort.Order.desc("prioritySort")).and(Sort.by(Sort.Order.desc("updatedAt")))),
                Aggregation.project("mentoringUuid", "name", "description", "thumbnailUrl", "prioritySort", "nowSessionCount")
                        .and("mentoringUuid").as("mentoringUuid")
                        .and("name").as("name")
                        .and("description").as("description")
                        .and("thumbnailUrl").as("thumbnailUrl")
                        .and("prioritySort").as("isAvailable")
                        .and("nowSessionCount").as("nowSessionCount")
                        .and("reviewCount").as("reviewCount")
                        .and("averageStar").as("averageStar"),
                // 페이지네이션 처리
                Aggregation.skip(pageable.getOffset()),
                Aggregation.limit(pageable.getPageSize())
        );
        List<MentoringCoreInfoResponseDto> content = mongoTemplate.aggregate(aggregation, "mentoring", MentoringCoreInfoResponseDto.class)
                .getMappedResults();

        // 토탈 카운트
        Aggregation countAggregation = Aggregation.newAggregation(
                Aggregation.match(nameCriteria),
                Aggregation.count().as("total")
        );

        // Total count 계산
        long total = Optional.ofNullable(mongoTemplate.aggregate(countAggregation, "mentoring", Document.class)
                        .getUniqueMappedResult())
                .map(result -> result.get("total"))
                .map(totalCount -> totalCount instanceof Number ? ((Number) totalCount).longValue() : 0L)
                .orElse(0L);

        return PageableExecutionUtils.getPage(content, pageable, () -> total);

    }

}
