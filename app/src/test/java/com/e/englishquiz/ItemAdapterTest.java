package com.e.englishquiz;

import android.content.Context;

import com.e.englishquiz.Adapters.ItemAdapter;
import com.e.englishquiz.Models.PhrasalVerb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(RobolectricTestRunner.class)
public class ItemAdapterTest {

    @Mock
    private ArrayList<PhrasalVerb> list;

    @Mock
    private Context context;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCount_returns_count() {
        // Arrange
        ItemAdapter adapter = new ItemAdapter(list, context);
        when(list.size()).thenReturn(2);

        // Act
        int count = adapter.getCount();

        // Assert
        assertEquals(2, count);
    }

    @Test
    public void getItem_returns_item() {
        // Arrange
        ItemAdapter adapter = new ItemAdapter(list, context);

        PhrasalVerb verb = new PhrasalVerb(1, "verb", "meaning", "example", false);
        when(list.get(0)).thenReturn(verb);

        // Act
        Object object = adapter.getItem(0);

        // Assert
        assertEquals(verb, object);
    }

    @Test
    public void getItemId_returns_itemId() {
        // Arrange
        ItemAdapter adapter = new ItemAdapter(list, context);

        PhrasalVerb verb = new PhrasalVerb(5, "verb1", "meaning1", "example1", false);
        when(list.get(0)).thenReturn(verb);

        // Act
        long test = adapter.getItemId(0);

        // Assert
        assertEquals(verb.getId(), test);
    }
}
