package arn.filipe.fooddelivery.api.v1.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.v1.model.PermissionModel;
import arn.filipe.fooddelivery.api.v1.model.input.PermissionInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Teams")
public interface TeamPermissionControllerOpenApi {

    @ApiOperation("List the teams permissions")
    CollectionModel<PermissionModel> listAll(@ApiParam(value = "Team id", example = "1", required = true) Long teamId);

    @ApiOperation("Register a new team permission")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Permission created with success"),
            @ApiResponse(code = 404, message = "Team not found", response = ApiError.class)
    })
    PermissionModel save(@ApiParam(value = "Team id", example = "1", required = true) Long teamId,
                         @ApiParam(name = "body", value = "Representation of a new team permission", required = true) PermissionInput permissionInput);

    @ApiOperation("Associate a permission with a team")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Team not found", response = ApiError.class)
    })
    ResponseEntity<Void> associate(@ApiParam(value = "Team id", example = "1", required = true) Long teamId,
                                   @ApiParam(value = "Team id", example = "1", required = true) Long permissionId);

    @ApiOperation("Disassociate a permission with a team")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Team not found", response = ApiError.class)
    })
    ResponseEntity<Void> disassociate(@ApiParam(value = "Team id", example = "1", required = true) Long teamId,
                      @ApiParam(value = "Team id", example = "1", required = true) Long permissionId);
}
