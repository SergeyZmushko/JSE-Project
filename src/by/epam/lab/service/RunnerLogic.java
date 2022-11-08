package by.epam.lab.service;

import by.epam.lab.dao.ResultDao;
import by.epam.lab.exceptions.ConnectionException;
import by.epam.lab.exceptions.LoadRuntimeException;
import by.epam.lab.exceptions.SourceException;
import by.epam.lab.util.ConnectionProvider;
import by.epam.lab.bean.Result;
import by.epam.lab.factory.ResultFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static by.epam.lab.util.Constants.*;
import static by.epam.lab.util.DBConstants.*;

public class RunnerLogic {

    public static void execute(ResultFactory resultFactory, String fileName) {
        try {
            loadResults(fileName, resultFactory);
            printAvgMarks(resultFactory);
            printCurrentMonthTests(resultFactory);
            printLastDayResults(resultFactory);
        } finally {
            ConnectionProvider.closeConnection();
        }
    }

    private static void loadResults(String fileName, ResultFactory factory) {
        try (ResultDao reader = factory.getResultDaoFromFactory(fileName, factory)) {
            ResultLoader.loadResults(reader);
        } catch (SourceException e) {
            System.err.println(SOURCE_ERROR);
        } catch (ConnectionException e) {
            throw new LoadRuntimeException(LOAD_DB_ERROR);
        } catch (IOException e) {
            System.err.println(IO_ERROR);
        }
    }

    private static void printLastDayResults(ResultFactory resultFactory) {
        try {
            List<Result> resultList = getCurrentMonthResults(resultFactory);
            if (!resultList.isEmpty()) {
                System.out.println(LAST_DAYS_RESULT);
                ListIterator<Result> it = resultList.listIterator(resultList.size());
                Result lastResult = it.previous();
                System.out.println(lastResult);
                while (it.hasPrevious()) {
                    Result currentResult = it.previous();
                    if (!lastResult.getDate().equals(currentResult.getDate())) {
                        return;
                    }
                    System.out.println(currentResult);
                }
            } else {
                System.out.println(NO_CURRENT_MONTH_DATA);
            }
        } catch (SQLException e) {
            System.err.println(CURRENT_MONTH_RESULT_ERROR);
        }
    }

    private static void printCurrentMonthTests(ResultFactory resultFactory) {
        try {
            List<Result> resultList = getCurrentMonthResults(resultFactory);
            if (!resultList.isEmpty()) {
                System.out.println(CURRENT_MONTH_TESTS);
                for (Result result : resultList) {
                    System.out.println(result);
                }
            } else {
                System.out.println(NO_CURRENT_MONTH_DATA);
            }
        } catch (SQLException e) {
            System.err.println(CURRENT_MONTH_RESULT_ERROR);
        }

    }

    private static List<Result> getCurrentMonthResults(ResultFactory resultFactory) throws SQLException {
        List<Result> results = new LinkedList<>();
        try (Statement statement = ConnectionProvider.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(SQL_SELECT_CURRENT_MONTH_ASC)) {
            while (rs.next()) {
                Result result = resultFactory.getResultFromFactory(rs.getString(LOGIN), rs.getString(TEST),
                        rs.getDate(DATE), rs.getInt(MARK));
                results.add(result);
            }
            return results;
        }
    }

    private static void printAvgMarks(ResultFactory resultFactory) {
        try (Statement statement = ConnectionProvider.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(SQL_SELECT_AVG_MARK)) {
            System.out.println(MEAN_MARK);
            while (rs.next()) {
                System.out.println(String.format(FORMAT_STRING_MEAN_MARK, rs.getString(LOGIN_IND_DB),
                        resultFactory.getRightAvgMark(rs.getDouble(AVG_MARK_IND_DB))).replace(COMMA, DOT));
            }
        } catch (SQLException e) {
            System.err.println(AVG_ERROR + e.getMessage());
        }
    }
}

