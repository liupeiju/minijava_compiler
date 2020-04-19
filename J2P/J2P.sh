java -jar pgi.jar < examp/BinaryTree.pg
#java -jar pgi.jar < examp/BubbleSort.pg 
#java -jar pgi.jar < examp/Factorial.pg
#java -jar pgi.jar < examp/LinearSearch.pg
#java -jar pgi.jar < examp/LinkedList.pg
#java -jar pgi.jar < examp/MoreThan4.pg
#java -jar pgi.jar < examp/QuickSort.pg
#java -jar pgi.jar < examp/TreeVisitor.pg

javac J2P.java

java J2P progs0/BinaryTree.java progs1/BinaryTree.pg
java J2P progs0/BubbleSort.java progs1/BubbleSort.pg
java J2P progs0/Factorial.java progs1/Factorial.pg
java J2P progs0/LinearSearch.java progs1/LinearSearch.pg
java J2P progs0/LinkedList.java progs1/LinkedList.pg
java J2P progs0/MoreThan4.java progs1/MoreThan4.pg
java J2P progs0/QuickSort.java progs1/QuickSort.pg
java J2P progs0/TreeVisitor.java progs1/TreeVisitor.pg
java J2P progs0/test0.java progs1/test0.pg



echo '----------------------------------------'
java -jar pgi.jar < progs1/BinaryTree.pg
#java -jar pgi.jar < progs1/BubbleSort.pg
#java -jar pgi.jar < progs1/Factorial.pg
#java -jar pgi.jar < progs1/LinearSearch.pg
#java -jar pgi.jar < progs1/LinkedList.pg
#java -jar pgi.jar < progs1/MoreThan4.pg
#java -jar pgi.jar < progs1/QuickSort.pg
#java -jar pgi.jar < progs1/TreeVisitor.pg
#java -jar pgi.jar < progs1/test0.pg