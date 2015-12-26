package org.eclipse.emf.search.codegen.jet.templates.ui.pages;

import java.util.List;
import java.util.ArrayList;
import org.eclipse.emf.codegen.ecore.genmodel.*;
import org.eclipse.emf.search.codegen.model.generator.*;

public class ModelSearchPage
{
  protected static String nl;
  public static synchronized ModelSearchPage create(String lineSeparator)
  {
    nl = lineSeparator;
    ModelSearchPage result = new ModelSearchPage();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package search.ui.pages;" + NL + "" + NL + "import org.eclipse.emf.ecore.EObject;" + NL + "import org.eclipse.emf.search.core.internal.replace.provisional.ITransformation;" + NL + "import org.eclipse.emf.search.core.internal.replace.provisional.NullModelSearchTransformation;" + NL + "import org.eclipse.emf.search.core.engine.IModelSearchQuery;" + NL + "import org.eclipse.emf.search.core.results.IModelResultEntry;" + NL + "import org.eclipse.emf.search.ui.pages.AbstractModelSearchPage;" + NL + "" + NL + "import search.replace.TextualReplaceTransformation;" + NL + "import search.util.TextualFeaturesUtils;" + NL + "" + NL + "public final class ModelSearchPage extends AbstractModelSearchPage {" + NL + "" + NL + "\t@Override" + NL + "\tprotected String getModelSearchPageID() {" + NL + "\t\treturn \"search.ui.pages.";
  protected final String TEXT_2 = "ModelSearchPageID\"; //$NON-NLS-1$" + NL + "\t}" + NL + "\tpublic String getOccurenceLabel(IModelResultEntry entry) {" + NL + "\t\treturn entry.getSource() instanceof EObject ? TextualFeaturesUtils.instance().getTextFromEStructuralFeatureIfAny((EObject)entry.getSource()): \"ERROR\"; //$NON-NLS-1$" + NL + "\t}" + NL + "\t@Override" + NL + "\tpublic ITransformation<EObject, IModelSearchQuery, String, String> getTransformation(EObject element, IModelSearchQuery query, String value) {" + NL + "\t\treturn  TextualFeaturesUtils.instance().getTextFromEStructuralFeatureIfAny(element) != null ? new TextualReplaceTransformation((EObject)element, query, value) : new NullModelSearchTransformation();" + NL + "\t}" + NL + "}";
  protected final String TEXT_3 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String basePackage = settings.getGenModel().getModelPluginID();
	String modelBasePackage = settings.getGenModel().getModelPluginPackageName();
	String editorBasePackage = settings.getGenModel().getEditorPluginPackageName();
	List<GenPackage> genPackagesList = settings.getGenModel().getGenPackages();
	String modelName = settings.getGenModel().getModelName();
	List<EStringAccessor> genTypeParameter = settings.getTextualSettings().getEStringAccessors();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(modelName);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    return stringBuffer.toString();
  }
}
