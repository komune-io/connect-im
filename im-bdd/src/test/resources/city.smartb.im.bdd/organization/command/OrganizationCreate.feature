# TODO Test create an organization with a parentOrganizationId that is not existing
Feature: OrganizationCreate

  Background:
    Given I am authenticated as admin

  Scenario: I want to create an organization
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
