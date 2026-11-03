Feature: UserGet

  Background:
    Given I am authenticated as admin

  Scenario: I want to get a user by ID
    Given A user is created
    When I get a user by ID
    Then I should receive the user

  Scenario: A user reads itself with the permissions every user holds by default
    Given A user is created:
      | identifier | memberOf |
      | u_self     | null     |
    And I am authenticated as:
      | identifier |
      | u_self     |
    When I get a user by ID
    Then I should receive the user

  Scenario: A token without im_user_self cannot read its own user
    Given A user is created:
      | identifier | memberOf |
      | u_self     | null     |
    And I am authenticated as:
      | identifier | withoutRoles |
      | u_self     | im_user_self |
    When I get a user by ID
    Then I should be forbidden to do so

  Scenario: A member reads a member of its organization with the permissions every user holds by default
    Given An organization is created:
      | identifier | name         |
      | o_mine     | My Org Users |
    And A user is created:
      | identifier  | memberOf | email                 |
      | u_colleague | o_mine   | colleague@test.com    |
    And A user is created:
      | identifier | memberOf | email          |
      | u_me       | o_mine   | me@test.com    |
    And I am authenticated as:
      | identifier |
      | u_me       |
    When I get a user by ID:
      | identifier  |
      | u_colleague |
    Then I should receive the user

  Scenario: A token without im_organization_read_own cannot read a member of its organization
    Given An organization is created:
      | identifier | name         |
      | o_mine     | My Org Users |
    And A user is created:
      | identifier  | memberOf | email                 |
      | u_colleague | o_mine   | colleague@test.com    |
    And A user is created:
      | identifier | memberOf | email          |
      | u_me       | o_mine   | me@test.com    |
    And I am authenticated as:
      | identifier | withoutRoles             |
      | u_me       | im_organization_read_own |
    When I get a user by ID:
      | identifier  |
      | u_colleague |
    Then I should be forbidden to do so
