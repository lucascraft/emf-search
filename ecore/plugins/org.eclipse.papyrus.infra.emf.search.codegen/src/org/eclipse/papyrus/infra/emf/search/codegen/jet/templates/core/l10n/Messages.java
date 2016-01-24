package org.eclipse.papyrus.infra.emf.search.codegen.jet.templates.core.l10n;

import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.*;

public class Messages
{
  protected static String nl;
  public static synchronized Messages create(String lineSeparator)
  {
    nl = lineSeparator;
    Messages result = new Messages();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.l10n;" + NL + "" + NL + "import java.util.MissingResourceException;" + NL + "import java.util.ResourceBundle;" + NL + "" + NL + "/**" + NL + " * l10n (localization) " + NL + " */" + NL + "public class Messages {" + NL + "\tprivate static final String BUNDLE_NAME = \"messages\"; //$NON-NLS-1$" + NL + "" + NL + "\tprivate static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle" + NL + "\t\t\t.getBundle(BUNDLE_NAME);" + NL + "" + NL + "\tprivate Messages() {" + NL + "\t}" + NL + "" + NL + "\tpublic static String getString(String key) {" + NL + "\t\ttry {" + NL + "\t\t\treturn RESOURCE_BUNDLE.getString(key);" + NL + "\t\t} catch (MissingResourceException e) {" + NL + "\t\t\treturn '!' + key + '!';" + NL + "\t\t}" + NL + "\t}" + NL + "}";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings= (ModelSearchGenSettings) argument;
	String basePackage = settings.getGenModel().getModelPluginID();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
