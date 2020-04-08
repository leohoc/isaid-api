Feature: Testing the Prophet API
  Clients should be able to create prophets by using POST request and retrieve then using a GET resource

  Background:

    Given the following prophets exists:
      | login      | prophetCode                          |
      | lskywalker | c25b1d8b-4246-408c-8521-937cf13a38be |
      | okenobi    | 64bf9ae2-37eb-4ad5-8060-5361fd763c46 |

    Scenario Outline: Retrieving Prophets
      When clients makes a GET request to "/prophets/<login>"
      Then a <httpResponseCode> http response will be returned by the Prophet resource
      And <shouldBeReturned> in the body a prophet with "<login>" as login and "<prophetCode>" as code

      Examples:
        | login      | httpResponseCode | shouldBeReturned | prophetCode                          |
        | lskywalker | 200              | true             | c25b1d8b-4246-408c-8521-937cf13a38be |
        | hsolo      | 404              | false            |                                      |
        |            | 405              | false            |                                      |

    Scenario Outline: Creating Prophets
      When clients makes a POST request to "/prophets/" with "{ \"login\":\"<login>\" }" in the body
      Then a <httpResponseCode> http response will be returned by the Prophet resource
      And <shouldVerify> a prophet with login equals to "<login>" in the database

      Examples:
        | login   | httpResponseCode | shouldVerify |
        | lorgana | 200              | true         |
        |         | 400              | false        |
        | okenobi | 409              | true         |