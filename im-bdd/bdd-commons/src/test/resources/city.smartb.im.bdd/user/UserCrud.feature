Feature: User crud

  Scenario: I want to create a user
    When I create a user
    Then The user should be created

  Scenario: I want to update an eligibility
    Given I create an user:
      | name |
      | My Im User Name    |
    When I update the user:
      | condition |
      | My New Im User Name     |
    Then The user should be updated
