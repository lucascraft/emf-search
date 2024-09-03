package org.eclipse.emf.search.codegen.jet.templates.ui.areas;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.eclipse.emf.codegen.ecore.genmodel.*;
import java.util.ArrayList;
import org.eclipse.emf.search.codegen.model.generator.*;

public class ModelSearchParticipantArea
{
  protected static String nl;
  public static synchronized ModelSearchParticipantArea create(String lineSeparator)
  {
    nl = lineSeparator;
    ModelSearchParticipantArea result = new ModelSearchParticipantArea();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.ui.areas;" + NL + "" + NL + "import java.util.ArrayList;" + NL + "import java.util.List;" + NL + "" + NL + "import org.eclipse.emf.common.notify.AdapterFactory;" + NL + "import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;" + NL + "import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;" + NL + "import org.eclipse.emf.search.ui.areas.AbstractModelSearchParticipantArea;" + NL + "import org.eclipse.emf.search.ui.pages.AbstractModelSearchPage;" + NL + "import org.eclipse.emf.search.ui.providers.AbstractMetaModelParticipantsItemProvider;" + NL + "import org.eclipse.swt.widgets.Composite;" + NL + "import org.eclipse.swt.graphics.Image;" + NL + "import org.eclipse.emf.search.utils.ModelSearchImagesUtil;" + NL + "import java.net.URL;" + NL + "" + NL + "import search.ui.Activator;" + NL + "" + NL + "import org.eclipse.core.runtime.FileLocator;" + NL + "import org.eclipse.core.runtime.Path;" + NL + "import org.eclipse.core.runtime.Status;" + NL + "" + NL + "import java.util.Collection;" + NL + "" + NL + "import search.util.TextualFeaturesUtils;" + NL + "" + NL + "import search.ui.providers.MetaModelParticipantsItemProvider;" + NL;
  protected final String TEXT_2 = NL + "import ";
  protected final String TEXT_3 = ";";
  protected final String TEXT_4 = " " + NL;
  protected final String TEXT_5 = " " + NL + "" + NL + "import org.eclipse.emf.ecore.EPackage;" + NL + "import org.eclipse.emf.ecore.ENamedElement;" + NL + "import org.eclipse.emf.ecore.EObject;" + NL + "import java.util.Arrays;" + NL + "" + NL + "public final class ModelSearchParticipantArea extends AbstractModelSearchParticipantArea {" + NL + "" + NL + "\tprivate String nsURI;" + NL + "\tpublic ModelSearchParticipantArea(Composite parent, AbstractModelSearchPage page, int style) {" + NL + "\t\tsuper(parent, page, style);" + NL + "\t\tcreateContent();" + NL + "\t}" + NL + "\t" + NL + "\tpublic ModelSearchParticipantArea(Composite parent, AbstractModelSearchPage page, int style, String nsURI) {" + NL + "\t\tsuper(parent, page, style);" + NL + "\t\tthis.nsURI = nsURI;" + NL + "\t\tcreateContent();" + NL + "\t}" + NL + "\t" + NL + "\t@Override" + NL + "\tprotected boolean getDefaultParticpantsControlEnabling() {" + NL + "\t\treturn false;" + NL + "\t}" + NL + "\t" + NL + "\t@Override" + NL + "\tpublic List<AdapterFactory> getMetaElementComposeableAdapterFactories(String nsURI) {" + NL + "\t\treturn getMetaElementComposeableAdapterFactories();" + NL + "\t}" + NL + "\t@Override" + NL + "\tpublic List<AdapterFactory> getMetaElementComposeableAdapterFactories() {" + NL + "\t\tList<AdapterFactory> composeableAdapterFactories = new ArrayList<AdapterFactory>();" + NL + "\t\tcomposeableAdapterFactories.add(new EcoreItemProviderAdapterFactory());" + NL + "\t\t";
  protected final String TEXT_6 = NL + "\t\tif (";
  protected final String TEXT_7 = ".eNS_URI.equals(nsURI)) {" + NL + "\t\t\tcomposeableAdapterFactories.add(new ";
  protected final String TEXT_8 = "());" + NL + "\t\t}\t" + NL + "\t\t";
  protected final String TEXT_9 = NL + "\t\tif  (nsURI == null || \"\".equals(nsURI)) {" + NL + "\t\t\t";
  protected final String TEXT_10 = NL + "\t\t\tcomposeableAdapterFactories.add(new ";
  protected final String TEXT_11 = "());" + NL + "\t\t\t";
  protected final String TEXT_12 = NL + "\t\t}" + NL + "\t\tcomposeableAdapterFactories.add(new ResourceItemProviderAdapterFactory());" + NL + "\t\treturn composeableAdapterFactories;" + NL + "\t}" + NL + "\t" + NL + "\t@Override" + NL + "\tpublic AbstractMetaModelParticipantsItemProvider getMetaModelParticipantsItemProvider() {" + NL + "\t\treturn new MetaModelParticipantsItemProvider(getTargetMetaModelIDs());" + NL + "\t}" + NL + "\t" + NL + "\t@Override" + NL + "\tprotected Collection<EPackage> getTargetModelPackages() {" + NL + "\t\t";
  protected final String TEXT_13 = ".eNS_URI.equals(nsURI)) {" + NL + "\t\t\treturn Arrays.asList(new EPackage[] { ";
  protected final String TEXT_14 = ".eINSTANCE });" + NL + "\t\t}" + NL + "\t\t";
  protected final String TEXT_15 = NL + "\t\treturn Arrays.asList(new EPackage[] {" + NL + "\t\t";
  protected final String TEXT_16 = NL + "\t          ";
  protected final String TEXT_17 = ".eINSTANCE";
  protected final String TEXT_18 = NL + "\t\t";
  protected final String TEXT_19 = NL + "\t\t});" + NL + " \t}" + NL + " \t" + NL + " \tpublic Collection<String> getTargetMetaModelIDs() {" + NL + "\t\t";
  protected final String TEXT_20 = ".eNS_URI.equals(nsURI)) {" + NL + "\t\t\treturn Arrays.asList(new String[] { nsURI }); " + NL + "\t\t}" + NL + "\t\t";
  protected final String TEXT_21 = NL + "\t\treturn Arrays.asList(new String[] {" + NL + "\t\t";
  protected final String TEXT_22 = ".eNS_URI";
  protected final String TEXT_23 = NL + "\t\t});" + NL + " \t}" + NL + "\t" + NL + "\t@Override" + NL + "\tprotected String getTargetModelElementText(Object object) {" + NL + "\t\treturn object instanceof ENamedElement ? ((ENamedElement) object).getName() : null;" + NL + "\t}" + NL + "\t" + NL + "\t@Override" + NL + "\tprotected Image getTargetModelElementImage(Object object) {" + NL + "\t\ttry {" + NL + "\t\t\tif (object instanceof ENamedElement) {" + NL + "\t\t\t\tString imagePath = \"/icons/full/obj16/\" + computeElementImageName(((ENamedElement)object).getName()) + \".gif\"; //$NON-NLS-1$  //$NON-NLS-2$" + NL + "\t\t\t\tURL url = FileLocator.find(";
  protected final String TEXT_24 = ".getPlugin().getBundle(), new Path(imagePath), null);" + NL + "\t\t\t\tif (url != null) {" + NL + "\t\t\t\t\treturn ModelSearchImagesUtil.getImage(url);" + NL + "\t\t\t\t}" + NL + "\t\t\t}" + NL + "\t\t} catch (Throwable t) {" + NL + "\t\t\tActivator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, \"Error while attempmting to retrieve image from edit\" + ";
  protected final String TEXT_25 = ".getPlugin().getBundle() + \" bundle\")); //$NON-NLS-1$  //$NON-NLS-2$" + NL + "\t\t} " + NL + "\t\treturn null;" + NL + "\t}" + NL + "\t" + NL + "\tprivate String computeElementImageName(String name) {" + NL + "\t\treturn name;" + NL + "\t}" + NL + "}";
  protected final String TEXT_26 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String basePackage = settings.getGenModel().getModelPluginID();
	String editPluginClass = settings.getGenModel().getEditPluginClass();
	List<GenPackage> genPackagesList = settings.getGenModel().getGenPackages();
	List<EStringAccessor> genTypeParameter = settings.getTextualSettings().getEStringAccessors();

    stringBuffer.append(TEXT_1);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_2);
    stringBuffer.append(p.getQualifiedItemProviderAdapterFactoryClassName());
    stringBuffer.append(TEXT_3);
     } 
    stringBuffer.append(TEXT_4);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_2);
    stringBuffer.append(p.getQualifiedPackageInterfaceName());
    stringBuffer.append(TEXT_3);
     } 
    stringBuffer.append(TEXT_5);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(p.getItemProviderAdapterFactoryClassName());
    stringBuffer.append(TEXT_8);
     } 
    stringBuffer.append(TEXT_9);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_10);
    stringBuffer.append(p.getItemProviderAdapterFactoryClassName());
    stringBuffer.append(TEXT_11);
     } 
    stringBuffer.append(TEXT_12);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_14);
     } 
    stringBuffer.append(TEXT_15);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_16);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(genPackagesList.indexOf(p)==genPackagesList.size()-1?"":",");
    stringBuffer.append(TEXT_18);
     } 
    stringBuffer.append(TEXT_19);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_20);
     } 
    stringBuffer.append(TEXT_21);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_16);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_22);
    stringBuffer.append(genPackagesList.indexOf(p)==genPackagesList.size()-1?"":",");
    stringBuffer.append(TEXT_18);
     } 
    stringBuffer.append(TEXT_23);
    stringBuffer.append(editPluginClass);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(editPluginClass);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(TEXT_26);
    return stringBuffer.toString();
  }
}
