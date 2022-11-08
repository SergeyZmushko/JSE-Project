package by.epam.lab.util;

public class DBConstants {
    public final static String USER = "jse";
    public final static String PASSWORD = "jse";
    public final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/results";
    public final static int LOGIN_IND_DB = 1;
    public final static int ID_LOGIN_DB_IND = 1;
    public final static int ID_TEST_DB_IND = 2;
    public final static int DATE_DB_IND = 3;
    public final static int MARK_DB_IND = 4;
    public final static int TEST_IND_DB = 2;
    public final static int AVG_MARK_IND_DB = TEST_IND_DB;
    public final static String SQL_TRUNCATE_RESULTS = "truncate TABLE results";
    public final static String SQL_SELECT_AVG_MARK = "Select name, avg(mark) as avg\n" +
            "From logins, results\n" +
            "where logins.idLogin = results.loginId\n" +
            "group by loginId\n" +
            "order by avg DESC";
    public final static String SQL_SELECT_CURRENT_MONTH_ASC = "SELECT logins.name as login, tests.name as test, dat as date, mark\n" +
            "FROM logins, tests, results\n" +
            "Where logins.idLogin = results.loginId and\n" +
            "tests.idTest = results.testId and\n" +
            "month(results.dat) = month(now())and\n" +
            "year(results.dat) = year(now())\n" +
            "Order by dat Asc";
    public final static String MEAN_MARK = "Print mean mark";
    public final static String LAST_DAYS_RESULT = "Last day result";
    public final static String CURRENT_MONTH_TESTS = "Print tests on current month";
    public final static String NO_CURRENT_MONTH_DATA = "No data for current month";
    public final static String SELECT_LOGIN = "select idLogin from logins where name = ?";
    public final static String SELECT_TEST = "select idTest from tests where name = ?";
    public final static String INSERT_LOGIN = "insert into logins (name) value (?)";
    public final static String INSERT_TEST = "insert into tests (name) value (?)";
    public final static String SQL_INSERT_RESULTS = "INSERT INTO results (loginId, testId, dat, mark) values(?, ?, ?, ?)";


}
