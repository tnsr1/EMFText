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
package org.emftext.sdk.codegen.creators;

import java.io.File;

import org.emftext.sdk.IPluginDescriptor;
import org.emftext.sdk.codegen.IArtifactCreator;
import org.emftext.sdk.concretesyntax.OptionTypes;

/**
 * Creates a set of given folders.
 */
public class FoldersCreator<ContextType> implements IArtifactCreator<ContextType> {

	private File[] folders;

	public FoldersCreator(File... folders) {
		super();
		this.folders = folders;
	}

	public void createArtifacts(IPluginDescriptor plugin, ContextType context) {
		for (File folder : folders) {
			createIfNeeded(folder);
		}
	}

	private void createIfNeeded(File targetFolder) {
		if (!targetFolder.exists()) {
		   	targetFolder.mkdirs();
		}
	}

	public OptionTypes getOverrideOption() {
		// there is not option to prevent the creation of the folders
		return null;
	}

	public String getArtifactTypeDescription() {
		return "folders";
	}
}
