Feature: UserGetByEmail
  Background:
    Given I am logged in as an admin
  Scenario: I want to get a user by email
    Given A user is created:
      | memberOf |
      | null     |
    When I get a user by email
    Then I should receive the user

  Scenario: I want to fetch a non-existing user by email
    Given A user is created
    When I get a user by email:
      | email           |
      | wrong@email.com |
    Then I should receive null instead of a user
