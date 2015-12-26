package org.eclipse.emf.search.codegen.jet.templates.core.factories;

import org.eclipse.emf.search.codegen.model.generator.*;

public class ModelSearchQueryFactory
{
  protected static String nl;
  public static synchronized ModelSearchQueryFactory create(String lineSeparator)
  {
    nl = lineSeparator;
    ModelSearchQueryFactory result = new ModelSearchQueryFactory();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.factories;" + NL + "" + NL + "import org.eclipse.emf.search.core.engine.AbstractModelSearchQuery;" + NL + "import org.eclipse.emf.search.core.factories.IModelSearchQueryFactory;" + NL + "import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;" + NL + "import search.engine.ModelSearchQuery;" + NL + "" + NL + "/**" + NL + " * Wraps ModelSearchQuery creation." + NL + " */" + NL + "public class ModelSearchQueryFactory implements IModelSearchQueryFactory {" + NL + "\tprivate static ModelSearchQueryFactory instance;" + NL + "\t" + NL + "\tpublic ModelSearchQueryFactory() {}" + NL + "\t" + NL + "\tpublic static ModelSearchQueryFactory getInstance() {" + NL + "\t\treturn instance == null ? instance = new ModelSearchQueryFactory() : instance;" + NL + "\t}" + NL + "\t" + NL + "\tpublic AbstractModelSearchQuery createModelSearchQuery(String expr, IModelSearchQueryParameters p) {" + NL + "\t\treturn new ModelSearchQuery(expr, p);" + NL + "\t}" + NL + "}";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String basePackage = settings.getGenModel().getModelPluginPackageName();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
