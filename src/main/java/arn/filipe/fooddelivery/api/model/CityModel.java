package arn.filipe.fooddelivery.api.model;

import arn.filipe.fooddelivery.api.model.input.StateIdInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityModel {

    private Long id;
    private String name;
    private StateModel state;
}