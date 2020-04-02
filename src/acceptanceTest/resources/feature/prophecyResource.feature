Feature: Testing the Prophecy API
  Clients should be able to create prophecies for prophets and retrieve them by prophetLogin, with the option to filter by date of creation

  Scenario: Create a Prophecy
    Given that exists a registered prophet with "pamidala" as login and "dbf624fe-55fc-4e4e-9df5-28a95e3a929a" as prophetCode
    When clients makes a POST request to "pamidala" prophecies with "Palpatine wasn't killed at Return of the Jedi" as summary and "He'll be back sooner than latter" as description
    Then a prophecy with "dbf624fe-55fc-4e4e-9df5-28a95e3a929a" as prophetCode, "Palpatine wasn't killed at Return of the Jedi" as summary and "He'll be back sooner than latter" as description should exist in the database
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
    
  Scenario: Retrieve all Prophecies from Prophet
    Given that exists a registered prophet with "jhutt" as login and "a4f3edc8-e024-4c07-994c-fe6c6e36ee9b" as prophetCode
    And that exists a stored prophecy with "a4f3edc8-e024-4c07-994c-fe6c6e36ee9b" as prophetCode, "2020-01-01T10:00:00.555" as prophecyTimestamp, "Prophecy 1 summary" as summary and "Prophecy 1 description" as description
    And that exists a stored prophecy with "a4f3edc8-e024-4c07-994c-fe6c6e36ee9b" as prophetCode, "2020-02-01T12:00:00.666" as prophecyTimestamp, "Prophecy 2 summary" as summary and "Prophecy 2 description" as description
    When clients makes a GET request to "jhutt" prophecies
    Then a 200 http response will be returned by the Prophecy resource
    And a prophecy with "a4f3edc8-e024-4c07-994c-fe6c6e36ee9b" as prophetCode, "2020-01-01T10:00:00.555" as prophecyTimestamp, "Prophecy 1 summary" as summary and "Prophecy 1 description" as description should be returned in the response body
    And a prophecy with "a4f3edc8-e024-4c07-994c-fe6c6e36ee9b" as prophetCode, "2020-02-01T12:00:00.666" as prophecyTimestamp, "Prophecy 2 summary" as summary and "Prophecy 2 description" as description should be returned in the response body

  Scenario: Retrieve empty Prophecies from Prophet
    Given that exists a registered prophet with "wantilles" as login and "0557b08f-77a1-4a51-b8e9-aa4f3755aced" as prophetCode
    When clients makes a GET request to "wantilles" prophecies
    Then a 200 http response will be returned by the Prophecy resource
    And a empty prophecy list should be returned in the response body

  Scenario: Retrieve Prophecies from Prophet with invalid login parameter
    When clients makes a GET request to "" prophecies
    Then a 400 http response will be returned by the Prophecy resource

  Scenario: Retrieve Prophecies from nonexistent Prophet
    When clients makes a GET request to "hsolo" prophecies
    Then a 404 http response will be returned by the Prophecy resource