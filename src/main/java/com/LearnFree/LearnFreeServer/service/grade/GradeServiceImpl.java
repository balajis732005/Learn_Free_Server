package com.LearnFree.LearnFreeServer.service.grade;

import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import com.LearnFree.LearnFreeServer.entity.GradeData;
import com.LearnFree.LearnFreeServer.repository.GradeDataRepository;
import com.LearnFree.LearnFreeServer.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService{

    private final UserAccountRepository userAccountRepository;
    private final GradeDataRepository gradeDataRepository;

    @Override
    public ResponseDTO studentsGradeAdd(
            MultipartFile gradeSheet,
            String exam_name,
            String subject_name,
            String  department
    ) {

        if (!isValidExcelFile(gradeSheet)) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("Invalid Excel file format")
                    .build();
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook(gradeSheet.getInputStream())) {
            XSSFSheet sheet = workbook.getSheet(department);
            if (sheet == null) {
                return ResponseDTO.builder()
                        .status(false)
                        .message("Department sheet not found")
                        .build();
            }

            Iterator<Row> rowIterator = sheet.iterator();
            int rowIndex = 0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (rowIndex++ == 0) continue;

                GradeData studentGrade = new GradeData();

                Iterator<Cell> cellIterator = row.cellIterator();
                int cellIndex = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0 -> studentGrade.setRegistrationNumber(getCellValueAsString(cell));
                        case 1 -> studentGrade.setGrade(getCellValueAsString(cell));
                        case 2 -> studentGrade.setMark(Double.parseDouble(getCellValueAsString(cell)));
                    }
                    cellIndex++;
                }

                studentGrade.setExamName(exam_name);
                studentGrade.setSubjectName(subject_name);
                studentGrade.setUserId(
                        userAccountRepository.findByRegistrationNumber(
                                studentGrade.getRegistrationNumber()).getId()
                );

                gradeDataRepository.save(studentGrade);

            }

            return ResponseDTO.builder()
                    .status(true)
                    .message("Students Grade added successfully")
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseDTO.builder()
                    .status(false)
                    .message("Error processing Excel file")
                    .build();
        }

    }

    private String getCellValueAsString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> "";
        };
    }

    private boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(),
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

}
