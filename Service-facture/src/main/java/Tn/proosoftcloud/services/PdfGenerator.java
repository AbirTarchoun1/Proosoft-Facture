package Tn.proosoftcloud.services;

import Tn.proosoftcloud.entities.Facture;
import Tn.proosoftcloud.repository.IFactureRepository;
import com.itextpdf.text.BaseColor;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;
public class PdfGenerator {
    @Autowired
    IFactureRepository FactureRepository;



    public void generateToClient(List<Facture> facture, HttpServletResponse response) throws DocumentException, IOException {
        // Creating the Object of Document
        Document document = new Document(PageSize.A4);

        // Getting instance of PdfWriter
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        // Opening the created document to change it
        document.open();

        PdfPTable titleTable = new PdfPTable(2);
        titleTable.setWidthPercentage(100f);
        titleTable.setWidths(new int[]{1, 9});

// Adding the image cell
        PdfPCell imageCell = new PdfPCell();
        Image image = Image.getInstance("C:\\Users\\TPDEV1\\Desktop\\PDFFacture\\logo.png");
        image.scaleToFit(100, 100);
        imageCell.addElement(image);
        imageCell.setBorder(Rectangle.NO_BORDER);
        titleTable.addCell(imageCell);

// Adding the "Facture" title cell
        PdfPCell titleCell = new PdfPCell(new Phrase("Facture", FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Color.BLUE)));
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        titleTable.addCell(titleCell);
        Paragraph lineBreak = new Paragraph(" ");
        document.add(lineBreak);
        Paragraph lineBreak2 = new Paragraph(" ");
        document.add(lineBreak2);

// Adding the title table to the document
        document.add(titleTable);

        // Adding the "Titre" outside the table
        for (Facture facturess : facture) {
            Paragraph titreParagraph = new Paragraph("Titre: " + facturess.getTitre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
            titreParagraph.setSpacingBefore(10);
            document.add(titreParagraph);
            Paragraph titreParagraph2 = new Paragraph("Reference: " + facturess.getReference(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
            titreParagraph.setSpacingBefore(10);
            document.add(titreParagraph2);
            Paragraph titreParagraph3 = new Paragraph("Date: " + facturess.getDateFacture(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
            titreParagraph.setSpacingBefore(10);
            document.add(titreParagraph3);

            // Add an empty paragraph for line break
            Paragraph lineBreakk = new Paragraph(" ");
            document.add(lineBreakk);
        }
// Creating a table of 6 columns
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{2, 2, 2,2});
        table.setSpacingBefore(8);

// Create table header cells
        PdfPCell cell = new PdfPCell();
        cell.setPadding(4);
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.WHITE);

// Set cell background color to blue (RGB: 0, 0, 255)
        cell.setBackgroundColor(new Color(0, 0, 255));

        cell.setPhrase(new Phrase("Client", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total_HT", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("TVA", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total_TTC", font));
        table.addCell(cell);

// Add data rows
        for (Facture facture1 : facture) {
            table.addCell(facture1.getClient());
            table.addCell(facture1.getTotal_HT());
            table.addCell(facture1.getTva());
            table.addCell(facture1.getTotal_TTC());
        }

// Add the table to the document
        document.add(table);

// Close the document
        document.close();
        writer.close();
    }
}