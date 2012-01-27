/*******************************************************************************
 * Copyright (c) 2006-2012
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

package org.emftext.sdk.concretesyntax.resource.cs.util;

public class CsMapUtil {
	
	/**
	 * This method encapsulate an unchecked cast from Object to java.util.Map<Object,
	 * Object>. This case can not be performed type safe, because type parameters are
	 * not available for reflective access to Ecore models.
	 * 
	 * @param value the object to cast
	 * 
	 * @return the same object casted to a map
	 */
	@SuppressWarnings("unchecked")	
	public static java.util.Map<Object, Object> castToMap(Object value) {
		return (java.util.Map<Object,Object>) value;
	}
	
	/**
	 * This method encapsulate an unchecked cast from Object to
	 * org.eclipse.emf.common.util.EMap<Object, Object>. This case can not be
	 * performed type safe, because type parameters are not available for reflective
	 * access to Ecore models.
	 * 
	 * @return the same object casted to a map
	 */
	@SuppressWarnings("unchecked")	public static org.eclipse.emf.common.util.EMap<Object, Object> castToEMap(Object value) {
		return (org.eclipse.emf.common.util.EMap<Object,Object>) value;
	}
	
	public static java.util.Map<Object, Object> copySafelyToObjectToObjectMap(java.util.Map<?, ?> map) {
		java.util.Map<Object, Object> castedCopy = new java.util.LinkedHashMap<Object, Object>();
		
		if (map == null) {
			return castedCopy;
		}
		
		java.util.Iterator<?> it = map.keySet().iterator();
		while (it.hasNext()) {
			Object nextKey = it.next();
			castedCopy.put(nextKey, map.get(nextKey));
		}
		return castedCopy;
	}
	
	/**
	 * Adds a new key,value pair to the given map. If there is already an entry with
	 * the same key, the two values are collected in a list.
	 */
	public static <K> void putAndMergeKeys(java.util.Map<K, Object> map, K key, Object value) {
		// check if there is already an option set
		if (map.containsKey(key)) {
			Object currentValue = map.get(key);
			if (currentValue instanceof java.util.List<?>) {
				// if the current value is a list, we add the new value to this list
				java.util.List<?> currentValueAsList = (java.util.List<?>) currentValue;
				java.util.List<Object> currentValueAsObjectList = org.emftext.sdk.concretesyntax.resource.cs.util.CsListUtil.copySafelyToObjectList(currentValueAsList);
				if (value instanceof java.util.Collection<?>) {
					currentValueAsObjectList.addAll((java.util.Collection<?>) value);
				} else {
					currentValueAsObjectList.add(value);
				}
				map.put(key, currentValueAsObjectList);
			} else {
				// if the current value is not a list, we create a fresh list and add both the old
				// (current) and the new value to this list
				java.util.List<Object> newValueList = new java.util.ArrayList<Object>();
				newValueList.add(currentValue);
				if (value instanceof java.util.Collection<?>) {
					newValueList.addAll((java.util.Collection<?>) value);
				} else {
					newValueList.add(value);
				}
				map.put(key, newValueList);
			}
		} else {
			map.put(key, value);
		}
	}
	
}
