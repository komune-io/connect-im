#Feature: OrganizationGetFromInsee
#  Background:
#    Given I am logged in as an admin
#  Scenario: I want to get an organization by its siret number
#    When I get an organization by its siret number from Insee:
#      | siret          |
#      | 84488096300013 |
#    Then I should receive the organization from Insee
#
#  Scenario: I want to get an organization by a non-existing siret number
#    When I get an organization by its siret number from Insee:
#      | siret          |
#      | 12345678912345 |
#    Then I should not receive the organization from Insee