
@tag
Feature: Error validation message test

  @ErrorValidation
  Scenario: Try sign in with invalid credentials
		Given I landed on Ecommerce Page
    Given Logged in with username <name> and password <password>
    Then  "Incorrect email or password." message is displayed
  

    Examples: 
       | name  								|  password		    |
      | rahulshetty@gmail.com |  Iamking@0		|