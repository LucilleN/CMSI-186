/**This class calculates how to get change for a given cent value with the fewest number of coins.*/
public class ChangeMaker {
	/**Takes in user input, calls the optimalChange function, and prints out an output.*/
	public static void main(String[] args) {
		if (args.length < 2) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < args.length - 1; i++) {
			try {
				if (Integer.parseInt(args[i]) <= 0) {
					throw new IllegalArgumentException();
				}
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}
			for (int j = 0; j < i; j++) {
				if (args[i].equals(args[j])) {
					throw new IllegalArgumentException();
				}
			}
		}
		try {
			if (Integer.parseInt(args[args.length-1]) < 0) {
				throw new IllegalArgumentException();
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
		Tuple denominations = new Tuple(args.length - 1);
		for (int i = 0; i < args.length - 1; i ++) {
			denominations.setElement(i, Integer.parseInt(args[i]));
		}
		int changeAmt = Integer.parseInt(args[args.length - 1]);
		Tuple result = optimalChange(denominations, changeAmt);
		if (result == null) {
			System.out.println("IMPOSSIBLE");
		} 
		else {
			for (int i = 0; i < denominations.length(); i++) {
				System.out.println(result.getElement(i) + " " + denominations.getElement(i) + "-cent coins");
			}
			System.out.println(result.sum() + " total coins");
		} 		
	}
	/**Returns a tuple whose elements are the optimal combination of coins to get change for a given amount.*/
	public static Tuple optimalChange(Tuple denominations, int changeAmt) { //make this throw an exception whenever you can't do one )];of the steps, i.e. it doesnt work. 
		Tuple[][] table = new Tuple[changeAmt + 1][denominations.length()];		
		for (int column = 0; column < table.length; column++) {
			for (int row = 0; row < denominations.length(); row++) {
				table[column][row] = null;
			}
		}
		for (int row = 0; row < denominations.length(); row++) {
			table[0][row] = new Tuple(denominations.length());
		}
		for (int column = 0; column < table.length; column++) {
			for (int row = 0; row < denominations.length(); row++) {
				if (denominations.getElement(row) <= column) {
					table[column][row] = new Tuple(denominations.length());
					table[column][row].setElement(row, 1);
					if (table[column - denominations.getElement(row)][row] != null) {
						table[column][row] = table[column][row].add(table[column - denominations.getElement(row)][row]);	
						if ((row > 0) && (table[column][row-1] != null) && (table[column][row].sum() > table[column][row-1].sum())) {
							table[column][row] = table[column][row-1].clone();
						}
					}
					else { //table[column - denominations.getElement(row)][row] == null
						table[column][row] = null;
					}
					if (row > 0 && table[column][row] == null && table[column][row-1] != null) {
						table[column][row] = table[column][row-1].clone();
					}
				}
				else if ((row > 0) && table[column][row-1] != null) {
					table[column][row] = table[column][row-1].clone();
				}
				// if (table[column][row] != null) {
				// 	System.out.println(table[column][row].toString());
				// }
				// else {
				// 	System.out.println(table[column][row]);
				// }
			}
		}
		return table[table.length-1][denominations.length()-1];
	}
}