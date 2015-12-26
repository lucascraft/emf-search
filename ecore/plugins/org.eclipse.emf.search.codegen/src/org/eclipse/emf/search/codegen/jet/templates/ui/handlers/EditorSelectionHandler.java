package org.eclipse.emf.search.codegen.jet.templates.ui.handlers;

import java.util.List;
import org.eclipse.emf.codegen.ecore.genmodel.*;
import org.eclipse.emf.search.codegen.model.generator.*;

public class EditorSelectionHandler
{
  protected static String nl;
  public static synchronized EditorSelectionHandler create(String lineSeparator)
  {
    nl = lineSeparator;
    EditorSelectionHandler result = new EditorSelectionHandler();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.ui.handlers;" + NL + "" + NL + "import java.util.Arrays;" + NL + "" + NL + "import org.eclipse.core.runtime.IStatus;" + NL + "import org.eclipse.core.runtime.Status;" + NL + "import org.eclipse.emf.search.ui.handlers.AbstractModelElementEditorSelectionHandler;" + NL + "import org.eclipse.ui.IEditorPart;" + NL;
  protected final String TEXT_2 = NL + "import ";
  protected final String TEXT_3 = ";";
  protected final String TEXT_4 = " " + NL + "" + NL + "/**" + NL + " * Defines entity responsible of editor selection handling." + NL + " * " + NL + " * In other words users defines here how the search double clicked result will be handled in " + NL + " * terms of corresponding editor selection." + NL + " *  " + NL + " */" + NL + "public class EditorSelectionHandler extends AbstractModelElementEditorSelectionHandler {" + NL + "" + NL + "\tpublic boolean isCompatibleModelElementEditorSelectionHandler(IEditorPart part) {" + NL + "     return ";
  protected final String TEXT_5 = " ";
  protected final String TEXT_6 = " (part instanceof ";
  protected final String TEXT_7 = ") ";
  protected final String TEXT_8 = "; " + NL + "\t}" + NL + "" + NL + "\tpublic IStatus handleOpenTreeEditorWithSelection(IEditorPart part," + NL + "\t\t\tObject selection) {" + NL + "\t\t";
  protected final String TEXT_9 = NL + "\t\tif (part instanceof ";
  protected final String TEXT_10 = ") {" + NL + "\t\t\t((";
  protected final String TEXT_11 = ")part).setSelectionToViewer(Arrays.asList(new Object[]{selection}));" + NL + "\t\t\treturn Status.OK_STATUS;" + NL + "\t\t}" + NL + "\t\t";
  protected final String TEXT_12 = " " + NL + "\t\treturn Status.CANCEL_STATUS;" + NL + "\t}" + NL + "" + NL + "\t@Override" + NL + "\tprotected String getNsURI() {" + NL + "\t\treturn \"\"; // TODO: user to return appropriate nsURI" + NL + "\t}" + NL + "}";
  protected final String TEXT_13 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String modelBasePackage = settings.getGenModel().getModelPluginPackageName();
	String editorBasePackage = settings.getGenModel().getEditorPluginPackageName();
	List<GenPackage> genPackagesList = settings.getGenModel().getGenPackages();

    stringBuffer.append(TEXT_1);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_2);
    stringBuffer.append(p.getQualifiedEditorClassName());
    stringBuffer.append(TEXT_3);
     } 
    stringBuffer.append(TEXT_4);
     String or = ""; for (GenPackage p : genPackagesList) {
    stringBuffer.append(TEXT_5);
    stringBuffer.append(or);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(p.getEditorClassName());
    stringBuffer.append(TEXT_7);
    or = "||"; }
    stringBuffer.append(TEXT_8);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_9);
    stringBuffer.append(p.getEditorClassName());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(p.getEditorClassName());
    stringBuffer.append(TEXT_11);
     } 
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    return stringBuffer.toString();
  }
}
