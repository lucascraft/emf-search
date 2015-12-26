package org.eclipse.emf.search.codegen.jet.templates.ui;

import java.util.List;
import org.eclipse.emf.codegen.ecore.genmodel.*;
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
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL + "<?eclipse version=\"3.2\"?>" + NL + "<plugin>" + NL + "   <extension" + NL + "         point=\"org.eclipse.emf.search.ui.modelSearchQueryTab\">" + NL + "      <queryTab" + NL + "            id=\"search.ui.";
  protected final String TEXT_2 = "PlainTextSearchID\"" + NL + "            image=\"icons/textQuery.gif\"" + NL + "            index=\"0\"" + NL + "            label=\"%ModelSearchParticipantArea.";
  protected final String TEXT_3 = "PlainTextSearchExtPoint.Label\"" + NL + "            queryCompositeAreaFactory=\"org.eclipse.emf.search.ecore.ui.factories.EcoreTextModelSearchQueryAreaFactory\"" + NL + "            targetSearchPageID=\"search.ui.pages.";
  protected final String TEXT_4 = "ModelSearchPageID\"" + NL + "            tooltip=\"%";
  protected final String TEXT_5 = "PlainTextSearchExtPoint.Tooltip\"/>" + NL + "   </extension>" + NL + "   " + NL + "   <extension" + NL + "         point=\"org.eclipse.emf.search.ui.modelSearchParticipantTab\">" + NL + "         ";
  protected final String TEXT_6 = NL + "           <participantTab" + NL + "            elementComposeableAdapterFactory=\"search.ui.providers.AllElementsItemProviderAdapterFactory\"" + NL + "            elementModelEditorSelectionHandler=\"search.ui.handlers.EditorSelectionHandler\"" + NL + "            id=\"";
  protected final String TEXT_7 = "AllElementsParticipantTab\"" + NL + "            image=\"icons/esearch.gif\"" + NL + "            index=\"1\"" + NL + "            label=\"%ModelSearchParticipantArea.AllElementsParticipantTab.Label\"" + NL + "            participantCompositeAreaFactory=\"search.ui.factories.ModelSearchParticipantAreaFactory\"" + NL + "            targetSearchPageID=\"search.ui.pages.";
  protected final String TEXT_8 = "ModelSearchPageID\"" + NL + "            targetNsURI=\"\"" + NL + "            tooltip=\"%ModelSearchParticipantArea.AllElementsParticipantTab.Tooltip\">" + NL + "      </participantTab>" + NL + "      ";
  protected final String TEXT_9 = NL + "      <participantTab" + NL + "            elementComposeableAdapterFactory=\"";
  protected final String TEXT_10 = "\"" + NL + "            elementModelEditorSelectionHandler=\"search.ui.handlers.EditorSelectionHandler\"" + NL + "            id=\"";
  protected final String TEXT_11 = "ParticipantTab\"" + NL + "            image=\"icons/ecore.gif\"" + NL + "            index=\"1\"" + NL + "            label=\"%ModelSearchParticipantArea.";
  protected final String TEXT_12 = "ParticipantTab.Label\"" + NL + "            participantCompositeAreaFactory=\"search.ui.factories.ModelSearchParticipantAreaFactory\"" + NL + "            targetSearchPageID=\"search.ui.pages.";
  protected final String TEXT_13 = "ModelSearchPageID\"" + NL + "            targetNsURI=\"";
  protected final String TEXT_14 = "\"" + NL + "            tooltip=\"%ModelSearchParticipantArea.";
  protected final String TEXT_15 = "ParticipantTab.Tooltip\">" + NL + "      </participantTab>" + NL + "      ";
  protected final String TEXT_16 = NL + "      " + NL + "   </extension>" + NL + "   " + NL + "   <extension" + NL + "         point=\"org.eclipse.search.searchPages\">" + NL + "      <page" + NL + "            canSearchEnclosingProjects=\"true\"" + NL + "            class=\"search.ui.pages.ModelSearchPage\"" + NL + "            enabled=\"true\"" + NL + "            icon=\"icons/esearch.gif\"" + NL + "            id=\"search.ui.pages.";
  protected final String TEXT_17 = "ModelSearchPageID\"" + NL + "            label=\"%ModelSearchParticipantArea.";
  protected final String TEXT_18 = "ModelSearchPage.Label\"" + NL + "            showScopeSection=\"true\"" + NL + "            tabPosition=\"0\"/>" + NL + "   </extension>" + NL + "   " + NL + "   <extension" + NL + "         point=\"org.eclipse.emf.search.ui.modelSearchEngineMapping\">" + NL + " ";
  protected final String TEXT_19 = NL + "      <mapping" + NL + "            id=\"";
  protected final String TEXT_20 = ".text4All\"" + NL + "            engineID=\"search.";
  protected final String TEXT_21 = "SearchEngine\"" + NL + "            participantsTabID=\"";
  protected final String TEXT_22 = "AllElementsParticipantTab\"" + NL + "            queryTabID=\"search.ui.";
  protected final String TEXT_23 = "PlainTextSearchID\">" + NL + "      </mapping>";
  protected final String TEXT_24 = NL + "      <mapping" + NL + "            id=\"search.text4";
  protected final String TEXT_25 = "\"" + NL + "            engineID=\"search.";
  protected final String TEXT_26 = "SearchEngine\"" + NL + "            participantsTabID=\"";
  protected final String TEXT_27 = "ParticipantTab\"" + NL + "            queryTabID=\"search.ui.";
  protected final String TEXT_28 = "PlainTextSearchID\">" + NL + "      </mapping>";
  protected final String TEXT_29 = NL + "      " + NL + "   </extension>" + NL + "</plugin>";
  protected final String TEXT_30 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String modelName = settings.getGenModel().getModelName();
	List<GenPackage> genPackagesList = settings.getGenModel().getGenPackages();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_5);
    
      if ((genPackagesList.size()>1)) { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append(settings.getGenModel().getModelPluginID());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_8);
        }
      
      for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_9);
    stringBuffer.append(p.getQualifiedItemProviderAdapterFactoryClassName());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(p.getQualifiedPackageInterfaceName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(p.getNSURI());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_15);
     }
      
    stringBuffer.append(TEXT_16);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_18);
     
       if (genPackagesList.size()>1) {
       
    stringBuffer.append(TEXT_19);
    stringBuffer.append(settings.getGenModel().getModelPluginID());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(settings.getGenModel().getModelPluginID());
    stringBuffer.append(TEXT_22);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_23);
     
       }
        for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_24);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_25);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(p.getQualifiedPackageInterfaceName());
    stringBuffer.append(TEXT_27);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_28);
     }  
    stringBuffer.append(TEXT_29);
    stringBuffer.append(TEXT_30);
    return stringBuffer.toString();
  }
}
