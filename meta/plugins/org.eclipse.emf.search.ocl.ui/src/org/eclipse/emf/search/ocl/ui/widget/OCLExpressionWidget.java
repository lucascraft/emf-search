/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * OCLExpressionWidget.java
 * 
 * Contributors:
 *		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 *		Lucas Bigeardel - layouts
 ******************************************************************************/

package org.eclipse.emf.search.ocl.ui.widget;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.DelegatingPackageRegistry;
import org.eclipse.emf.search.ocl.engine.IOCLFactory;
import org.eclipse.emf.search.ocl.engine.ModelingLevel;
import org.eclipse.emf.search.ocl.engine.TargetMetamodel;
import org.eclipse.emf.search.ocl.ui.viewer.ColorManager;
import org.eclipse.emf.search.ocl.ui.viewer.OCLDocument;
import org.eclipse.emf.search.ocl.ui.viewer.OCLSourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.ocl.AbstractEnvironmentFactory;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.ConstraintKind;
import org.eclipse.ocl.helper.OCLHelper;
import org.eclipse.ocl.uml.UMLEnvironmentFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.NamedElement;

public final class OCLExpressionWidget extends Composite {
	private final OCLSourceViewer viewer;
	private final OCLDocument document;
	private EObject context;
	private TargetMetamodel targetMetaModel = TargetMetamodel.Ecore;
	private AbstractEnvironmentFactory<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> environmentFactory;
	private boolean contentAssistEnabled;

	private final IOCLFactory<Object> ocl4EcoreFactory = new EcoreOCLFactory();
	private final IOCLFactory<Object> ocl4UmlFactory = new UMLOCLFactory();

	private ModelingLevel modelingLevel = ModelingLevel.M2;

	public TargetMetamodel getTargetMetamodel() {
		return targetMetaModel;
	}

	public void setTargetMetamodel(TargetMetamodel metamodel) {
		targetMetaModel = metamodel;
		document.setOCLFactory(getOCLFactory());
	}

	public ModelingLevel getModelingLevel() {
		return modelingLevel;
	}

	public void setModelingLevel(ModelingLevel level) {
		modelingLevel = level;
	}

	public EObject getContext() {
		return context;
	}

	public void setContext(EObject ctx) {
		context = ctx;
		document.setOCLContext(ctx);
	}

	public AbstractEnvironmentFactory<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> getEnvironmentFactory() {
		return environmentFactory;
	}

	public void setEnvironmentFactory(
			AbstractEnvironmentFactory<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> envFactory) {
		environmentFactory = envFactory;
	}

	/**
	 * A key listener that listens for the Enter key to evaluate the OCL
	 * expression.
	 */
	private class InputKeyListener implements KeyListener {
		private boolean evaluationSuccess = false;
		boolean contentAssistEnabled;

		public InputKeyListener() {
			this(true);
		}

		public InputKeyListener(boolean contentAssistEnabled) {
			this.contentAssistEnabled = contentAssistEnabled;
		}

		public void keyPressed(KeyEvent e) {
			switch (e.keyCode) {
			case SWT.CR:
				if (!viewer.isContentAssistActive()
						&& (e.stateMask & (SWT.CTRL | SWT.SHIFT)) == 0) {
					String text = document.get();
					evaluationSuccess = evaluate(text);
				}
				break;
			}
		}

		public void keyReleased(KeyEvent e) {
			switch (e.keyCode) {
			case SWT.CR:
				if ((e.stateMask & SWT.CTRL) == 0) {
					if (evaluationSuccess) {
						document.set(""); //$NON-NLS-1$
					}
					evaluationSuccess = false;
				}
				break;
			case ' ':
				if (contentAssistEnabled) {
					if ((e.stateMask & SWT.CTRL) == SWT.CTRL) {
						viewer.getContentAssistant().showPossibleCompletions();
					}
				}
			}
		}
	}

	private class EcoreOCLFactory implements IOCLFactory<Object> {
		public TargetMetamodel getTargetMetamodel() {
			return targetMetaModel;
		}

		@SuppressWarnings("unchecked")
		public OCL<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> createOCL() {
			return OCL.newInstance(new EcoreEnvironmentFactory());
		}

		@SuppressWarnings("unchecked")
		public OCL<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> createOCL(Resource res) {
			return OCL.newInstance(new EcoreEnvironmentFactory(
					new DelegatingPackageRegistry(context.eResource()
							.getResourceSet().getPackageRegistry(),
							EPackage.Registry.INSTANCE)), res);
		}

		public Object getContextClassifier(EObject object) {
			return context.eClass();
		}

		public String getName(Object modelElement) {
			return ((ENamedElement) modelElement).getName();
		}
	}

	private class UMLOCLFactory implements IOCLFactory<Object> {
		public TargetMetamodel getTargetMetamodel() {
			return targetMetaModel;
		}

		@SuppressWarnings("unchecked")
		public OCL<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> createOCL() {
			return OCL.newInstance(new UMLEnvironmentFactory());
		}

		@SuppressWarnings("unchecked")
		public OCL<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> createOCL(Resource res) {
			return OCL.newInstance(new UMLEnvironmentFactory(
					new DelegatingPackageRegistry(context.eResource()
							.getResourceSet().getPackageRegistry(),
							EPackage.Registry.INSTANCE), res.getResourceSet()));
		}

		public Object getContextClassifier(EObject object) {
			return context; //M1
		}

		public String getName(Object modelElement) {
			return ((NamedElement) modelElement).getName();
		}
	}

	private OCL<?, Object, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> createOCL() {
		switch (targetMetaModel) {
			case Ecore:
				return ocl4EcoreFactory.createOCL();
			case UML:
				return ocl4UmlFactory.createOCL();
			default:
				return ocl4EcoreFactory.createOCL();
		}
	}

	private IOCLFactory<Object> getOCLFactory() {
		switch (getTargetMetamodel()) {
			case Ecore:
				return ocl4EcoreFactory;
			case UML:
				return ocl4UmlFactory;
			default:
				return ocl4EcoreFactory;
		}
	}

	/**
	 * Evaluates an OCL expression using the OCL Interpreter's {@link OCLHelper}
	 * API.
	 * 
	 * @param expression
	 *            an OCL expression
	 * 
	 * @return <code>true</code> on successful evaluation; <code>false</code>
	 *         if the expression failed to parse or evaluate
	 */
	boolean evaluate(String expression) {
		boolean result = true;

		if (context == null) {
			result = false;
		} else {
			// create an OCL helper to do our parsing and evaluating
			OCL<?, Object, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> ocl = createOCL();
			OCLHelper<Object, ?, ?, ?> helper = ocl.createOCLHelper();

			// set our helper's context classifier to parse against it
			ConstraintKind kind = modelingLevel.setContext(helper, context,
					getOCLFactory());

			try {
				switch (modelingLevel) {
					case M2:
						OCLExpression<Object> parsed = helper
								.createQuery(expression);
						Query<Object, ?, ?> query = ocl.createQuery(parsed);
						System.out.println(query.evaluate(context));
						break;
					case M1:
						helper.createConstraint(kind, expression);
						break;
				}
				// lastOCLExpression = expression;
			} catch (Exception e) {
				result = false;
				System.out.println(e.getLocalizedMessage());
			}
		}
		return result;
	}
	
	public OCLExpressionWidget(Composite parent) {
		this(parent, true);
	}
	
	public OCLExpressionWidget(Composite parent, boolean contentAssistEnablement) {
		super(parent, SWT.NONE);
		viewer = new OCLSourceViewer(parent, new ColorManager(), SWT.BORDER | SWT.MULTI);
		contentAssistEnabled = contentAssistEnablement;
		
		document = new OCLDocument();
		document.setModelingLevel(modelingLevel);
		viewer.setDocument(document);

		viewer.getTextWidget().addKeyListener(new InputKeyListener(contentAssistEnablement));
		viewer.getTextWidget().setLayout(new GridLayout());

		GridData gd1 = new GridData(GridData.FILL_BOTH);
		gd1.heightHint = gd1.minimumHeight = 150;
		viewer.getTextWidget().setLayoutData(gd1);
		
		GridData gd2 = new GridData(GridData.FILL_BOTH);
		gd2.heightHint = gd2.minimumHeight = 150;

		viewer.getControl().setLayoutData(gd2);
	}
	
	public boolean evaluate() {
		return evaluate(getExpression());
	}

	public String getExpression() {
		return document.get();
	}

	public void setExpression(String expr) {
		document.set(expr);
	}

	public SourceViewer getViewer() {
		return viewer;
	}
}
