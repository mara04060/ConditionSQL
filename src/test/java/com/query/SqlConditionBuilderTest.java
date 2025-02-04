package com.query;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class SqlConditionBuilderTest {
    @DataProvider(name = "sqlTestData")
    public Object[][] sqlTestData() {
        return new Object[][] {
                // 1: Allowed
                {
                        List.of(
                                new AccountCondition(1, (byte) 1, "2630", (byte) 1, "12"),
                                new AccountCondition(1, (byte) 1, "2690", (byte) 1, "2"),
                                new AccountCondition(2, (byte) 1, "2620", (byte) 0, "36")
                        ),
                        "and ((nbs = 2630 and ob22 = \"12\") and (nbs = 2690 and ob22 = \"2\") and (nbs = 2620 and ob22 != \"36\"))"
                },
                // 2: forbidden
                {
                        List.of(
                                new AccountCondition(3, (byte) 0, "2628", (byte) 1, "12"),
                                new AccountCondition(4, (byte) 0, "2608", (byte) 0, "2")
                        ),
                        "and NOT ((nbs = 2628 and ob22 = \"12\") and (nbs = 2608 and ob22 != \"2\")))"
                },
                // 3: Allowed
                {
                        List.of(
                                new AccountCondition(1, (byte) 1, "2620", (byte) 1, "2"),
                                new AccountCondition(1, (byte) 1, "2625", (byte) 1, "20")
                        ),
                        "and ((nbs = 2620 and ob22 = \"2\") and (nbs = 2625 and ob22 = \"20\"))"
                },
                // 4 Negative Test
                {
                        List.of(
                                new AccountCondition(1, (byte) 1, "2620", (byte) 1, "2"),
                                new AccountCondition(0, (byte) 1, "2625", (byte) 1, "20")
                        ),
                        "and ((nbs = 2620 and ob22 = \"2\") and NOT (nbs = 2625 and ob22 = \"20\"))"
                }

        };
    }

    @Test(dataProvider = "sqlTestData")
    public void testGetSqlConditionPositive(List<AccountCondition> conditions, String expectedSql) {
        SqlConditionBuilder generator = new SqlConditionBuilder();
        String actualSql = generator.GetSqlCondition(conditions);
        Assert.assertEquals(actualSql, expectedSql);
    }

}
