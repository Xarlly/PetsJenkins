@pet
Feature: (e2e) Validate pet endpoint

  @addNewPet
  Scenario Outline: Validate add a new pet
    Given the post request that creates a new pet with name "<name>"
    Then the response status code for the pet post is 200
    And the body response contains a field id with numeric non negative value
    And the body response for the pet post contains a field name with value "<name>"

    Examples:
      | name     |
      | testPet1 |

  @updatePet
  Scenario Outline: Validate update a pet
    Given the post request that creates a new pet with name "<nameBefore>"
    And the put request that updates the pet created with name "<nameAfter>"
    Then the response status code for the pet put is 200
    And the body response for the pet put contains a field name with value "<nameAfter>"

    Examples:
      | nameBefore | nameAfter       |
      | testPet1   | testPet1Updated |

  @deletePet
  Scenario Outline: Validate delete a pet
    Given the post request that creates a new pet with name "<name>"
    And the delete request that deletes the pet created
    Then the response status code for the delete is 200
    And the body response for the delete contains a field message with value equal to the id of the deleted pet

    Examples:
      | name     |
      | testPet1 |

  @findPetById
  Scenario Outline: Validate find pet by id
    Given the post request that creates a new pet with name "<name>"
    And the get request that finds the pet created by its id
    Then the response status code for the get pet by id is 200
    And the body response for the get pet by id contains a field name with value "<name>"

    Examples:
      | name     |
      | testPet1 |

  @findPetByStatus
  Scenario Outline: Validate find pet by status
    Given the get request that finds pets by status "<petStatus>"
    Then the response status code for the get pet by status is 200
    And the response body contains a list of pets with status "<petStatus>"

    Examples:
      | petStatus |
      | available |