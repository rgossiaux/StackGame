/**
 * 
 */
package ox.stackgame.ui;

import java.util.*;

import ox.stackgame.challenge.AbstractChallenge;
import ox.stackgame.challenge.StackResultChallenge;
import ox.stackgame.stackmachine.IntStackValue;
import ox.stackgame.stackmachine.instructions.Instruction;

/**
 * Allows the user to create a program to solve a specific challenge (limited
 * instruction set, well defined success conditions, specified max stack size).
 * 
 * At the moment, it stores the current challenge.
 * 
 * @author danfox
 * @author rgossiaux
 * 
 */
public class ChallengeMode extends DesignMode {
    public static final List<AbstractChallenge> challengeList = new LinkedList<AbstractChallenge>();

    public void accept(ModeVisitor v) {
        v.visit(this);
    }

    public ChallengeMode() {
        
        // initialise challengeList
        HashMap<Instruction,Integer> instructionSet = new HashMap<Instruction,Integer>();
        instructionSet.put(new Instruction("const", new IntStackValue(1)), -1);
        instructionSet.put(new Instruction("add"), -1);
        challengeList.add(new StackResultChallenge("Use unlimited 'const 1' and 'add' instructions to produce 5 on the stack.",
                instructionSet, new IntStackValue(5) ));

        HashMap<Instruction,Integer> iSet2 = new HashMap<Instruction,Integer>();
        iSet2.put(new Instruction("const", new IntStackValue(4)), -1);
        iSet2.put(new Instruction("const", new IntStackValue(3)), -1);
        iSet2.put(new Instruction("add"), -1);
        iSet2.put(new Instruction("div"), -1);
        challengeList.add(new StackResultChallenge("Combine 'const 3', 'const 4', 'add' and 'div' to produce 5 on the stack.",
                iSet2, new IntStackValue(5) ));
       
    }

}
