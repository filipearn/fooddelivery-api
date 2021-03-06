package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.assembler.PhotoProductModelAssembler;
import arn.filipe.fooddelivery.api.v1.model.PhotoProductModel;
import arn.filipe.fooddelivery.api.v1.model.input.PhotoProductInput;
import arn.filipe.fooddelivery.api.v1.openapi.controller.RestaurantProductPhotoControllerOpenApi;
import arn.filipe.fooddelivery.core.security.CheckSecurity;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.PhotoProduct;
import arn.filipe.fooddelivery.domain.model.Product;
import arn.filipe.fooddelivery.domain.service.PhotoProductService;
import arn.filipe.fooddelivery.domain.service.PhotoStorageService;
import arn.filipe.fooddelivery.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/restaurants/{restaurantId}/products/{productId}/photo",
produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductPhotoController implements RestaurantProductPhotoControllerOpenApi {

    @Autowired
    private ProductService productService;

    @Autowired
    private PhotoProductService photoProductService;

    @Autowired
    private PhotoProductModelAssembler photoProductModelAssembler;

    @CheckSecurity.Restaurants.CanFind
    @Override
    @GetMapping
    public PhotoProductModel find(@PathVariable Long restaurantId,
                                  @PathVariable Long productId) throws Exception {
        Product product = productService.verifyIfExistsOrThrow(restaurantId, productId);

        PhotoProduct photoProduct = photoProductService.find(restaurantId, productId);

        return photoProductModelAssembler.toModel(photoProduct);
    }

    @CheckSecurity.Restaurants.CanFind
    @Override
    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servePhoto(@PathVariable Long restaurantId,
                                                          @PathVariable Long productId,
                                                          @RequestHeader(name = "accept") String acceptHeader) throws Exception {
        try{
            Product product = productService.verifyIfExistsOrThrow(restaurantId, productId);

            PhotoProduct photoProduct = photoProductService.find(restaurantId, productId);

            List<MediaType> mediaTypeAllowed = MediaType.parseMediaTypes(acceptHeader);
            MediaType mediaTypePhoto = MediaType.parseMediaType(photoProduct.getContentType());

            verifyMediaTypesCompatibility(mediaTypePhoto, mediaTypeAllowed);

            PhotoStorageService.RecoveredPhoto recoveredPhoto = photoProductService.servePhoto(restaurantId, productId);

            if(recoveredPhoto.hasUrl()){
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, recoveredPhoto.getUrl())
                        .build();
            }
            else {
                return ResponseEntity.ok()
                        .contentType(mediaTypePhoto)
                        .body(new InputStreamResource(recoveredPhoto.getInputStream()));
            }
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

    private void verifyMediaTypesCompatibility(MediaType mediaTypePhoto, List<MediaType> mediaTypeAllowed) throws HttpMediaTypeNotAcceptableException {
        boolean compatible = mediaTypeAllowed.stream()
                .anyMatch(mediaTypeAccepted -> mediaTypeAccepted.isCompatibleWith(mediaTypePhoto));

        if(!compatible){
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAllowed);
        }
    }

    @CheckSecurity.Restaurants.CanManageOperation
    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PhotoProductModel updatePhoto(@PathVariable Long restaurantId,
                                         @PathVariable Long productId,
                                         @Valid PhotoProductInput productPhotoInput) throws IOException {

        Product product = productService.verifyIfExistsOrThrow(restaurantId, productId);

        MultipartFile file = productPhotoInput.getFile();

        PhotoProduct photo = new PhotoProduct();
        photo.setProduct(product);
        photo.setDescription(productPhotoInput.getDescription());
        photo.setContentType(file.getContentType());
        photo.setSize(file.getSize());
        photo.setFileName(file.getOriginalFilename());

        return photoProductModelAssembler.toModel(photoProductService.save(photo, file.getInputStream()));
    }

    @CheckSecurity.Restaurants.CanManageOperation
    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@PathVariable Long restaurantId,
                                         @PathVariable Long productId){
        photoProductService.deletePhoto(restaurantId, productId);
    }
}
