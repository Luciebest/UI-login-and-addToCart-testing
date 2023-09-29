# 11.spring-ta
spring, @autowired,  field, constructor and setter injection, @Configuration, @Bean, @Component,   @Qualifier, @Value, @Profile  
-   we have added to our pom the following dependencies: cucumber-spring, spring-context, spring-test, jakarta.annotation-api
-   We now have a new BaseSteps class that is annotated with @CucumberContextConfiguration and @ContextConfiguration, that points to FrameworkConfiguration.class created by us
  
 
```bash
    @CucumberContextConfiguration
    @ContextConfiguration(classes = FrameworkConfiguration.class)
  ```

- FrameworkConfiguration class is annotated with  @Configuration to tell spring to look for @Beans that need to be added to the Spring context
- In the FrameworkConfiguration class we declared a bean of type Webdriver
  https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-basic-concepts

  https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-bean-annotation

  https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-configuration-annotation


- We used @PreDestroy to quit the driver at the end

  https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-postconstruct-and-predestroy-annotations

- Also, FrameworkConfiguration is annotated with @ComponentScan to search in  specified basePackages for Spring stereotype annotations(@Component, @Repository, @Service, and @Controller). We used only @Component. 
- All Page classes extend BasePage and are now Spring components(@Component)
- In page classes, constructor injection was used initially for injecting driver in the BasePage constructor. Inside the BasePage constructor the PageFactory.initElement was called. The other pages were only calling super(driver) in their constructor.

```bash
  protected WebDriver driver;
      
  @Autowired
  public BasePage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }
  ```

- After that, the constructor was commented in all the Page classes and the driver Bean was injected using field injection. 
- Also, the PageFactory.initElements was now called from @PostConstruct method 
```bash
      @Autowired
      protected WebDriver driver;
      
      @PostConstruct
      public void init() {
        System.out.println("Initializing page for class: " + this.getClass());
        PageFactory.initElements(driver, this);
    }
```

- FrameworkConfiguration class is also annotated with @PropertySource that specifies a properties file 
```
  @PropertySource("classpath:framework.properties")
```
- we can use @Value to inject externalized properties from the previously created file
```
    @Value("${base.url:}")
    private String baseUrl;
```

- we can assign a default value, that will be used, if not found in file 
```
    @Value("${base.url:https://www.saucedemo.com/cart.html}")
    private String baseUrl;
```
- and we can override it in vmoptions at runtime 
```
    -Dbase.url="https://www.saucedemo.com/inventory.html"
```

### 2. (Assignment) Implement a cucumber scenario outline for successful adding a product to cart on https://www.saucedemo.com/ .

#### What
##### Perform the full flow of a user

logging in
- adding product to cart
- going to cart page by clocking the cart icon
- checking out
- filling the checkout info
- finishing checkout
- verifying "Thank you for your order!" is displayed on the last page
- returning to products page by pressing the "Back Home" button from the last page
- verifying that we are on the products page using the already defined step "Products page is displayed"

##### General guidelines
You must use Spring @Component  on Page classes and wire them with @Autowired in BaseSteps.
Suggestions on how to name your classes

- CartPage.java
- CheckoutInfoPage.java
- CheckoutOverview.java
- CheckoutFinishPage.java

### 3. (Assignment) Add and implement a step to check that the added product name and price from ProductsPage is found on CheckoutOverviewPage
#### How
#####  You must use a spring component. 
Create a new class named OrderItem.
Use the  @Scope("cucumber-glue") cucumber specific annotation that will dispose of the bean once the scenario is finished.
```bash
  @Component
  @Scope("cucumber-glue")
  public class OrderItem {
    private String name;
    private double price;
    private int quantity;
    
    //getters and setters
}

```

##### You will need to autowire the OrderItem in the SauceLabSteps
```bash
    @Autowired
    private OrderItem orderItem;
```

##### In the step that performs the adding product to cart that you previously implemented, set name, price and  quantity of the orderItem

```gherkin
   When User adds to cart product "Sauce Labs Backpack"
```

```bash
    @When("User adds to cart product {string}")
    public void userAddsToCart(String productName) {
       //already existing code that you wrote at step 2 to add product to cart
       //...
       
       //TODO
       //set the productName on the orderItem that you will use in another step to make assertions 
        orderItem.setName(productName);
        
      //TODO
      //get  productPrice using selenium and use Double.parseDouble("price of type string from selenium") to convert to double
      //you will need to follow the same pattern with page classes. There should not be driver in Steps classes. 
        
        //set price and quantity
        orderItem.setPrice(productPrice);
        orderItem.setQuantity(1);
  }

```

##### Make the assertions on the  CheckoutOverviewPage. Add and implement a step to check that the added product name and price is found on CheckoutOverviewPage

```bash
    @Then("Users checks product is found on the Checkout Overview page")
    public void usersChecksProductIsFoundOnTheCheckoutOverviewPage() {
        String expectedProductName = orderItem.getName();
        double expectedProductPrice = orderItem.getPrice();
        int expectedProductQuantity = orderItem.getQuantity();
        //TODO add assertions
        //...
        
    }

```


##### Use the step in the scenario after reaching Checkout Overview Page

```gherkin
     
     Then Checkout Overview is displayed
     And Users checks product is found on the Checkout Overview page
 
```
