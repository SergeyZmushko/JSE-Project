package by.epam.lab.bean;

import java.sql.Date;

import static by.epam.lab.util.Constants.*;
import static by.epam.lab.util.Constants.REPLACEMENT;

public class HalfResult extends Result {
    public HalfResult(String login, String test, Date date, int mark) {
        super(login, test, date, mark);
    }

    public HalfResult(String login, String test, String date, String mark) {
        super(login, test, date, String.valueOf((int) (Double.parseDouble(mark) * FORMAT_COEFFICIENT)));
    }

    protected String markToString() {
        return String.format(MARK_FORMAT, getMark() / FORMAT_COEFFICIENT, getMark() % FORMAT_COEFFICIENT)
                .replaceAll(REPLACE_REGEX, REPLACEMENT);
    }
}
