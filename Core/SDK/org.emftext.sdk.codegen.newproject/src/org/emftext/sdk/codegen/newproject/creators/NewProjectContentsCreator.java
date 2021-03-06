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
package org.emftext.sdk.codegen.newproject.creators;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.emftext.sdk.IPluginDescriptor;
import org.emftext.sdk.codegen.ArtifactDescriptor;
import org.emftext.sdk.codegen.BasicPackage;
import org.emftext.sdk.codegen.IArtifactCreator;
import org.emftext.sdk.codegen.IPackage;
import org.emftext.sdk.codegen.IPluginCreator;
import org.emftext.sdk.codegen.ISyntaxContext;
import org.emftext.sdk.codegen.creators.DotClasspathCreator;
import org.emftext.sdk.codegen.creators.DotProjectCreator;
import org.emftext.sdk.codegen.creators.FoldersCreator;
import org.emftext.sdk.codegen.creators.OverridingArtifactCreator;
import org.emftext.sdk.codegen.newproject.NewProjectArtifacts;
import org.emftext.sdk.codegen.newproject.NewProjectGenerationContext;
import org.emftext.sdk.codegen.newproject.NewProjectParameters;
import org.emftext.sdk.codegen.newproject.TextResourceCreator;
import org.emftext.sdk.codegen.newproject.generators.GenModelGenerator;
import org.emftext.sdk.codegen.newproject.generators.MetaModelGenerator;
import org.emftext.sdk.codegen.newproject.generators.SyntaxGenerator;
import org.emftext.sdk.codegen.parameters.ClassPathParameters;
import org.emftext.sdk.codegen.parameters.DotProjectParameters;
import org.emftext.sdk.codegen.parameters.SimpleParameter;

/**
 * This class creates the content of new EMFText project. First it uses special
 * generators to create a meta model, a concrete syntax and a generator model. 
 * Afterwards the EMF code generation and the EMFText code generation is called
 * to obtain runnable DSL plug-ins. 
 */
public class NewProjectContentsCreator implements IPluginCreator<NewProjectGenerationContext, Object> {

	public NewProjectContentsCreator() {
		super();
	}

	public void create(IPluginDescriptor plugin, NewProjectGenerationContext context, Object parameters,
			IProgressMonitor unusedMonitor) throws IOException {

		IProgressMonitor monitor = context.getMonitor();
		List<IArtifactCreator<NewProjectGenerationContext>> creators = getCreators(context);
		monitor.beginTask("Creating new project", creators.size());
		for (IArtifactCreator<NewProjectGenerationContext> creator : creators) {
			creator.createArtifacts(context.getPluginDescriptor(), context);
			monitor.worked(1);
		}
	}
	
	public List<IArtifactCreator<NewProjectGenerationContext>> getCreators(NewProjectGenerationContext context) {
		NewProjectParameters parameters = context.getParameters();
		IPackage<ISyntaxContext> metamodelPackage = new BasicPackage<ISyntaxContext>(parameters.getMetamodelFolder());
		ArtifactDescriptor<NewProjectGenerationContext, SimpleParameter<NewProjectGenerationContext, String>> metamodel  = 
			new ArtifactDescriptor<NewProjectGenerationContext, SimpleParameter<NewProjectGenerationContext, String>>(
					metamodelPackage, 
					"", 
					parameters.getEcoreFile(), 
					MetaModelGenerator.class, 
					null);

		ArtifactDescriptor<NewProjectGenerationContext, SimpleParameter<NewProjectGenerationContext, String>> genModel  = 
			new ArtifactDescriptor<NewProjectGenerationContext, SimpleParameter<NewProjectGenerationContext, String>>(
					metamodelPackage, 
					"", 
					parameters.getGenmodelFile(), 
					GenModelGenerator.class, 
					null);

		ArtifactDescriptor<NewProjectGenerationContext, SimpleParameter<NewProjectGenerationContext, String>> syntax  = 
			new ArtifactDescriptor<NewProjectGenerationContext, SimpleParameter<NewProjectGenerationContext, String>>(
					metamodelPackage, 
					"", 
					parameters.getSyntaxFile(), 
					SyntaxGenerator.class, 
					null);

		List<IArtifactCreator<NewProjectGenerationContext>> creators = new ArrayList<IArtifactCreator<NewProjectGenerationContext>>();
		
		creators.add(new FoldersCreator<NewProjectGenerationContext>(new File(context.getProjectFolder(context.getPluginDescriptor()) + File.separator + metamodelPackage.getName(context))));
    	creators.add(new OverridingArtifactCreator<NewProjectGenerationContext, SimpleParameter<NewProjectGenerationContext, String>>(new SimpleParameter<NewProjectGenerationContext, String>(metamodel, parameters.getEcoreFile())));
    	creators.add(new OverridingArtifactCreator<NewProjectGenerationContext, SimpleParameter<NewProjectGenerationContext, String>>(new SimpleParameter<NewProjectGenerationContext, String>(genModel, parameters.getGenmodelFile())));
    	creators.add(new OverridingArtifactCreator<NewProjectGenerationContext, SimpleParameter<NewProjectGenerationContext, String>>(new SimpleParameter<NewProjectGenerationContext, String>(syntax, parameters.getSyntaxFile())));
       	creators.add(new GenerateCodeCreator());
    	if (parameters.isGenerateResourceCode()) {
        	creators.add(new TextResourceCreator());
		}
    	
    	ClassPathParameters<NewProjectGenerationContext> cpp = new ClassPathParameters<NewProjectGenerationContext>(NewProjectArtifacts.DOT_CLASSPATH, context.getPluginDescriptor());
    	cpp.getSourceFolders().add(parameters.getSrcFolder());
		creators.add(new DotClasspathCreator<NewProjectGenerationContext>(cpp, true));
		
		DotProjectParameters<NewProjectGenerationContext> dpp = new DotProjectParameters<NewProjectGenerationContext>(NewProjectArtifacts.DOT_PROJECT, context.getPluginDescriptor());
		creators.add(new DotProjectCreator<NewProjectGenerationContext>(dpp, true));
		
		return creators;
	}
}
