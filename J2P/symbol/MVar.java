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
	private int offset = 0;
	private int tempNum = 0;
	public int getOffset(){
		return offset;
	}
	public void setOffset(int offset){
		this.offset = offset;
	}
	public int getTempNum(){
		return tempNum;
	}
	public void setTempNum(int tempNum){
		this.tempNum = tempNum;
	}
}