package com.example.section.elasticSearch.application;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
public class HangulUtils {

    private static final int HANGUL_BASE = 0xAC00; // '가'
    private static final int CHO_COUNT = 19; // 초성 개수
    private static final int JUNG_COUNT = 21; // 중성 개수
    private static final int JONG_COUNT = 28; // 종성 개수

    private static final char[] CHO = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ".toCharArray();
    private static final char[] JUNG = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ".toCharArray();
    private static final char[] JONG = " \0ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ".toCharArray();

    public static String combine(String decomposed) {
        StringBuilder result = new StringBuilder();
        int cho = -1, jung = -1, jong = -1;

        for (char c : decomposed.toCharArray()) {
            if (isCho(c)) {
                if (cho != -1 && jung != -1) { // 조합 완료된 한글
                    result.append(combineHangul(cho, jung, jong));
                    cho = -1;
                    jung = -1;
                    jong = -1;
                }
                cho = getIndex(CHO, c);
            } else if (isJung(c)) {
                if (jung != -1) { // 중성이 중복되면 조합 불가능, 초기화
                    result.append(combineHangul(cho, jung, jong));
                    cho = -1;
                    jung = -1;
                    jong = -1;
                }
                jung = getIndex(JUNG, c);
            } else if (isJong(c)) {
                if (jong != -1) { // 종성이 중복되면 초기화
                    result.append(combineHangul(cho, jung, jong));
                    cho = -1;
                    jung = -1;
                    jong = -1;
                }
                jong = getIndex(JONG, c);
            } else { // 자모가 아닌 경우 그대로 추가
                result.append(c);
            }
        }

        // 마지막 글자 처리
        if (cho != -1 && jung != -1) {
            result.append(combineHangul(cho, jung, jong));
        }

        return result.toString();
    }

    private static boolean isCho(char c) {
        return getIndex(CHO, c) != -1;
    }

    private static boolean isJung(char c) {
        return getIndex(JUNG, c) != -1;
    }

    private static boolean isJong(char c) {
        return getIndex(JONG, c) != -1;
    }

    private static int getIndex(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1;
    }

    private static char combineHangul(int cho, int jung, int jong) {
        return (char) (HANGUL_BASE + cho * JUNG_COUNT * JONG_COUNT + jung * JONG_COUNT + (jong + 1));
    }
}