Feature: Testing the Prophecy API
  Clients should be able to create prophecies for prophets and retrieve them by prophetLogin, with the option to filter by date of creation

  Background:

    Given the following prophets exists:
      | login     | prophetCode                          |
      | pamidala  | dbf624fe-55fc-4e4e-9df5-28a95e3a929a |
      | jhutt     | a4f3edc8-e024-4c07-994c-fe6c6e36ee9b |
      | wantilles | 0557b08f-77a1-4a51-b8e9-aa4f3755aced |
      | dsidious  | ce00e12f-3b38-4f5f-8a21-a0e0b49635e0 |
      | qjinn     | f0fa9988-b2f2-47a6-a8e7-e00f560b2307 |
      | jbinks    | f1f040d2-cc83-4dba-8625-93a653b2a490 |

    Given the following prophecies exists:
      | prophetCode                          | prophecyTimestamp       | summary                    | description            |
      | a4f3edc8-e024-4c07-994c-fe6c6e36ee9b | 2020-01-01T10:00:00.555 | Jabba prophecy 1 summary   | Prophecy 1 description |
      | a4f3edc8-e024-4c07-994c-fe6c6e36ee9b | 2020-02-01T12:00:00.666 | Jabba prophecy 2 summary   | Prophecy 2 description |
      | ce00e12f-3b38-4f5f-8a21-a0e0b49635e0 | 2020-04-01T10:00:00.555 | Sidious prophecy 1 summary | Prophecy 1 description |
      | ce00e12f-3b38-4f5f-8a21-a0e0b49635e0 | 2020-04-01T12:00:00.666 | Sidious prophecy 2 summary | Prophecy 2 description |
      | ce00e12f-3b38-4f5f-8a21-a0e0b49635e0 | 2020-04-01T15:00:00.777 | Sidious prophecy 3 summary | Prophecy 3 description |
      | f0fa9988-b2f2-47a6-a8e7-e00f560b2307 | 2020-04-01T10:00:00.555 | Qui Gon prophecy 1 summary | Prophecy 1 description |
      | f0fa9988-b2f2-47a6-a8e7-e00f560b2307 | 2020-04-01T12:00:00.666 | Qui Gon prophecy 2 summary | Prophecy 2 description |
      | f0fa9988-b2f2-47a6-a8e7-e00f560b2307 | 2020-04-01T15:00:00.777 | Qui Gon prophecy 3 summary | Prophecy 3 description |
      | f1f040d2-cc83-4dba-8625-93a653b2a490 | 2020-04-01T10:00:00.555 | Jar Jar prophecy 1 summary | Prophecy 1 description |
      | f1f040d2-cc83-4dba-8625-93a653b2a490 | 2020-04-01T12:00:00.666 | Jar Jar prophecy 2 summary | Prophecy 2 description |
      | f1f040d2-cc83-4dba-8625-93a653b2a490 | 2020-04-01T15:00:00.777 | Jar Jar prophecy 3 summary | Prophecy 3 description |

  Scenario Outline: Creating Prophecies
    When clients makes a POST request to the Prophecy resource with "/prophets/<prophetLogin>/prophecies" uri and "{ \"summary\":\"<summary>\", \"description\":\"<description>\" }" in the body
    Then a <httpResponseCode> http response will be returned by the Prophecy resource
    And <shouldSearch> the database for a prophecy with "<prophetCode>", "<summary>" and "<description>"

    Examples:
      | prophetLogin | prophetCode                          | summary                                       | description                              | httpResponseCode | shouldSearch |
      | pamidala     | dbf624fe-55fc-4e4e-9df5-28a95e3a929a | Palpatine wasn't killed at Return of the Jedi | He'll be back sooner than latter         | 200              | true         |
      | hsolo        |                                      | I bet misconfigured bots will try this        | There'll be all sort of strange behavior | 404              | false        |
      |              |                                      | I bet misconfigured bots will try this        | There'll be all sort of strange behavior | 405              | false        |
      | hsolo        |                                      |                                               | There'll be all sort of strange behavior | 400              | false        |
      | hsolo        |                                      | I bet misconfigured bots will try this        |                                          | 400              | false        |

  Scenario: Retrieve all Prophecies from Prophet
    When clients makes a GET request to the Prophecy resource with "/prophets/jhutt/prophecies" uri
    Then a 200 http response will be returned by the Prophecy resource
    And a prophecy list with 2 elements should be returned in the response body
    And the following prophecies will be returned in the response body
      | prophetCode                          | prophecyTimestamp       | summary            | description            |
      | a4f3edc8-e024-4c07-994c-fe6c6e36ee9b | 2020-01-01T10:00:00.555 | Jabba prophecy 1 summary | Prophecy 1 description |
      | a4f3edc8-e024-4c07-994c-fe6c6e36ee9b | 2020-02-01T12:00:00.666 | Jabba prophecy 2 summary | Prophecy 2 description |

  Scenario: Retrieve all Prophecies a Prophet made within a time range
    When clients makes a GET request to the Prophecy resource with "/prophets/dsidious/prophecies?startDateTime=2020-04-01T10:00:00.556&endDateTime=2020-04-01T14:00:00.888" uri
    Then a 200 http response will be returned by the Prophecy resource
    And a prophecy list with 1 elements should be returned in the response body
    And the following prophecies will be returned in the response body
      | prophetCode                          | prophecyTimestamp       | summary            | description            |
      | ce00e12f-3b38-4f5f-8a21-a0e0b49635e0 | 2020-04-01T12:00:00.666 | Sidious prophecy 2 summary | Prophecy 2 description |

  Scenario: Retrieve all Prophecies a Prophet made after a specific time
    When clients makes a GET request to the Prophecy resource with "/prophets/qjinn/prophecies?startDateTime=2020-04-01T11:00:00.111" uri
    Then a 200 http response will be returned by the Prophecy resource
    And a prophecy list with 2 elements should be returned in the response body
    And the following prophecies will be returned in the response body
      | prophetCode                          | prophecyTimestamp       | summary            | description            |
      | f0fa9988-b2f2-47a6-a8e7-e00f560b2307 | 2020-04-01T12:00:00.666 | Qui Gon prophecy 2 summary | Prophecy 2 description |
      | f0fa9988-b2f2-47a6-a8e7-e00f560b2307 | 2020-04-01T15:00:00.777 | Qui Gon prophecy 3 summary | Prophecy 3 description |

  Scenario: Retrieve all Prophecies a Prophet made before a specific time
    When clients makes a GET request to the Prophecy resource with "/prophets/jbinks/prophecies?endDateTime=2020-04-01T14:59:00.111" uri
    Then a 200 http response will be returned by the Prophecy resource
    And a prophecy list with 2 elements should be returned in the response body
    And the following prophecies will be returned in the response body
      | prophetCode                          | prophecyTimestamp       | summary            | description            |
      | f1f040d2-cc83-4dba-8625-93a653b2a490 | 2020-04-01T10:00:00.555 | Jar Jar prophecy 1 summary | Prophecy 1 description |
      | f1f040d2-cc83-4dba-8625-93a653b2a490 | 2020-04-01T12:00:00.666 | Jar Jar prophecy 2 summary | Prophecy 2 description |

  Scenario Outline: Retrieving Prophecies Errors
    When clients makes a GET request to the Prophecy resource with "/prophets/<login>/prophecies" uri
    Then a <httpResponseCode> http response will be returned by the Prophecy resource
    And <shouldVerify> a prophecy list with <responseSize> elements should be returned in the response body
    Examples:
      | login     | httpResponseCode | shouldVerify | responseSize |
      | wantilles | 200              | true         | 0            |
      |           | 404              | false        | 0            |
      | hsolo     | 404              | false        | 0            |