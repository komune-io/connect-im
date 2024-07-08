Feature: OrganizationRefGet
  Background:
    Given I am authenticated as admin
  Scenario: I want to get an organization ref by ID
    Given An organization is created
    When I get an organization ref by ID
    Then I should receive the organization ref

  Scenario: I want to fetch a non-existing organization by ID
    When I get an organization ref by ID:
      | identifier |
      | notARealID |
    Then I should receive null instead of an organization ref
