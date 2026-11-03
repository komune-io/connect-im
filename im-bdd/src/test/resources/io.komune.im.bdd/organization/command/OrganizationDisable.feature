Feature: OrganizationDisable
  Background:
    Given I am authenticated as admin
  Scenario: I want to disable an organization
    Given An organization is created
    When I disable an organization
    Then The organization should be disabled

  Scenario: I want to disable only the enabled users of an organization, and leave a user disabled before as it was
    Given An organization is created:
      | identifier  |
      | cascade_org |
    Given Some users are created:
      | identifier      | email                    | memberOf    |
      | active          | active@cascade.test      | cascade_org |
      | disabled_before | disabled@cascade.test    | cascade_org |
    Given A user is disabled:
      | identifier      | disabledBy |
      | disabled_before | manager    |
    When I disable an organization:
      | identifier  | disabledBy |
      | cascade_org | cascade    |
    Then The organization should be disabled
    And The organization disable should have disabled only the users:
      | identifier |
      | active     |
    And The users should be disabled by:
      | identifier      | disabledBy |
      | active          | cascade    |
      | disabled_before | manager    |
