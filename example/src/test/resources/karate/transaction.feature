Feature: Customer transactions

  Background:
    * url 'http://localhost:8881'

  Scenario: Create transaction
    Given path 'transaction'
    And request { id: '1234' , customerId: '999999'}
    When method POST
    Then status 200
    And match $.data.customerId == "#notnull"

    Given path 'transaction/999999'
    When method GET
    Then status 200
    And match $.data[0].customerId == "999999"

    Given path 'transaction/999999xxx'
    When method GET
    Then status 404
    And match $.success == false
    And match $.errors[0].errorCode == "CUSTOMER_TRANSACTIONS_NOT_FOUND"

    Given path 'transaction/999999'
    When method DELETE
    Then status 200