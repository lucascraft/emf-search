package org.eclipse.emf.search.codegen.jet.templates.core.evaluators;

import org.eclipse.emf.codegen.ecore.genmodel.*;
import org.eclipse.emf.search.codegen.model.generator.*;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;

public class ModelTextualModelSearchQueryEvaluator
{
  protected static String nl;
  public static synchronized ModelTextualModelSearchQueryEvaluator create(String lineSeparator)
  {
    nl = lineSeparator;
    ModelTextualModelSearchQueryEvaluator result = new ModelTextualModelSearchQueryEvaluator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.evaluators;" + NL + "" + NL + "import java.util.ArrayList;" + NL + "import java.util.List;" + NL + "" + NL + "import org.eclipse.emf.search.core.engine.IModelSearchQuery;" + NL + "import org.eclipse.emf.search.ecore.engine.EcoreModelSearchQuery;" + NL + "import org.eclipse.emf.search.ecore.evaluators.EcoreTextualModelSearchQueryEvaluator;" + NL + "import org.eclipse.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;" + NL + "import org.eclipse.emf.search.ecore.regex.ModelSearchQueryTextualExpressionMatchingHelper;" + NL + "" + NL + "import org.eclipse.emf.ecore.EObject;" + NL + "import org.eclipse.emf.ecore.EStructuralFeature;" + NL + "import org.eclipse.emf.ecore.resource.Resource;" + NL + "import org.eclipse.emf.ecore.ETypedElement;" + NL + "" + NL + "import search.util.TextualFeaturesUtils;" + NL + "" + NL + "import search.l10n.Messages;" + NL + "" + NL + "public final class ModelTextualModelSearchQueryEvaluator<Q extends IModelSearchQuery, T> extends" + NL + "EcoreTextualModelSearchQueryEvaluator<Q, T> {" + NL + "" + NL + "\t@Override" + NL + "\tpublic List<?> eval(Q query, T target, boolean notification) {" + NL + "\t\tList<Object> results = new ArrayList<Object>();" + NL + "\t\tif (query instanceof EcoreModelSearchQuery) {" + NL + "\t\t\t// discriminating according to participant meta elements selection" + NL + "\t\t\tfor (Object o : query.getValidParticipantMetaElements()) {" + NL + "\t\t\t\t// In order to avoid duplicate results, the current element should be contained " + NL + "\t\t\t\t// by the current resource" + NL + "\t\t\t\tif (target instanceof Resource) {" + NL + "\t\t\t\t\tif (o instanceof EObject) {" + NL + "\t\t\t\t\t\tResource r = ((EObject) o).eResource();" + NL + "\t\t\t\t\t\tif (r instanceof Resource" + NL + "\t\t\t\t\t\t\t\t&& r.getURI().equals(" + NL + "\t\t\t\t\t\t\t\t\t\t((Resource) target).getURI())) {" + NL + "\t\t\t\t\t\t\tprocessVisitedElement(" + NL + "\t\t\t\t\t\t\t\t\t(EObject) o," + NL + "\t\t\t\t\t\t\t\t\tresults," + NL + "\t\t\t\t\t\t\t\t\tquery," + NL + "\t\t\t\t\t\t\t\t\ttarget," + NL + "\t\t\t\t\t\t\t\t\tnotification" + NL + "\t\t\t\t\t\t\t);" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t\treturn results;" + NL + "\t}" + NL + "\t" + NL + "\tprivate String computeExpression(Q query, ModelSearchQueryTextualExpressionEnum kind) {" + NL + "\t\tString expression = query.getQueryExpression();" + NL + "\t\texpression = (" + NL + "\t\t\t\texpression == \"\" &&  //$NON-NLS-1$ " + NL + "\t\t\t\tkind == ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT" + NL + "\t\t\t\t) ? " + NL + "\t\t\t\t\t\t\"*\" : //$NON-NLS-2$" + NL + "\t\t\t\t\t\t\texpression;" + NL + "\t\treturn expression;" + NL + "\t}" + NL + "\t" + NL + "\tprivate String resolveText(EObject eObj, ETypedElement eTypedElem) {" + NL + "\t\treturn TextualFeaturesUtils" + NL + "\t\t.instance()" + NL + "\t\t.getTextFromETypedElement(eObj," + NL + "\t\t\t\teTypedElem);" + NL + "\t}" + NL + "\t" + NL + "\tprivate boolean matchExpression(" + NL + "\t\t\tString valuation," + NL + "\t\t\tString expression," + NL + "\t\t\tModelSearchQueryTextualExpressionEnum kind" + NL + "\t) {" + NL + "\t\treturn valuation != null" + NL + "\t\t&& ModelSearchQueryTextualExpressionMatchingHelper" + NL + "\t\t.getInstance().lookAt(" + NL + "\t\t\t\tvaluation, expression," + NL + "\t\t\t\tkind);" + NL + "\t}" + NL + "\t" + NL + "\tprivate boolean isAValidAttribute(ETypedElement eTypedElem) {" + NL + "\t\treturn TextualFeaturesUtils.instance()." + NL + "\t\t\tgetParticipantTextualTypedElement().contains(eTypedElem);" + NL + "\t}" + NL + "\t" + NL + "\tprivate void processVisitedElement(" + NL + "\t\t\tEObject eObj, " + NL + "\t\t\tList<Object> results," + NL + "\t\t\tQ query," + NL + "\t\t\tT target," + NL + "\t\t\tboolean notification" + NL + "\t) {" + NL + "\t\tif (TextualFeaturesUtils.instance()" + NL + "\t\t\t\t.getTextFromEStructuralFeatureIfAny(eObj) != null) {" + NL + "            List<EStructuralFeature> eligibleFeatures = TextualFeaturesUtils.instance()." + NL + "                getEStructuralFeaturesFromQueryAndEObject(" + NL + "                    eObj," + NL + "                    query" + NL + "                );" + NL + "            " + NL + "            for (ETypedElement eTypedElem : eligibleFeatures) {" + NL + "\t\t\t\tif (isAValidAttribute(eTypedElem)) {" + NL + "\t\t\t\t\tModelSearchQueryTextualExpressionEnum kind = " + NL + "\t\t\t\t\t\t((EcoreModelSearchQuery) query).getKind();" + NL + "\t\t\t\t\tString valuation = resolveText(eObj, eTypedElem);" + NL + "\t\t\t\t\tString expression = computeExpression(query, kind);" + NL + "\t\t\t\t\tif (matchExpression(valuation, expression, kind)) {" + NL + "\t\t\t\t\t\tresults.add(" + NL + "\t\t\t\t\t\t\t\tquery.processSearchResultOccurence(" + NL + "\t\t\t\t\t\t\t\t\t\ttarget," + NL + "\t\t\t\t\t\t\t\t\t\teObj," + NL + "\t\t\t\t\t\t\t\t\t\teTypedElem," + NL + "\t\t\t\t\t\t\t\t\t\tvaluation," + NL + "\t\t\t\t\t\t\t\t\t\tnotification" + NL + "\t\t\t\t\t\t\t\t)" + NL + "\t\t\t\t\t\t);" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "\t@Override" + NL + "\tpublic String getLabel() {" + NL + "\t\treturn Messages" + NL + "\t\t\t\t.getString(\"ModelTextualModelSearchQueryEvaluator.Label\"); //$NON-NLS-1$" + NL + "\t}" + NL + "}";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	List<EStringAccessor> genTypeParameter = settings.getTextualSettings().getEStringAccessors();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
