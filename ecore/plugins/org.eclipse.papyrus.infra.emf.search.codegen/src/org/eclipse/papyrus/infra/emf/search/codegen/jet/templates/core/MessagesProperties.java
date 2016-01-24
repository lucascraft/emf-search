package org.eclipse.papyrus.infra.emf.search.codegen.jet.templates.core;

import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.*;

public class MessagesProperties
{
  protected static String nl;
  public static synchronized MessagesProperties create(String lineSeparator)
  {
    nl = lineSeparator;
    MessagesProperties result = new MessagesProperties();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "ModelSearchQuery.GenClassifiersWalkErrorMessage=impossible to walk Model classifiers" + NL + "ModelSearchQuery.GenNormalTextMessage=\\ Model Normal Text" + NL + "ModelSearchQuery.GenCaseSensitiveMessage=\\ Model Case Sensitive" + NL + "ModelSearchQuery.GenRegularExpressionMessage=\\ Model Regular Expression" + NL + "ModelSearchQuery.GenMessage=\\ GenModel" + NL + "" + NL + "TextualModelSearchQueryEvaluator.Label=Model Query" + NL;
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ModelSearchGenSettings settings = (ModelSearchGenSettings) argument;
	String modelName = settings.getGenModel().getModelName();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
