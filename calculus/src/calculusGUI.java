import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Richard on 14/03/2016.
 */
public class calculusGUI extends JFrame {
    private JPanel mainWindow;
    private JTextArea processState;
    private JButton wideMouthFrogButton;
    private JButton factorialExampleButton;
    private JButton needhamSchroederButton;
    public JButton proceedButton;
    private JTextArea outputText;
    private JTextField factInput;
    public JButton stopButton;
    private JButton mainTestButton;
    private JButton createOwnButton;
    private JButton needhamSchroederIntruderButton;
    private calculus active;

    public calculusGUI(calculus program) {
        super("Spi Calculus");

        setContentPane(mainWindow);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        DefaultCaret caret = (DefaultCaret) outputText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        needhamSchroederButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active.process = 2;
                stopButton.setEnabled(true);
                proceedButton.setEnabled(true);
                wideMouthFrogButton.setEnabled(false);
                factorialExampleButton.setEnabled(false);
                mainTestButton.setEnabled(false);
                factInput.setEnabled(false);
                needhamSchroederIntruderButton.setEnabled(false);
            }
        });

        needhamSchroederIntruderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active.process = 5;
                stopButton.setEnabled(true);
                proceedButton.setEnabled(true);
                needhamSchroederButton.setEnabled(false);
                wideMouthFrogButton.setEnabled(false);
                factorialExampleButton.setEnabled(false);
                mainTestButton.setEnabled(false);
                factInput.setEnabled(false);
            }
        });


        wideMouthFrogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active.process = 1;
                stopButton.setEnabled(true);
                proceedButton.setEnabled(true);
                needhamSchroederButton.setEnabled(false);
                factorialExampleButton.setEnabled(false);
                mainTestButton.setEnabled(false);
                factInput.setEnabled(false);
                needhamSchroederIntruderButton.setEnabled(false);
            }
        });

        factorialExampleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active.factNumber = Integer.parseInt(factInput.getText());
                active.process = 3;
                stopButton.setEnabled(true);
                proceedButton.setEnabled(true);
                needhamSchroederButton.setEnabled(false);
                wideMouthFrogButton.setEnabled(false);
                mainTestButton.setEnabled(false);
                needhamSchroederIntruderButton.setEnabled(false);
            }
        });

        mainTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active.process = 4;
                stopButton.setEnabled(true);
                proceedButton.setEnabled(true);
                needhamSchroederButton.setEnabled(false);
                wideMouthFrogButton.setEnabled(false);
                factorialExampleButton.setEnabled(false);
                factInput.setEnabled(false);
                needhamSchroederIntruderButton.setEnabled(false);
            }
        });

        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active.proceed = true;
                if (proceedButton.getText().equals("Clear")) {
                    proceedButton.setText("Start");
                    proceedButton.setEnabled(false);
                    needhamSchroederButton.setEnabled(true);
                    wideMouthFrogButton.setEnabled(true);
                    factorialExampleButton.setEnabled(true);
                    mainTestButton.setEnabled(true);
                    factInput.setEnabled(true);
                } else if (proceedButton.getText().equals("Start")) {
                    proceedButton.setText("Proceed");
                    active.masterStop = false;
                }
            }
        });

        this.active = program;
        setVisible(true);

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active.masterStop = true;
                active.proceed = true;
                proceedButton.setText("Clear");
                stopButton.setEnabled(false);
            }
        });
        createOwnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active.process = 6;
            }
        });
    }

    public void updateOutput(String toOutput) {
        outputText.append(toOutput + "\n");
        active.outputProcesses();
        return;
    }

    public void updateState(String toState) {
        processState.append(toState + "\n");
        return;
    }

    public void clearStates() {
        processState.setText("");
    }

    public void clearOutput() {
        outputText.setText("");
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
        mainWindow = new JPanel();
        mainWindow.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(5, 5, 5, 5), -1, -1));
        mainWindow.setBackground(new Color(-4866627));
        mainWindow.setForeground(new Color(-4866627));
        mainWindow.setMaximumSize(new Dimension(1920, 1080));
        mainWindow.setPreferredSize(new Dimension(825, 520));
        mainWindow.setRequestFocusEnabled(true);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-15197922));
        panel1.setForeground(new Color(-4866627));
        mainWindow.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(600, 400), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setAutoscrolls(true);
        panel1.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        processState = new JTextArea();
        processState.setBackground(new Color(-3289651));
        processState.setDisabledTextColor(new Color(-15197922));
        processState.setEditable(false);
        processState.setForeground(new Color(-15197922));
        processState.setLineWrap(true);
        processState.setMargin(new Insets(10, 10, 0, 0));
        processState.setText("");
        processState.setWrapStyleWord(true);
        scrollPane1.setViewportView(processState);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 2, new Insets(5, 5, 5, 15), -1, -1));
        panel2.setBackground(new Color(-4866627));
        panel2.setForeground(new Color(-4866627));
        mainWindow.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 400), null, 0, false));
        wideMouthFrogButton = new JButton();
        wideMouthFrogButton.setHideActionText(false);
        wideMouthFrogButton.setText("Wide Mouth Frog");
        panel2.add(wideMouthFrogButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        factorialExampleButton = new JButton();
        factorialExampleButton.setText("Factorial Example");
        panel2.add(factorialExampleButton, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        needhamSchroederButton = new JButton();
        needhamSchroederButton.setEnabled(true);
        needhamSchroederButton.setText("Needham Schroeder");
        panel2.add(needhamSchroederButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        factInput = new JTextField();
        panel2.add(factInput, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        mainTestButton = new JButton();
        mainTestButton.setEnabled(true);
        mainTestButton.setText("General Test");
        panel2.add(mainTestButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createOwnButton = new JButton();
        createOwnButton.setEnabled(true);
        createOwnButton.setText("Create Your Own");
        panel2.add(createOwnButton, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        needhamSchroederIntruderButton = new JButton();
        needhamSchroederIntruderButton.setEnabled(true);
        needhamSchroederIntruderButton.setText("Needham Schroeder Intruder");
        panel2.add(needhamSchroederIntruderButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-4866627));
        panel3.setForeground(new Color(-4866627));
        mainWindow.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 100), null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setAutoscrolls(true);
        scrollPane2.setForeground(new Color(-15197922));
        scrollPane2.setWheelScrollingEnabled(true);
        panel3.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(600, -1), null, 0, false));
        outputText = new JTextArea();
        outputText.setAutoscrolls(true);
        outputText.setBackground(new Color(-3289651));
        outputText.setEditable(false);
        outputText.setForeground(new Color(-15197922));
        outputText.setInheritsPopupMenu(false);
        outputText.setLineWrap(true);
        outputText.setMargin(new Insets(10, 10, 0, 0));
        outputText.setText("");
        outputText.setWrapStyleWord(true);
        scrollPane2.setViewportView(outputText);
        proceedButton = new JButton();
        proceedButton.setEnabled(false);
        proceedButton.setHorizontalAlignment(0);
        proceedButton.setText("Start");
        panel3.add(proceedButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(95, 30), null, 0, false));
        stopButton = new JButton();
        stopButton.setEnabled(false);
        stopButton.setText("Stop");
        panel3.add(stopButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(95, 30), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainWindow;
    }
}
