package uz.click.tool.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jsmpp.bean.Alphabet;
import uz.click.tool.dto.SMPPSimpleMessage;

import java.util.regex.Pattern;

/**
 * Message processing using.
 * Basically transforms message to smpp exchange.
 */
public class SMPPProcessor implements Processor {

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");


    public void process(Exchange exchange) throws Exception {
        SMPPSimpleMessage msg = exchange.getIn().getBody(SMPPSimpleMessage.class);
        exchange.getIn().setHeader("CamelSmppDestAddr", msg.msisdn());
        exchange.getIn().setHeader("CamelSmppDestAddrTon", (byte) 1);
        exchange.getIn().setHeader("CamelSmppDestAddrNpi", (byte) 1);
        exchange.getIn().setHeader("CamelSmppSourceAddr", msg.alpha());
        exchange.getIn().setHeader("CamelSmppSourceAddrTon", (byte) 5);
        exchange.getIn().setHeader("CamelSmppSourceAddrNpi", (byte) 0);
        exchange.getIn().setHeader("CamelSmppServiceType", "WAP");
        exchange.getIn().setHeader("CamelSmppRegisteredDelivery", (byte) 0);

        if(isNumeric(msg.alpha())){
            exchange.getIn().setHeader("CamelSmppSourceAddr", msg.alpha());
            exchange.getIn().setHeader("CamelSmppSourceAddrTon", (byte) 1);
            exchange.getIn().setHeader("CamelSmppSourceAddrNpi", (byte) 1);
        }

        if(msg.message().chars()
                .mapToObj(Character.UnicodeBlock::of)
                .anyMatch(Character.UnicodeBlock.CYRILLIC::equals)) {
            exchange.getIn().setHeader("CamelSmppDataCoding",(byte)0x08);
            exchange.getIn().setHeader("CamelSmppAlphabet", Alphabet.ALPHA_UCS2.value());
        }
        exchange.getIn().setBody(msg.message());
    }


    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
