package com.example.section.elasticSearch.application;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ChosungAnalyzeServiceTest {

    @Autowired ChosungAnalyzeService chosungAnalyzeService;


    @Test
    void extractChosung(){
        log.info(chosungAnalyzeService.extractChosung("해외여행"));
        log.info(chosungAnalyzeService.extractChosung("해외sd여행"));
        log.info(chosungAnalyzeService.extractChosung("엘라스틱"));
        log.info(chosungAnalyzeService.extractChosung("랗굱낮"));
        log.info(chosungAnalyzeService.extractChosung("12345"));
        log.info(chosungAnalyzeService.extractChosung("해외여행sd"));
        log.info(chosungAnalyzeService.extractChosung("sd해외여행"));
        log.info(chosungAnalyzeService.extractChosung("해외여행 쏘굿 입니다"));


    }

}