Feature: OrganizationGet
  Background:
    Given I am authenticated as admin
  Scenario: I want to get an organization by ID
    Given An organization is created
    When I get an organization by ID
    Then I should receive the organization

  Scenario: I want to fetch a non-existing organization by ID
    When I get an organization by ID:
      | identifier |
      | notARealID |
    Then I should receive null instead of an organization

  Scenario: A member reads its organization with the permissions every user holds by default
    Given An organization is created:
      | identifier | name       |
      | o_mine     | My Org Get |
    And A user is created:
      | identifier | memberOf |
      | u_me       | o_mine   |
    And I am authenticated as:
      | identifier |
      | u_me       |
    When I get an organization by ID:
      | identifier |
      | o_mine     |
    Then I should receive the organization

  Scenario: A token without im_organization_read_own cannot read its organization
    Given An organization is created:
      | identifier | name       |
      | o_mine     | My Org Get |
    And A user is created:
      | identifier | memberOf |
      | u_me       | o_mine   |
    And I am authenticated as:
      | identifier | withoutRoles             |
      | u_me       | im_organization_read_own |
    When I get an organization by ID:
      | identifier |
      | o_mine     |
    Then I should be forbidden to do so
