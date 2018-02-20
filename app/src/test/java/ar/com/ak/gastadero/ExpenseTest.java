package ar.com.ak.gastadero;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class ExpenseTest {
    private static final UUID ID = UUID.fromString("1c37e9d0-de08-4fa8-b7ad-ea68b09f2a3c");
    private static final Date TARGET = new Date(1511885955100l);
    private static final Date CREATED_ON = new Date(453361477000l);
    private static final String JSON = "{\"amount\":123.45,\"description\":\"Expense description\",\"currency\":\"ARS\",\"id\":\"1c37e9d0-de08-4fa8-b7ad-ea68b09f2a3c\",\"createdOn\":\"1984-05-14T02:44:37-0300\",\"target\":\"2017-11-28\"}";

    @Test
    public void toJson() throws Exception {
        Expense expense = new TestExpense(TARGET, Currency.getInstance("ARS"), BigDecimal.valueOf(123.45), "Expense description");
        assertThat(expense.toJson())
                .isEqualTo(JSON);
    }

    @Test
    public void fromJson() throws Exception {
        Expense expense = Expense.fromJson(JSON);
        assertThat(expense.toJson())
                .isEqualTo(JSON);
    }

    private static class TestExpense extends Expense {
        TestExpense(Date target, Currency currency, BigDecimal amount, String description) {
            super(target, currency, amount, description);
        }

        protected Date newDate() {
            return CREATED_ON;
        }

        protected UUID newUUID() {
            return ID;
        }
    }
}
