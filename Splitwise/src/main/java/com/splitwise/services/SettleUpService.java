package com.splitwise.services;

import com.splitwise.exceptions.UserNotFoundException;
import com.splitwise.models.Expense;
import com.splitwise.models.ExpenseUser;
import com.splitwise.models.ExpenseUserType;
import com.splitwise.models.User;
import com.splitwise.repositories.ExpenseUserRepository;
import com.splitwise.repositories.UserRepository;
import com.splitwise.strategies.SettleUpStrategy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettleUpService {
    private UserRepository userRepository;
    private ExpenseUserRepository expenseUserRepository;
    private SettleUpStrategy settleUpStrategy;

    public SettleUpService(
            UserRepository userRepository,
            ExpenseUserRepository expenseUserRepository,
            SettleUpStrategy settleUpStrategy
    ) {
        this.userRepository = userRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.settleUpStrategy = settleUpStrategy;
    }

    public List<Expense> settleUpUser(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }

        User user = optionalUser.get();

        //1. Get all the expenses for the user from the DB.
        List<ExpenseUser> expenseUsers = expenseUserRepository.findAllByUser(userId);

        //2. Go through all expenses and find out all the people involved in the expenses and their net balance.
        Set<Expense> expenses = new HashSet<>();
        for(ExpenseUser expenseUser : expenseUsers) {
            expenses.add(expenseUser.getExpense());
        }

        //3. Use Min and Max heap to find all the transactions that needs to be done in order to settle up.
        List<Expense> settledUpExpenses = settleUpStrategy.settleUp(expenses.stream().toList());
        //We are fetching only those expenses which the user is part of.
        List<Expense> finalSettledUpExpenses = new ArrayList<>();
        for(Expense settleUpExpense : settledUpExpenses) {
            for(ExpenseUser expenseUser : settleUpExpense.getExpenseUsers()) {
                if(expenseUser.getUser().equals(user)) {
                    finalSettledUpExpenses.add(settleUpExpense);
                }
            }
        }

        //4. Return all the transactions to the User.
        return finalSettledUpExpenses;
    }

    public List<Expense> settleUpGroup(Long groupId) {
        return null;
    }
}
