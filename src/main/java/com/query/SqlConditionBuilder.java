package com.query;

import java.util.List;

public class SqlConditionBuilder {
    private String getCondition(AccountCondition condition) {
        String nbsCondition = "nbs = " + condition.NBS;
        String ob22Condition = condition.Flag_OB22 == 1 ? "ob22 = \"" + condition.OB22 +"\"" : "ob22 != \"" + condition.OB22 +"\"";
        return "(" + nbsCondition + " and " + ob22Condition + ")";
    }

    public String GetSqlCondition(List<AccountCondition> conditions) {

        StringBuilder sqlCondition = new StringBuilder("and ");

        if (conditions.get(0).Flag_NBS == 1) {
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
}
