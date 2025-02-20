Feature: Amazon Shopping Cart Functionality

  Scenario: Add cheapest Snickers and Skittles to the basket and verify checkout redirection
    Given user is on Amazon home page
    And the user deliver address is United States Min
    When the user searches and adds the cheapest items to the basket
      | Snickers |
      | Skittles |
#    Then the basket should display the correct total
#    And the user should be redirected to the registration page on checkout
