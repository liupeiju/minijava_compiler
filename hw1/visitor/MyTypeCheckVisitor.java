package visitor;
import syntaxtree.*;
import java.util.*;
import symbol.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class MyTypeCheckVisitor extends GJDepthFirst<MType, MType> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
    public MType visit(NodeList n, MType argu) {
      MType _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
    }

    public MType visit(NodeListOptional n, MType argu) {
    // modified for (ExpressionRest)*
      if ( n.present() ) {
         MTypeList types = new MTypeList();
         MType _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            MType exp = e.nextElement().accept(this,argu);
            types.addType(exp);
            _count++;
         }
         return types;
      }
      else
         return null;
    }

    public MType visit(NodeOptional n, MType argu) {
    // (ExpressionList)?
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
    }

    public MType visit(NodeSequence n, MType argu) {
      MType _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
    }

    public MType visit(NodeToken n, MType argu) { 
      //System.out.println(n.tokenImage);
      return null; 
    }

    //
    // User-generated visitor methods below
    //

    /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
    public MType visit(Goal n, MType argu) {// argu: null
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
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
    public MType visit(MainClass n, MType argu) {// argu: null
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MClass nclass = MClassList.getInstance().getClassByName(n.f1.f0.tokenImage);
      n.f2.accept(this, nclass);
      n.f3.accept(this, nclass);
      n.f4.accept(this, nclass);
      n.f5.accept(this, nclass);
      n.f6.accept(this, nclass);
      MMethod nmethod = nclass.getMethodByName(n.f6.tokenImage);
      n.f7.accept(this, nmethod);
      n.f8.accept(this, nmethod);
      n.f9.accept(this, nmethod);
      n.f10.accept(this, nmethod);
      n.f11.accept(this, nmethod);
      n.f12.accept(this, nmethod);
      n.f13.accept(this, nmethod);
      n.f14.accept(this, nmethod);
      n.f15.accept(this, nmethod);
      n.f16.accept(this, nmethod);
      n.f17.accept(this, nclass);
      return _ret;
    }

    /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    public MType visit(TypeDeclaration n, MType argu) {// argu: null
      MType _ret=null;
      n.f0.accept(this, argu);
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
    public MType visit(ClassDeclaration n, MType argu) {// argu: null
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MClass nclass = MClassList.getInstance().getClassByName(n.f1.f0.tokenImage);
      n.f2.accept(this, nclass);
      n.f3.accept(this, nclass);
      n.f4.accept(this, nclass);
      n.f5.accept(this, nclass);
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
    public MType visit(ClassExtendsDeclaration n, MType argu) {// argu: null
    // 父类的存在和类的循环
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MClass nclass = MClassList.getInstance().getClassByName(n.f1.f0.tokenImage); 
      n.f2.accept(this, nclass);
      n.f3.accept(this, nclass);
      if (!MClassList.getInstance().checkExtend(nclass)) return null;
      n.f4.accept(this, nclass);
      n.f5.accept(this, nclass);
      n.f6.accept(this, nclass);
      n.f7.accept(this, nclass);
      return _ret;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
    public MType visit(VarDeclaration n, MType argu) { //argu: MMethod or MClass
    // 检查type 是否定义
      MType _ret = null;
      MType type = n.f0.accept(this, argu);
      if (MClassList.getInstance().getClassByName(type.getTypeName()) == null){
        MErrorPrinter.getInstance().addError(type.getTypeName(), type.getLine(), type.getCol(), "type undefined");
        return null;
      }
      n.f1.accept(this, argu);
      return _ret;
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
    public MType visit(MethodDeclaration n, MType argu) {// argu: MClass
    // 返回类型匹配, 检查method是否重载
      MType _ret=null;
      n.f0.accept(this, argu);
      // Type()要返回一个MType
      MType type = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      MClass nclass = (MClass)argu;
      MMethod nmethod = nclass.getMethodByName(n.f2.f0.tokenImage);
      if (!nclass.checkOverload(nmethod)){
        MErrorPrinter.getInstance().addError(n.f2.f0.tokenImage, n.f2.f0.beginLine, n.f2.f0.beginColumn, "method overloaded");
        return null;
      }
      n.f3.accept(this, nmethod);
      n.f4.accept(this, nmethod);
      n.f5.accept(this, nmethod);
      n.f6.accept(this, nmethod);
      n.f7.accept(this, nmethod);
      n.f8.accept(this, nmethod);
      n.f9.accept(this, nmethod);
      MType exp = n.f10.accept(this, nmethod);
      if (exp == null) return null;
      // 返回和类型匹配
      if (!MClassList.getInstance().checkTypeMatch(type.getTypeName(), exp.getTypeName())){
        MErrorPrinter.getInstance().addError(exp.getName(), exp.getLine(), exp.getCol(), "return type unmatched");
        return null;
      }
      n.f11.accept(this, nmethod);
      n.f12.accept(this, nmethod);
      return _ret;
    }

    /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
    public MType visit(FormalParameterList n, MType argu) {// argu: MMethod
      MType _ret=null;
      n.f1.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    public MType visit(FormalParameter n, MType argu) {// argu: MMethod
    // 检查type未定义
      MType _ret=null;
      MType type = n.f0.accept(this, argu);
      if (MClassList.getInstance().getClassByName(type.getTypeName()) == null){
        MErrorPrinter.getInstance().addError(type.getTypeName(), type.getLine(), type.getCol(), "type undefined");
        return null;
      }
      n.f1.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    public MType visit(FormalParameterRest n, MType argu) {// argu: MMethod
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    public MType visit(Type n, MType argu) {
    //传递
      MType _ret=null;
      MType type = n.f0.accept(this, argu);
      return type;
    }

    /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    public MType visit(ArrayType n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      MType type = new MType(null, "int[]", n.f0.beginLine, n.f0.beginColumn);
      return type;
    }

    /**
    * f0 -> "boolean"
    */
    public MType visit(BooleanType n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      MType type = new MType(null, "boolean", n.f0.beginLine, n.f0.beginColumn);
      return type;
    }

    /**
    * f0 -> "int"
    */
    public MType visit(IntegerType n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      MType type = new MType(null, "int", n.f0.beginLine, n.f0.beginColumn);
      return type;
    }

    /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
    public MType visit(Statement n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
    public MType visit(Block n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
    public MType visit(AssignmentStatement n, MType argu) { // argu: MMethod
    // id已定义，类型匹配
      MType _ret=null;
      n.f0.accept(this, argu);
      MMethod nmethod = (MMethod)argu;
      MVar nvar = nmethod.getVarByName(n.f0.f0.tokenImage);
      if (nvar == null){
        MErrorPrinter.getInstance().addError(n.f0.f0.tokenImage, n.f0.f0.beginLine, n.f0.f0.beginColumn, "var undefined");
        return null;
      }
      n.f1.accept(this, argu);
      MType exp = n.f2.accept(this, argu);
      if (exp == null) return null;
      if (!MClassList.getInstance().checkTypeMatch(nvar.getTypeName(), exp.getTypeName())){// 检查赋值的类型的匹配
        MErrorPrinter.getInstance().addError(exp.getName(), exp.getLine(), exp.getCol(), "type unmatched");
        return null;
      }
      n.f3.accept(this, argu);
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
    public MType visit(ArrayAssignmentStatement n, MType argu) { //MMethod
    // id已定义, id是int[]类型， exp1是int型，exp2是int型
      MType _ret=null;
      MMethod nmethod = (MMethod)argu;
      n.f0.accept(this, nmethod);
      MVar nvar = nmethod.getVarByName(n.f0.f0.tokenImage);
      if (nvar == null){
        MErrorPrinter.getInstance().addError(n.f0.f0.tokenImage, n.f0.f0.beginLine, n.f0.f0.beginColumn, "var undefined");
        return null;       
      }
      if (nvar.getTypeName() != "int[]"){
        MErrorPrinter.getInstance().addError(n.f0.f0.tokenImage, n.f0.f0.beginLine, n.f0.f0.beginColumn, "var type error");
        return null;
      }
      n.f1.accept(this, nmethod);
      MType exp1 = n.f2.accept(this, nmethod);
      if (exp1 == null) return null;
      if (exp1.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(exp1.getName(), exp1.getLine(), exp1.getCol(), "exp type error");
        return null;
      }
      n.f3.accept(this, nmethod);
      n.f4.accept(this, nmethod);
      MType exp2 = n.f5.accept(this, nmethod);
      if (exp2 == null) return null;
      if (exp2.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(exp2.getName(), exp2.getLine(), exp2.getCol(), "exp type error");
        return null;
      }
      n.f6.accept(this, nmethod);
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
    public MType visit(IfStatement n, MType argu) {
    // exp类型为boolean
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MType exp = n.f2.accept(this, argu);
      if (exp == null) return null;
      if (exp.getTypeName() != "boolean"){
        MErrorPrinter.getInstance().addError(exp.getName(), exp.getLine(), exp.getCol(), "exp type error");
        return null;
      }
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
    public MType visit(WhileStatement n, MType argu) {
    // exp类型为boolean
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MType exp = n.f2.accept(this, argu);
      if (exp == null) return null;
      if (exp.getTypeName() != "boolean"){
        MErrorPrinter.getInstance().addError(exp.getName(), exp.getLine(), exp.getCol(), "exp type error");
        return null;
      }
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
    public MType visit(PrintStatement n, MType argu) {
      // exp类型为int
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MType exp = n.f2.accept(this, argu);
      if (exp == null) return null;
      if (exp.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(exp.getName(), exp.getLine(), exp.getCol(), "exp type error");
        return null;
      }
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
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
    public MType visit(Expression n, MType argu) {
    // 返回一个MType
      MType _ret=null;
      MType exp = n.f0.accept(this, argu);
      return exp;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
    public MType visit(AndExpression n, MType argu) {
    // p0, p2是boolean型，返回boolean型MType

      MType _ret=null;
      MType p0 = n.f0.accept(this, argu);
      if (p0 == null) return null;
      n.f1.accept(this, argu);
      MType p2 = n.f2.accept(this, argu);
      if (p2 == null) return null;
      if (p0.getTypeName() != "boolean"){
        MErrorPrinter.getInstance().addError(p0.getName(), p0.getLine(), p0.getCol(), "prime type error");
        return null;
      }
      if (p2.getTypeName() != "boolean"){
        MErrorPrinter.getInstance().addError(p2.getName(), p2.getLine(), p2.getCol(), "prime type error");
        return null;
      }
      MType exp = new MType(null, "boolean", p0.getLine(), p0.getCol());
      return exp;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
    public MType visit(CompareExpression n, MType argu) {
    // p0, p2是int型，返回boolean型MType
      MType _ret=null;
      MType p0 = n.f0.accept(this, argu);
      if (p0 == null) return null;
      n.f1.accept(this, argu);
      MType p2 = n.f2.accept(this, argu);
      if (p2 == null) return null;
      if (p0.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(p0.getName(), p0.getLine(), p0.getCol(), "prime type error");
        return null;
      }
      if (p2.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(p2.getName(), p2.getLine(), p2.getCol(), "prime type error");
        return null;
      }
      MType exp = new MType(null, "boolean", p0.getLine(), p0.getCol());
      return exp;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
    public MType visit(PlusExpression n, MType argu) {
    // p0, p2是int型，返回int型MType
      MType _ret=null;
      MType p0 = n.f0.accept(this, argu);
      if (p0 == null) return null;
      n.f1.accept(this, argu);
      MType p2 = n.f2.accept(this, argu);
      if (p2 == null) return null;
      if (p0.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(p0.getName(), p0.getLine(), p0.getCol(), "prime type error");
        return null;
      }
      if (p2.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(p2.getName(), p2.getLine(), p2.getCol(), "prime type error");
        return null;
      }
      MType exp = new MType(null, "int", p0.getLine(), p0.getCol());
      return exp;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
    public MType visit(MinusExpression n, MType argu) {
    // p0, p2是int型, 返回int型MType
      MType _ret=null;
      MType p0 = n.f0.accept(this, argu);
      if (p0 == null) return null;
      n.f1.accept(this, argu);
      MType p2 = n.f2.accept(this, argu);
      if (p2 == null) return null;
      if (p0.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(p0.getName(), p0.getLine(), p0.getCol(), "prime type error");
        return null;
      }
      if (p2.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(p2.getName(), p2.getLine(), p2.getCol(), "prime type error");
        return null;
      }
      MType exp = new MType(null, "int", p0.getLine(), p0.getCol());
      return exp;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
    public MType visit(TimesExpression n, MType argu) {
    // p0, p2是int型, 返回int型MType
      MType _ret=null;
      MType p0 = n.f0.accept(this, argu);
      if (p0 == null) return null;
      n.f1.accept(this, argu);
      MType p2 = n.f2.accept(this, argu);
      if (p2 == null) return null;
      if (p0.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(p0.getName(), p0.getLine(), p0.getCol(), "prime type error");
        return null;
      }
      if (p2.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(p2.getName(), p2.getLine(), p2.getCol(), "prime type error");
        return null;
      }
      MType exp = new MType(null, "int", p0.getLine(), p0.getCol());
      return exp;
    }


    /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
    public MType visit(ArrayLookup n, MType argu) {
    // p0 id已定义，p0是int[]型, p2是int型，返回int型MType
      MType _ret=null;
      MType p0 = n.f0.accept(this, argu);
      if (p0 == null) return null;
      MMethod nmethod = (MMethod)argu;
      if (p0.getName() != null){// new int[]
        if (nmethod.getVarByName(p0.getName()) == null){
          MErrorPrinter.getInstance().addError(p0.getName(), p0.getLine(), p0.getCol(), "prime undefined");
          return null;
        }
      }
      if (p0.getTypeName() != "int[]"){
        MErrorPrinter.getInstance().addError(p0.getName(), p0.getLine(), p0.getCol(), "prime type error");
        return null;
      }
      n.f1.accept(this, argu);
      MType p2 = n.f2.accept(this, argu);
      if (p2 == null) return null;
      if (p2.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(p2.getName(), p2.getLine(), p2.getCol(), "prime type error");
        return null;
      }
      n.f3.accept(this, argu);
      MType exp = new MType(null, "int", p0.getLine(), p0.getCol());
      return exp;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
    public MType visit(ArrayLength n, MType argu) {
    // p0 id已定义，p0是int[]型，返回int型MType
      MType _ret=null;
      MType p0 = n.f0.accept(this, argu);
      if (p0 == null) return null;
      MMethod nmethod = (MMethod)argu;
      if (p0.getName() != null){// new int[]
        if (nmethod.getVarByName(p0.getName()) == null){
          MErrorPrinter.getInstance().addError(p0.getName(), p0.getLine(), p0.getCol(), "prime undefined");
          return null;
        }
      }
      if (p0.getTypeName() != "int[]"){
        MErrorPrinter.getInstance().addError(p0.getName(), p0.getLine(), p0.getCol(), "prime type error");
        return null;
      }
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      MType exp = new MType(null, "int", p0.getLine(), p0.getCol());
      return exp;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
    public MType visit(MessageSend n, MType argu) {// argu: MMethod
    // p0 已定义; p0的类中存在方法id; 参数类型匹配; 生成返回值
      MType _ret=null;
      MType p0 = n.f0.accept(this, argu);
      if (p0 == null) return null;
      MMethod nmethod = (MMethod)argu;
      if (p0.getName() != null){// new id()
        if(nmethod.getVarByName(p0.getName()) == null){
          MErrorPrinter.getInstance().addError(p0.getName(), p0.getLine(), p0.getCol(), "prime undefined");
          return null;
        }
      }
      MClass nclass = MClassList.getInstance().getClassByName(p0.getTypeName());
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      MMethod call_method = nclass.getMethodByName(n.f2.f0.tokenImage);
      if (call_method == null){
        MErrorPrinter.getInstance().addError(n.f2.f0.tokenImage, n.f2.f0.beginLine, n.f2.f0.beginColumn, "method not found");
        return null;
      }
      n.f3.accept(this, argu);
      MTypeList types = (MTypeList)n.f4.accept(this, nmethod);
      if (types != null){
        if (!call_method.checkListTypeMatch(types.getList())){
          MErrorPrinter.getInstance().addError(n.f3.tokenImage, n.f3.beginLine, n.f3.beginColumn, "argument unmatched");
          return null;
        }
      }
      n.f5.accept(this, argu);
      MType exp = new MType(null, call_method.getTypeName(), n.f1.beginLine, n.f1.beginColumn);
      return exp;
    }

    /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
    public MType visit(ExpressionList n, MType argu){ // argu: MMethod
    //exp id已定义，合并exp, typelist
      MType _ret=null;
      MMethod nmethod = (MMethod)argu;
      MTypeList types = new MTypeList();
      MType exp = n.f0.accept(this, argu);
      if (exp == null) return null;
      if (exp.getName() != null){
        if (nmethod.getVarByName(exp.getName()) == null){
          MErrorPrinter.getInstance().addError(exp.getName(), exp.getLine(), exp.getCol(), "var undefined");
          return null;
        }
      }
      types.addType(exp);
      MTypeList typelist = (MTypeList)n.f1.accept(this, argu);
      if (typelist != null) 
        types.mergeList(typelist);
      return types;
    }

    /**
    * f0 -> ","
    * f1 -> Expression()
    */
    public MType visit(ExpressionRest n, MType argu) {
    // exp id已定义，返回Mtype
      MType _ret=null;
      n.f0.accept(this, argu);
      MMethod nmethod = (MMethod)argu;
      MType exp = n.f1.accept(this, argu);
      if (exp == null) return null;
      if (exp.getName() != null){ //常值
        if (nmethod.getVarByName(exp.getName()) == null){
          MErrorPrinter.getInstance().addError(exp.getName(), exp.getLine(), exp.getCol(), "var undefined");
          return null;
        }
      }
      return exp;
      }

    /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier() //**** notice
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
    public MType visit(PrimaryExpression n, MType argu) { // MMethod
      MType _ret=null;
      MType exp = n.f0.accept(this, argu);
      if (exp == null) return null;
      // Identifier类型未知, 在这里补充.
      if (exp.getName() == exp.getTypeName()){ //来自id
      // 检查id已定义, 增加TypeName
        MMethod nmethod = (MMethod)argu;
        MVar nvar = nmethod.getVarByName(exp.getName());
        if (nvar == null){
          MErrorPrinter.getInstance().addError(exp.getName(), exp.getLine(), exp.getCol(), "var undefined");
          return null;
        }
        MType exp_new = new MType(exp.getName(), nvar.getTypeName(), exp.getLine(), exp.getCol());
        return exp_new;
      }
      return exp;
    }

    /**
    * f0 -> <INTEGER_LITERAL>
    */
    public MType visit(IntegerLiteral n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      MType p = new MType(null, "int", n.f0.beginLine, n.f0.beginColumn);
      return p;
    }

    /**
    * f0 -> "true"
    */
    public MType visit(TrueLiteral n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      MType p = new MType(null, "boolean", n.f0.beginLine, n.f0.beginColumn);
      return p;
    }

    /**
    * f0 -> "false"
    */
    public MType visit(FalseLiteral n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      MType p = new MType(null, "boolean", n.f0.beginLine, n.f0.beginColumn);
      return p;
    }

    /**
    * f0 -> <IDENTIFIER>
    */
    public MType visit(Identifier n, MType argu) {
    // Type()中，作为类名; Primary中,作为变量名. 此处不作区分
      MType _ret=null;
      n.f0.accept(this, argu);
      MType type = new MType(n.f0.tokenImage, n.f0.tokenImage, n.f0.beginLine, n.f0.beginColumn);
      return type;
    }

    /**
    * f0 -> "this"
    */
    public MType visit(ThisExpression n, MType argu) { //Mmethod
    // 返回MType, TypeName为环境中的类
      MType _ret=null;
      n.f0.accept(this, argu);
      MMethod nmethod = (MMethod)argu;
      MType type = new MType(null, nmethod.getMyClass().getName(), n.f0.beginLine, n.f0.beginColumn);
      return type;
    }

    /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
    public MType visit(ArrayAllocationExpression n, MType argu) {

    // exp类型为int, 返回MType, 类型为int[]
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      MType exp = n.f3.accept(this, argu);
      if (exp == null) return null;
      if (exp.getTypeName() != "int"){
        MErrorPrinter.getInstance().addError(exp.getName(), exp.getLine(), exp.getCol(), "exp type error");
        return null;
      }
      n.f4.accept(this, argu);
      MType type = new MType(null, "int[]", n.f0.beginLine, n.f0.beginColumn);
      return type;
    }

    /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
    public MType visit(AllocationExpression n, MType argu) {
    // id是类名已定义, 返回MType, 类型为类名
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      if (MClassList.getInstance().getClassByName(n.f1.f0.tokenImage) == null){
        MErrorPrinter.getInstance().addError(n.f1.f0.tokenImage, n.f1.f0.beginLine, n.f1.f0.beginColumn, "type undefined");
        return null;
      }
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      MType type = new MType(null, n.f1.f0.tokenImage, n.f0.beginLine, n.f0.beginColumn);
      return type;
    }

    /**
    * f0 -> "!"
    * f1 -> Expression()
    */
    public MType visit(NotExpression n, MType argu) {
    // exp类型为boolean, 返回MType, 类型为boolean
      MType _ret=null;
      n.f0.accept(this, argu);
      MType exp = n.f1.accept(this, argu);
      if (exp == null) return null;
      if (exp.getTypeName() != "boolean"){
        MErrorPrinter.getInstance().addError(exp.getName(), exp.getLine(), exp.getCol(), "type error");
        return null;
      }
      MType type = new MType(null, "boolean", n.f0.beginLine, n.f0.beginColumn);
      return type;
    }

    /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
    public MType visit(BracketExpression n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      MType exp = n.f1.accept(this, argu);
      if (exp == null) return null;
      n.f2.accept(this, argu);
      return exp;
    }

}
