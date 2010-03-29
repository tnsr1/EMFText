package org.emftext.sdk.codegen.generators.mopp;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.*;

import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.composites.StringComposite;
import org.emftext.sdk.codegen.generators.JavaBaseGenerator;

public class LayoutInformationAdapterGenerator extends JavaBaseGenerator {

	private String layoutInformationClassName;

	public LayoutInformationAdapterGenerator() {
		super();
	}

	private LayoutInformationAdapterGenerator(GenerationContext context) {
		super(context, EArtifact.LAYOUT_INFORMATION_ADAPTER);
		layoutInformationClassName = getContext().getQualifiedClassName(EArtifact.LAYOUT_INFORMATION);
	}

	public IGenerator newInstance(GenerationContext context) {
		return new LayoutInformationAdapterGenerator(context);
	}

	public boolean generateJavaContents(StringComposite sc) {
		
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		
		sc.add("public class " + getResourceClassName() + " implements " + ADAPTER + " {");
		sc.addLineBreak();
		addFields(sc);
		addMethods(sc);
		sc.add("}");
		return true;
	}

	private void addMethods(StringComposite sc) {
		addGetTargetMethod(sc);
		addIsAdapterForTypeMethod(sc);
		addNotifyChangedMethod(sc);
		addSetTargetMethod(sc);
		addGetLayoutInformationsMethod(sc);
		addAddLayoutInformationMethod(sc);
	}

	private void addFields(StringComposite sc) {
		sc.add("private " + NOTIFIER + " target;");
		sc.add("private " + LIST + "<" + layoutInformationClassName + "> layoutInformations = new " + ARRAY_LIST + "<" + layoutInformationClassName + ">();");
		sc.addLineBreak();
	}

	private void addGetTargetMethod(StringComposite sc) {
		sc.add("public " + NOTIFIER + " getTarget() {");
		sc.add("return target;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addIsAdapterForTypeMethod(StringComposite sc) {
		sc.add("public boolean isAdapterForType(" + OBJECT + " type) {");
		sc.add("return false;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addNotifyChangedMethod(StringComposite sc) {
		sc.add("public void notifyChanged(" + NOTIFICATION + " notification) {");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addSetTargetMethod(StringComposite sc) {
		sc.add("public void setTarget(" + NOTIFIER + " newTarget) {");
		sc.add("this.target = newTarget;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetLayoutInformationsMethod(StringComposite sc) {
		sc.add("public " + LIST + "<" + layoutInformationClassName + "> getLayoutInformations() {");
		sc.add("return layoutInformations;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addAddLayoutInformationMethod(StringComposite sc) {
		sc.add("public void addLayoutInformation(" + layoutInformationClassName + " layoutInformation) {");
		sc.add("layoutInformations.add(layoutInformation);");
		sc.add("}");
		sc.addLineBreak();
	}
}