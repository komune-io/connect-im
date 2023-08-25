Feature: RoleGet

  Background:
    Given I am authenticated as admin

  Scenario: I want to get a role
    Given A role is defined
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

  Scenario: I want to be allowed to get a role with the permission im_read_role
    Given A role is defined:
      | identifier |
      | r1         |
    And A user is created:
      | identifier | roles        |
      | reader     | im_read_role |
    And I am authenticated as:
      | identifier |
      | reader     |
    When I get the role:
      | identifier |
      | r1         |
    Then I should receive the role

  Scenario: I want to be forbidden from getting a role without the permission im_read_role
    Given A role is defined:
      | identifier |
      | r1         |
    And A user is created:
      | identifier | roles         |
      | writer     | im_write_role |
    And I am authenticated as:
      | identifier |
      | writer     |
    When I get the role:
      | identifier |
      | r1         |
    Then I should be forbidden to do so
