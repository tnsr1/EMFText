/*******************************************************************************
 * Copyright (c) 2006-2010 
 * Software Technology Group, Dresden University of Technology
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany 
 *      - initial API and implementation
 ******************************************************************************/

package org.emftext.sdk.concretesyntax.resource.cs.ui;

public class CsDefaultHoverTextProvider implements org.emftext.sdk.concretesyntax.resource.cs.ICsHoverTextProvider {
	
	public java.lang.String getHoverText(org.eclipse.emf.ecore.EObject object) {
		if (object == null) {
			return null;
		}
		org.eclipse.emf.ecore.EClass eClass = object.eClass();
		String label = "<strong>" + eClass.getName() + "</strong>";
		for (org.eclipse.emf.ecore.EAttribute attribute : eClass.getEAllAttributes()) {
			java.lang.Object value = null;
			try {
				value = object.eGet(attribute);
			} catch (java.lang.Exception e) {
				// Exception in eGet, do nothing
			}
			if (value != null && value.toString() != null && !value.toString().equals("[]")) {
				label += "<br />" + attribute.getName() + ": " + object.eGet(attribute).toString();
			}
		}
		return label;
	}
}