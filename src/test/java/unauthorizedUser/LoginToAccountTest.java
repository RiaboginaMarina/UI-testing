package unauthorizedUser;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import stellarBurger.api.User;
import stellarBurger.api.UserClient;
import stellarBurger.pages.LoginPage;
import stellarBurger.pages.MainPageBurgers;
import stellarBurger.pages.RegistrationFormPage;
import stellarBurger.pages.RestorePasswordPage;

import static org.junit.Assert.assertEquals;


public class LoginToAccountTest {
    private final UserClient client = new UserClient();
    private final String email = RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru";
    private final String password = RandomStringUtils.randomAlphanumeric(10);
    private final String name = RandomStringUtils.randomAlphabetic(10);
    private final String expectedChangedTextOnLoginButton = "Оформить заказ";
    private final User user = new User(email, password, name);
    @Rule
    public DriverRule driverRule = new DriverRule();
    private MainPageBurgers mainPage;

    @Before
    public void setUp() {
        client.createNewUser(user);
        mainPage = new MainPageBurgers(driverRule.getDriver());
        mainPage.open();
    }

    @After
    public void deleteUser() {
        LocalStorage localStorage = ((WebStorage) driverRule.getDriver()).getLocalStorage();
        String accessToken = localStorage.getItem("accessToken");
        client.delete(accessToken);
    }

    @Test
    public void loginToAccountUsingLogInButtonOnMainPage() {
        LoginPage loginPage = mainPage.clickLogInButton();
        loginPage.waitForLoadLoginPage()
                .fillLoginForm(email, password)
                .clickLogInButton();

        mainPage.waitForLoadMainPage();
        String actualTextOnLoginButtonWhenLoggedIn = mainPage.getTextFromLoginButton();
        assertEquals(expectedChangedTextOnLoginButton, actualTextOnLoginButtonWhenLoggedIn);
    }

    @Test
    public void loginToAccountUsingAccountButtonOnMainPage() {
        LoginPage loginPage = mainPage.clickAccountButtonWithoutLoggedUser();
        loginPage.waitForLoadLoginPage()
                .fillLoginForm(email, password)
                .clickLogInButton();

        mainPage.waitForLoadMainPage();
        String actualTextOnLoginButtonWhenLoggedIn = mainPage.getTextFromLoginButton();
        assertEquals(expectedChangedTextOnLoginButton, actualTextOnLoginButtonWhenLoggedIn);
    }

    @Test
    public void loginToAccountUsingLoginLinkInRegistrationForm() {
        LoginPage loginPage = mainPage.clickLogInButton();
        loginPage.waitForLoadLoginPage();

        RegistrationFormPage registerPage = loginPage.clickSignUpLink();
        registerPage.waitForLoadRegistrationForm()
                .clickLoginLink();

        loginPage.waitForLoadLoginPage()
                .fillLoginForm(email, password)
                .clickLogInButton();

        mainPage.waitForLoadMainPage();
        String actualTextOnLoginButtonWhenLoggedIn = mainPage.getTextFromLoginButton();
        assertEquals(expectedChangedTextOnLoginButton, actualTextOnLoginButtonWhenLoggedIn);
    }

    @Test
    public void loginToAccountUsingLoginLinkOnRestorePasswordPage() {
        LoginPage loginPage = mainPage.clickLogInButton();
        loginPage.waitForLoadLoginPage();

        RestorePasswordPage passwordPage = loginPage.clickResetPasswordLink();
        passwordPage.waitForLoadRestorePasswordPage()
                .clickLoginLink();

        loginPage.waitForLoadLoginPage()
                .fillLoginForm(email, password)
                .clickLogInButton();

        mainPage.waitForLoadMainPage();
        String actualTextOnLoginButtonWhenLoggedIn = mainPage.getTextFromLoginButton();
        assertEquals(expectedChangedTextOnLoginButton, actualTextOnLoginButtonWhenLoggedIn);
    }
}
