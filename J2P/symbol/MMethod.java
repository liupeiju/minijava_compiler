package symbol;
import java.util.*;

public class MMethod extends MType {
	private MClass nclass;
	private HashMap<String, MVar> paramMap = new HashMap<String, MVar>();
	private ArrayList<MVar> paramList = new ArrayList<MVar>();
	private HashMap<String, MVar> varMap = new HashMap<String, MVar>();


	public MMethod(String name, String typeName, int line, int col, MClass nclass){
		super(name, typeName, line, col);
		this.nclass = nclass;
	}

	public boolean addParam(MVar nparam){
		if (paramMap.containsKey(nparam.getName())){
			MErrorPrinter.getInstance().addError(nparam.getName(), nparam.getLine(), nparam.getCol(), "param duplicated definition");
			return false;
		}
		paramMap.put(nparam.getName(), nparam);
		paramList.add(nparam);
		return true;
	}

	public boolean addVar(MVar nvar){
		if (varMap.containsKey(nvar.getName()) || paramMap.containsKey(nvar.getName())){
			MErrorPrinter.getInstance().addError(nvar.getName(), nvar.getLine(), nvar.getCol(), "var duplicated definition");
			return false;
		}
		varMap.put(nvar.getName(), nvar);
		return true;
	}

	public MClass getMyClass(){
		return nclass;
	}

	public MVar getVarByName(String name){
		if (varMap.containsKey(name)) return varMap.get(name);
		if (paramMap.containsKey(name)) return paramMap.get(name);
		return nclass.getVarByName(name);
	}

	public boolean checkListTypeMatch(ArrayList<MType> typelist){
		if (paramList.size() != typelist.size()){
			return false;
		}
		for (int i = 0; i < paramList.size(); i ++){
			String name1 = paramList.get(i).getTypeName();
			String name2 = typelist.get(i).getTypeName();
			if (!MClassList.getInstance().checkTypeMatch(name1, name2))
				return false;
		}
		return true;
	}

	public ArrayList<MVar> getParams(){
		return paramList;
	}

	//piglet
	private int offset;
	private String pigletName;

	public int getOffset(){
		return offset;
	}

	public int setOffset(int offset, int tempNum){
		this.offset = offset;
		int num=1;
		for (MVar param: paramList){
			param.setTempNum(num++);
		}
		for (MVar nvar: varMap.values()){
			nvar.setTempNum(tempNum++);
		}
		return tempNum;
	}

	public void setPigletName(){
		pigletName = nclass.getName()+"_"+getName();
	}
	public String getPigletName(){
		return pigletName;
	}
	public String getPigletDefinition(){
		return pigletName+" [ "+(paramList.size()+1)+" ]";
	}
}