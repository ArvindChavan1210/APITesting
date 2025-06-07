Feature: Validate Add book api End to End testing

  @add_booksAPI @BookRegression
  Scenario Outline: Validate Add books api with body
    Given body for request with "<book_name>"
    When  user hits "post" request for "addbooks" api and "string_req_for_getting_req_data"
    Then validate response "Status" code is 200
    And validate response body contains "Msg" like "successfully added"
    Given user hits "get" request for "addbooks" api and "author_name"
    Then validate response body contains "isbn","aisle" and "<book_name>" used while hitting request

    Examples:
      | book_name                         |
      | Learn Appium Automation with JAVA |

  @get_booksByID @BookRegression @hook_getBookByID
  Scenario: Validate get books api with id
    Given user hits "get" request for "addbooks" api and "id"
    Then validate response "Status" code is 200
    And validate response body contains "isbn","aisle" and "author" used while hitting request

  @delete_booksByID @BookRegression @hook_deleteBookByID
  Scenario: Validate delete book by id
    Given user hits "delete" request for "addbooks" api and "id"
#    Then validate response "Status" code is 200
    And validate response body contains "msg" like "book is successfully deleted"
    Given user hits "get" request for "addbooks" api and "id"
    And validate response body contains "msg" like "The book by requested bookid / author name does not exists!"