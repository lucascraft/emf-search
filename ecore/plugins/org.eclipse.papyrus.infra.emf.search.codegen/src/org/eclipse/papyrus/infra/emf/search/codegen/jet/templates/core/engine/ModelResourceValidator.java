package org.eclipse.papyrus.infra.emf.search.codegen.jet.templates.core.engine;

import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.*;
import org.eclipse.emf.codegen.ecore.genmodel.*;

public class ModelResourceValidator
{
  protected static String nl;
  public static synchronized ModelResourceValidator create(String lineSeparator)
  {
    nl = lineSeparator;
    ModelResourceValidator result = new ModelResourceValidator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.engine;" + NL + "" + NL + "import org.eclipse.papyrus.infra.emf.search.core.resource.AbstractModelResourceValidator;" + NL + "" + NL + "/**" + NL + " * Allows users to describe all the specific query search supported model editor extensions." + NL + " */" + NL + "public class ModelResourceValidator extends AbstractModelResourceValidator {" + NL + "\tpublic ModelResourceValidator() {" + NL + "\t\t";
  protected final String TEXT_2 = NL + "\t\taddModelFileExtension(\"";
  protected final String TEXT_3 = "\"); //$NON-NLS-1$" + NL + "\t\t";
  protected final String TEXT_4 = NL + "\t}" + NL + "}" + NL;
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
