/*******************************************************************************
 * Copyright (c) 2006-2012
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH, Berlin, Amtsgericht Charlottenburg, HRB 140026
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Berlin, Germany
 *      - initial API and implementation
 ******************************************************************************/
package org.emftext.sdk.concretesyntax.resource.cs.postprocessing.syntax_extension;

import java.util.Collection;

import org.emftext.sdk.EPredefinedTokens;
import org.emftext.sdk.OptionManager;
import org.emftext.sdk.concretesyntax.CompleteTokenDefinition;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.ConcretesyntaxPackage;
import org.emftext.sdk.concretesyntax.OptionTypes;
import org.emftext.sdk.concretesyntax.PlaceholderUsingDefaultToken;
import org.emftext.sdk.concretesyntax.resource.cs.mopp.CsAnalysisProblemType;
import org.emftext.sdk.concretesyntax.resource.cs.postprocessing.AbstractPostProcessor;
import org.emftext.sdk.util.EObjectUtil;

/**
 * The DefaultTokenConnector looks for PlaceholderUsingDefaultToken. 
 * For each PlaceholderUsingDefaultToken that is found in the resource, 
 * the 'token' reference is set.
 */
public class DefaultTokenConnector extends AbstractPostProcessor {

	@Override
	public void analyse(ConcreteSyntax syntax) {
		String standardTokenName = OptionManager.INSTANCE.getStringOptionValue(syntax, OptionTypes.DEFAULT_TOKEN_NAME);
		if (standardTokenName == null) {
			standardTokenName = EPredefinedTokens.STANDARD.getTokenName();
		}

		CompleteTokenDefinition definition = findToken(syntax, standardTokenName);
		
		Collection<PlaceholderUsingDefaultToken> placeholders = EObjectUtil.getObjectsByType(syntax.eAllContents(), ConcretesyntaxPackage.eINSTANCE.getPlaceholderUsingDefaultToken());
		for (PlaceholderUsingDefaultToken placeholder : placeholders) {
			// this placeholder must use the standard token
			if (definition == null) {
				addProblem(CsAnalysisProblemType.DEFAULT_TOKEN_NOT_DEFINED, "There is no token definition for the default token \"" + standardTokenName + "\".", placeholder);
			} else {
				placeholder.setToken(definition);
			}
		}
	}

	private CompleteTokenDefinition findToken(ConcreteSyntax syntax, String tokenName) {
		for (CompleteTokenDefinition next : syntax.getActiveTokens()) {
			if (tokenName.equals(next.getName())) {
				return next;
			}
		}
		return null;
	}
}
