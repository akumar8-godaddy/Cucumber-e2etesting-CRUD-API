Feature: CRUD API Testing

  Scenario: Create an Order
    Given I have a valid API endpoint for create
    When I send a POST request to create a resource with resource details as below
      | orderID         | order9    |
      | productID       | product9  |
      | customerID      | customer9 |
      | customerName    | Kshitij   |
      | customerAddress | Darbaga   |
      | subTotal        | 10000     |
    Then the response body should contain the string "Order added"

  Scenario: get an Order by orderID and customerID
    Given I have a valid API endpoint for get
    When I send a GET request to read a order with orderID as "order9" and customerID as "customer9"
    Then the response body should contain the order with orderID as "order9" and customerID as "customer9"

  Scenario: update an Order
    Given I have a valid API endpoint for update
    When I send a PUT request to update a order with orderID as "order9" and customerID as "customer9" with new details as below
      | productID       | product9 |
      | customerName    | Yashika  |
      | customerAddress | Darbaga  |
      | subTotal        | 1000     |
    Then the response body should contain the order with orderID as "order9" and customerID as "customer9" with updated details i.e name should be "Yashika" and subTotal should be "1000"

  Scenario: delete an Order
    Given I have a valid API endpoint for delete
    When I send a DELETE request to delete a order with orderID as "order9" and customerID as "customer9"
    Then the response body of delete request should contain the string "Deleted"
    And the response body should not contain the order with orderID as "order9" and customerID as "customer9" when I send a GET request should get empty list"
