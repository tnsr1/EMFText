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
package org.emftext.sdk.concretesyntax.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.emftext.sdk.concretesyntax.AbstractTokenDefinition;
import org.emftext.sdk.concretesyntax.CompleteTokenDefinition;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.ConcretesyntaxPackage;
import org.emftext.sdk.concretesyntax.NamedTokenDefinition;
import org.emftext.sdk.concretesyntax.Placeholder;
import org.emftext.sdk.concretesyntax.ReferencableTokenDefinition;
import org.emftext.sdk.concretesyntax.RegexComposite;
import org.emftext.sdk.concretesyntax.RegexOwner;
import org.emftext.sdk.concretesyntax.RegexPart;
import org.emftext.sdk.concretesyntax.TokenDirective;
import org.emftext.sdk.concretesyntax.TokenRedefinition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Token Redefinition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.emftext.sdk.concretesyntax.impl.TokenRedefinitionImpl#getRegex <em>Regex</em>}</li>
 *   <li>{@link org.emftext.sdk.concretesyntax.impl.TokenRedefinitionImpl#getRegexParts <em>Regex Parts</em>}</li>
 *   <li>{@link org.emftext.sdk.concretesyntax.impl.TokenRedefinitionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.emftext.sdk.concretesyntax.impl.TokenRedefinitionImpl#getAttributeReferences <em>Attribute References</em>}</li>
 *   <li>{@link org.emftext.sdk.concretesyntax.impl.TokenRedefinitionImpl#getAttributeName <em>Attribute Name</em>}</li>
 *   <li>{@link org.emftext.sdk.concretesyntax.impl.TokenRedefinitionImpl#getRedefinedToken <em>Redefined Token</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TokenRedefinitionImpl extends AnnotableImpl implements TokenRedefinition {
	/**
	 * The default value of the '{@link #getRegex() <em>Regex</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegex()
	 * @generated
	 * @ordered
	 */
	protected static final String REGEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRegexParts() <em>Regex Parts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegexParts()
	 * @generated
	 * @ordered
	 */
	protected EList<RegexPart> regexParts;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttributeReferences() <em>Attribute References</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<Placeholder> attributeReferences;

	/**
	 * The default value of the '{@link #getAttributeName() <em>Attribute Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeName()
	 * @generated
	 * @ordered
	 */
	protected static final String ATTRIBUTE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAttributeName() <em>Attribute Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeName()
	 * @generated
	 * @ordered
	 */
	protected String attributeName = ATTRIBUTE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRedefinedToken() <em>Redefined Token</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRedefinedToken()
	 * @generated
	 * @ordered
	 */
	protected CompleteTokenDefinition redefinedToken;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TokenRedefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConcretesyntaxPackage.Literals.TOKEN_REDEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRegex() {
		java.lang.StringBuilder result = new java.lang.StringBuilder();
		for ( org.emftext.sdk.concretesyntax.RegexPart part : getRegexParts()) {
			result.append(part.getRegex());
		}
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RegexPart> getRegexParts() {
		if (regexParts == null) {
			regexParts = new EObjectContainmentEList<RegexPart>(RegexPart.class, this, ConcretesyntaxPackage.TOKEN_REDEFINITION__REGEX_PARTS);
		}
		return regexParts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConcretesyntaxPackage.TOKEN_REDEFINITION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Placeholder> getAttributeReferences() {
		if (attributeReferences == null) {
			attributeReferences = new EObjectWithInverseResolvingEList<Placeholder>(Placeholder.class, this, ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_REFERENCES, ConcretesyntaxPackage.PLACEHOLDER__TOKEN);
		}
		return attributeReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeName(String newAttributeName) {
		String oldAttributeName = attributeName;
		attributeName = newAttributeName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_NAME, oldAttributeName, attributeName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompleteTokenDefinition getRedefinedToken() {
		if (redefinedToken != null && redefinedToken.eIsProxy()) {
			InternalEObject oldRedefinedToken = (InternalEObject)redefinedToken;
			redefinedToken = (CompleteTokenDefinition)eResolveProxy(oldRedefinedToken);
			if (redefinedToken != oldRedefinedToken) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConcretesyntaxPackage.TOKEN_REDEFINITION__REDEFINED_TOKEN, oldRedefinedToken, redefinedToken));
			}
		}
		return redefinedToken;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompleteTokenDefinition basicGetRedefinedToken() {
		return redefinedToken;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRedefinedToken(CompleteTokenDefinition newRedefinedToken) {
		CompleteTokenDefinition oldRedefinedToken = redefinedToken;
		redefinedToken = newRedefinedToken;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConcretesyntaxPackage.TOKEN_REDEFINITION__REDEFINED_TOKEN, oldRedefinedToken, redefinedToken));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHidden() {
		boolean isReferenced = !getAttributeReferences().isEmpty();
		boolean isCollectInToken = getAttributeName() != null;
		return !isReferenced || isCollectInToken;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUsed() {
		boolean isReferenced = !getAttributeReferences().isEmpty();
		boolean isCollectInToken = getAttributeName() != null;
		return isReferenced || isCollectInToken;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isImported(final ConcreteSyntax syntax) {
		return !syntax.equals(getContainingSyntax(syntax));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConcreteSyntax getContainingSyntax(final ConcreteSyntax syntax) {
		org.eclipse.emf.ecore.EObject container = this.eContainer();
		if (container instanceof org.emftext.sdk.concretesyntax.ConcreteSyntax) {
			return ( org.emftext.sdk.concretesyntax.ConcreteSyntax) container;
		}
		return syntax;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_REFERENCES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAttributeReferences()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__REGEX_PARTS:
				return ((InternalEList<?>)getRegexParts()).basicRemove(otherEnd, msgs);
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_REFERENCES:
				return ((InternalEList<?>)getAttributeReferences()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__REGEX:
				return getRegex();
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__REGEX_PARTS:
				return getRegexParts();
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__NAME:
				return getName();
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_REFERENCES:
				return getAttributeReferences();
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_NAME:
				return getAttributeName();
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__REDEFINED_TOKEN:
				if (resolve) return getRedefinedToken();
				return basicGetRedefinedToken();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__REGEX_PARTS:
				getRegexParts().clear();
				getRegexParts().addAll((Collection<? extends RegexPart>)newValue);
				return;
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__NAME:
				setName((String)newValue);
				return;
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_REFERENCES:
				getAttributeReferences().clear();
				getAttributeReferences().addAll((Collection<? extends Placeholder>)newValue);
				return;
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_NAME:
				setAttributeName((String)newValue);
				return;
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__REDEFINED_TOKEN:
				setRedefinedToken((CompleteTokenDefinition)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__REGEX_PARTS:
				getRegexParts().clear();
				return;
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_REFERENCES:
				getAttributeReferences().clear();
				return;
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_NAME:
				setAttributeName(ATTRIBUTE_NAME_EDEFAULT);
				return;
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__REDEFINED_TOKEN:
				setRedefinedToken((CompleteTokenDefinition)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__REGEX:
				return REGEX_EDEFAULT == null ? getRegex() != null : !REGEX_EDEFAULT.equals(getRegex());
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__REGEX_PARTS:
				return regexParts != null && !regexParts.isEmpty();
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_REFERENCES:
				return attributeReferences != null && !attributeReferences.isEmpty();
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_NAME:
				return ATTRIBUTE_NAME_EDEFAULT == null ? attributeName != null : !ATTRIBUTE_NAME_EDEFAULT.equals(attributeName);
			case ConcretesyntaxPackage.TOKEN_REDEFINITION__REDEFINED_TOKEN:
				return redefinedToken != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == RegexOwner.class) {
			switch (derivedFeatureID) {
				case ConcretesyntaxPackage.TOKEN_REDEFINITION__REGEX: return ConcretesyntaxPackage.REGEX_OWNER__REGEX;
				default: return -1;
			}
		}
		if (baseClass == RegexComposite.class) {
			switch (derivedFeatureID) {
				case ConcretesyntaxPackage.TOKEN_REDEFINITION__REGEX_PARTS: return ConcretesyntaxPackage.REGEX_COMPOSITE__REGEX_PARTS;
				default: return -1;
			}
		}
		if (baseClass == TokenDirective.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == AbstractTokenDefinition.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == NamedTokenDefinition.class) {
			switch (derivedFeatureID) {
				case ConcretesyntaxPackage.TOKEN_REDEFINITION__NAME: return ConcretesyntaxPackage.NAMED_TOKEN_DEFINITION__NAME;
				default: return -1;
			}
		}
		if (baseClass == ReferencableTokenDefinition.class) {
			switch (derivedFeatureID) {
				case ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_REFERENCES: return ConcretesyntaxPackage.REFERENCABLE_TOKEN_DEFINITION__ATTRIBUTE_REFERENCES;
				default: return -1;
			}
		}
		if (baseClass == CompleteTokenDefinition.class) {
			switch (derivedFeatureID) {
				case ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_NAME: return ConcretesyntaxPackage.COMPLETE_TOKEN_DEFINITION__ATTRIBUTE_NAME;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == RegexOwner.class) {
			switch (baseFeatureID) {
				case ConcretesyntaxPackage.REGEX_OWNER__REGEX: return ConcretesyntaxPackage.TOKEN_REDEFINITION__REGEX;
				default: return -1;
			}
		}
		if (baseClass == RegexComposite.class) {
			switch (baseFeatureID) {
				case ConcretesyntaxPackage.REGEX_COMPOSITE__REGEX_PARTS: return ConcretesyntaxPackage.TOKEN_REDEFINITION__REGEX_PARTS;
				default: return -1;
			}
		}
		if (baseClass == TokenDirective.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == AbstractTokenDefinition.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == NamedTokenDefinition.class) {
			switch (baseFeatureID) {
				case ConcretesyntaxPackage.NAMED_TOKEN_DEFINITION__NAME: return ConcretesyntaxPackage.TOKEN_REDEFINITION__NAME;
				default: return -1;
			}
		}
		if (baseClass == ReferencableTokenDefinition.class) {
			switch (baseFeatureID) {
				case ConcretesyntaxPackage.REFERENCABLE_TOKEN_DEFINITION__ATTRIBUTE_REFERENCES: return ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_REFERENCES;
				default: return -1;
			}
		}
		if (baseClass == CompleteTokenDefinition.class) {
			switch (baseFeatureID) {
				case ConcretesyntaxPackage.COMPLETE_TOKEN_DEFINITION__ATTRIBUTE_NAME: return ConcretesyntaxPackage.TOKEN_REDEFINITION__ATTRIBUTE_NAME;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", attributeName: ");
		result.append(attributeName);
		result.append(')');
		return result.toString();
	}

} //TokenRedefinitionImpl
