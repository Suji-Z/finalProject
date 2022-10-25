package com.project.tour.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tour.domain.Pay;
import com.project.tour.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class SalesExcelService {

    private final PayRepository payRepository;
    private ObjectMapper objectMapper;

    public Object getPackageSales(HttpServletResponse response, boolean excelDownload){
        List<Pay> payList = payRepository.findAll();

        if(excelDownload){
            createExcelDownloadResponse(response, payList);
            return null; //없으면 에러!
        }

        List<Map> payMap = payList.stream()
                .map(pay -> objectMapper.convertValue(pay, Map.class))
                .collect(Collectors.toList());
        return payMap;
    }


    //엑셀 다운로드 구현

    private void createExcelDownloadResponse(HttpServletResponse response, List<Pay> payList) {
        try{
            Workbook workbook = new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet("여행어때매출");

            //숫자 포맷은 아래 numberCellStyle을 적용시킬 것이다다
            CellStyle numberCellStyle = workbook.createCellStyle();
            numberCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

            //파일명
            final String fileName = "여행어때매출";

            //헤더
            final String[] header = {"결제번호","이메일","이름","패키지명","지역1","지역2","출발일","결제 수단","결제 금액"};
            Row row = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
            }

            //바디
            for (int i = 0; i < payList.size(); i++) {
                row = sheet.createRow(i + 1);  //헤더 이후로 데이터가 출력되어야하니 +1

                Pay pay = payList.get(i);

                Cell cell = null;
                cell = row.createCell(0);
                cell.setCellValue(pay.getId());

                cell = row.createCell(1);
                cell.setCellValue(pay.getMember().getEmail());

                cell = row.createCell(2);
                cell.setCellValue(pay.getUserBooking().getMember().getName());

                cell = row.createCell(3);
                cell.setCellValue(pay.getUserBooking().getAPackage().getPackageName());

                cell = row.createCell(4);
                cell.setCellValue(pay.getUserBooking().getAPackage().getLocation1());

                cell = row.createCell(5);
                cell.setCellValue(pay.getUserBooking().getAPackage().getLocation2());

                cell = row.createCell(6);
                cell.setCellValue(pay.getUserBooking().getDeparture());

                cell = row.createCell(7);
                cell.setCellValue(pay.getPayMethod());

                cell = row.createCell(8);
                cell.setCellStyle(numberCellStyle);      //숫자포맷 적용
                cell.setCellValue(pay.getPayTotalPrice());

            }


            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8")+".xlsx");
            //파일명은 URLEncoder로 감싸주는게 좋다!

            workbook.write(response.getOutputStream());
            workbook.close();

        }catch(IOException e){
            e.printStackTrace();
        }



    }
}
