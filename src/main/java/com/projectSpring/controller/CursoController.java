package com.projectSpring.controller;




import com.projectSpring.Reports.cursoExporterExcel;
import com.projectSpring.Reports.cursoExporterPDF;
import com.projectSpring.entity.Curso;
import com.projectSpring.repository.CursoRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

@Controller
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public String home(){
        return "redirect:/cursos";
    }

    @GetMapping("/cursos")
    public  String listarCursos(Model model, @Param("keyword") String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size){
        try{
            List<Curso> cursos = new ArrayList<>();
            Pageable pagin = PageRequest.of(page-1,size);

            Page<Curso> pageCurso = null;

            if (keyword ==null){
                pageCurso = cursoRepository.findAll(pagin);
            }else{
                pageCurso = cursoRepository.findByTituloContainingIgnoreCase(keyword,pagin);
                model.addAttribute("keyword",keyword);
            }
            cursos = pageCurso.getContent();
            model.addAttribute("cursos",cursos);
            model.addAttribute("currentPage", pageCurso.getNumber() + 1);
            model.addAttribute("totalItem", pageCurso.getTotalElements());
            model.addAttribute("totalPages", pageCurso.getTotalPages());
            model.addAttribute("pageSize", size);
        }catch(Exception e){
            model.addAttribute("message", e.getMessage());
        }
        return "cursos";
    }

    @GetMapping("/cursos/nuevo")
    public String agregarCursos(Model model){
        Curso curso = new Curso();
        curso.setPublicado(true);

        model.addAttribute("curso",curso);
        model.addAttribute("pageTitle","Nuevo curso");

        return "curso_form";
    }
    @PostMapping("/cursos/save")
    public String guardarCursos(Curso curso , RedirectAttributes redirectAttributes){
        try {
            cursoRepository.save(curso);
            redirectAttributes.addFlashAttribute("message","El curso se ha guardado con exito");

        }catch (Exception e) {
            redirectAttributes.addAttribute("message",e.getMessage());

        }
        return "redirect:/cursos";
    }

    @GetMapping("/cursos/editar/{id}")
    public String editarCurso(@PathVariable Integer id, Model model , RedirectAttributes redirectAttributes){
        try {
           Curso curso = cursoRepository.findById(id).get();
           model.addAttribute("curso", curso);
           model.addAttribute("pageTitle","Editar curso : "+ id);
          return "curso_form";

        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());

        }
        return "redirect:/cursos";
    }

    @GetMapping("/cursos/eliminar/{id}")
    public String eliminarCursos(@PathVariable Integer id, Model model , RedirectAttributes redirectAttributes){
        try {
            cursoRepository.deleteById(id);
            redirectAttributes.addAttribute("message","curso eliminado con exito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/cursos";
    }
    @GetMapping("/export/pdf")
    public  void  generarReportePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "content-Disposition";
        String headerValue = "attachment; filename=cursos"+ currentDateTime +".pdf";

        response.setHeader(headerKey, headerValue);

        List<Curso>cursos = cursoRepository.findAll();

        cursoExporterPDF exporterPDF = new cursoExporterPDF(cursos);
        exporterPDF.export(response);
    }

    @GetMapping("/export/excel")
    public  void  generarReporteExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");//decir que es excel
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "content-Disposition";
        String headerValue = "attachment; filename=cursos"+ currentDateTime +".xlsx";

        response.setHeader(headerKey, headerValue);

        List<Curso>cursos = cursoRepository.findAll();

        cursoExporterExcel exporterPDF = new cursoExporterExcel(cursos);
        exporterPDF.export(response);
    }
    
}
