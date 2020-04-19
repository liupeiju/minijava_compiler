package symbol;

public class MPiglet{
	StringBuilder code;
	public MPiglet(){
		code = new StringBuilder();
	}
	public MPiglet(String str){
		code = new StringBuilder(str);
	}
	public String toString(){
		return code.toString();
	}
	public void add(String str){
		code.append(str);
	}
	
	public void add(MPiglet piglet){
		code.append(piglet.toString());
	}

	private MClass nclass;
	public MClass getNclass(){
		return nclass;
	}
	public void setNclass(MClass nclass){
		this.nclass = nclass;
	}
}