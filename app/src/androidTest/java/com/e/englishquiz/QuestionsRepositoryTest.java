package com.e.englishquiz;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.e.englishquiz.Models.Question;
import com.e.englishquiz.Repositories.QuestionsRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

public class QuestionsRepositoryTest {

    private QuestionsRepository repository;

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        repository = new QuestionsRepository(appContext);
    }

    @Test
    public void it_is_possible_to_open_db() {

        Boolean isOpenDb = repository.openDataBase();
        assertTrue(isOpenDb);
    }

    @Test
    public void it_is_possible_to_close_db() {
        repository.close();
    }

    @Test
    public void it_is_possible_to_get_data_from_db() {

        ArrayList<Question> list = repository.getAllQuestions();
        assertTrue(list.size() > 0);
    }

}
