package com.splitwise.commands;

import com.splitwise.controllers.SettleUpController;
import com.splitwise.dto.SettleUpRequestDTO;
import com.splitwise.dto.SettleUpResponseDTO;
import com.splitwise.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SettleUpCommand implements Command {
    private SettleUpController settleUpController;

    public SettleUpCommand(SettleUpController settleUpController) {
        this.settleUpController = settleUpController;
    }

    @Override
    public void execute(String input) throws UserNotFoundException {
        List<String> words = List.of(input.split(" "));
        Long userId = Long.valueOf(words.get(0));

        SettleUpRequestDTO settleUpRequest = new SettleUpRequestDTO();
        settleUpRequest.setUserId(userId);

        SettleUpResponseDTO settleUpResponse = settleUpController.settleUpUser(settleUpRequest);
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));
        String inputCommand = words.get(1);

        return words.size() == 2 && inputCommand.equals(CommandKeywords.settleUpCommand);
    }
}
