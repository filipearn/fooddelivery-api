package arn.filipe.fooddelivery.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserWithPasswordInput extends UserInput{

    @ApiModelProperty(example = "password", required = true)
    @NotBlank
    private String password;
}
