package ru.netology.bdd.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ru.netology.bdd.data.DataHelper;
import ru.netology.bdd.page.DashBoardPage;
import ru.netology.bdd.page.LoginPage;
import ru.netology.bdd.page.TransferPage;
import ru.netology.bdd.page.VerificationPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.bdd.data.DataHelper.getCardIdInList;

public class TemplateSteps {
    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashBoardPage dashBoardPage;
    private static TransferPage transferPage;
    private static DataHelper dataHelper;


    @Given("login page is open at {string} in firefox browser")
    public void authPageRun(String url) {
        Configuration.browser = "firefox";
        loginPage = Selenide.open(url, LoginPage.class);
    }

    @When("the user login with name {string} and password {string}")
    public void loginWithNamePassword(String login, String password) {
        verificationPage = LoginPage.validLogin(new DataHelper.UserInfo(login, password));
    }

    @And("inputs verification code {string}")
    public void verificationCodeInput(String code) {
        dashBoardPage = verificationPage.validVerify(new DataHelper.VerCode(code));
    }

    @When("the user chooses to replenish the card")
    public void chooseCardToReplenish() {
        dashBoardPage.selectCardToTransfer(DataHelper.getCardIdInList());
    }

    @And("inputs {string} rub to transfer from one card to another")
    public void transferMoney(String amount) {
        transferPage.makeTransfer(Integer.parseInt(amount), getCardIdInList());
    }

    @Then("the balance of the card replenished must be {string} rub")
    public void getCardBalance(String balance) {
        var actualBalance = DashBoardPage.getCardBalance(getCardIdInList());
        var expectBalance = balance;
        assertEquals(expectBalance, actualBalance);
    }
}
