package ox.stackgame.stackmachine;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ox.stackgame.stackmachine.exceptions.StackRuntimeException;
import ox.stackgame.stackmachine.instructions.Instruction;

public class StackMachineListenerTest {

<<<<<<< HEAD
	class MockListener implements StackMachineListener{
		public boolean stackInstructionsChangedFired = false;
		public void stackInstructionsChanged(List<Instruction> instructions) {
			this.stackInstructionsChangedFired = true;
		}

		public int stackProgramCounter = 0;
		public void programCounterChanged(int line) {
			this.stackProgramCounter = line;
		}

		public boolean storeChangedFired = false;
		public void storeChanged(int address) {
			this.storeChangedFired=true;
		}

		public boolean inputConsumedFired=false;
		public void inputConsumed(int startIndex) {
			this.inputConsumedFired=true;
		}

		public boolean outputChangedFired=false;
		public void outputChanged() {
			this.outputChangedFired=true;
		}
	}
	
	@Test
	public void testStackInstructionsChanged() {
		StackMachine machine = new StackMachine(new StackProgram());
		MockListener l = new MockListener();
		machine.addListener(l);
		machine.addInstruction(0, new Instruction("load", new IntStackValue(2)));
		assertEquals(true, l.stackInstructionsChangedFired);
	}
	
	@Test 
	public void testStackProgramCounterChanged(){
		StackMachine machine = new StackMachine(new StackProgram());
		MockListener l = new MockListener();
		machine.addListener(l);
		machine.addInstruction(0, new Instruction("load", new IntStackValue(2)));
		assertEquals(0, l.stackProgramCounter);
		try {
			machine.step();
		} catch (StackRuntimeException e) {
			e.printStackTrace();
		}
		assertEquals(1, l.stackProgramCounter);
		
	}
	
	@Test 
	public void testStoreChanged(){
		// TODO write a test
		assertEquals(true, false);		
	}
	
	@Test 
	public void testInputConsumed(){
		// TODO write a test
		assertEquals(true, false);		
	}
	
	@Test 
	public void testOutputChanged(){
		// TODO write a test
		assertEquals(true, false);		
=======
    class MockListener implements StackMachineListener {
	public boolean stackInstructionsChangedFired = false;

	public void stackInstructionsChanged(List<Instruction> instructions) {
	    this.stackInstructionsChangedFired = true;
	}

	public boolean stackProgramCounterChanged = false;

	public void programCounterChanged(int line) {
	    this.stackProgramCounterChanged = true;
	}

	public boolean storeChangedFired = false;

	public void storeChanged(int address) {
	    this.storeChangedFired = true;
	}

	public boolean inputConsumedFired = false;

	public void inputConsumed(int startIndex) {
	    this.inputConsumedFired = true;
	}

	public boolean outputChangedFired = false;

	public void outputChanged() {
	    this.outputChangedFired = true;
>>>>>>> e00e221c768d14cf2962d15eccc78276248478a1
	}
    }

    @Test
    public void testStackInstructionsChanged() {
	StackProgram program = new StackProgram();
	StackMachine machine = new StackMachine(program);
	MockListener l = new MockListener();
	machine.addListener(l);
	machine.addInstruction(0, new Instruction("load", new IntStackValue(2)));
	assertEquals(true, l.stackInstructionsChangedFired);
    }

    @Test
    public void testStackProgramCounterChanged() throws StackRuntimeException {
	List<Instruction> program = new ArrayList<Instruction>();
	StackMachine machine = new StackMachine(program);
	MockListener l = new MockListener();
	machine.addListener(l);
	machine.addInstruction(0, new Instruction("const", new IntStackValue(5)));
	machine.step();
	assertEquals(true, l.stackProgramCounterChanged);
    }

    @Test
    public void testStoreChanged() throws StackRuntimeException {
	List<Instruction> program = new ArrayList<Instruction>();
	StackMachine machine = new StackMachine(program);
	MockListener l = new MockListener();
	machine.addListener(l);
	machine.addInstruction(0, new Instruction("const", new IntStackValue(5)));
	machine.addInstruction(1, new Instruction("store", new IntStackValue(1)));
	machine.step();
	machine.step();
	assertEquals(true, l.storeChangedFired);
    }

    @Test
    public void testInputConsumed() throws StackRuntimeException {
	List<StackValue<?>> input = new ArrayList<StackValue<?>>();
	input.add(new IntStackValue(3));
	List<Instruction> program = new ArrayList<Instruction>();
	StackMachine machine = new StackMachine(program, input);
	MockListener l = new MockListener();
	machine.addListener(l);
	machine.addInstruction(0, new Instruction("input", new IntStackValue(5)));
	machine.step();
	assertEquals(true, l.inputConsumedFired);
    }

    @Test
    public void testOutputChanged() {

    }
}
