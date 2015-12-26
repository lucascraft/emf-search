package org.eclipse.emf.search.codegen.jet.templates.ui.providers;

import java.util.Collection;
import java.util.List;
import org.eclipse.emf.codegen.ecore.genmodel.*;
import org.eclipse.emf.search.codegen.model.generator.*;

public class MetaModelParticipantsItemProvider
{
  protected static String nl;
  public static synchronized MetaModelParticipantsItemProvider create(String lineSeparator)
  {
    nl = lineSeparator;
    MetaModelParticipantsItemProvider result = new MetaModelParticipantsItemProvider();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.ui.providers;" + NL + "" + NL + "import org.eclipse.emf.ecore.EAttribute;" + NL + "import org.eclipse.emf.ecore.EClass;" + NL + "import org.eclipse.emf.ecore.EClassifier;" + NL + "import org.eclipse.emf.search.ui.providers.AbstractMetaModelParticipantsItemProvider;" + NL + "import java.util.ArrayList;" + NL + "import java.util.List;" + NL + "import java.util.Collection;" + NL;
  protected final String TEXT_2 = NL + "import ";
  protected final String TEXT_3 = ";";
  protected final String TEXT_4 = " " + NL + "" + NL + "public class MetaModelParticipantsItemProvider extends" + NL + "AbstractMetaModelParticipantsItemProvider {" + NL + "\tprivate Collection<String> nsURIs;" + NL + "\tpublic MetaModelParticipantsItemProvider(Collection<String> nsURIs) {" + NL + "\t\tthis.nsURIs = nsURIs;" + NL + "\t}" + NL + "\tpublic Object[] getElements(Object inputElement) {" + NL + "\t\tList<EClassifier> eClassifierList = new ArrayList<EClassifier>();" + NL + "\t\t";
  protected final String TEXT_5 = NL + "\t\tif (nsURIs.contains(";
  protected final String TEXT_6 = ".eNS_URI)) {" + NL + "\t\t\teClassifierList.addAll(";
  protected final String TEXT_7 = ".eINSTANCE.getEClassifiers());" + NL + "\t\t}" + NL + "\t\t";
  protected final String TEXT_8 = NL + "\t\treturn eClassifierList.toArray();" + NL + "\t}" + NL + "\t   @Override" + NL + "    public boolean hasChildren(Object element) {" + NL + "        return getChildren(element).length > 0;" + NL + "    }" + NL + "" + NL + "    @Override" + NL + "    public Object[] getChildren(Object parentElement) {" + NL + "        if (parentElement instanceof EClass) {" + NL + "            return ((EClass)parentElement).getEAttributes().toArray();" + NL + "        }" + NL + "        return super.getChildren(parentElement);" + NL + "    }" + NL + "    " + NL + "    @Override" + NL + "    public Object getParent(Object element) {" + NL + "        if (element instanceof EAttribute) {" + NL + "            return ((EAttribute)element).eContainer();" + NL + "        }" + NL + "        return super.getParent(element);" + NL + "    }" + NL + "}";
  protected final String TEXT_9 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	List<GenPackage> genPackagesList = settings.getGenModel().getGenPackages();
	String basePackage = settings.getGenModel().getModelPluginID();

    stringBuffer.append(TEXT_1);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_2);
    stringBuffer.append(p.getQualifiedPackageInterfaceName());
    stringBuffer.append(TEXT_3);
     } 
    stringBuffer.append(TEXT_4);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_5);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_7);
     } 
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
}
