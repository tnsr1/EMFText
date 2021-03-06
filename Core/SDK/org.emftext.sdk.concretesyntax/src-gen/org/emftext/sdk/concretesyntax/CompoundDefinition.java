/**
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
 *  
 */
package org.emftext.sdk.concretesyntax;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compound Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A group of syntax elements that must appear together.
 * <!-- end-model-doc -->
 *
 *
 * @see org.emftext.sdk.concretesyntax.ConcretesyntaxPackage#getCompoundDefinition()
 * @model
 * @generated
 */
public interface CompoundDefinition extends CardinalityDefinition {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='org.eclipse.emf.common.util.EList< org.emftext.sdk.concretesyntax.SyntaxElement> children = getChildren();\n// there should be at most one child\nassert children == null || children.size() == 1;\n\nif (children != null && children.size() > 0) {\n\torg.emftext.sdk.concretesyntax.SyntaxElement firstChild = children.get(0);\n\tif (firstChild instanceof org.emftext.sdk.concretesyntax.Choice) {\n\t\treturn ( org.emftext.sdk.concretesyntax.Choice) firstChild;\n\t} else {\n\t\t// there should be no element other than Choice\n\t\tassert false;\n\t\treturn null;\n\t}\n}\nreturn null;'"
	 * @generated
	 */
	Choice getDefinition();

} // CompoundDefinition
