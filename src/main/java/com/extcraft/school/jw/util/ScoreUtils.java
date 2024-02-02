package com.extcraft.school.jw.util;

import java.util.List;

public class ScoreUtils {

        public static double calculateTotalScore(List<String> scoreStrList) {
            return scoreStrList.stream()
                    .mapToDouble(ScoreUtils::parseScore)
                    .sum();
        }
        private static double parseScore(String scoreStr) {
            if (scoreStr != null && !scoreStr.isEmpty()) {
                try {
                    return Double.parseDouble(scoreStr);
                } catch (NumberFormatException e) {
                    // 记录异常或处理错误情况
                    return 0.0;
                }
            } else {
                return 0.0;
            }
        }
    }

