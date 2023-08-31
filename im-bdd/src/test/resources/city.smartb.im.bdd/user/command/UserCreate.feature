Feature: UserCreate
  Background:
    Given I am authenticated as admin
  Scenario: I want to create a user not belonging to an organization
    When I create a user:
      | memberOf |
      | null     |
    Then The user should be created

  Scenario: I want to create a user belonging to an organization
    Given An organization is created
    When I create a user
    Then The user should be created

  Scenario: I want to create a user belonging to a non-existing organization
    When I create a user:
      | memberOf   |
      | notARealId |
    Then The user should not be created
    And An exception should be thrown:
      | code |
      | 404  |
