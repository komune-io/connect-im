Feature: OrganizationCreate

  Background:
    Given I am authenticated as admin

  Scenario: I want to create an organization
    Given A role is defined:
      | targets      |
      | ORGANIZATION |
    When I create an organization
    Then The organization should be created

  Scenario: I want to receive an error when creating two organizations with the same name
    Given An organization is created:
      | name        |
      | An org name |
    When I create an organization:
      | name        |
      | An org name |
    Then An exception should be thrown:
      | code |
      | 409  |

  Scenario: I want to receive an error when creating an organization with a role without the ORGANIZATION target
    Given A role is defined:
      | targets |
      | USER    |
    When I create an organization
    Then An exception should be thrown:
      | code |
      | 1000 |
