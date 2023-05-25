package ASM.fgw;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Management extends JFrame{
    private JPanel mainPanel;
    private JTextField txtID;
    private JTextField txtName;
    private JRadioButton rbNew;
    private JRadioButton rbFemale;
    private JComboBox cbCategory;
    private JTextField txtEmail;
    private JTable tbCan;
    private JButton btnFind;
    private JButton btnNew;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnOpen;
    private JButton btnExit;
    private JTextField txtBday;
    private JLabel Birthday;
    private JLabel ID;
    private JLabel Name;
    private JLabel Gender;
    private JLabel Category;
    private JLabel Price;

    DefaultComboBoxModel cbModel = new DefaultComboBoxModel<>();
    int row = -1;
    String file = "Manager.dat";
    List<Manager> list = new ArrayList<>();
    DefaultTableModel tbModel = new DefaultTableModel();

    public Management(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        cbModel.addElement("Basics ");
        cbModel.addElement("Hero");
        cbModel.addElement("Anime");
        cbModel.addElement("HF");
        cbCategory.setModel(cbModel);


        //**
        tbCan.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Product ID","Product Name","New or Old","Status","Price","Date of manufacture",
                }
        ));

        //**
        tbModel = (DefaultTableModel) tbCan.getModel();
        fillToTable();
        open();
        if(row>0) showDetail(row);

        //Add button
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add();
            }
        });

        //ClickDetail
        tbCan.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clickDetail();

            }
        });

        //New button
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        //Delete button
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Delete();
            }
        });

        //Update button
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });


        //Exit button
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               {
                    Management.this.saveFile();
                    Management.this.setVisible(false);
                    Management.this.dispose();
                    JFrame c = new ASM_Login("Login");
                    c.setSize(600, 250);
                    c.setLocationRelativeTo(null);
                    c.setVisible(true);
                }
            }
        });

        btnOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find();
            }
        });
        txtBday.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_SLASH)) {
                    e.consume();
                }
            }
        });
        txtName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(Character.isDigit(c)){
                    e.consume();
                }
            }
        });
    }
    public JTextField getTxtID() {
        return txtID;
    }

    public void setTxtID(JTextField txtID) {
        this.txtID = txtID;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public void setTxtName(JTextField txtName) {
        this.txtName = txtName;
    }

    public JRadioButton getRbMale() {
        return rbNew;
    }

    public void setRbMale(JRadioButton rbMale) {
        this.rbNew = rbMale;
    }

    public JRadioButton getRbFemale() {
        return rbFemale;
    }

    public void setRbFemale(JRadioButton rbFemale) {
        this.rbFemale = rbFemale;
    }

    public JComboBox getCbPosition() {
        return cbCategory;
    }

    public void setCbPosition(JComboBox cbPosition) {
        this.cbCategory = cbPosition;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public JTable getTbCan() {
        return tbCan;
    }

    public void setTbCan(JTable tbCan) {
        this.tbCan = tbCan;
    }

    public JTextField getTxtBday() {
        return txtBday;
    }

    public void setTxtBday(JTextField txtBday) {
        this.txtBday = txtBday;
    }

    public void find(){
        boolean findExist = false;
        if(tbCan.getRowCount() > 0) {
            for (int i = 0; i < tbCan.getRowCount(); i++) {
                String canId = (String) tbCan.getValueAt(i, 0);
                if (txtID.getText().equals(canId)) {
                    this.showDetail(i);
                    break;
                }
                if(tbCan.getRowCount() - 1 == i){
                    findExist = true;
                }
            }
        }else{
            JOptionPane.showMessageDialog(this, "Can not found any!", "Error", JOptionPane.WARNING_MESSAGE);
        }
        if(findExist == true){
            JOptionPane.showMessageDialog(this, "Can not found any!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    public void Add(){
        try {
            boolean IDexisted = false;
            if(tbCan.getRowCount() > 0) {
                for (int i = 0; i < tbCan.getRowCount(); i++) {
                    String cID = (String)tbCan.getValueAt(i, 0);
                    if (txtID.getText().equals(cID)) {
                        IDexisted = true;
                    }
                }
            }
            if(IDexisted) {
                JOptionPane.showMessageDialog(this, "Duplicate product ID", "Error", JOptionPane.WARNING_MESSAGE);
            }else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date bdate = sdf.parse(txtBday.getText());
                if (sdf.format(bdate).equals(txtBday.getText())) {
                    Date currentDay = new Date();
                    long diff = Math.abs(currentDay.getTime() - bdate.getTime());
                    long diffDays = (diff / (2460  * 1000));
                    if(diffDays >= 0) {
                        if (txtID.getText().equals("") || txtName.getText().equals("") || txtEmail.getText().equals("")) {
                            JOptionPane.showMessageDialog(this, "Please enter enough information!", "Error", JOptionPane.WARNING_MESSAGE);
                        } else {
                            this.addCan();
                            this.fillToTable();
                            this.saveFile();
                        }
                    }else{
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong birthday format!", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }catch(ParseException e) {
            JOptionPane.showMessageDialog(this, "Error!", "Error", JOptionPane.WARNING_MESSAGE);
        }
//        if(txtID.getText().equals("") || txtName.getText().equals("")
//        || txtEmail.getText().equals("") || txtBday.getText().equals(""))
//        {
//            nof+= "\nYou must have fill in all the information  adding";
//        }else{
//
//            //check ID
//            for (Manager M:list){
//                String ID = M.getID().trim().toLowerCase();
//                if(ID.equals(txtID.getText().trim().toLowerCase(Locale.ROOT))){
//                    nof+="\n ID Existed";
//                }
//            }
//
//            //Check Name
//            for (Manager M:list){
//                String ID = M.getID().trim().toLowerCase();
//                if(ID.equals(txtName.getText().trim().toLowerCase(Locale.ROOT))){
//                    nof+="\n ID Existed";
//                }
//            }
//        }
    }
    //clear form
    public void clearForm(){
        txtID.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtBday.setText("");
    }

    public void update(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date bdate = sdf.parse(txtBday.getText());
            if (sdf.format(bdate).equals(txtBday.getText())) {
                Date currentDay = new Date();
                long diff = Math.abs(currentDay.getTime() - bdate.getTime());
                long diffDays = (diff / (2460  * 1000));
                if (diffDays >= 0) {
                    if (txtID.getText().equals("") || txtName.getText().equals("") || txtEmail.getText().equals("")) {
                        JOptionPane.showMessageDialog(this, "Please enter enough information!", "Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        this.updateCan();
                        this.fillToTable();
                        this.saveFile();
                    }
                } else {
                }
            } else {
                JOptionPane.showMessageDialog(this, "Wrong birthday format!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }catch(ParseException e){
            JOptionPane.showMessageDialog(this, "Error!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    public void updateCan(){
        Manager c = list.get(row);
        c.setID(txtID.getText());
        c.setName(txtName.getText());
        c.setGender(rbFemale.isSelected());
        c.setPosition(cbCategory.getSelectedItem().toString());
        c.setEmail(txtEmail.getText());
        c.convert(txtBday.getText());
    }

    public void Delete(){
        //check selected row?
        if(row > -1){
            int op = JOptionPane.showConfirmDialog(this,"Do you wanna remove it?","Question",
                    JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if(op == JOptionPane.YES_OPTION){
                this.removeCan();
                this.clearForm();
                this.fillToTable();
                this.saveFile();
                JOptionPane.showMessageDialog(this,
                        "Delete successfully");
            }else{
                JOptionPane.showMessageDialog(this,
                        "Please choose someone","Requirement",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public void removeCan(){
        list.remove(row);
    }

//    public void sort(String cri){
//        Collections.sort(list, new Comparator<Manager>() {
//            @Override
//            public int compare(Manager o1, Manager o2) {
//                if(cri.equalsIgnoreCase("name")){
//                    return o1.getName().compareTo(o2.getName());
//                }
//                return 0;
//            }
//        });
//        this.fillToTable();
//    }

    public void clickDetail(){
        this.row = tbCan.getSelectedRow();
        this.showDetail(row);
    }

    public void addCan(){
        Manager c1 = new Manager(txtID.getText(),txtName.getText(),
                rbFemale.isSelected(),
                cbCategory.getSelectedItem().toString(),
                txtEmail.getText(),txtBday.getText());
        list.add(c1);
    }

    public void saveFile() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.flush();
            fos.flush();
            oos.close();
            oos.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void open() {
        try {
            //Open file
            this.openFile();//List
            if(list.size()>0){
                row = 0;
                //fill to table
                this.fillToTable();
                //show detail 1st row
                this.showDetail(row);
            }else{
                row = -1;
                this.clearForm();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void showDetail(int row){
        //String
        String ID = (String)tbCan.getValueAt(row,0);
        txtID.setText(ID);
        String Name = (String) tbCan.getValueAt(row,1);
        txtName.setText(Name);
        //Boolean
        if(tbCan.getValueAt(row,2) == "New"){
        rbFemale.setSelected(true);

        }else rbNew.setSelected(true);
        //String
        String Position = (String) tbCan.getValueAt(row,3);
        cbCategory.setSelectedItem(Position);
        String Mail = (String) tbCan.getValueAt(row,4);
        txtEmail.setText(Mail);
        //Date
        Date Bday = (Date) tbCan.getValueAt(row,5);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String str = sdf.format(Bday);
        txtBday.setText(str);
    }

    public void openFile() {
        String useDir = System.getProperty("user.dir");
        JFileChooser fc = new JFileChooser(useDir);
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File f = fc.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(f)){
                ObjectInputStream ois = new ObjectInputStream(fis);
                list = (ArrayList<Manager>)ois.readObject();
                ois.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Nothing at all");
            }
        }
    }

    public void fillToTable() {
        String Gender;
        tbModel.setRowCount(0);
        for (Manager c1:list) {
            if(c1.isGender()==true){
                Gender = "Old";
            }else Gender = "New";

            Object[] row = new Object[]{
                    c1.getID(), c1.getName(),
                    Gender,c1.getPosition(),
                    c1.getEmail(), c1.getBirthday()


            };
            tbModel.addRow(row);
        }
    }
    // Function Main
    public static void main(String[] args){
        JFrame c = new Management("Management");

        c.setSize(800,450);
        c.setLocationRelativeTo(null);//move form to center
        c.setVisible(true);
    }
}
