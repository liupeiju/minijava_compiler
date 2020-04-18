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
	// 0: initial 1:complete 2:setOffset
	private HashSet<MVar> varSet = new HashSet<>();
	private HashSet<MMethod> methodSet = new HashSet<>();
	public void initSet(){
		for (MVar nvar: varMap.values())
			varSet.add(nvar);
		for (MMethod nmethod: methodMap.values())
			methodSet.add(nmethod);
	}
	public HashSet<MVar> getVarSet(){
		return varSet;
	}
	public HashSet<MMethod> getMethodSet(){
		return methodSet;
	}

	private int status = 0;
	public int setOffset(int tempNum){
		if (status == 1) return tempNum;
		initSet();
		int offset = 0;
		MClass father = null;
		if (fatherName != null){
			father = MClassList.getInstance().getClassByName(fatherName);
			tempNum = father.setOffset(tempNum);
		}

		if (father != null){
			for (MVar nvar: father.getVarSet()){
				varSet.add(nvar);
				if (nvar.getOffset() > offset)
					offset = nvar.getOffset();
			}
		}
		for (MVar nvar: this.getVarSet()){
			offset += 4;
			nvar.setOffset(offset);
		}

		offset = 0;
		HashSet<Integer> methodOffset = new HashSet<>();
		if (father != null){
			for (MMethod nmethod: father.getMethodSet()){
				if (!methodMap.containsKey(nmethod.getName()))
					methodSet.add(nmethod);
					methodOffset.add(nmethod.getOffset());
			}
		}
		for (MMethod nmethod: this.getMethodSet()){
			while (methodOffset.contains(offset))
				offset += 4;
			tempNum = nmethod.setOffset(offset, tempNum);
			offset += 4;
		}
		status = 1;
		return tempNum;
	}
}