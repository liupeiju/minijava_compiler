#java -jar pgi.jar < examp/BinaryTree.pg > out/BinaryTree.out
#java -jar pgi.jar < examp/BubbleSort.pg > out/BubbleSort.out
#java -jar pgi.jar < examp/Factorial.pg > out/Factorial.out
#java -jar pgi.jar < examp/LinearSearch.pg > out/LinearSearch.out
#java -jar pgi.jar < examp/LinkedList.pg > out/LinkedList.out
#java -jar pgi.jar < examp/MoreThan4.pg > out/MoreThan4.out
#java -jar pgi.jar < examp/QuickSort.pg > out/QuickSort.out
#java -jar pgi.jar < examp/TreeVisitor.pg > out/TreeVisitor.out

javac J2P.java
java J2P progs0/BinaryTree.java progs1/BinaryTree.pg
java J2P progs0/BubbleSort.java progs1/BubbleSort.pg
java J2P progs0/Factorial.java progs1/Factorial.pg
java J2P progs0/LinearSearch.java progs1/LinearSearch.pg
java J2P progs0/LinkedList.java progs1/LinkedList.pg
java J2P progs0/MoreThan4.java progs1/MoreThan4.pg
java J2P progs0/QuickSort.java progs1/QuickSort.pg
java J2P progs0/TreeVisitor.java progs1/TreeVisitor.pg
#java J2P progs0/test0.java progs1/test0.pg

java -jar pgi.jar < progs1/BinaryTree.pg > out/my_BinaryTree.out
java -jar pgi.jar < progs1/BubbleSort.pg > out/my_BubbleSort.out
java -jar pgi.jar < progs1/Factorial.pg > out/my_Factorial.out
java -jar pgi.jar < progs1/LinearSearch.pg > out/my_LinearSearch.out
java -jar pgi.jar < progs1/LinkedList.pg > out/my_LinkedList.out
java -jar pgi.jar < progs1/MoreThan4.pg > out/my_MoreThan4.out
java -jar pgi.jar < progs1/QuickSort.pg > out/my_QuickSort.out
java -jar pgi.jar < progs1/TreeVisitor.pg > out/my_TreeVisitor.out
#java -jar pgi.jar < progs1/test0.pg

diff out/BinaryTree.out out/my_BinaryTree.out
diff out/BubbleSort.out out/my_BubbleSort.out
diff out/Factorial.out out/my_Factorial.out
diff out/LinearSearch.out out/my_LinearSearch.out
diff out/LinkedList.out out/my_LinkedList.out
diff out/MoreThan4.out out/my_MoreThan4.out
diff out/QuickSort.out out/my_QuickSort.out
diff out/TreeVisitor.out out/my_TreeVisitor.out
