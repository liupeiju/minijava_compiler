javac Typecheck.java


for file in `ls ./testcases/priv/correct`; do
	echo $file
	java Typecheck ./testcases/priv/correct/$file
done
