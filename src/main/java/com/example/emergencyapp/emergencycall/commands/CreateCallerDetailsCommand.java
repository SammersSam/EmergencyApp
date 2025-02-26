package com.example.emergencyapp.emergencycall.commands;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CreateCallerDetailsCommand {

    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;

    public CreateCallerDetailsCommand() {
    }

    public CreateCallerDetailsCommand(@NotBlank String name, @NotBlank String lastName, @NotBlank String phoneNumber) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
