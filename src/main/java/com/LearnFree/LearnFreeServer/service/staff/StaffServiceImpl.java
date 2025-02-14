package com.LearnFree.LearnFreeServer.service.staff;

import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import com.LearnFree.LearnFreeServer.entity.RoleEnum;
import com.LearnFree.LearnFreeServer.entity.UserAccount;
import com.LearnFree.LearnFreeServer.entity.UserAuthentication;
import com.LearnFree.LearnFreeServer.repository.UserAccountRepository;
import com.LearnFree.LearnFreeServer.repository.UserAuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final UserAccountRepository userAccountRepository;
    private final UserAuthenticationRepository userAuthenticationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseDTO addStudents(MultipartFile file, String department, Integer
            academicYear) {
        if (!isValidExcelFile(file)) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("Invalid Excel file format")
                    .build();
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
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

                UserAccount userAccount = new UserAccount();
                String email = null;
                String regNo = null;

                Iterator<Cell> cellIterator = row.cellIterator();
                int cellIndex = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0 -> userAccount.setFirstName(getCellValueAsString(cell));
                        case 1 -> userAccount.setLastName(getCellValueAsString(cell));
                        case 2 -> userAccount.setGender(getCellValueAsString(cell));
                        case 3 -> userAccount.setMobileNumber(getCellValueAsString(cell));
                        case 4 -> email = getCellValueAsString(cell);
                        case 5 -> {
                            regNo = getCellValueAsString(cell);
                            userAccount.setRegistrationNumber(regNo);
                        }
                        case 6 -> userAccount.setAcademicYear(Integer.parseInt(getCellValueAsString(cell)));
                        case 7 -> userAccount.setSemester(Integer.parseInt(getCellValueAsString(cell)));
                    }
                    cellIndex++;
                }

                if (email == null || regNo == null || userAuthenticationRepository.existsByEmail(email)) {
                    continue;
                }

                userAccount.setDepartment(department);
                userAccount.setAcademicYear(academicYear);

                UserAccount savedUser = userAccountRepository.save(userAccount);

                UserAuthentication auth = UserAuthentication.builder()
                        .userId(savedUser.getId())
                        .email(email)
                        .password(passwordEncoder.encode(regNo))
                        .role(RoleEnum.STUDENT)
                        .build();
                userAuthenticationRepository.save(auth);
            }

            return ResponseDTO.builder()
                    .status(true)
                    .message("Students added successfully")
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