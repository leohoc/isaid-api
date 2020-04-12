Feature: Testing the Follower API
  Clients should be able register prophets following other prophets, to list a prophet followers and to list the prophets a prophet is following

  Background:

    Given the following prophets exists:
      | login     | prophetCode                          |
      | rnobody   | d2645154-dd58-419e-8b1b-bba60654538a |
      | kren      | c0f41377-ae21-4e4d-9007-c7e7f7591008 |
      | chewbacca | f48da9fb-5b97-441c-87f5-49406b2124ee |
      | yoda      | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | r2d2      | d715bf51-cfb8-47e7-8b4c-5de31a15e6f6 |
      | c3po      | 800e99c4-c6f9-47aa-a61a-77712d9f905d |
      | bb8       | ae9116f4-bf4a-46bb-8900-893190317bcf |
      | pdameron  | 84b7dbc1-0011-4303-a72a-23f1cc3f1b6e |
      | finn      | deb4dd63-99d2-4ada-ab1c-8827ad223337 |
      | zbliss    | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba |

    Given the following followers exists:
      | followerCode                         | prophetCode                          |
      | f48da9fb-5b97-441c-87f5-49406b2124ee | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | f48da9fb-5b97-441c-87f5-49406b2124ee | d715bf51-cfb8-47e7-8b4c-5de31a15e6f6 |
      | f48da9fb-5b97-441c-87f5-49406b2124ee | 800e99c4-c6f9-47aa-a61a-77712d9f905d |
      | ae9116f4-bf4a-46bb-8900-893190317bcf | 84b7dbc1-0011-4303-a72a-23f1cc3f1b6e |
      | ae9116f4-bf4a-46bb-8900-893190317bcf | deb4dd63-99d2-4ada-ab1c-8827ad223337 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | d2645154-dd58-419e-8b1b-bba60654538a |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | c0f41377-ae21-4e4d-9007-c7e7f7591008 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | f48da9fb-5b97-441c-87f5-49406b2124ee |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | d715bf51-cfb8-47e7-8b4c-5de31a15e6f6 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 800e99c4-c6f9-47aa-a61a-77712d9f905d |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | ae9116f4-bf4a-46bb-8900-893190317bcf |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 84b7dbc1-0011-4303-a72a-23f1cc3f1b6e |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | deb4dd63-99d2-4ada-ab1c-8827ad223337 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba |

  Scenario Outline: Inserting Followers
    When clients makes a POST request to the Follower resource with "/prophets/<prophetLogin>/followers" uri and "{ \"followerCode\":\"<followerCode>\" }" in the body
    Then a <httpResponseCode> http response will be returned by the Follower resource
    And <shouldVerify> a follower with followerCode equals to "<followerCode>" and prophetCode equals to "<prophetCode>" in the reponse body
    And <shouldVerify> a follower with followerCode equals to "<followerCode>" and prophetCode equals to "<prophetCode>" in the database

    Examples:
      | prophetLogin | followerCode                         | httpResponseCode | shouldVerify | prophetCode                          |
      | rnobody      | c0f41377-ae21-4e4d-9007-c7e7f7591008 | 200              | true         | d2645154-dd58-419e-8b1b-bba60654538a |
      | kren         | d2645154-dd58-419e-8b1b-bba60654538a | 200              | true         | c0f41377-ae21-4e4d-9007-c7e7f7591008 |
      | lcalrissian  | d2645154-dd58-419e-8b1b-bba60654538a | 404              | false        |                                      |
      |              | d2645154-dd58-419e-8b1b-bba60654538a | 405              | false        |                                      |
      | kren         |                                      | 400              | false        |                                      |

  Scenario: Retrieving Prophets Followed by a Prophet
    When clients makes a GET request to the Follower resource with "/prophets/zbliss/followedProphets" uri
    Then a 200 http response will be returned by the Follower resource
    And the following followers will be returned in the response body
      | followerCode                         | prophetCode                          |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | d2645154-dd58-419e-8b1b-bba60654538a |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | c0f41377-ae21-4e4d-9007-c7e7f7591008 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | f48da9fb-5b97-441c-87f5-49406b2124ee |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | d715bf51-cfb8-47e7-8b4c-5de31a15e6f6 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 800e99c4-c6f9-47aa-a61a-77712d9f905d |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | ae9116f4-bf4a-46bb-8900-893190317bcf |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 84b7dbc1-0011-4303-a72a-23f1cc3f1b6e |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | deb4dd63-99d2-4ada-ab1c-8827ad223337 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba |

  Scenario Outline: Listing Prophets Followed by a Prophet Corner Cases
    When clients makes a GET request to the Follower resource with "/prophets/<prophetLogin>/followedProphets" uri
    Then a <httpResponseCode> http response will be returned by the Follower resource
    And <shouldVerify> a follower list with <responseSize> elements should be returned in the response body
    Examples:
      | prophetLogin       | httpResponseCode | shouldVerify | responseSize |
      | yoda               | 200              | true         | 0            |
      | @invalidLogin      | 400              | false        | 0            |
      | nonexistentProphet | 404              | false        | 0            |
