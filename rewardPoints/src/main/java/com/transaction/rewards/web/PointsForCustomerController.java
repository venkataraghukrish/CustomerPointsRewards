package com.transaction.rewards.web;

import com.transaction.rewards.model.PointsByMonth;
import com.transaction.rewards.model.Transaction;
import com.transaction.rewards.repository.TransactionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

@RestController
public class PointsForCustomerController {

    TransactionRepository transactionRepo;

    //http://localHost:8080/ABC1111
    @GetMapping("/customer/{id}")
    public List<PointsByMonth> getPointsForCustomerForLast3Months(@PathVariable("id") String id){

        List<PointsByMonth> pointsList = new ArrayList<>();
        YearMonth yearMonth = YearMonth.now();

        //getting points for last 3 months
        for (int i = 1; i < 3; i++){
            YearMonth month = yearMonth.minusMonths(i);
            List<Transaction> monthlyTrans = getTransactions(id, i);
            int totalPointsMinus1Month = calculateTotalPoints(monthlyTrans);
            pointsList.add(new PointsByMonth(month.getMonth().toString(), totalPointsMinus1Month));
        }
        return pointsList;
    }

    public int calculateTotalPoints(List<Transaction> list){
        int points = 0;
        for(Iterator<Transaction> itr = list.iterator(); itr.hasNext();){
            for (Transaction t : list) {
                points += t.getPoints();
            }
        }
        return points;
    }

    public List<Transaction> getTransactions(String id, int minusMonth){
        YearMonth yearMonth = YearMonth.now();
        YearMonth currentMinus1Month = yearMonth.minusMonths(minusMonth);
        LocalDate firstDayOfMinus1Month = currentMinus1Month.atDay(1);
        LocalDate lastDayOfMinus1Month = currentMinus1Month.atEndOfMonth();
        Date currentMinus1MontfirstDate = java.util.Date.from(firstDayOfMinus1Month.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date currentMinus1MontlastDate = java.util.Date.from(lastDayOfMinus1Month.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return getAllTransactionsForIDForMonth(id, currentMinus1MontfirstDate, currentMinus1MontlastDate);
    }

    public List<Transaction> getAllTransactionsForIDForMonth(String id, Date start, Date end){

        Calendar startDateTime = Calendar.getInstance();
        startDateTime.setTime(start);
        Calendar endDateTime = Calendar.getInstance();
        endDateTime.setTime(end);

        return transactionRepo.findTransactionsByIdAndMonth(id, startDateTime, endDateTime);
    }
}
