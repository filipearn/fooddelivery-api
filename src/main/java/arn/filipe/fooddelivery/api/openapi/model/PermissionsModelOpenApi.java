package arn.filipe.fooddelivery.api.openapi.model;

import arn.filipe.fooddelivery.api.model.CityModel;
import arn.filipe.fooddelivery.api.model.PermissionModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PermissionsModel")
@Data
public class PermissionsModelOpenApi {

    private PermissionsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("PermissionsEmbeddedModel")
    @Data
    public class PermissionsEmbeddedModelOpenApi{
        private List<PermissionModel> permissions;
    }
}
