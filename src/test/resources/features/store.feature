@store
Feature: (e2e) Validate store endpoint

  @placeOrder
  Scenario Outline: Validate place a new order
    Given the post request that creates a new pet with name "<petName>"
    And the post request that places an order for purchasing the pet created
    Then the response status code for the post order is 200
    And the body response for the post order contains a field status with value "placed"

    Examples:
      | petName  |
      | testPet1 |

  @deleteOrder
  Scenario Outline: Validate delete order
    Given the post request that creates a new pet with name "<petName>"
    And the post request that places an order for purchasing the pet created
    When the user sends the delete request that removes the order created
    Then the response status code for the delete order is 200
    And the body response contains a field message with value equal to the id of the order created

    Examples:
      | petName  |
      | testPet1 |

  @findOrder
  Scenario Outline: Validate find purchase order
    Given the post request that creates a new pet with name "<petName>"
    And the post request that places an order for purchasing the pet created
    When the user sends the get request that finds the order placed
    Then the response status code for the get order is 200
    And the body response contains a field id with value equal to the order id

    Examples:
      | petName  |
      | testPet1 |

  @getInventory
  Scenario: Validate get pet inventory
    Given the get request that returns pet inventories by status
    Then the response status code for the get inventories is 200
    And the body response contains a field named "available"
