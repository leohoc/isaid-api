Feature: Testing the Prophet API
  Clients should be able to create prophets by using POST request and retrieve then using a GET resource

  Scenario: Retrieving a Prophet
    Given that exists a registered prophet with "lskywalker" as login and "c25b1d8b-4246-408c-8521-937cf13a38be" as prophetCode
    When clients makes a GET request to Prophet resource passing "lskywalker" as login
    Then the prophet "lskywalker" which code is "c25b1d8b-4246-408c-8521-937cf13a38be" will be returned

  Scenario: Retrieving a nonexistent Prophet
    When clients makes a GET request to Prophet resource passing "hsolo" as login
    Then no prophet should be returned
    
  Scenario: Retrieving a Prophet with invalid parameter
    When clients makes a GET request to Prophet resource passing "" as login
    Then a exception with the message "login cannot be null or an empty string" should be throwed

  Scenario: Creating a Prophet
    When clients makes a POST request with "lorgana" as login
    Then a prophet with login equals to "lorgana" should exist in the database

  Scenario: Creating a Prophet with invalid parameter
    When clients makes a POST request with "" as login
    Then a exception with the message "login cannot be null or an empty string" should be throwed

  Scenario: Creating an already existent Prophet
    Given that exists a registered prophet with "okenobi" as login and "64bf9ae2-37eb-4ad5-8060-5361fd763c46" as prophetCode
    When clients makes a POST request with "okenobi" as login
    Then a prophet with login equals to "okenobi" should exist in the database