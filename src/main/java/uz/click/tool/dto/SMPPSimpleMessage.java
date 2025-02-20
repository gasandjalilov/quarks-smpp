package uz.click.tool.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;

@RegisterForReflection
public record SMPPSimpleMessage(
        String msisdn,
        String alpha,
        String message
) implements Serializable {
}
