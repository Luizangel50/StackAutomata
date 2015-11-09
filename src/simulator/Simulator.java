package simulator;

import java.util.Stack;
import java.util.ArrayList;

public class Simulator {
	
	//Constructor
	public Simulator(Automata automata, String[] string) {
		this._automata = automata;
		this._string = string;
	}
	
	//Simulates the pushdown automata
	public void doSimulation() {
		//try {
			Stack<String> stack = new Stack<String>();
			Stack<String> stackStep = new Stack<String>();
			State currentState = initialState();
			
			doTransition(stack, currentState, 0, stackStep);
	}
	
	//Simulates a Transition
	public void doTransition(Stack<String> stack,
							State currentState, 
							int position, 
							Stack<String> stackStep) {
		try{
			if(position != this._string.length) {
				//Epsilon transitions
				boolean notEpsilon = false;
				stackStep.push(currentState._nameState);
				ArrayList<Integer> transitions = searchTransition(currentState._nameState, "*");
				if(!transitions.isEmpty()) {
					for(int i = 0; i < transitions.size(); i++) {
						String nextState = this._automata._transitions.get(transitions.get(i))[4];
						boolean epsilon = this._automata._transitions.get(transitions.get(i))[2].equals("*");
						boolean canPop = !stack.empty() && stack.lastElement().equals(this._automata._transitions.get(transitions.get(i))[2]);
						String itemPushed = this._automata._transitions.get(transitions.get(i))[3];
						//Element to be popped is not epsilon and stack is not empty
						if(!epsilon) {
							String popped = null;
							if(canPop) {
								popped = stack.pop();
								if(!itemPushed.equals("*"))	stack.push(itemPushed);
								doTransition(stack, searchState(nextState), position, stackStep);
								if(!itemPushed.equals("*"))	stack.pop();
								stack.push(popped);
							}
							else if(!stack.empty() && !stack.lastElement().equals(this._automata._transitions.get(transitions.get(i))[2])){
								if(!stackStep.isEmpty()) System.out.println(stackStep);	
							}
							
						}
						//Element to be popped is epsilon
						else if(epsilon) {
							if(!itemPushed.equals("*"))	stack.push(itemPushed);
							doTransition(stack, searchState(nextState),	position, stackStep);
							if(!itemPushed.equals("*"))	stack.pop();
						}
					}
					stackStep.pop();
				}
				else {
					notEpsilon = true;
					if(!stackStep.isEmpty()) stackStep.pop();
				}
		
				//Non-epsilon transitions
				stackStep.push(currentState._nameState);
				transitions.clear();
				transitions = searchTransition(currentState._nameState, this._string[position]);
				if(!transitions.isEmpty()) {
					for(int i = 0; i < transitions.size(); i++) {
						String nextState = this._automata._transitions.get(transitions.get(i))[4];
						boolean epsilon = this._automata._transitions.get(transitions.get(i))[2].equals("*");
						boolean canPop = !stack.empty() && stack.lastElement().equals(this._automata._transitions.get(transitions.get(i))[2]);
						String itemPushed = this._automata._transitions.get(transitions.get(i))[3];
						//Element to be popped is not epsilon and stack is not empty
						if(!epsilon) {
							String popped = null;
							if(canPop) {
								popped = stack.pop();
								if(!itemPushed.equals("*"))	stack.push(itemPushed);
								doTransition(stack, searchState(nextState), position+1, stackStep);
								if(!itemPushed.equals("*"))	stack.pop();
								stack.push(popped);
							}
							else if(!stack.empty() && !stack.lastElement().equals(this._automata._transitions.get(transitions.get(i))[2])){
								if(!stackStep.isEmpty()) System.out.println(stackStep);	
							}				
						}
						//Element to be popped is epsilon
						else if(epsilon) {
							if(!itemPushed.equals("*"))	stack.push(itemPushed);
							doTransition(stack, searchState(nextState),	position + 1, stackStep);
							if(!itemPushed.equals("*")) stack.pop();
						}
					}
					stackStep.pop();
				}
				else {
					if(!stackStep.isEmpty() && notEpsilon==false) {
						if(!currentState._isInitial) {
							//System.out.println(stackStep);
							stackStep.pop();
							return;
						}
					}
					else if(!stackStep.isEmpty() && notEpsilon==true) {
						System.out.println(stackStep);
						stackStep.pop();
						return;
					}
				}
			}
			else {
						
				//boolean notEpsilon = false;
				stackStep.push(currentState._nameState);
				ArrayList<Integer> transitions = searchTransition(currentState._nameState, "*");
				if(!transitions.isEmpty()) {
					for(int i = 0; i < transitions.size(); i++) {
						String nextState = this._automata._transitions.get(transitions.get(i))[4];
						boolean epsilon = this._automata._transitions.get(transitions.get(i))[2].equals("*");
						boolean canPop = !stack.empty() && stack.lastElement().equals(this._automata._transitions.get(transitions.get(i))[2]);
						String itemPushed = this._automata._transitions.get(transitions.get(i))[3];
						//Element to be popped is not epsilon and stack is not empty
						if(!epsilon) {
							String popped = null;
							if(canPop) {
								popped = stack.pop();
								if(!itemPushed.equals("*"))	stack.push(itemPushed);
								doTransition(stack, searchState(nextState), position, stackStep);
								if(!itemPushed.equals("*"))	stack.pop();
								stack.push(popped);
							}
							else if(!stack.empty() && !stack.lastElement().equals(this._automata._transitions.get(transitions.get(i))[2])){
								if(!stackStep.isEmpty()) System.out.println(stackStep);	
							}
							
						}
						//Element to be popped is epsilon
						else if(epsilon) {
							if(!itemPushed.equals("*"))	stack.push(itemPushed);
							doTransition(stack, searchState(nextState),	position, stackStep);
							if(!itemPushed.equals("*"))	stack.pop();
							
						}
					}
					stackStep.pop();
				}
				else {
					//System.out.println(stackStep);
					//notEpsilon = true;
					if(!stackStep.isEmpty()) stackStep.pop();
				}
				
				if(currentState._isFinal){
					stackStep.push(currentState._nameState);
					if(!stackStep.isEmpty()) System.out.print(stackStep);
					System.out.print(" : String accepted \n");
					stackStep.clear();
				}
				//System.out.println();
				return;
			}
		}
		catch(Exception e) {
			
		}
	}
	
	//Searches the State given the name of State
	public State searchState(String name) {
		for(int i = 0; i < this._automata._states.size(); i++) {
			if(this._automata._states.get(i)._nameState.equals(name)) {
				return this._automata._states.get(i);
			}
		}
		return this._automata._states.get(-1);
	}
	
	//Searches the initial State
	public State initialState() {
		for(int i = 0; i < this._automata._states.size(); i++) {
			if(this._automata._states.get(i)._isInitial) {
				return this._automata._states.get(i);
			}
		}
		return this._automata._states.get(-1);
	}
	
	//Searches transitions
	public ArrayList<Integer> searchTransition(String name, String symbol) {
		ArrayList<Integer> transList = new ArrayList<Integer>();
		for(int i = 0; i < this._automata._transitions.size(); i++) {
			if(this._automata._transitions.get(i)[0].equals(name) &&
				this._automata._transitions.get(i)[1].equals(symbol)) {
				transList.add(i);
			}
		}
		return transList;
	}
		
	public Automata _automata;
	public String[] _string;
}
