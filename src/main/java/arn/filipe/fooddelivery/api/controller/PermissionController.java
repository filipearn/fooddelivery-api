package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.PermissionModelAssembler;
import arn.filipe.fooddelivery.api.model.PermissionModel;
import arn.filipe.fooddelivery.api.openapi.controller.PermissionControllerOpenApi;
import arn.filipe.fooddelivery.domain.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
@RestController
@RequestMapping(path = "/api/v1/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController implements PermissionControllerOpenApi {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @Override
    @GetMapping
    public CollectionModel<PermissionModel> listAll(){
        return permissionModelAssembler.toCollectionModel(permissionService.listAll());
    }
}
