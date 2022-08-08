#TODO create a user with a non existing organizationRef
Feature: User crud
  Scenario: I want to create a user
    When I create a user
    Then The user should be created

  Scenario: I want to create a user belonging to an organization
    Given An organization is created
    When I create a user
    Then The user should be created
