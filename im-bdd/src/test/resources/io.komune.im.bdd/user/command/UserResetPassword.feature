Feature: UserResetPassword
  Background:
    Given I am authenticated as admin
  Scenario: I want to reset the password of a user
    Given A user is created:
      | memberOf |
      | null     |
    When I reset the password of a user
  # TODO Implement test SMTP to check if email was sent

  Scenario: A user resets its own password with the permissions every user holds by default
    Given A user is created:
      | identifier | memberOf |
      | u_self     | null     |
    And I am authenticated as:
      | identifier |
      | u_self     |
    When I reset the password of a user
    Then I should not be forbidden to do so

  Scenario: A token without im_user_self cannot reset its own password, even without organization
    Given A user is created:
      | identifier | memberOf |
      | u_self     | null     |
    And I am authenticated as:
      | identifier | withoutRoles |
      | u_self     | im_user_self |
    When I reset the password of a user
    Then I should be forbidden to do so

  Scenario: A token without im_user_self cannot reset its own password in its organization
    Given An organization is created:
      | identifier | name          |
      | o_mine     | My Org Passwd |
    And A user is created:
      | identifier | memberOf |
      | u_self     | o_mine   |
    And I am authenticated as:
      | identifier | withoutRoles |
      | u_self     | im_user_self |
    When I reset the password of a user
    Then I should be forbidden to do so
