import java.io.*;
import visitor.*;
import syntaxtree.*;
import symbol.*;

public class Typecheck {
  	public static void main(String args[]){
		try {
			InputStream in = new FileInputStream(args[0]);
			Node root = new MiniJavaParser(in).Goal();
			root.accept(new MyBuildSymbolTableVisitor(), null);
			root.accept(new MyTypeCheckVisitor(), null);
			if (MErrorPrinter.getInstance().getSize() == 0)
				System.out.println("Program type checked successfully");
			else System.out.println("Type error");
      
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (TokenMgrError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

