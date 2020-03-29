Feature: Testing the Prophet API
  Clients should be able to create prophets by using POST request and retrieve then using a GET resource

  Scenario: Retrieving a Prophet
    Given that exists a registered prophet with "lskywalker" as login and "c25b1d8b-4246-408c-8521-937cf13a38be" as prophetCode
    When clients makes a GET request to Prophet resource passing "lskywalker" as login
    Then a 200 http response with a body containing a prophet with "lskywalker" as login and "c25b1d8b-4246-408c-8521-937cf13a38be" as code will be returned

  Scenario: Retrieving a nonexistent Prophet
    When clients makes a GET request to Prophet resource passing "hsolo" as login
    Then a 404 http response will be returned
    
  Scenario: Retrieving a Prophet with invalid parameter
    When clients makes a GET request to Prophet resource passing "" as login
    Then a 400 http response will be returned

  Scenario: Creating a Prophet
    When clients makes a POST request with "lorgana" as login
    Then a 200 http response will be returned
    And a prophet with login equals to "lorgana" should exist in the database

  Scenario: Creating a Prophet with invalid parameter
    When clients makes a POST request with "" as login
    Then a 400 http response will be returned

  Scenario: Creating an already existent Prophet
    Given that exists a registered prophet with "okenobi" as login and "64bf9ae2-37eb-4ad5-8060-5361fd763c46" as prophetCode
    When clients makes a POST request with "okenobi" as login
    Then a 409 http response will be returned
    And a prophet with login equals to "okenobi" should exist in the database