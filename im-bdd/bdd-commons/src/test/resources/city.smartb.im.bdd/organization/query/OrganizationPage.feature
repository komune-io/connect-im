Feature: OrganizationPage
  Background:
    Given I am logged in as an admin
  Scenario: I want to get a page of organizations
    Given Some organizations are created:
      | identifier |
      | org1       |
      | org2       |
    When I get a page of organizations
    Then I should receive a list of organizations:
      | identifier |
      | org1       |
      | org2       |