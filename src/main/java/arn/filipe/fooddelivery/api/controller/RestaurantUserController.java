package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.BuildLinks;
import arn.filipe.fooddelivery.api.assembler.UserInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.UserModelAssembler;
import arn.filipe.fooddelivery.api.model.PaymentWayModel;
import arn.filipe.fooddelivery.api.model.UserModel;
import arn.filipe.fooddelivery.api.openapi.controller.RestaurantUserControllerOpenApi;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import arn.filipe.fooddelivery.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1/restaurants/{restaurantId}/responsible", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantUserController implements RestaurantUserControllerOpenApi {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private UserInputDisassembler userInputDisassembler;

    @Autowired
    private UserService userService;

    @Autowired
    private BuildLinks buildLinks;

    @GetMapping
    public CollectionModel<UserModel> listAll(@PathVariable Long restaurantId){
        Restaurant restaurant = restaurantService.verifyIfExistsOrThrow(restaurantId);

        CollectionModel<UserModel> usersModel =  userModelAssembler.toCollectionModel(restaurant.getUsers())
                .removeLinks()
                .add(buildLinks.linkToResponsibleRestaurant(restaurantId, "responsibles"))
                .add(buildLinks.linkToResponsibleRestaurantAssociate(restaurantId, "associate"));

        usersModel.getContent().forEach(
                userModel -> {
                    userModel.add(buildLinks.linkToResponsibleRestaurantDisassociate(
                            restaurantId, userModel.getId(), "disassociate"));
                }
        );

        return usersModel;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> associateUser(@PathVariable Long restaurantId, @PathVariable Long userId){
        restaurantService.associateUser(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> disassociateUser(@PathVariable Long restaurantId, @PathVariable Long userId){
        restaurantService.disassociateUser(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }
}
