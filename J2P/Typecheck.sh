javac Typecheck.java

for file in `ls ./progs0`; do
	echo $file
	java Typecheck progs0/$file
done
