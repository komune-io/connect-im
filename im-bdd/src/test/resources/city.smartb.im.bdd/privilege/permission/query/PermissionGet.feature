Feature: PermissionGet

  Background:
    Given I am authenticated as admin

  Scenario: I want to get a permission
    Given A permission is defined
    When I get the permission
    Then I should receive the permission

  Scenario: I want to receive null when getting a non-existent permission
    When I get the permission:
      | identifier |
      | fake       |
    Then I should receive null instead of a permission

  Scenario: I want to receive null when getting a privilege that is not a permission
    Given A role is defined:
      | identifier |
      | r1         |
    When I get the permission:
      | identifier |
      | r1         |
    Then I should receive null instead of a permission

  Scenario: I want to be forbidden from getting a permission unauthenticated
    Given A permission is defined
    And I am not authenticated
    When I get the permission
    Then I should be forbidden to do so

  Scenario: I want to be allowed to get a permission with the permission im_role_read
    Given A permission is defined
    And A user is created:
      | identifier | roles        |
      | reader     | im_role_read |
    And I am authenticated as:
      | identifier |
      | reader     |
    When I get the permission
    Then I should receive the permission

  Scenario: I want to be forbidden from getting a permission without the permission im_role_read
    Given A permission is defined
    And A user is created:
      | identifier | roles         |
      | writer     | im_role_write |
    And I am authenticated as:
      | identifier |
      | writer     |
    When I get the permission
    Then I should be forbidden to do so
