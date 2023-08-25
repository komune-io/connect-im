Feature: UserGet
  Background:
    Given I am authenticated as admin
  Scenario: I want to get a user by ID
    Given A user is created:
      | memberOf |
      | null     |
    When I get a user by ID
    Then I should receive the user

  Scenario: I want to fetch a non-existing user by ID
    When I get a user by ID:
      | identifier |
      | notARealID |
    Then I should receive null instead of a user
