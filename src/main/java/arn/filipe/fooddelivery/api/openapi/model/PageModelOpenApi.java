package arn.filipe.fooddelivery.api.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageModel")
@Setter
@Getter
public class PageModelOpenApi {
    @ApiModelProperty(example = "10", value = "Number of registers by page")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total number of registers")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Total number of pages")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Page number (starts with 0)")
    private Long number;
}
