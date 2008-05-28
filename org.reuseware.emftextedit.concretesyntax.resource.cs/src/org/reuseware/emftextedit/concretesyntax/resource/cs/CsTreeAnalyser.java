package org.reuseware.emftextedit.concretesyntax.resource.cs; 

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.reuseware.emftextedit.resource.TextResource;
import org.reuseware.emftextedit.resource.impl.*;

import org.reuseware.emftextedit.concretesyntax.ConcreteSyntax;
import org.reuseware.emftextedit.concretesyntax.Terminal;
import org.reuseware.emftextedit.concretesyntax.DefinedPlaceholder;
import org.reuseware.emftextedit.concretesyntax.Rule;
import org.reuseware.emftextedit.concretesyntax.Import;
import org.reuseware.emftextedit.concretesyntax.resource.cs.analysis.*;

public class CsTreeAnalyser extends EMFTextTreeAnalyserImpl {

	protected ConcreteSyntaxStartSymbolsProxyResolver concreteSyntaxStartSymbolsProxyResolver = new ConcreteSyntaxStartSymbolsProxyResolver();

	protected TerminalFeatureProxyResolver terminalFeatureProxyResolver = new TerminalFeatureProxyResolver();

	protected DefinedPlaceholderTokenProxyResolver definedPlaceholderTokenProxyResolver = new DefinedPlaceholderTokenProxyResolver();

	protected RuleMetaclassProxyResolver ruleMetaclassProxyResolver = new RuleMetaclassProxyResolver();

	protected ImportConcreteSyntaxProxyResolver importConcreteSyntaxProxyResolver = new ImportConcreteSyntaxProxyResolver();

	protected ImportPackageProxyResolver importPackageProxyResolver = new ImportPackageProxyResolver();

	protected ConcreteSyntaxPackageProxyResolver concreteSyntaxPackageProxyResolver = new ConcreteSyntaxPackageProxyResolver();

	public EObject resolve(InternalEObject proxy, EObject container, EReference reference, TextResource resource, boolean reportErrors) {
		if (container instanceof ConcreteSyntax && reference.getFeatureID() == 3) {
			return concreteSyntaxStartSymbolsProxyResolver.resolve(proxy,container,reference,resource,reportErrors);
			}
		if (container instanceof Terminal && reference.getFeatureID() == 1) {
			return terminalFeatureProxyResolver.resolve(proxy,container,reference,resource,reportErrors);
			}
		if (container instanceof DefinedPlaceholder && reference.getFeatureID() == 2) {
			return definedPlaceholderTokenProxyResolver.resolve(proxy,container,reference,resource,reportErrors);
			}
		if (container instanceof Rule && reference.getFeatureID() == 1) {
			return ruleMetaclassProxyResolver.resolve(proxy,container,reference,resource,reportErrors);
			}
		if (container instanceof Import && reference.getFeatureID() == 1) {
			return importConcreteSyntaxProxyResolver.resolve(proxy,container,reference,resource,reportErrors);
			}
		if (container instanceof Import && reference.getFeatureID() == 2) {
			return importPackageProxyResolver.resolve(proxy,container,reference,resource,reportErrors);
			}
		if (container instanceof ConcreteSyntax && reference.getFeatureID() == 1) {
			return concreteSyntaxPackageProxyResolver.resolve(proxy,container,reference,resource,reportErrors);
			}
		return null;
	}

	public String deResolve(EObject refObject, EObject container, EReference reference) {
		if (container instanceof ConcreteSyntax && reference.getFeatureID() == 3) {
			return concreteSyntaxStartSymbolsProxyResolver.deResolve(refObject,container,reference);
			}
		if (container instanceof Terminal && reference.getFeatureID() == 1) {
			return terminalFeatureProxyResolver.deResolve(refObject,container,reference);
			}
		if (container instanceof DefinedPlaceholder && reference.getFeatureID() == 2) {
			return definedPlaceholderTokenProxyResolver.deResolve(refObject,container,reference);
			}
		if (container instanceof Rule && reference.getFeatureID() == 1) {
			return ruleMetaclassProxyResolver.deResolve(refObject,container,reference);
			}
		if (container instanceof Import && reference.getFeatureID() == 1) {
			return importConcreteSyntaxProxyResolver.deResolve(refObject,container,reference);
			}
		if (container instanceof Import && reference.getFeatureID() == 2) {
			return importPackageProxyResolver.deResolve(refObject,container,reference);
			}
		if (container instanceof ConcreteSyntax && reference.getFeatureID() == 1) {
			return concreteSyntaxPackageProxyResolver.deResolve(refObject,container,reference);
			}
		return null;
	}

}
