package com.projectSpring.Reports;

import com.projectSpring.entity.Curso;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class cursoExporterExcel {

    private XSSFWorkbook workbook;

    private XSSFSheet sheet;

    private List<Curso> cursos;

    public cursoExporterExcel(List<Curso> cursos) {
        this.cursos = cursos;
        workbook =new XSSFWorkbook();
    }

    private  void  writeHeader(){
        sheet = workbook.createSheet("cursos");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row,0,"ID",style);
        createCell(row,1,"TITULO",style);
        createCell(row,2,"DESCRIPCION",style);
        createCell(row,3,"NIVEL",style);
        createCell(row,4,"ESTADO",style);

    }

    private  void createCell(Row row, int columnCount,Object value, CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer){
            cell.setCellValue((Integer)value);
        }else if(value instanceof Boolean){
            cell.setCellValue((Boolean)value);
        } else{
            cell.setCellValue((String)value);
        }
            cell.setCellStyle(style);
        }
    private void writeDataLines(){
        int rowCount=1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();

        font.setFontHeight(14);
        style.setFont(font);

        for (Curso curso:cursos){//3
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row,columnCount++,curso.getId(),style);
            createCell(row,columnCount++,curso.getTitulo(),style);
            createCell(row,columnCount++,curso.getDescription(),style);
            createCell(row,columnCount++,curso.getNivel(),style);
            createCell(row,columnCount++,curso.isPublicado(),style);
        }
    }

    public void export(HttpServletResponse servletResponse) throws IOException {
        writeHeader();
        writeDataLines();


        ServletOutputStream outputStream = servletResponse.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
    }


