package arn.filipe.fooddelivery.api.v1.openapi.model;

import arn.filipe.fooddelivery.api.v1.model.RestaurantSummaryModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("RestaurantsSummaryModel")
@Data
public class RestaurantsSummaryModelOpenApi {

    private RestaurantsSummaryEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("RestaurantsSummaryEmbeddedModel")
    @Data
    public class RestaurantsSummaryEmbeddedModelOpenApi{
        private List<RestaurantSummaryModel> restaurantsSummary;
    }
}
