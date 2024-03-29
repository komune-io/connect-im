Feature: UserUpdate

  Background:
    Given I am authenticated as admin

  Scenario: I want to update a user with an existing organization
    Given An organization is created
    Given A user is created
    When I create an organization
    And I update a user
    Then The user should be updated

  Scenario: I want to update the address of a user
    Given A user is created:
      | memberOf |
      | null     |
    When I update a user:
      | street     | city     | postalCode     | memberOf |
      | new street | new city | new postalCode | null     |
    Then The user should be updated

  Scenario: I want to update the role of a user
    Given A role is defined:
      | targets |
      | USER    |
    And A user is created:
      | memberOf |
      | null     |
    When A role is defined:
      | targets |
      | USER    |
    And I update a user:
      | memberOf |
      | null     |
    Then The user should be updated

  Scenario: I want to update the job attribute of a user
    Given A user is created:
      | memberOf |
      | null     |
    When I update a user:
      | job     | memberOf |
      | new job | null     |
    Then The user should be updated
