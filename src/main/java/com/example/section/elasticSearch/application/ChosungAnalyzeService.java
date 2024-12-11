package com.example.section.elasticSearch.application;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ChosungAnalyzeService {
    // 한글 음절 유니코드 범위 및 관련 상수
    private static final char UNICODE_SYLLABLES_START_CODEPOINT = 0xAC00; // '가'
    private static final int COUNT_IN_UNICODE = 11172; // 한글 음절 개수
    private static final int COUNT_JUNGSUNG_IN_UNICODE = 21; // 중성 개수
    private static final int COUNT_JONGSUNG_IN_UNICODE = 28; // 종성 개수
    private static final int JAMO_SPLIT_VALUE = COUNT_JUNGSUNG_IN_UNICODE * COUNT_JONGSUNG_IN_UNICODE;

    // 초성 배열 (한글 초성 19개)
    private static final char[] COMPATIBILITY_CHOSUNGs = {
            0x3131, 0x3132, 0x3134, 0x3137, 0x3138, // ㄱ, ㄲ, ㄴ, ㄷ, ㄸ
            0x3139, 0x3141, 0x3142, 0x3143, 0x3145, // ㄹ, ㅁ, ㅂ, ㅃ, ㅅ
            0x3146, 0x3147, 0x3148, 0x3149, 0x314A, // ㅆ, ㅇ, ㅈ, ㅉ, ㅊ
            0x314B, 0x314C, 0x314D, 0x314E          // ㅋ, ㅌ, ㅍ, ㅎ
    };

    /**
     * 주어진 문자열에서 한글 초성을 추출하는 메서드
     *
     * @param input 분석할 문자열
     * @return 추출된 초성 문자열 (빈 문자열은 한글이 없거나 입력이 없을 때 반환)
     */
    public String extractChosung(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder chosungBuilder = new StringBuilder();

        for (char ch : input.toCharArray()) {
            // 한글 음절 여부 확인
            char expectedKorean = (char) (ch - UNICODE_SYLLABLES_START_CODEPOINT);

            if (expectedKorean >= 0 && expectedKorean < COUNT_IN_UNICODE) {
                // 한글 음절인 경우 초성 추출
                int chosungIndex = expectedKorean / JAMO_SPLIT_VALUE;
                chosungBuilder.append(COMPATIBILITY_CHOSUNGs[chosungIndex]);
            } else {
                // 한글 음절이 아닌 경우 무시
                continue;
            }
        }

        return chosungBuilder.toString();
    }

    /**
     * 문자열이 한글로만 구성되어 있는지 확인
     *
     * @param token 확인할 문자열
     * @return 한글로만 구성되어 있으면 true, 아니면 false
     */
    public boolean isKorean(String token) {
        if (token == null || token.isEmpty()) {
            return false; // null이나 빈 문자열은 한글 아님
        }

        for (char ch : token.toCharArray()) {
            if (ch < 0xAC00 || ch > 0xD7A3) {
                return false; // 한글 음절 범위에 없으면 false
            }
        }

        return true; // 모든 문자가 한글 음절
    }
}