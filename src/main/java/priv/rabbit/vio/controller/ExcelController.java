package priv.rabbit.vio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.rabbit.vio.service.impl.PoiExcelService;

import java.io.IOException;

@RestController
public class ExcelController {

    @Autowired
    PoiExcelService poiExcelService;

    @GetMapping("/getExcelTemplate")
    public void getExcelTemplate() throws IOException {
        poiExcelService.doProcess();
    }
}
