package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.BuildLinks;
import arn.filipe.fooddelivery.api.controller.RestaurantProductPhotoController;
import arn.filipe.fooddelivery.api.model.PhotoProductModel;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.model.PhotoProduct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PhotoProductModelAssembler extends RepresentationModelAssemblerSupport<PhotoProduct, PhotoProductModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    public PhotoProductModelAssembler(){
        super(RestaurantProductPhotoController.class, PhotoProductModel.class);
    }

    public PhotoProductModel toModel(PhotoProduct photoProduct){
        PhotoProductModel photoProductModel = modelMapper.map(photoProduct, PhotoProductModel.class);

        try {
            photoProductModel.add(
                   buildLinks.linkToRestaurantProductPhoto(
                           photoProduct.getProduct().getRestaurant().getId(),
                           photoProduct.getProduct().getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        photoProductModel.add(
                buildLinks.linkToRestaurantProducts(
                        photoProduct.getProduct().getRestaurant().getId(),
                        photoProduct.getProduct().getId(), "product"));
        return photoProductModel;
    }

    public List<PhotoProductModel> toCollectionModel(Collection<PhotoProduct> photoProducts){
        return photoProducts.stream()
                .map(photoProduct -> toModel(photoProduct))
                .collect(Collectors.toList());
    }
}
