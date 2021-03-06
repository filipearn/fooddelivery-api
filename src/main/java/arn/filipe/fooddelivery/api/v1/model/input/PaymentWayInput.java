package arn.filipe.fooddelivery.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PaymentWayInput {

    @ApiModelProperty(example = "Cartão de crédito", required = true)
    @NotBlank
    private String description;
}
