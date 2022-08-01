Feature: EligibilityUpdate

  Scenario: I want to update an eligibility
    Given I create an eligibility:
      | condition |
      | ALWAYS    |
    When I update the eligibility:
      | condition |
      | NEVER     |
    Then The eligibility should be updated
