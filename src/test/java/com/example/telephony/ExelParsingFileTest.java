package com.example.telephony;

import com.example.telephony.domain.CallersBase;
import com.example.telephony.exception.FileParsingException;
import com.example.telephony.service.file.FileParseService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
public class ExelParsingFileTest {
    @Autowired
    private FileParseService fileParseService;

    private final static String RELATIVE_WAY = "str\\test\\java\\com\\example\\telephony\\data\\exel";
    private final static String RELATIVE_WAY_CORRECT_FILES = "\\correct";
    private final static String RELATIVE_WAY_WRONG_FILES = "\\wrong";

    private final Path absolutePathCorrectFiles;
    private final Path absolutePathWrongFiles;

    public ExelParsingFileTest(Path absolutePathCorrectFiles) {
        this.absolutePathCorrectFiles = Paths.get(RELATIVE_WAY + RELATIVE_WAY_CORRECT_FILES).toAbsolutePath();
        this.absolutePathWrongFiles = Paths.get(RELATIVE_WAY + RELATIVE_WAY_CORRECT_FILES).toAbsolutePath();
    }

    @Test
    public void simpleFile_Ok() {
        InputStream inputStream = getCorrectFile("simple");
        CallersBase callersBase = fileParseService.parseExelToCallersBase(inputStream);

    }

    private InputStream getCorrectFile(String fileName) {
        try {
            return Files.newInputStream(absolutePathCorrectFiles.resolve(fileName));
        } catch (IOException e) {
            throw new FileParsingException("Test");
        }
    }
}
