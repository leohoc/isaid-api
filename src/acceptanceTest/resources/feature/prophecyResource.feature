Feature: Testing the Prophecy API
  Clients should be able to create prophecies for prophets and retrieve them by prophetCode, with the option to filter by date of creation

  Scenario: Create a Prophecy
    Given that exists a registered prophet with "lskywalker" as login and "c25b1d8b-4246-408c-8521-937cf13a38be" as prophetCode
    When clients makes a POST request to "lskywalker" prophecies with "Palpatine wasn't killed at Return of the Jedi" as summary and "He'll be back sooner than latter" as description
    Then a prophecy with "c25b1d8b-4246-408c-8521-937cf13a38be" as prophetCode, "Palpatine wasn't killed at Return of the Jedi" as summary and "He'll be back sooner than latter" as description should exist in the database
    And a 200 http response will be returned by the Prophecy resource

  Scenario: Create a Prophecy of a nonexistent Prophet
    When clients makes a POST request to "hsolo" prophecies with "I bet misconfigured bots will try this" as summary and "There'll be all sort of strange behavior" as description
    Then a 404 http response will be returned by the Prophecy resource

  Scenario: Create a Prophecy with invalid Prophet login
    When clients makes a POST request to "" prophecies with "I bet misconfigured bots will try this" as summary and "There'll be all sort of strange behavior" as description
    Then a 400 http response will be returned by the Prophecy resource

  Scenario: Create a Prophecy with invalid summary
    When clients makes a POST request to "hsolo" prophecies with "" as summary and "There'll be all sort of strange behavior" as description
    Then a 400 http response will be returned by the Prophecy resource

  Scenario: Create a Prophecy with invalid description
    When clients makes a POST request to "hsolo" prophecies with "I bet misconfigured bots will try this" as summary and "" as description
    Then a 400 http response will be returned by the Prophecy resource