package ru.netology.data;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class DataHelper {
    private static final String correctUserName = "vasya";
    private static final String correctUserPass = "qwerty123";
    private static final String incorrectUserPass = "abra-kadabra";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/app-db";
    private static final String user = "user";
    private static final String password = "pass";

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getCorrectAuthInfo() {
        return new AuthInfo(correctUserName, correctUserPass);
    }

    public static AuthInfo getIncorrectAuthInfo() {
        return new AuthInfo(correctUserName, incorrectUserPass);
    }


    @SneakyThrows
    public static String getVerificationCode(String userName) {
        var codeSQL = "SELECT code FROM users " +
                "join auth_codes on users.id = auth_codes.user_id " +
                "where login = '" + userName + "' " +
                "ORDER BY created DESC LIMIT 1;";

        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection(dbUrl, user, password);
        ) {

            return runner.query(conn, codeSQL, new ScalarHandler<>());
        }
    }

    public static String getInvalidVerifyCode() {
        return "-8989368";
    }

    @SneakyThrows
    public static void clearTables() {
        var cardTransactionsTableClearQuery = "DELETE FROM card_transactions;";
        var authTableClearQuery = "DELETE FROM auth_codes;";
        var cardsTableClearQuery = "DELETE FROM cards;";
        var usersTableClearQuery = "DELETE FROM users;";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(dbUrl, user, password);
        ) {
            runner.update(conn, cardTransactionsTableClearQuery);
            runner.update(conn, authTableClearQuery);
            runner.update(conn, cardsTableClearQuery);
            runner.update(conn, usersTableClearQuery);
        }
    }
}
