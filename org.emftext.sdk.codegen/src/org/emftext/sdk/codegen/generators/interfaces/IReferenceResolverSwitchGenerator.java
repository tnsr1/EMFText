package org.emftext.sdk.codegen.generators.interfaces;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.E_OBJECT;

import java.io.PrintWriter;

import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.generators.BaseGenerator;

public class IReferenceResolverSwitchGenerator extends BaseGenerator {

	private String iReferenceResolveResultClassName;
	private String iConfigurableClassName;

	public IReferenceResolverSwitchGenerator() {
		super();
	}

	private IReferenceResolverSwitchGenerator(GenerationContext context) {
		super(context, EArtifact.I_REFERENCE_RESOLVER_SWITCH);
		iConfigurableClassName = getContext().getQualifiedClassName(EArtifact.I_CONFIGURABLE);
		iReferenceResolveResultClassName = getContext().getQualifiedClassName(EArtifact.I_REFERENCE_RESOLVE_RESULT);
	}

	public IGenerator newInstance(GenerationContext context) {
		return new IReferenceResolverSwitchGenerator(context);
	}

	public boolean generate(PrintWriter out) {
		org.emftext.sdk.codegen.composites.StringComposite sc = new org.emftext.sdk.codegen.composites.JavaComposite();
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		
		sc.add("// An IReferenceResolverSwitch is a object that holds references to multiple");
		sc.add("// other reference resolvers and delegates requests to the appropriate resolver.");
		sc.add("public interface " + getResourceClassName() + " extends " + iConfigurableClassName + " {");
		sc.addLineBreak();
		
		sc.add("// Attempts to resolve a reference string fuzzy (returning objects that do not match exactly).");
		sc.add("//");
		sc.add("// @param identifier The identifier for the reference.");
		sc.add("// @param container The object that contains the reference.");
		sc.add("// @param reference The reference that points to the target of the reference.");
		sc.add("// @param result an object to store the result of the resolve operation.");
		sc.add("public void resolveFuzzy(String identifier, " + E_OBJECT + " container, int position, " + iReferenceResolveResultClassName + "<" + E_OBJECT + "> result);");
		
		sc.add("}");
		out.print(sc.toString());
		return true;
	}
}