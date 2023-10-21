#language:en

Feature: Money transfer between bank accounts

  Scenario: successful authorization, verification and money transfer
    Given login page is open at "http://localhost:9999" in firefox browser
    When the user login with name "vasya" and password "qwerty123"
    And inputs verification code "12345"
    When the user chooses to replenish the card
    And inputs "5 000" rub to transfer from one card to another
    Then the balance of the card replenished must be "15 000" rub


