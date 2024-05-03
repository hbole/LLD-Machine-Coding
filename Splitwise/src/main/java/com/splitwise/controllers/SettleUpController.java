package com.splitwise.controllers;

import com.splitwise.dto.SettleUpGroupRequestDTO;
import com.splitwise.dto.SettleUpGroupResponseDTO;
import com.splitwise.dto.SettleUpRequestDTO;
import com.splitwise.dto.SettleUpResponseDTO;
import com.splitwise.exceptions.UserNotFoundException;
import com.splitwise.models.Expense;
import com.splitwise.services.SettleUpService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SettleUpController {
    private SettleUpService settleUpService;

    public SettleUpController(SettleUpService settleUpService) {
        this.settleUpService = settleUpService;
    }

    public SettleUpResponseDTO settleUpUser(SettleUpRequestDTO settleUpRequest) throws UserNotFoundException {
        List<Expense> expenses = settleUpService.settleUpUser(settleUpRequest.getUserId());

        SettleUpResponseDTO settleUpResponse = new SettleUpResponseDTO();
        settleUpResponse.setExpenses(expenses);
        return settleUpResponse;
    }

    public SettleUpGroupResponseDTO settleUpGroup(SettleUpGroupRequestDTO settleUpGroupRequest) {
        return null;
    }
}
