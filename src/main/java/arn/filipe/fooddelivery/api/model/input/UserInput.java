package arn.filipe.fooddelivery.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserInput {

    @ApiModelProperty(example = "Filipe", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "filipearn@yahoo.com.br", required = true)
    @NotBlank
    @Email
    private String email;
}
