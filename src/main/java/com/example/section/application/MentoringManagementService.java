package com.example.section.application;

import com.example.section.dto.out.MentoringResponseDto;

import java.util.List;

public interface MentoringManagementService {
    void setMainMentoring(String mentoringUuid);

    void unSetMainMentoring(String mentoringUuid);

    List<MentoringResponseDto> getMainMentoringList();

}
