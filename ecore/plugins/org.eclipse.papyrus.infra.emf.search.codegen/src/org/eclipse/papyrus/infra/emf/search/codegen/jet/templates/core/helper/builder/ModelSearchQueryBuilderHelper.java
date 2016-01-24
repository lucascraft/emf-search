package org.eclipse.papyrus.infra.emf.search.codegen.jet.templates.core.helper.builder;

import java.util.List;
import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.*;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;

public class ModelSearchQueryBuilderHelper
{
  protected static String nl;
  public static synchronized ModelSearchQueryBuilderHelper create(String lineSeparator)
  {
    nl = lineSeparator;
    ModelSearchQueryBuilderHelper result = new ModelSearchQueryBuilderHelper();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.helper.builder;" + NL + "" + NL + "import java.util.List;" + NL + "" + NL + "import org.eclipse.emf.ecore.resource.Resource;" + NL + "import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;" + NL + "import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;" + NL + "import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;" + NL + "import org.eclipse.papyrus.infra.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;" + NL + "" + NL + "import search.evaluators.ModelTextualModelSearchQueryEvaluator;" + NL + "import search.factories.ModelSearchQueryFactory;" + NL + "import search.factories.ModelSearchQueryParametersFactory;" + NL + "" + NL + "public class ModelSearchQueryBuilderHelper extends AbstractTextualModelSearchQueryBuilderHelper{" + NL + "\t" + NL + "\tprivate static ModelSearchQueryBuilderHelper instance;" + NL + "\t" + NL + "\t// Singleton" + NL + "\tpublic static ModelSearchQueryBuilderHelper getInstance() {" + NL + "\t\treturn instance==null?instance = new ModelSearchQueryBuilderHelper():instance;" + NL + "\t}" + NL + "\t" + NL + "\tprotected IModelSearchQueryParameters createParameters(" + NL + "\t\t\tIModelSearchScope<Object, Resource> scope," + NL + "\t\t\tList<? extends Object> participants," + NL + "\t\t\tModelSearchQueryTextualExpressionEnum textualExpression) {" + NL + "\t\t" + NL + "\t\tIModelSearchQueryParameters parameters = ModelSearchQueryParametersFactory.getInstance().createSearchQueryParameters();" + NL + "" + NL + "\t\tparameters.setEvaluator(new ModelTextualModelSearchQueryEvaluator<IModelSearchQuery, Object>());" + NL + "\t\tparameters.setParticipantElements(participants);          " + NL + "\t\tparameters.setScope(scope);" + NL + "\t\t" + NL + "\t\tinitTextualQueryParametersFromPatternKind(parameters, textualExpression);" + NL + "" + NL + "\t\treturn parameters;" + NL + "\t}" + NL + "\t" + NL + "\tprotected IModelSearchQuery createQuery(String pattern, IModelSearchQueryParameters parameters) {\t\t" + NL + "\t\treturn ModelSearchQueryFactory.getInstance().createModelSearchQuery(pattern, parameters);" + NL + "\t}" + NL + "\t" + NL + "\t";
  protected final String TEXT_2 = NL + "\tpublic IModelSearchQuery buildGlobalTextual";
  protected final String TEXT_3 = "ModelSearchQuery(String expr," + NL + "\t\t\tIModelSearchScope<Object, Resource> scope) {" + NL + "\t\treturn buildTextualModelSearchQuery(" + NL + "\t\t\texpr," + NL + "\t\t\t";
  protected final String TEXT_4 = ".eINSTANCE.getEClassifiers()," + NL + "\t\t\tModelSearchQueryTextualExpressionEnum.NORMAL_TEXT," + NL + "\t\t\tscope," + NL + "\t\t\t\"";
  protected final String TEXT_5 = "\" //$NON-NLS-1$" + NL + "\t\t);" + NL + "\t}" + NL + "\t";
  protected final String TEXT_6 = NL + NL + "\t";
  protected final String TEXT_7 = NL + "\tpublic IModelSearchQuery buildGlobalRegex";
  protected final String TEXT_8 = "ModelSearchQuery(String expr," + NL + "\t\t\tIModelSearchScope<Object, Resource> scope) {" + NL + "\t\treturn buildTextualModelSearchQuery(" + NL + "\t\t\texpr," + NL + "\t\t\t";
  protected final String TEXT_9 = ".eINSTANCE.getEClassifiers()," + NL + "\t\t\tModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION," + NL + "\t\t\tscope," + NL + "\t\t\t\"";
  protected final String TEXT_10 = "\" //$NON-NLS-1$" + NL + "\t\t);" + NL + "\t}" + NL + "\t";
  protected final String TEXT_11 = NL + NL + "\t";
  protected final String TEXT_12 = NL + "\tpublic IModelSearchQuery buildGlobalCaseSensitive";
  protected final String TEXT_13 = "ModelSearchQuery(" + NL + "\t\t\tString expr, IModelSearchScope<Object, Resource> scope) {" + NL + "\t\treturn buildTextualModelSearchQuery(" + NL + "\t\t\texpr," + NL + "\t\t\t";
  protected final String TEXT_14 = ".eINSTANCE.getEClassifiers()," + NL + "\t\t\tModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE," + NL + "\t\t\tscope," + NL + "\t\t\t\"";
  protected final String TEXT_15 = "\" //$NON-NLS-1$" + NL + "\t\t);" + NL + "\t}" + NL + "\t";
  protected final String TEXT_16 = NL + "\t" + NL + "\tpublic IModelSearchQuery buildGlobalTextualModelSearchQuery(String pattern," + NL + "\t\t\tIModelSearchScope<Object, Resource> scope, String nsURI) {" + NL + "\t";
  protected final String TEXT_17 = NL + "\t\tif (nsURI.equals(\"";
  protected final String TEXT_18 = "\")) { //$NON-NLS-1$" + NL + "\t\t\treturn buildGlobalTextual";
  protected final String TEXT_19 = "ModelSearchQuery(" + NL + "\t\t\t\tpattern," + NL + "\t\t\t\tscope" + NL + "\t\t\t);" + NL + "\t\t}" + NL + "\t";
  protected final String TEXT_20 = NL + "\t\treturn null;" + NL + "\t}" + NL + "" + NL + "\tpublic IModelSearchQuery buildGlobalRegexModelSearchQuery(String pattern," + NL + "\t\t\tIModelSearchScope<Object, Resource> scope, String nsURI) {" + NL + "\t";
  protected final String TEXT_21 = NL + "\t\tif (nsURI.equals(\"";
  protected final String TEXT_22 = "\")) { //$NON-NLS-1$" + NL + "\t\t\treturn buildGlobalRegex";
  protected final String TEXT_23 = "ModelSearchQuery(" + NL + "\t\t\t\tpattern," + NL + "\t\t\t\tscope" + NL + "\t\t\t);" + NL + "\t\t}" + NL + "\t";
  protected final String TEXT_24 = NL + "\t\treturn null;" + NL + "\t}" + NL + "" + NL + "\tpublic IModelSearchQuery buildGlobalCaseSensitiveModelSearchQuery(" + NL + "\t\t\tString pattern, IModelSearchScope<Object, Resource> scope," + NL + "\t\t\tString nsURI) {" + NL + "\t";
  protected final String TEXT_25 = NL + "\t\tif (nsURI.equals(\"";
  protected final String TEXT_26 = "\")) { //$NON-NLS-1$" + NL + "\t\t\treturn buildGlobalCaseSensitive";
  protected final String TEXT_27 = "ModelSearchQuery(" + NL + "\t\t\t\tpattern," + NL + "\t\t\t\tscope" + NL + "\t\t\t);" + NL + "\t\t}" + NL + "\t";
  protected final String TEXT_28 = NL + "\t\treturn null;" + NL + "\t}" + NL + "}";
  protected final String TEXT_29 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String basePackage = settings.getGenModel().getModelPluginPackageName();
	List<GenPackage> genPackagesList = settings.getGenModel().getGenPackages();

    stringBuffer.append(TEXT_1);
    
	for (GenPackage p : genPackagesList) {
	
    stringBuffer.append(TEXT_2);
    stringBuffer.append(p.getPackageName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(p.getQualifiedPackageInterfaceName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(p.getNSURI());
    stringBuffer.append(TEXT_5);
     } 
    stringBuffer.append(TEXT_6);
    
	for (GenPackage p : genPackagesList) {
	
    stringBuffer.append(TEXT_7);
    stringBuffer.append(p.getPackageName());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(p.getQualifiedPackageInterfaceName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(p.getNSURI());
    stringBuffer.append(TEXT_10);
     } 
    stringBuffer.append(TEXT_11);
    
	for (GenPackage p : genPackagesList) {
	
    stringBuffer.append(TEXT_12);
    stringBuffer.append(p.getPackageName());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(p.getQualifiedPackageInterfaceName());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(p.getNSURI());
    stringBuffer.append(TEXT_15);
     } 
    stringBuffer.append(TEXT_16);
    
	for (GenPackage p : genPackagesList) {
	
    stringBuffer.append(TEXT_17);
    stringBuffer.append(p.getNSURI());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(p.getPackageName());
    stringBuffer.append(TEXT_19);
     } 
    stringBuffer.append(TEXT_20);
    
	for (GenPackage p : genPackagesList) {
	
    stringBuffer.append(TEXT_21);
    stringBuffer.append(p.getNSURI());
    stringBuffer.append(TEXT_22);
    stringBuffer.append(p.getPackageName());
    stringBuffer.append(TEXT_23);
     } 
    stringBuffer.append(TEXT_24);
    
	for (GenPackage p : genPackagesList) {
	
    stringBuffer.append(TEXT_25);
    stringBuffer.append(p.getNSURI());
    stringBuffer.append(TEXT_26);
    stringBuffer.append(p.getPackageName());
    stringBuffer.append(TEXT_27);
     } 
    stringBuffer.append(TEXT_28);
    stringBuffer.append(TEXT_29);
    return stringBuffer.toString();
  }
}
