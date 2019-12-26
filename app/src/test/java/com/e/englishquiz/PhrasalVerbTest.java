package com.e.englishquiz;

import com.e.englishquiz.Models.PhrasalVerb;

import org.junit.Test;

import static org.junit.Assert.*;

public class PhrasalVerbTest {

    @Test
    public void getId_returns_id() {

        // Arrange
        PhrasalVerb verb = new PhrasalVerb(1, "verb1", "meaning1", "example1", false);

        // Act
        int id = verb.getId();

        // Assert
        assertEquals(1, id);
    }

    @Test
    public void getVerb_returns_verbs_name() {

        // Arrange
        PhrasalVerb verb = new PhrasalVerb(2, "verb2", "meaning2", "example2", false);

        // Act
        String verbsName = verb.getVerb();

        // Assert
        assertEquals("verb2", verbsName);
    }

    @Test
    public void getMeaning_returns_meaning() {

        // Arrange
        PhrasalVerb verb = new PhrasalVerb(3, "verb3", "meaning3", "example3", false);

        // Act
        String verbMeaning = verb.getMeaning();

        // Assert
        assertEquals("meaning3", verbMeaning);
    }

    @Test
    public void getExample_returns_example() {

        // Arrange
        PhrasalVerb verb = new PhrasalVerb(4, "verb4", "meaning4", "example4", false);

        // Act
        String verbExample = verb.getExample();

        // Assert
        assertEquals("example4", verbExample);
    }

    @Test
    public void getIsKnown_returns_isKnown() {

        // Arrange
        PhrasalVerb verb = new PhrasalVerb(5, "verb5", "meaning5", "example5", false);

        // Act
        Boolean verbIsKnown = verb.isKnown();

        // Assert
        assertEquals(false, verbIsKnown);
    }

    @Test
    public void setIsKnown_sets_isKnown() {

        // Arrange
        PhrasalVerb verb = new PhrasalVerb(5, "verb5", "meaning5", "example5", false);

        // Act
        verb.setKnown(true);
        Boolean verbIsKnown = verb.isKnown();

        // Assert
        assertTrue(verbIsKnown);
    }
}






