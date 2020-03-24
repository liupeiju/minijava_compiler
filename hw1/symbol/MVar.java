package symbol;

public class MVar extends MType {
	private MMethod nmethod;
	private MClass nclass;

	public MVar(String name, String typeName, int line, int col, MMethod nmethod, MClass nclass){
		super(name, typeName, line, col);
		this.nmethod = nmethod;
		this.nclass = nclass;
	}

}