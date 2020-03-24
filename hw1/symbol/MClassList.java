package symbol;
import java.util.*;

public class MClassList{
	public static MClassList instance = new MClassList();
	private HashMap<String, MClass> classMap = new HashMap<String, MClass>();
	// build symbol
	private MClassList(){
		classMap.put("int[]", new MClass("int[]", 0, 0));
		classMap.put("boolean", new MClass("boolean", 0, 0));
		classMap.put("int", new MClass("int", 0, 0));
	}

	public static MClassList getInstance(){
		return instance;
	}

	public boolean addClass(MClass nclass){
		if (classMap.containsKey(nclass.getName())){
			MErrorPrinter.getInstance().addError(nclass.getName(), nclass.getLine(), nclass.getCol(), "class duplicated definition");
			return false;
		}
		classMap.put(nclass.getName(), nclass);
		return true;
	}
	// typecheck
	public MClass getClassByName(String name){
		return classMap.get(name);
	}

	public boolean checkExtend(MClass nclass){
		// check father defined
		if (classMap.get(nclass.getFatherName()) == null){
			MErrorPrinter.getInstance().addError(nclass.getName(), nclass.getLine(), nclass.getCol(), "fatherclass undefined");
			return false;
		}
		// check recursively extended
		MClass son = nclass;
		MClass father = classMap.get(son.getFatherName());
		while (true){
			if (father == null) break;
			son.setFather(father);
			if (father.getName() == nclass.getName()){
				MErrorPrinter.getInstance().addError(nclass.getName(), nclass.getLine(), nclass.getCol(), "class extends recursively");
				return false;
			}
			son = father;
			father = classMap.get(son.getFatherName());
		}
		return true;
	}
	public boolean checkTypeMatch(String name1, String name2){
		if (name1 == name2) return true;
		MClass nclass2 = classMap.get(name2);
		if (nclass2.getFatherName() == null)
			return false;
		return checkTypeMatch(name1, nclass2.getFatherName());
	}
}