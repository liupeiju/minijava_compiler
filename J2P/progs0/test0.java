class test0 {
	public static void main(String[] a){
		System.out.println(new B().a());
	}
}
class A{
	public int a(){
		return 1;
	}
}
class B extends A{
}