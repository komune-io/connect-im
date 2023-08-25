Feature: OrganizationGet
  Background:
    Given I am authenticated as admin
  Scenario: I want to get an organization by ID
    Given An organization is created
    When I get an organization by ID
    Then I should receive the organization

  Scenario: I want to fetch a non-existing organization by ID
    When I get an organization by ID:
      | identifier |
      | notARealID |
    Then I should receive null instead of an organization
