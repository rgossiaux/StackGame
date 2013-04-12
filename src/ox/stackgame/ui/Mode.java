package ox.stackgame.ui;

/**
 * Representive class of the collective behaviour of the StackGame UI.
 * @author danfox
 *
 */
public abstract class Mode {
	public final void accept (ModeVisitor v){
		v.visit(this);
	}
}
