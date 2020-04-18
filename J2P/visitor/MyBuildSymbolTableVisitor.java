package visitor;
import syntaxtree.*;
import java.util.*;
import symbol.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class MyBuildSymbolTableVisitor extends GJDepthFirst<MType, MType> {
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
      if ( n.present() ) {
         MType _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
    }

    public MType visit(NodeOptional n, MType argu) {
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

    public MType visit(NodeToken n, MType argu) { return null; }

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
      MClass nclass = new MClass(n.f1.f0.tokenImage, n.f1.f0.beginLine, n.f1.f0.beginColumn);
      if (!MClassList.getInstance().addClass(nclass)) return null;
      n.f2.accept(this, nclass);
      n.f3.accept(this, nclass);
      n.f4.accept(this, nclass);
      n.f5.accept(this, nclass);
      n.f6.accept(this, nclass);
      MMethod nmethod = new MMethod("main", "void", n.f6.beginLine, n.f6.beginColumn, nclass);
      if (!nclass.addMethod(nmethod)) return null;
      n.f7.accept(this, nmethod);
      n.f8.accept(this, nmethod);
      n.f9.accept(this, nmethod);
      n.f10.accept(this, nmethod);
      n.f11.accept(this, nmethod);
      MVar nparam = new MVar(n.f11.f0.tokenImage, "String[]", n.f11.f0.beginLine, n.f11.f0.beginColumn, nmethod, nclass);
      if (!nmethod.addParam(nparam)) return null;
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
      MClass nclass = new MClass(n.f1.f0.tokenImage, n.f1.f0.beginLine, n.f1.f0.beginColumn);
      if (!MClassList.getInstance().addClass(nclass)) return null;
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
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MClass nclass = new MClass(n.f1.f0.tokenImage, n.f1.f0.beginLine, n.f1.f0.beginColumn);
      if (!MClassList.getInstance().addClass(nclass)) return null;
      n.f2.accept(this, nclass);
      n.f3.accept(this, nclass);
      nclass.setFatherName(n.f3.f0.tokenImage);
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
      MType _ret=null;
      MType type = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      if (argu instanceof MMethod){
        MMethod nmethod = (MMethod)argu;
        MVar nvar = new MVar(n.f1.f0.tokenImage, type.getTypeName(), n.f1.f0.beginLine, n.f1.f0.beginColumn, nmethod, nmethod.getMyClass());
        if (!nmethod.addVar(nvar)) return null;
      }
      else{
        MClass nclass = (MClass)argu;
        MVar nvar = new MVar(n.f1.f0.tokenImage, type.getTypeName(), n.f1.f0.beginLine, n.f1.f0.beginColumn, null, nclass);
        if (!nclass.addVar(nvar)) return null;
      }
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
      MType _ret=null;
      n.f0.accept(this, argu);
      MType type = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      MClass nclass = (MClass)argu;
      MMethod nmethod = new MMethod(n.f2.f0.tokenImage, type.getTypeName(), n.f2.f0.beginLine, n.f2.f0.beginColumn, nclass);
      if (!nclass.addMethod(nmethod)) return null;
      n.f3.accept(this, nmethod);
      n.f4.accept(this, nmethod);
      n.f5.accept(this, nmethod);
      n.f6.accept(this, nmethod);
      n.f7.accept(this, nmethod);
      n.f8.accept(this, nmethod);
      n.f9.accept(this, nmethod);
      n.f10.accept(this, nmethod);
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
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    public MType visit(FormalParameter n, MType argu) {// argu: MMethod
      MType _ret=null;
      MType type = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MMethod nmethod = (MMethod)argu;
      MVar nparam = new MVar(n.f1.f0.tokenImage, type.getTypeName(), n.f1.f0.beginLine, n.f1.f0.beginColumn, nmethod, nmethod.getMyClass());
      if (!nmethod.addParam(nparam)) return null;
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
    public MType visit(AssignmentStatement n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
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
    public MType visit(ArrayAssignmentStatement n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
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
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
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
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
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
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
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
      MType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
    public MType visit(AndExpression n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
    public MType visit(CompareExpression n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
    public MType visit(PlusExpression n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
    public MType visit(MinusExpression n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
    public MType visit(TimesExpression n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
    public MType visit(ArrayLookup n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
    public MType visit(ArrayLength n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
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
    public MType visit(MessageSend n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
    public MType visit(ExpressionList n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> ","
    * f1 -> Expression()
    */
    public MType visit(ExpressionRest n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
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
    public MType visit(PrimaryExpression n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> <INTEGER_LITERAL>
    */
    public MType visit(IntegerLiteral n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> "true"
    */
    public MType visit(TrueLiteral n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> "false"
    */
    public MType visit(FalseLiteral n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> <IDENTIFIER>
    */
    public MType visit(Identifier n, MType argu) {
    // 为Type 传递类名
      MType _ret=null;
      n.f0.accept(this, argu);
      MType type = new MType(null, n.f0.tokenImage, n.f0.beginLine, n.f0.beginColumn);
      return type;
    }

    /**
    * f0 -> "this"
    */
    public MType visit(ThisExpression n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
    public MType visit(ArrayAllocationExpression n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
    public MType visit(AllocationExpression n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> "!"
    * f1 -> Expression()
    */
    public MType visit(NotExpression n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
    }

    /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
    public MType visit(BracketExpression n, MType argu) {
      MType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
    }

}
