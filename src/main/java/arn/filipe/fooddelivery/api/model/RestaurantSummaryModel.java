package arn.filipe.fooddelivery.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurants")
@Getter
@Setter
public class RestaurantSummaryModel extends RepresentationModel<RestaurantSummaryModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Komb espeteria")
    private String name;

    @ApiModelProperty(example = "9.99")
    private BigDecimal freightRate;

    private KitchenModel kitchen;
}
