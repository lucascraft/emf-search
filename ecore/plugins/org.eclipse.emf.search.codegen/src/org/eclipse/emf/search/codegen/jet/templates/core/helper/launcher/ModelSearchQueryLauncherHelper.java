package org.eclipse.emf.search.codegen.jet.templates.core.helper.launcher;

import org.eclipse.emf.search.codegen.model.generator.*;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import java.util.List;

public class ModelSearchQueryLauncherHelper
{
  protected static String nl;
  public static synchronized ModelSearchQueryLauncherHelper create(String lineSeparator)
  {
    nl = lineSeparator;
    ModelSearchQueryLauncherHelper result = new ModelSearchQueryLauncherHelper();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.helper.launcher;" + NL + "" + NL + "import org.eclipse.core.runtime.NullProgressMonitor;" + NL + "import org.eclipse.emf.ecore.resource.Resource;" + NL + "import org.eclipse.emf.search.core.engine.IModelSearchQuery;" + NL + "import org.eclipse.emf.search.core.results.IModelSearchResult;" + NL + "import org.eclipse.emf.search.core.scope.IModelSearchScope;" + NL + "import org.eclipse.emf.search.ecore.helper.launcher.EcoreModelSearchQueryLauncherHelper;" + NL + "import org.eclipse.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;" + NL + "" + NL + "public class ModelSearchQueryLauncherHelper extends EcoreModelSearchQueryLauncherHelper {" + NL + "" + NL + "\tprivate static ModelSearchQueryLauncherHelper instance;" + NL + "\t" + NL + "\t// Singleton" + NL + "\tpublic static ModelSearchQueryLauncherHelper getInstance() {" + NL + "\t\treturn instance==null?instance = new ModelSearchQueryLauncherHelper():instance;" + NL + "\t}" + NL + "\t" + NL + "\t//" + NL + "\t// NORMAL TEXT" + NL + "\t//" + NL + "\t";
  protected final String TEXT_2 = NL + "\tpublic IModelSearchResult launchGlobalTextual";
  protected final String TEXT_3 = "ModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope) {" + NL + "\t\tIModelSearchQuery q = buildTextualModelSearchQuery(" + NL + "\t\t\tpattern," + NL + "\t\t\t";
  protected final String TEXT_4 = ".eINSTANCE.getEClassifiers(), " + NL + "\t\t\tModelSearchQueryTextualExpressionEnum.NORMAL_TEXT," + NL + "\t\t\tscope," + NL + "\t\t\t\"";
  protected final String TEXT_5 = "\" //$NON-NLS-1$" + NL + "\t\t);" + NL + "\t\tq.run(new NullProgressMonitor());" + NL + "\t\treturn q.getModelSearchResult();" + NL + "\t}" + NL + "\t";
  protected final String TEXT_6 = " " + NL + "" + NL + "\t//" + NL + "\t// REGEX" + NL + "\t//" + NL + "\t";
  protected final String TEXT_7 = NL + "\tpublic IModelSearchResult launchGlobalRegex";
  protected final String TEXT_8 = ".eINSTANCE.getEClassifiers()," + NL + "\t\t\tModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION," + NL + "\t\t\tscope," + NL + "\t\t\t\"";
  protected final String TEXT_9 = " " + NL + "\t" + NL + "\t//" + NL + "\t// CASE SENSITIVE" + NL + "\t//" + NL + "\t";
  protected final String TEXT_10 = NL + "\tpublic IModelSearchResult launchGlobalCaseSensitive";
  protected final String TEXT_11 = "ModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope) {" + NL + "\t\tIModelSearchQuery q = buildTextualModelSearchQuery(" + NL + "\t\tpattern," + NL + "\t\t";
  protected final String TEXT_12 = ".eINSTANCE.getEClassifiers()," + NL + "\t\tModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE," + NL + "\t\tscope," + NL + "\t\t\"";
  protected final String TEXT_13 = "\" //$NON-NLS-1$" + NL + "\t);" + NL + "\t\tq.run(new NullProgressMonitor());" + NL + "\t\treturn q.getModelSearchResult();" + NL + "\t}" + NL + "\t";
  protected final String TEXT_14 = " " + NL + "}";
  protected final String TEXT_15 = NL;

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
    stringBuffer.append(TEXT_3);
    stringBuffer.append(p.getQualifiedPackageInterfaceName());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(p.getNSURI());
    stringBuffer.append(TEXT_5);
     } 
    stringBuffer.append(TEXT_9);
     for (GenPackage p : genPackagesList) { 
    stringBuffer.append(TEXT_10);
    stringBuffer.append(p.getPackageName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(p.getQualifiedPackageInterfaceName());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(p.getNSURI());
    stringBuffer.append(TEXT_13);
     } 
    stringBuffer.append(TEXT_14);
    stringBuffer.append(TEXT_15);
    return stringBuffer.toString();
  }
}
