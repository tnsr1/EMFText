/*******************************************************************************
 * Copyright (c) 2006-2015
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH, Dresden, Amtsgericht Dresden, HRB 34001
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Dresden, Germany
 *      - initial API and implementation
 ******************************************************************************/

package org.emftext.sdk.concretesyntax.resource.cs;

import org.eclipse.emf.ecore.EObject;

/**
 * <p>
 * A delegating reference resolver is an extension of a normal reference resolver
 * that can be configured with another resolver that it may delegate method calls
 * to. This interface can be implemented by additional resolvers to customize
 * resolving using the load option ADDITIONAL_REFERENCE_RESOLVERS.
 * </p>
 * 
 * @see org.emftext.sdk.concretesyntax.resource.cs.ICsOptions
 */
public interface ICsDelegatingReferenceResolver<ContainerType extends EObject, ReferenceType extends EObject> extends org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolver<ContainerType, ReferenceType> {
	
	/**
	 * Sets the delegate for this resolver.
	 */
	public void setDelegate(org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolver<ContainerType, ReferenceType> delegate);
	
}
