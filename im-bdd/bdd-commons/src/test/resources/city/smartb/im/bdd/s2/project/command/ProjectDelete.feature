Feature: ProjectDelete

  Scenario: I want to delete a project
    Given I create a project
    When I delete the project
    Then The project should be deleted
