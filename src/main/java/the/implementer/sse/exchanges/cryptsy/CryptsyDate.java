package the.implementer.sse.exchanges.cryptsy;

import the.implementer.sse.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CryptsyDate implements Value<LocalDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final LocalDateTime date;

    public CryptsyDate(String date) {
        this.date = LocalDateTime.parse(date, DATE_TIME_FORMATTER);
    }

    @Override
    public LocalDateTime get() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CryptsyDate that = (CryptsyDate) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return date != null ? date.hashCode() : 0;
    }

    @Override
    public String toString() {
        return DATE_TIME_FORMATTER.format(date);
    }
}
