package org.eclipse.emf.search.codegen.jet.templates.ui;

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

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "package ";
  protected final String TEXT_3 = ".ui.factories;" + NL + "" + NL + "import org.eclipse.emf.search.ui.areas.IModelSearchArea;" + NL + "import org.eclipse.emf.search.ui.areas.IModelSearchAreaFactory;" + NL + "import org.eclipse.emf.search.ui.pages.ModelExtensibleSearchPage;" + NL + "import org.eclipse.swt.SWT;" + NL + "import org.eclipse.swt.widgets.Composite;" + NL + "import <>.search.ui.areas.ModelSearchParticipantArea;" + NL + "" + NL + "/**" + NL + " * Wrapper class which create a specific participant area into the model search page." + NL + " * " + NL + " */" + NL + "public class ModelSearchParticipantAreaFactory implements IModelSearchAreaFactory {" + NL + "\tpublic IModelSearchArea createArea(Composite parent, ModelExtensibleSearchPage searchPage) {" + NL + "\t\treturn new ModelSearchParticipantArea(parent, searchPage, SWT.NONE);" + NL + "\t}" + NL + "}";
  protected final String TEXT_4 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String basePackage = settings.getGenModel().getModelDirectory();

    stringBuffer.append(TEXT_2);
    stringBuffer.append(basePackage);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}
