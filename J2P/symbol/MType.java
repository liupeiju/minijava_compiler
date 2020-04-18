package symbol;

public class MType {
	protected String name;
	protected String typeName;
	protected int line;
	protected int col;

	// constructor
	public MType(){}
	public MType(String name, String typeName, int line, int col){
		this.name = name;
		this.typeName = typeName;
		this.line = line;
		this.col = col;
	}

	public String getName(){ 
		return this.name;
	}
	public String getTypeName(){
		return this.typeName;
	}
	public int getLine(){
		return this.line;
	}
	public int getCol(){
		return this.col;
	}
}