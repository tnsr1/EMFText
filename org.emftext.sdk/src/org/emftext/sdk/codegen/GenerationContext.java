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
package org.emftext.sdk.codegen;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.runtime.resource.IReferenceResolver;
import org.emftext.runtime.resource.ITokenResolver;
import org.emftext.runtime.resource.ITokenResolverFactory;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.Import;
import org.emftext.sdk.concretesyntax.OptionTypes;
import org.emftext.sdk.concretesyntax.TokenDefinition;
import org.emftext.sdk.finders.GenClassFinder;

/**
 * A GenerationContext provides all information that is needed by the 
 * generators. This includes a resolved concrete syntax, 
 * a package name for parser and printer, a package name for resolvers 
 * (reference and token resolvers) and a resource target folder. Furthermore,
 * the context collects information about the generation process as it
 * is executed.
 * 
 * @see org.emftext.sdk.codegen.creators.ResourcePluginContentCreator
 * 
 * @author Sven Karol (Sven.Karol@tu-dresden.de)
 * 
 * TODO this class is a total mess. we must figure out what really belongs in here.
 */
public abstract class GenerationContext {
	
	public static final String CLASS_SUFFIX_TOKEN_RESOLVER = ITokenResolver.class.getSimpleName().substring(1);
	public static final String CLASS_SUFFIX_TOKEN_RESOLVER_FACTORY = ITokenResolverFactory.class.getSimpleName().substring(1);
	public static final String CLASS_SUFFIX_REFERENCE_RESOLVER = IReferenceResolver.class.getSimpleName().substring(1);

	private static final String CLASS_SUFFIX_PRINTER = "Printer";
	private static final String CLASS_SUFFIX_PRINTER_BASE = "PrinterBase";
	private static final String CLASS_SUFFIX_RESOURCE = "Resource";
	private static final String CLASS_SUFFIX_RESOURCE_FACTORY = "ResourceFactory";
	private static final String CLASS_SUFFIX_REFERENCE_RESOLVER_SWITCH = "ReferenceResolverSwitch";
	private static final String CLASS_SUFFIX_NEW_FILE_WIZARD = "NewFileWizard";
	
	private static final String ANTRL_GRAMMAR_FILE_EXTENSION = ".g";
	private static final String JAVA_FILE_EXTENSION = ".java";

	private static final String DEFAULT_NEW_ICON_NAME = "default_new_icon.gif";
	private static final String DEFAULT_ICON_DIR = "icons";
	
	private final GenClassFinder genClassFinder = new GenClassFinder();

	private final ConcreteSyntax concreteSyntax;
	private final IProblemCollector problemCollector;
	
	/**
	 * A list that contains the names of all resolver classes that are needed.
	 * Some of them might be generated during the generation process, others
	 * may already exist. This list must not contain resolver classes that are
	 * contained in imported syntaxes and that are reused.
	 */
	private Set<String> resolverFileNames = new LinkedHashSet<String>();
	private Collection<GenFeature> nonContainmentReferences = new LinkedHashSet<GenFeature>();
	
	public GenerationContext(ConcreteSyntax concreteSyntax, IProblemCollector problemCollector) {
		if (concreteSyntax == null) {
			throw new IllegalArgumentException("A concrete syntax must be specified!");
		}
		this.concreteSyntax = concreteSyntax;
		this.problemCollector = problemCollector;
	}

	/**	 
	 * Returns the name of the package where token and reference resolvers 
	 * must go to. Depending on the given generator feature this package
	 * might be part of a resource plug-in that belongs to an imported
	 * syntax.
	 */
	public String getResolverPackageName(GenFeature genFeature) {
		ConcreteSyntax syntax = getConcreteSyntax(genFeature);
		return getResolverPackageName(syntax);
	}

	/**	 
	 * Returns the name of the package where token and reference resolvers 
	 * must go to.
	 */
	public String getResolverPackageName() {
		return getResolverPackageName(concreteSyntax);
	}
	
	/**	 
	 * Returns the name of the package where token and reference resolvers 
	 * must go to depending on the given syntax.
	 */
	public String getResolverPackageName(ConcreteSyntax syntax) {
		String csPackageName = getPackageName(syntax);
		return (csPackageName == null || csPackageName.equals("") ? "" : csPackageName + ".") + "analysis";
	}

	// feature may be contained in imported rules and thus belong to a different
	// CS specification
	private ConcreteSyntax getConcreteSyntax(GenFeature genFeature) {
		for (Import nextImport : concreteSyntax.getImports()) {
			ConcreteSyntax nextSyntax = nextImport.getConcreteSyntax();
			if (nextSyntax == null) {
				continue;
			}
			if (genClassFinder.contains(genClassFinder.findAllGenClasses(nextSyntax, true, true), genFeature.getGenClass())) {
				ConcreteSyntax cs = genClassFinder.getContainingSyntax(nextSyntax, genFeature.getGenClass());
				return cs;
			}
		}
		return concreteSyntax;
	}

	/**
	 * @return The concrete syntax to be processed and which is 
	 * assumed to contain all resolved information.
	 */
	public ConcreteSyntax getConcreteSyntax(){
		return concreteSyntax;
	}
	
	public abstract File getPluginProjectFolder();

	/**
	 * Returns the actual file which contains the CS specification.
	 */
	public File getConcreteSyntaxFile() {
		Resource resource = concreteSyntax.eResource();
		URI uri = resource.getURI();
		File file = new File(uri.toFileString());
		return file;
	}
	
	public File getOutputFolder() {
		return new File(getPluginProjectFolder().getAbsolutePath() + File.separator + "bin");
	}

	public File getSourceFolder() {
		String srcFolderName;
		String srcFolderOptionValue = OptionManager.INSTANCE.getStringOptionValue(concreteSyntax, OptionTypes.SOURCE_FOLDER);
		if (srcFolderOptionValue != null) {
			// use package plug-in from option
			srcFolderName = srcFolderOptionValue;
		} else {
			// use default plug-in name
			srcFolderName = "src";
		}
		return new File(getPluginProjectFolder().getAbsolutePath() + File.separator + srcFolderName);
	}
	
	/**
	 * Returns a collection that contains the names of all resolver
	 * classes (both token and reference resolvers) that are needed.
	 * Each resolver that is not part of an imported plug-in should 
	 * be added to this list. 
	 * 
	 * @return the collection of already generated resolver classes
	 */
	public Set<String> getResolverFileNames() {
		return resolverFileNames;
	}

	public String getPluginName() {
		return getPluginName(concreteSyntax);
	}

	public String getPluginName(ConcreteSyntax syntax) {
		String resourcePluginID = OptionManager.INSTANCE.getStringOptionValue(syntax, OptionTypes.RESOURCE_PLUGIN_ID);
		if (resourcePluginID != null) {
			// use package plug-in from option
			return resourcePluginID;
		} else {
			// use default plug-in name
			return getPackageName(syntax);
		}
	}

	public String getPackageName() {
		return getPackageName(concreteSyntax);
	}

	public String getPackageName(ConcreteSyntax syntax) {
		String packageName = "";
		String basePackage = OptionManager.INSTANCE.getStringOptionValue(syntax, OptionTypes.BASE_PACKAGE);
		if (basePackage != null) {
			// use package name from option
			packageName = basePackage;
		} else {
			// use default package name
			GenPackage concreteSyntaxPackage = syntax.getPackage();
			boolean hasBasePackage = concreteSyntaxPackage.getBasePackage() != null;
			if (hasBasePackage) {
				packageName = concreteSyntaxPackage.getBasePackage() + ".";
			}
			packageName += concreteSyntaxPackage.getEcorePackage().getName();
			packageName += ".resource." + syntax.getName();
		}
		return packageName;
	}

	public IProblemCollector getProblemCollector() {
		return problemCollector;
	}
	
	public String getCapitalizedConcreteSyntaxName() {
		return getCapitalizedConcreteSyntaxName(getConcreteSyntax());
	}
	
	public String getCapitalizedConcreteSyntaxName(ConcreteSyntax syntax) {
		return capitalize(syntax.getName());
	}
	
    public String getPrinterName() {
    	return getCapitalizedConcreteSyntaxName() + CLASS_SUFFIX_PRINTER;
    }
    
    public String getPrinterBaseClassName() {
    	return getCapitalizedConcreteSyntaxName() + CLASS_SUFFIX_PRINTER_BASE;
    }
    
    public String getResourceClassName() {
    	return getCapitalizedConcreteSyntaxName() + CLASS_SUFFIX_RESOURCE;
    }
    
    public String getResourceFactoryClassName() {
    	return getCapitalizedConcreteSyntaxName() + CLASS_SUFFIX_RESOURCE_FACTORY;
    }
    
    public String getNewFileActionClassName() {
    	return getCapitalizedConcreteSyntaxName() + CLASS_SUFFIX_NEW_FILE_WIZARD;
    }
    
	public String getQualifiedNewFileActionName() {
		return getPackageName() + "." + getNewFileActionClassName();
	}

	public String getReferenceResolverSwitchClassName() {
    	return getCapitalizedConcreteSyntaxName() + CLASS_SUFFIX_REFERENCE_RESOLVER_SWITCH;
    }
    
    public String getQualifiedReferenceResolverSwitchClassName() {
    	return getPackageName() + "." + getReferenceResolverSwitchClassName();
    }
    
    public String getTokenResolverFactoryClassName() {
    	return getCapitalizedConcreteSyntaxName() + CLASS_SUFFIX_TOKEN_RESOLVER_FACTORY;
    }

	public String getTokenResolverClassName(TokenDefinition tokenDefinition) {

		String syntaxName = getCapitalizedConcreteSyntaxName(getContainingSyntax(tokenDefinition));
		boolean isCollect = tokenDefinition.getAttributeName() != null;
		if (isCollect) {
			String attributeName = tokenDefinition.getAttributeName();
			return syntaxName +  "COLLECT_" + attributeName + CLASS_SUFFIX_TOKEN_RESOLVER;
		} else {
			return syntaxName +  tokenDefinition.getName() + CLASS_SUFFIX_TOKEN_RESOLVER;
		}
	}

	public String getReferenceResolverClassName(GenFeature proxyReference) {
		return proxyReference.getGenClass().getName() + capitalize(proxyReference.getName()) + CLASS_SUFFIX_REFERENCE_RESOLVER;
	}
	
	public ConcreteSyntax getContainingSyntax(TokenDefinition baseDefinition) {
		EObject container = baseDefinition.eContainer();
		if (container instanceof ConcreteSyntax) {
			return (ConcreteSyntax) container;
		}
		return concreteSyntax;
	}
	
    /**
     * Capitalizes the first letter of the given string.
     * 
     * @param text a string.
     * @return the modified string.
     */
    private String capitalize(String text) {
        String h = text.substring(0, 1).toUpperCase();
        String t = text.substring(1);      
        return h + t;
    }

	public void addNonContainmentReference(GenFeature proxyReference) {
		nonContainmentReferences.add(proxyReference);
	}

	public void addReferenceResolverClass(GenFeature proxyReference) {
		resolverFileNames.add(getReferenceResolverClassName(proxyReference) + JAVA_FILE_EXTENSION);
	}

	public void addTokenResolverClass(TokenDefinition tokenDefinition) {
		resolverFileNames.add(getTokenResolverClassName(tokenDefinition) + JAVA_FILE_EXTENSION);
	}

	public Collection<GenFeature> getNonContainmentReferences() {
		return nonContainmentReferences;
	}

	public boolean isImportedWithSyntaxReference(GenFeature genFeature) {
		//Set<GenClass> classesExceptImports = genClassFinder.findAllGenClasses(concreteSyntax, false, false);
		ConcreteSyntax containingSyntax = genClassFinder.getContainingSyntax(concreteSyntax, genFeature.getGenClass());
		if (containingSyntax == null) return false;
		if (containingSyntax == concreteSyntax) return false;
		return true;
	}

	public boolean isImportedToken(TokenDefinition tokenDefinition) {
		return !concreteSyntax.equals(getContainingSyntax(tokenDefinition));
	}

	public String getQualifiedReferenceResolverClassName(
			GenFeature proxyReference) {
		return getResolverPackageName(proxyReference) + "." + getReferenceResolverClassName(proxyReference);
	}

	public String getReferenceResolverAccessor(GenFeature genFeature) {
		String prefix = "((" + getQualifiedReferenceResolverSwitchClassName() + ") resource.getReferenceResolverSwitch())";
		return prefix + ".get" + getReferenceResolverClassName(genFeature) + "()";
	}

	/**
	 * Returns the name of the project that contains the concrete 
	 * syntax definition. Note that this is usually NOT the text
	 * resource project.
	 */
	public abstract String getSyntaxProjectName();

	/**
	 * Returns the path of the concrete syntax definition
	 * file relative to the project that contains the
	 * file.
	 */
	public abstract String getProjectRelativePathToSyntaxFile();

	public File getIconsDir() {
		return new File(getPluginProjectFolder().getAbsolutePath() + File.separator + DEFAULT_ICON_DIR);
	}

	public File getNewIconFile() {
		return new File(getIconsDir().getAbsolutePath() + File.separator + DEFAULT_NEW_ICON_NAME);
	}

	public String getProjectRelativeNewIconPath() {
		// it is OK to use slashes here, because this path is put into
		// the plugin.xml
		return "/" + DEFAULT_ICON_DIR + "/" + DEFAULT_NEW_ICON_NAME;
	}

	public boolean isGenerateTestActionEnabled() {
		return OptionManager.INSTANCE.getBooleanOptionValue(getConcreteSyntax(), OptionTypes.GENERATE_TEST_ACTION);
	}

	public String getQualifiedParserClassName() {
		return getPackageName() + "." + getCapitalizedConcreteSyntaxName() + "Parser";
	}

	public String getQualifiedPrinterName() {
		return getPackageName() + "." + getPrinterName();
	}

	public String getPackagePath() {
		File targetFolder = getSourceFolder();
		IPath csPackagePath = new Path(getPackageName().replaceAll("\\.","/"));
		String targetFolderPath = targetFolder.getAbsolutePath();
		String packagePath = targetFolderPath + File.separator + csPackagePath + File.separator;
		return packagePath;
	}

	public File getPrinterFile() {
		return new File(getPackagePath() + getPrinterName() + JAVA_FILE_EXTENSION);
	}

	public File getPrinterBaseFile() {
		return new File(getPackagePath() + getPrinterBaseClassName() + JAVA_FILE_EXTENSION);
	}

	public File getTextResourceFile() {
		return new File(getPackagePath() + getResourceClassName() + JAVA_FILE_EXTENSION);
	}

	public File getTokenResolverFactoryFile() {
		return new File(getPackagePath() + getTokenResolverFactoryClassName() + JAVA_FILE_EXTENSION);
	}

	public File getNewFileWizardFile() {
		return new File(getPackagePath() + getNewFileActionClassName() + JAVA_FILE_EXTENSION);
	}

	public File getReferenceResolverSwitchFile() {
		return new File(getPackagePath() + getReferenceResolverSwitchClassName() + JAVA_FILE_EXTENSION);
	}

	public File getResourceFactoryFile() {
		return new File(getPackagePath() + getResourceFactoryClassName() + JAVA_FILE_EXTENSION);
	}

	public File getResolverFile(GenFeature proxyReference) {
		File resolverFile = new File(getSourceFolder() + File.separator + getResolverPackagePath() + File.separator + getReferenceResolverClassName(proxyReference) + JAVA_FILE_EXTENSION);
		return resolverFile;
	}

	public File getTokenResolverFile(TokenDefinition tokenDefinition) {
		return new File(getSourceFolder().getAbsolutePath() + File.separator + getResolverPackagePath() + File.separator + getTokenResolverClassName(tokenDefinition) + JAVA_FILE_EXTENSION);
	}

	private IPath getResolverPackagePath() {
		return new Path(getResolverPackageName().replaceAll("\\.","/"));
	}

	public File getResolverPackageFile() {
		return new File(getSourceFolder().getAbsolutePath() + File.separator + getResolverPackagePath());
	}

	public File getANTLRGrammarFile() {
		String antlrName = getCapitalizedConcreteSyntaxName();
		String packagePath = getPackagePath();
  		File antlrFile = new File(packagePath + antlrName + ANTRL_GRAMMAR_FILE_EXTENSION);
		return antlrFile;
	}
}
