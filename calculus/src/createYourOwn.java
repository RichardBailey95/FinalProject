import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Richard on 01/04/2016.
 */
public class createYourOwn extends JFrame {
    private JPanel createWindow;
    private JTextArea Information;
    private JTextField numberOfProcessesInput;
    private JButton OKButton;
    private JPanel ProcessNumber;
    private JPanel ProcessNames;
    private JTextField nameInput;
    private JButton OKButton1;
    private JLabel processNaming;
    private JComboBox createChain;
    private JButton previousProcess;
    private JButton nextProcess;
    private JPanel creatingCalculus;
    private JLabel currentProcess;
    private JButton addButton;
    private JPanel calculusShow;
    private JPanel PopulateVariables;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton inputButton;
    private JButton finishedButton;
    private JButton editButton;
    private JPanel addTerm;
    private JButton createButton;
    private JButton addButton1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JPanel outputCreate;
    private JPanel inputCreate;
    private JPanel createChannels;
    private JComboBox channelB;
    private JComboBox channelA;
    private JButton createChannelButton;

    private calculus active;

    private int numberProcess;
    private int created = 0;
    private int editing = 0;

    public createYourOwn(calculus main) {
        super("Create Your Own");

        active = main;

        setContentPane(createWindow);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberProcess = Integer.parseInt(numberOfProcessesInput.getText());
                ProcessNumber.setVisible(false);
                ProcessNames.setVisible(true);
            }
        });
        OKButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active.createProcess(nameInput.getText());
                nameInput.setText("");
                created++;
                processNaming.setText("Please name process " + (created + 1));
                if (created == numberProcess) {
                    ProcessNames.setVisible(false);
                    PopulateVariables.setVisible(true);
                    populateChoices();
                }
            }
        });

        nextProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editing = (editing + 1) % numberProcess;
                creation();
            }
        });
        previousProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editing = (editing - 1 + numberProcess) % numberProcess;
                creation();
            }
        });

        finishedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PopulateVariables.setVisible(false);
                creatingCalculus.setVisible(true);
                creation();
            }
        });

        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active.input(comboBox1.getSelectedIndex(), textField1.getText(), textField2.getText());
                textField1.setText("");
                textField2.setText("");
            }
        });
        createChannelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active.createChannel(active.CYOprocesses.get(channelA.getSelectedIndex()), active.CYOprocesses.get(channelB.getSelectedIndex()));
            }
        });
    }

    private void creation() {
        currentProcess.setText("Currently editing " + active.CYOprocesses.get(editing).processName);
        previousProcess.setText("Edit " + active.CYOprocesses.get((editing - 1 + numberProcess) % numberProcess).processName);
        nextProcess.setText("Edit " + active.CYOprocesses.get((editing + 1) % numberProcess).processName);
    }

    public void updateInfo(String s) {
        Information.append(s);
    }

    public void clearText() {
        Information.setText("");
    }

    public void populateChoices(){
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (int i = 0; i < numberProcess; i++) {
            model.addElement(active.CYOprocesses.get(i).processName);
        }
        comboBox1.setModel(model);
        channelA.setModel(model);
        channelB.setModel(model);
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createWindow = new JPanel();
        createWindow.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 1, new Insets(5, 5, 5, 5), -1, -1));
        createWindow.setBackground(new Color(-4407875));
        createWindow.setForeground(new Color(-4407875));
        createWindow.setPreferredSize(new Dimension(450, 591));
        createWindow.setRequestFocusEnabled(true);
        calculusShow = new JPanel();
        calculusShow.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, -1));
        calculusShow.setBackground(new Color(-4407875));
        createWindow.add(calculusShow, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 250), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        calculusShow.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        Information = new JTextArea();
        Information.setBackground(new Color(-3289651));
        Information.setEditable(false);
        Information.setForeground(new Color(-15197922));
        scrollPane1.setViewportView(Information);
        ProcessNumber = new JPanel();
        ProcessNumber.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(5, 5, 5, 5), -1, -1));
        ProcessNumber.setBackground(new Color(-4407875));
        createWindow.add(ProcessNumber, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        numberOfProcessesInput = new JTextField();
        numberOfProcessesInput.setToolTipText("Insert the number of processes you require");
        ProcessNumber.add(numberOfProcessesInput, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        OKButton = new JButton();
        OKButton.setText("OK");
        ProcessNumber.add(OKButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("How many processes are in your system?");
        ProcessNumber.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ProcessNames = new JPanel();
        ProcessNames.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(5, 5, 5, 5), -1, -1));
        ProcessNames.setBackground(new Color(-4407875));
        ProcessNames.setEnabled(true);
        ProcessNames.setVisible(false);
        createWindow.add(ProcessNames, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        processNaming = new JLabel();
        processNaming.setText("Please name process 1");
        ProcessNames.add(processNaming, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameInput = new JTextField();
        ProcessNames.add(nameInput, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        OKButton1 = new JButton();
        OKButton1.setText("OK");
        ProcessNames.add(OKButton1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        creatingCalculus = new JPanel();
        creatingCalculus.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), -1, -1));
        creatingCalculus.setBackground(new Color(-4407875));
        creatingCalculus.setForeground(new Color(-4407875));
        creatingCalculus.setVisible(false);
        createWindow.add(creatingCalculus, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-4407875));
        panel1.setForeground(new Color(-4407875));
        creatingCalculus.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        createChain = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Output");
        defaultComboBoxModel1.addElement("Input");
        defaultComboBoxModel1.addElement("Encrypt");
        defaultComboBoxModel1.addElement("Decrypt");
        defaultComboBoxModel1.addElement("Create Pair");
        defaultComboBoxModel1.addElement("Split Pair");
        defaultComboBoxModel1.addElement("Restrict");
        defaultComboBoxModel1.addElement("Replicate");
        defaultComboBoxModel1.addElement("Match");
        defaultComboBoxModel1.addElement("Integer Case");
        defaultComboBoxModel1.addElement("Arithmetic Operation");
        createChain.setModel(defaultComboBoxModel1);
        panel1.add(createChain, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setText("Button");
        panel1.add(addButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-4407875));
        panel2.setForeground(new Color(-4407875));
        creatingCalculus.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nextProcess = new JButton();
        nextProcess.setText("Button");
        panel2.add(nextProcess, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, -1), null, 0, false));
        currentProcess = new JLabel();
        currentProcess.setText("Label");
        panel2.add(currentProcess, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        previousProcess = new JButton();
        previousProcess.setText("Button");
        panel2.add(previousProcess, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, -1), null, 0, false));
        PopulateVariables = new JPanel();
        PopulateVariables.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 4, new Insets(5, 5, 5, 5), -1, -1));
        PopulateVariables.setBackground(new Color(-4407875));
        PopulateVariables.setForeground(new Color(-4407875));
        PopulateVariables.setVisible(false);
        createWindow.add(PopulateVariables, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        PopulateVariables.add(comboBox1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        textField1.setToolTipText("The value of the variable");
        PopulateVariables.add(textField1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField2 = new JTextField();
        textField2.setToolTipText("The binding of the variable");
        PopulateVariables.add(textField2, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        inputButton = new JButton();
        inputButton.setText("Input");
        PopulateVariables.add(inputButton, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        finishedButton = new JButton();
        finishedButton.setText("Finished");
        PopulateVariables.add(finishedButton, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return createWindow;
    }
}
