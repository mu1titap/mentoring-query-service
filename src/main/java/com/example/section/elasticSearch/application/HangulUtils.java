package com.example.section.elasticSearch.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HangulUtils {
    // BASE: 가(AC00) 코드 포인트
    private static final int BASE = 0xAC00;

    // 실제 사용 시 초기자(INITIALS), 중성(MEDIALS), 종성(FINALES), MIXED는
    // 기존 JavaScript 코드에 사용했던 동일한 값으로 채워야 합니다.
    private static final String[] INITIALS = {
            "ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ","ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"
    };
    private static final String[] MEDIALS = {
            "ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ","ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ"
    };
    private static final String[] FINALES = {
            "", "ㄱ", "ㄲ", "ㄳ", "ㄴ", "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ", "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅁ",
            "ㅂ", "ㅄ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
    };

    // MIXED: 복합 자모를 단일 자모로 매핑할 때 사용.
    // 여기서 `ㄺ`과 같은 복합 종성을 추가로 넣어줍니다.
    private static final Map<String, String[]> MIXED = new HashMap<String, String[]>() {{
        // 복합 모음 예시
        put("ㅘ", new String[]{"ㅗ","ㅏ"});
        put("ㅙ", new String[]{"ㅗ","ㅐ"});
        put("ㅚ", new String[]{"ㅗ","ㅣ"});
        put("ㅝ", new String[]{"ㅜ","ㅓ"});
        put("ㅞ", new String[]{"ㅜ","ㅔ"});
        put("ㅟ", new String[]{"ㅜ","ㅣ"});
        put("ㅢ", new String[]{"ㅡ","ㅣ"});

        // 복합 종성 예시 (불닭 처리용)
        put("ㄺ", new String[]{"ㄹ","ㄱ"});
        put("ㄳ", new String[]{"ㄱ", "ㅅ"});
        put("ㄵ", new String[]{"ㄴ", "ㅈ"});
        put("ㄶ", new String[]{"ㄴ", "ㅎ"});
        put("ㄻ", new String[]{"ㄹ", "ㅁ"});
        put("ㄼ", new String[]{"ㄹ", "ㅂ"});
        put("ㄽ", new String[]{"ㄹ", "ㅅ"});
        put("ㄾ", new String[]{"ㄹ", "ㅌ"});
        put("ㄿ", new String[]{"ㄹ", "ㅍ"});
        put("ㅀ", new String[]{"ㄹ", "ㅎ"});

    }};

    private static final Map<String, String> complexDict = new HashMap<>();

    static {
        // MIXED를 바탕으로 역매핑을 생성
        for (Map.Entry<String, String[]> entry : MIXED.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            String joined = String.join("", value);
            complexDict.put(joined, key);
        }
    }

    private static boolean isNotUndefined(Object e) {
        return e != null;
    }

    private static String assemble(List<String> arr) {
        int startIndex = -1;
        for (int i = 0; i < arr.size(); i++) {
            if (indexOf(MEDIALS, arr.get(i)) != -1) {
                startIndex = i;
                break;
            }
        }

        int endIndex;
        if (startIndex != -1 && startIndex + 1 < arr.size() && indexOf(MEDIALS, arr.get(startIndex + 1)) != -1) {
            endIndex = startIndex + 1;
        } else {
            endIndex = startIndex;
        }

        String initial = join(arr.subList(0, startIndex == -1 ? 0 : startIndex));
        String medial = startIndex == -1 ? "" : join(arr.subList(startIndex, endIndex + 1));
        String finale = endIndex == -1 ? "" : join(arr.subList(endIndex + 1, arr.size()));

        initial = complexDict.getOrDefault(initial, initial);
        medial = complexDict.getOrDefault(medial, medial);
        finale = complexDict.getOrDefault(finale, finale);

        int initialOffset = indexOf(INITIALS, initial);
        int medialOffset = indexOf(MEDIALS, medial);
        int finaleOffset = indexOf(FINALES, finale);

        if (initialOffset != -1 && medialOffset != -1) {
            int code = BASE + initialOffset * (MEDIALS.length * FINALES.length) + medialOffset * FINALES.length + (finaleOffset == -1 ? 0 : finaleOffset);
            return String.valueOf((char) code);
        }
        return join(arr);
    }

    @SuppressWarnings("unchecked")
    public static String implode(Object input) {
        List<Object> chars = new ArrayList<>();
        // 문자열일 경우 쪼개기
        if (input instanceof String) {
            String str = (String) input;
            for (int i = 0; i < str.length(); i++) {
                chars.add(String.valueOf(str.charAt(i)));
            }
        } else if (input instanceof List) {
            chars.addAll((List<Object>) input);
        } else if (input.getClass().isArray()) {
            Object[] arr = (Object[]) input;
            for (Object e : arr) chars.add(e);
        }

        // 인접한 모음 합치기
        List<Object> combined = new ArrayList<>();
        for (int i = 0; i < chars.size(); i++) {
            Object e = chars.get(i);
            if (i > 0 && combined.size() > 0 && isVowel(chars.get(i - 1)) && isVowel(e)) {
                String prev = (String) combined.get(combined.size() - 1);
                String curr = (String) e;
                String joined = prev + curr;
                if (complexDict.containsKey(joined)) {
                    combined.set(combined.size() - 1, complexDict.get(joined));
                } else {
                    combined.add(e);
                }
            } else {
                combined.add(e);
            }
        }

        class Group {
            String medial = null;
            List<String> finales = new ArrayList<>();
            List<String> initials = null;
            List<List<String>> grouped = null;
        }

        List<Group> items = new ArrayList<>();
        Group cursor = new Group();
        items.add(cursor);

        // 모음 기준으로 그룹핑
        for (Object o : combined) {
            if (o instanceof List) {
                List<String> g = castToStringList(o);
                cursor = new Group();
                Group grouped = new Group();
                grouped.grouped = new ArrayList<>();
                grouped.grouped.add(g);
                items.add(grouped);
                cursor = new Group();
                items.add(cursor);
            } else {
                String e = (String) o;
                if (isVowel(e)) {
                    cursor = new Group();
                    cursor.medial = e;
                    items.add(cursor);
                } else {
                    cursor.finales.add(e);
                }
            }
        }

        // 앞 그룹의 남는 자음을 다음 그룹의 초성으로 이동
        for (int i = 1; i < items.size(); i++) {
            Group prev = items.get(i - 1);
            Group currG = items.get(i);
            if (prev.medial == null || prev.finales.size() == 1) {
                currG.initials = prev.finales;
                prev.finales = new ArrayList<>();
            } else {
                String finale = prev.finales.size() > 0 ? prev.finales.get(0) : null;
                List<String> initials = new ArrayList<>();
                if (prev.finales.size() > 1) {
                    initials.addAll(prev.finales.subList(1, prev.finales.size()));
                }
                currG.initials = initials;
                prev.finales = finale == null ? new ArrayList<>() : listOf(finale);
            }
            if (currG.finales.size() > 2 || (i == items.size() - 1 && currG.finales.size() > 1)) {
                // 복합 종성 결합 시도
                if (currG.finales.size() > 1) {
                    String a = currG.finales.get(0);
                    String b = currG.finales.get(1);
                    String joined = a + b;
                    if (complexDict.containsKey(joined)) {
                        List<String> rest = new ArrayList<>();
                        if (currG.finales.size() > 2) {
                            rest.addAll(currG.finales.subList(2, currG.finales.size()));
                        }
                        currG.finales.clear();
                        currG.finales.add(complexDict.get(joined));
                        currG.finales.addAll(rest);
                    }
                }
            }
        }

        // 각 그룹 조합
        List<List<String>> groups = new ArrayList<>();
        for (Group g : items) {
            if (g.grouped != null && !g.grouped.isEmpty()) {
                groups.addAll(g.grouped);
            } else {
                List<String> initials = g.initials != null ? g.initials : new ArrayList<>();
                String medial = g.medial;
                List<String> finales = g.finales;

                List<String> pre = new ArrayList<>(initials);
                String initial = pre.isEmpty() ? null : pre.remove(pre.size() - 1);
                String finale = "";
                List<String> post = new ArrayList<>(finales);

                if (!post.isEmpty()) {
                    String firstFinale = post.get(0);
                    if (indexOf(FINALES, firstFinale) != -1) {
                        finale = firstFinale;
                        post.remove(0);
                    }
                }

                for (String e : pre) {
                    groups.add(listOf(e));
                }

                List<String> main = new ArrayList<>();
                if (initial != null) main.add(initial);
                if (medial != null) main.add(medial);
                if (!finale.isEmpty()) main.add(finale);
                if (!main.isEmpty()) groups.add(main);

                for (String e : post) {
                    groups.add(listOf(e));
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (List<String> group : groups) {
            sb.append(assemble(group));
        }

        return sb.toString();
    }

    private static int indexOf(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target)) return i;
        }
        return -1;
    }

    private static String join(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) sb.append(s);
        return sb.toString();
    }

    private static boolean isVowel(Object ch) {
        if (!(ch instanceof String)) return false;
        String s = (String) ch;
        return indexOf(MEDIALS, s) != -1;
    }

    private static List<String> castToStringList(Object o) {
        List<String> result = new ArrayList<>();
        if (o instanceof List) {
            for (Object e : (List<?>) o) {
                result.add(e.toString());
            }
        } else {
            result.add(o.toString());
        }
        return result;
    }

    private static List<String> listOf(String... elements) {
        List<String> list = new ArrayList<>();
        for (String e : elements) {
            list.add(e);
        }
        return list;
    }

    public static void main(String[] args) {
        // 테스트
        System.out.println(implode("ㅇㅓㅂㅔㄴㅈㅕㅅㅡ ㅇㅐㄴㄷㅡㄱㅔㅇㅣㅁ"));  // "어벤져스 앤드게임"
        System.out.println(implode(new String[]{"ㅂ", "ㅜ", "ㄹ", "ㄷ", "ㅏ", "ㄹ", "ㄱ"}));  // "불닭" 정상 출력

    }
}