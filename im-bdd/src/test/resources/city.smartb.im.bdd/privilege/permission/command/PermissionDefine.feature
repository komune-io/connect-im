Feature: PermissionDefine

  Background:
    Given I am authenticated as admin

  Scenario: I want to create a permission
    When I define a permission
    Then The permission should be defined

  Scenario: I want to update a permission
    Given A permission is defined:
      | description     |
      | The description |
    When I define the permission:
      | description         |
      | The new description |
    Then The permission should be defined

  Scenario: I want to receive an error when defining a permission unauthenticated
    Given I am not authenticated
    When I define a permission
    Then I should be forbidden to do so

  Scenario: I want to be allowed to define a permission with the permission im_write_role
    Given A user is created:
      | identifier | roles         |
      | writer     | im_write_role |
    And I am authenticated as:
      | identifier |
      | writer     |
    When I define a permission
    Then The permission should be defined

  Scenario: I want to receive an error when defining a permission without the permission im_write_role
    Given A user is created:
      | identifier | roles        |
      | reader     | im_read_role |
    And I am authenticated as:
      | identifier |
      | reader     |
    When I define a permission
    Then I should be forbidden to do so
