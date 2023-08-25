Feature: Role Crud

  Background:
    Given I am authenticated as admin

  Scenario: I want to create an role
    When I create a role
    Then The role should be created

  Scenario: I want to update a role
    Given I create a role:
      | description     |
      | The description |
    When I update the role:
      | description         |
      | The new description |
    Then The role should be updated
