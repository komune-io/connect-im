Feature: UserUpdateEmail
  Background:
    Given I am logged in as an admin
  Scenario: I want to update the email of a user
    Given A user is created:
      | memberOf |
      | null     |
    When I update the email of a user:
      | memberOf |
      | null     |
    Then The user's email should be updated
