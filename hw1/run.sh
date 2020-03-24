javac Typecheck.java

for file in `ls ./progs`; do
	echo $file
	java Typecheck progs/$file
done
