Feature: UserUpdate

  Background:
    Given I am authenticated as admin

  Scenario: I want to update the name of a user
    Given A user is created:
      | name                 |
      | My Im Test User Name |
    When I update a user:
      | name                     |
      | My New Im Test User Name |
    Then The user should be updated

  Scenario: I want to update the role of a user
    Given A role is defined:
      | targets |
      | USER    |
    And A user is created
    When A role is defined:
      | targets |
      | USER    |
    And I update a user
    Then The user should be updated

Scenario: I want to update the organization of a user and lose the old org roles
    Given A role is defined:
      | identifier  | targets |
      | user_reader | USER    |
    Given A role is defined:
      | identifier | targets      | bindings                  |
      | reader     | ORGANIZATION | {"USER": ["user_reader"]} |
    Given An organization is created:
      | identifier    | name                | roles  |
      | reader_org    | READER ORGANIZATION | reader |
    Given A role is defined:
      | identifier     | targets |
      | user_validator | USER    |
    Given A role is defined:
      | identifier | targets      | bindings                     |
      | validator  | ORGANIZATION | {"USER": ["user_validator"]} |
    Given An organization is created:
      | identifier    | name                   | roles     |
      | validator_org | VALIDATOR ORGANIZATION | validator |
    Given A user is created:
      | memberOf   | roles       |
      | reader_org | user_reader |
    When I update a user:
      | memberOf     | roles                      |
      |validator_org |user_reader, user_validator |
    Then The user roles should be:
      | roles          |
      | user_validator |

  Scenario: I want to lose the user's roles that are not authorized in his organization's roles bindings when the organization's roles are updated
    Given A role is defined:
      | identifier   | targets |
      | user_default | USER    |
    Given A role is defined:
      | identifier  | targets |
      | user_reader | USER    |
    Given A role is defined:
      | identifier | targets      | bindings                                  |
      | reader     | ORGANIZATION | {"USER": ["user_reader", "user_default"]} |
    Given An organization is created:
      | identifier    | name                | roles  |
      | reader_org    | READER ORGANIZATION | reader |
    Given A user is created:
      | memberOf   | roles                     |
      | reader_org | user_default, user_reader |
    Given A role is defined:
      | identifier     | targets |
      | user_validator | USER    |
    Given A role is defined:
      | identifier | targets      | bindings                                     |
      | validator  | ORGANIZATION | {"USER": ["user_validator", "user_default"]} |
    When An organization is updated:
      | roles     |
      | validator |
    Then The user roles should be:
      | roles        |
      | user_default |
