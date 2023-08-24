Feature: UserDisable
  Background:
    Given I am logged in as an admin
  Scenario: I want to disable a user
    Given A user is created:
      | memberOf |
      | null     |
    When I disable a user
    Then The user should be disabled