Feature: UserUpdate

  Background:
    Given I am authenticated as admin
    And Some roles are defined:
      | identifier | permissions     | targets |
      | r_writer   | im_organization_write, im_organization_read, im_user_write, im_user_role_write | USER    |
      | r_reader   | im_organization_read  | USER    |

  Scenario: I want to not update the role of a user if not allowed
    Given A user is created:
      | identifier | roles    |
      | u_read   | r_reader |
    And I am authenticated as:
      | identifier |
      | u_read   |
    And I update a user:
      | identifier | roles        |
      | u_read     | r_writer     |
    Then The user should be updated:
      | identifier | roles        |
      | u_read     | r_reader     |

  Scenario: I want to update the role of a user if not allowed
    Given A user is created:
      | identifier | roles    |
      | u_read   | r_reader |
    And I update a user:
      | identifier | roles        |
      | u_read     | r_writer     |
    Then The user should be updated:
      | identifier | roles        |
      | u_read     | r_writer     |

