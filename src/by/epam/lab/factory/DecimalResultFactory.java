package by.epam.lab.factory;


import by.epam.lab.bean.DecimalResult;
import by.epam.lab.bean.Result;
import by.epam.lab.dao.ResultDao;
import by.epam.lab.dao.impl.ResultImplXml;
import by.epam.lab.exceptions.SourceException;
import by.epam.lab.util.Constants;

import java.sql.Date;

public class DecimalResultFactory extends ResultFactory {

    public Result getResultFromFactory(String login, String test, Date date, int mark) {
        return new DecimalResult(login, test, date, mark);
    }

    public Result getResultFromFactory(String login, String test, String date, String mark) {
        return new DecimalResult(login, test, date, mark);
    }

    public ResultDao getResultDaoFromFactory(String fileName, ResultFactory factory) throws SourceException {
        return new ResultImplXml(fileName, factory);
    }

    public double getRightAvgMark(double mark) {
        return mark / Constants.FORMAT_COEFFICIENT;
    }
}
