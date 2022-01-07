package com.example.telephony.service;

import com.example.telephony.domain.callers.base.CallerVariable;
import com.example.telephony.domain.callers.base.CallersBase;
import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.domain.dialing.DialingCallerResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportService {
    private final DialingService dialingService;
    private final CallerBaseService callerBaseService;

    public ExportService(DialingService dialingService, CallerBaseService callerBaseService) {
        this.dialingService = dialingService;
        this.callerBaseService = callerBaseService;
    }

    public XSSFWorkbook createResultFile(long dialingId) {
        Dialing dialing = dialingService.getById(dialingId);
        XSSFWorkbook workbook = initUserWorkbook(callerBaseService.getListColumnsName(dialing.getCallersBaseId()));
        Sheet sheet = workbook.getSheetAt(0);

        for (DialingCallerResult callResult : dialingService.getResultsCallerByDialing(dialing)) {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);

            // TODO: 07.01.2022 get by pages
            for (CallerVariable variable : callResult.getCaller().getVariables()) {
                int rowNumber = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
                row.createCell(rowNumber).setCellValue(variable.getValue());
            }
            row.createCell(row.getLastCellNum()).setCellValue(StringUtils.join(callResult.getAnswers(), ";"));
            row.createCell(row.getLastCellNum()).setCellValue(callResult.getMessage());
        }

        return workbook;
    }

    private XSSFWorkbook initUserWorkbook(List<String> columnsNameCallersBase) {
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(0);
        for (int i = 0; i < columnsNameCallersBase.size(); i++) {
            row.createCell(i).setCellValue(columnsNameCallersBase.get(i));
        }

        row.createCell(row.getLastCellNum()).setCellValue("Answers");
        row.createCell(row.getLastCellNum()).setCellValue("Error messages");
        return wb;
    }
}
