package by.epam.lab.factory;

import by.epam.lab.dao.ResultDao;
import by.epam.lab.dao.impl.ResultImplCsv;
import by.epam.lab.bean.Result;
import by.epam.lab.exceptions.SourceException;

import java.sql.Date;

public class ResultFactory {
    public Result getResultFromFactory(String login, String test, Date date, int mark) {
        return new Result(login, test, date, mark);
    }

    public Result getResultFromFactory(String login, String test, String date, String mark) {
        return new Result(login, test, date, mark);
    }

    public ResultDao getResultDaoFromFactory(String fileName, ResultFactory factory) throws SourceException {
        return new ResultImplCsv(fileName, factory);
    }

    public double getRightAvgMark(double mark) {
        return mark;
    }


}
