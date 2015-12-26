package org.eclipse.emf.search.codegen.jet.templates.core.engine;

import java.util.List;
import org.eclipse.emf.codegen.ecore.genmodel.*;
import org.eclipse.emf.search.codegen.model.generator.*;

public class ModelSearchQuery
{
  protected static String nl;
  public static synchronized ModelSearchQuery create(String lineSeparator)
  {
    nl = lineSeparator;
    ModelSearchQuery result = new ModelSearchQuery();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.engine;" + NL + "" + NL + "import java.util.ArrayList;" + NL + "import java.util.List;" + NL + "" + NL + "import org.eclipse.core.runtime.IStatus;" + NL + "import org.eclipse.core.runtime.Status;" + NL + "import org.eclipse.emf.ecore.EClassifier;" + NL + "import org.eclipse.emf.ecore.ENamedElement;" + NL + "import org.eclipse.emf.ecore.EObject;" + NL + "import org.eclipse.core.runtime.IProgressMonitor;" + NL + "import org.eclipse.core.runtime.NullProgressMonitor;" + NL + "import org.eclipse.emf.search.core.engine.IModelSearchQuery;" + NL + "import org.eclipse.emf.search.core.eval.IModelSearchQueryEvaluator;" + NL + "import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;" + NL + "import org.eclipse.emf.search.core.results.IModelResultEntry;" + NL + "import org.eclipse.emf.search.ecore.engine.EcoreMetaModelIntrospector;" + NL + "import org.eclipse.emf.search.ecore.engine.EcoreModelSearchQuery;" + NL + "import org.eclipse.emf.search.ecore.results.EcoreModelSearchResultEntry;" + NL + "import org.eclipse.emf.search.ecore.utils.EcoreModelLoaderUtil;" + NL + "" + NL + "import search.Activator;" + NL + "import search.l10n.Messages;" + NL + "import search.evaluators.ModelTextualModelSearchQueryEvaluator;" + NL;
  protected final String TEXT_2 = NL + "import ";
  protected final String TEXT_3 = ";";
  protected final String TEXT_4 = " " + NL + "" + NL + "/**" + NL + " * Gather all model search settings to run a specific query." + NL + " * TODO: the name of the class and its file should include modelName" + NL + " * " + NL + " */" + NL + "public final class ModelSearchQuery extends EcoreModelSearchQuery {" + NL + "\tpublic final static String ";
  protected final String TEXT_5 = "_MODEL_SEARCH_IMAGE_PATH = \"icons/esearch.gif\"; //$NON-NLS-1$" + NL + "\tpublic final static String ";
  protected final String TEXT_6 = "_MODEL_SEARCH_RESULT_IMAGE_PATH = \"icons/esearch.gif\"; //$NON-NLS-1$" + NL + "" + NL + "\tfinal static class ";
  protected final String TEXT_7 = "SupportedElements {" + NL + "\t\tprivate static List<EClassifier> get";
  protected final String TEXT_8 = "EClassifiersLiterals() {" + NL + "\t\t\tList<EClassifier> eclassifiersLiteralsList = new ArrayList<EClassifier>();" + NL + "\t\t\t" + NL + "\t\t\ttry {" + NL + "\t\t\t";
  protected final String TEXT_9 = NL + "\t\t\t\tfor (Object o : ";
  protected final String TEXT_10 = ".eINSTANCE.getEClassifiers()) {" + NL + "\t\t\t\t\tif (o instanceof ENamedElement ) {" + NL + "\t\t\t\t\t\teclassifiersLiteralsList.add((EClassifier)o);" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_11 = NL + "\t\t\t} catch (Throwable t) {" + NL + "\t\t\t\tActivator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString(\"ModelSearchQuery.";
  protected final String TEXT_12 = "ClassifiersWalkErrorMessage\"), t)); //$NON-NLS-1$" + NL + "\t\t\t}" + NL + "\t\t\t" + NL + "\t\t\treturn eclassifiersLiteralsList;" + NL + "\t\t}" + NL + "" + NL + "\t\tpublic static List<EClassifier> getSupportedElements(List<? extends Object> participantEClassList) {" + NL + "\t\t\tArrayList<EClassifier> definitiveMetaElementParticipantList = new ArrayList<EClassifier>();" + NL + "\t\t\tfor (EClassifier eClass : get";
  protected final String TEXT_13 = "EClassifiersLiterals()) {" + NL + "\t\t\t\tif (participantEClassList.contains(eClass)) {" + NL + "\t\t\t\t\tdefinitiveMetaElementParticipantList.add(eClass);" + NL + "\t\t\t\t}" + NL + "\t\t\t}" + NL + "\t\t\treturn definitiveMetaElementParticipantList;" + NL + "\t\t}" + NL + "\t}" + NL + "\t" + NL + "\tpublic ModelSearchQuery(String expr, IModelSearchQueryParameters parameters) {" + NL + "\t\tsuper(expr, parameters);" + NL + "\t}" + NL + "\t" + NL + "\t@Override" + NL + "\tpublic String getQueryImagePath() {" + NL + "\t\treturn ";
  protected final String TEXT_14 = "_MODEL_SEARCH_IMAGE_PATH;" + NL + "\t}" + NL + "\t" + NL + "\t@Override" + NL + "\tpublic String getResultImagePath() {" + NL + "\t\treturn ";
  protected final String TEXT_15 = "_MODEL_SEARCH_RESULT_IMAGE_PATH;" + NL + "\t}" + NL + "" + NL + "\t@Override" + NL + "    public String getBundleSymbolicName() {" + NL + "    \treturn Activator.getDefault().getBundle().getSymbolicName();" + NL + "    }" + NL + "\t" + NL + "    @Override" + NL + "    public IStatus search(Object resource, boolean notify) {" + NL + "        return search(resource, notify, new NullProgressMonitor());" + NL + "    }" + NL + "" + NL + "\tpublic IStatus search(Object resource, boolean notify, IProgressMonitor monitor) {" + NL + "\t\ttry {" + NL + "\t\t\tif (monitor.isCanceled()) {" + NL + "\t\t\t\treturn Status.CANCEL_STATUS;" + NL + "\t\t\t}" + NL + "\t\t\t" + NL + "\t\t\tEObject root = EcoreModelLoaderUtil.openFile(resource, false);" + NL + "\t\t\t" + NL + "\t\t\tif (monitor.isCanceled()) {" + NL + "\t\t\t\treturn Status.CANCEL_STATUS;" + NL + "\t\t\t}" + NL + "\t\t\t" + NL + "\t\t\tvalidParticipantMetaElements = EcoreMetaModelIntrospector.discriminateValidMetaElements((EObject)root, ";
  protected final String TEXT_16 = "SupportedElements.getSupportedElements(participantElements));" + NL + "\t\t\t" + NL + "\t\t\tif (monitor.isCanceled()) {" + NL + "\t\t\t\treturn Status.CANCEL_STATUS;" + NL + "\t\t\t}" + NL + "\t\t\t" + NL + "\t\t\t((IModelSearchQueryEvaluator<IModelSearchQuery, Object>)getEvaluator()).eval(this, resource, notify);" + NL + "\t\t\t" + NL + "\t\t\tmonitor.setTaskName(getLabel());" + NL + "\t\t\t" + NL + "\t\t\tif (monitor.isCanceled()) {" + NL + "\t\t\t\treturn Status.CANCEL_STATUS;" + NL + "\t\t\t}" + NL + "\t\t} catch (Exception e) {" + NL + "\t\t\treturn Status.CANCEL_STATUS;" + NL + "\t\t}" + NL + "\t\treturn Status.OK_STATUS;" + NL + "\t}" + NL + "\t" + NL + "\t@Override" + NL + "\tpublic IModelSearchQueryEvaluator<IModelSearchQuery, ?> getEvaluator() {" + NL + "\t\tevaluator = getModelSearchParameters().getEvaluator();" + NL + "\t\t//TODO: get the name of evalutar's class compound of modelName and string \"TextualModelSearchQueryEvaluator\"" + NL + "\t\treturn evaluator!=null?evaluator:(evaluator=new ModelTextualModelSearchQueryEvaluator<IModelSearchQuery, Object>());" + NL + "\t}" + NL + "\t" + NL + "\t@Override" + NL + "\tpublic String getName() {" + NL + "\t   switch(getKind()) {" + NL + "\t\t case NORMAL_TEXT:" + NL + "\t\t   return Messages.getString(\"";
  protected final String TEXT_17 = "ModelSearchQuery.NormalTextMessage\"); //$NON-NLS-1$" + NL + "\t\t case CASE_SENSITIVE:" + NL + "\t\t   return Messages.getString(\"";
  protected final String TEXT_18 = "ModelSearchQuery.CaseSensitiveMessage\"); //$NON-NLS-1$" + NL + "\t\t case REGULAR_EXPRESSION:" + NL + "\t\t   return Messages.getString(\"";
  protected final String TEXT_19 = "ModelSearchQuery.RegularExpressionMessage\"); //$NON-NLS-1$" + NL + "       }" + NL + "\t   " + NL + "\t   return Messages.getString(\"";
  protected final String TEXT_20 = "ModelSearchQuery.DefaultSearchKindMessage\"); //$NON-NLS-1$" + NL + "\t}" + NL + "}";
  protected final String TEXT_21 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String basePackage = settings.getGenModel().getModelPluginPackageName();
	List<GenPackage> genPackagesList = settings.getGenModel().getGenPackages();
	String modelName = settings.getGenModel().getModelName();

    stringBuffer.append(TEXT_1);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_2);
    stringBuffer.append(p.getQualifiedPackageInterfaceName());
    stringBuffer.append(TEXT_3);
     } 
    stringBuffer.append(TEXT_4);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_8);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_9);
    stringBuffer.append(p.getPackageInterfaceName());
    stringBuffer.append(TEXT_10);
     } 
    stringBuffer.append(TEXT_11);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(TEXT_21);
    return stringBuffer.toString();
  }
}
