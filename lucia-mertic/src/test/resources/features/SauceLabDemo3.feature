Feature: SauceLab Demo3

  @loginTest2
  Scenario Outline: Successful login
    Given Login page is opened
    When Username and password "<username>" and "<password>" are entered
    And Login is attempted
    Then Products page is displayed

    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |
      | performance_glitch_user | secret_sauce |


  @addToCartHappy
  Scenario Outline: Add to cart
    Given Login page is opened
    When Username and password "<username>" and "<password>" are entered
    And Login is attempted
    Then Products page is displayed

    When User adds to cart product "<productName>"
    Then The product with name "<productName>" is displayed in the cart

    When User clicks on the cart icon
    Then Cart page is displayed

    When User clicks on the checkout button
    Then Checkout Info page is displayed

    When User enters checkout info "<checkoutFirstName>" "<checkoutLastName>" "<postalCode>"
    And User continues on Checkout Info page
    Then Checkout Overview is displayed
    And Users checks product is found on the Checkout Overview page


    When User finishes on Checkout Overview page
    Then Checkout Complete page is displayed
    And "Thank you for your order!" is displayed on the page

    When User goes back to Products page
    Then Products page is displayed

    Examples:
      | username      | password     |productName | checkoutFirstName | checkoutLastName | postalCode |
      | standard_user | secret_sauce | Sauce Labs Backpack| checkoutFirstName2  | checkoutLastName2 | 12346|
      | problem_user      | secret_sauce | Sauce Labs Bike Light | Meeny | Doe | 590234      |
      | performance_glitch_user | secret_sauce | Sauce Labs Fleece Jacket | Miny | Doe | 123456      |
      |  locked_out_user    | secret_sauce | Sauce Labs Onesie | Moe | Doe | 987654      |
