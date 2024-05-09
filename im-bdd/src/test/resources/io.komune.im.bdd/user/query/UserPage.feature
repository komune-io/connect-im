Feature: UserPage

  Background:
    Given I am authenticated as admin

  Scenario: I want to get a page of users
    Given Some users are created:
      | identifier | email          | memberOf |
      | usr1       | user1@test.com | null     |
      | usr2       | user2@test.com | null     |
    When I get a page of users:
      | organizationId |
      | null           |
    Then I should receive a list of users:
      | identifier | email          |
      | usr1       | user1@test.com |
      | usr2       | user2@test.com |

  Scenario: I don't want to have API key users mixed-in when I get a page of users
    Given An organization is created:
      | identifier |
      | org1       |
    And Some users are created:
      | identifier | email          | memberOf |
      | usr1       | user1@test.com | org1     |
      | usr2       | user2@test.com | org1     |
    And Some API keys are created:
      | identifier | name | organization |
      | a1        | a1  | org1         |
      | a2        | a2  | org1         |
    When I get a page of users:
      | organizationId |
      | org1           |
    Then I should receive a list of users:
      | identifier | email          |
      | usr1       | user1@test.com |
      | usr2       | user2@test.com |
