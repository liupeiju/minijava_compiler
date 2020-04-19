package symbol;
import java.util.*;

public class MPiglet{
	private StringBuilder code;
	private ArrayList<String> paramList;

	public MPiglet(){
		code = new StringBuilder();
		paramList = new ArrayList<String>();
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

	public void init(String param){
		paramList.add(param);
	}
	
	public void add(MPiglet piglet){
		code.append(piglet.toString());
		for (String param: piglet.paramList)
			this.paramList.add(param);
	}

	private MClass nclass;
	public MClass getNclass(){
		return nclass;
	}
	public void setNclass(MClass nclass){
		this.nclass = nclass;
	}

	public ArrayList<String> getParams(){
		return paramList;
	}

}