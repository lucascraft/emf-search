package org.eclipse.papyrus.infra.emf.search.ecore.scope.workspace;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.core.services.ModelExtensibleSearchEngineExtensionManager;
import org.eclipse.papyrus.infra.emf.search.core.services.ModelSearchEngineDescriptor;

/**
 * Recursively visit ModelResourceScope IResource tree adding each potential 
 * participant according to modelSearchEngine extension point resource
 * validator.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public class ModelSearchScopeResourceVisitor implements IResourceProxyVisitor {
	IModelSearchScope<Object, Resource> modelSearchScope;
	String engineID;
	/**
	 * Visitor looking for search potential participants.
	 * 
	 * @param scope Search scope to populate with potential participants
	 * @param params Current search query parameters
	 */
	public ModelSearchScopeResourceVisitor(IModelSearchScope<Object, Resource> scope, String id) {
		modelSearchScope = scope;
		engineID = id;
	}
	
	/**
	 * Visit each resource proxy and add it if potential search participant.
	 */
	public boolean visit(IResourceProxy proxy) throws CoreException {
		switch (proxy.getType()) {
			case IResource.FILE:
				IResource resource = proxy.requestResource();

			    URI uri = URI.createPlatformResourceURI(resource.getFullPath().toString(), true);
				
				if (isParticipantCurrentSearchEngineValid(uri)) {
					ResourceSet resourceSet =  new ResourceSetImpl();
					resourceSet.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap());

					Resource r = resourceSet.getResource(uri, true);

					if (r instanceof Resource) {
						modelSearchScope.addParticipant(r);
					}
				}
				break;
			default:
				break;
		}
		return true;
	}
	public String getModelSearchEngineID() {
		return engineID;
	}
	public IModelSearchScope<Object, Resource> getModelSearchScope() {
		return modelSearchScope;
	}
	
	/**
	 * Evaluates whether a resource is a potential search participant or not.
	 * 
	 * @param resource Currently visited resource
	 * 
	 * @return true if resource is potential search participant according to the 
	 * contributed validator, false otherwise
	 */
	private boolean isParticipantCurrentSearchEngineValid(URI uri) {
		ModelSearchEngineDescriptor searchEngineDescriptor = ModelExtensibleSearchEngineExtensionManager.getInstance().find(engineID);
		return searchEngineDescriptor.getModelResourceValidator().check(uri);
	}

}
