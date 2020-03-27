Feature: Testing the Prophet API
  Clients should be able to create prophets by using POST request and retrieve then using a GET resource

  Scenario: Retrieving a Prophet
    Given that exists a registered prophet with lskywalker as login and c25b1d8b-4246-408c-8521-937cf13a38be as prophetCode
    When clients makes a GET request to /prophet/lskywalker
    Then the prophet lskywalker which code is c25b1d8b-4246-408c-8521-937cf13a38be will be returned