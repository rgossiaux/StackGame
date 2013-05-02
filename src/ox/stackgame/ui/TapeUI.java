package ox.stackgame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

import ox.stackgame.stackmachine.CharStackValue;
import ox.stackgame.stackmachine.IntStackValue;
import ox.stackgame.stackmachine.StackMachine;
import ox.stackgame.stackmachine.StackMachineListener;
import ox.stackgame.stackmachine.StackMachineListenerAdapter;
import ox.stackgame.stackmachine.StackValue;
import ox.stackgame.stackmachine.exceptions.InvalidCharException;

/**
 * Visualisation of the input and output tapes. Allows user input in design mode
 * only. Displays current read head during RunMode.
 * 
 * @author danfox
 * 
 */

@SuppressWarnings("serial")
public class TapeUI extends JPanel {

    private List<StackValue<?>> inputTape;
    private List<StackValue<?>> outputTape;
    private StackMachine activeMachine = null;
    private static final int boxSize = 20;
    private static final int padding = 10;
    public static final int UIHeight = 2*boxSize + 4*padding;

    private ModeVisitor modeActivationVisitor = new ModeVisitor() {
        // TODO make input tape editable on DesignMode visitors

        public void visit(RunMode m) {
            activeMachine = m.machine;
            resetTapes();
            resetCursors();
        }

        @Override
        public void visit(ChallengeMode m) {
            // TODO Auto-generated method stub

        }

        @Override
        public void visit(FreeDesignMode m) {
            // TODO Auto-generated method stub

        }
    };

    private ModeVisitor modeDeactivationVisitor = new ModeVisitor() {
        // TODO make input tape uneditable on design modes

        public void visit(RunMode m) {
            // TODO hide cursor
        }

        @Override
        public void visit(ChallengeMode m) {
            // TODO Auto-generated method stub

        }

        @Override
        public void visit(FreeDesignMode m) {
            // TODO Auto-generated method stub

        }
    };

    private StackMachineListener l = new StackMachineListenerAdapter() {
        public void inputConsumed(int startIndex) {
            // TODO update input tape, move cursor
            inputTape.remove(0);
        }

        public void outputChanged(Iterator<StackValue<?>> outputs) {
            // TODO update output tape
            StackValue<?> mostRecent = null;
            while (outputs.hasNext())
                mostRecent = outputs.next();
            outputTape.add(mostRecent);
            repaint();
        }
    };

    public TapeUI(StateManager m) {

        // pay attention to mode changes
        m.registerModeActivationVisitor(modeActivationVisitor);
        m.registerModeDeactivationVisitor(modeDeactivationVisitor);

        activeMachine = m.stackMachine;
        resetTapes();

        // listen to the stack machine
        m.stackMachine.addListener(l);

        // sort out appearance
        this.setBackground(ApplicationFrame.caBlue2L);
        this.setSize(new Dimension(750 - 2 * ApplicationFrame.p, boxSize * 2
                + 4 * padding));

        resetCursors();

        // TODO create scrolling (two scrollbars?) monotype font etc, different
        // colours for input/output

    }
    
    protected void resetTapes() {
        outputTape = new LinkedList<StackValue<?>>();
        inputTape = new LinkedList<StackValue<?>>();
        for (StackValue<?> v : activeMachine.getInput())
            inputTape.add(v);
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;

        // Draw input tape

        int i = 0;
        for (StackValue<?> v : inputTape) {
            int x = i * (boxSize + padding);

            g.setColor(Color.gray);
            g.fillRect(x + padding, padding, boxSize, boxSize);

            g.setColor(Color.WHITE);
            g.drawString(v.toString(), x + padding + boxSize / 3, padding
                    + (boxSize - g.getFontMetrics().getAscent())
                    / 2 + g.getFontMetrics().getAscent());

            i++;
        }

        // Draw output tape

        int j = 0;

        for (StackValue<?> v : outputTape) {
            int x = j * (boxSize + padding);

            g.setColor(Color.gray);
            g.fillRect(x + padding, padding * 3 + boxSize, boxSize, boxSize);

            g.setColor(Color.WHITE);
            g.drawString(v.toString(), x + padding + boxSize / 3, padding * 3
                    + boxSize + (boxSize - g.getFontMetrics().getAscent())
                    / 2 + g.getFontMetrics().getAscent());

            j++;
        }
    }

    protected void resetCursors() {
        // TODO Auto-generated method stub

    }
}
