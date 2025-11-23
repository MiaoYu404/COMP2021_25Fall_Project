package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.controller.Data;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class TestDataUndoRedo {
    @Test
    public void testInsertOpNull() {
        Data d = new Data();
        try {
            d.insertOp(null);
        } catch (NullPointerException e) {
            // expected
            assertThat(d.isOperationHistoryEmpty()).isTrue();
        }
    }

}
