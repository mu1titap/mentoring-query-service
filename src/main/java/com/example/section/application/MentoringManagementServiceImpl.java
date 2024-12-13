package com.example.section.application;

import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.infrastructure.custom.CustomMentoringRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MentoringManagementServiceImpl implements MentoringManagementService {
    private final CustomMentoringRepository customMentoringRepository;

    @Override
    public void setMainMentoring(String mentoringUuid) {
        customMentoringRepository.setMainMentoring(mentoringUuid);
    }

    @Override
    public void unSetMainMentoring(String mentoringUuid) {
        customMentoringRepository.unSetMainMentoring(mentoringUuid);
    }

    @Override
    public List<MentoringResponseDto> getMainMentoringList() {
        return customMentoringRepository.getMainMentoringList();
    }

}
