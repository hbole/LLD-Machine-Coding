package com.splitwise.commands;

import com.splitwise.exceptions.UserNotFoundException;

public interface Command {
    void execute(String command) throws Exception;
    boolean matches(String command);
}
