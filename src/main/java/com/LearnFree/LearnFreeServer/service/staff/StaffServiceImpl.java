package com.LearnFree.LearnFreeServer.service.staff;

import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import com.LearnFree.LearnFreeServer.entity.UserAccount;
import com.LearnFree.LearnFreeServer.entity.UserAuthentication;
import com.LearnFree.LearnFreeServer.repository.UserAccountRepository;
import com.LearnFree.LearnFreeServer.repository.UserAuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final UserAccountRepository userAccountRepository;
    private final UserAuthenticationRepository userAuthenticationRepository;

    @Override
    public ResponseDTO addStudents(MultipartFile file, String department) {

        if(!isValidExcelFile(file)){
            return ResponseDTO.builder()
                    .status(false)
                    .message("This is not a valid Excel file")
                    .build();
        }

        List<UserAccount> students = new ArrayList<>();
        List<UserAuthentication> authStudents = new ArrayList<>();

        try{
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheet(department);
            int rowIndex=0;
            for(Row row : sheet){
                if(rowIndex==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex=0;
                UserAccount eachStudent = new UserAccount();
                UserAuthentication authStudent = new UserAuthentication();
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 1 -> eachStudent.setFirstName(cell.getStringCellValue());
                        case 2 -> eachStudent.setLastName(cell.getStringCellValue());
                        case 3 -> eachStudent.setGender(cell.getStringCellValue());
                        case 4 -> eachStudent.setAge(Integer.parseInt(cell.getStringCellValue()));
                        case 5 -> eachStudent.setMobileNumber(cell.getStringCellValue());
                        case 6 -> authStudent.setEmail(cell.getStringCellValue());
                        case 7 -> authStudent.setPassword(cell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                if(userAuthenticationRepository.existsByEmail(authStudent.getEmail())){
                    continue;
                }
                students.add(eachStudent);
                authStudents.add(authStudent);
            }
            userAccountRepository.saveAll(students);
            userAuthenticationRepository.saveAll(authStudents);
        } catch(IOException e)  {
            e.getStackTrace();
        }

        return ResponseDTO.builder()
                .status(true)
                .message("Excel Data Saved to DataBase Successfully")
                .build();
    }

    private boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(),
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

}
