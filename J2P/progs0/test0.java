class test0 {
	public static void main(String[] a){
		System.out.println(new Start().start());
	}
}
class Start {
	public int start(){
		B b;

		System.out.println(111);
		b = new B();
		return B.a();

	}
}

class A{
	public int a(){
		return 3;
	}
}

class B extends A{

}