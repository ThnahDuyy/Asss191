package ASM.fgw;

import org.junit.jupiter.api.Test;
//import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.AssertJUnit.assertEquals;

class ManagementTest {

    @Test
    void TestAdd(){
        Management mn = new Management("Test");
        mn.getTxtID().setText("TestID");
        mn.getTxtName().setText("Duyyy");
        mn.getRbMale().setSelected(true);
        mn.getCbPosition().setSelectedIndex(0);
        mn.getTxtEmail().setText("duy213@gmail.com");
        mn.getTxtBday().setText("19/4/2000");
        mn.Add();
        assertEquals(4, mn.list.size());
    }

    @Test
    void TestUpdate(){
        Management mn = new Management("Test");
        mn.getTxtID().setText("E2");
        mn.getTxtName().setText("Duyyy");
        mn.getRbMale().setSelected(true);
        mn.getCbPosition().setSelectedIndex(0);
        mn.getTxtEmail().setText("duy2132e");
        mn.getTxtBday().setText("19/04/2001");
        mn.update();
        for (int i = 0; i < mn.getTbCan().getRowCount(); i++) {
            String cID = (String) mn.getTbCan().getValueAt(i, 0);
            if (cID.equals("E2")) {
                Manager c = mn.list.get(i);
                assertEquals("Duyyy", mn.getTxtName().getText());
                break;
            }
        }
    }
    @Test
    void TestDelete() {
        Management mn = new Management("Test");
        mn.getTbCan().setRowSelectionInterval(1, 1);
        mn.Delete();
        assertEquals(2, mn.list.size());
    }
}