package org.eclipse.emf.search.ecore.scope.http;

import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.core.services.ModelExtensibleSearchEngineExtensionManager;
import org.eclipse.emf.search.core.services.ModelSearchEngineDescriptor;

/**
 * Recursively visit ModelResourceScope IResource tree adding each potential 
 * participant according to modelSearchEngine extension point resource
 * validator.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public class EcoreModelSearchScopeHttpVisitor {
	IModelSearchScope<Object, Resource> modelSearchScope;
	String searchEngineID;
	
	/**
	 * Visitor looking for search potential participants.
	 * 
	 * @param scope Search scope to populate with potential participants
	 * @param params Current search query parameters
	 */
	public EcoreModelSearchScopeHttpVisitor(IModelSearchScope<Object, Resource> scope, String engineID) {
		modelSearchScope = scope;
		searchEngineID = engineID;
	}
	
	/**
	 * Visit each resource proxy and add it if potential search participant.
	 */
	public boolean visit(URL url) {
		if (isAValidParticipant(url)) {
			// Create a resource set.
			ResourceSet resourceSet = new ResourceSetImpl();

			// Get the URI of the model file.
			URI fileURI = null;
			try {
				fileURI = URI.createURI(url.toURI().toString());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}

			if (fileURI != null) {
				initResourceSet(resourceSet, fileURI);
				try {
					Resource resource = resourceSet.getResource(fileURI, true);
					if (resource instanceof Resource) {
						modelSearchScope.addParticipant(resource);
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
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
		Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
	}
	
	/**
	 * Evaluates whether a resource is a potential search participant or not.
	 * 
	 * @param resource Currently visited resource
	 * 
	 * @return true if resource is potential search participant according to the 
	 * contributed validator, false otherwise
	 */
	protected boolean isAValidParticipant(URL url) {
		ModelSearchEngineDescriptor desc = ModelExtensibleSearchEngineExtensionManager.getInstance().find(searchEngineID);
		return desc.getModelResourceValidator().check(url); //$NON-NLS-1$
	}
}
