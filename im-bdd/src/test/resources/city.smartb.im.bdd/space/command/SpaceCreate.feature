Feature: SpaceCreate

  Background:
    Given I am authenticated as admin

  Scenario: I want to create a space
    When I create a space
    Then The space should be created

  Scenario: I want to receive an error when creating a space unauthenticated
    Given I am not authenticated
    When I create a space
    Then I should be forbidden to do so

  Scenario: I want to be allowed to create a space with the permission im_space_write
    Given A user is created:
      | identifier | roles          |
      | writer     | im_space_write |
    And I am authenticated as:
      | identifier |
      | writer     |
    When I create a space
    Then The space should be created

  Scenario: I want to receive an error when creating a space without the permission im_space_write
    Given A user is created:
      | identifier | roles         |
      | reader     | im_space_read |
    And I am authenticated as:
      | identifier |
      | reader     |
    When I create a space
    Then I should be forbidden to do so
