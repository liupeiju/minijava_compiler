class test0 {
	public static void main(String[] a){
		System.out.println(new A().start());
	}
}
class A {
	boolean x;
	B b;
	int _ret;
	public int start(){
		x = false;
		b = new B();
		_ret = b.init();
		if (x && (b.add()))
			System.out.println(111);
		else
			System.out.println(222);
		_ret = b.print();
		return 0;
	}
}

class B {
	int y;
	public int init(){
		y = 0;
		return y;
	}
	public boolean add(){
		y = y + 1;
		return true;
	}
	public int print(){
		System.out.println(y);
		return 0;
	}
}