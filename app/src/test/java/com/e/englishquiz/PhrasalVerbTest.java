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
}






