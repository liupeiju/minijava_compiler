package symbol;

public class MErrorPrinter {
	private static MErrorPrinter instance = new MErrorPrinter();
	private static int size = 0;

	public static MErrorPrinter getInstance(){
		return instance;
	}
	public static int getSize(){
		return size;
	}
	public static void addError(String name, int line, int col, String info){
		size += 1;
		System.out.printf("(Line %d, Column %d) %s: %s\n", line, col, name, info);
	}

}