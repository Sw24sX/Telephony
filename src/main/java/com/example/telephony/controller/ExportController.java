package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.service.ExportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping(GlobalMapping.API + "export")
@Api("Operations pertaining to export results")
public class ExportController {
    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("results/{dialing_id}")
    @ApiOperation("Export dialing result to xlsx file")
    public ResponseEntity<ByteArrayResource> exportXlsxDialingResult(@PathVariable("dialing_id") Long id,
                                                         HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/xlsx");
//        String fileName = String.format("results_%s.xlsx", id);
//        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        XSSFWorkbook workbook = exportService.createResultFile(id); // creates the workbook
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        String fileName = String.format("results_%s.xlsx", id);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        workbook.write(stream);
        workbook.close();
        return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                header, HttpStatus.CREATED);
    }
}
