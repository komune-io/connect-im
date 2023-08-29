Feature: PermissionList

  Background:
    Given I am authenticated as admin

  Scenario: I want to list permissions
    Given Some permissions are defined:
      | identifier |
      | p1         |
      | p2         |
      | p3         |
      | p4         |
    When I list the permissions
    Then I should receive a list of permissions:
      | identifier |
      | p1         |
      | p2         |
      | p3         |
      | p4         |

  Scenario: I want to receive an empty list when there is no permission
    When I list the permissions
    Then I should receive an empty list of permissions

  Scenario: I want to receive an empty list when there are some privileges but no permission
    Given A role is defined:
      | identifier |
      | r1         |
    When I list the permissions
    Then I should receive an empty list of permissions

  Scenario: I want to be forbidden from listing permissions unauthenticated
    Given A permission is defined
    And I am not authenticated
    When I list the permissions
    Then I should be forbidden to do so

  Scenario: I want to be allowed to list permissions with the permission im_read_role
    Given A permission is defined:
      | identifier |
      | p1         |
    And A user is created:
      | identifier | roles        |
      | reader     | im_read_role |
    And I am authenticated as:
      | identifier |
      | reader     |
    When I list the permissions
    Then I should receive a list of permissions:
      | identifier |
      | p1         |

  Scenario: I want to be forbidden from listing permissions without the permission im_read_role
    Given A permission is defined
    And A user is created:
      | identifier | roles         |
      | writer     | im_write_role |
    And I am authenticated as:
      | identifier |
      | writer     |
    When I list the permissions
    Then I should be forbidden to do so
