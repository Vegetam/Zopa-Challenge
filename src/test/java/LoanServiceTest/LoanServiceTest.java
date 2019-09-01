package LoanServiceTest;

import com.francescomalagrino.zopa.exceptions.AvailableAmountException;
import com.francescomalagrino.zopa.exceptions.InvalidAmountException;
import com.francescomalagrino.zopa.models.Loaner;
import com.francescomalagrino.zopa.models.Quote;
import com.francescomalagrino.zopa.permanent.Permanent;
import com.francescomalagrino.zopa.services.LoanService;
import com.francescomalagrino.zopa.CVS.Parser.CSVParser;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LoanServiceTest {

    LoanService loanService;

    Quote quote;

    String CSVFile = "MarketData.csv";

    @Before
    public void setup() throws IOException, AvailableAmountException, InvalidAmountException {
        loanService = new LoanService(CSVFile);
        quote = loanService.getRate(1000);
    }

    @Test
    public void checkRate() {
        assertEquals(0.075, quote.getMonthlyRate(), 0.01);
    }

    @Test
    public void checkMonthlyRepayment() {
        assertEquals(30.78, quote.getMonthlyRepayment(), 0.01);
    }

    @Test
    public void checkTotalPayment() {
        assertEquals(1108.1013978795265, quote.getTotalRepayment(), 0.01);
    }

    @Test
    public void checkLoanLength() {
        assertEquals(36, loanService.getLoanLength());
    }

    @Test
    public void checkAvailableLoanAmount() {
        assertEquals(2330, loanService.getAvailableAmount());
    }

    @Test
    public void checkLenders() throws IOException {
        List<Loaner> loaners = CSVParser.parseCSV(CSVFile);
        assertEquals(loaners.size(), loanService.getLenders().size());
        Collections.sort(loaners, Comparator.comparingDouble(Loaner::getRate));
        for (int i = 0; i < loaners.size(); i++) {
            assertEquals(loaners.get(i).getAmount(), loanService.getLenders().get(i).getAmount());
            assertEquals(loaners.get(i).getRate(), loanService.getLenders().get(i).getRate(), 0.01);
            assertEquals(loaners.get(i).getName(), loanService.getLenders().get(i).getName());
        }
    }

    @Test(expected = IOException.class)
    public void testLoanServiceWithNotExistingCSV() throws IOException {
        loanService = new LoanService("ciao");
    }

    @Test(expected = AvailableAmountException.class)
    public void testGetRateWithUnavailableAmount() throws AvailableAmountException {
			loanService.getRate(1500000);
		
    
    }

}
