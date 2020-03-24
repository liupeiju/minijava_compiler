package symbol;
import java.util.*;

public class MClass extends MType {
	private String fatherName;
	private MClass father;
	private HashMap<String, MVar> varMap = new HashMap<String, MVar>();
	private HashMap<String, MMethod> methodMap = new HashMap<String, MMethod>();

	public MClass(String name, int line, int col){
		this.name = name;
		this.line = line;
		this.col = col;
	}

	public boolean addVar(MVar nvar){
		if (varMap.containsKey(nvar.getName())){
			MErrorPrinter.getInstance().addError(nvar.getName(), nvar.getLine(), nvar.getCol(), "var duplicated definition");
			return false;
		}
		varMap.put(nvar.getName(), nvar);
		return true;
	}
	public boolean addMethod(MMethod nmethod){
		if (methodMap.containsKey(nmethod.getName())){
			MErrorPrinter.getInstance().addError(nmethod.getName(), nmethod.getLine(), nmethod.getCol(), "method duplicated definition");
			return false;
		}
		methodMap.put(nmethod.getName(), nmethod);
		return true;
	}
	public boolean checkOverload(MMethod nmethod){
		if (father == null) return true;
		return father.checkMethod(nmethod);
	}

	public boolean checkMethod(MMethod nmethod){
		if (methodMap.containsKey(nmethod.getName())){
			MMethod find_method = methodMap.get(nmethod.getName());
			if (find_method.getTypeName() != nmethod.getTypeName()) //override要求类型完全相同
				return false;
			ArrayList<MVar> find_list = find_method.getParams();
			ArrayList<MVar> nlist = nmethod.getParams();
			if (find_list.size() != nlist.size())
				return false;
			for (int i = 0; i < find_list.size(); i ++){
				if (find_list.get(i).getTypeName() != nlist.get(i).getTypeName())
					return false;
			}
			return true;
		}
		if (father == null) return true;
		return father.checkMethod(nmethod);
	}

	public void setFatherName(String fatherName){
		this.fatherName = fatherName;
	}
	public MMethod getMethodByName(String name){
		return methodMap.get(name);
	}
	public String getFatherName(){
		return fatherName;
	}
	public void setFather(MClass father){
		this.father = father;
	}
	public MVar getVarByName(String name){
		if (varMap.containsKey(name)) return varMap.get(name);
		if (father == null) return null;
		return father.getVarByName(name);
	}
}