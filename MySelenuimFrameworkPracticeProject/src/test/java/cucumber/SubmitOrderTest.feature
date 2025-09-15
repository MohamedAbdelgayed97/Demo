@tag
Feature: Purchase the order from Ecommerce Website
  As a customer
  I want to purchase products from the Ecommerce Website
  So that I can confirm my order successfully

  Background:
    Given I landed on Ecommerce Page

  @Regression
  Scenario Outline: Positive Test of Submitting the order
    Given Logged in with username <name> and password <password>
    When I add product <productName> to Cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage

    Examples: 
      | name                  | password     | productName  |
      | rahulshetty@gmail.com | Iamking@000  | ZARA COAT 3  |