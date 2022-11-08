package by.epam.lab.util;

import by.epam.lab.exceptions.InitRuntimeException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static by.epam.lab.util.DBConstants.*;
import static by.epam.lab.util.Constants.*;

public class ConnectionProvider {

    private static final Connection INSTANCE = getInstance();

    private ConnectionProvider() {
    }

    public static Connection getInstance() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e);
            throw new InitRuntimeException(CONNECTION_ERROR);
        }
    }

    public static Connection getConnection() {
        return INSTANCE;
    }

    public static void closeConnection() {
        if (INSTANCE != null) {
            try {
                INSTANCE.close();
            } catch (SQLException e) {
                System.err.println(CLOSE_CONNECTION_ERROR);
            }
        }
    }
}
