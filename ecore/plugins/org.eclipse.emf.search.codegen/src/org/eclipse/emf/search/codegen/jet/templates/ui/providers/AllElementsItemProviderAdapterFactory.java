package org.eclipse.emf.search.codegen.jet.templates.ui.providers;

import java.util.Collection;
import java.util.List;
import org.eclipse.emf.codegen.ecore.genmodel.*;
import org.eclipse.emf.search.codegen.model.generator.*;

public class AllElementsItemProviderAdapterFactory
{
  protected static String nl;
  public static synchronized AllElementsItemProviderAdapterFactory create(String lineSeparator)
  {
    nl = lineSeparator;
    AllElementsItemProviderAdapterFactory result = new AllElementsItemProviderAdapterFactory();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.ui.providers;" + NL + "" + NL + "import org.eclipse.emf.common.notify.AdapterFactory;" + NL + "import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;" + NL + "import org.eclipse.emf.edit.provider.ComposedAdapterFactory;" + NL + "import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;" + NL;
  protected final String TEXT_2 = NL + "import ";
  protected final String TEXT_3 = ";";
  protected final String TEXT_4 = " " + NL + "" + NL + "public class AllElementsItemProviderAdapterFactory extends ComposedAdapterFactory {" + NL + "\tpublic AllElementsItemProviderAdapterFactory() {" + NL + "\t\tsuper(" + NL + "\t\t\tnew AdapterFactory[] {" + NL + "\t\t\t\tnew EcoreItemProviderAdapterFactory()," + NL + "\t\t\t\t";
  protected final String TEXT_5 = NL + "\t\t\t\tnew ";
  protected final String TEXT_6 = "()," + NL + "\t\t\t\t";
  protected final String TEXT_7 = NL + "\t\t\t\tnew ResourceItemProviderAdapterFactory()" + NL + "\t\t\t}" + NL + "\t\t);" + NL + "\t}" + NL + "}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	List<GenPackage> genPackagesList = settings.getGenModel().getGenPackages();
	String basePackage = settings.getGenModel().getModelPluginID();

    stringBuffer.append(TEXT_1);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_2);
    stringBuffer.append(p.getQualifiedItemProviderAdapterFactoryClassName());
    stringBuffer.append(TEXT_3);
     } 
    stringBuffer.append(TEXT_4);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_5);
    stringBuffer.append(p.getItemProviderAdapterFactoryClassName());
    stringBuffer.append(TEXT_6);
     } 
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}
