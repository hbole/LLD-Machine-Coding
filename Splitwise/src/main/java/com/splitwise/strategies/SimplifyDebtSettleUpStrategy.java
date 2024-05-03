package com.splitwise.strategies;

import com.splitwise.models.Expense;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimplifyDebtSettleUpStrategy implements SettleUpStrategy {
    //Settle up strategy using min & max heap.

    @Override
    public List<Expense> settleUp(List<Expense> expenses) {
        //1. Go through all expenses to settle up and find the paid extra and the ones who have paid less.
        //2. People who have paid extra, put them into max heap and others in the min heap.
        return null;
    }
}
