package ar.com.ak.gastadero;


import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@ToString
public class Expense {
    private UUID id;
    private String target;
    private String createdOn;
    private Currency currency;
    private BigDecimal amount;
    private String description;

    public Expense(Date target, Currency currency, BigDecimal amount, String description) {
        this.target = formatTarget(target);
        this.currency = currency;
        this.amount = amount;
        this.description = description;
        id = newUUID();
        createdOn = formatCreatedOn();
    }

    public static Expense fromJson(String json) {
        try {
            Expense result = new Expense();
            JSONObject jsonObject = new JSONObject(json);
            result.id = UUID.fromString(jsonObject.getString("id"));
            result.target = jsonObject.getString("target");
            result.createdOn = jsonObject.getString("createdOn");
            result.currency = Currency.getInstance(jsonObject.getString("currency"));
            result.amount = new BigDecimal(jsonObject.get("amount").toString());
            result.description = jsonObject.getString("description");
            return result;
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    public String toJson() {
        try {
            return new JSONObject()
                    .put("id", id)
                    .put("target", target)
                    .put("createdOn", createdOn)
                    .put("currency", currency)
                    .put("amount", amount)
                    .put("description", description)
                    .toString();
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    private String formatTarget(Date target) {
        return new SimpleDateFormat("yyyy-MM-dd").format(target);
    }

    private String formatCreatedOn() {
        SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        iso8601.setTimeZone(TimeZone.getDefault());
        return iso8601.format(newDate());
    }

    protected Date newDate() {
        return new Date();
    }

    protected UUID newUUID() {
        return UUID.randomUUID();
    }
}
