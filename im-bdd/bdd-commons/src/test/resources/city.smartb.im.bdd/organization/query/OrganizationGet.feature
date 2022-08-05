Feature: OrganizationGet
  Scenario: I want to get an organization by its ID
    Given An organization is created
    When I get an organization by its ID
    Then I should receive the organization

  Scenario: I want to fetch a non-existing organization by its ID
    When I get an organization by its ID:
      | identifier |
      | notARealID |
    Then I should receive null instead of an organization
