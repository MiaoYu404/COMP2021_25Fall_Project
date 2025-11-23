package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.model.operation.OpUngroup;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Shape;
import org.junit.Test;

public class TestOpUngroupExtra {
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFatherNull() {
        OpUngroup op = new OpUngroup(new hk.edu.polyu.comp.comp2021.clevis.controller.Data());
        op.removeFather(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFatherNoFather() {
        Shape s = new hk.edu.polyu.comp.comp2021.clevis.model.shape.Circle();
        OpUngroup op = new OpUngroup(new hk.edu.polyu.comp.comp2021.clevis.controller.Data());
        op.removeFather(s);
    }
}
