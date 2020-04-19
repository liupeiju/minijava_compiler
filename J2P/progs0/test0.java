class test0 {
	public static void main(String[] cc){
		System.out.println(new A().start());
	}
}
class A {
	public int start(){
		int y;
		int[] z;

		z = new int[5];
		z[2] = 4;
		y = this.morethan19(z, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
		return y;
	}
	public int morethan19(int[] z,int x2,int x3,int x4,int x5,int x6,int x7,int x8,
		int x9, int x10, int x11, int x12,int x13, int x14, int x15, int x16, int x17,
		int x18, int x19, int x20){
		int y;

		y = (z[2]) + 5;
		z[2] = 50 + y;
		x16 = 5;
		return z[2];
	}
}