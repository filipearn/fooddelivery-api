package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.BuildLinks;
import arn.filipe.fooddelivery.api.assembler.PaymentWayInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.PaymentWayModelAssembler;
import arn.filipe.fooddelivery.api.model.PaymentWayModel;
import arn.filipe.fooddelivery.api.openapi.controller.RestaurantPaymentWayControllerOpenApi;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.model.PaymentWay;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.service.PaymentWayService;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/restaurants/{restaurantId}/payment-ways",
                produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantPaymentWayController implements RestaurantPaymentWayControllerOpenApi {
    @Autowired
    private PaymentWayService paymentWayService;

    @Autowired
    private PaymentWayInputDisassembler paymentWayInputDisassembler;

    @Autowired
    private PaymentWayModelAssembler paymentWayModelAssembler;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private BuildLinks buildLinks;

    @GetMapping
    public CollectionModel<PaymentWayModel> listAll(@PathVariable Long restaurantId){
        Restaurant restaurant = restaurantService.verifyIfExistsOrThrow(restaurantId);
        CollectionModel<PaymentWayModel> paymentWaysModel
                = paymentWayModelAssembler.toCollectionModel(restaurant.getPaymentWays())
                .removeLinks()
                .add(buildLinks.linkToPaymentWayRestaurant(restaurantId))
                .add(buildLinks.linkToRestaurantPaymentWayAssociation(restaurantId, "associate"));

        paymentWaysModel.getContent().forEach(
            paymentWayModel -> {
                paymentWayModel.add(buildLinks.linkToRestaurantPaymentWayDisassociation(
                                restaurantId, paymentWayModel.getId(), "disassociate"));
            }
        );

        return paymentWaysModel;
    }

    @PutMapping("/{paymentWayId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long restaurantId, @PathVariable Long paymentWayId){

        restaurantService.associatePaymentWay(restaurantId, paymentWayId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{paymentWayId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> disassociate(@PathVariable Long restaurantId, @PathVariable Long paymentWayId){

        restaurantService.disassociatePaymentWay(restaurantId, paymentWayId);
        return ResponseEntity.noContent().build();
    }
}
