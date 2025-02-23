package com.example.emergencyapp.emergencycall.commands;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCallerDetailsCommand {

    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;
}
