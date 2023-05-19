Feature: UserDelete
  Background:
    Given I am logged in as an admin
  Scenario: I want to delete a user
    Given A user is created:
      | memberOf |
      | null     |
    When I delete a user
    Then The user should be deleted