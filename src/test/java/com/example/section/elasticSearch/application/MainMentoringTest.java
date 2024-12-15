package com.example.section.elasticSearch.application;

import com.example.section.elasticSearch.dto.SuggestedNameResponseDto;
import com.example.section.entity.Mentoring;
import com.example.section.entity.vo.MainImage;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
@Log4j2
class MainMentoringTest {
    @Autowired
    private  MongoTemplate mongoTemplate;


    @Test
    void updateMainMentoring(){
        String mentoringUuid = "7853a101-4706-46aa-9497-7b07b2b47a94";
        List<MainImage> mainImageList = List.of(
                MainImage.builder()
                        .url("https://cdn.inflearn.com/public/course-325553-cover/115b1272-f9f9-4a07-b4b9-1ed5ce678d3a?w=420&f=avif")
                        .build(),
                // ?
                MainImage.builder()
                        .url("https://cdn.inflearn.com/public/courses/326362/cover/24612801-a34d-4ef4-bbb0-e8de449d912c?w=420&f=avif")
                        .build(),
                MainImage.builder()
                        .url("https://cdn.inflearn.com/public/courses/335203/cover/def950d1-826b-4e43-a045-4c620fd57829/335203.png?w=420&f=avif")
                        .build()
        );
        Update update = new Update();
        update.set("mainImageList", mainImageList);
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringUuid").is(mentoringUuid));
        update.set("mainImageList", mainImageList);

        mongoTemplate.updateFirst(query, update, Mentoring.class);
    }
    



}