/**
 * A class replicating the addition of two positive integers with adders
 * Support for any size number of bits
 * Last updated 7/21/2013
 * @author Matthew Herbst
 */
public class AdderAddition {	
	//Values created by the half-adder
	private int halfAdderValue;
	private int halfAdderCarry;
	
	//Values created by the full adder
	private int fullAdderValue;
	private int fullAdderCarry;
	
	/**
	 * Default constructor
	 * @param firstValue The first value to be added
	 * @param secondValue The second value to be added
	 */
	public AdderAddition() {
		//Initialize variables
		halfAdderValue = -1;
		halfAdderCarry = -1;
		fullAdderValue = -1;
		fullAdderCarry = -1;
	}
	
	/**
	 * Takes two integers and returns the bit-string sum of the two
	 * Calculation done via adder addition
	 * @param a The first number to be summed
	 * @param b The second number to be summed
	 * @return The sum of a and b as a bit-string
	 */
	public String getSum(int a, int b) {
		int valOne = a;
		int valTwo = b;
		String bitPattern = "";
		
		//Do the half-adder
		halfAdder(valOne & 1, valTwo & 1);
		bitPattern = bitPattern + halfAdderValue;
		
		valOne = valOne >> 1;
		valTwo = valTwo >> 1;
		
		//Do as many full-adders as needed
		int mask = 1;
		int carryOver = halfAdderCarry;
		for(int i = 0; i < Integer.SIZE; ++i) {
			fullAdder(valOne & mask, valTwo & mask, carryOver);
			carryOver = fullAdderCarry;
			bitPattern = fullAdderValue + bitPattern;
			
			valOne = valOne >> 1;
			valTwo = valTwo >> 1;
		}
		
		return bitPattern;
	}
	
	/**
	 * Takes two integers and prints a result log of the program
	 * @param a The first number to be summed
	 * @param b the Second number to be summed
	 */
	public void printResults(int a, int b) {
		System.out.println("Result: ");
		
		//Print the versions
		System.out.println("Decimal version: " + a + " + " + b);
		System.out.println("Binary version: " + getBitPattern(a) + " + " + getBitPattern(b));
		
		//Print the calculations
		System.out.println("Decimal result: " + (a+b));
		System.out.println("Binary result: " + getSum(a, b) + "\n");
	}
	
	/**
	 * Returns a bit-string representation of a number
	 * @param in the decimal number to be converted
	 * @return the bit-string representation of in
	 */
	public String getBitPattern(int in) {
		int input = in;
		int count = 0;
		int mask = 1;
		int bit = 0;
		
		String pattern = "";
		
		//Go through all bits
		for(int i = 0; i < Integer.SIZE; ++i) {
			//Single & for bit wise AND
			bit = mask & input;
			
			if(bit == mask) {
				count++;
			}
			
			pattern = bit + pattern;
			
			//Bit-shift
			input = input >> 1;
		}
		
		return pattern;
	}
	
	/**
	 * Simulates addition within a half-adder
	 * @param firstOne the first value
	 * @param secondOne the second value
	 */
	private void halfAdder(int firstOne, int secondOne) {
		//XOR
		halfAdderValue = firstOne ^ secondOne;
		
		//AND
		halfAdderCarry = firstOne & secondOne;
	}
	
	/**
	 * Simulates addition within a full-adder
	 * @param firstTen the first value's tens place digit
	 * @param secondTen the second value's tens place digit
	 * @param carryOver the carry over digit from the half-adder
	 */
	private void fullAdder(int firstTen, int secondTen, int carryOver) {
		//XOR
		int tempXOR = firstTen ^ secondTen;

		//XOR
		fullAdderValue = tempXOR ^ carryOver;
		
		//OR
		fullAdderCarry = (firstTen & secondTen) | (tempXOR & carryOver);
	}
}