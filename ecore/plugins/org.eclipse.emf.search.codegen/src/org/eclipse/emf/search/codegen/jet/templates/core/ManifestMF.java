package org.eclipse.emf.search.codegen.jet.templates.core;

import org.eclipse.emf.search.codegen.model.generator.*;

public class ManifestMF
{
  protected static String nl;
  public static synchronized ManifestMF create(String lineSeparator)
  {
    nl = lineSeparator;
    ManifestMF result = new ManifestMF();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "Manifest-Version: 1.0" + NL + "Bundle-ManifestVersion: 2" + NL + "Bundle-Name: %pluginName" + NL + "Bundle-SymbolicName: ";
  protected final String TEXT_2 = ".search;singleton:=true" + NL + "Bundle-Version: 1.0.0" + NL + "Bundle-Vendor: %providerName" + NL + "Bundle-Activator: search.Activator" + NL + "Bundle-Localization: plugin" + NL + "Require-Bundle: org.eclipse.core.runtime," + NL + " org.eclipse.core.resources," + NL + " org.eclipse.emf.ecore," + NL + " org.eclipse.emf.search," + NL + " org.eclipse.emf.search.common," + NL + " org.eclipse.emf.search.ecore,";
  protected final String TEXT_3 = NL + " ";
  protected final String TEXT_4 = ",";
  protected final String TEXT_5 = "," + NL + " org.eclipse.swt" + NL + "Bundle-ActivationPolicy: lazy" + NL + "Bundle-RequiredExecutionEnvironment: J2SE-1.5" + NL + "Export-Package: search," + NL + " search.helper.builder," + NL + " search.helper.launcher," + NL + " search.factories," + NL + " search.l10n," + NL + " search.engine," + NL + " search.replace," + NL + " search.evaluators," + NL + " search.util";
  protected final String TEXT_6 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings= (ModelSearchGenSettings) argument;
	String modelPluginID = settings.getGenModel().getModelPluginID();
	String editPluginID = settings.getGenModel().getEditPluginID();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(modelPluginID);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(modelPluginID);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(editPluginID);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    return stringBuffer.toString();
  }
}
