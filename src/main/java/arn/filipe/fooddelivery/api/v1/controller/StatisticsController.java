package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.openapi.controller.StatisticsControllerOpenApi;
import arn.filipe.fooddelivery.core.security.CheckSecurity;
import arn.filipe.fooddelivery.domain.filter.DailySaleFilter;
import arn.filipe.fooddelivery.domain.model.dto.DailySale;
import arn.filipe.fooddelivery.domain.service.SaleQueryService;
import arn.filipe.fooddelivery.domain.service.SaleReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/statistics")
public class StatisticsController implements StatisticsControllerOpenApi {

    @Autowired
    private SaleReportService saleReportService;

    @Autowired
    private SaleQueryService saleQueryService;

    @Autowired
    private BuildLinks buildLinks;

    private static class StatisticsEntryPointModel extends RepresentationModel<StatisticsEntryPointModel> {

    }

    @CheckSecurity.Statistics.CanFind
    @GetMapping
    public StatisticsEntryPointModel root(DailySaleFilter dailySaleFilter,
                                          @RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
        var statisticsEntryPointModel = new StatisticsEntryPointModel();

        statisticsEntryPointModel.add(buildLinks.linkToDailySales("daily-sales"));

        return statisticsEntryPointModel;
    }

    @CheckSecurity.Statistics.CanFind
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySale> findDailySales(DailySaleFilter dailySaleFilter,
                                          @RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
        return saleQueryService.findDailySales(dailySaleFilter, timeOffset);
    }

    @CheckSecurity.Statistics.CanFind
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> findDailySalesPDF(DailySaleFilter dailySaleFilter,
                                            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) throws JRException {

        byte[] bytesPDF = saleReportService.emitDailySales(dailySaleFilter, timeOffset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPDF);
    }
}
