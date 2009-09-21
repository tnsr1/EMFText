/*******************************************************************************
 * Copyright (c) 2006-2009 
 * Software Technology Group, Dresden University of Technology
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * See the GNU Lesser General Public License for more details. You should have
 * received a copy of the GNU Lesser General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA  02111-1307 USA
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany 
 *   - initial API and implementation
 ******************************************************************************/
package org.emftext.sdk.syntax_analysis;

import java.util.ArrayList;
import java.util.List;

import org.emftext.sdk.AbstractPostProcessor;
import org.emftext.sdk.ECsProblemType;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.TokenDefinition;
import org.emftext.sdk.concretesyntax.resource.cs.CsResource;

/**
 * A analyser that checks that all token names start with a capital letter.
 */
public class TokenNameAnalyser extends AbstractPostProcessor {

	@Override
	public void analyse(CsResource resource, ConcreteSyntax syntax) {
		List<TokenDefinition> wrongDefinitions = getTokenDefinitionsWithInvalidNames(syntax);
		for (TokenDefinition next : wrongDefinitions) {
			addProblem(resource, ECsProblemType.INVALID_TOKEN_NAME, "Token names must start with a capital letter.", next);
		}
	}

	public List<TokenDefinition> getTokenDefinitionsWithInvalidNames(ConcreteSyntax syntax) {
		List<TokenDefinition> result = new ArrayList<TokenDefinition>();
		for (TokenDefinition definition : syntax.getActiveTokens()) {
			char firstLetter = definition.getName().charAt(0);
			if (!(firstLetter >= 'A' && firstLetter <= 'Z')) {
				result.add(definition);
			}
		}
		return result;
	}
}