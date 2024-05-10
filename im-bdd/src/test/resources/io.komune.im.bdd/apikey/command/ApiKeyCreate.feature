Feature: ApiKeyCreate

  Background:
    Given I am authenticated as admin
    And An organization is created:
      | identifier | name   |
      | o1         | Komune |
    And Some roles are defined:
      | identifier | permissions     | targets |
      | r_writer   | im_apikey_write | USER    |
      | r_reader   | im_apikey_read  | USER    |

  Scenario: I want to create an API key
    Given A role is defined:
      | identifier | targets |
      | r1         | API_KEY |
    When I create an API key:
      | organization | name   | roles |
      | o1           | ze key | r1    |
    Then The API key should be created:
      | keyIdentifier            | organization | name   |
      | tr-komune-ze-key-api-key | o1           | ze key |

  Scenario: I want to create an API key with a secret
    Given A role is defined:
      | identifier | targets |
      | r1         | API_KEY |
    When I create an API key:
      | organization | name   | roles | secret|
      | o1           | ze key | r1    | verySecret |
    Then The API key should be created:
      | keyIdentifier            | organization | name   | secret     |
      | tr-komune-ze-key-api-key | o1           | ze key | verySecret |

  Scenario: I want to receive an error when creating an API key for an organization that does not exist
    When I create an API key:
      | organization |
      | fake         |
    Then An exception should be thrown:
      | code |
      | 404  |

  Scenario: I want to receive an error when assigning roles that does not exist an API key
    When I create an API key:
      | roles |
      | fake  |
    Then An exception should be thrown:
      | code |
      | 404  |

  Scenario: I want to receive an error when assigning roles without the API_KEY target to an API key
    Given A role is defined:
      | identifier | targets |
      | r1         | USER    |
    When I create an API key:
      | roles |
      | r1    |
    Then An exception should be thrown:
      | code |
      | 1000 |

  Scenario: I want to receive an error when creating an API key unauthenticated
    Given I am not authenticated
    When I create an API key
    Then I should be forbidden to do so

  Scenario: I want to be allowed to create an API key with the permission im_apikey_write
    Given A user is created:
      | identifier | roles    |
      | u_writer   | r_writer |
    And I am authenticated as:
      | identifier |
      | u_writer   |
    When I create an API key
    Then The API key should be created

  Scenario: I want to receive an error when creating an API key without the permission im_apikey_write
    Given A user is created:
      | identifier | roles    |
      | u_reader   | r_reader |
    And I am authenticated as:
      | identifier |
      | u_reader   |
    When I create an API key
    Then I should be forbidden to do so
