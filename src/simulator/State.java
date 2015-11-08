package simulator;

public class State {
	
	//Constructor
	public State(String nameState) {
		this._nameState = nameState;
	}
	
	public String _nameState;
	public boolean _isInitial = false;
	public boolean _isFinal = false;
}
