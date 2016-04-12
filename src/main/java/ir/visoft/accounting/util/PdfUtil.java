package ir.visoft.accounting.util;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.AccountBalance;
import ir.visoft.accounting.entity.Bill;
import ir.visoft.accounting.entity.User;
import ir.visoft.accounting.exception.DatabaseOperationException;
import ir.visoft.accounting.ui.UTF8Control;

import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Math.abs;
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
            user = (User) DatabaseUtil.getEntity(user).get(0);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }

        BaseFont baseFont = null;
        try {
            baseFont = BaseFont.createFont("/fonts/BNazanin.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Font font = new Font(baseFont, 13);
        Font fontB = new Font(baseFont, 15, Font.BOLD);
        Font fontL = new Font(baseFont, 11);
        Font fontNum = new Font(baseFont, 18, Font.BOLD);
        PdfPTable table = new PdfPTable(3);
        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

        Paragraph customerNumber = new Paragraph(resourceBundle.getString("customerNumber") + " :  " + user.getCustomerNumber(), fontNum);
        customerNumber.setAlignment(Element.ALIGN_RIGHT);

        Paragraph customerName = new Paragraph(resourceBundle.getString("customerName") + ": " + user.getFirstName() + " " + user.getLastName(), font);
        customerName.setAlignment(Element.ALIGN_RIGHT);

        Paragraph familyCount = new Paragraph(resourceBundle.getString("familyCount") + " :  " + user.getFamilyCount(), font);
        familyCount.setAlignment(Element.ALIGN_RIGHT);

        Paragraph AddressCount = new Paragraph(resourceBundle.getString("address") + " : " + user.getHomeAddress(), font);
        AddressCount.setAlignment(Element.ALIGN_RIGHT);

        Paragraph preDate = new Paragraph(resourceBundle.getString("preDate") + ":" + (bill.getPreviousDate() != null ? DateUtil.getDateAsString(bill.getPreviousDate()) : ""), font);
        preDate.setAlignment(Element.ALIGN_RIGHT);

        Paragraph currentDate = new Paragraph(resourceBundle.getString("currentDate") + ": " + (bill.getNewDate() != null ? DateUtil.getDateAsString(bill.getNewDate()) : ""), font);
        currentDate.setAlignment(Element.ALIGN_RIGHT);

        Paragraph previousFigure = new Paragraph(resourceBundle.getString("preFigure") + " :  " + bill.getPreviousFigure(), font);
        previousFigure.setAlignment(Element.ALIGN_RIGHT);

        Paragraph currentFigure = new Paragraph(resourceBundle.getString("currentFigure") + " :  " + bill.getCurrentFigure(), font);
        currentFigure.setAlignment(Element.ALIGN_RIGHT);

        Paragraph cunsumption = new Paragraph(resourceBundle.getString("cunsumption") + " :  " + bill.getCunsumption(), font);
        cunsumption.setAlignment(Element.ALIGN_RIGHT);

        Paragraph amount = new Paragraph("          " + resourceBundle.getString("final_amount"), fontB);
        amount.setAlignment(Element.ALIGN_CENTER);

        Paragraph amountValue = new Paragraph("           " + bill.getFinalAmount().toString(), fontB);
        amountValue.setAlignment(Element.ALIGN_CENTER);

        Paragraph expireDate = new Paragraph("              " + resourceBundle.getString("expire_Date") + " : ", font);
        expireDate.setAlignment(Element.ALIGN_CENTER);

        Paragraph expireDateValue = new Paragraph("         " + resourceBundle.getString("expire_Date_value"), fontL);
        expireDateValue.setAlignment(Element.ALIGN_CENTER);

        Paragraph cost_water = new Paragraph("     " + resourceBundle.getString("masraf_per_family") + ":" + bill.getCostWater(), font);
        cost_water.setAlignment(Element.ALIGN_CENTER);

        Paragraph abonman = new Paragraph("          " + resourceBundle.getString("abonman") + " : " + bill.getAbonman(), font);
        abonman.setAlignment(Element.ALIGN_RIGHT);

        Paragraph services = new Paragraph("          " + resourceBundle.getString("services") + " : " + bill.getServices(), font);
        services.setAlignment(Element.ALIGN_RIGHT);

        Paragraph cost_balance = new Paragraph("          " + resourceBundle.getString("cost_balance") + ": " + bill.getCostBalance(), font);
        cost_balance.setAlignment(Element.ALIGN_RIGHT);

        Paragraph reduction = new Paragraph("          " + resourceBundle.getString("reduction") + " : " + bill.getReduction(), font);
        reduction.setAlignment(Element.ALIGN_RIGHT);

        Paragraph bedehi = new Paragraph("          " + resourceBundle.getString("last_bedehi") + " : " + 0, font);
        bedehi.setAlignment(Element.ALIGN_RIGHT);

        Paragraph bestankari = new Paragraph("          " + resourceBundle.getString("bestankar") + " : " + 0, font);
        bestankari.setAlignment(Element.ALIGN_RIGHT);

        if (bill.getLastDebit() >= 0) {
            bedehi = new Paragraph("          " + resourceBundle.getString("last_bedehi") + " : " + bill.getLastDebit(), font);
            bedehi.setAlignment(Element.ALIGN_RIGHT);
        } else {
            bestankari = new Paragraph("          " + resourceBundle.getString("bestankar") + " : " + abs(bill.getLastDebit()), font);
            bestankari.setAlignment(Element.ALIGN_RIGHT);
        }

        Paragraph title = new Paragraph("        " + resourceBundle.getString("WaterSysGhaemTown"), fontB);
        title.setAlignment(Element.ALIGN_RIGHT);

        PdfPCell customerNumberCell = new PdfPCell(customerNumber);
        customerNumberCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        customerNumberCell.setBorder(0);
        customerNumberCell.setMinimumHeight(20);

        PdfPCell customerNameCell = new PdfPCell(customerName);
        customerNameCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        customerNameCell.setBorder(0);
        customerNameCell.setMinimumHeight(20);

        PdfPCell familyCountCell = new PdfPCell(familyCount);
        familyCountCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        familyCountCell.setBorder(0);
        familyCountCell.setMinimumHeight(20);

        PdfPCell addressCountCell = new PdfPCell(AddressCount);
        addressCountCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        addressCountCell.setBorder(0);
        addressCountCell.setMinimumHeight(30);

        PdfPCell preDateCell = new PdfPCell(preDate);
        preDateCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        preDateCell.setBorder(0);
        preDateCell.setMinimumHeight(20);

        PdfPCell currentDateCell = new PdfPCell(currentDate);
        currentDateCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        currentDateCell.setBorder(0);
        currentDateCell.setMinimumHeight(20);

        PdfPCell previousFigureCell = new PdfPCell(previousFigure);
        previousFigureCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        previousFigureCell.setBorder(0);
        previousFigureCell.setMinimumHeight(20);

        PdfPCell currentFigureCell = new PdfPCell(currentFigure);
        currentFigureCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        currentFigureCell.setBorder(0);
        currentFigureCell.setMinimumHeight(20);

        PdfPCell cunsumptionCell = new PdfPCell(cunsumption);
        cunsumptionCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cunsumptionCell.setBorder(0);
        cunsumptionCell.setMinimumHeight(20);

        PdfPCell amountCell = new PdfPCell(amount);
        amountCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        amountCell.setBorder(0);
        amountCell.setMinimumHeight(20);

        PdfPCell amountValueCell = new PdfPCell(amountValue);
        amountValueCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        amountValueCell.setBorder(0);
        amountValueCell.setMinimumHeight(20);

        PdfPCell expireDateCell = new PdfPCell(expireDate);
        expireDateCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        expireDateCell.setBorder(0);
        expireDateCell.setMinimumHeight(20);

        PdfPCell expireDateValueCell = new PdfPCell(expireDateValue);
        expireDateValueCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        expireDateValueCell.setBorder(0);
        expireDateValueCell.setMinimumHeight(20);

        PdfPCell costWaterCell = new PdfPCell(cost_water);
        costWaterCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        costWaterCell.setBorder(0);
        costWaterCell.setMinimumHeight(20);

        PdfPCell abonmanCell = new PdfPCell(abonman);
        abonmanCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        abonmanCell.setBorder(0);
        abonmanCell.setMinimumHeight(20);

        PdfPCell servicesCell = new PdfPCell(services);
        servicesCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        servicesCell.setBorder(0);
        servicesCell.setMinimumHeight(20);

        PdfPCell costBalanceCell = new PdfPCell(cost_balance);
        costBalanceCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        costBalanceCell.setBorder(0);
        costBalanceCell.setMinimumHeight(20);

        PdfPCell reductionCell = new PdfPCell(reduction);
        reductionCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        reductionCell.setBorder(0);
        reductionCell.setMinimumHeight(20);

        PdfPCell bedehkariCell = new PdfPCell(bedehi);
        bedehkariCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        bedehkariCell.setBorder(0);
        bedehkariCell.setMinimumHeight(20);

        PdfPCell bestankariCell = new PdfPCell(bestankari);
        bestankariCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        bestankariCell.setBorder(0);
        bestankariCell.setMinimumHeight(20);

        PdfPCell emptyCell = new PdfPCell(new Phrase(""));
        emptyCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        emptyCell.setBorder(0);
        emptyCell.setMinimumHeight(15);

        PdfPCell titleCell = new PdfPCell(title);
        titleCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        titleCell.setBorder(0);
        titleCell.setMinimumHeight(5);

        table.addCell(emptyCell);
        table.addCell(titleCell);
        table.addCell(emptyCell);
        table.addCell(customerNumberCell);
        table.addCell(emptyCell);
        table.addCell(abonmanCell);
        table.addCell(customerNameCell);
        table.addCell(emptyCell);
        table.addCell(bedehkariCell);
        table.addCell(familyCountCell);
        table.addCell(emptyCell);
        table.addCell(servicesCell);
        table.addCell(addressCountCell);
        table.addCell(emptyCell);
        table.addCell(costBalanceCell);
        table.addCell(preDateCell);
        table.addCell(emptyCell);
        table.addCell(costWaterCell);
        table.addCell(currentDateCell);
        table.addCell(amountCell);
        table.addCell(emptyCell);
        table.addCell(previousFigureCell);
        table.addCell(amountValueCell);
        table.addCell(emptyCell);
        table.addCell(currentFigureCell);
        table.addCell(expireDateCell);
        table.addCell(reductionCell);
        table.addCell(cunsumptionCell);
        table.addCell(expireDateValueCell);
        table.addCell(bestankariCell);

        document.add(table);

        document.close();
    }

}
