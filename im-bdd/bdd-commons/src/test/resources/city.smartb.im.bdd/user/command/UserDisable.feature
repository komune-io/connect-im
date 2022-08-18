Feature: UserDisable
  Scenario: I want to disable a user
    Given A user is created:
      | memberOf |
      | null     |
    When I disable a user
    Then The user should be disabled