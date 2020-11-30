package io.pivotal.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO-00: In this lab, you are going to exercise the following:
//
// - Writing slice testings on MVC, Service, Repository, and Caching layers
// - Writing integration testing
//
//
// TODO-01: Write MVC slice testing
//
// ### Functionality to be tested
//
// - The `getCar` method of the `CarController`,
//   given a correct car name in the URL,
//   i.e. "/cars/prius", should return success status code of 200
//   and Car data in JSON format in the response body
//
// - The `getCar` method of the `CarController`,
//   given a non-existent car name in the URL,
//   i.e. "/cars/nocar", should return failure status code of 404
//
//-  (Optional)
//   The `addCar` method of the `CarController`, given a new car,
//   should add it to the system and return success status code of
//   201 with `Location` response header set with the URI of
//   the newly created car
//
// ### Tips
//
// - Use `@WebMvcTest`, which auto-configures `MockMvc` object
//
// - Given that it is a slice testing, you should mock `CarService`
//   object - think about which annotation to use for mocking,
//   `@Mock` or `@MockBean`
//
// - In Spring, all exceptions should be a child class of `RunTimeException`
//
//
// TODO-02: Write Service slice testing
//
// ### Functionality to be tested
//
// - The`getCarDetails` method of the `CarService`, given
//   a valid car name, i.e. "prius", should return Car object
//
// - The`getCarDetails` method of the `CarService`,
//   given a non-existent car name, i.e. "nocar",
//   should throw `CarNotFoundException`
//
// - (Optional)
//   The `addCarDetails(Car car)` method of the `CarService`
//   should add it to the system and return added Car
//
// ### Tips
//
// - Since this is a slice testing, you should mock
//   `CarRepository` dependency object
//
// - Start with testing with Spring using `@ExtendWith(SpringExtension.class)`
//   and `@ContextConfiguration` annotations (using Spring testing framework)
//   and then refactor the code with the them with
//   `@ExtendWith(MockitoExtension.class)` (without using Spring testing framework)
//   and think about which one is a better practice at the service layer
//
// TODO-03: Write JPA Repository slice testing
//
// ### Functionality to be tested
//
// - The `findByName(..)` method of the `CarRepository` should return
//   a Car given a valid car name
//
// ### Tips
//
// - Use `@DataJpaTest` annotation, which provides `TestEntityManager`
//
// ### Trouble-shooting
//
// - When your test fails below fails with
//   `org.springframework.orm.jpa.JpaSystemException: No default constructor for entity: ..`,
//   it means your `Car` class does not have a default constructor
//
//
// TODO-04: Write Service caching slice testing
//
// ### Functionality to be tested
//
// - The `getCarDetails(..)` method of the `CarService` should
//   return an item from a cache if it gets called more than once
//
// ### Tips
//
// - The way you can test the caching behavior is to
//   call `getCarDetails(..)` method of the `CarService` twice
//   and then see if it called `CarRepository's` `findByName(..)`
//   method once
//
//
// TODO-05: Write integration testing
//
// ### Functionality to be tested
//
// - The application, when accessed with a valid URL,
//   i.e. "http://localhost:8080/cars/prius", should return success
//   status of 200 and Car data in JSON format in the response body
//
// - The application, when accessed with an invalid URL,
//   i.e. "http://localhost:8080/cars/nocar", should return failure
//   status of 404
//
//-  (Optional)
//   The application, when requested to add a new car,
//   should add it to the system and return success status code of 201
//   with `Location` response header with the URI of
//   the newly created car
//

@SpringBootApplication
public class SpringBootTddApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootTddApplication.class, args);
	}
}
