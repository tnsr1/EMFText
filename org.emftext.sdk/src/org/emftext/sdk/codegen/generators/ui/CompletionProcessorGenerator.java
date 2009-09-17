package org.emftext.sdk.codegen.generators.ui;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.CODE_COMPLETION_HELPER;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.COLLECTION;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.COMPLETION_PROPOSAL;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.CONTEXT_INFORMATION;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.I_COMPLETION_PROPOSAL;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.I_CONTENT_ASSIST_PROCESSOR;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.I_CONTEXT_INFORMATION;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.I_CONTEXT_INFORMATION_VALIDATOR;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.I_TEXT_RESOURCE;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.I_TEXT_VIEWER;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.RESOURCE;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.STRING_UTIL;

import java.io.PrintWriter;

import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.generators.BaseGenerator;

public class CompletionProcessorGenerator extends BaseGenerator {

	private String editorClassName;

	public CompletionProcessorGenerator() {
		super();
	}

	private CompletionProcessorGenerator(GenerationContext context) {
		super(context, EArtifact.COMPLETION_PROCESSOR);
		editorClassName = getContext().getQualifiedClassName(EArtifact.EDITOR);
	}

	public boolean generate(PrintWriter out) {
		org.emftext.sdk.codegen.composites.StringComposite sc = new org.emftext.sdk.codegen.composites.JavaComposite();
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("public class " + getResourceClassName() + " implements " + I_CONTENT_ASSIST_PROCESSOR + " {");
		sc.addLineBreak();

		addFields(sc);
		addConstructor(sc);
		addMethods(sc);
		
		sc.add("}");
		out.print(sc.toString());
		return true;
	}

	private void addFields(org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("private " + editorClassName + " editor;");
		sc.addLineBreak();
	}

	private void addMethods(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		addComputeCompletionProposalsMethod(sc);
		addComputeContextInformationMethod(sc);
		addGetCompletionProposalAutoActivationCharactersMethod(sc);
		addGetContextInformationAutoActivationCharactersMethod(sc);
		addGetContextInformationValidatorMethod(sc);
		addGetErrorMessageMethod(sc);
	}

	private void addGetErrorMessageMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public String getErrorMessage() {");
		sc.add("return null;");
		sc.add("}");
	}

	private void addGetContextInformationValidatorMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public " + I_CONTEXT_INFORMATION_VALIDATOR + " getContextInformationValidator() {");
		sc.add("return null;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetContextInformationAutoActivationCharactersMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public char[] getContextInformationAutoActivationCharacters() {");
		sc.add("return null;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetCompletionProposalAutoActivationCharactersMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public char[] getCompletionProposalAutoActivationCharacters() {");
		sc.add("return null;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addComputeContextInformationMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public " + I_CONTEXT_INFORMATION + "[] computeContextInformation(" + I_TEXT_VIEWER + " viewer, int offset) {");
		sc.add("return null;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addComputeCompletionProposalsMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public " + I_COMPLETION_PROPOSAL + "[] computeCompletionProposals(" + I_TEXT_VIEWER + " viewer, int offset) {");
		sc.addLineBreak();
		sc.add(RESOURCE + " resource = editor.getResource();");
		sc.add(I_TEXT_RESOURCE + " textResource = (" + I_TEXT_RESOURCE + ") resource;");
		sc.add("String content = viewer.getDocument().get();");
		sc.add(CODE_COMPLETION_HELPER + " helper = new " + CODE_COMPLETION_HELPER + "();");
		sc.add(COLLECTION + "<String> proposals = helper.computeCompletionProposals(textResource.getMetaInformation(), content, offset);");
		sc.addLineBreak();
		sc.add(I_COMPLETION_PROPOSAL + "[] result = new " + I_COMPLETION_PROPOSAL + "[proposals.size()];");
		sc.add("int i = 0;");
		sc.add("for (String proposal : proposals) {");
		sc.add(I_CONTEXT_INFORMATION + " info = new " + CONTEXT_INFORMATION + "(proposal, proposal);");
		sc.add("String contentBefore = content.substring(0, offset);");
		sc.add("String insertString = " + STRING_UTIL + ".getMissingTail(contentBefore, proposal);");
		sc.add("result[i++] = new " + COMPLETION_PROPOSAL + "(insertString, offset, 0, insertString.length(), null, proposal, info, proposal);");
		sc.add("}");
		sc.add("return result;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addConstructor(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public " + getResourceClassName() + "(" + editorClassName + " editor) {");
		sc.add("this.editor = editor;");
		sc.add("}");
		sc.addLineBreak();
	}

	public IGenerator newInstance(GenerationContext context) {
		return new CompletionProcessorGenerator(context);
	}
}