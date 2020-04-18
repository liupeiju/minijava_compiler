package symbol;
import java.util.*;

public class MTypeList extends MType{
	private ArrayList<MType> types = new ArrayList<MType>();

	public ArrayList<MType> getList(){
		return types;
	}

	public void addType(MType type){
		types.add(type);
	}
	public void mergeList(MTypeList typelist){
		for (MType type : typelist.getList())
			types.add(type);
	}
}