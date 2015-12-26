package org.eclipse.emf.search.codegen.jet.templates.core;

import org.eclipse.emf.search.codegen.model.generator.*;

public class PluginXML
{
  protected static String nl;
  public static synchronized PluginXML create(String lineSeparator)
  {
    nl = lineSeparator;
    PluginXML result = new PluginXML();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL + "<?eclipse version=\"3.2\"?>" + NL + "" + NL + "<plugin>" + NL + "   <extension" + NL + "         point=\"org.eclipse.emf.search.modelSearchEngine\">" + NL + "      <modelSearchEngine" + NL + "            id=\"search.";
  protected final String TEXT_2 = "SearchEngine\"" + NL + "            label=\"";
  protected final String TEXT_3 = " Search Engine\"" + NL + "            modelResourceValidator=\"search.engine.ModelResourceValidator\"" + NL + "            queryParametersFactory=\"search.factories.ModelSearchQueryParametersFactory\"" + NL + "            searchQueryFactory=\"search.factories.ModelSearchQueryFactory\"/>" + NL + "   </extension>" + NL + "</plugin>";
  protected final String TEXT_4 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String modelName = settings.getGenModel().getModelName();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}
