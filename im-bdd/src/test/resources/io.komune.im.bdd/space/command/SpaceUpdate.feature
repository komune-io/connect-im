Feature: SpaceUpdate

  Background:
    Given I am authenticated as admin

  Scenario: I want a space updated with locales to offer them
    Given A space is created
    And The space has internationalization disabled
    When I update the space with locales "fr,en"
    Then The space should have default locale "fr" and locales "fr,en"

  # Keycloak returns the same set in one fixed order, so one of the two rows has its default last.
  Scenario Outline: I want a space to list its default locale first
    Given A space is created:
      | locales   |
      | <locales> |
    When I get the space
    Then The fetched space should list locales "<locales>"

    Examples:
      | locales |
      | fr,en   |
      | en,fr   |
