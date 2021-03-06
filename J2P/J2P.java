import java.io.*;
import visitor.*;
import syntaxtree.*;
import symbol.*;

public class J2P {
  	public static void main(String args[]){
		try {
			InputStream in = new FileInputStream(args[0]);
			OutputStream out = new FileOutputStream(args[1]);

			Node root = new MiniJavaParser(in).Goal();
			root.accept(new MyBuildSymbolTableVisitor(), null);
			root.accept(new MyTypeCheckVisitor(), null);
			if (MErrorPrinter.getInstance().getSize() != 0){
				System.out.println("Type error");
				System.exit(0);
			}
			
			MClassList.getInstance().setOffset();
			String ans = root.accept(new MyJ2PVisitor(), null).toString();
			out.write(ans.getBytes());
			out.close();
      
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (TokenMgrError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}