package uz.click.tool.handler;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import uz.click.tool.processor.SMPPProcessor;

/**
 * Camel Message Router.
 * Only parameter that is used is full smpp path.
 */
@ApplicationScoped
public class MessageRouter extends RouteBuilder {


    @ConfigProperty(name = "smpp.full")
    String smpp;

    @Override
    public void configure() throws Exception {
        from("direct:smpp")
                .log(">>> [SMPP Router] Received message: ${body}")
                .routeId("message-router")
                .process(new SMPPProcessor())
                .log(">>> [SMPP Router] After processing: ${body}")
                .toD(smpp)
                .log(">>> [SMPP Router] Message sent to SMPP endpoint: ${headers}");
    }
}
