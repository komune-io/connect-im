Feature: OrganizationCreate
  Scenario: I want to create an organization
    When I create an organization
    Then The organization should be created

  Scenario: I want to create two organizations with the same name
    Given An organization is created:
      | name        |
      | An org name |
    When I create an organization:
      | name        |
      | An org name |
    Then The creation has thrown an error
