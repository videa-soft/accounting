package ir.visoft.accounting;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import ir.visoft.accounting.entity.Bill;
import ir.visoft.accounting.util.PdfUtil;

import java.io.IOException;
import java.util.Calendar;

import java.util.Date;


/**
 * @author Amir
 */
public class Application {

    public static void main(String[] args) {
        System.out.println("Application started...!");

        try {
            Calendar calendar = Calendar.getInstance();
            String dateAsString = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)+ "-"+ calendar.get(Calendar.DAY_OF_MONTH) + "_(" +
                    calendar.get(Calendar.HOUR) + "-" + calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND) + ")";


            Document billPdf = PdfUtil.createPdf(System.getProperty("user.home") + "\\Desktop\\bill_" + dateAsString + ".pdf");
            Bill bill = new Bill();
            bill.setBillId(200);
            bill.setUserId(100);
            bill.setPreviousDate(new Date());
            bill.setNewDate(new Date());
            bill.setPreviousFigure(100);
            bill.setCurrentFigure(234);
            bill.setCunsumption(1234);

            bill.setFinalAmount(29331);

            PdfUtil.createBillPdf(bill);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
