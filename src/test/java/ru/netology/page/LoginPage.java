package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");

    public void fillPasswordField(DataHelper.AuthInfo userAuthInfo) {
        loginField.setValue(userAuthInfo.getLogin());
        passwordField.setValue(userAuthInfo.getPassword());
        loginButton.click();
    }

    public VerificationPage loginUser(DataHelper.AuthInfo userAuthInfo) {
        fillPasswordField(userAuthInfo);
        return new VerificationPage();
    }

    public void inputWrongLoginThreeTimes(DataHelper.AuthInfo userAuthInfo) {
        fillPasswordField(userAuthInfo);
        loginButton.click();
        loginButton.click();
        loginButton.click();
    }
}
