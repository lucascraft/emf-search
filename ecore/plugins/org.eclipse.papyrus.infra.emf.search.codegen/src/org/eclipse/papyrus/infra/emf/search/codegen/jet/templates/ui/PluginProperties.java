package org.eclipse.papyrus.infra.emf.search.codegen.jet.templates.ui;

import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.*;
import java.util.List;
import org.eclipse.emf.codegen.ecore.genmodel.*;

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
  protected final String TEXT_1 = "  ";
  protected final String TEXT_2 = NL + NL + "ModelSearchParticipantArea.";
  protected final String TEXT_3 = "ModelSearchPage.Label=";
  protected final String TEXT_4 = " Search" + NL + "" + NL + "ModelSearchParticipantArea.";
  protected final String TEXT_5 = "PlainTextSearchExtPoint.Label=Text" + NL;
  protected final String TEXT_6 = NL + "ModelSearchParticipantArea.";
  protected final String TEXT_7 = "=";
  protected final String TEXT_8 = "  Meta Elements Search Participants: " + NL + "ModelSearchParticipantArea.";
  protected final String TEXT_9 = "ParticipantTab.Tooltip=";
  protected final String TEXT_10 = " Meta Elements Participants" + NL + "ModelSearchParticipantArea.";
  protected final String TEXT_11 = "ParticipantTab.Label=";
  protected final String TEXT_12 = NL + "      " + NL + "ModelSearchParticipantArea.AllElementsParticipantTab.Label=All Elements" + NL + "ModelSearchParticipantArea.AllElementsParticipantTab.Tooltip=All Element Coming From All Partcipant Model Packages" + NL + "" + NL + "ModelSearchParticipantArea.selecAllLabel=Select All" + NL + "ModelSearchParticipantArea.deselectAllLabel=Deselect All" + NL + "" + NL + " " + NL + "pluginName=";
  protected final String TEXT_13 = " Model Search Ui Plug-in" + NL + "providerName=Eclipse.org";
  protected final String TEXT_14 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String modelName = settings.getGenModel().getModelName();
	List<GenPackage> genPackagesList = settings.getGenModel().getGenPackages();

    stringBuffer.append(TEXT_2);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_5);
      for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(p.getPackageInterfaceName());
     } 
    stringBuffer.append(TEXT_12);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    return stringBuffer.toString();
  }
}
