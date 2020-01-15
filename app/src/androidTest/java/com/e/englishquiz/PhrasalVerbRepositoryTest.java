package com.e.englishquiz;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.e.englishquiz.Models.PhrasalVerb;
import com.e.englishquiz.Repositories.PhrasalVerbRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class PhrasalVerbRepositoryTest {

    private PhrasalVerbRepository repository;

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        repository = new PhrasalVerbRepository(appContext);
    }

    @Test
    public void it_is_possible_to_get_data_from_db() {
        ArrayList<PhrasalVerb> verbs = repository.getAllVerbs();
        assertTrue(verbs.size() > 0);
    }

    @Test
    public void it_is_possible_to_update_isKnown_parameter_from_db() {
        // Arrange
        ArrayList<PhrasalVerb> verbs = repository.getAllVerbs();
        PhrasalVerb verb = verbs.get(0);
        verb.setKnown(true);
        Boolean expected = verb.isKnown();

        // Act
        repository.updateIsKnownParameter(expected, verb);

        // Assert
        ArrayList<PhrasalVerb> verbs2 = repository.getAllVerbs();
        Boolean result = verbs2.get(0).isKnown();
        assertEquals(expected, result);
    }
}
