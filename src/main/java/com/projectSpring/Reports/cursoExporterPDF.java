package com.projectSpring.Reports;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.projectSpring.entity.Curso;
import com.sun.net.httpserver.HttpServer;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class cursoExporterPDF {

    private List<Curso> listacursos;

    public cursoExporterPDF(List<Curso> listacursos) {
        this.listacursos = listacursos;
    }

    private void writeTableHeader(PdfPTable pdfTable){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        pdfTable.addCell(cell);
        cell.setPhrase(new Phrase("Titulo", font));
        pdfTable.addCell(cell);
        cell.setPhrase(new Phrase("Descripcion", font));
        pdfTable.addCell(cell);
        cell.setPhrase(new Phrase("Nivel", font));
        pdfTable.addCell(cell);
        cell.setPhrase(new Phrase("Publicado", font));
        pdfTable.addCell(cell);

    }

    private void writeTableData(PdfPTable pdfPTable){
        for (Curso curso : listacursos){
            pdfPTable.addCell(String.valueOf(curso.getId()));
            pdfPTable.addCell(String.valueOf(curso.getTitulo()));
            pdfPTable.addCell(String.valueOf(curso.getDescription()));
            pdfPTable.addCell(String.valueOf(curso.getNivel()));
            pdfPTable.addCell(String.valueOf(curso.isPublicado()));
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista de cursos", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        //f se pone por los espacios en float
        table.setWidths(new float[]{1.3f,3.5f,3.5f,2.0f,1.5f});
        //espaciado
        table.setSpacingBefore(10);


        //cabecera de la tabla
        writeTableHeader(table);
        writeTableData(table);

        //agregamos el documento a la tabla
        document.add(table);
        //cerramos la tabla
        document.close();
    }

}
