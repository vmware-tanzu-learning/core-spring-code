package rewards;

// TODO-07 : Capture properties into a class using @ConfigurationProperties
// - Note that application.properties file already contains the following properties
//
//    rewards.recipient.name=John Doe
//    rewards.recipient.age=10
//    rewards.recipient.gender=Male
//    rewards.recipient.hobby=Tennis
//
// - Annotate this class with @ConfigurationProperties
//   with prefix attribute set to a proper value
// - Create fields (along with needed getters/setters) that reflect the
//   properties above in the RewardsRecipientProperties class
// - Use one of the following 3 schemes to enable @ConfigurationProperties
//   (1) Add @EnableConfigurationProperties(RewardsRecipientProperties.class)
//       to this class or
//   (2) Add @ConfigurationPropertiesScan to this class or
//   (3) Annotate the RewardsRecipientProperties class with @Component
// - Implement a new command line runner that displays the name of the rewards
//   recipient when the application gets started
public class RewardsRecipientProperties {

}
