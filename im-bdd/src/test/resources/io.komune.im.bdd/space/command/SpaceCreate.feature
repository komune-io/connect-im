Feature: SpaceCreate

  Background:
    Given I am authenticated as admin

  Scenario: I want to create a space with default sslRequired
    When I create a space
    Then The space should be created
    And The space should have sslRequired "EXTERNAL"

  Scenario: I want to create a space with sslRequired NONE
    When I create a space:
      | sslRequired |
      | NONE        |
    Then The space should be created
    And The space should have sslRequired "NONE"

  Scenario: I want to create a space with sslRequired none (lowercase)
    When I create a space:
      | sslRequired |
      | none        |
    Then The space should be created
    And The space should have sslRequired "none"

  Scenario: I want to create a space with sslRequired ALL
    When I create a space:
      | sslRequired |
      | ALL         |
    Then The space should be created
    And The space should have sslRequired "ALL"
