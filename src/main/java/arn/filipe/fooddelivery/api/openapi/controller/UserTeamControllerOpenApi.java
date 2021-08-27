package arn.filipe.fooddelivery.api.openapi.controller;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.model.TeamModel;
import io.swagger.annotations.*;
import java.util.List;

@Api(tags = "Users")
public interface UserTeamControllerOpenApi {

    @ApiOperation("List the teams by user id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid user id", response = ApiError.class),
            @ApiResponse(code = 404, message = "User not found", response = ApiError.class)
    })
    List<TeamModel> listAll(@ApiParam(value = "User id", example = "1", required = true) Long userId);

    @ApiOperation("Associate a team with a user")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associated with success"),
            @ApiResponse(code = 400, message = "Invalid user or team id", response = ApiError.class),
            @ApiResponse(code = 404, message = "User or team not found", response = ApiError.class)
    })
    void associate(@ApiParam(value = "User id", example = "1", required = true) Long userId,
                   @ApiParam(value = "Team id", example = "1", required = true) Long teamId);

    @ApiOperation("Disassociate a team with a user")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Disassociated with success"),
            @ApiResponse(code = 400, message = "Invalid user or team id", response = ApiError.class),
            @ApiResponse(code = 404, message = "User or team not found", response = ApiError.class)
    })
    void disassociate(@ApiParam(value = "User id", example = "1", required = true) Long userId,
                      @ApiParam(value = "Team id", example = "1", required = true) Long teamId);
}