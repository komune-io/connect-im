Feature: UserPage
  Background:
    Given I am logged in as an admin
  Scenario: I want to get a page of users
    Given Some users are created:
      | identifier | email          | memberOf |
      | org1       | user1@test.com | null     |
      | org2       | user2@test.com | null     |
    When I get a page of users:
      | organizationId |
      | null           |
    Then I should receive a list of users:
      | identifier | email          |
      | org1       | user1@test.com |
      | org2       | user2@test.com |