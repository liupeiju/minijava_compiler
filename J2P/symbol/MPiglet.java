package symbol;

public class MPiglet{
	StringBuilder code;
	public MPiglet(){
		code = new StringBuilder();
	}
	public MPiglet(String str){
		code = new StringBuilder(str);
	}
	public String codeStr(){
		return code.toString();
	}
	public void add(String str){
		code.Append(str);
	}
	public void add(MPiglet piglet){
		code.Append(piglet.codeStr());
	}

	private MClass nclass;
	private MMethod nmethod;
	public MClass getNclass(){
		return nclass;
	}
	public void setNclass(nclass){
		this.nclass = nclass;
	}
	public MMethod getNmethd(){
		return nmethod;
	}
	public void setNclass(nmethod){
		this.nmethod = nmethod;
	}
}