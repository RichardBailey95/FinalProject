import chains.ChainElement;
import process.Process;
import terms.Term;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static java.util.Collections.singletonList;

/**
 * Created by Richard on 01/04/2016.
 */
public class createYourOwn extends JFrame implements Serializable {
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
    private JPanel createVariables;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton inputButton;
    private JButton finishedButton;
    private JButton editButton;
    private JPanel addTerm;
    private JButton createButton;
    private JButton addOutput;
    private JTextField channelsOut;
    private JTextField variableOut;
    private JPanel outputCreate;
    private JPanel inputCreate;
    private JPanel createChannels;
    private JButton createChannelButton;
    private JButton finishedChannels;
    private JTextField channelName;
    private JPanel addOrEdit;
    private JTextField inputVariableBind;
    private JTextField channelsIn;
    private JButton addInput;
    private JButton backButton;
    private JButton runButton;
    private JButton backButton1;
    private JButton backButton2;
    private JButton addVariableButton;
    private JButton addChannelButton;
    private JButton addKeyButton;
    private JPanel createKeys;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JButton createKeyButton;
    private JButton finishedKeyButton;
    private JPanel encryptCreate;
    private JButton addEncrypt;
    private JTextField keyToEncryptWith;
    private JTextField variableToEncrypt;
    private JButton backButton3;
    private JPanel decryptCreate;
    private JTextField bindToDecrypt;
    private JButton backButton4;
    private JTextField decryptVariable;
    private JTextField decryptKey;
    private JButton addDecrypt;
    private JPanel createPair;
    private JButton backButton5;
    private JButton addPairCreate;
    private JTextField pairACreate;
    private JTextField pairBCreate;
    private JTextField pairBindCreate;
    private JPanel splitPair;
    private JTextField pairBindSplit;
    private JButton addPairSplit;
    private JButton backButton6;
    private JTextField pairASplit;
    private JTextField pairBSplit;
    private JPanel restrictCreate;
    private JPanel replicateCreate;
    private JPanel matchCreate;
    private JPanel integerCaseCreate;
    private JPanel arithmeticCreate;
    private JComboBox comboBox4;
    private JTextField arithmeticB;
    private JTextField arithmeticA;
    private JButton backButton11;
    private JButton addArithmetic;
    private JButton backButton7;
    private JButton endReplicate;
    private JButton backButton9;
    private JButton backButton10;
    private JCheckBox numberCheckBox;
    private JTextField matchA;
    private JTextField matchB;
    private JButton addMatch;
    private JButton addReplicate;
    private JComboBox replicateSelection;
    private JTextField caseA;
    private JTextField succCase;
    private JButton addIntegerCase;
    public JTextArea calculusRep;
    private JPanel intCase1Create;
    private JPanel intCase2Create;
    private JComboBox intCase1Select;
    private JButton intCase1Add;
    private JButton intCase1End;
    private JComboBox intCase2Select;
    private JButton intCase2Add;
    private JButton intCase2End;
    private JLabel nextTerm;
    private JLabel prevTerm;
    private JLabel currentTerm;
    private JButton prevTermButton;
    private JButton nextTermButton;
    private JButton delTermButton;
    private JButton editTermButton;
    private JPanel editPanel;
    private JButton endEditButton;
    private JButton addTermButton;
    private JPanel editSelection;
    private JPanel mainMenu;
    private JButton newButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton resetButton;

    private calculus active;

    private JFileChooser fc = new JFileChooser();



    private ArrayList<ArrayList<ArrayList<String>>> calcRep = new ArrayList<>();

    private boolean replication = false;
    private boolean intCase1 = false;
    private boolean intCase2 = false;
    private boolean editAdd = false;
    private boolean editTerm = false;
    private boolean editReplicate = false;
    private boolean editingActive = false;


    private int numberProcess;
    private int created = 0;
    private int editing = 0;
    private int editingTerm = 1;
    private int replicateEdit = 1;
    private int editingChain = 0;
    private int editingReplicate = 0;
    private int editIntCase = 0;

    ArrayList<ArrayList<ChainElement>> chain = new ArrayList<>();
    private ArrayList<ChainElement> replicate = new ArrayList<>();
    private ArrayList<ChainElement> intCase1chain = new ArrayList<>();
    private ArrayList<ChainElement> intCase2chain = new ArrayList<>();
    private ArrayList<ChainElement> editReplicateChain;


    createYourOwn(calculus main) {
        super("Create Your Own");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Spi Calculus Files", "spi");
        fc.setFileFilter(filter);

        active = main;

        setContentPane(createWindow);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(false);

        ArrayList<JPanel> creation = new ArrayList<>();
        creation.add(outputCreate);
        creation.add(inputCreate);
        creation.add(encryptCreate);
        creation.add(decryptCreate);
        creation.add(createPair);
        creation.add(splitPair);
        //creation.add(restrictCreate);
        creation.add(replicateCreate);
        creation.add(matchCreate);
        creation.add(integerCaseCreate);
        creation.add(arithmeticCreate);


        // CREATE AND NAME PROCESSES
        OKButton.addActionListener(e -> {
            numberProcess = Integer.parseInt(numberOfProcessesInput.getText());
            ProcessNumber.setVisible(false);
            ProcessNames.setVisible(true);
        });
        OKButton1.addActionListener(e -> {
            active.createProcess(nameInput.getText());
            chain.add(new ArrayList<>());
            chain.get(created).add(new ChainElement(active.CYOprocesses.get(created)));
            calcRep.add(new ArrayList<>());
            calcRep.get(created).add(new ArrayList<>(singletonList(nameInput.getText() + " = ")));
            updateRep();
            nameInput.setText("");
            created++;
            processNaming.setText("Please name process " + (created + 1));
            if (created == numberProcess) {
                ProcessNames.setVisible(false);
                creatingCalculus.setVisible(true);
                populateChoices();
                creation();
            }
        });

        // CHANGE EDITING PROCESS
        nextProcess.addActionListener(e -> {
            editing = (editing + 1) % numberProcess;
            editingTerm = 1;
            creation();
            updateEdit();
        });
        previousProcess.addActionListener(e -> {
            editing = (editing - 1 + numberProcess) % numberProcess;
            editingTerm = 1;
            creation();
            updateEdit();
        });


        // Finished variables
        finishedButton.addActionListener(e -> {
            createVariables.setVisible(false);
            creatingCalculus.setVisible(true);
        });
        // Add variable
        inputButton.addActionListener(e -> {
            active.input(comboBox1.getSelectedIndex(), textField1.getText(), textField2.getText(), numberCheckBox.isSelected());
            textField1.setText("");
            textField2.setText("");
        });

        // Add channel
        createChannelButton.addActionListener(e -> {
            active.createChannelCYO(channelName.getText());
            channelName.setText("");
            active.showInformation();
        });
        finishedChannels.addActionListener(e -> {
            createChannels.setVisible(false);
            creatingCalculus.setVisible(true);
        });

        // Add key
        createKeyButton.addActionListener(e -> active.createKey(comboBox2.getSelectedIndex(), comboBox3.getSelectedIndex()));
        finishedKeyButton.addActionListener(e -> {
            createKeys.setVisible(false);
            creatingCalculus.setVisible(true);
        });

        // ADD, EDIT, RUN
        addButton.addActionListener(e -> {
            addOrEdit.setVisible(false);
            nextProcess.setVisible(false);
            previousProcess.setVisible(false);
            addTerm.setVisible(true);
        });

        runButton.addActionListener(e -> {
            active.process = 6;
            active.masterStop = false;
        });

        addChannelButton.addActionListener(e -> {
            createChannels.setVisible(true);
            creatingCalculus.setVisible(false);
        });

        addKeyButton.addActionListener(e -> {
            createKeys.setVisible(true);
            creatingCalculus.setVisible(false);
            populate();
        });

        addVariableButton.addActionListener(e -> {
            createVariables.setVisible(true);
            creatingCalculus.setVisible(false);
        });

        createButton.addActionListener(e -> {
            addTerm.setVisible(false);
            creation.get(createChain.getSelectedIndex()).setVisible(true);
            if (createChain.getSelectedIndex() == 6) {
                if (editAdd && (editIntCase == 2)) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + 1, "!(");
                    calcRep.get(editing).get(editingTerm).add(editingChain + 2, "0).");
                } else if (editAdd && (editIntCase == 3)) {
                    calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + editingChain + 2, "!(");
                    calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + editingChain + 3, "0).");
                } else if (editAdd) {
                    calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList("!(")));
                    calcRep.get(editing).get(editingTerm).add("0).");
                } else {
                    calcRep.get(editing).add(new ArrayList<>(Arrays.asList("!(", "0).")));
                }

                updateRep();
            }
            populate();
        });
        backButton.addActionListener(e -> {
            addTerm.setVisible(false);
            if (editingActive) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
            } else {
                addOrEdit.setVisible(true);
                nextProcess.setVisible(true);
                previousProcess.setVisible(true);
            }
        });

        addOutput.addActionListener(e -> {
            if (intCase1) {
                intCase1chain.add(active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
            } else if (intCase2) {
                intCase2chain.add(active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
            } else if (editAdd && replication && (editIntCase > 1)) {
                replicate.add(active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
            } else if (editAdd && replication) {
                replicate.add(editingChain, active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
            } else if (replication) {
                replicate.add(active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
            } else {
                if (editReplicate && editAdd) {
                    editReplicateChain.add(editingChain, active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
                } else if (editIntCase == 2 && editAdd) {
                    intCase1chain.add(editingChain, active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
                } else if (editIntCase == 3 && editAdd) {
                    intCase2chain.add(editingChain, active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
                } else if (editAdd) {
                    chain.get(editing).add(editingTerm, active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
                } else if (editReplicate) {
                    editReplicateChain.remove(editingChain);
                    editReplicateChain.add(editingChain, active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
                } else if (editIntCase == 2) {
                    intCase1chain.remove(editingChain);
                    intCase1chain.add(editingChain, active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
                } else if (editIntCase == 3) {
                    intCase2chain.remove(editingChain);
                    intCase2chain.add(editingChain, active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
                } else if (editTerm) {
                    chain.get(editing).remove(editingTerm);
                    chain.get(editing).add(editingTerm, active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
                } else {
                    chain.get(editing).add(active.createChainLink("O", channelsOut.getText(), variableOut.getText()));
                }
            }
            active.showInformation();
            String channel = "";
            for (int i = 0; i < channelsOut.getText().length(); i++) {
                channel = channel + channelsOut.getText().charAt(i) + "\u0305";
            }

            if (editAdd && replication && (editIntCase == 2)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(replicate) + editingChain + 1, String.format("%s<%s>.", channel, variableOut.getText()));
            } else if (editAdd && replication && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + sizeOfChain(replicate) + editingChain + 2, String.format("%s<%s>.", channel, variableOut.getText()));
            } else if (editAdd && replication) {
                calcRep.get(editing).get(editingTerm).add(replicateEdit, String.format("%s<%s>.", channel, variableOut.getText()));
                replicateEdit++;
            } else if (replication) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(calcRep.get(editing).get(calcRep.get(editing).size() - 1).size() - 1, String.format("%s<%s>.", channel, variableOut.getText()));
            } else if (intCase1) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("%s<%s>.", channel, variableOut.getText()));
            } else if (intCase2) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("%s<%s>.", channel, variableOut.getText()));
            } else if (editReplicate && editAdd && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + sizeOfChain(intCase1chain) + editingChain + 2, String.format("%s<%s>.", channel, variableOut.getText()));
            } else if (editReplicate && editAdd) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + editingChain + 1, String.format("%s<%s>.", channel, variableOut.getText()));
            } else if (editAdd) {
                if (editIntCase == 2) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("%s<%s>.", channel, variableOut.getText()));
                } else if (editIntCase == 3) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + intCase1chain.size() + 2, String.format("%s<%s>.", channel, variableOut.getText()));
                } else {
                    calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("%s<%s>.", channel, variableOut.getText()))));
                }
            } else if (editReplicate) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("%s<%s>.", channel, variableOut.getText()));
            } else if (editIntCase == 2) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("%s<%s>.", channel, variableOut.getText()));
            } else if (editIntCase == 3) {
                calcRep.get(editing).get(editingTerm).remove(sizeOfChain(intCase1chain) + editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + editingChain + 1, String.format("%s<%s>.", channel, variableOut.getText()));
            } else if (editTerm) {
                calcRep.get(editing).remove(editingTerm);
                calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("%s<%s>.", channel, variableOut.getText()))));
            } else {
                calcRep.get(editing).add(new ArrayList<>(singletonList(String.format("%s<%s>.", channel, variableOut.getText()))));
            }

            updateRep();
            outputCreate.setVisible(false);
            if (intCase1) {
                intCase1Create.setVisible(true);
            } else if (intCase2) {
                intCase2Create.setVisible(true);
            } else if (replication) {
                replicateCreate.setVisible(true);
            } else if (editReplicate) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(editReplicateChain);
            } else if (editIntCase == 2) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase1chain);
            } else if (editIntCase == 3) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase2chain);
            } else if (editAdd || editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editAdd = false;
                editTerm = false;
                nextProcess.setVisible(true);
                previousProcess.setVisible(true);
                updateEdit();
            } else {
                addTerm.setVisible(true);
            }
            channelsOut.setText("");
            variableOut.setText("");
        });

        addInput.addActionListener(e -> {
            if (intCase1) {
                intCase1chain.add(active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
            } else if (intCase2) {
                intCase2chain.add(active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
            } else if (editAdd && replication && (editIntCase > 1)) {
                replicate.add(active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
            } else if (editAdd && replication) {
                replicate.add(editingChain, active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
            } else if (replication) {
                replicate.add(active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
            } else {
                if (editReplicate && editAdd) {
                    editReplicateChain.add(editingChain, active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
                } else if (editIntCase == 2 && editAdd) {
                    intCase1chain.add(editingChain, active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
                } else if (editIntCase == 3 && editAdd) {
                    intCase2chain.add(editingChain, active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
                } else if (editAdd) {
                    chain.get(editing).add(editingTerm, active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
                } else if (editReplicate) {
                    editReplicateChain.remove(editingChain);
                    editReplicateChain.add(editingChain, active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
                } else if (editIntCase == 2) {
                    intCase1chain.remove(editingChain);
                    intCase1chain.add(editingChain, active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
                } else if (editIntCase == 3) {
                    intCase2chain.remove(editingChain);
                    intCase2chain.add(editingChain, active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
                } else if (editTerm) {
                    chain.get(editing).remove(editingTerm);
                    chain.get(editing).add(editingTerm, active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
                } else {
                    chain.get(editing).add(active.createChainLink("I", channelsIn.getText(), inputVariableBind.getText()));
                }
            }
            active.showInformation();

            if (editAdd && replication && (editIntCase == 2)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(replicate) + editingChain + 1, String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
            } else if (editAdd && replication && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + sizeOfChain(replicate) + editingChain + 2, String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
            } else if (editAdd && replication) {
                calcRep.get(editing).get(editingTerm).add(replicateEdit, String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
                replicateEdit++;
            } else if (replication) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(calcRep.get(editing).get(calcRep.get(editing).size() - 1).size() - 1, String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
            } else if (intCase1) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
            } else if (intCase2) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
            } else if (editReplicate && editAdd && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + sizeOfChain(intCase1chain) + editingChain + 2, String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
            } else if (editReplicate && editAdd) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + editingChain + 1, String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
            } else if (editAdd) {
                if (editIntCase == 2) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
                } else if (editIntCase == 3) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + intCase1chain.size() + 2, String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
                } else {
                    calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()))));
                }
            } else if (editReplicate) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
            } else if (editIntCase == 2) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
            } else if (editIntCase == 3) {
                calcRep.get(editing).get(editingTerm).remove(sizeOfChain(intCase1chain) + editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + editingChain + 1, String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()));
            } else if (editTerm) {
                calcRep.get(editing).remove(editingTerm);
                calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()))));
            } else {
                calcRep.get(editing).add(new ArrayList<>(singletonList(String.format("%s(%s).", channelsIn.getText(), inputVariableBind.getText()))));
            }

            updateRep();
            inputCreate.setVisible(false);
            if (intCase1) {
                intCase1Create.setVisible(true);
            } else if (intCase2) {
                intCase2Create.setVisible(true);
            } else if (replication) {
                replicateCreate.setVisible(true);
            } else if (editReplicate) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(editReplicateChain);
            } else if (editIntCase == 2) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase1chain);
            } else if (editIntCase == 3) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase2chain);
            } else if (editAdd || editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editAdd = false;
                editTerm = false;
                nextProcess.setVisible(true);
                previousProcess.setVisible(true);
                updateEdit();
            } else {
                addTerm.setVisible(true);
            }
            channelsIn.setText("");
            inputVariableBind.setText("");
        });
        addEncrypt.addActionListener(e -> {
            String keyToUse = key(keyToEncryptWith.getText());
            if (intCase1) {
                intCase1chain.add(active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
            } else if (intCase2) {
                intCase2chain.add(active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
            } else if (editAdd && replication && (editIntCase > 1)) {
                replicate.add(active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
            } else if (editAdd && replication) {
                replicate.add(editingChain, active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
            } else if (replication) {
                replicate.add(active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
            } else {
                if (editReplicate && editAdd) {
                    editReplicateChain.add(editingChain, active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
                } else if (editIntCase == 2 && editAdd) {
                    intCase1chain.add(editingChain, active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
                } else if (editIntCase == 3 && editAdd) {
                    intCase2chain.add(editingChain, active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
                } else if (editAdd) {
                    chain.get(editing).add(editingTerm, active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
                } else if (editReplicate) {
                    editReplicateChain.remove(editingChain);
                    editReplicateChain.add(editingChain, active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
                } else if (editIntCase == 2) {
                    intCase1chain.remove(editingChain);
                    intCase1chain.add(editingChain, active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
                } else if (editIntCase == 3) {
                    intCase2chain.remove(editingChain);
                    intCase2chain.add(editingChain, active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
                } else if (editTerm) {
                    chain.get(editing).remove(editingTerm);
                    chain.get(editing).add(editingTerm, active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
                } else {
                    chain.get(editing).add(active.createChainLink("E", variableToEncrypt.getText(), keyToUse));
                }
            }
            active.showInformation();

            if (editAdd && replication && (editIntCase == 2)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(replicate) + editingChain + 1, String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
            } else if (editAdd && replication && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + sizeOfChain(replicate) + editingChain + 2, String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
            } else if (editAdd && replication) {
                calcRep.get(editing).get(editingTerm).add(replicateEdit, String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
                replicateEdit++;
            } else if (replication) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(calcRep.get(editing).get(calcRep.get(editing).size() - 1).size() - 1, String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
            } else if (intCase1) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
            } else if (intCase2) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
            } else if (editReplicate && editAdd && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + sizeOfChain(intCase1chain) + editingChain + 2, String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
            } else if (editReplicate && editAdd) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + editingChain + 1, String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
            } else if (editAdd) {
                if (editIntCase == 2) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
                } else if (editIntCase == 3) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + intCase1chain.size() + 2, String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
                } else {
                    calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse))));
                }
            } else if (editReplicate) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
            } else if (editIntCase == 2) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
            } else if (editIntCase == 3) {
                calcRep.get(editing).get(editingTerm).remove(sizeOfChain(intCase1chain) + editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + editingChain + 1, String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse));
            } else if (editTerm) {
                calcRep.get(editing).remove(editingTerm);
                calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse))));
            } else {
                calcRep.get(editing).add(new ArrayList<>(singletonList(String.format("{%s}%s.", variableToEncrypt.getText(), keyToUse))));
            }

            updateRep();
            encryptCreate.setVisible(false);
            if (intCase1) {
                intCase1Create.setVisible(true);
            } else if (intCase2) {
                intCase2Create.setVisible(true);
            } else if (replication) {
                replicateCreate.setVisible(true);
            } else if (editReplicate) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(editReplicateChain);
            } else if (editIntCase == 2) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase1chain);
            } else if (editIntCase == 3) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase2chain);
            } else if (editAdd || editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editAdd = false;
                editTerm = false;
                nextProcess.setVisible(true);
                previousProcess.setVisible(true);
                updateEdit();
            } else {
                addTerm.setVisible(true);
            }
            variableToEncrypt.setText("");
            keyToEncryptWith.setText("");
        });
        addDecrypt.addActionListener(e -> {
            String keyToUse = key(decryptKey.getText());
            if (intCase1) {
                intCase1chain.add(active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
            } else if (intCase2) {
                intCase2chain.add(active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
            } else if (editAdd && replication && (editIntCase > 1)) {
                replicate.add(active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
            } else if (editAdd && replication) {
                replicate.add(editingChain, active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
            } else if (replication) {
                replicate.add(active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
            } else {
                if (editReplicate && editAdd) {
                    editReplicateChain.add(editingChain, active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
                } else if (editIntCase == 2 && editAdd) {
                    intCase1chain.add(editingChain, active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
                } else if (editIntCase == 3 && editAdd) {
                    intCase2chain.add(editingChain, active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
                } else if (editAdd) {
                    chain.get(editing).add(editingTerm, active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
                } else if (editReplicate) {
                    editReplicateChain.remove(editingChain);
                    editReplicateChain.add(editingChain, active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
                } else if (editIntCase == 2) {
                    intCase1chain.remove(editingChain);
                    intCase1chain.add(editingChain, active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
                } else if (editIntCase == 3) {
                    intCase2chain.remove(editingChain);
                    intCase2chain.add(editingChain, active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
                } else if (editTerm) {
                    chain.get(editing).remove(editingTerm);
                    chain.get(editing).add(editingTerm, active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
                } else {
                    chain.get(editing).add(active.createChainLink("D", decryptVariable.getText(), keyToUse, bindToDecrypt.getText()));
                }
            }
            active.showInformation();

            if (editAdd && replication && (editIntCase == 2)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(replicate) + editingChain + 1, String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
            } else if (editAdd && replication && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + sizeOfChain(replicate) + editingChain + 2, String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
            } else if (editAdd && replication) {
                calcRep.get(editing).get(editingTerm).add(replicateEdit, String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
                replicateEdit++;
            } else if (replication) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(calcRep.get(editing).get(calcRep.get(editing).size() - 1).size() - 1, String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
            } else if (intCase1) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
            } else if (intCase2) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
            } else if (editReplicate && editAdd && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + sizeOfChain(intCase1chain) + editingChain + 2, String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
            } else if (editReplicate && editAdd) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + editingChain + 1, String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
            } else if (editAdd) {
                if (editIntCase == 2) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
                } else if (editIntCase == 3) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + intCase1chain.size() + 2, String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
                } else {
                    calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse))));
                }
            } else if (editReplicate) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
            } else if (editIntCase == 2) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
            } else if (editIntCase == 3) {
                calcRep.get(editing).get(editingTerm).remove(sizeOfChain(intCase1chain) + editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + editingChain + 1, String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse));
            } else if (editTerm) {
                calcRep.get(editing).remove(editingTerm);
                calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse))));
            } else {
                calcRep.get(editing).add(new ArrayList<>(singletonList(String.format("case %s of {%s}%s in ", decryptVariable.getText(), bindToDecrypt.getText(), keyToUse))));
            }

            updateRep();
            decryptCreate.setVisible(false);
            if (intCase1) {
                intCase1Create.setVisible(true);
            } else if (intCase2) {
                intCase2Create.setVisible(true);
            } else if (replication) {
                replicateCreate.setVisible(true);
            } else if (editReplicate) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(editReplicateChain);
            } else if (editIntCase == 2) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase1chain);
            } else if (editIntCase == 3) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase2chain);
            } else if (editAdd || editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editAdd = false;
                editTerm = false;
                nextProcess.setVisible(true);
                previousProcess.setVisible(true);
                updateEdit();
            } else {
                addTerm.setVisible(true);
            }
            bindToDecrypt.setText("");
            decryptKey.setText("");
            decryptVariable.setText("");
        });
        addPairCreate.addActionListener(e -> {
            if (intCase1) {
                intCase1chain.add(active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (intCase2) {
                intCase2chain.add(active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (editAdd && replication && (editIntCase > 1)) {
                replicate.add(active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (editAdd && replication) {
                replicate.add(editingChain, active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (replication) {
                replicate.add(active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else {
                if (editReplicate && editAdd) {
                    editReplicateChain.add(editingChain, active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
                } else if (editIntCase == 2 && editAdd) {
                    intCase1chain.add(editingChain, active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
                } else if (editIntCase == 3 && editAdd) {
                    intCase2chain.add(editingChain, active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
                } else if (editAdd) {
                    chain.get(editing).add(editingTerm, active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
                } else if (editReplicate) {
                    editReplicateChain.remove(editingChain);
                    editReplicateChain.add(editingChain, active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
                } else if (editIntCase == 2) {
                    intCase1chain.remove(editingChain);
                    intCase1chain.add(editingChain, active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
                } else if (editIntCase == 3) {
                    intCase2chain.remove(editingChain);
                    intCase2chain.add(editingChain, active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
                } else if (editTerm) {
                    chain.get(editing).remove(editingTerm);
                    chain.get(editing).add(editingTerm, active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
                } else {
                    chain.get(editing).add(active.createChainLink("P", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
                }
            }
            active.showInformation();

            if (editAdd && replication && (editIntCase == 2)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(replicate) + editingChain + 1, String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (editAdd && replication && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + sizeOfChain(replicate) + editingChain + 2, String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (editAdd && replication) {
                calcRep.get(editing).get(editingTerm).add(replicateEdit, String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
                replicateEdit++;
            } else if (replication) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(calcRep.get(editing).get(calcRep.get(editing).size() - 1).size() - 1, String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (intCase1) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (intCase2) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (editReplicate && editAdd && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + sizeOfChain(intCase1chain) + editingChain + 2, String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (editReplicate && editAdd) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + editingChain + 1, String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (editAdd) {
                if (editIntCase == 2) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
                } else if (editIntCase == 3) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + intCase1chain.size() + 2, String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
                } else {
                    calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()))));
                }
            } else if (editReplicate) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (editIntCase == 2) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (editIntCase == 3) {
                calcRep.get(editing).get(editingTerm).remove(sizeOfChain(intCase1chain) + editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + editingChain + 1, String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()));
            } else if (editTerm) {
                calcRep.get(editing).remove(editingTerm);
                calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()))));
            } else {
                calcRep.get(editing).add(new ArrayList<>(singletonList(String.format("%s=(%s,%s).", pairBindCreate.getText(), pairACreate.getText(), pairBCreate.getText()))));
            }

            updateRep();
            createPair.setVisible(false);
            if (intCase1) {
                intCase1Create.setVisible(true);
            } else if (intCase2) {
                intCase2Create.setVisible(true);
            } else if (replication) {
                replicateCreate.setVisible(true);
            } else if (editReplicate) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(editReplicateChain);
            } else if (editIntCase == 2) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase1chain);
            } else if (editIntCase == 3) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase2chain);
            } else if (editAdd || editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editAdd = false;
                editTerm = false;
                nextProcess.setVisible(true);
                previousProcess.setVisible(true);
                updateEdit();
            } else {
                addTerm.setVisible(true);
            }
            pairBindCreate.setText("");
            pairACreate.setText("");
            pairBCreate.setText("");
        });
        addPairSplit.addActionListener(e -> {
            if (intCase1) {
                intCase1chain.add(active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
            } else if (intCase2) {
                intCase2chain.add(active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
            } else if (editAdd && replication && (editIntCase > 1)) {
                replicate.add(active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
            } else if (editAdd && replication) {
                replicate.add(editingChain, active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
            } else if (replication) {
                replicate.add(active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
            } else {
                if (editReplicate && editAdd) {
                    editReplicateChain.add(editingChain, active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
                } else if (editIntCase == 2 && editAdd) {
                    intCase1chain.add(editingChain, active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
                } else if (editIntCase == 3 && editAdd) {
                    intCase2chain.add(editingChain, active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
                } else if (editAdd) {
                    chain.get(editing).add(editingTerm, active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
                } else if (editReplicate) {
                    editReplicateChain.remove(editingChain);
                    editReplicateChain.add(editingChain, active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
                } else if (editIntCase == 2) {
                    intCase1chain.remove(editingChain);
                    intCase1chain.add(editingChain, active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
                } else if (editIntCase == 3) {
                    intCase2chain.remove(editingChain);
                    intCase2chain.add(editingChain, active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
                } else if (editTerm) {
                    chain.get(editing).remove(editingTerm);
                    chain.get(editing).add(editingTerm, active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
                } else {
                    chain.get(editing).add(active.createChainLink("S", pairBindSplit.getText(), pairASplit.getText(), pairBSplit.getText()));
                }
            }
            active.showInformation();

            if (editAdd && replication && (editIntCase == 2)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(replicate) + editingChain + 1, String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
            } else if (editAdd && replication && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + sizeOfChain(replicate) + editingChain + 2, String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
            } else if (editAdd && replication) {
                calcRep.get(editing).get(editingTerm).add(replicateEdit, String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
                replicateEdit++;
            } else if (replication) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(calcRep.get(editing).get(calcRep.get(editing).size() - 1).size() - 1, String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
            } else if (intCase1) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
            } else if (intCase2) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
            } else if (editReplicate && editAdd && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + sizeOfChain(intCase1chain) + editingChain + 2, String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
            } else if (editReplicate && editAdd) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + editingChain + 1, String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
            } else if (editAdd) {
                if (editIntCase == 2) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
                } else if (editIntCase == 3) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + intCase1chain.size() + 2, String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
                } else {
                    calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()))));
                }
            } else if (editReplicate) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
            } else if (editIntCase == 2) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
            } else if (editIntCase == 3) {
                calcRep.get(editing).get(editingTerm).remove(sizeOfChain(intCase1chain) + editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + editingChain + 1, String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()));
            } else if (editTerm) {
                calcRep.get(editing).remove(editingTerm);
                calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()))));
            } else {
                calcRep.get(editing).add(new ArrayList<>(singletonList(String.format("let (%s,%s)=%s in ", pairASplit.getText(), pairBSplit.getText(), pairBindSplit.getText()))));
            }

            updateRep();
            splitPair.setVisible(false);
            if (intCase1) {
                intCase1Create.setVisible(true);
            } else if (intCase2) {
                intCase2Create.setVisible(true);
            } else if (replication) {
                replicateCreate.setVisible(true);
            } else if (editReplicate) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(editReplicateChain);
            } else if (editIntCase == 2) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase1chain);
            } else if (editIntCase == 3) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase2chain);
            } else if (editAdd || editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editAdd = false;
                editTerm = false;
                nextProcess.setVisible(true);
                previousProcess.setVisible(true);
                updateEdit();
            } else {
                addTerm.setVisible(true);
            }
            pairBindSplit.setText("");
            pairASplit.setText("");
            pairBSplit.setText("");
        });
        addMatch.addActionListener(e -> {
            if (intCase1) {
                intCase1chain.add(active.createChainLink("M", matchA.getText(), matchB.getText()));
            } else if (intCase2) {
                intCase2chain.add(active.createChainLink("M", matchA.getText(), matchB.getText()));
            } else if (editAdd && replication && (editIntCase > 1)) {
                replicate.add(active.createChainLink("M", matchA.getText(), matchB.getText()));
            } else if (editAdd && replication) {
                replicate.add(editingChain, active.createChainLink("M", matchA.getText(), matchB.getText()));
            } else if (replication) {
                replicate.add(active.createChainLink("M", matchA.getText(), matchB.getText()));
            } else {
                if (editReplicate && editAdd) {
                    editReplicateChain.add(editingChain, active.createChainLink("M", matchA.getText(), matchB.getText()));
                } else if (editIntCase == 2 && editAdd) {
                    intCase1chain.add(editingChain, active.createChainLink("M", matchA.getText(), matchB.getText()));
                } else if (editIntCase == 3 && editAdd) {
                    intCase2chain.add(editingChain, active.createChainLink("M", matchA.getText(), matchB.getText()));
                } else if (editAdd) {
                    chain.get(editing).add(editingTerm, active.createChainLink("M", matchA.getText(), matchB.getText()));
                } else if (editReplicate) {
                    editReplicateChain.remove(editingChain);
                    editReplicateChain.add(editingChain, active.createChainLink("M", matchA.getText(), matchB.getText()));
                } else if (editIntCase == 2) {
                    intCase1chain.remove(editingChain);
                    intCase1chain.add(editingChain, active.createChainLink("M", matchA.getText(), matchB.getText()));
                } else if (editIntCase == 3) {
                    intCase2chain.remove(editingChain);
                    intCase2chain.add(editingChain, active.createChainLink("M", matchA.getText(), matchB.getText()));
                } else if (editTerm) {
                    chain.get(editing).remove(editingTerm);
                    chain.get(editing).add(editingTerm, active.createChainLink("M", matchA.getText(), matchB.getText()));
                } else {
                    chain.get(editing).add(active.createChainLink("M", matchA.getText(), matchB.getText()));
                }
            }
            active.showInformation();

            if (editAdd && replication && (editIntCase == 2)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(replicate) + editingChain + 1, String.format("[%s is %s].", matchA.getText(), matchB.getText()));
            } else if (editAdd && replication && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + sizeOfChain(replicate) + editingChain + 2, String.format("[%s is %s].", matchA.getText(), matchB.getText()));
            } else if (editAdd && replication) {
                calcRep.get(editing).get(editingTerm).add(replicateEdit, String.format("[%s is %s].", matchA.getText(), matchB.getText()));
                replicateEdit++;
            } else if (replication) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(calcRep.get(editing).get(calcRep.get(editing).size() - 1).size() - 1, String.format("[%s is %s].", matchA.getText(), matchB.getText()));
            } else if (intCase1) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("[%s is %s].", matchA.getText(), matchB.getText()));
            } else if (intCase2) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("[%s is %s].", matchA.getText(), matchB.getText()));
            } else if (editReplicate && editAdd && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + sizeOfChain(intCase1chain) + editingChain + 2, String.format("[%s is %s].", matchA.getText(), matchB.getText()));
            } else if (editReplicate && editAdd) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + editingChain + 1, String.format("[%s is %s].", matchA.getText(), matchB.getText()));
            } else if (editAdd) {
                if (editIntCase == 2) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("[%s is %s].", matchA.getText(), matchB.getText()));
                } else if (editIntCase == 3) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + intCase1chain.size() + 2, String.format("[%s is %s].", matchA.getText(), matchB.getText()));
                } else {
                    calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("[%s is %s].", matchA.getText(), matchB.getText()))));
                }
            } else if (editReplicate) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("[%s is %s].", matchA.getText(), matchB.getText()));
            } else if (editIntCase == 2) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("[%s is %s].", matchA.getText(), matchB.getText()));
            } else if (editIntCase == 3) {
                calcRep.get(editing).get(editingTerm).remove(sizeOfChain(intCase1chain) + editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + editingChain + 1, String.format("[%s is %s].", matchA.getText(), matchB.getText()));
            } else if (editTerm) {
                calcRep.get(editing).remove(editingTerm);
                calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("[%s is %s].", matchA.getText(), matchB.getText()))));
            } else {
                calcRep.get(editing).add(new ArrayList<>(singletonList(String.format("[%s is %s].", matchA.getText(), matchB.getText()))));
            }

            updateRep();
            matchCreate.setVisible(false);
            if (intCase1) {
                intCase1Create.setVisible(true);
            } else if (intCase2) {
                intCase2Create.setVisible(true);
            } else if (replication) {
                replicateCreate.setVisible(true);
            } else if (editReplicate) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(editReplicateChain);
            } else if (editIntCase == 2) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase1chain);
            } else if (editIntCase == 3) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase2chain);
            } else if (editAdd || editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editAdd = false;
                editTerm = false;
                nextProcess.setVisible(true);
                previousProcess.setVisible(true);
                updateEdit();
            } else {
                addTerm.setVisible(true);
            }
            matchA.setText("");
            matchB.setText("");
        });

        addArithmetic.addActionListener(e -> {
            if (intCase1) {
                intCase1chain.add(active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
            } else if (intCase2) {
                intCase2chain.add(active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
            } else if (editAdd && replication && (editIntCase > 1)) {
                replicate.add(active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
            } else if (editAdd && replication) {
                replicate.add(editingChain, active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
            } else if (replication) {
                replicate.add(active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
            } else {
                if (editReplicate && editAdd) {
                    editReplicateChain.add(editingChain, active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
                } else if (editIntCase == 2 && editAdd) {
                    intCase1chain.add(editingChain, active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
                } else if (editIntCase == 3 && editAdd) {
                    intCase2chain.add(editingChain, active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
                } else if (editAdd) {
                    chain.get(editing).add(editingTerm, active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
                } else if (editReplicate) {
                    editReplicateChain.remove(editingChain);
                    editReplicateChain.add(editingChain, active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
                } else if (editIntCase == 2) {
                    intCase1chain.remove(editingChain);
                    intCase1chain.add(editingChain, active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
                } else if (editIntCase == 3) {
                    intCase2chain.remove(editingChain);
                    intCase2chain.add(editingChain, active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
                } else if (editTerm) {
                    chain.get(editing).remove(editingTerm);
                    chain.get(editing).add(editingTerm, active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
                } else {
                    chain.get(editing).add(active.createChainLink((String) comboBox4.getSelectedItem(), arithmeticA.getText(), arithmeticB.getText()));
                }
            }

            active.showInformation();

            if (editAdd && replication && (editIntCase == 2)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(replicate) + editingChain + 1, String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
            } else if (editAdd && replication && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + sizeOfChain(replicate) + editingChain + 2, String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
            } else if (editAdd && replication) {
                calcRep.get(editing).get(editingTerm).add(replicateEdit, String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
                replicateEdit++;
            } else if (replication) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(calcRep.get(editing).get(calcRep.get(editing).size() - 1).size() - 1, String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
            } else if (intCase1) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
            } else if (intCase2) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
            } else if (editReplicate && editAdd && (editIntCase == 3)) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + sizeOfChain(intCase1chain) + editingChain + 2, String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
            } else if (editReplicate && editAdd) {
                calcRep.get(editing).get(editingTerm).add(editingReplicate + editingChain + 1, String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
            } else if (editAdd) {
                if (editIntCase == 2) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
                } else if (editIntCase == 3) {
                    calcRep.get(editing).get(editingTerm).add(editingChain + intCase1chain.size() + 2, String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
                } else {
                    calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()))));
                }
            } else if (editReplicate) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
            } else if (editIntCase == 2) {
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(editingChain + 1, String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
            } else if (editIntCase == 3) {
                calcRep.get(editing).get(editingTerm).remove(sizeOfChain(intCase1chain) + editingChain + 1);
                calcRep.get(editing).get(editingTerm).add(sizeOfChain(intCase1chain) + editingChain + 1, String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()));
            } else if (editTerm) {
                calcRep.get(editing).remove(editingTerm);
                calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()))));
            } else {
                calcRep.get(editing).add(new ArrayList<>(singletonList(String.format("%s%s%s.", arithmeticA.getText(), comboBox4.getSelectedItem(), arithmeticB.getText()))));
            }

            updateRep();
            arithmeticCreate.setVisible(false);
            if (intCase1) {
                intCase1Create.setVisible(true);
            } else if (intCase2) {
                intCase2Create.setVisible(true);
            } else if (replication) {
                replicateCreate.setVisible(true);
            } else if (editReplicate) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(editReplicateChain);
            } else if (editIntCase == 2) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase1chain);
            } else if (editIntCase == 3) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase2chain);
            } else if (editAdd || editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editAdd = false;
                editTerm = false;
                nextProcess.setVisible(true);
                previousProcess.setVisible(true);
                updateEdit();
            } else {
                addTerm.setVisible(true);
            }
            arithmeticA.setText("");
            arithmeticB.setText("");
        });

        addReplicate.addActionListener(e -> {
            replication = true;
            replicateCreate.setVisible(false);
            creation.get(replicateSelection.getSelectedIndex()).setVisible(true);
            populate();
        });
        endReplicate.addActionListener(e -> {
            if (replicate.size() == 0) {
                if (editAdd) {
                    calcRep.get(editing).remove(editingTerm);
                } else {
                    calcRep.get(editing).remove(calcRep.get(editing).size() - 1);
                }
                updateRep();
            } else {
                if (intCase1) {
                    intCase1chain.add(active.createChainLink("R", replicate));
                } else if (intCase2) {
                    intCase2chain.add(active.createChainLink("R", replicate));
                } else if (editAdd && replication && (editIntCase > 1)) {
                    replicate.add(active.createChainLink("R", replicate));
                } else if (editAdd && replication) {
                    replicate.add(editingChain, active.createChainLink("R", replicate));
                } else if (editReplicate && editAdd) {
                    editReplicateChain.add(editingChain, active.createChainLink("R", replicate));
                } else if (editIntCase == 2 && editAdd) {
                    intCase1chain.add(editingChain, active.createChainLink("R", replicate));
                } else if (editIntCase == 3 && editAdd) {
                    intCase2chain.add(editingChain, active.createChainLink("R", replicate));
                } else if (editAdd) {
                    chain.get(editing).add(editingTerm, active.createChainLink("R", replicate));
                } else if (editReplicate) {
                    editReplicateChain.remove(editingChain);
                    editReplicateChain.add(editingChain, active.createChainLink("R", replicate));
                } else if (editIntCase == 2) {
                    intCase1chain.remove(editingChain);
                    intCase1chain.add(editingChain, active.createChainLink("R", replicate));
                } else if (editIntCase == 3) {
                    intCase2chain.remove(editingChain);
                    intCase2chain.add(editingChain, active.createChainLink("R", replicate));
                } else if (editTerm) {
                    chain.get(editing).remove(editingTerm);
                    chain.get(editing).add(editingTerm, active.createChainLink("R", replicate));
                } else {
                    chain.get(editing).add(active.createChainLink("R", replicate));
                }
            }


            active.showInformation();
            replicate = new ArrayList<>();
            replicateCreate.setVisible(false);
            if (editAdd && (editIntCase == 2)) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editAdd = false;
                updateEditChain(intCase1chain);
            } else if (editAdd && (editIntCase == 3)) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editAdd = false;
                updateEditChain(intCase2chain);
            } else if (editAdd) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editAdd = false;
                updateEdit();
            } else {
                addTerm.setVisible(true);
            }
        });

        addIntegerCase.addActionListener(e -> {
            integerCaseCreate.setVisible(false);
            intCase1Create.setVisible(true);

            if (editAdd) {
                calcRep.get(editing).add(editingTerm, new ArrayList<>(singletonList(String.format("case %s of 0 : ", caseA.getText()))));
            } else if (editIntCase == 1) {
                calcRep.get(editing).get(editingTerm).remove(0);
                calcRep.get(editing).get(editingTerm).remove(intCase1chain.size());
                calcRep.get(editing).get(editingTerm).add(0, String.format("case %s of 0 : ", caseA.getText()));
                calcRep.get(editing).get(editingTerm).add(intCase1chain.size() + 1, String.format("0 suc(%s) : ", succCase.getText()));

                chain.get(editing).remove(editingTerm);
                chain.get(editing).add(editingTerm, active.createChainLink("N", caseA.getText(), intCase1chain, succCase.getText(), intCase2chain));

                intCase1Create.setVisible(false);
                caseA.setText("");
                succCase.setText("");
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEditChain(intCase1chain);
                editIntCase = 2;
            } else if (replication) {
                calcRep.get(editing).get(editingTerm).add(calcRep.get(editing).get(editingTerm).size() - 1, String.format("case %s of 0 : ", caseA.getText()));
            } else {
                calcRep.get(editing).add(new ArrayList<>(singletonList(String.format("case %s of 0 : ", caseA.getText()))));
            }

            updateRep();
        });
        intCase1Add.addActionListener(e -> {
            intCase1 = true;
            intCase1Create.setVisible(false);
            creation.get(intCase1Select.getSelectedIndex()).setVisible(true);
            populate();
        });
        intCase2Add.addActionListener(e -> {
            intCase2 = true;
            intCase2Create.setVisible(false);
            creation.get(intCase2Select.getSelectedIndex()).setVisible(true);
            populate();
        });
        intCase1End.addActionListener(e -> {
            intCase1 = false;
            intCase1Create.setVisible(false);
            intCase2Create.setVisible(true);

            if (replication) {
                calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(calcRep.get(editing).get(calcRep.get(editing).size() - 1).size() - 1, String.format("0 suc(%s) : ", succCase.getText()));
            } else {
                if (editAdd) {
                    calcRep.get(editing).get(editingTerm).add(String.format("0 suc(%s) : ", succCase.getText()));
                } else {
                    calcRep.get(editing).get(calcRep.get(editing).size() - 1).add(String.format("0 suc(%s) : ", succCase.getText()));
                }
            }

            updateRep();
        });
        intCase2End.addActionListener(e -> {
            intCase2 = false;
            intCase2Create.setVisible(false);
            if (replication) {
                replicate.add(active.createChainLink("N", caseA.getText(), intCase1chain, succCase.getText(), intCase2chain));
            } else if (editAdd) {
                chain.get(editing).add(editingTerm, active.createChainLink("N", caseA.getText(), intCase1chain, succCase.getText(), intCase2chain));
            } else {
                chain.get(editing).add(active.createChainLink("N", caseA.getText(), intCase1chain, succCase.getText(), intCase2chain));
            }
            active.showInformation();
            intCase1chain = new ArrayList<>();
            intCase2chain = new ArrayList<>();
            caseA.setText("");
            succCase.setText("");
            if (replication) {
                replicateCreate.setVisible(true);
            } else if (editAdd) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                updateEdit();
            } else {
                addTerm.setVisible(true);
            }
        });


        backButton1.addActionListener(e -> {
            outputCreate.setVisible(false);
            if (editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editTerm = false;
            } else {
                addTerm.setVisible(true);
            }
            channelsOut.setText("");
            variableOut.setText("");
        });
        backButton2.addActionListener(e -> {
            inputCreate.setVisible(false);
            if (editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editTerm = false;
            } else {
                addTerm.setVisible(true);
            }
            channelsIn.setText("");
            inputVariableBind.setText("");
        });
        backButton3.addActionListener(e -> {
            encryptCreate.setVisible(false);
            if (editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editTerm = false;
            } else {
                addTerm.setVisible(true);
            }
            variableToEncrypt.setText("");
            keyToEncryptWith.setText("");
        });
        backButton4.addActionListener(e -> {
            decryptCreate.setVisible(false);
            if (editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editTerm = false;
            } else {
                addTerm.setVisible(true);
            }
            bindToDecrypt.setText("");
            decryptKey.setText("");
            decryptVariable.setText("");
        });
        backButton5.addActionListener(e -> {
            createPair.setVisible(false);
            if (editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editTerm = false;
            } else {
                addTerm.setVisible(true);
            }
            pairACreate.setText("");
            pairBCreate.setText("");
            pairBindCreate.setText("");
        });
        backButton6.addActionListener(e -> {
            splitPair.setVisible(false);
            if (editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editTerm = false;
            } else {
                addTerm.setVisible(true);
            }
            pairASplit.setText("");
            pairBSplit.setText("");
            pairBindSplit.setText("");
        });
        backButton7.addActionListener(e -> {
            restrictCreate.setVisible(false);
            if (editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editTerm = false;
            } else {
                addTerm.setVisible(true);
            }
        });

        backButton9.addActionListener(e -> {
            matchCreate.setVisible(false);
            if (editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editTerm = false;
            } else {
                addTerm.setVisible(true);
            }
            matchA.setText("");
            matchB.setText("");
        });
        backButton10.addActionListener(e -> {
            integerCaseCreate.setVisible(false);
            if (editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editTerm = false;
            } else {
                addTerm.setVisible(true);
            }
        });
        backButton11.addActionListener(e -> {
            arithmeticCreate.setVisible(false);
            if (editTerm) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editTerm = false;
            } else {
                addTerm.setVisible(true);
            }
            arithmeticA.setText("");
            arithmeticB.setText("");
        });

        // EDIT CHAIN

        editButton.addActionListener(e -> {
            addOrEdit.setVisible(false);
            editPanel.setVisible(true);
            editSelection.setVisible(true);
            editingActive = true;
            editingTerm = 1;
            updateEdit();
        });
        endEditButton.addActionListener(e -> {
            if (editReplicate) {
                editingChain = 0;
                editReplicate = false;
                if (editIntCase == 2) {
                    updateEditChain(intCase1chain);
                } else if (editIntCase == 3) {
                    updateEditChain(intCase2chain);
                } else {
                    updateEdit();
                }
            } else if (editIntCase == 2) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editIntCase = 3;
                editingChain = 0;
                updateEditChain(intCase2chain);
            } else if (editIntCase == 3) {
                editPanel.setVisible(true);
                editSelection.setVisible(true);
                editIntCase = 0;
                editingChain = 0;
                editingTerm = 1;
                updateEdit();
            } else {
                addOrEdit.setVisible(true);
                editPanel.setVisible(false);
                editSelection.setVisible(false);
                editingActive = false;
            }
        });

        nextTermButton.addActionListener(e -> {
            if (editReplicate) {
                editingChain++;
                updateEditChain(editReplicateChain);
            } else if (editIntCase == 2) {
                editingChain++;
                updateEditChain(intCase1chain);
            } else if (editIntCase == 3) {
                editingChain++;
                updateEditChain(intCase2chain);
            } else {
                editingTerm++;
                updateEdit();
            }
        });

        prevTermButton.addActionListener(e -> {
            if (editReplicate) {
                editingChain--;
                updateEditChain(editReplicateChain);
            } else if (editIntCase == 2) {
                editingChain--;
                updateEditChain(intCase1chain);
            } else if (editIntCase == 3) {
                editingChain--;
                updateEditChain(intCase2chain);
            } else {
                editingTerm--;
                updateEdit();
            }
        });

        addTermButton.addActionListener(e -> {
            addTerm.setVisible(true);
            editAdd = true;
            editSelection.setVisible(false);
            editPanel.setVisible(false);
        });

        delTermButton.addActionListener(e -> {
            if (editReplicate) {
                editReplicateChain.remove(editingChain);
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                if (editingChain >= editReplicateChain.size()) {
                    editingChain--;
                }
                updateEditChain(editReplicateChain);
                updateRep();
            } else if (editIntCase == 2) {
                intCase1chain.remove(editingChain);
                calcRep.get(editing).get(editingTerm).remove(editingChain + 1);
                if (editingChain >= intCase1chain.size()) {
                    editingChain--;
                }
                updateEditChain(intCase1chain);
                updateRep();
            } else if (editIntCase == 3) {
                intCase2chain.remove(editingChain);
                calcRep.get(editing).get(editingTerm).remove(intCase1chain.size() + editingChain + 2);
                if (editingChain >= intCase2chain.size()) {
                    editingChain--;
                }
                updateEditChain(intCase2chain);
                updateRep();
            } else {
                chain.get(editing).remove(editingTerm);
                calcRep.get(editing).remove(editingTerm);
                if (editingTerm >= chain.get(editing).size()) {
                    editingTerm--;
                }
                updateEdit();
                updateRep();
            }
        });

        editTermButton.addActionListener(e -> {
            if (editReplicate) {
                editTerm(editReplicateChain.get(editingChain));
            } else if (editIntCase == 2) {
                editTerm(intCase1chain.get(editingChain));
            } else if (editIntCase == 3) {
                editTerm(intCase2chain.get(editingChain));
            } else {
                editTerm(chain.get(editing).get(editingTerm));
            }
        });

        saveButton.addActionListener(e -> {
            int returnVal = fc.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String filename = file.toString();
                    if (!filename .endsWith(".spi")) {
                        filename += ".spi";
                    }
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
                    oos.writeObject(numberProcess);
                    oos.writeObject(chain);
                    oos.writeObject(calcRep);
                    oos.writeObject(active.CYOprocesses);
                    oos.writeObject(active.processes);
                    oos.writeObject(active.variables);
                    oos.writeObject(active.channelsCYO);
                    oos.close();
                    JOptionPane.showMessageDialog(this, "Process has been saved.");
                } catch (IOException e1) {
                }
            }
        });

        loadButton.addActionListener(e -> {
            int returnVal = fc.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file));
                    numberProcess = (int) oos.readObject();
                    chain = (ArrayList<ArrayList<ChainElement>>) oos.readObject();
                    calcRep = (ArrayList<ArrayList<ArrayList<String>>>) oos.readObject();
                    active.CYOprocesses = (ArrayList<Process>) oos.readObject();
                    active.processes = (ArrayList<String>) oos.readObject();
                    active.variables = (ArrayList<ArrayList<String>>) oos.readObject();
                    active.channelsCYO = (Map<String, Term>) oos.readObject();
                    oos.close();
                    mainMenu.setVisible(false);
                    creatingCalculus.setVisible(true);
                    populateChoices();
                    creation();
                    active.showInformation();
                    updateRep();
                    JOptionPane.showMessageDialog(this, "Process has been loaded.");;
                } catch (IOException e1) {
                } catch (ClassNotFoundException e1) {
                }
            }
        });

        newButton.addActionListener(e -> {
            mainMenu.setVisible(false);
            ProcessNumber.setVisible(true);
        });

        resetButton.addActionListener(e -> {
            int reset = JOptionPane.showConfirmDialog(null, "Are you sure?", "Reset", JOptionPane.YES_NO_OPTION);
            if(reset == JOptionPane.YES_OPTION) {
                chain = new ArrayList<ArrayList<ChainElement>>();
                calcRep = new ArrayList<ArrayList<ArrayList<String>>>();
                created = 0;
                processNaming.setText("Please name process " + (created + 1));
                active.CYOprocesses.clear();
                active.processes.clear();
                active.variables.clear();
                active.channelsCYO.clear();
                active.showInformation();
                updateRep();

                mainMenu.setVisible(true);
                creatingCalculus.setVisible(false);
            }
        });

    }

    private void editTerm(ChainElement toEdit) {
        nextProcess.setVisible(false);
        previousProcess.setVisible(false);
        editTerm = true;
        switch (toEdit.getChain().get(0).getString()) {
            case "O":
                outputCreate.setVisible(true);
                editSelection.setVisible(false);
                editPanel.setVisible(false);
                break;
            case "I":
                inputCreate.setVisible(true);
                editSelection.setVisible(false);
                editPanel.setVisible(false);
                break;
            case "E":
                encryptCreate.setVisible(true);
                editSelection.setVisible(false);
                editPanel.setVisible(false);
                break;
            case "D":
                decryptCreate.setVisible(true);
                editSelection.setVisible(false);
                editPanel.setVisible(false);
                break;
            case "P":
                createPair.setVisible(true);
                editSelection.setVisible(false);
                editPanel.setVisible(false);
                break;
            case "S":
                splitPair.setVisible(true);
                editSelection.setVisible(false);
                editPanel.setVisible(false);
                break;
            case "R":
                editReplicateChain = toEdit.getChain().get(1).getChain();
                editReplicate = true;
                if (editIntCase > 1) {
                    editingReplicate += editingChain;
                }
                editingChain = 0;
                updateEditChain(editReplicateChain);
                break;
            case "M":
                matchCreate.setVisible(true);
                editSelection.setVisible(false);
                editPanel.setVisible(false);
                break;
            case "N":
                intCase1chain = toEdit.getChain().get(2).getChain();
                intCase2chain = toEdit.getChain().get(4).getChain();
                integerCaseCreate.setVisible(true);
                editSelection.setVisible(false);
                editPanel.setVisible(false);
                editIntCase = 1;
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                arithmeticCreate.setVisible(true);
                editSelection.setVisible(false);
                editPanel.setVisible(false);
                break;
        }
    }

    private void updateEdit() {
        if (editingTerm <= 1) {
            prevTerm.setText("");
            prevTermButton.setEnabled(false);
        } else if (chain.get(editing).get(editingTerm - 1).getChain().get(0).getString().equals("R")) {
            prevTerm.setText("!(-).");
            prevTermButton.setEnabled(true);
        } else if (chain.get(editing).get(editingTerm - 1).getChain().get(0).getString().equals("N")) {
            prevTerm.setText("case " + chain.get(editing).get(editingTerm - 1).getChain().get(1).getString() + " : (-) suc(" + chain.get(editing).get(editingTerm - 1).getChain().get(3).getString() + ") : (-)");
            prevTermButton.setEnabled(true);
        } else {
            prevTerm.setText(active.chainPieceToString(chain.get(editing).get(editingTerm - 1)));
            prevTermButton.setEnabled(true);
        }
        if (chain.get(editing).size() == 1) {
            currentTerm.setText("0");
        } else {
            if (chain.get(editing).get(editingTerm).getChain().get(0).getString().equals("R")) {
                currentTerm.setText("!(-).");
            } else if (chain.get(editing).get(editingTerm).getChain().get(0).getString().equals("N")) {
                currentTerm.setText("case " + chain.get(editing).get(editingTerm).getChain().get(1).getString() + " : (-) suc(" + chain.get(editing).get(editingTerm).getChain().get(3).getString() + ") : (-)");
            } else {
                currentTerm.setText(active.chainPieceToString(chain.get(editing).get(editingTerm)));
            }
        }
        if (editingTerm + 1 >= chain.get(editing).size()) {
            nextTerm.setText("");
            nextTermButton.setEnabled(false);
        } else if (chain.get(editing).get(editingTerm + 1).getChain().get(0).getString().equals("R")) {
            nextTerm.setText("!(-).");
            nextTermButton.setEnabled(true);
        } else if (chain.get(editing).get(editingTerm + 1).getChain().get(0).getString().equals("N")) {
            nextTerm.setText("case " + chain.get(editing).get(editingTerm + 1).getChain().get(1).getString() + " : (-) suc(" + chain.get(editing).get(editingTerm + 1).getChain().get(3).getString() + ") : (-)");
            nextTermButton.setEnabled(true);
        } else {
            nextTerm.setText(active.chainPieceToString(chain.get(editing).get(editingTerm + 1)));
            nextTermButton.setEnabled(true);
        }
    }

    private void updateEditChain(ArrayList<ChainElement> chainToEdit) {
        if (editingChain < 0) {
            editingChain = 0;
        }
        if (editingChain == 0) {
            prevTerm.setText("");
            prevTermButton.setEnabled(false);
        } else if (chainToEdit.get(editingChain - 1).getChain().get(0).getString().equals("R")) {
            prevTerm.setText("!(-).");
            prevTermButton.setEnabled(true);
        } else if (chainToEdit.get(editingChain - 1).getChain().get(0).getString().equals("N")) {
            prevTerm.setText("case " + chainToEdit.get(editingChain - 1).getChain().get(1).getString() + " : (-) suc(" + chainToEdit.get(editingChain - 1).getChain().get(3).getString() + ") : (-)");
            prevTermButton.setEnabled(true);
        } else {
            prevTerm.setText(active.chainPieceToString(chainToEdit.get(editingChain - 1)));
            prevTermButton.setEnabled(true);
        }
        if (chainToEdit.size() == 0 || editingChain == chainToEdit.size()) {
            currentTerm.setText("0");
        } else {
            if (chainToEdit.get(editingChain).getChain().get(0).getString().equals("R")) {
                currentTerm.setText("!(-).");
            } else if (chainToEdit.get(editingChain).getChain().get(0).getString().equals("N")) {
                currentTerm.setText("case " + chainToEdit.get(editingChain).getChain().get(1).getString() + " : (-) suc(" + chainToEdit.get(editingChain).getChain().get(3).getString() + ") : (-)");
            } else {
                currentTerm.setText(active.chainPieceToString(chainToEdit.get(editingChain)));
            }
        }
        if (editingChain + 1 == chainToEdit.size()) {
            nextTerm.setText("0");
            nextTermButton.setEnabled(true);
        } else if (editingChain == chainToEdit.size()) {
            nextTerm.setText("");
            nextTermButton.setEnabled(false);
        } else if (chainToEdit.get(editingChain + 1).getChain().get(0).getString().equals("R")) {
            nextTerm.setText("!(-).");
            nextTermButton.setEnabled(true);
        } else if (chainToEdit.get(editingChain + 1).getChain().get(0).getString().equals("N")) {
            nextTerm.setText("case " + chainToEdit.get(editingChain + 1).getChain().get(1).getString() + " : (-) suc(" + chainToEdit.get(editingChain + 1).getChain().get(3).getString() + ") : (-)");
            nextTermButton.setEnabled(true);
        } else {
            nextTerm.setText(active.chainPieceToString(chainToEdit.get(editingChain + 1)));
            nextTermButton.setEnabled(true);
        }
    }


    private void updateRep() {
        calculusRep.setText("");
        for (ArrayList<ArrayList<String>> calcRepProcess : calcRep) {
            for (ArrayList<String> calcRepPiece : calcRepProcess) {
                for (String str : calcRepPiece) {
                    calculusRep.append(str);
                }
            }
            calculusRep.append("0\n");
        }
    }

    private String key(String input) {
        if (active.CYOprocesses.get(editing).getKey(input) != null) {
            return active.CYOprocesses.get(editing).getKey(input);
        } else {
            return input;
        }
    }

    private void creation() {
        currentProcess.setText("Currently editing " + active.CYOprocesses.get(editing).processName);
        previousProcess.setText("Edit " + active.CYOprocesses.get((editing - 1 + numberProcess) % numberProcess).processName);
        nextProcess.setText("Edit " + active.CYOprocesses.get((editing + 1) % numberProcess).processName);
    }

    private int sizeOfChain(ArrayList<ChainElement> chain) {
        int size = 0;
        for (ChainElement process : chain) {
            if (process.getChain().get(0).getString().equals("R")) {
                size += 2;
                size += sizeOfChain(process.getChain().get(1).getChain());
            } else if (process.getChain().get(0).getString().equals("N")) {
                size += 3;
                size += sizeOfChain(process.getChain().get(3).getChain());
                size += sizeOfChain(process.getChain().get(5).getChain());
            } else {
                size++;
            }
        }
        return size;
    }

    public void updateInfo(String s) {
        Information.append(s);
    }

    public void clearText() {
        Information.setText("");
    }

    public void populateChoices() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        DefaultComboBoxModel model2 = new DefaultComboBoxModel();
        DefaultComboBoxModel model3 = new DefaultComboBoxModel();
        for (int i = 0; i < numberProcess; i++) {
            model.addElement(active.CYOprocesses.get(i).processName);
            model2.addElement(active.CYOprocesses.get(i).processName);
            model3.addElement(active.CYOprocesses.get(i).processName);
        }
        comboBox1.setModel(model);
        comboBox2.setModel(model2);
        comboBox3.setModel(model3);
    }

    public void populateChannels() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (Map.Entry<String, Term> channel : active.channels.entrySet()) {
            model.addElement(channel.getValue().returnValue());
        }
    }

    public void populateVariables() {
        ArrayList<DefaultComboBoxModel> model = new ArrayList<>();
        for (Process output : active.CYOprocesses) {
            DefaultComboBoxModel temp = new DefaultComboBoxModel();
            for (Map.Entry<String, Term> entry : output.terms.entrySet()) {
                temp.addElement(entry.getKey());
            }
            model.add(temp);
        }
    }

    public void populate() {
        populateVariables();
        populateChannels();
        populateChoices();
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
        createWindow.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 1, new Insets(5, 5, 5, 5), -1, -1));
        createWindow.setBackground(new Color(-4407875));
        createWindow.setForeground(new Color(-4407875));
        createWindow.setMinimumSize(new Dimension(1, 1));
        createWindow.setPreferredSize(new Dimension(600, 520));
        createWindow.setRequestFocusEnabled(true);
        createWindow.setVisible(true);
        calculusShow = new JPanel();
        calculusShow.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), -1, -1));
        calculusShow.setBackground(new Color(-4407875));
        createWindow.add(calculusShow, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 350), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        calculusShow.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, 1, 1, null, new Dimension(-1, 250), null, 0, false));
        Information = new JTextArea();
        Information.setBackground(new Color(-3289651));
        Information.setEditable(false);
        Information.setForeground(new Color(-15197922));
        scrollPane1.setViewportView(Information);
        final JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setVisible(true);
        calculusShow.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, 1, 1, null, new Dimension(-1, 100), null, 0, false));
        calculusRep = new JTextArea();
        calculusRep.setBackground(new Color(-3289651));
        calculusRep.setEditable(false);
        calculusRep.setForeground(new Color(-15197922));
        calculusRep.setVisible(true);
        scrollPane2.setViewportView(calculusRep);
        ProcessNumber = new JPanel();
        ProcessNumber.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(5, 5, 5, 5), -1, -1));
        ProcessNumber.setBackground(new Color(-4407875));
        ProcessNumber.setVisible(false);
        createWindow.add(ProcessNumber, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
        createWindow.add(ProcessNames, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        processNaming = new JLabel();
        processNaming.setText("Please name process 1");
        ProcessNames.add(processNaming, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameInput = new JTextField();
        ProcessNames.add(nameInput, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        OKButton1 = new JButton();
        OKButton1.setText("OK");
        ProcessNames.add(OKButton1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        creatingCalculus = new JPanel();
        creatingCalculus.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(18, 1, new Insets(5, 5, 5, 5), -1, -1));
        creatingCalculus.setBackground(new Color(-4407875));
        creatingCalculus.setEnabled(true);
        creatingCalculus.setForeground(new Color(-4407875));
        creatingCalculus.setVisible(false);
        createWindow.add(creatingCalculus, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addOrEdit = new JPanel();
        addOrEdit.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        addOrEdit.setBackground(new Color(-4407875));
        addOrEdit.setForeground(new Color(-4407875));
        addOrEdit.setVisible(true);
        creatingCalculus.add(addOrEdit, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addVariableButton = new JButton();
        addVariableButton.setText("Add Variable");
        addOrEdit.add(addVariableButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addChannelButton = new JButton();
        addChannelButton.setText("Add Channel");
        addOrEdit.add(addChannelButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addKeyButton = new JButton();
        addKeyButton.setText("Add Key");
        addOrEdit.add(addKeyButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-4407875));
        addOrEdit.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setEnabled(true);
        editButton.setText("Edit");
        panel1.add(editButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setText("Add");
        panel1.add(addButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        runButton = new JButton();
        runButton.setText("Run");
        panel1.add(runButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setText("Save");
        panel1.add(saveButton, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resetButton = new JButton();
        resetButton.setText("Reset");
        panel1.add(resetButton, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-4407875));
        panel2.setForeground(new Color(-4407875));
        panel2.setVisible(true);
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
        addTerm = new JPanel();
        addTerm.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        addTerm.setBackground(new Color(-4407875));
        addTerm.setForeground(new Color(-4407875));
        addTerm.setVisible(false);
        creatingCalculus.add(addTerm, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        createChain = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Output");
        defaultComboBoxModel1.addElement("Input");
        defaultComboBoxModel1.addElement("Encrypt");
        defaultComboBoxModel1.addElement("Decrypt");
        defaultComboBoxModel1.addElement("Create Pair");
        defaultComboBoxModel1.addElement("Split Pair");
        defaultComboBoxModel1.addElement("Replicate");
        defaultComboBoxModel1.addElement("Match");
        defaultComboBoxModel1.addElement("Integer Case");
        defaultComboBoxModel1.addElement("Arithmetic Operation");
        createChain.setModel(defaultComboBoxModel1);
        addTerm.add(createChain, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createButton = new JButton();
        createButton.setText("Create");
        addTerm.add(createButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setText("Back");
        addTerm.add(backButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputCreate = new JPanel();
        outputCreate.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        outputCreate.setBackground(new Color(-4407875));
        outputCreate.setEnabled(true);
        outputCreate.setForeground(new Color(-4407875));
        outputCreate.setVisible(false);
        creatingCalculus.add(outputCreate, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addOutput = new JButton();
        addOutput.setText("Add");
        addOutput.setVisible(true);
        outputCreate.add(addOutput, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        variableOut = new JTextField();
        variableOut.setToolTipText("The variable the channel will output");
        variableOut.setVisible(true);
        outputCreate.add(variableOut, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        channelsOut = new JTextField();
        channelsOut.setToolTipText("The channel which the process will output on");
        channelsOut.setVisible(true);
        outputCreate.add(channelsOut, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        backButton1 = new JButton();
        backButton1.setMargin(new Insets(2, 8, 2, 8));
        backButton1.setText("Back");
        backButton1.setVisible(true);
        outputCreate.add(backButton1, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputCreate = new JPanel();
        inputCreate.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        inputCreate.setBackground(new Color(-4407875));
        inputCreate.setEnabled(true);
        inputCreate.setForeground(new Color(-4407875));
        inputCreate.setVisible(false);
        creatingCalculus.add(inputCreate, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addInput = new JButton();
        addInput.setText("Add");
        addInput.setVisible(true);
        inputCreate.add(addInput, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputVariableBind = new JTextField();
        inputVariableBind.setToolTipText("The binding the process will bind the input to");
        inputVariableBind.setVisible(true);
        inputCreate.add(inputVariableBind, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        channelsIn = new JTextField();
        channelsIn.setToolTipText("The channel the process will input from");
        channelsIn.setVisible(true);
        inputCreate.add(channelsIn, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        backButton2 = new JButton();
        backButton2.setMargin(new Insets(2, 8, 2, 8));
        backButton2.setText("Back");
        backButton2.setVisible(true);
        inputCreate.add(backButton2, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        encryptCreate = new JPanel();
        encryptCreate.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        encryptCreate.setBackground(new Color(-4407875));
        encryptCreate.setEnabled(true);
        encryptCreate.setForeground(new Color(-4407875));
        encryptCreate.setVisible(false);
        creatingCalculus.add(encryptCreate, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addEncrypt = new JButton();
        addEncrypt.setText("Add");
        addEncrypt.setVisible(true);
        encryptCreate.add(addEncrypt, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keyToEncryptWith = new JTextField();
        keyToEncryptWith.setToolTipText("The key to encrypt the variable with. Specify a process name, or a variable binding.");
        keyToEncryptWith.setVisible(true);
        encryptCreate.add(keyToEncryptWith, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        variableToEncrypt = new JTextField();
        variableToEncrypt.setToolTipText("The variable to be encrypted");
        variableToEncrypt.setVisible(true);
        encryptCreate.add(variableToEncrypt, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        backButton3 = new JButton();
        backButton3.setMargin(new Insets(2, 8, 2, 8));
        backButton3.setText("Back");
        backButton3.setVisible(true);
        encryptCreate.add(backButton3, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        decryptCreate = new JPanel();
        decryptCreate.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        decryptCreate.setBackground(new Color(-4407875));
        decryptCreate.setEnabled(true);
        decryptCreate.setForeground(new Color(-4407875));
        decryptCreate.setVisible(false);
        creatingCalculus.add(decryptCreate, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addDecrypt = new JButton();
        addDecrypt.setText("Add");
        addDecrypt.setVisible(true);
        decryptCreate.add(addDecrypt, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        decryptKey = new JTextField();
        decryptKey.setToolTipText("The key to decrypt the variable with. Specify a process name, or a variable binding.");
        decryptKey.setVisible(true);
        decryptCreate.add(decryptKey, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        decryptVariable = new JTextField();
        decryptVariable.setToolTipText("The binding of the encrypted term");
        decryptVariable.setVisible(true);
        decryptCreate.add(decryptVariable, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        backButton4 = new JButton();
        backButton4.setMargin(new Insets(2, 8, 2, 8));
        backButton4.setText("Back");
        backButton4.setVisible(true);
        decryptCreate.add(backButton4, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bindToDecrypt = new JTextField();
        bindToDecrypt.setToolTipText("The binding for the decrypted term");
        bindToDecrypt.setVisible(true);
        decryptCreate.add(bindToDecrypt, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        createPair = new JPanel();
        createPair.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        createPair.setBackground(new Color(-4407875));
        createPair.setEnabled(true);
        createPair.setForeground(new Color(-4407875));
        createPair.setVisible(false);
        creatingCalculus.add(createPair, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addPairCreate = new JButton();
        addPairCreate.setText("Add");
        addPairCreate.setVisible(true);
        createPair.add(addPairCreate, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pairBCreate = new JTextField();
        pairBCreate.setToolTipText("The variable of the second term of the pair");
        pairBCreate.setVisible(true);
        createPair.add(pairBCreate, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        pairACreate = new JTextField();
        pairACreate.setToolTipText("The variable of the first term of the pair");
        pairACreate.setVisible(true);
        createPair.add(pairACreate, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        backButton5 = new JButton();
        backButton5.setMargin(new Insets(2, 8, 2, 8));
        backButton5.setText("Back");
        backButton5.setVisible(true);
        createPair.add(backButton5, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pairBindCreate = new JTextField();
        pairBindCreate.setToolTipText("The binding of the new pair");
        pairBindCreate.setVisible(true);
        createPair.add(pairBindCreate, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        splitPair = new JPanel();
        splitPair.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        splitPair.setBackground(new Color(-4407875));
        splitPair.setEnabled(true);
        splitPair.setForeground(new Color(-4407875));
        splitPair.setVisible(false);
        creatingCalculus.add(splitPair, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addPairSplit = new JButton();
        addPairSplit.setText("Add");
        addPairSplit.setVisible(true);
        splitPair.add(addPairSplit, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pairASplit = new JTextField();
        pairASplit.setToolTipText("The binding for the first term of the pair");
        pairASplit.setVisible(true);
        splitPair.add(pairASplit, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        pairBindSplit = new JTextField();
        pairBindSplit.setToolTipText("The binding of the pair");
        pairBindSplit.setVisible(true);
        splitPair.add(pairBindSplit, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        backButton6 = new JButton();
        backButton6.setMargin(new Insets(2, 8, 2, 8));
        backButton6.setText("Back");
        backButton6.setVisible(true);
        splitPair.add(backButton6, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pairBSplit = new JTextField();
        pairBSplit.setToolTipText("The binding for the second term of the pair");
        pairBSplit.setVisible(true);
        splitPair.add(pairBSplit, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        restrictCreate = new JPanel();
        restrictCreate.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        restrictCreate.setBackground(new Color(-4407875));
        restrictCreate.setEnabled(true);
        restrictCreate.setForeground(new Color(-4407875));
        restrictCreate.setVisible(false);
        creatingCalculus.add(restrictCreate, new com.intellij.uiDesigner.core.GridConstraints(11, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JButton button1 = new JButton();
        button1.setText("Add");
        button1.setVisible(false);
        restrictCreate.add(button1, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JTextField textField3 = new JTextField();
        textField3.setToolTipText("The binding of the first half of the pair");
        textField3.setVisible(false);
        restrictCreate.add(textField3, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JTextField textField4 = new JTextField();
        textField4.setToolTipText("The variable the pair is bound to");
        textField4.setVisible(false);
        restrictCreate.add(textField4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        backButton7 = new JButton();
        backButton7.setMargin(new Insets(2, 8, 2, 8));
        backButton7.setText("Back");
        backButton7.setVisible(false);
        restrictCreate.add(backButton7, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JTextField textField5 = new JTextField();
        textField5.setToolTipText("The binding of the second half of the pair");
        textField5.setVisible(false);
        restrictCreate.add(textField5, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        replicateCreate = new JPanel();
        replicateCreate.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        replicateCreate.setBackground(new Color(-4407875));
        replicateCreate.setEnabled(true);
        replicateCreate.setForeground(new Color(-4407875));
        replicateCreate.setVisible(false);
        creatingCalculus.add(replicateCreate, new com.intellij.uiDesigner.core.GridConstraints(12, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addReplicate = new JButton();
        addReplicate.setText("Add to Replicate");
        replicateCreate.add(addReplicate, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endReplicate = new JButton();
        endReplicate.setMargin(new Insets(2, 8, 2, 8));
        endReplicate.setText("End Replicate");
        replicateCreate.add(endReplicate, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        replicateSelection = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Output");
        defaultComboBoxModel2.addElement("Input");
        defaultComboBoxModel2.addElement("Encrypt");
        defaultComboBoxModel2.addElement("Decrypt");
        defaultComboBoxModel2.addElement("Create Pair");
        defaultComboBoxModel2.addElement("Split Pair");
        defaultComboBoxModel2.addElement("Restrict");
        defaultComboBoxModel2.addElement("Match");
        defaultComboBoxModel2.addElement("Integer Case");
        defaultComboBoxModel2.addElement("Arithmetic Operation");
        replicateSelection.setModel(defaultComboBoxModel2);
        replicateCreate.add(replicateSelection, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        matchCreate = new JPanel();
        matchCreate.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        matchCreate.setBackground(new Color(-4407875));
        matchCreate.setEnabled(true);
        matchCreate.setForeground(new Color(-4407875));
        matchCreate.setVisible(false);
        creatingCalculus.add(matchCreate, new com.intellij.uiDesigner.core.GridConstraints(13, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addMatch = new JButton();
        addMatch.setText("Add");
        addMatch.setVisible(true);
        matchCreate.add(addMatch, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        matchA = new JTextField();
        matchA.setToolTipText("The binding of the first term in the match");
        matchA.setVisible(true);
        matchCreate.add(matchA, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        backButton9 = new JButton();
        backButton9.setMargin(new Insets(2, 8, 2, 8));
        backButton9.setText("Back");
        backButton9.setVisible(true);
        matchCreate.add(backButton9, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        matchB = new JTextField();
        matchB.setToolTipText("The binding of the second term in the match");
        matchB.setVisible(true);
        matchCreate.add(matchB, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        integerCaseCreate = new JPanel();
        integerCaseCreate.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        integerCaseCreate.setBackground(new Color(-4407875));
        integerCaseCreate.setEnabled(true);
        integerCaseCreate.setForeground(new Color(-4407875));
        integerCaseCreate.setVisible(false);
        creatingCalculus.add(integerCaseCreate, new com.intellij.uiDesigner.core.GridConstraints(14, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addIntegerCase = new JButton();
        addIntegerCase.setLabel("Add");
        addIntegerCase.setText("Add");
        addIntegerCase.setVisible(true);
        integerCaseCreate.add(addIntegerCase, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        caseA = new JTextField();
        caseA.setToolTipText("The variable to check is 0");
        caseA.setVisible(true);
        integerCaseCreate.add(caseA, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        backButton10 = new JButton();
        backButton10.setMargin(new Insets(2, 8, 2, 8));
        backButton10.setText("Back");
        backButton10.setVisible(true);
        integerCaseCreate.add(backButton10, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        succCase = new JTextField();
        succCase.setToolTipText("The binding to use for the successor of the first variable");
        succCase.setVisible(true);
        integerCaseCreate.add(succCase, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        arithmeticCreate = new JPanel();
        arithmeticCreate.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        arithmeticCreate.setBackground(new Color(-4407875));
        arithmeticCreate.setEnabled(true);
        arithmeticCreate.setForeground(new Color(-4407875));
        arithmeticCreate.setVisible(false);
        creatingCalculus.add(arithmeticCreate, new com.intellij.uiDesigner.core.GridConstraints(17, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addArithmetic = new JButton();
        addArithmetic.setText("Add");
        addArithmetic.setVisible(true);
        arithmeticCreate.add(addArithmetic, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        arithmeticA = new JTextField();
        arithmeticA.setToolTipText("The variable to update with the result of the arithmetic");
        arithmeticA.setVisible(true);
        arithmeticCreate.add(arithmeticA, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        backButton11 = new JButton();
        backButton11.setMargin(new Insets(2, 8, 2, 8));
        backButton11.setText("Back");
        backButton11.setVisible(true);
        arithmeticCreate.add(backButton11, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        arithmeticB = new JTextField();
        arithmeticB.setToolTipText("The binding to do the arithmetic with");
        arithmeticB.setVisible(true);
        arithmeticCreate.add(arithmeticB, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        comboBox4 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("+");
        defaultComboBoxModel3.addElement("-");
        defaultComboBoxModel3.addElement("*");
        defaultComboBoxModel3.addElement("/");
        comboBox4.setModel(defaultComboBoxModel3);
        comboBox4.setVisible(true);
        arithmeticCreate.add(comboBox4, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intCase1Create = new JPanel();
        intCase1Create.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        intCase1Create.setBackground(new Color(-4407875));
        intCase1Create.setForeground(new Color(-4407875));
        intCase1Create.setVisible(false);
        creatingCalculus.add(intCase1Create, new com.intellij.uiDesigner.core.GridConstraints(15, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        intCase1Select = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("Output");
        defaultComboBoxModel4.addElement("Input");
        defaultComboBoxModel4.addElement("Encrypt");
        defaultComboBoxModel4.addElement("Decrypt");
        defaultComboBoxModel4.addElement("Create Pair");
        defaultComboBoxModel4.addElement("Split Pair");
        defaultComboBoxModel4.addElement("Replicate");
        defaultComboBoxModel4.addElement("Match");
        defaultComboBoxModel4.addElement("Integer Case");
        defaultComboBoxModel4.addElement("Arithmetic Operation");
        intCase1Select.setModel(defaultComboBoxModel4);
        intCase1Create.add(intCase1Select, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intCase1Add = new JButton();
        intCase1Add.setText("Add to chain 1");
        intCase1Create.add(intCase1Add, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intCase1End = new JButton();
        intCase1End.setLabel("End chain 1");
        intCase1End.setText("End chain 1");
        intCase1Create.add(intCase1End, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intCase2Create = new JPanel();
        intCase2Create.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        intCase2Create.setBackground(new Color(-4407875));
        intCase2Create.setForeground(new Color(-4407875));
        intCase2Create.setVisible(false);
        creatingCalculus.add(intCase2Create, new com.intellij.uiDesigner.core.GridConstraints(16, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        intCase2Select = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        defaultComboBoxModel5.addElement("Output");
        defaultComboBoxModel5.addElement("Input");
        defaultComboBoxModel5.addElement("Encrypt");
        defaultComboBoxModel5.addElement("Decrypt");
        defaultComboBoxModel5.addElement("Create Pair");
        defaultComboBoxModel5.addElement("Split Pair");
        defaultComboBoxModel5.addElement("Replicate");
        defaultComboBoxModel5.addElement("Match");
        defaultComboBoxModel5.addElement("Integer Case");
        defaultComboBoxModel5.addElement("Arithmetic Operation");
        intCase2Select.setModel(defaultComboBoxModel5);
        intCase2Create.add(intCase2Select, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intCase2Add = new JButton();
        intCase2Add.setLabel("Add to chain 2");
        intCase2Add.setText("Add to chain 2");
        intCase2Create.add(intCase2Add, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intCase2End = new JButton();
        intCase2End.setLabel("End chain 2");
        intCase2End.setText("End chain 2");
        intCase2Create.add(intCase2End, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editPanel = new JPanel();
        editPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        editPanel.setBackground(new Color(-4407875));
        editPanel.setForeground(new Color(-4407875));
        editPanel.setVisible(false);
        creatingCalculus.add(editPanel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nextTermButton = new JButton();
        nextTermButton.setText("Next Term");
        editPanel.add(nextTermButton, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), new Dimension(100, -1), 0, false));
        prevTermButton = new JButton();
        prevTermButton.setActionCommand("Prev Term");
        prevTermButton.setLabel("Prev Term");
        prevTermButton.setText("Prev Term");
        editPanel.add(prevTermButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), new Dimension(100, -1), 0, false));
        nextTerm = new JLabel();
        nextTerm.setHorizontalAlignment(0);
        nextTerm.setHorizontalTextPosition(0);
        nextTerm.setText("Label");
        editPanel.add(nextTerm, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        prevTerm = new JLabel();
        prevTerm.setHorizontalAlignment(0);
        prevTerm.setHorizontalTextPosition(0);
        prevTerm.setText("Label");
        editPanel.add(prevTerm, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-4407875));
        editPanel.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4517351)), null));
        currentTerm = new JLabel();
        currentTerm.setText("Label");
        panel3.add(currentTerm, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editSelection = new JPanel();
        editSelection.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        editSelection.setBackground(new Color(-4407875));
        editSelection.setForeground(new Color(-4407875));
        editSelection.setVisible(false);
        creatingCalculus.add(editSelection, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addTermButton = new JButton();
        addTermButton.setText("Add Term");
        editSelection.add(addTermButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, -1), null, 0, false));
        editTermButton = new JButton();
        editTermButton.setText("Edit Term");
        editSelection.add(editTermButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, -1), null, 0, false));
        delTermButton = new JButton();
        delTermButton.setText("Delete Term");
        editSelection.add(delTermButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, -1), null, 0, false));
        endEditButton = new JButton();
        endEditButton.setText("Finish");
        editSelection.add(endEditButton, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, -1), null, 0, false));
        createVariables = new JPanel();
        createVariables.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 5, new Insets(5, 5, 5, 5), -1, -1));
        createVariables.setBackground(new Color(-4407875));
        createVariables.setForeground(new Color(-4407875));
        createVariables.setVisible(false);
        createWindow.add(createVariables, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        comboBox1.setToolTipText("Select which agent the variable belongs to");
        createVariables.add(comboBox1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        textField1.setToolTipText("The value of the variable");
        createVariables.add(textField1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        textField2 = new JTextField();
        textField2.setToolTipText("The binding of the variable");
        createVariables.add(textField2, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        inputButton = new JButton();
        inputButton.setText("Input");
        inputButton.setToolTipText("Input the variable");
        createVariables.add(inputButton, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        finishedButton = new JButton();
        finishedButton.setText("Finished");
        finishedButton.setToolTipText("Return to selection");
        createVariables.add(finishedButton, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Create variables needed at the beginning of your system");
        createVariables.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        numberCheckBox = new JCheckBox();
        numberCheckBox.setBackground(new Color(-4407875));
        numberCheckBox.setForeground(new Color(-12763319));
        numberCheckBox.setText("Number");
        numberCheckBox.setToolTipText("Select this if the variable should be treated as a number");
        createVariables.add(numberCheckBox, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createChannels = new JPanel();
        createChannels.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 4, new Insets(5, 5, 5, 5), -1, -1));
        createChannels.setBackground(new Color(-4407875));
        createChannels.setForeground(new Color(-4407875));
        createChannels.setVisible(false);
        createWindow.add(createChannels, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        createChannelButton = new JButton();
        createChannelButton.setText("Create");
        createChannels.add(createChannelButton, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        finishedChannels = new JButton();
        finishedChannels.setText("Finished");
        createChannels.add(finishedChannels, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Create channels between processes that are required");
        createChannels.add(label3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        channelName = new JTextField();
        channelName.setToolTipText("The name of the channel");
        createChannels.add(channelName, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        createKeys = new JPanel();
        createKeys.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 4, new Insets(5, 5, 5, 5), -1, -1));
        createKeys.setBackground(new Color(-4407875));
        createKeys.setForeground(new Color(-4407875));
        createKeys.setVisible(false);
        createWindow.add(createKeys, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        createKeyButton = new JButton();
        createKeyButton.setText("Create");
        createKeys.add(createKeyButton, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        finishedKeyButton = new JButton();
        finishedKeyButton.setText("Finished");
        createKeys.add(finishedKeyButton, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Create keys between two processes");
        createKeys.add(label4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox2 = new JComboBox();
        createKeys.add(comboBox2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox3 = new JComboBox();
        createKeys.add(comboBox3, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mainMenu = new JPanel();
        mainMenu.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(5, 5, 5, 5), -1, -1));
        mainMenu.setBackground(new Color(-4407875));
        createWindow.add(mainMenu, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        loadButton = new JButton();
        loadButton.setText("Load");
        mainMenu.add(loadButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newButton = new JButton();
        newButton.setText("New");
        mainMenu.add(newButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return createWindow;
    }
}
