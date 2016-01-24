package org.eclipse.papyrus.infra.emf.search.codegen.jet.templates.core.utils;

import java.util.List;
import org.eclipse.emf.codegen.ecore.genmodel.*;
import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;

public class TextualFeaturesUtils
{
  protected static String nl;
  public static synchronized TextualFeaturesUtils create(String lineSeparator)
  {
    nl = lineSeparator;
    TextualFeaturesUtils result = new TextualFeaturesUtils();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.util;" + NL + "" + NL + "import java.util.ArrayList;" + NL + "import java.util.Arrays;" + NL + "import java.util.List;" + NL + "" + NL + "import org.eclipse.emf.ecore.EAttribute;" + NL + "import org.eclipse.emf.ecore.EClass;" + NL + "import org.eclipse.emf.ecore.EObject;" + NL + "import org.eclipse.emf.ecore.EStructuralFeature;" + NL + "import org.eclipse.emf.ecore.ETypedElement;" + NL + "import org.eclipse.emf.ecore.util.EcoreUtil;" + NL + "import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;" + NL;
  protected final String TEXT_2 = NL + "import ";
  protected final String TEXT_3 = ";";
  protected final String TEXT_4 = NL + NL + "public class TextualFeaturesUtils  {" + NL + "\t\t" + NL + "\tpublic TextualFeaturesUtils() {" + NL + "\t}" + NL + "\t" + NL + "\tprivate static TextualFeaturesUtils instance;" + NL + "\t" + NL + "\tpublic static TextualFeaturesUtils instance() {" + NL + "\t\treturn instance == null ? instance = new TextualFeaturesUtils() : instance;" + NL + "\t}" + NL + "\t" + NL + "\t\t";
  protected final String TEXT_5 = NL + NL + "\tpublic List<ETypedElement> getParticipantTextualTypedElement() {" + NL + "\t\tList<ETypedElement> list = new ArrayList<ETypedElement>();" + NL + "\t\t";
  protected final String TEXT_6 = NL + "\t\t\tlist.addAll(get";
  protected final String TEXT_7 = "ParticipantTextualTypedElement());" + NL + "\t\t";
  protected final String TEXT_8 = NL + "\t\treturn list;" + NL + "\t}" + NL + "\t" + NL + "\t";
  protected final String TEXT_9 = NL + "\tpublic List<ETypedElement> get";
  protected final String TEXT_10 = "ParticipantTextualTypedElement() {" + NL + "\t\treturn Arrays.asList(" + NL + "\t\t\tnew ETypedElement[] {" + NL + "\t            ";
  protected final String TEXT_11 = NL + "                    ";
  protected final String TEXT_12 = NL + "\t\t\t}" + NL + "\t\t);" + NL + "\t}" + NL + "\t";
  protected final String TEXT_13 = NL + NL + NL + "    public List<ETypedElement> getOwnedETypedElementsFromEObject(EObject obj) {" + NL + "        List<ETypedElement> list = new ArrayList<ETypedElement>();" + NL + "        for (ETypedElement e : getParticipantTextualTypedElement()) {" + NL + "            if (e instanceof EAttribute) {" + NL + "                try {" + NL + "                    String val = getTextFromETypedElement(obj, e);" + NL + "                    if (val instanceof String) {" + NL + "                        list.add(e);" + NL + "                    }" + NL + "                } catch (Throwable t) {" + NL + "                    //ugly" + NL + "                }" + NL + "            }" + NL + "        }" + NL + "        return list;" + NL + "    }" + NL + "" + NL + "    public String getTextFromETypedElement(EObject obj, ETypedElement elem) {" + NL + "        if (elem instanceof EAttribute) {" + NL + "            return EcoreUtil.convertToString(((EAttribute) elem)" + NL + "                    .getEAttributeType(), obj.eGet((EStructuralFeature) elem));" + NL + "        }" + NL + "        return null;" + NL + "    }" + NL + "" + NL + "    public String getTextFromEStructuralFeatureIfAny(EObject obj) {" + NL + "        List<ETypedElement> lst = getOwnedETypedElementsFromEObject(obj);" + NL + "        if (!lst.isEmpty()) {" + NL + "            ETypedElement elem = lst.get(0);" + NL + "            if (elem instanceof EAttribute) {" + NL + "                return EcoreUtil.convertToString(((EAttribute) elem)" + NL + "                        .getEAttributeType(), obj" + NL + "                        .eGet((EStructuralFeature) elem));" + NL + "            }" + NL + "        }" + NL + "        return null;" + NL + "    }" + NL + "" + NL + "    public void setTextForEStructuralFeatureIfAny(EObject obj, Object val) {" + NL + "        for (ETypedElement e : getParticipantTextualTypedElement()) {" + NL + "            if (e instanceof EAttribute) {" + NL + "                try {" + NL + "                    if (val instanceof String) {" + NL + "                        Object valuation = EcoreUtil.createFromString(" + NL + "                                ((EAttribute) e).getEAttributeType()," + NL + "                                (String) val);" + NL + "                        if (valuation != null) {" + NL + "                            obj.eSet((EStructuralFeature) e, valuation);" + NL + "                        }" + NL + "                    }" + NL + "                } catch (Throwable ex) {" + NL + "                    //TODO: User to handle exception in an elegant way" + NL + "                }" + NL + "            }" + NL + "        }" + NL + "    }" + NL + "" + NL + "    public void setTextForETypedElement(EObject obj, ETypedElement e, Object val) {" + NL + "        if (e instanceof EAttribute) {" + NL + "            try {" + NL + "                if (val instanceof String) {" + NL + "                    Object valuation = EcoreUtil.createFromString(" + NL + "                            ((EAttribute) e).getEAttributeType(), (String) val);" + NL + "                    if (valuation != null) {" + NL + "                        obj.eSet((EStructuralFeature) e, valuation);" + NL + "                    }" + NL + "                }" + NL + "            } catch (Throwable ex) {" + NL + "                //TODO: User to handle exception in an elegant way" + NL + "            }" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    //" + NL + "    // Get EStructuralFeature subset coming from query selection " + NL + "    //" + NL + "    public List<EStructuralFeature> getEStructuralFeaturesFromQueryAndEObject(EObject eObj, IModelSearchQuery query) {" + NL + "        List<EStructuralFeature> lst = new ArrayList<EStructuralFeature>();" + NL + "        for (EStructuralFeature f : query.getModelSearchParameters().getEStructuralFeatures()) {" + NL + "            if (eObj instanceof EObject) {" + NL + "                if (eObj.eClass().getEAttributes().contains(f)) {" + NL + "                    lst.add(f);" + NL + "                }" + NL + "            }" + NL + "        }" + NL + "        if (lst.isEmpty()) {" + NL + "            lst.add(((EClass) eObj).getEIDAttribute());" + NL + "        }" + NL + "        return lst;" + NL + "    }" + NL + "}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	List<EStringAccessor> genTypeParameter = settings.getTextualSettings().getEStringAccessors();
	String basePackage = settings.getGenModel().getModelPluginPackageName();
	List<GenPackage> genPackagesList = settings.getGenModel().getGenPackages();
	String modelName = settings.getGenModel().getModelName();

    stringBuffer.append(TEXT_1);
    
// 
// Refines package names to only get unique ones
//
List<String> uniquePkgList = new ArrayList<String>();
for (EStringAccessor element : genTypeParameter) { 
    String pName = element.getGenPackage().getQualifiedPackageInterfaceName();
    if(!uniquePkgList.contains(pName)) {
        uniquePkgList.add(pName);
    }
}

    
for (String pkgName : uniquePkgList) {

    stringBuffer.append(TEXT_2);
    stringBuffer.append(pkgName);
    stringBuffer.append(TEXT_3);
     } 
    stringBuffer.append(TEXT_4);
    
		Map<GenPackage, List<String>> literalsForPackageMap = new HashMap<GenPackage, List<String>>();
		for (GenPackage p : genPackagesList) {
			literalsForPackageMap.put(p, new ArrayList<String>());
			for  (EStringAccessor element : genTypeParameter) { 
                String literal = element.getLiteral();
				if (literal.startsWith(p.getPackageInterfaceName())) {
					literalsForPackageMap.get(p).add(literal);
				}
			}
		}
	
    stringBuffer.append(TEXT_5);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_7);
     } 
    stringBuffer.append(TEXT_8);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_9);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_10);
     for (String literal : literalsForPackageMap.get(p)) { 
    stringBuffer.append(TEXT_11);
    stringBuffer.append(literal);
    stringBuffer.append(literalsForPackageMap.get(p).indexOf(literal)==literalsForPackageMap.get(p).size()-1?"":",");
     } 
    stringBuffer.append(TEXT_12);
     } 
    stringBuffer.append(TEXT_13);
    return stringBuffer.toString();
  }
}
