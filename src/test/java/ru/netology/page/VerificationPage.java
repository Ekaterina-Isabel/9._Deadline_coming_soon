package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final String needConfirmationText = "Необходимо подтверждение";
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement header = $(".heading");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification]");


    public void inputVerifyCode(String verificationCode) {
        $(withText(needConfirmationText)).shouldBe(Condition.visible);
        codeField.setValue(verificationCode);
        verifyButton.click();
        header.shouldBe(Condition.text(" Личный кабинет"));
    }

    public void inputInvalidCode(String verificationCode) {
        $(withText(needConfirmationText)).shouldBe(Condition.visible);
        codeField.setValue(verificationCode);
        verifyButton.click();
        errorNotification.shouldHave(Condition.text("Неверно указан код"));
    }
}
