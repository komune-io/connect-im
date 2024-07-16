Feature: FeatureDefine

  Background:
    Given I am authenticated as admin
    And Some roles are defined:
      | identifier | permissions   | targets |
      | r_writer   | im_role_write | USER    |
      | r_reader   | im_role_read  | USER    |

  Scenario: I want to create a feature
    When I define a feature
    Then The feature should be defined

  Scenario: I want to update a feature
    Given A feature is defined:
      | description     |
      | The description |
    When I define the feature:
      | description         |
      | The new description |
    Then The feature should be defined

  Scenario: I want to receive an error when defining a feature unauthenticated
    Given I am not authenticated
    When I define a feature
    Then I should be forbidden to do so

  Scenario: I want to be allowed to define a feature with the permission im_role_write
    Given A user is created:
      | identifier | roles    |
      | u_writer   | r_writer |
    And I am authenticated as:
      | identifier |
      | u_writer   |
    When I define a feature
    Then The feature should be defined

  Scenario: I want to receive an error when defining a feature without the permission im_role_write
    Given A user is created:
      | identifier | roles    |
      | u_reader   | r_reader |
    And I am authenticated as:
      | identifier |
      | u_reader   |
    When I define a feature
    Then I should be forbidden to do so
