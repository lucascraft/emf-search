package org.eclipse.emf.search.codegen.jet.templates.ui;

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
  protected final String TEXT_2 = ".search.ui;singleton:=true" + NL + "Bundle-Version: 1.0.0" + NL + "Bundle-Vendor: %providerName" + NL + "Bundle-Activator: search.ui.Activator" + NL + "Bundle-Localization: plugin" + NL + "Require-Bundle: org.eclipse.ui," + NL + " org.eclipse.core.runtime,";
  protected final String TEXT_3 = NL + " ";
  protected final String TEXT_4 = ",";
  protected final String TEXT_5 = "," + NL + " org.eclipse.emf.search," + NL + " org.eclipse.emf.search.ui,";
  protected final String TEXT_6 = ".search," + NL + " org.eclipse.emf.search.ecore.ui," + NL + " org.eclipse.emf.ecore.edit," + NL + " org.eclipse.emf.ecore.editor" + NL + "Bundle-ActivationPolicy: lazy" + NL + "Bundle-RequiredExecutionEnvironment: J2SE-1.5" + NL + "Export-Package: search.ui.areas," + NL + " search.ui.factories," + NL + " search.ui.l10n," + NL + " search.ui.factories," + NL + " search.ui.handlers";
  protected final String TEXT_7 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings= (ModelSearchGenSettings) argument;
	String modelPluginID = settings.getGenModel().getModelPluginID();
	String editPluginID = settings.getGenModel().getEditPluginID();
	String editorPluginID = settings.getGenModel().getEditorPluginID();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(modelPluginID);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(modelPluginID);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(editPluginID);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(editorPluginID);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(modelPluginID);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}
