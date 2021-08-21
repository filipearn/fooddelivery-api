package arn.filipe.fooddelivery.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TeamInput {
    @NotBlank
    private String name;
}