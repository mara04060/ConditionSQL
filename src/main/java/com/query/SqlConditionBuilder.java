package com.query;

import java.util.List;

public class SqlConditionBuilder {
    private String getCondition(AccountCondition condition) {
        String nbsCondition = "nbs = " + condition.NBS;
        String ob22Condition = condition.Flag_OB22 == 1 ? "ob22 = \"" + condition.OB22 +"\"" : "ob22 != \"" + condition.OB22 +"\"";
        return "(" + nbsCondition + " and " + ob22Condition + ")";
    }

    public String GetSqlCondition(List<AccountCondition> conditions) {
        // Определяем тип условий
        boolean isAllowingConditions = conditions.get(0).Flag_NBS == 1;

        StringBuilder sqlCondition = new StringBuilder("and ");

        if (isAllowingConditions) {
            // Positive
            sqlCondition.append("(");
            for (int i = 0; i < conditions.size(); i++) {
                if (i > 0) sqlCondition.append(" and ");
                sqlCondition.append(getCondition(conditions.get(i)));
            }
            sqlCondition.append(")");
        } else {
            // Negative
            sqlCondition.append("NOT (");
            for (int i = 0; i < conditions.size(); i++) {
                if (i > 0) sqlCondition.append(" and ");
                sqlCondition.append(getCondition(conditions.get(i)));
            }
            sqlCondition.append("))");
        }

        return sqlCondition.toString();
    }
//
//    public static void main(String[] args) {
//        SqlConditionBuilder generator = new SqlConditionBuilder();
//
//        List<AccountCondition> conditionsAllowing = List.of(
//                new AccountCondition(1, (byte) 1, "2620", (byte) 1, "2"),
//                new AccountCondition(1, (byte) 1, "2625", (byte) 1, "20")
//        );
//        System.out.println(generator.GetSqlCondition(conditionsAllowing));
//        List<AccountCondition> conditionsDenying = List.of(
//                new AccountCondition(3, (byte) 0, "2628", (byte) 1, "12"),
//                new AccountCondition(3, (byte) 0, "2608", (byte) 0, "2")
//        );
//        System.out.println(generator.GetSqlCondition(conditionsDenying));
//    }
}
