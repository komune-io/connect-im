Feature: RoleGet

  Background:
    Given I am authenticated as admin

  Scenario: I want to get a role
    Given Some roles are defined:
      | identifier |
      | r1         |
      | r2         |
    Given A role is defined:
      | identifier | targets      | bindings                                      |
      | r3         | ORGANIZATION | { "USER": ["r1", "r2"], "API_KEY": ["r1", "r2"] } |
    When I get the role
    Then I should receive the role

  Scenario: I want to receive null when getting a non-existent role
    When I get the role:
      | identifier |
      | fake       |
    Then I should receive null instead of a role

  Scenario: I want to receive null when getting a privilege that is not a role
    Given A permission is defined:
      | identifier |
      | p1         |
    When I get the role:
      | identifier |
      | p1         |
    Then I should receive null instead of a role

  Scenario: I want to be forbidden from getting a role unauthenticated
    Given A role is defined
    And I am not authenticated
    When I get the role
    Then I should be forbidden to do so

  Scenario: I want to be allowed to get a role with the permission im_role_read
    Given A role is defined:
      | identifier | permissions   | targets |
      | r_reader   | im_role_read | USER    |
    And A user is created:
      | identifier | roles    |
      | u_reader   | r_reader |
    And I am authenticated as:
      | identifier |
      | u_reader   |
    When I get the role:
      | identifier |
      | r_reader   |
    Then I should receive the role

  Scenario: I want to be forbidden from getting a role without the permission im_role_read
    Given A role is defined:
      | identifier | permissions    | targets |
      | r_writer   | im_role_write | USER    |
    And A user is created:
      | identifier | roles    |
      | u_writer   | r_writer |
    And I am authenticated as:
      | identifier |
      | u_writer   |
    When I get the role:
      | identifier |
      | r_writer   |
    Then I should be forbidden to do so
