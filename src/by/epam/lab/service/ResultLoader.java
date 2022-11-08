package by.epam.lab.service;

import by.epam.lab.bean.Result;
import by.epam.lab.dao.ResultDao;
import by.epam.lab.exceptions.ConnectionException;
import by.epam.lab.util.ConnectionProvider;
import by.epam.lab.util.Constants;

import java.sql.*;

import static by.epam.lab.util.DBConstants.*;

public class ResultLoader {

    public static void loadResults(ResultDao reader) throws ConnectionException {
        Connection cn = ConnectionProvider.getConnection();
        try (Statement st = cn.createStatement();
             PreparedStatement psSelectLogin = cn.prepareStatement(SELECT_LOGIN);
             PreparedStatement psInsertLogin = cn.prepareStatement(INSERT_LOGIN);
             PreparedStatement psSelectTest = cn.prepareStatement(SELECT_TEST);
             PreparedStatement psInsertTest = cn.prepareStatement(INSERT_TEST);
             PreparedStatement psInsertResult = cn.prepareStatement(SQL_INSERT_RESULTS)) {
            st.executeUpdate(SQL_TRUNCATE_RESULTS);
            while (reader.hasResult()) {
                Result result = reader.nextResult();
                int idLogin = getId(result.getLogin(), psSelectLogin, psInsertLogin);
                int idTest = getId(result.getTest(), psSelectTest, psInsertTest);
                psInsertResult.setInt(ID_LOGIN_DB_IND, idLogin);
                psInsertResult.setInt(ID_TEST_DB_IND, idTest);
                psInsertResult.setDate(DATE_DB_IND, result.getDate());
                psInsertResult.setInt(MARK_DB_IND, result.getMark());
                psInsertResult.addBatch();
            }
            psInsertResult.executeBatch();
        } catch (SQLException e) {
            throw new ConnectionException(Constants.ERROR_DATA_ERROR);
        }
    }

    private static int getId(String tableName, PreparedStatement psSelect, PreparedStatement psInsert) throws SQLException {
        int tableId;
        psSelect.setString(LOGIN_IND_DB, tableName);
        ResultSet resultSet = psSelect.executeQuery();
        if (resultSet.next()) {
            tableId = resultSet.getInt(LOGIN_IND_DB);
        } else {
            psInsert.setString(LOGIN_IND_DB, tableName);
            tableId = psInsert.executeUpdate();
        }
        return tableId;
    }
}
