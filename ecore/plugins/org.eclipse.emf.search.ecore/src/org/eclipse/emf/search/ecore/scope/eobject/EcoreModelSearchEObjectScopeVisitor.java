package org.eclipse.emf.search.ecore.scope.eobject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.search.core.scope.IModelSearchScope;

/**
 * Recursively visit ModelResourceScope IResource tree adding each potential
 * participant according to modelSearchEngine extension point resource
 * validator.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public class EcoreModelSearchEObjectScopeVisitor {
	IModelSearchScope<Object, Resource> modelSearchScope;
	String searchEngineID;
	Resource stupidResource;

	/**
	 * Visitor looking for search potential participants.
	 * 
	 * @param scope
	 *            Search scope to populate with potential participants
	 * @param params
	 *            Current search query parameters
	 */
	public EcoreModelSearchEObjectScopeVisitor(
			IModelSearchScope<Object, Resource> scope, String engineID) {
		modelSearchScope = scope;
		searchEngineID = engineID;
		stupidResource = new ResourceImpl();
	}

	/**
	 * Visit each resource proxy and add it if potential search participant.
	 */
	public boolean visit(EObject eObj) {
		if (isParticipantValid(eObj)) {
			if (eObj.eResource() != null) {
				try {
					if (!modelSearchScope.getParticipants().contains(
							eObj.eResource())) {
						modelSearchScope.addParticipant(eObj.eResource());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	public IModelSearchScope<Object, Resource> getModelSearchScope() {
		return modelSearchScope;
	}

	protected void initResourceSet(ResourceSet resourceSet, URI fileURI) {
		// Register the default resource factory -- only needed for stand-alone!
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());
	}

	/**
	 * Evaluates whether a resource is a potential search participant or not.
	 * 
	 * @param resource
	 *            Currently visited {@link EObject}
	 * 
	 * @return true if resource is potential search participant according to the
	 *         contributed validator, false otherwise
	 */
	protected boolean isParticipantValid(EObject eObj) {
		if (eObj.eResource() == null) {
			stupidResource.getContents().add(eObj);
		}
		return eObj != null;
	}
}
