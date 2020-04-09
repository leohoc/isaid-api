Feature: Testing the Follower API
  Clients should be able register prophets following other prophets, to list a prophet followers and to list the prophets a prophet is following

  Background:

    Given the following prophets exists:
      | login      | prophetCode                          |
      | rnobody    | d2645154-dd58-419e-8b1b-bba60654538a |
      | kren       | c0f41377-ae21-4e4d-9007-c7e7f7591008 |

  Scenario Outline: Inserting Followers
    When clients makes a POST request to the Follower resource with "/prophets/<prophetLogin>/followers" uri and "{ \"followerCode\":\"<followerCode>\" }" in the body
    Then a <httpResponseCode> http response will be returned by the Follower resource
    And <shouldVerify> a follower with followerCode equals to "<followerCode>" and prophetCode equals to "<prophetCode>" in the database

    Examples:
      | prophetLogin | followerCode                         | httpResponseCode | shouldVerify | prophetCode                          |
      | rnobody      | c0f41377-ae21-4e4d-9007-c7e7f7591008 | 200              | true         | d2645154-dd58-419e-8b1b-bba60654538a |
      | kren         | d2645154-dd58-419e-8b1b-bba60654538a | 200              | true         | c0f41377-ae21-4e4d-9007-c7e7f7591008 |
