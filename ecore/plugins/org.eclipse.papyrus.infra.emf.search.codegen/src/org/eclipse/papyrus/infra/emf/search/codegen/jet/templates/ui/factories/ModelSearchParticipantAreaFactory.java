package org.eclipse.papyrus.infra.emf.search.codegen.jet.templates.ui.factories;

import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.*;

public class ModelSearchParticipantAreaFactory
{
  protected static String nl;
  public static synchronized ModelSearchParticipantAreaFactory create(String lineSeparator)
  {
    nl = lineSeparator;
    ModelSearchParticipantAreaFactory result = new ModelSearchParticipantAreaFactory();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.ui.factories;" + NL + "" + NL + "import org.eclipse.papyrus.infra.emf.search.ui.areas.IModelSearchArea;" + NL + "import org.eclipse.papyrus.infra.emf.search.ui.areas.IModelSearchAreaFactory;" + NL + "import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;" + NL + "import org.eclipse.swt.SWT;" + NL + "import org.eclipse.swt.widgets.Composite;" + NL + "" + NL + "import search.ui.areas.ModelSearchParticipantArea;" + NL + "" + NL + "/**" + NL + " * Wrapper class which create a specific participant area into the model search page." + NL + " */" + NL + "public class ModelSearchParticipantAreaFactory implements IModelSearchAreaFactory {" + NL + "\tpublic IModelSearchArea createArea(Composite parent, AbstractModelSearchPage searchPage) {" + NL + "\t\treturn new ModelSearchParticipantArea(parent, searchPage, SWT.NONE);" + NL + "\t}" + NL + "\tpublic IModelSearchArea createArea(Composite parent," + NL + "\t\t\tAbstractModelSearchPage searchPage, String nsURI) {" + NL + "\t\treturn new ModelSearchParticipantArea(parent, searchPage, SWT.NONE, nsURI);" + NL + "\t}" + NL + "}";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String basePackage = settings.getGenModel().getModelPluginID();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
