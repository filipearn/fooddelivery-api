package arn.filipe.fooddelivery.api.openapi.model;

import arn.filipe.fooddelivery.api.model.RestaurantModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("RestaurantsModel")
@Data
public class RestaurantsModelOpenApi {

    private RestaurantsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("RestaurantsEmbeddedModel")
    @Data
    public class RestaurantsEmbeddedModelOpenApi{
        private List<RestaurantModel> restaurants;
    }
}
