package org.eclipse.emf.search.codegen.jet.templates.core.util;

public class ModelSearchDirectoryScopeFactory
{
  protected static String nl;
  public static synchronized ModelSearchDirectoryScopeFactory create(String lineSeparator)
  {
    nl = lineSeparator;
    ModelSearchDirectoryScopeFactory result = new ModelSearchDirectoryScopeFactory();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.util;" + NL + "" + NL + "import org.eclipse.emf.ecore.resource.Resource;" + NL + "import org.eclipse.emf.search.core.scope.IModelSearchScope;" + NL + "import org.eclipse.emf.search.ecore.scope.file.EcoreModelSearchDirectoryScopeFactory;" + NL + "import org.eclipse.emf.search.ecore.scope.file.EcoreModelSearchScopeFileSystemVisitor;" + NL + "" + NL + "" + NL + "public class ModelSearchDirectoryScopeFactory extends" + NL + "\t\tEcoreModelSearchDirectoryScopeFactory {" + NL + "" + NL + "\t// shared ModelSearchScopeFactory instance" + NL + "\tprivate static ModelSearchDirectoryScopeFactory instance;" + NL + "\t" + NL + "\t/**" + NL + "\t * Singleton access to the ModelSearchDirectoryScopeFactory instance." + NL + "\t * " + NL + "\t * @return New ModelSearchDirectoryScopeFactory instance or previously created one" + NL + "\t */" + NL + "\tpublic static ModelSearchDirectoryScopeFactory getInstance() {" + NL + "\t\treturn instance==null?instance=new ModelSearchDirectoryScopeFactory():instance;" + NL + "\t}" + NL + "" + NL + "\t@Override" + NL + "\tprotected EcoreModelSearchScopeFileSystemVisitor getModelSearchFileSystemVisitor(" + NL + "\t\t\tIModelSearchScope<Object, Resource> scope, String engineID) {" + NL + "\t\treturn new ModelSearchScopeFileSystemVisitor(scope, engineID);" + NL + "\t}" + NL + "}";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
