## ECommerce App Run Guide ##

- First step
    ```
    git clone https://github.com/aykanferhat/ecommerce-app.git
    ```
        
- Second step
    ```
    mvn install
    ```
        
- Third step
    ```
    docker-compose up
    ```
    
## Default App port ##

    | APPLICATION    | DEFAULT PORT |  
    |:--------------:|:------------:|
    | Mysql          |  `3306`      |
    | PhpMyAdmin     |  `7070`      |
    | EurekaApp      |  `8761`      |
    | ProductApp     |  `9090`      |
    | DiscountApp    |  `8088`      |
    | BasketApp|  `8585`      |

- You can change default ports in docker-compose.yml if you wish. 

#### Notice:

- You should use Lombok plugin on your IDE, to build the project

#### SWAGGER URL 

* Product App 
    ```
    http://localhost:{{PRODUCT_SERVICE_PORT}}/swagger-ui.html
    ```
* Discount App 
    ```
    http://localhost:{{BASKET_SERVICE_PORT}}/swagger-ui.html
    ```
* Basket App 
    ```
    http://localhost:{{DISCOUNT_SERVICE_PORT}}/swagger-ui.html
    ```
    
### POSTMAN COLLECTION

- 
    ```
    https://www.getpostman.com/collections/9b5a2bc4c5ba9d5362d9
    ```     

#### EUREKA APP

* Check Registered service in eureka from your browser.
    ```
      http://localhost:{{EUREKA_SERVICE_PORT}}
    ```


#### PRODUCT APP API
    
* Create Category
    ```
    POST http://localhost:{{PRODUCT_SERVICE_PORT}}/api/v1/categories
        {
            "title":"CategoryName" 
        }
    ```
    
* Check Category is exists
    ```
    GET http://localhost:{{PRODUCT_SERVICE_PORT}}/api/v1/categories/{categoryId}/exists
    ```
    
* Create Product
    ```
    POST http://localhost:{{PRODUCT_SERVICE_PORT}}/api/v1/products
       {
        "title":"ProductName", 
        "price":2500.00,       
        "categoryId":1          
       }
    ```
* Check Product is exists   
    ```
    GET http://localhost:{{PRODUCT_SERVICE_PORT}}/api/v1/products/{productId}/exists
    ```

* Get Category by Id
    ```
    GET http://localhost:{{PRODUCT_SERVICE_PORT}}/api/v1/categories/{categoryId}
    ```


#### BASKET APP API

* Create Basket
    ```
    POST http://localhost:{{BASKET_SERVICE_PORT}}/api/v1/baskets
        {
        	"userId":1
        }
    ```
    
* Get Basket by Id
    ```
    GET http://localhost:{{BASKET_SERVICE_PORT}}/api/v1/baskets/{basketId}
    ```
    
* Add Item to Basket
    ```
    POST http://localhost:{{BASKET_SERVICE_PORT}}/api/v1/baskets/{basketId}
       {
       	"productId":1,
       	"quantity":1
       }
    ```

#### DISCOUNT APP API

* Create Campaign
    ```
    POST http://localhost:{{DISCOUNT_SERVICE_PORT}}/api/v1/campaigns
        
        {
        	"categoryId":1,
        	"threshold":3,
        	"discountType":"RATE",
        	"discount":25
        }
        OR
        {
        	"categoryId":1,
        	"threshold":1000.00,
        	"discountType":"AMOUNT",
        	"discount":250.00
        }
    ```
    
* Get Campaign by Id
    ```
    GET http://localhost:{{DISCOUNT_SERVICE_PORT}}/api/v1/campaigns/{campaignId}
    ```
    
* Create Coupon
    ```
    POST http://localhost:{{DISCOUNT_SERVICE_PORT}}/api/v1/coupons
       {
       	"threshold":1000.00,
       	"discountType":"AMOUNT",
       	"discount":250.00
       }
       OR
       {
       	"threshold":1000.00,
       	"discountType":"RATE",
       	"discount":25
       }       
    ```
* Get Coupon by Id
    ```
    GET http://localhost:{{DISCOUNT_SERVICE_PORT}}/api/v1/coupons/{couponId}
    ```   
    
* Get Coupon by Id 
    ```
    GET http://localhost:{{DISCOUNT_SERVICE_PORT}}/api/v1/coupons/{couponId}
    ```

* Get Coupon by threshold filter
    ```
    GET http://localhost:{{DISCOUNT_SERVICE_PORT}}/api/v1/coupons/thresholdFilter?price={price}
    ```    
   
