package com.example.telephony;

import com.example.telephony.common.CallersBaseBuilder;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.enums.FileParsingExceptionMessage;
import com.example.telephony.exception.FileParsingException;
import com.example.telephony.service.file.FileParseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@SpringBootTest
public class ExelParsingFileTest {
    @Autowired
    private FileParseService fileParseService;

    private final static String RELATIVE_WAY = "src\\test\\java\\com\\example\\telephony\\data\\exel";
    private final static String CORRECT_FILES = "correct";
    private final static String WRONG_FILES = "wrong";
    private final static String NOT_VALID_FILES = "not_valid";

    private final Path correctFiles;
    private final Path wrongFiles;
    private final Path notValidFiles;

    public ExelParsingFileTest() {
        this.correctFiles = Paths.get(RELATIVE_WAY).resolve(CORRECT_FILES).toAbsolutePath();
        this.wrongFiles = Paths.get(RELATIVE_WAY).resolve(WRONG_FILES).toAbsolutePath();
        this.notValidFiles = Paths.get(RELATIVE_WAY).resolve(NOT_VALID_FILES).toAbsolutePath();
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

    @Test
    public void correctCellTypes_Ok() {
        InputStream inputStream = getCorrectFile("correct_cell_type.xlsx");
        CallersBase actual = fileParseService.parseExelToCallersBase(inputStream);

        CallersBase expected = new CallersBaseBuilder(Arrays.asList("номер телефона", "test", "test_1", "one two three", "date"))
                .addPhoneColumnName("номер телефона")
                .addCallerRow(Arrays.asList("123.0", "dcewa", "ces", "123.0", "44510.0"), true)
                .addCallerRow(Arrays.asList("8(12)", "cesa", "dcvds", "dcs", "cvds"), true)
                .build();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void smallTable_Ok() {
        InputStream inputStream = getCorrectFile("small_table.xlsx");
        CallersBase actual = fileParseService.parseExelToCallersBase(inputStream);

        CallersBase expected = new CallersBaseBuilder(Arrays.asList("phone number"))
                .addPhoneColumnName("phone number")
                .addCallerRow(Arrays.asList("1234.0"), true)
                .addCallerRow(Arrays.asList("12(3)4"), true)
                .build();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void emptyRow_Ok() {
        InputStream inputStream = getCorrectFile("empty_row.xlsx");
        CallersBase actual = fileParseService.parseExelToCallersBase(inputStream);

        CallersBase expected = new CallersBaseBuilder(Arrays.asList("phone", "test", "test_1"))
                .addPhoneColumnName("phone")
                .addCallerRow(Arrays.asList("test", "test", "test"), true)
                .build();

        Assertions.assertEquals(expected, actual);
    }

    private InputStream getCorrectFile(String fileName) {
        try {
            Path path = correctFiles.resolve(fileName);
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new FileParsingException(e.getMessage());
        }
    }

    @Test
    public void emptyFile_Wrong() {
        executeWrongTest("empty_file.xlsx");
    }

    @Test
    public void cellWithOneSpaceInHeader_Wrong() {
        executeWrongTest("cell_with_only_space_in_header.xlsx");
    }

    @Test
    public void dataHaveRowWithMoreColumnsInMiddle_Wrong() {
        executeWrongTest("data_fave_more_columns_middle_row.xlsx");
    }

    @Test
    public void dataHaveRowWithMoreColumnsInFirstRow_Wrong() {
        executeWrongTest("data_have_more_columns_first_row.xlsx");
    }

    @Test
    public void emptyHeadersCellInMiddle_Wrong() {
        executeWrongTest("empty_cell_in_middle_in_headers.xlsx");
    }

    @Test
    public void emptyFirstHeadersCell_Wrong() {
        executeWrongTest("empty_first_cell_in_headers.xlsx");
    }

    @Test
    public void onlyOneRowInFile_Wrong() {
        executeWrongTest("one_row.xlsx");
    }

    @Test
    public void twoEqualsNameColumn_Wrong() {
        executeWrongTest("two_equals_name_column.xlsx");
    }

    @Test
    public void twoEqualsNamePhoneColumn_Wrong() {
        executeWrongTest("two_equals_name_phone_column.xlsx");
    }

    private void executeWrongTest(String fileName) {
        InputStream inputStream = getWrongFile(fileName);
        Assertions.assertThrows(FileParsingException.class, () -> fileParseService.parseExelToCallersBase(inputStream));
    }

    private InputStream getWrongFile(String fileName) {
        try {
            Path path = wrongFiles.resolve(fileName);
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new FileParsingException(e.getMessage());
        }
    }

    @Test
    public void oneLeftCellIsEmpty_Ok() {
        InputStream inputStream = getNotValidFile("one_cell_empty_in_left.xlsx");
        CallersBase actual = fileParseService.parseExelToCallersBase(inputStream);

        CallersBase expected = new CallersBaseBuilder(Arrays.asList("phone", "test", "test_1"))
                .addPhoneColumnName("phone")
                .addCallerRow(Arrays.asList(fileParseService.NOT_VALID_VALUE, "test", "test"), false)
                .build();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void oneMiddleCellIsEmpty_Ok() {
        InputStream inputStream = getNotValidFile("one_cell_empty_in_middle.xlsx");
        CallersBase actual = fileParseService.parseExelToCallersBase(inputStream);

        CallersBase expected = new CallersBaseBuilder(Arrays.asList("phone", "test", "test_1"))
                .addPhoneColumnName("phone")
                .addCallerRow(Arrays.asList("test", fileParseService.NOT_VALID_VALUE, "test"), false)
                .build();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void oneRightCellIsEmpty_Ok() {
        InputStream inputStream = getNotValidFile("one_cell_empty_in_right.xlsx");
        CallersBase actual = fileParseService.parseExelToCallersBase(inputStream);

        CallersBase expected = new CallersBaseBuilder(Arrays.asList("phone", "test", "test_1"))
                .addPhoneColumnName("phone")
                .addCallerRow(Arrays.asList("test", "test", fileParseService.NOT_VALID_VALUE), false)
                .build();

        Assertions.assertEquals(expected, actual);
    }

    private InputStream getNotValidFile(String fileName) {
        try {
            Path path = notValidFiles.resolve(fileName);
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new FileParsingException(e.getMessage());
        }
    }
}
