package org.emftext.sdk.codegen.generators;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.*;
import org.emftext.sdk.codegen.generators.BaseGenerator;
import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;

import java.io.PrintWriter;

public class ContextDependentURIFragmentGenerator extends BaseGenerator {

	private String qualifiedReferenceResolveResultClassName;

	public ContextDependentURIFragmentGenerator() {
		super();
	}
	
	private ContextDependentURIFragmentGenerator(GenerationContext context) {
		super(context, EArtifact.CONTEXT_DEPENDENT_URI_FRAGMENT);
		qualifiedReferenceResolveResultClassName = context.getQualifiedClassName(EArtifact.REFERENCE_RESOLVE_RESULT);
	}

	public boolean generate(PrintWriter out) {
		org.emftext.sdk.codegen.composites.StringComposite sc = new org.emftext.sdk.codegen.composites.JavaComposite();
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("// Standard implementation of <code>IContextDependentURIFragment</code>.");
		sc.add("//");
		sc.add("// @param <ContainerType> the type of the object that contains the reference which shall be resolved by this fragment.");
		sc.add("// @param <ReferenceType> the type of the reference which shall be resolved by this fragment.");
		sc.add("//");
		sc.add("public abstract class " + getResourceClassName() + "<ContainerType extends " + E_OBJECT + ", ReferenceType extends " + E_OBJECT + "> implements " + getClassNameHelper().getI_CONTEXT_DEPENDENT_URI_FRAGMENT() + "<ReferenceType> {");
		sc.addLineBreak();
		sc.add("protected String identifier;");
		sc.add("protected ContainerType container;");
		sc.add("protected " + E_REFERENCE + " reference;");
		sc.add("protected int positionInReference;");
		sc.add("protected " + E_OBJECT + " proxy;");
		sc.add("protected " + getClassNameHelper().getI_REFERENCE_RESOLVE_RESULT() + "<ReferenceType> result;");
		sc.addLineBreak();
		sc.add("private boolean resolving;");
		sc.addLineBreak();
		sc.add("public " + getResourceClassName() + "(String identifier, ContainerType container, " + E_REFERENCE + " reference, int positionInReference, " + E_OBJECT + " proxy) {");
		sc.add("this.identifier = identifier;");
		sc.add("this.container = container;");
		sc.add("this.reference = reference;");
		sc.add("this.positionInReference = positionInReference;");
		sc.add("this.proxy = proxy;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public boolean isResolved() {");
		sc.add("return result != null;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public synchronized " + getClassNameHelper().getI_REFERENCE_RESOLVE_RESULT() + "<ReferenceType> resolve() {");
		sc.add("if (resolving) {");
		sc.add("return null;");
		sc.add("}");
		sc.add("resolving = true;");
		sc.add("if (result == null || !result.wasResolved()) {");
		sc.add("result = new " + qualifiedReferenceResolveResultClassName + "<ReferenceType>(false);");
		sc.add("//set an initial default error message");
		sc.add("result.setErrorMessage(getStdErrorMessage());");
		sc.addLineBreak();
		sc.add("" + getClassNameHelper().getI_REFERENCE_RESOLVER() + "<ContainerType, ReferenceType> resolver = getResolver();");
		sc.add("//do the actual resolving");
		sc.add("resolver.resolve(getIdentifier(), getContainer(), getReference(), getPositionInReference(), false, result);");
		sc.addLineBreak();
		sc.add("//EMFText allows proxies to resolve to multiple objects");
		sc.add("//the first is returned, the others are added here to the reference");
		sc.add("if(result.wasResolvedMultiple()) {");
		sc.add("handleMultipleResults();");
		sc.add("}");
		sc.add("}");
		sc.add("resolving = false;");
		sc.add("return result;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public abstract " + getClassNameHelper().getI_REFERENCE_RESOLVER() + "<ContainerType, ReferenceType> getResolver();");
		sc.addLineBreak();
		sc.add("private void handleMultipleResults() {");
		sc.add(E_LIST + "<" + E_OBJECT + "> list = null;");
		sc.add(OBJECT + " temp = container.eGet(reference);");
		sc.add("if (temp instanceof " + E_LIST + "<?>) {");
		sc.add("list = " + getClassNameHelper().getCAST_UTIL() + ".cast(temp);");
		sc.add("}");
		sc.addLineBreak();
		sc.add("boolean first = true;");
		sc.add("for(" + getClassNameHelper().getI_REFERENCE_MAPPING() + "<ReferenceType> mapping : result.getMappings()) {");
		sc.add("if (first) {");
		sc.add("first = false;");
		sc.add("} else if (list != null) {");
		sc.add("addResultToList(mapping, proxy, list);");
		sc.add("} else {");
		sc.add(getClassNameHelper().getEMFTEXT_RUNTIME_PLUGIN() + ".logError(container.eClass().getName() + \".\" + reference.getName() + \" has multiplicity 1 but was resolved to multiple elements\", null);");
		sc.add("}");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
		sc.add("private void addResultToList(" + getClassNameHelper().getI_REFERENCE_MAPPING() + "<ReferenceType> mapping, " + E_OBJECT + " proxy, " + E_LIST + "<" + E_OBJECT + "> list) {");
		sc.add("" + E_OBJECT + " target = null;");
		sc.add("int proxyPosition = list.indexOf(proxy);");
		sc.addLineBreak();
		sc.add("if (mapping instanceof " + getClassNameHelper().getI_ELEMENT_MAPPING() + "<?>) {");
		sc.add("target = ((" + getClassNameHelper().getI_ELEMENT_MAPPING() + "<ReferenceType>) mapping).getTargetElement();");
		sc.add("} else if (mapping instanceof " + getClassNameHelper().getI_URI_MAPPING() + "<?>) {");
		sc.add("target = " + ECORE_UTIL + ".copy(proxy);");
		sc.add(URI + " uri = ((" + getClassNameHelper().getI_URI_MAPPING() + "<ReferenceType>) mapping).getTargetIdentifier();");
		sc.add("((" + INTERNAL_E_OBJECT + ") target).eSetProxyURI(uri);");
		sc.add("} else {");
		sc.add("assert false;");
		sc.add("}");
		sc.add("try {");
		sc.add("// if target is an another proxy and list is \"unique\"");
		sc.add("// add() will try to resolve the new proxy to check for uniqueness.");
		sc.add("// There seems to be no way to avoid that. Until now this does not");
		sc.add("// cause any problems.");
		sc.add("if (proxyPosition + 1 == list.size()) {");
		sc.add("list.add(target);");
		sc.add("} else {");
		sc.add("list.add(proxyPosition + 1, target);");
		sc.add("}");
		sc.add("} catch (Exception e1) {");
		sc.add("e1.printStackTrace();");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
		sc.add("private String getStdErrorMessage() {");
		sc.add("String typeName = this.getReference().getEType().getName();");
		sc.add("String msg = typeName + \" '\" + identifier + \"' not declared\";");
		sc.add("return msg;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public String getIdentifier() {");
		sc.add("return identifier;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public ContainerType getContainer() {");
		sc.add("return container;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public " + E_REFERENCE + " getReference() {");
		sc.add("return reference;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public int getPositionInReference() {");
		sc.add("return positionInReference;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public " + E_OBJECT + " getProxy() {");
		sc.add("return proxy;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("}");
		out.print(sc.toString());
		return true;
	}

	public IGenerator newInstance(GenerationContext context) {
		return new ContextDependentURIFragmentGenerator(context);
	}
}