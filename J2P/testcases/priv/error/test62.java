class test62{
    public static void main(String[] a){
	System.out.println(new Operator().compute());
    }
}

class Operator{
    
    int result;

    public int compute(){

	result = 10 + false;	// TE

	return 0;
    }
}
