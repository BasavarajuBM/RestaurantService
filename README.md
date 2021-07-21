# RestaurantService
Few details about  API's

Server is running in port 8080 defined in application.yaml file under src/main/resources

Swagger ui can be accessed with the url http://localhost:8080/swagger-ui/

I am using API first approch all the API's are defined in the restaurant-api.yaml  under src/main/resources
and interfaces are autogenerated using the swagger code gen plugin so 
Application has to be built using Maven before starting the application 
Or  run the jar file directly using "java -jar restaurant-service-0.0.1-SNAPSHOT.jar" attached the jar in the email
Note: you may get Major minor version problem so  please use JDK 11

Unit and Integration tests are written but not all the scenarios are covered due to time constraint.

Scenario of searching restaurants within 5KM radius and searching restaurants by operation timing is not implemented due to time constarint 
it can be done with additional time

Below end points are exposed where user can get all the required services
1. /restaurants/register - Users can register their restaurants using this end point
2. /restaurants/menu/publish - Users can pubish their menu with this endpiont along with registered restaurant
3. /restaurants/search - Users can search the restaurant with this url with different criterias
4. /restaurants/rate - ser can rate the menu or restaurant 
5. /restaurants/menu-rating/{menuRating}/restaurant-rating/{restaurantRating}/search - Search the restaurant by menu rating or restaurant rating