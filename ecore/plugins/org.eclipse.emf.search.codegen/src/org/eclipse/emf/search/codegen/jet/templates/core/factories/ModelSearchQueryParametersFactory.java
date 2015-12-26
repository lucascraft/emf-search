package org.eclipse.emf.search.codegen.jet.templates.core.factories;

import org.eclipse.emf.search.codegen.model.generator.*;

public class ModelSearchQueryParametersFactory
{
  protected static String nl;
  public static synchronized ModelSearchQueryParametersFactory create(String lineSeparator)
  {
    nl = lineSeparator;
    ModelSearchQueryParametersFactory result = new ModelSearchQueryParametersFactory();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.factories;" + NL + "" + NL + "import org.eclipse.emf.search.core.factories.IModelSearchQueryParametersFactory;" + NL + "import org.eclipse.emf.search.core.parameters.AbstractModelSearchQueryParameters;" + NL + "import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;" + NL + "" + NL + "/**" + NL + " * Wraps ModelSearchQueryParameters creation." + NL + " */" + NL + "public class ModelSearchQueryParametersFactory implements IModelSearchQueryParametersFactory {" + NL + "\tprivate static ModelSearchQueryParametersFactory instance;" + NL + "\t" + NL + "\tpublic ModelSearchQueryParametersFactory() {}" + NL + "\t" + NL + "\tpublic static ModelSearchQueryParametersFactory getInstance() {" + NL + "\t\treturn instance == null ? instance = new ModelSearchQueryParametersFactory() : instance;" + NL + "\t}" + NL + "\t" + NL + "\tprotected final class ModelSearchQueryParameters extends AbstractModelSearchQueryParameters {" + NL + "\t\tpublic String getModelSearchEngineID() {" + NL + "\t\t\treturn \"search.";
  protected final String TEXT_2 = "SearchEngine\"; //$NON-NLS-1$" + NL + "\t\t}\t" + NL + "\t}" + NL + "\t" + NL + "\tpublic IModelSearchQueryParameters createSearchQueryParameters() {" + NL + "\t\treturn new ModelSearchQueryParameters();" + NL + "\t}" + NL + "}";
  protected final String TEXT_3 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String basePackage = settings.getGenModel().getModelPluginPackageName();
	String modelName = settings.getGenModel().getModelName();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    return stringBuffer.toString();
  }
}
