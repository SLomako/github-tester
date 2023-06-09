package com.github.tests.ui;

import com.github.config.GitHubConfig;
import com.github.config.ConfigurationManager;
import io.qameta.allure.*;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.github.helpers.annotations.LocalTestExtensions;
import com.github.tests.ui.pages.LoginPage;

import static io.qameta.allure.Allure.step;

@Owner("SLomako")
@Epic("Авторизация")
@Feature("UI: Авторизация")
@DisplayName("Авторизация")
public class LoginTest extends TestBase {

    private final GitHubConfig gitHubConfig = ConfigurationManager.getGitHubConfig();
    private final LoginPage loginPage = new LoginPage();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @LocalTestExtensions.LocalTest
    @DisplayName("Успешный вход в систему")
    void testSuccessfulLogin() {
        step("Открыть страницу входа", () ->
                loginPage.openLoginPage(testData.getUrlLoginPage()));

        step("Ввести логин", () ->
                loginPage.enterLogin(gitHubConfig.login()));

        step("Ввести пароль", () ->
                loginPage.enterPassword(gitHubConfig.password()));

        step("Нажать на кнопку входа", loginPage::clickLoginButton);

        step("Нажать на индикатор предварительного просмотра", loginPage::clickFeaturePreviewIndicator);

        step("Проверить имя пользователя", () ->
                Assertions.assertEquals(loginPage.getUsername(), gitHubConfig.login()));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @LocalTestExtensions.LocalTest
    @DisplayName("Вход в систему с неправильным email")
    void testLoginWithInvalidEmail() {
        step("Открыть страницу входа", () ->
                loginPage.openLoginPage(testData.getUrlLoginPage()));

        step("Ввести неправильный email", () ->
                loginPage.enterLogin("invalidEmail"));

        step("Ввести пароль", () ->
                loginPage.enterPassword(gitHubConfig.password()));

        step("Нажать на кнопку входа", loginPage::clickLoginButton);

        step("Проверить текст предупреждения", () ->
                Assertions.assertEquals(loginPage.getAlertText(), "Incorrect username or password."));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @LocalTestExtensions.LocalTest
    @DisplayName("Вход в систему с неправильным паролем")
    void testLoginWithInvalidPassword() {
        step("Открыть страницу входа", () ->
                loginPage.openLoginPage(testData.getUrlLoginPage()));

        step("Ввести логин", () ->
                loginPage.enterLogin(gitHubConfig.login()));

        step("Ввести неправильный пароль", () ->
                loginPage.enterPassword("InvalidPassword"));

        step("Нажать на кнопку входа", loginPage::clickLoginButton);

        step("Проверить текст предупреждения", () ->
                Assertions.assertEquals(loginPage.getAlertText(), "Incorrect username or password."));
    }
}
