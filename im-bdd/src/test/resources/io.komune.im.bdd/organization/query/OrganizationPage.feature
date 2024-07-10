Feature: OrganizationPage

  Background:
    Given I am authenticated as admin

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

  Scenario: I want to get the second page of organizations
    Given Some organizations are created:
      | identifier |
      | org1       |
      | org2       |
      | org3       |
      | org4       |
    When I get a page of organizations:
      | offset | limit |
      | 2      | 2     |
    Then I should receive a list of organizations:
      | identifier |
      | org2       |
      | org1       |

  Scenario: I want to get a page of organizations filtered by name
    Given Some organizations are created:
      | identifier | name |
      | org1       | aaaa |
      | org2       | zzzz |
    When I get a page of organizations:
      | name |
      | aa   |
    Then I should receive a list of organizations:
      | identifier |
      | org1       |
