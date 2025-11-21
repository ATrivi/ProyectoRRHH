package com.example.proyectoRecursosHumanos.service;

import com.example.proyectoRecursosHumanos.model.Payroll;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

@Service
public class PdfGenerationService {

    //array de bytes para generar el pdf del recibo de pago
    public byte[] generarReciboPagoPdf(Payroll payroll) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            //Estilos
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font encabezadoFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font textoFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font netoFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);


            //Contenido

            Paragraph titulo = new Paragraph("RECIBO DE NÓMINA", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("----------------------------------------------------------------------"));
            document.add(new Paragraph("\n"));

            //Detalles del Empleado
            document.add(new Paragraph("DATOS DEL EMPLEADO", encabezadoFont));
            document.add(new Paragraph("Nombre: " + payroll.getEmpleado().getNombre(), textoFont));
            document.add(new Paragraph("ID Empleado: " + payroll.getEmpleado().getId(), textoFont));
            document.add(new Paragraph("Fecha de Pago: " + payroll.getFechaPago().toString(), textoFont));

            document.add(new Paragraph("\n"));

            //Detalles Financieros
            document.add(new Paragraph("DETALLE DE INGRESOS Y DEDUCCIONES", encabezadoFont));
            document.add(new Paragraph("Salario Base: " + payroll.getSalarioBase().toString() + " EUR", textoFont));
            document.add(new Paragraph("Complementos: " + payroll.getComplementos().toString() + " EUR", textoFont));

            document.add(new Paragraph("\n"));

            //Suma de Deducciones e Impuestos para mostrar el total restado
            BigDecimal totalDeducido = payroll.getDeducciones().add(payroll.getImpuestos());
            document.add(new Paragraph("Total Deducciones e Impuestos: " + totalDeducido.toString() + " EUR", textoFont));

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("----------------------------------------------------------------------"));
            document.add(new Paragraph("\n"));

            //Total Neto
            Paragraph neto = new Paragraph("SALARIO NETO A PAGAR: " + payroll.getSalarioNeto().toString() + " EUR", netoFont);
            neto.setAlignment(Element.ALIGN_RIGHT);
            document.add(neto);

            document.close();

            //Devuelve el contenido
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar el recibo de pago en PDF.", e);
        }
    }
}