The only purpose of this /static/resources folder and files is to make 
is slightly easier to serve up resources for boot and non-boot resources 
in the same course.

In the mvc labs, the pages will refer to the resources under "/resources/styles"
and "/resources/images", and a special resource handler is needed to to translate 
"/resources/**" into "classpath:/static/*".  This works beautifully.

But boot handles static classpath resources even more beautifully.  
Boot finds resources automatically from /static, /resources, etc. etc. with no special
mapping needed.  When we copy the pages that work in the non-boot labs to the boot lab, 
references to "/resources/styles" would need to be located under "/static/resources/styles"

We could easily resolve this by adding a resource handler to the boot app like this:

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	   registry.addResourceHandler("/resources/**")
	           .addResourceLocations("classpath:/static/");
	 }  
}
 but this would be a bit challenging to explain, and would give the impression that
boot requires more special configuration than a normal Spring app, which is certainly
not the impression we wish to leave students with.