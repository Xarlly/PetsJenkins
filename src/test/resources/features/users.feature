@users
Feature: (e2e) Users endpoint

  @createUser
  Scenario: Validate create a new user
    Given the post request that creates a new user with username "testUser1"
    Then the response status code for the post user is 200
    And the body response contains a field message with a numeric non negative value

  @createUserWithArray
  Scenario: Validate create list of users with array
    Given the post request that creates a list of user with an array
    Then the response status code for the create users with array post is 200
    And the body response for the create with array contains a field message with value "ok"

  @createUsersWithList
  Scenario: Validate create list of users with list
    Given the post request that creates a list of user with a list
    Then the response status code for the create users with list post is 200
    And the body response for the create with list contains a field message with value "ok"

  @updateUser
  Scenario: Validate update user
    Given the post request that creates a new user with username "testUser1"
    And the put request that updates the user created with name "testUser1Updated"
    Then the response status code for the user put is 200
    And the body response for the put contains a field message with a numeric non negative value

  @deleteUser
  Scenario Outline: Validate delete user
    Given the post request that creates a new user with username "<userName>"
    And the delete request that deletes the user created
    Then the response status code for the delete user is 200
    And the body response contains a field message with value equal to "<userName>"

    Examples:
      | userName  |
      | testUser1 |

  @login
  Scenario: Validate login
    Given the post request that creates a new user with username "testUser1" and password "password"
    When the user sends the get request that logs the user into the system
    Then the response status code for the get login is 200


  @logout
  Scenario: Validate logout
    Given the post request that creates a new user with username "testUser1" and password "password"
    When the user sends the get request that logs the user into the system
    And the user sends the get request that logs out the session
    Then the response status code for the logout get is 200

  @getUser
  Scenario Outline: Validate get user by username
    Given the post request that creates a new user with username "<userName>"
    When the user sends the get request that returns the user with username "<userName>"
    Then the response status code for the get user is 200
    And the response body contains a field username with value "<userName>"

    Examples:
      | userName  |
      | testUser1 |

