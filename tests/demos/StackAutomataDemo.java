package demos;

import java.io.IOException;

import simulator.*;

public class StackAutomataDemo {
	public static void main(String[] args) throws IOException {
		System.out.println("Item a)");
		Reader reader1 = new Reader("./input1.txt");
		reader1.doRead();
		Simulator simulator1 = new Simulator(reader1._automata, reader1._string);
		simulator1.doSimulation();
		System.out.println("--------------------------------------------------");
		
		System.out.println("Item b)");
		Reader reader2 = new Reader("./input2.txt");
		reader2.doRead();
		Simulator simulator2 = new Simulator(reader2._automata, reader2._string);
		simulator2.doSimulation();
		System.out.println("--------------------------------------------------");
		
		System.out.println("Item c)");
		Reader reader3 = new Reader("./input3.txt");
		reader3.doRead();
		Simulator simulator3 = new Simulator(reader3._automata, reader3._string);
		simulator3.doSimulation();
		System.out.println("--------------------------------------------------");
	}
}
