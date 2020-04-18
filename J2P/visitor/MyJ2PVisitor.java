//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJDepthFirst<MPiglet, Object> implements GJVisitor<MPiglet, Object> {
    //
    // Auto class visitors--probably don't need to be overridden.
    //
    public MPiglet visit(NodeList n, Object argu) { // ()
        MPiglet _ret = new MPiglet();
        int _count=0;
        for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            MPiglet elem = e.nextElement().accept(this,argu);
            _ret.add(elem)
            _count++;
        }
        return _ret;
    }

    public MPiglet visit(NodeListOptional n, Object argu) { //*
        MPiglet _ret = new MPiglet();
        if (n.present()) {
            int _count=0;
            for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
                MPiglet elem = e.nextElement().accept(this,argu);
                _ret.add(elem);
                _count++;
            }
            return _ret;
        }
        else
            return _ret;
    }

    public MPiglet visit(NodeOptional n, Object argu) { //?
        MPiglet _ret = new MPiglet();
        if (n.present())
            return n.node.accept(this,argu);
        else
            return _ret;
    }

    public MPiglet visit(NodeSequence n, Object argu) {
        MPiglet _ret = new MPiglet();
        int _count=0;
        for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            MPiglet elem = e.nextElement().accept(this,argu);
            _ret.add(elem);
            _count++;
        }
        return _ret;
    }

   public MPiglet visit(NodeToken n, Object argu) { return null; }

   //
   // User-generated visitor methods below
   //

    /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
    public MPiglet visit(Goal n, Object argu) {
        MPiglet _ret = new MPiglet();
        _ret.add(n.f0.accept(this, null));
        _ret.add(n.f1.accept(this, null));
        return _ret;
    }

    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> ( VarDeclaration() )*
    * f15 -> ( Statement() )*
    * f16 -> "}"
    * f17 -> "}"
    */
    public MPiglet visit(MainClass n, Object argu) {
        MPiglet _ret = new MPiglet("MAIN\n");
        MClass nclass = MClassList.getInstance().getClassByName(n.f1.f0.tokenImage);
        MMethod nmethod = nclass.getMethodByName("main");
        _ret.add(n.f14.accept(this, nmethod));
        _ret.add(n.f15.accept(this, nmethod));
        _ret.add("END")
        return _ret;
    }

    /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    public MPiglet visit(TypeDeclaration n, Object argu) {
        MPiglet _ret = new MPiglet();
        _ret.add(n.f0.accept(this, null));
        return _ret;
    }

    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
    public MPiglet visit(ClassDeclaration n, Object argu) {
        MPiglet _ret = new MPiglet();
        MClass nclass = MClassList.getInstance().getClassByName(n.f1.f0.tokenImage);
        _ret.add(n.f4.accept(this, nclass));
        return _ret;
    }

    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
    public MPiglet visit(ClassExtendsDeclaration n, Object argu) {
        MPiglet _ret = new MPiglet();
        MClass nclass = MClassList.getInstance().getClassByName(n.f1.f0.tokenImage);
        _ret.add(n.f6.accept(this, nclass));
        return _ret;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
    public MPiglet visit(VarDeclaration n, Object argu) {
        return null;
    }

    /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
    public MPiglet visit(MethodDeclaration n, Object argu) {
        MPiglet _ret = new MPiglet();
        MClass nclass = (MClass)argu;
        MMethod nmethod = nclass.getMethodByName(n.f2.f0.tokenImage);
        _ret.add(nmethod.getPigletDefinition());
        _ret.add("BEGIN\n");
        _ret.add(n.f8.accept(this, nmethod));
        _ret.add("RETURN\n")
        _ret.add(n.f10.accept(this, nmethod));
        _ret.add("END\n")
      return _ret;
   }

    /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
    public MPiglet visit(FormalParameterList n, Object argu) {
        return null;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    public MPiglet visit(FormalParameter n, Object argu) {
        return null;
    }

    /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    public MPiglet visit(FormalParameterRest n, Object argu) {
        return null;
    }

    /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    public MPiglet visit(Type n, Object argu) {
        return null;
    }

    /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    public MPiglet visit(ArrayType n, Object argu) {
        return null;
    }

    /**
    * f0 -> "boolean"
    */
    public MPiglet visit(BooleanType n, Object argu) {
        return null;
    }

    /**
    * f0 -> "int"
    */
    public MPiglet visit(IntegerType n, Object argu) {
        return null;
    }

    /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
    public MPiglet visit(Statement n, Object argu) {
        MPiglet _ret = new MPiglet();
        _ret.add(n.f0.accept(this, argu));
        return _ret;
    }

    /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
    public MPiglet visit(Block n, Object argu) {
        MPiglet _ret = new MPiglet();
        _ret.add(n.f1.accept(this, argu));
        return _ret;
    }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public MPiglet visit(AssignmentStatement n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet id = n.f0.accept(this, argu);
        MPiglet exp = n.f2.accept(this, argu);
        _ret.add("MOVE "+id.codeStr()+" "+exp.codeStr()+"\n");
        return _ret;
    }

    /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
    public MPiglet visit(ArrayAssignmentStatement n, Object argu) {
        MPiglet _ret = new MPiglet();
        MMethod nmethod = (MMethod)argu;
        MPiglet id = n.f0.accept(this, argu);
        MPiglet exp1 = n.f2.accept(this, argu);
        MPiglet exp2 = n.f5.accept(this, argu);
        _ret.add("HSTORE "+id.codeStr()+" "+exp1.codeStr()+" "+exp2.codeStr()+"\n")
        return _ret;
    }

    /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
    public MPiglet visit(IfStatement n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet exp1 = n.f2.accept(this, argu);
        MPiglet stmt1 = n.f4.accept(this, argu);
        MPiglet stmt2 = n.f6.accept(this, argu);
        String label1 = nextLabel();
        String label2 = nextLabel();

        _ret.add("CJUMP "+exp1.codeStr()+" "+label1+"\n");
        _ret.add(stmt1);
        _ret.add("JUMP "+label2+"\n");
        _ret.add(label1+"\n");
        _ret.add(stmt2);
        _ret.add(label2+" NOOP\n");
        return _ret;
    }

    /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
    public MPiglet visit(WhileStatement n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet exp1 = n.f2.accept(this, argu);
        MPiglet stmt1 = n.f4.accept(this, argu);
        String label1 = nextLabel();
        String label2 = nextLabel();

        _ret.add(label1+"\n");
        _ret.add("CJUMP "+exp1.codeStr()+" "+label2+"\n");
        _ret.add(stmt1);
        _ret.add("JUMP "+label1+"\n");
        _ret.add(label2+"\n");
        return _ret;

    }

    /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
    public MPiglet visit(PrintStatement n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet exp1 = n.f2.accept(this, argu);
        
        _ret.add("PRINT "+exp1.codeStr()+"\n");
        return _ret;
    }

    /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
    public MPiglet visit(Expression n, Object argu) {
        MPiglet _ret = new MPiglet();
        _ret.add(n.f0.accept(this, argu))
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
    public MPiglet visit(AndExpression n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet p1 = n.f0.accept(this, argu);
        MPiglet p2 = n.f2.accept(this, argu);
        _ret.add("TIMES "+p1.codeStr()+" "+p2.codeStr()+"\n");
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
    public MPiglet visit(CompareExpression n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet p1 = n.f0.accept(this, argu);
        MPiglet p2 = n.f2.accept(this, argu);
        _ret.add("LT "+p1.codeStr()+" "+p2.codeStr()+"\n");
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
    public MPiglet visit(PlusExpression n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet p1 = n.f0.accept(this, argu);
        MPiglet p2 = n.f2.accept(this, argu);
        _ret.add("PLUS "+p1.codeStr()+" "+p2.codeStr()+"\n");
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
    public MPiglet visit(MinusExpression n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet p1 = n.f0.accept(this, argu);
        MPiglet p2 = n.f2.accept(this, argu);
        _ret.add("MINUS "+p1.codeStr()+" "+p2.codeStr()+"\n");
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
    public MPiglet visit(TimesExpression n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet p1 = n.f0.accept(this, argu);
        MPiglet p2 = n.f2.accept(this, argu);
        _ret.add("TIMES "+p1.codeStr()+" "+p2.codeStr()+"\n");
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
    public MPiglet visit(ArrayLookup n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet p1 = n.f0.accept(this, argu);
        MPiglet p2 = n.f2.accept(this, argu);
        String t1 = nextTemp();
        String t2 = nextTemp();
        _ret.add("BEGIN\n");
        _ret.add("MOVE "+t1+" "+p1.codeStr()+"\n");
        _ret.add("HLOAD "+t2+" "+t1+" PLUS 4 TIMES 4 "+p2.codeStr()+"\n");
        _ret.add("RETURN "+t2+"\n");
        _ret.add("END\n");
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
    public MPiglet visit(ArrayLength n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet p1 = n.f0.accept(this, argu);
        String t1 = nextTemp();
        String t2 = nextTemp();
        _ret.add("BEGIN\n");
        _ret.add("MOVE "+t1+" "+p1.codeStr()+"\n");
        _ret.add("HLOAD "+t2+" "+t1+" 0\n");
        _ret.add("RETURN "+t2+"\n");
        _ret.add("END\n");
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
    public MPiglet visit(MessageSend n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet p1 = n.f0.accept(this, argu);
        MClass nclass = p1.getNclass();
        MMethod nmethod = nclass.getMethodByName(n.f2.f0.tokenImage);
        MPiglet exp1 = n.f4.accept(this, argu);
        _ret.add("CALL\n");
        _ret.add("BEGIN\n");
        _ret.add("MOVE "+t1+" "+p1.codeStr()+"\n");
        _ret.add("HLOAD "+t2+" "+t1+" 0\n");
        _ret.add("HLOAD "+t3+" "+t2+" "+nmethod.getOffset()+"\n");
        _ret.add("RETURN "+t3+"\n");
        _ret.add("END\n");
        _ret.add("( "+t1+" "+exp1.codeStr()+" )\n");
        return _ret;
    }

    /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
    public MPiglet visit(ExpressionList n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet exp1 = n.f0.accept(this, argu);
        MPiglet exp2 = n.f1.accept(this, argu);
        _ret.add(exp1);
        _ret.add(exp2);
        return _ret;
    }

    /**
    * f0 -> ","
    * f1 -> Expression()
    */
    public MPiglet visit(ExpressionRest n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet exp1 = n.f1.accept(this, argu);
        _ret.add(" ");
        _ret.add(exp1);
        return _ret;
    }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
    public MPiglet visit(PrimaryExpression n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet exp1 = n.f0.accept(this, argu);
        _ret.add(exp1);
        return _ret;
    }

    /**
    * f0 -> <INTEGER_LITERAL>
    */
    public MPiglet visit(IntegerLiteral n, Object argu) {
        MPiglet _ret = new MPiglet();
        _ret.add(n.f0.f0.tokenImage);
        return _ret;
    }

    /**
    * f0 -> "true"
    */
    public MPiglet visit(TrueLiteral n, Object argu) {
        MPiglet _ret = new MPiglet();
        _ret.add("1");
        return _ret;
    }

    /**
    * f0 -> "false"
    */
    public MPiglet visit(FalseLiteral n, Object argu) {
        MPiglet _ret = new MPiglet();
        _ret.add("0");
        return _ret;
    }

    /**
    * f0 -> <IDENTIFIER>
    */
    public MPiglet visit(Identifier n, Object argu) {
        MPiglet _ret = new MPiglet();
        MMethod nmethod = (MMethod)argu;
        MVar nvar = nmethod.getVarByName(n.f0.tokenImage);
        String t1 = nextTemp();
        if (nvar.getTempNum() == 0) //类变量
            _ret.add("BEGIN\n");
            _ret.add("MOVE "+t1+" PLUS 4 TIMES 4 "+nvar.getOffset()+"\n");
            _ret.add("MOVE "+t2+" PLUS TEMP0 "+t1+"\n");
            _ret.add("RETURN "+t2+"\n");
            _ret.add("END\n");
        else
            _ret.add("TEMP "+nvar.getTempNum());
        return _ret;
    }

    /**
    * f0 -> "this"
    */
    public MPiglet visit(ThisExpression n, Object argu) {
        MPiglet _ret = new MPiglet();
        _ret.add("TEMP 0");
        return _ret;
    }

    /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
    public MPiglet visit(ArrayAllocationExpression n, Object argu) {
        MPiglet _ret = new MPiglet();
        MMethod nmethod = (MMethod)argu;
        MPiglet exp = n.f3.accept(this, nmethod);
        String t1 = nextTemp();
        String t2 = nextTemp();
        String t3 = nextTemp();
        _ret.add("BEGIN\n");
        _ret.add("MOVE "+t1+" TIMES 4 "+exp.codeStr()+"\n");
        _ret.add("MOVE "+t2+" PLUS 4 "+t1+"\n");
        _ret.add("MOVE "+t3+" HALLOCATE "+t2+"\n");
        _ret.add("RETURN\n")
        _ret.add(t3+"\n")
        _ret.add("END\n")
        return _ret;
    }

    /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
    public MPiglet visit(AllocationExpression n, Object argu) {
        MPiglet _ret = new MPiglet();
        MClass nclass = MClassList.getInstance().getClassByName(n.f1.f0.tokenImage);
        HashSet<Mvar> varSet = nclass.getVarSet();
        HashSet<MMethod> methodSet = nclass.getMethodSet();
        String t1 = nextTemp();
        String t2 = nextTemp();

        _ret.add("BEGIN\n");
        _ret.add("MOVE "+t1+" HALLOCATE TIMES 4 "+methodSet.size()+"\n");
        for (MMethod nmethod: methodSet){
            String methodName = nclass.getName()+"_"+nmethod.getName();
            _ret.add("HSTORE "+t1+" "+nmethod.getOffset()+" "+methodName+"\n");
        }
        _ret.add("MOVE "+t2+" HALLOCATE PLUS 4 TIMES 4 "+varSet.size()+"\n");
        _ret.add("HSTORE "+t2+" 0 "+t1+"\n");
        for (Mvar nvar: varSet)
            _ret.add("HSTORE "+t2+" "+nvar.getOffset()+" 0\n");
        _ret.add("RETURN "+t2+"\n");
        _ret.add("END\n");
        return _ret;
    }

    /**
    * f0 -> "!"
    * f1 -> Expression()
    */
    public MPiglet visit(NotExpression n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet exp1 = n.f1.accept(this, argu);
        _ret.add("MINUS 1 "+exp1+"\n");
        return _ret;
    }

    /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
    public MPiglet visit(BracketExpression n, Object argu) {
        MPiglet _ret = new MPiglet();
        MPiglet exp1 = n.f1.accept(this, argu);
        _ret.add(exp1);
      return _ret;
    }

    // assistance
    public String nextTemp(){
        int num = MClassList.getInstance().nextTemp();
        return "TEMP " + num;
    }
    public String nextLabel(){
        int num = MClassList.getInstance().nextLable();
        return "L" + num;
    }

}
