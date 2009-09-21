package org.emftext.sdk.codegen.generators.interfaces;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.MAP;

import java.io.PrintWriter;

import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.generators.BaseGenerator;

public class IOptionProviderGenerator extends BaseGenerator {

	public IOptionProviderGenerator() {
		super();
	}

	private IOptionProviderGenerator(GenerationContext context) {
		super(context, EArtifact.I_OPTION_PROVIDER);
	}

	public IGenerator newInstance(GenerationContext context) {
		return new IOptionProviderGenerator(context);
	}

	public boolean generate(PrintWriter out) {
		org.emftext.sdk.codegen.composites.StringComposite sc = new org.emftext.sdk.codegen.composites.JavaComposite();
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		
		sc.add("// Implementors of this interface can provide options that");
		sc.add("// are used when resources are loaded.");
		sc.add("public interface " + getResourceClassName() + " {");
		sc.addLineBreak();
		
		sc.add("// The name of the attribute of the default_load_options");
		sc.add("// extension point that specifies to which resources an");
		sc.add("// option provider applies.");
		sc.add("public static final String CS_NAME = \"csName\";");
		sc.addLineBreak();
		
		sc.add("// Returns a map of options. The keys are the names of the");
		sc.add("// options, the values are arbitrary object that provide");
		sc.add("// additional information or logic for the option.");
		sc.add("public " + MAP + "<?,?> getOptions();");
		
		sc.add("}");
		out.print(sc.toString());
		return true;
	}
}