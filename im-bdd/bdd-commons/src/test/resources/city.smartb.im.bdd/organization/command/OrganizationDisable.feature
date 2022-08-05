Feature: OrganizationDisable
  Scenario: I want to disable an organization
    Given An organization is created
    When I disable an organization
    Then The organization should be disabled