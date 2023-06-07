Feature: OrganizationManageApiKey
  Background:
    Given I am logged in as an admin
  Scenario: I want to add an api key on an organization
    Given An organization without apiKeys is created
    When I add an api key on the organization:
      | name     |
      | new name |
    Then The organization ApiKeys should contains the new apiKey

    When I remove the api key on the organization
    Then The organization ApiKeys should not contains the apiKey