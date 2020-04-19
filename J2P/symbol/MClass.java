package symbol;
import java.util.*;

public class MClass extends MType {
	private String fatherName;
	private HashMap<String, MVar> varMap = new HashMap<String, MVar>();
	private HashMap<String, MMethod> methodMap = new HashMap<String, MMethod>();

	public MClass(String name, int line, int col){
		super(name, null, line, col);
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
	public boolean checkOverload(String name){
		MMethod nmethod = methodMap.get(name);
		if (fatherName == null) return true;
		MClass father = MClassList.getInstance().getClassByName(fatherName);
		return father.checkMethod(nmethod);
	}

	public boolean checkMethod(MMethod nmethod){
		if (methodMap.containsKey(nmethod.getName())){
			MMethod find_method = methodMap.get(nmethod.getName());
			//if (find_method.getTypeName() != nmethod.getTypeName())
			String name1 = find_method.getTypeName();
			String name2 = nmethod.getTypeName();
			if (!MClassList.getInstance().checkTypeMatch(name1, name2))
			// override要求参数列表完全相同,返回类型兼容. overload要求参数列表不同.否则报错.
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
		if (fatherName == null) return true;
		MClass father = MClassList.getInstance().getClassByName(fatherName);
		return father.checkMethod(nmethod);
	}

	public void setFatherName(String fatherName){
		this.fatherName = fatherName;
	}
	public String getFatherName(){
		return fatherName;
	}
	public MMethod getMethodByName(String name){
		if (methodMap.containsKey(name)) return methodMap.get(name);
		if (fatherName == null) return null;
		MClass father = MClassList.getInstance().getClassByName(fatherName);
		return father.getMethodByName(name);
	}
	public MVar getVarByName(String name){
		if (varMap.containsKey(name)) return varMap.get(name);
		if (fatherName == null) return null;
		MClass father = MClassList.getInstance().getClassByName(fatherName);
		return father.getVarByName(name);
	}

	// piglet
	private ArrayList<MVar> varList = new ArrayList<>();
	private ArrayList<MMethod> methodList = new ArrayList<>();
	public ArrayList<MVar> getVarList(){
		return varList;
	}
	public ArrayList<MMethod> getMethodList(){
		return methodList;
	}

	private int status = 0;
	public int setOffset(int tempNum){
		if (status == 1) return tempNum;
		MClass father = null;
		if (fatherName != null){
			father = MClassList.getInstance().getClassByName(fatherName);
			tempNum = father.setOffset(tempNum);
		}

		int offset = 1;
		if (father != null){
			for (MVar nvar: father.getVarList()){
				varList.add(nvar);
				offset = nvar.getOffset() + 1;
			}
		}
		for (MVar nvar: varMap.values()){
			nvar.setOffset(offset);
			varList.add(nvar);
			offset += 1;
		}

		offset = 0;
		if (father != null){
			for (MMethod nmethod: father.getMethodList()){
				String name = nmethod.getName();
				int offset1 = nmethod.getOffset();
				if (!methodMap.containsKey(name)){
					methodList.add(nmethod);
				} else{
					MMethod nmethod2 = methodMap.get(name);
					tempNum = nmethod2.setOffset(offset1, tempNum);
					nmethod2.setPigletName();
					methodList.add(nmethod2);
				}
				offset = offset1 + 1;
			}
		}
		for (MMethod nmethod: methodMap.values()){
			tempNum = nmethod.setOffset(offset, tempNum);
			nmethod.setPigletName();
			methodList.add(nmethod);
			offset += 1;
		}
		status = 1;
		return tempNum;
	}
}