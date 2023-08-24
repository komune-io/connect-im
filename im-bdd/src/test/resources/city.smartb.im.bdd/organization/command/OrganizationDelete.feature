Feature: OrganizationDelete
  Background:
    Given I am logged in as an admin
  Scenario: I want to delete an organization
    Given An organization is created
    When I delete an organization
    Then The organization should be deleted