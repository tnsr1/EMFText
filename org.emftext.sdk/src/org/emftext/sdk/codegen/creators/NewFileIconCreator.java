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
package org.emftext.sdk.codegen.creators;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.emftext.runtime.EMFTextRuntimePlugin;
import org.emftext.runtime.ui.new_wizard.AbstractNewFileWizard;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IArtifactCreator;

/**
 * Creates a default icon for the NewFileWizard of generated
 * text resources by copying the default_new_icon.gif contained
 * in this package.
 */
public class NewFileIconCreator implements IArtifactCreator {

	public void createArtifacts(GenerationContext context) {
		File iconsDir = context.getIconsDir();
		iconsDir.mkdir();
		
		InputStream in = AbstractNewFileWizard.class.getResourceAsStream("default_new_icon.gif");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(context.getNewIconFile());
			int read;
			while ((read = in.read()) >= 0) {
				fos.write(read);
			}
			fos.close();
		} catch (IOException e) {
			EMFTextRuntimePlugin.logError("Error while copying icon.", e);
		}
	}

	public String getArtifactDescription() {
		return "new file icon";
	}
}
