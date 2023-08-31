Feature: RoleDefine

  Background:
    Given I am authenticated as admin

  Scenario: I want to create a role
    Given I am authenticated as admin
    When I define a role:
      | identifier | permissions                                      | targets | locale                          |
      | im_reader  | im_user_read, im_organization_read, im_role_read | USER    | { en: "Reader", fr: "Lecteur" } |
    Then The role should be defined

  Scenario: I want to update a role
    Given A role is defined:
      | identifier | description                  | target | permissions                        |
      | im_reader  | Read users and organizations | USER   | im_user_read, im_organization_read |
    When I define the role:
      | identifier | description          | target             | permissions                |
      | im_reader  | Read users and roles | USER, ORGANIZATION | im_user_read, im_role_read |
    Then The role should be defined

  Scenario: I want to receive an error when defining a role unauthenticated
    Given I am not authenticated
    When I define a role
    Then I should be forbidden to do so

  Scenario: I want to be allowed to define a role with the permission im_role_write
    Given A user is created:
      | identifier | roles         |
      | writer     | im_role_write |
    And I am authenticated as:
      | identifier |
      | writer     |
    When I define a role
    Then The role should be defined

  Scenario: I want to receive an error when defining a role without the permission im_role_write
    Given A user is created:
      | identifier | roles        |
      | writer     | im_role_read |
    And I am authenticated as:
      | identifier |
      | writer     |
    When I define a role
    Then I should be forbidden to do so
