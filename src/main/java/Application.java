import java.io.IOException;

import com.francescomalagrino.zopa.exceptions.ArgumentNotFoundException;
import com.francescomalagrino.zopa.exceptions.AvailableAmountException;
import com.francescomalagrino.zopa.exceptions.InvalidAmountException;
import com.francescomalagrino.zopa.models.Quote;
import com.francescomalagrino.zopa.permanent.Permanent;
import com.francescomalagrino.zopa.services.LoanService;

public class Application {

    public static void main(String... args) throws ArrayIndexOutOfBoundsException, NumberFormatException, ArgumentNotFoundException, InvalidAmountException,IOException,AvailableAmountException {
        try {
            String csv = args[0];
            int loanAmount = Integer.parseInt(args[1]);
            argumentCheck(csv, loanAmount);
            LoanService loanService = new LoanService(csv);
            Quote quote = loanService.getRate(loanAmount);
            buildOutput(loanAmount, quote);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Permanent.CHECK_ARGUMENTS);
        } catch (NumberFormatException e) {
            System.out.println(Permanent.CHECK_FORMAT);
        } catch (ArgumentNotFoundException e) {
            System.out.println(Permanent.CHECK_IF_ARGUMENT);
        } catch (IOException e) {
            System.out.println(Permanent.INVALID_INPUT);
        } catch (AvailableAmountException e) {
            System.out.println(Permanent.AVAILABLE_LOAN);
        }
    }

    private static void buildOutput(int amount, Quote quote) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Requested amount: £%d\n", amount));
        sb.append(String.format("Annual Interest Rate: %.1f%%\n", quote.getMonthlyRate() * 100));
        sb.append(String.format("Monthly repayment: £%.2f\n", quote.getMonthlyRepayment()));
        sb.append(String.format("Total repayment: £%.2f", quote.getTotalRepayment()));
        if(amount < 1000 || amount <= 15000 || amount % 100 != 0) {
        	System.out.println(sb.toString());
        }
    }
    
    private static void argumentCheck(String csv, int loanAmount) throws ArgumentNotFoundException {
        if (csv == null || !csv.endsWith(".csv")) {
        	 System.out.println(Permanent.CHECK_IF_ARGUMENT);
        }
    }
}