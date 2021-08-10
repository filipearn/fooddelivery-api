package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.repository.KitchenRepository;
import arn.filipe.fooddelivery.domain.repository.RestaurantRepository;
import arn.filipe.fooddelivery.infrastructure.repository.spec.RestaurantSpecFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRepository kitchenRepository;

    public Restaurant save(Restaurant restaurant){
        Long kitchenId = restaurant.getKitchen().getId();

        Kitchen kitchen = kitchenRepository.findById(kitchenId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Kitchen with id %d not found.", kitchenId)));

        restaurant.setKitchen(kitchen);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> listAll(){
        return restaurantRepository.findAll();
    }

    public Restaurant findById(Long id){
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                    String.format("Restaurant with id %d not found.", id)));
    }

    public List<Restaurant> findByNameAndKitchen(String name, Long kitchenId){
        return restaurantRepository.queryByNameAndKitchen(name, kitchenId);
    }

    public List<Restaurant> findByNameAndfreighRate(String name, BigDecimal freighRateInicial, BigDecimal freighRateFinal){
        return restaurantRepository.customizedFind(name, freighRateInicial, freighRateFinal);
    }

    public List<Restaurant> withFreeShipping(String name){
        return restaurantRepository.withFreeShipping(name);
    }

    public Restaurant update(Long id, Restaurant restaurant){
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenRepository.findById(kitchenId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Kitchen with id %d not found.", kitchenId)));

        Restaurant restaurantToUpdate = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Restaurant with id %d not found.", id)));

        BeanUtils.copyProperties(restaurant, restaurantToUpdate, "id", "paymentWay", "address", "registrationDate", "products");

        restaurantToUpdate.setKitchen(kitchen);

        return restaurantRepository.save(restaurantToUpdate);
    }


    public void delete(Long id) {
        restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Restaurant with id %d not found.", id)));

        try {
            restaurantRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("Restaurant with id %d can't be removed. Resource in use.", id));
        }
    }

    public Restaurant findFirst() {
        return restaurantRepository.findFirst().orElseThrow(
                () -> new EmptyResultDataAccessException(
                        String.format("No restaurant found."), 1));
    }
}
