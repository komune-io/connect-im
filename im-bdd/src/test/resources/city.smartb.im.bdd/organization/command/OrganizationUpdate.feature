Feature: OrganizationUpdate
  Background:
    Given I am authenticated as admin
  Scenario: I want to update the name of an organization
    Given An organization is created:
      | name                         |
      | My Im Test Organization Name |
    When I update an organization:
      | name                             |
      | My New Im Test Organization Name |
    Then The organization should be updated

  Scenario: I want to update the role of an organization
    Given A role is defined
    And An organization is created
    When A role is defined
    And I update an organization
    Then The organization should be updated

  Scenario: I want to update the address of an organization
    Given An organization is created
    When I update an organization:
      | street     | city     | postalCode     |
      | new street | new city | new postalCode |
    Then The organization should be updated

  Scenario: I want to update the job attribute of an organization
    Given An organization is created
    When I update an organization:
      | job     |
      | new job |
    Then The organization should be updated
