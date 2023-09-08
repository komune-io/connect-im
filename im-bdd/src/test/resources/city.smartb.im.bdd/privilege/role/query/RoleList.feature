Feature: RoleList

  Background:
    Given I am authenticated as admin

  Scenario: I want to list roles
    Given Some roles are defined:
      | identifier | targets       |
      | p1         | ORGANIZATION  |
      | p2         | USER          |
      | p3         | USER, API_KEY |
      | p4         | ORGANIZATION  |
    When I list the roles:
      | target |
      | USER   |
    Then I should receive a list of roles:
      | identifier |
      | p2         |
      | p3         |

  Scenario: I want to receive an empty list when there is no role
    When I list the roles
    Then I should receive an empty list of roles

  Scenario: I want to receive an empty list when there are some privileges but no role
    Given A permission is defined:
      | identifier |
      | p1         |
    When I list the roles
    Then I should receive an empty list of roles

  Scenario: I want to be forbidden from listing roles unauthenticated
    Given A role is defined
    And I am not authenticated
    When I list the roles
    Then I should be forbidden to do so

  Scenario: I want to be allowed to list roles with the permission im_role_read
    Given A role is defined:
      | identifier | permissions   | targets |
      | r_reader   | im_role_read | USER    |
    And A user is created:
      | identifier | roles    |
      | u_reader   | r_reader |
    And I am authenticated as:
      | identifier |
      | u_reader   |
    When I list the roles
    Then I should receive a list of roles:
      | identifier |
      | r_reader   |

  Scenario: I want to be forbidden from listing roles without the permission im_role_read
    Given A role is defined:
      | identifier | permissions    | targets |
      | r_writer   | im_role_write | USER    |
    And A user is created:
      | identifier | roles    |
      | u_writer   | r_writer |
    And I am authenticated as:
      | identifier |
      | u_writer   |
    When I list the roles
    Then I should be forbidden to do so
