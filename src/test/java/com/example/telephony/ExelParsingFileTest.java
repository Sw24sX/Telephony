package com.example.telephony;

import com.example.telephony.common.CallersBaseBuilder;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.exception.FileParsingException;
import com.example.telephony.service.file.FileParseService;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ExelParsingFileTest {
    @Autowired
    private FileParseService fileParseService;

    private final static String RELATIVE_WAY = "src\\test\\java\\com\\example\\telephony\\data\\exel";
    private final static String RELATIVE_WAY_CORRECT_FILES = "correct";
    private final static String RELATIVE_WAY_WRONG_FILES = "wrong";

    private final Path absolutePathCorrectFiles;
    private final Path absolutePathWrongFiles;

    public ExelParsingFileTest() {
        this.absolutePathCorrectFiles = Paths.get(RELATIVE_WAY).resolve(RELATIVE_WAY_CORRECT_FILES).toAbsolutePath();
        this.absolutePathWrongFiles = Paths.get(RELATIVE_WAY).resolve(RELATIVE_WAY_WRONG_FILES).toAbsolutePath();
    }

    @Test
    public void simpleFile_Ok() {
        InputStream inputStream = getCorrectFile("simple.xlsx");
        CallersBase actual = fileParseService.parseExelToCallersBase(inputStream);

        CallersBase expected = new CallersBaseBuilder(Arrays.asList("phone", "test", "test_1"))
                .addPhoneColumnName("phone")
                .addCallerRow(Arrays.asList("8(12)123", "name", "surname"), true)
                .addCallerRow(Arrays.asList("890(123)", "neam", "searname"), true)
                .build();

        Assertions.assertEquals(expected, actual);
    }

    private InputStream getCorrectFile(String fileName) {
        try {
            Path path = absolutePathCorrectFiles.resolve(fileName);
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new FileParsingException(e.getMessage());
        }
    }
}
