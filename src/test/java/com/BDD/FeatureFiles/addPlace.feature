Feature: Validate add feature APIs


  @add_placeAPI
  Scenario: Validate add place api with body
    Given Add place payload
    When user calls "add" with "post" https request
    Then then api call get success with status code 200
    And "status" in response boy is "OK"

  @get_placeAPI
  Scenario: Validate get place api request
    Given user calls "get" with "get" https request
    Then then api call get success with status code 200
    And "name" in response boy is "Frontline House"


  @update_placeAPI
  Scenario Outline: Validate update place api with body
    Given Update place payload with "<address>";
    When user calls "update" with "put" https request
    Then then api call get success with status code 200
    And "msg" in response boy is "Address successfully updated"
    Given user calls "get" with "get" https request
    And "address" in response boy is "<address>"

    Examples:
      | address                                        |
      | 2703, town center lane, Sunnyvale 94086 CA, US |


  @delete_placeAPI
  Scenario: Validate delete place api
    Given delete place payload
    When user calls "delete" with "delete" https request
    Then then api call get success with status code 200
    And "status" in response boy is "OK"
    Given user calls "get" with "get" https request
    And "msg" in response boy is "Get operation failed, looks like place_id  doesn't exists"