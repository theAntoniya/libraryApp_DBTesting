@login
Feature: Login Functionality

  Scenario: Login with valid credentials
    Given the user logged in  "librarian55@library" and "67UQi3Ol"
    When user gets username  from user fields
    Then the username should be same with database

  @db
  Scenario Outline: Login with valid credentials DDT
    Given the user logged in  "<email>" and "<password>"
    When user gets username  from user fields
    Then the username should be same with database
    Examples:
      | email               | password |
      | librarian55@library | 67UQi3Ol |
      | librarian56@library | pBQnq0NN |
      | student5@library    | i1oDgf2d |
      | student6@library    | NXhpXJdC |