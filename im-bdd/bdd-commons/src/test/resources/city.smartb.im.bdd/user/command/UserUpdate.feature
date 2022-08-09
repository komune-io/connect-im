Feature: UserUpdate
  Scenario: I want to update a user with an existing organization
    Given An organization is created
    Given A user is created
    When I create an organization
    And I update a user
    Then The user should be updated

  Scenario: I want to update a user with a non-existing organization
    Given An organization is created
    Given A user is created
    When I update a user:
      | memberOf  |
      | notRealID |
    Then The user's organization should not be updated

  Scenario: I want to update the address of a user
    Given A user is created
    When I update a user:
      | street     | city     | postalCode     |
      | new street | new city | new postalCode |
    Then The user should be updated

  Scenario: I want to update the role of a user
    Given A role is created
    And A user is created
    When A role is created
    And I update a user
    Then The user should be updated

  Scenario: I want to update the job attribute of a user
    Given A user is created
    When I update a user:
      | job     |
      | new job |
    Then The user should be updated