Feature: UserResetPassword
  Background:
    Given I am authenticated as admin
  Scenario: I want to reset the password of a user
    Given A user is created:
      | memberOf |
      | null     |
    When I reset the password of a user
  # TODO Implement test SMTP to check if email was sent
