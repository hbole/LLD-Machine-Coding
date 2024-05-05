package repositories;

import models.AnswerOption;

import java.util.Map;
import java.util.Optional;

public class AnswerOptionRepository {
    private Map<Long, AnswerOption> answerOptions;
    private Long id;

    public AnswerOptionRepository(Map<Long, AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public AnswerOption saveAnswerOption(String answer) {
        id += 1;
        AnswerOption answerOption = new AnswerOption(id, answer);

        answerOptions.put(id, answerOption);
        return answerOption;
    }

    public Optional<AnswerOption> findAnswerByText(String answer) {
        for(AnswerOption answerOption : answerOptions.values()) {
            if(answerOption.getOption().equals(answer)) {
                return Optional.of(answerOption);
            }
        }

        return Optional.empty();
    }
}
