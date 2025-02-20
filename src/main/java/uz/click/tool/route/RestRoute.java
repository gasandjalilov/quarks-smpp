package uz.click.tool.route;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import uz.click.tool.dto.SMPPSimpleMessage;

@ApplicationScoped
public class RestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration()
                .inlineRoutes(true)
                .dataFormatProperty("autoDiscoverObjectMapper", "true")
                .bindingMode(RestBindingMode.json)
                .port(9009);

        rest("/notification")
                .post("/smpp")
                .consumes("application/json")
                .type(SMPPSimpleMessage.class)
                .to("direct:smpp");
    }
}
