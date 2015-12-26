package org.eclipse.emf.search.codegen.jet.templates.core;

import org.eclipse.emf.search.codegen.model.generator.*;

public class PluginProperties
{
  protected static String nl;
  public static synchronized PluginProperties create(String lineSeparator)
  {
    nl = lineSeparator;
    PluginProperties result = new PluginProperties();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "pluginName=";
  protected final String TEXT_2 = " Model Search Core Plug-in " + NL + "providerName=Eclipse.org";
  protected final String TEXT_3 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String modelName = settings.getGenModel().getModelName();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    return stringBuffer.toString();
  }
}
