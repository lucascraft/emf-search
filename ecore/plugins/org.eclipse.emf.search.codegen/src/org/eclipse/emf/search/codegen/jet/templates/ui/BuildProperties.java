package org.eclipse.emf.search.codegen.jet.templates.ui;

public class BuildProperties
{
  protected static String nl;
  public static synchronized BuildProperties create(String lineSeparator)
  {
    nl = lineSeparator;
    BuildProperties result = new BuildProperties();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t  " + NL + "source.. = src/" + NL + "output.. = bin/" + NL + "bin.includes = META-INF/,\\" + NL + "               .,\\" + NL + "               plugin.xml,\\" + NL + "               icons/,\\" + NL + "               plugin.properties,\\" + NL + "               messages.properties" + NL + "               ";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
