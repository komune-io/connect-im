Feature: UserCreate

  Background:
    Given I am authenticated as admin

  Scenario: I want to create a user
    Given A role is defined:
      | targets |
      | USER    |
    When I create a user
    Then The user should be created

  Scenario: I want to receive an error when creating two users with the same email
    Given A user is created:
      | email          |
      | user@email.com |
    When I create a user:
      | email          |
      | user@email.com |
    Then An exception should be thrown:
      | code |
      | 409  |
