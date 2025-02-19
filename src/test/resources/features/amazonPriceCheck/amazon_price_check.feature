Feature: Amazon Shopping Cart Functionality

  Scenario: Add cheapest Snickers and Skittles to the basket and verify checkout redirection
    Given user is on Amazon home page
    And the user deliver address is United States Min
    When the user searches for Snickers
    And the user adds the cheapest Snickers to the basket
#    And the user searches for "Skittles"
#    And the user adds the cheapest item to the basket
#    Then the basket should display the correct total
#    And the user should be redirected to the registration page on checkout
