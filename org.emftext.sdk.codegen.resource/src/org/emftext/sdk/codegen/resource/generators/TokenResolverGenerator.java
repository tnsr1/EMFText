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
package org.emftext.sdk.codegen.resource.generators;

import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.E_OBJECT;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.E_STRUCTURAL_FEATURE;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.OBJECT;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.STRING;

import org.emftext.sdk.codegen.ICodeGenerationComponent;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.composites.StringComposite;
import org.emftext.sdk.codegen.generators.GeneratorProvider;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.GeneratorUtil;
import org.emftext.sdk.codegen.resource.TextResourceArtifacts;
import org.emftext.sdk.codegen.util.NameUtil;
import org.emftext.sdk.concretesyntax.CompleteTokenDefinition;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.QuotedTokenDefinition;
import org.emftext.sdk.util.StringUtil;

/**
 * A TokenResolverGenerator generates a single TokenResolver for a given TokenDefinition.
 * 
 * For a definition with prefix and suffix it generates code which removes these 
 * strings from the beginning and the end of a lexem and then passes the manipulated 
 * lexem to the default token resolver. In the deresolvement the conversion from 
 * object to string is also delegated to the default resolver. 
 * Finally the deresolved String will be decorated by pre- and suffixes again. 
 * 
 * @author Sven Karol (Sven.Karol@tu-dresden.de)
 */
public class TokenResolverGenerator extends JavaBaseGenerator<CompleteTokenDefinition> {
	
	public static final GeneratorProvider<GenerationContext, CompleteTokenDefinition> PROVIDER = 
		new GeneratorProvider<GenerationContext, CompleteTokenDefinition>(new TokenResolverGenerator());
	
	private final GeneratorUtil generatorUtil = new GeneratorUtil();
	private final NameUtil nameUtil = new NameUtil();
	
	private CompleteTokenDefinition definition;
	
	private TokenResolverGenerator() {
		super();
	}
	
	private TokenResolverGenerator(ICodeGenerationComponent parent, GenerationContext context) {
		super(parent, context, TextResourceArtifacts.TOKEN_RESOLVER);
	}

	public void generateJavaContents(JavaComposite sc) {
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();

		ConcreteSyntax syntax = getContext().getConcreteSyntax();
		sc.add("public class " + nameUtil.getTokenResolverClassName(syntax, definition) + " implements " + iTokenResolverClassName + " {");
		sc.addLineBreak();
		
		// do not generate a resolver for imported tokens
		boolean isImportedToken = definition.isImported(syntax);

		if (isImportedToken) {
			String importedTokenResolverClassName = nameUtil.getQualifiedTokenResolverClassName(syntax, definition, true);

			sc.add("private " + importedTokenResolverClassName + " importedResolver = new " + importedTokenResolverClassName + "();");
			sc.addLineBreak();
			generateDeResolveMethod2(sc);
			generateResolveMethod2(sc);
		    generatorUtil.addSetOptionsMethod(sc, "importedResolver.setOptions(options);");
		} else {
			sc.add("private " + defaultTokenResolverClassName + " defaultTokenResolver = new " + defaultTokenResolverClassName + "();");
			sc.addLineBreak();
			generateDeResolveMethod1(sc);
			generateResolveMethod1(sc);
		    generatorUtil.addSetOptionsMethod(sc, "defaultTokenResolver.setOptions(options);");
		}
		sc.add("}");
	}

	private void generateDeResolveMethod1(StringComposite sc) {
		sc.add("public " + STRING + " deResolve(" + OBJECT + " value, " + E_STRUCTURAL_FEATURE + " feature, " + E_OBJECT + " container) {");
		sc.add(STRING + " result = defaultTokenResolver.deResolve(value, feature, container);");
		String suffix = getSuffix();
		String prefix = getPrefix();
		
		if (suffix != null) {
			String javaSourceSuffix = StringUtil.escapeToJavaString(suffix);
			// take care of the escape character (may be null)
			String escapeCharacter = getEscapeCharacter();
			if (escapeCharacter != null) {
				String javaSourceEscapeCharacter = StringUtil.escapeToJavaString(escapeCharacter);
				sc.add("result = result.replace(\"" + javaSourceEscapeCharacter + "\", \"" + javaSourceEscapeCharacter + javaSourceEscapeCharacter + "\");");
				sc.add("result = result.replace(\"" + javaSourceSuffix + "\", \"" + javaSourceEscapeCharacter + javaSourceSuffix + "\");");
			}
			sc.add("result += \"" + javaSourceSuffix + "\";");
		}	
		
		if (prefix != null) {
			sc.add("result = \"" + StringUtil.escapeToJavaString(prefix) + "\" + result;");
		}
		sc.add("return result;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void generateDeResolveMethod2(StringComposite sc) {
		sc.add("public " + STRING + " deResolve(" + OBJECT + " value, " + E_STRUCTURAL_FEATURE + " feature, " + E_OBJECT + " container) {");
		sc.add(STRING + " result = importedResolver.deResolve(value, feature, container);");
		sc.add("return result;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void generateResolveMethod1(StringComposite sc) {
		sc.add("public void resolve(" + STRING + " lexem, " + E_STRUCTURAL_FEATURE + " feature, " + iTokenResolveResultClassName + " result) {");

		String suffix = getSuffix();
		String prefix = getPrefix();
		
		if (prefix != null) {
			int count = prefix.length();
			sc.add("lexem = lexem.substring(" + count + ");");			
		}
		if (suffix != null) {
			int count = suffix.length();
			sc.add("lexem = lexem.substring(0, lexem.length() - " + count + ");");
			String javaSourceSuffix = StringUtil.escapeToJavaString(suffix);
			// take care of the escape character (may be null)
			String escapeCharacter = getEscapeCharacter();
			if (escapeCharacter != null) {
				String javaSourceEscapeCharacter = StringUtil.escapeToJavaString(escapeCharacter);
				sc.add("lexem = lexem.replace(\"" + javaSourceEscapeCharacter + javaSourceSuffix + "\", \"" + javaSourceSuffix + "\");");
				sc.add("lexem = lexem.replace(\"" + javaSourceEscapeCharacter + javaSourceEscapeCharacter + "\", \"" + javaSourceEscapeCharacter + "\");");
			}
		}
		sc.add("defaultTokenResolver.resolve(lexem, feature, result);");
		sc.add("}");
		sc.addLineBreak();
	}
	
	private void generateResolveMethod2(StringComposite sc) {
		ConcreteSyntax syntax = getContext().getConcreteSyntax();
		ConcreteSyntax containingSyntax = definition.getContainingSyntax(syntax);
		String importedTokenResolveResultClassName = getContext().getQualifiedClassName(TextResourceArtifacts.I_TOKEN_RESOLVE_RESULT, containingSyntax);
		sc.add("public void resolve(" + STRING + " lexem, " + E_STRUCTURAL_FEATURE + " feature, final " + iTokenResolveResultClassName + " result) {");
		sc.add("importedResolver.resolve(lexem, feature, new " + importedTokenResolveResultClassName + "() {");
		sc.add("public String getErrorMessage() {");
		sc.add("return result.getErrorMessage();");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public Object getResolvedToken() {");
		sc.add("return result.getResolvedToken();");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void setErrorMessage(String message) {");
		sc.add("result.setErrorMessage(message);");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void setResolvedToken(Object resolvedToken) {");
		sc.add("result.setResolvedToken(resolvedToken);");
		sc.add("}");
		sc.addLineBreak();
		sc.add("});");
		sc.add("}");
		sc.addLineBreak();
	}
	
	private String getPrefix() {
		String prefix = null;
		if (definition instanceof QuotedTokenDefinition) {
			prefix = ((QuotedTokenDefinition) definition).getPrefix();
		}
		return prefix;
	}

	private String getSuffix() {
		String suffix = null;
		if (definition instanceof QuotedTokenDefinition) {
			suffix = ((QuotedTokenDefinition) definition).getSuffix();
		}
		return suffix;
	}

	private String getEscapeCharacter() {
		String suffix = null;
		if (definition instanceof QuotedTokenDefinition) {
			suffix = ((QuotedTokenDefinition) definition).getEscapeCharacter();
		}
		return suffix;
	}

	public IGenerator<GenerationContext, CompleteTokenDefinition> newInstance(ICodeGenerationComponent parent, GenerationContext context, CompleteTokenDefinition parameters) {
		TokenResolverGenerator instance = new TokenResolverGenerator(parent, context);
		instance.setTokenDefinition(parameters);
		return instance;
	}

	public void setTokenDefinition(CompleteTokenDefinition tokenDefinition) {
		this.definition = tokenDefinition;
	}


}
