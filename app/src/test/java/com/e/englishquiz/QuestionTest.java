package com.e.englishquiz;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    @Test
    public void getId_returns_id() {

        // Arrange
        Question question = new Question(1, "text", true);

        // Act
        int id = question.getId();

        // Assert
        assertEquals(1, id);
    }

    @Test
    public void getQuestionText_returnsQuestionText() {

        // Arrange
        Question question = new Question(2,"example", false);

        // Act
        String questionTextExample = question.getQuestionText();

        // Assert
        assertEquals("example", questionTextExample);
    }

    @Test
    public void getIsAnswerTrue_returnsAnswerTrue() {

        // Arrange
        Question question = new Question(4,"test", false);

        // Act
        Boolean isAnswerTrueExample = question.isAnswerTrue();

        // Assert
        assertEquals(false, isAnswerTrueExample);
    }
}
