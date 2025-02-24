package uz.click.tool.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;

/**
 * Base SMPP Request.
 * @param msisdn phone number.
 * @param alpha alpha name.
 * @param message text message.
 */
@RegisterForReflection
public record SMPPSimpleMessage(
        String msisdn,
        String alpha,
        String message
) implements Serializable {
}
