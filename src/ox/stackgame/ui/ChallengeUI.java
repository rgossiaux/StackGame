package ox.stackgame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ox.stackgame.stackmachine.IntStackValue;
import ox.stackgame.stackmachine.StackMachine;
import ox.stackgame.stackmachine.StackMachineListener;
import ox.stackgame.stackmachine.StackMachineListenerAdapter;
import ox.stackgame.stackmachine.instructions.Instruction;

/**
 * Allow the user to select a challenge from a predefined list. Listen to the
 * machine during RunMode, evaluating it against the current challenge success
 * criteria and displaying either success or failure messages as appropriate.
 * Also constrains execution to the challenge's specified maxInstructions,
 * stackSize and allowedInstructions.
 * 
 * @author danfox
 * @author rgossiaux
 * 
 */

@SuppressWarnings("serial")
public class ChallengeUI extends JPanel {

    /**
     * currentChallenge stores the active challenge. This can be null; denoting
     * the user has not yet selected a challenge.
     */
    //protected AbstractChallenge currChallenge = null;
    protected JLabel descLabel;
    private StackMachine machine;
    private final StateManager stateManager;
    private final ChallengeMode challengeMode;
    private ErrorUI eui;

    public ChallengeUI(StateManager m, ChallengeMode cm, ErrorUI eui) {
        m.stackMachine.addListener(l);
        this.machine = m.stackMachine;
        this.stateManager = m;
        this.challengeMode = cm;
        this.eui = eui;

        m.registerModeActivationVisitor(modeActivationVisitor);
        m.registerModeDeactivationVisitor(modeDeactivationVisitor);

        this.setLayout(new FlowLayout());
        this.setBackground(Color.white);
        this.setSize(new Dimension(ApplicationFrame.LEFT_PANEL_WIDTH, ApplicationFrame.h));
        this.setBorder(new EmptyBorder(15, 15, 15, 15));
        JLabel l = new JLabel("Challenge");
        l.setFont(new Font("Helvetica Neue", Font.BOLD, 23));
        l.setForeground(new Color(66, 66, 66));
        this.add(l);
        descLabel = new JLabel();
        this.add(descLabel);
        this.setVisible(false);
    }

    private StackMachineListener l = new StackMachineListenerAdapter() {
        public void programCounterChanged(int line) {
            if (stateManager.getActiveMode() instanceof ChallengeMode && challengeMode.getChallenge() != null){
                // when the machine terminates, evaluate machine against challenge's hasSucceeded() function
                if (machine.isRunning()==false) {
                    Boolean hasSucceeded = challengeMode.getChallenge().hasSucceeded(machine);
                    String message = challengeMode.getChallenge().getMessage();
                    System.out.println(hasSucceeded ? "Passed" : "Failed");
                    System.out.println(message);
                    // TODO display message in GUI
                }
            }
        }
    };
    
    private void checkMachineInstructions(StackMachine m){
        if (challengeMode.getChallenge() != null) {
            
            System.out.println(new Instruction("const", new IntStackValue(1)).equals(new Instruction("const", new IntStackValue(1))));
            
            // check the program is allowed by the challenge
            if (challengeMode.getChallenge().checkProgram(m.getInstructions())==false){
                System.out.println("Program doesn't conform to Challenge's instructionSet");
                eui.displayError("Your program must use only the allowed instructions");
            }
        }
    }

    private ModeVisitor modeActivationVisitor = new ModeVisitor() {

        /**
         * When switching to RunMode, check the program against allowedInstructions.
         */
        public void visit(RunMode m) {
            if (stateManager.getActiveMode() instanceof ChallengeMode)
                ChallengeUI.this.checkMachineInstructions(machine);
        }

        // this mode is activated!
        public void visit(ChallengeMode m) {
            descLabel.setText("<html>"+challengeMode.getChallenge().description+"</html>");
            ChallengeUI.this.setVisible(true);
        }

        public void visit(FreeDesignMode m) {
        }
    };

    // code to be executed when a mode is deactivated
    private ModeVisitor modeDeactivationVisitor = new ModeVisitor() {
        public void visit(RunMode m) {
        }

        // this mode is deactivated
        public void visit(ChallengeMode m) {
            ChallengeUI.this.setVisible(false);
        }

        public void visit(FreeDesignMode m) {
        }
    };

}
