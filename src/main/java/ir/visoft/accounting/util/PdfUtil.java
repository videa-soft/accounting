package ir.visoft.accounting.util;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.Bill;
import ir.visoft.accounting.entity.User;
import ir.visoft.accounting.exception.DatabaseOperationException;
import ir.visoft.accounting.ui.UTF8Control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * @author Amir
 */
public class PdfUtil {

    public static Document createPdf(String filename) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        return document;
    }

    public static void createBillPdf(Bill bill) throws DocumentException, IOException {
        Calendar calendar = Calendar.getInstance();
        String dateAsString = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)+ "-"+ calendar.get(Calendar.DAY_OF_MONTH) + "_(" +
                calendar.get(Calendar.HOUR) + "-" + calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND) + ")";
        createBillPdf(bill, System.getProperty("user.home") + "\\Desktop\\bill\\bill_" + dateAsString + ".pdf");
    }

    public static void createBillPdf(Bill bill, String fileName) throws DocumentException, IOException {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.message", new Locale("fa"), new UTF8Control());

        Document document = createPdf(fileName);
        User user = new User(bill.getUserId());
        try {
            user = (User)DatabaseUtil.getEntity(user).get(0);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }


        BaseFont baseFont = null;
        try {
            baseFont = BaseFont.createFont("/fonts/baran.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font font = new Font(baseFont, 12);
        PdfPTable table = new PdfPTable(1);
        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

        Paragraph customerNumber = new Paragraph(resourceBundle.getString("customerNumber") + " : " + user.getCustomerNumber(), font);
        customerNumber.setAlignment(Element.ALIGN_RIGHT);

        Paragraph customerName = new Paragraph(resourceBundle.getString("customerName") + " : " + user.getFirstName() + " " + user.getLastName(), font);
        customerName.setAlignment(Element.ALIGN_RIGHT);

        Paragraph familyCount = new Paragraph( resourceBundle.getString("familyCount") + " : " + user.getFamilyCount(), font);
        familyCount.setAlignment(Element.ALIGN_RIGHT);

        Paragraph preDate = new Paragraph( resourceBundle.getString("preDate") + " : " + (bill.getPreviousDate() != null ? DateUtil.getDateAsString(bill.getPreviousDate()) : ""), font);
        preDate.setAlignment(Element.ALIGN_RIGHT);

        Paragraph currentDate = new Paragraph( resourceBundle.getString("currentDate") + " : " + (bill.getNewDate() != null ? DateUtil.getDateAsString(bill.getNewDate()) : ""), font);
        currentDate.setAlignment(Element.ALIGN_RIGHT);

        Paragraph previousFigure = new Paragraph( resourceBundle.getString("preFigure") + " : " + bill.getPreviousFigure(), font);
        previousFigure.setAlignment(Element.ALIGN_RIGHT);

        Paragraph currentFigure = new Paragraph( resourceBundle.getString("currentFigure") + " : " + bill.getCurrentFigure(), font);
        currentFigure.setAlignment(Element.ALIGN_RIGHT);

        Paragraph cunsumption = new Paragraph( resourceBundle.getString("cunsumption") + " : " + bill.getCunsumption(), font);
        cunsumption.setAlignment(Element.ALIGN_RIGHT);



        PdfPCell customerNumberCell = new PdfPCell(customerNumber);
        customerNumberCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        customerNumberCell.setBorder(0);
        customerNumberCell.setMinimumHeight(25);

        PdfPCell customerNameCell = new PdfPCell(customerName);
        customerNameCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        customerNameCell.setBorder(0);
        customerNameCell.setMinimumHeight(25);

        PdfPCell familyCountCell = new PdfPCell(familyCount);
        familyCountCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        familyCountCell.setBorder(0);
        familyCountCell.setMinimumHeight(25);

        PdfPCell preDateCell = new PdfPCell(preDate);
        preDateCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        preDateCell.setBorder(0);
        preDateCell.setMinimumHeight(25);

        PdfPCell currentDateCell = new PdfPCell(currentDate);
        currentDateCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        currentDateCell.setBorder(0);
        currentDateCell.setMinimumHeight(25);

        PdfPCell previousFigureCell = new PdfPCell(previousFigure);
        previousFigureCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        previousFigureCell.setBorder(0);
        previousFigureCell.setMinimumHeight(25);

        PdfPCell currentFigureCell = new PdfPCell(currentFigure);
        currentFigureCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        currentFigureCell.setBorder(0);
        currentFigureCell.setMinimumHeight(25);

        PdfPCell cunsumptionCell = new PdfPCell(cunsumption);
        cunsumptionCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cunsumptionCell.setBorder(0);
        cunsumptionCell.setMinimumHeight(25);

        table.addCell(customerNumberCell);
        table.addCell(customerNameCell);
        table.addCell(familyCountCell);
        table.addCell(preDateCell);
        table.addCell(currentDateCell);
        table.addCell(previousFigureCell);
        table.addCell(currentFigureCell);
        table.addCell(cunsumptionCell);
        document.add(table);


        document.close();
    }

}
