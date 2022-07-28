Feature: EligibilityIsEligible

  Scenario: I don't want to be eligible when no eligibility has been defined for my category code
    Given An eligibility is created:
      | legalCategory3Code | condition |
      | blblbl             | ALWAYS    |
    When I check my eligibility:
      | legalCategory3Code |
      | fake               |
    Then I should not be eligible

  Scenario: I want to always be eligible
    Given An eligibility is created:
      | legalCategory3Code | condition |
      | blblbl             | ALWAYS    |
    When I check my eligibility:
      | legalCategory3Code |
      | blblbl             |
    Then I should be eligible

  Scenario: I want to never be eligible
    Given An eligibility is created:
      | legalCategory3Code | condition |
      | blblbl             | NEVER     |
    When I check my eligibility:
      | legalCategory3Code |
      | blblbl             |
    Then I should not be eligible

  Scenario: I want to be eligible according to my NAF code
    Given An eligibility is created:
      | legalCategory3Code | condition | conditionData |
      | blblbl             | NAF       | 12.34Z, 12.34 |
    When I check my eligibility:
      | legalCategory3Code | naf    |
      | blblbl             | 12.34Z |
    Then I should be eligible

  Scenario: I don't want to be eligible according to my NAF code
    Given An eligibility is created:
      | legalCategory3Code | condition | conditionData |
      | blblbl             | NAF       | 12.34Z        |
    When I check my eligibility:
      | legalCategory3Code | naf |
      | blblbl             | 12  |
    Then I should not be eligible

  Scenario: I want to be eligible according to my address
    Given An eligibility is created:
      | legalCategory3Code | condition | conditionData          |
      | blblbl             | ADDRESS   | Velo City, Montpellier |
    When I check my eligibility:
      | legalCategory3Code | address   |
      | blblbl             | Velo City |
    Then I should be eligible

  Scenario: I don't want to be eligible according to my address
    Given An eligibility is created:
      | legalCategory3Code | condition | conditionData |
      | blblbl             | NAF       | Velo City     |
    When I check my eligibility:
      | legalCategory3Code | address |
      | blblbl             | nowhere |
    Then I should not be eligible
