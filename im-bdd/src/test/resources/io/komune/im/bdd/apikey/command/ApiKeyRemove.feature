Feature: ApiKeyRemove

  Background:
    Given I am authenticated as admin
    And An organization is created:
      | identifier | name   |
      | o1         | Komune |
    And Some roles are defined:
      | identifier | permissions     | targets |
      | r_writer   | im_apikey_write | USER    |
      | r_reader   | im_apikey_read  | USER    |
    And An API key is created

  Scenario: I want to remove an API key
    When I remove an API key
    Then The API key should be removed

  Scenario: I want to receive an error when removing an API key that does not exist
    When I remove an API key:
      | identifier |
      | fake       |
    Then An exception should be thrown:
      | code |
      | 404  |

  Scenario: I want to receive an error when removing an API key unauthenticated
    Given I am not authenticated
    When I remove an API key
    Then I should be forbidden to do so

  Scenario: I want to be allowed to remove an API key with the permission im_apikey_write
    Given A user is created:
      | identifier | roles    |
      | u_writer   | r_writer |
    And I am authenticated as:
      | identifier |
      | u_writer   |
    When I remove an API key
    Then The API key should be removed

  Scenario: I want to receive an error when removing an API key without the permission im_apikey_write
    Given A user is created:
      | identifier | roles    |
      | u_reader   | r_reader |
    And I am authenticated as:
      | identifier |
      | u_reader   |
    When I remove an API key
    Then I should be forbidden to do so
