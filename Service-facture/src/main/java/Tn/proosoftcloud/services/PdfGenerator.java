package Tn.proosoftcloud.services;

import Tn.proosoftcloud.entities.Facture;
import Tn.proosoftcloud.repository.IFactureRepository;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
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

        PdfWriter.getInstance(document, response.getOutputStream());
        // Opening the created document to change it

        document.open();

        // Creating font

        // Setting font style and size

        Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);

        fontTiltle.setSize(15);
        fontTiltle.setColor(Color.DARK_GRAY);
        // Creating paragraph
        Paragraph paragraph1 = new Paragraph("Facture", fontTiltle);
        // Aligning the paragraph in the document

        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);

        // Adding the created paragraph in the document

        document.add(paragraph1);

        Image image = Image.getInstance("https://proosoftcloud.com/web/image/website/1/favicon/");
        image.scaleToFit(50, 50);
        image.setBottom(12);


        document.add(image);

        // Creating a table of the 4 columns

        PdfPTable table = new PdfPTable(4);

        // Setting width of the table, its columns and spacing

        table.setWidthPercentage(100f);

        table.setWidths(new int[] {6,6,6,6});

        table.setSpacingBefore(8);

        // Create Table Cells for the table header

        PdfPCell cell = new PdfPCell();

        // Setting the background color and padding of the table cell

        cell.setBackgroundColor(CMYKColor.red);

        cell.setPadding(4);

        // Creating font

        // Setting font style and size

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);

        font.setColor(CMYKColor.WHITE);

        // Adding headings in the created table cell or  header

        // Adding Cell to table

        cell.setPhrase(new Phrase("Titre", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Reference", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Client", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("DateFacture", font));
        cell.setPhrase(new Phrase("Total_HT", font));
        cell.setPhrase(new Phrase("Total_TTC", font));

        table.addCell(cell);

        // Iterating the list of students

        for (Facture facture1: facture) {
            table.addCell(facture1.getTitre());
            table.addCell(facture1.getReference());
            table.addCell(facture1.getClient());
            table.addCell(facture1.getDateFacture());
            table.addCell(facture1.getTotal_HT());
            table.addCell(facture1.getTotal_TTC());



        }

        // Adding the created table to the document

        document.add(table);

        // Closing the document

        document.close();

    }


}
