package org.emftext.sdk.codegen.generators;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.E_CLASS;

import java.io.PrintWriter;
import java.util.Collection;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.composites.StringComposite;
import org.emftext.sdk.codegen.util.ConcreteSyntaxUtil;
import org.emftext.sdk.codegen.util.GenClassUtil;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;

public class SyntaxCoverageInformationProviderGenerator extends BaseGenerator {

	private final ConcreteSyntaxUtil csUtil = new ConcreteSyntaxUtil();
	private final GenClassUtil genClassUtil = new GenClassUtil();

	public SyntaxCoverageInformationProviderGenerator() {
		super();
	}

	public SyntaxCoverageInformationProviderGenerator(GenerationContext context) {
		super(context, EArtifact.SYNTAX_COVERAGE_INFORMATION_PROVIDER);
	}

	@Override
	public boolean generate(PrintWriter out) {
		StringComposite sc = new JavaComposite();
		
        sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
        
        sc.add("public class " + getResourceClassName()+ " {");
        sc.addLineBreak();
		addGetClassesWithSyntaxMethod(sc);
		sc.add("}");
		
		out.write(sc.toString());
		return true;
	}

	private void addGetClassesWithSyntaxMethod(StringComposite sc) {
		ConcreteSyntax syntax = getContext().getConcreteSyntax();
		
		Collection<GenClass> classesWithSyntax = csUtil.getClassesWithSyntax(syntax);
		sc.add("public " + E_CLASS + "[] getClassesWithSyntax() {");
		sc.add("return new " + E_CLASS + "[] {");
		for (GenClass classWithSyntax : classesWithSyntax) {
			sc.add(genClassUtil.getAccessor(classWithSyntax) + ",");
		}
		sc.add("};");
		sc.add("}");
        sc.addLineBreak();
	}

	public IGenerator newInstance(GenerationContext context) {
		return new SyntaxCoverageInformationProviderGenerator(context);
	}
}