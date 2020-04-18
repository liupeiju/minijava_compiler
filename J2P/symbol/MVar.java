package symbol;

public class MVar extends MType {
	private MMethod nmethod;
	private MClass nclass;

	public MVar(String name, String typeName, int line, int col, MMethod nmethod, MClass nclass){
		super(name, typeName, line, col);
		this.nmethod = nmethod;
		this.nclass = nclass;
	}

	// piglet
	private int temp = 0;
	public int getTemp(){
		return temp;
	}

	private int offset = -1;
	public int getOffset(){
		return offset;
	}
	public void setOffset(int offset){
		this.offset = offset;
	}

}