package org.eclipse.papyrus.infra.emf.search.codegen.jet.templates.core.util;

import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.*;
import org.eclipse.emf.codegen.ecore.genmodel.*;

public class ModelSearchScopeFileSystemVisitor
{
  protected static String nl;
  public static synchronized ModelSearchScopeFileSystemVisitor create(String lineSeparator)
  {
    nl = lineSeparator;
    ModelSearchScopeFileSystemVisitor result = new ModelSearchScopeFileSystemVisitor();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.util;" + NL + "" + NL + "import org.eclipse.emf.ecore.resource.Resource;" + NL + "import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;" + NL + "import org.eclipse.papyrus.infra.emf.search.ecore.scope.file.EcoreModelSearchScopeFileSystemVisitor;" + NL + "import java.io.File;" + NL + "" + NL + "public class ModelSearchScopeFileSystemVisitor extends" + NL + "\t\tEcoreModelSearchScopeFileSystemVisitor {" + NL + "" + NL + "\tpublic ModelSearchScopeFileSystemVisitor(" + NL + "\t\t\tIModelSearchScope<Object, Resource> scope, String engineID) {" + NL + "\t\tsuper(scope,  engineID);" + NL + "\t}" + NL + "\tprotected boolean isParticipantCurrentSearchEngineValid(File f) {" + NL + "\t\tif (f instanceof File && f.canRead() && f.exists() && ! f.isHidden()) {" + NL + "\t\t\t";
  protected final String TEXT_2 = NL + "\t\t\tif (f.getName().endsWith(\".";
  protected final String TEXT_3 = "\")) { //$NON-NLS-1$" + NL + "\t\t\t\t\treturn true;" + NL + "\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_4 = NL + "\t\t} " + NL + "\t\treturn false;" + NL + "\t}" + NL + "}";
  protected final String TEXT_5 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;

    stringBuffer.append(TEXT_1);
     for (GenPackage p : settings.getGenModel().getGenPackages()) { 
    stringBuffer.append(TEXT_2);
    stringBuffer.append(p.getPackageName().toLowerCase());
    stringBuffer.append(TEXT_3);
     } 
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    return stringBuffer.toString();
  }
}
