package simulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	
	//Constructor
	public Reader(String file) {
		try {
			this._input = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("Input file not found");
		}
	}
	
	//Read the input file
	public void doRead() {
		try {
			readAlphabet();
			readStatesTrans();
			readString();
			this._input.close();
		}
		catch (Exception e){
			System.out.println("Error on reading input file");
		}
	}
	
	//Reads the Alphabet
	public void readAlphabet() throws Exception {
		String[] alpha = this._input.readLine().split("\\s");
		for(int i = 0; i < alpha.length; i++) {
			//The symbol '*' is reserved for epsilon character
			if(!alpha[i].equals("*")) {
				this._automata._alphabet.add(alpha[i]);
			}
			else {
				throw new Exception();
			}		
		}
	}
	
	//Reads States and Transitions
	public void readStatesTrans() throws NumberFormatException, IOException {
		String[] states = this._input.readLine().split("\\s");
		for(int i = 0; i < states.length; i++) {
			this._automata._states.add(new State(states[i]));
		}
		
		String initialState = this._input.readLine();
		String[] finalStates = this._input.readLine().split("\\s");
		for(int i = 0; i < this._automata._states.size(); i++) {
			if(this._automata._states.get(i).
					_nameState.equals(initialState)) {
					this._automata._states.get(i)._isInitial = true;
			}
			for(int j = 0; j < finalStates.length; j++) {
				if(this._automata._states.get(i).
					_nameState.equals(finalStates[j])) {
					this._automata._states.get(i)._isFinal = true;
				}
			}
		}
		for(int i = 0, numberStates = Integer.parseInt(this._input.readLine());
			i < numberStates; i++) {
			this._automata._transitions.add(this._input.readLine().split("\\s"));
		}
	}
	
	//Reads the input String
	public void readString() throws Exception {
		this._string = this._input.readLine().split("\\s");
	}
	
	//Modifies the input file
	public void modifyFile(String file) {
		try {
			this._input = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("Input file not found");
		}
	}
	
	private BufferedReader _input;
	public Automata _automata = new Automata();
	public String[] _string;
}
