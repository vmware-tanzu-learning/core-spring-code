package accounts.web;

/**
 * TODO-16a: Create custom health indicator
 * - Make this class implement HealthIndicator interface
 * - Make this class a component
 * - Add a constructor to pass in the restaurant repository
 *   and use it to implement health().
 * - health() method should return DOWN if the repository is empty
 *   (no restaurants) or UP otherwise.
 *
 * ------------------------------------------
 *
 * TODO-22 (Optional): Experiment with HealthIndicator
 * - Include the restaurant count as extra detail in the health endpoint.
 *   Have a look at the Health class to see how this might work.
 * - Instead of returning DOWN when there are no restaurants,
 *   use a custom status (e.g. NO_RESTAURANTS).
 * - When there are no restaurants in the DB, what overall status
 *   is returned for the application? Fix this issue by adjusting
 *   the order of precedence for the health statuses.
 */
public class RestaurantHealthCheck {

}
