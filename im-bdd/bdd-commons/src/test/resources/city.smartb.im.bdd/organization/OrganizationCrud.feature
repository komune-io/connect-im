Feature: Organizaiton Crud

  Scenario: I want to create an organization
    When I create an organization
    Then The organization should be created

  Scenario: I want to update an organization
    Given I create an organization:
      | name |
      | My Im Test Organization Name |
    When I update the organization:
      | condition |
      | My New Im Test Organization Name |
    Then The organization should be updated