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
      | swexley   | 90548af7-4fba-4c5c-8ad9-805859851456 |
      | ghux      | 702ad376-bae4-4808-ba54-a5b9ee3d27ef |
      | mkanata   | 63484cf9-fdc0-4684-a465-16e7c8f5522e |
      | rtico     | 01736ff4-ac8b-4acc-8a8f-cc1cf1ea9055 |
      | abednedo  | 343129d7-96f3-41c9-9874-10c832eb67b8 |
      | kconnix   | 5c23e272-65d6-4e66-8c3a-70b20b22e878 |

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
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 702ad376-bae4-4808-ba54-a5b9ee3d27ef |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 63484cf9-fdc0-4684-a465-16e7c8f5522e |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 01736ff4-ac8b-4acc-8a8f-cc1cf1ea9055 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 343129d7-96f3-41c9-9874-10c832eb67b8 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 5c23e272-65d6-4e66-8c3a-70b20b22e878 |
      | d2645154-dd58-419e-8b1b-bba60654538a | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | c0f41377-ae21-4e4d-9007-c7e7f7591008 | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | d715bf51-cfb8-47e7-8b4c-5de31a15e6f6 | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | 800e99c4-c6f9-47aa-a61a-77712d9f905d | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | ae9116f4-bf4a-46bb-8900-893190317bcf | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | 84b7dbc1-0011-4303-a72a-23f1cc3f1b6e | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | deb4dd63-99d2-4ada-ab1c-8827ad223337 | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | 90548af7-4fba-4c5c-8ad9-805859851456 | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | 702ad376-bae4-4808-ba54-a5b9ee3d27ef | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | 63484cf9-fdc0-4684-a465-16e7c8f5522e | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | 01736ff4-ac8b-4acc-8a8f-cc1cf1ea9055 | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | 343129d7-96f3-41c9-9874-10c832eb67b8 | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | 5c23e272-65d6-4e66-8c3a-70b20b22e878 | dfce2d96-d660-4acb-9dba-cb4e36903606 |

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
      | rnobody      | 390d917e-9297-4ad6-ae4b-5e443abb8ade | 404              | false        |                                      |

  Scenario: Retrieving Prophets Followed by a Prophet First Page
    When clients makes a GET request to the Follower resource with "/prophets/zbliss/followedProphets?page=0" uri
    Then a 200 http response will be returned by the Follower resource
    And the following followers will be returned in the response body
      | followerCode                         | prophetCode                          |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 01736ff4-ac8b-4acc-8a8f-cc1cf1ea9055 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 343129d7-96f3-41c9-9874-10c832eb67b8 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 5c23e272-65d6-4e66-8c3a-70b20b22e878 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 63484cf9-fdc0-4684-a465-16e7c8f5522e |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 702ad376-bae4-4808-ba54-a5b9ee3d27ef |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 800e99c4-c6f9-47aa-a61a-77712d9f905d |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | 84b7dbc1-0011-4303-a72a-23f1cc3f1b6e |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | ae9116f4-bf4a-46bb-8900-893190317bcf |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | c0f41377-ae21-4e4d-9007-c7e7f7591008 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | d2645154-dd58-419e-8b1b-bba60654538a |

  Scenario: Retrieving Prophets Followed by a Prophet Second Page
    When clients makes a GET request to the Follower resource with "/prophets/zbliss/followedProphets?page=1" uri
    Then a 200 http response will be returned by the Follower resource
    And the following followers will be returned in the response body
      | followerCode                         | prophetCode                          |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | d715bf51-cfb8-47e7-8b4c-5de31a15e6f6 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | deb4dd63-99d2-4ada-ab1c-8827ad223337 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | dfce2d96-d660-4acb-9dba-cb4e36903606 |
      | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba | f48da9fb-5b97-441c-87f5-49406b2124ee |

  Scenario Outline: Retrieving Prophets Followed by a Prophet Corner Cases
    When clients makes a GET request to the Follower resource with "/prophets/<prophetLogin>/followedProphets?page=<page>" uri
    Then a <httpResponseCode> http response will be returned by the Follower resource
    And <shouldVerify> a follower list with <responseSize> elements should be returned in the response body
    Examples:
      | prophetLogin       | page | httpResponseCode | shouldVerify | responseSize |
      | yoda               | 0    | 200              | true         | 0            |
      | zbliss             | 2    | 200              | true         | 0            |
      | @invalidLogin      | 0    | 400              | false        | 0            |
      | nonexistentProphet | 0    | 404              | false        | 0            |

  Scenario: Retrieving a Prophet Followers First Page
    When clients makes a GET request to the Follower resource with "/prophets/yoda/followers?page=0" uri
    Then a 200 http response will be returned by the Follower resource
    And the following followers will be returned in the response body
      | prophetCode                          | followerCode                         |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | 01736ff4-ac8b-4acc-8a8f-cc1cf1ea9055 |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | 343129d7-96f3-41c9-9874-10c832eb67b8 |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | 5c23e272-65d6-4e66-8c3a-70b20b22e878 |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | 63484cf9-fdc0-4684-a465-16e7c8f5522e |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | 702ad376-bae4-4808-ba54-a5b9ee3d27ef |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | 800e99c4-c6f9-47aa-a61a-77712d9f905d |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | 84b7dbc1-0011-4303-a72a-23f1cc3f1b6e |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | 90548af7-4fba-4c5c-8ad9-805859851456 |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | ae9116f4-bf4a-46bb-8900-893190317bcf |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | c0f41377-ae21-4e4d-9007-c7e7f7591008 |

  Scenario: Retrieving a Prophet Followers Second Page
    When clients makes a GET request to the Follower resource with "/prophets/yoda/followers?page=1" uri
    Then a 200 http response will be returned by the Follower resource
    And the following followers will be returned in the response body
      | prophetCode                          | followerCode                         |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | d2645154-dd58-419e-8b1b-bba60654538a |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | d715bf51-cfb8-47e7-8b4c-5de31a15e6f6 |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | deb4dd63-99d2-4ada-ab1c-8827ad223337 |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | e4e80a70-4570-4b0b-b0d9-8270d7cb66ba |
      | dfce2d96-d660-4acb-9dba-cb4e36903606 | f48da9fb-5b97-441c-87f5-49406b2124ee |

  Scenario Outline: Retrieving a Prophet Followers Corner Cases
    When clients makes a GET request to the Follower resource with "/prophets/<prophetLogin>/followers?page=<page>" uri
    Then a <httpResponseCode> http response will be returned by the Follower resource
    And <shouldVerify> a follower list with <responseSize> elements should be returned in the response body
    Examples:
      | prophetLogin       | page | httpResponseCode | shouldVerify | responseSize |
      | swexley            | 0    | 200              | true         | 0            |
      | yoda               | 2    | 200              | true         | 0            |
      | r2d2               | 1    | 200              | true         | 0            |
      | invalid login      | 0    | 400              | false        | 0            |
      | nonexistentProphet | 0    | 404              | false        | 0            |
