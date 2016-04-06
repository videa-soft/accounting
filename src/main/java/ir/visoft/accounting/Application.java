package ir.visoft.accounting;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import ir.visoft.accounting.entity.Bill;
import ir.visoft.accounting.util.PdfUtil;

import java.io.IOException;
import java.util.Calendar;

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

            Document billPdf = PdfUtil.createPdf("C:\\Users\\Amir\\Desktop\\bill_" + dateAsString + ".pdf");
            Bill bill = new Bill();
            bill.setBillId(200);
            bill.setUserId(21);
            bill.setFinalAmount(29331);

            PdfUtil.createBillPdf(bill, billPdf);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
