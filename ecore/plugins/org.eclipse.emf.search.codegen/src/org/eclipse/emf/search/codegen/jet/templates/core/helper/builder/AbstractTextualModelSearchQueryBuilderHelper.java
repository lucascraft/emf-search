package org.eclipse.emf.search.codegen.jet.templates.core.helper.builder;

import org.eclipse.emf.search.codegen.model.generator.*;

public class AbstractTextualModelSearchQueryBuilderHelper
{
  protected static String nl;
  public static synchronized AbstractTextualModelSearchQueryBuilderHelper create(String lineSeparator)
  {
    nl = lineSeparator;
    AbstractTextualModelSearchQueryBuilderHelper result = new AbstractTextualModelSearchQueryBuilderHelper();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.helper.builder;" + NL + "" + NL + "import java.util.List;" + NL + "" + NL + "import org.eclipse.emf.ecore.resource.Resource;" + NL + "" + NL + "import org.eclipse.emf.search.core.engine.IModelSearchQuery;" + NL + "import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;" + NL + "import org.eclipse.emf.search.core.scope.IModelSearchScope;" + NL + "" + NL + "import org.eclipse.emf.search.ecore.helper.builder.AbstractEcoreTextualModelSearchQueryBuilderHelper;" + NL + "import org.eclipse.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;" + NL + "" + NL + "import search.evaluators.ModelTextualModelSearchQueryEvaluator;" + NL + "import search.factories.ModelSearchQueryFactory;" + NL + "import search.factories.ModelSearchQueryParametersFactory;" + NL + "" + NL + "public abstract class AbstractTextualModelSearchQueryBuilderHelper " + NL + "\textends AbstractEcoreTextualModelSearchQueryBuilderHelper {" + NL + "\t" + NL + "\tprotected IModelSearchQueryParameters createParameters(" + NL + "\t\t\tIModelSearchScope<Object, Resource> scope," + NL + "\t\t\tList<? extends Object> participants," + NL + "\t\t\tModelSearchQueryTextualExpressionEnum textualExpression) {" + NL + "\t\t" + NL + "\t\tIModelSearchQueryParameters parameters = ModelSearchQueryParametersFactory.getInstance().createSearchQueryParameters();" + NL + "" + NL + "\t\tparameters.setEvaluator(new ModelTextualModelSearchQueryEvaluator<IModelSearchQuery, Object>());" + NL + "\t\tparameters.setParticipantElements(participants);          " + NL + "\t\tparameters.setScope(scope);" + NL + "\t\t" + NL + "\t\tinitTextualQueryParametersFromPatternKind(parameters, textualExpression);" + NL + "" + NL + "\t\treturn parameters;" + NL + "\t}" + NL + "\t" + NL + "\tprotected IModelSearchQuery createQuery(" + NL + "\t\t\tString pattern," + NL + "\t\t\tIModelSearchQueryParameters parameters) {\t\t" + NL + "" + NL + "\t\treturn ModelSearchQueryFactory.getInstance().createModelSearchQuery(pattern, parameters);" + NL + "\t}" + NL + "}";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String basePackage = settings.getGenModel().getModelPluginPackageName();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
