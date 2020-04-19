class test0 {
	public static void main(String[] a){
		System.out.println(new B().start());
	}
}
class A {
	public int start(){
		return 0;
	}
}
class B extends A{

}