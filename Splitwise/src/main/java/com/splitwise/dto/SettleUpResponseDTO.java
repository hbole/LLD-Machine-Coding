package com.splitwise.dto;

import com.splitwise.models.Expense;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettleUpResponseDTO {
    private List<Expense> expenses;
}
