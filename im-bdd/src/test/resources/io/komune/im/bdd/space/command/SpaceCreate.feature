Feature: SpaceCreate

  Background:
    Given I am authenticated as admin
    And Some roles are defined:
      | identifier | permissions     | targets |
      | r_writer   | im_space_write | USER    |
      | r_reader   | im_space_read  | USER    |

  Scenario: I want to create a space
    When I create a space
    Then The space should be created

  Scenario: I want to receive an error when creating a space unauthenticated
    Given I am not authenticated
    When I create a space
    Then I should be forbidden to do so

  Scenario: I want to be allowed to create a space with the permission im_space_write
    Given A user is created:
      | identifier | roles    |
      | u_writer   | r_writer |
    And I am authenticated as:
      | identifier |
      | u_writer   |
    When I create a space
    Then The space should be created

  Scenario: I want to receive an error when creating a space without the permission im_space_write
    Given A user is created:
      | identifier | roles    |
      | u_reader   | r_reader |
    And I am authenticated as:
      | identifier |
      | u_reader   |
    When I create a space
    Then I should be forbidden to do so
