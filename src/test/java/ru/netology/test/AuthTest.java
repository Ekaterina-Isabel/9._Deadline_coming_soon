package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    public static void delete() {
        DataHelper.clearTables();
    }

    @Test
    void shouldSuccessLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getCorrectAuthInfo();
        var verificationPage = loginPage.loginUser(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo.getLogin());
        verificationPage.inputVerifyCode(verificationCode);
    }

    @Test
    void shouldFailedLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getCorrectAuthInfo();
        var verificationPage = loginPage.loginUser(authInfo);
        var verificationCode = DataHelper.getInvalidVerifyCode();
        verificationPage.inputInvalidCode(verificationCode);
    }

    @Test
    void shouldBlockUserAccount() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getIncorrectAuthInfo();
        loginPage.inputWrongLoginThreeTimes(authInfo);
    }
}
