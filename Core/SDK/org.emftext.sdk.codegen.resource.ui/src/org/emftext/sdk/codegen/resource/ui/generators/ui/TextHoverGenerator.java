/*******************************************************************************
 * Copyright (c) 2006-2014
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
package org.emftext.sdk.codegen.resource.ui.generators.ui;

import static de.devboost.codecomposers.java.ClassNameConstants.LIST;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.BUNDLE;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.ECORE_UTIL;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.E_OBJECT;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.IO_EXCEPTION;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.LISTENER_LIST;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.PLATFORM;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.URL;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.ABSTRACT_REUSABLE_INFORMATION_CONTROL_CREATOR;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.ACTION;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.BAD_LOCATION_EXCEPTION;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.DEFAULT_INFORMATION_CONTROL;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.EDITORS_UI;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.FONT_DATA;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_INFORMATION_CONTROL;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_INFORMATION_CONTROL_CREATOR;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_INFORMATION_CONTROL_EXTENSION4;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_INPUT_CHANGED_LISTENER;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_REGION;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_SELECTION;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_SELECTION_CHANGED_LISTENER;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_SELECTION_PROVIDER;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_SHARED_IMAGES;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_TEXT_HOVER;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_TEXT_HOVER_EXTENSION;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_TEXT_HOVER_EXTENSION2;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_TEXT_VIEWER;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.J_FACE_RESOURCES;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.PLATFORM_UI;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.POINT;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.REGION;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.SELECTION_CHANGED_EVENT;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.SHELL;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.STRUCTURED_SELECTION;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.SWT;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.TOOL_BAR_MANAGER;

import org.emftext.sdk.codegen.parameters.ArtifactParameter;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.ui.UIConstants;
import org.emftext.sdk.codegen.resource.ui.generators.UIJavaBaseGenerator;

import de.devboost.codecomposers.java.JavaComposite;

public class TextHoverGenerator extends UIJavaBaseGenerator<ArtifactParameter<GenerationContext>> {

	public void generateJavaContents(JavaComposite sc) {
		
		sc.add("package " + getResourcePackageName() + ";");sc.addLineBreak();sc.addImportsPlaceholder();
		sc.addLineBreak();
		
		sc.addJavadoc(
			"A class to display the information of an element. " +
			"Most of the code is taken from " +
			"<code>org.eclipse.jdt.internal.ui.text.java.hover.JavadocHover</code>."
		);
		sc.add("public class " + getResourceClassName() + " implements " + I_TEXT_HOVER(sc) + ", " + I_TEXT_HOVER_EXTENSION(sc) + ", " + I_TEXT_HOVER_EXTENSION2(sc) + " {");
		sc.addLineBreak();

		addFields(sc);
		addInnerClasses(sc);
		addConstructor(sc);
		addMethods(sc);
		
		sc.add("}");
	}

	private void addMethods(JavaComposite sc) {
		addGetHoverInfoMethod(sc);
		addGetHoverRegionMethod(sc);
		addGetHoverControlCreatorMethod(sc);
		addGetInformationPresenterControlCreatorMethod(sc);
		addGetHoverInfo2Method(sc);
		addInternalGetHoverInfoMethod(sc);
		addGetHoverInfo3Method(sc);
		addGetStyleSheetMethod(sc);
		addLoadStyleSheetMethod(sc);
		addGetFirstProxyMethod(sc);
		addGetFirstNonProxyMethod(sc);
		addGetFirstObjectMethod(sc);
	}

	private void addInnerClasses(JavaComposite sc) {
		addSimpleSelectionProviderClass(sc);
		addOpenDeclarationActionClass(sc);
		addPresenterControlCreatorClass(sc);
		addHoverControlCreatorClass(sc);
	}

	private void addHoverControlCreatorClass(JavaComposite sc) {
		sc.addJavadoc("Hover control creator. Creates a hover control before focus.");
		sc.add("public static final class HoverControlCreator extends " + ABSTRACT_REUSABLE_INFORMATION_CONTROL_CREATOR(sc) + " {");
		sc.addLineBreak();
		
		sc.addJavadoc("The information presenter control creator.");
		sc.add("private final " + I_INFORMATION_CONTROL_CREATOR(sc) + " fInformationPresenterControlCreator;");
		sc.addLineBreak();
		
		sc.addJavadoc("@param informationPresenterControlCreator control creator for enriched hover");
		sc.add("public HoverControlCreator(" + I_INFORMATION_CONTROL_CREATOR(sc) + " informationPresenterControlCreator) {");
		sc.add("fInformationPresenterControlCreator = informationPresenterControlCreator;");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("public " + I_INFORMATION_CONTROL(sc) + " doCreateInformationControl(" + SHELL(sc) + " parent) {");
		sc.add("String tooltipAffordanceString = " + EDITORS_UI(sc) + ".getTooltipAffordanceString();");
		sc.add("if (" + browserInformationControlClassName + ".isAvailable(parent)) {");
		sc.add(browserInformationControlClassName + " iControl = new " + browserInformationControlClassName + "(parent, FONT, tooltipAffordanceString) {");
		sc.add("public " + I_INFORMATION_CONTROL_CREATOR(sc) + " getInformationPresenterControlCreator() {");
		sc.add("return fInformationPresenterControlCreator;");
		sc.add("}");
		sc.add("};");
		sc.add("return iControl;");
		sc.add("} else {");
		sc.add("return new " + DEFAULT_INFORMATION_CONTROL(sc) + "(parent, tooltipAffordanceString);");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("public boolean canReuse(" + I_INFORMATION_CONTROL(sc) + " control) {");
		sc.add("if (!super.canReuse(control)) {");
		sc.add("return false;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("if (control instanceof " + I_INFORMATION_CONTROL_EXTENSION4(sc) + ") {");
		sc.add("String tooltipAffordanceString = " + EDITORS_UI(sc) + ".getTooltipAffordanceString();");
		sc.add("((" + I_INFORMATION_CONTROL_EXTENSION4(sc) + ") control).setStatusText(tooltipAffordanceString);");
		sc.add("}");
		sc.addLineBreak();
		sc.add("return true;");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetHoverInfoMethod(JavaComposite sc) {
		sc.addComment("The warning about overriding or implementing a deprecated API cannot be avoided because the SourceViewerConfiguration class depends on ITextHover.");
		sc.add("public String getHoverInfo(" + I_TEXT_VIEWER(sc) + " textViewer, " + I_REGION(sc) + " hoverRegion) {");
		sc.add("Object hoverInfo = getHoverInfo2(textViewer, hoverRegion);");
		sc.add("if (hoverInfo == null) {");
		sc.add("return null;");
		sc.add("}");
		sc.add("return ((" + docBrowserInformationControlInputClassName + ") hoverInfo).getHtml();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetHoverRegionMethod(JavaComposite sc) {
		sc.add("public " + I_REGION(sc) + " getHoverRegion(" + I_TEXT_VIEWER(sc) + " textViewer, int offset) {");
		sc.add(POINT(sc) + " selection = textViewer.getSelectedRange();");
		sc.add("if (selection.x <= offset && offset < selection.x + selection.y) {");
		sc.add("return new " + REGION(sc) + "(selection.x, selection.y);");
		sc.add("}");
		sc.add("return new " + REGION(sc) + "(offset, 0);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addFields(JavaComposite sc) {
		sc.add("private static final String FONT = " + J_FACE_RESOURCES(sc) + ".DIALOG_FONT;");
		sc.addLineBreak();

		sc.add("private " + iResourceProviderClassName + " resourceProvider;");
		sc.add("private " + iHoverTextProviderClassName + " hoverTextProvider;");
		
		sc.addJavadoc("The style sheet (css).");
		sc.add("private static String styleSheet;");
		sc.addLineBreak();
		
		sc.addJavadoc("The hover control creator.");
		sc.add("private " + I_INFORMATION_CONTROL_CREATOR(sc) + " hoverControlCreator;");
		sc.addLineBreak();
		
		sc.addJavadoc("The presentation control creator.");
		sc.add("private " + I_INFORMATION_CONTROL_CREATOR(sc) + " presenterControlCreator;");
		sc.addLineBreak();
	}

	private void addGetHoverControlCreatorMethod(JavaComposite sc) {
		sc.add("public " + I_INFORMATION_CONTROL_CREATOR(sc) + " getHoverControlCreator() {");
		sc.add("if (hoverControlCreator == null) {");
		sc.add("hoverControlCreator = new HoverControlCreator(getInformationPresenterControlCreator());");
		sc.add("}");
		sc.add("return hoverControlCreator;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetInformationPresenterControlCreatorMethod(JavaComposite sc) {
		sc.add("public " + I_INFORMATION_CONTROL_CREATOR(sc) + " getInformationPresenterControlCreator() {");
		sc.add("if (presenterControlCreator == null) {");
		sc.add("presenterControlCreator = new PresenterControlCreator();");
		sc.add("}");
		sc.add("return presenterControlCreator;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetHoverInfo2Method(JavaComposite sc) {
		sc.add("public Object getHoverInfo2(" + I_TEXT_VIEWER(sc) + " textViewer, " + I_REGION(sc) + " hoverRegion) {");
		sc.add("return hoverTextProvider == null ? null : internalGetHoverInfo(textViewer, hoverRegion);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addInternalGetHoverInfoMethod(JavaComposite sc) {
		sc.add("private " + docBrowserInformationControlInputClassName + " internalGetHoverInfo(" + I_TEXT_VIEWER(sc) + " textViewer, " + I_REGION(sc) + " hoverRegion) {");
		sc.add(iTextResourceClassName + " textResource = resourceProvider.getResource();");
		sc.add("if (textResource == null) {");
		sc.add("return null;");
		sc.add("}");
		sc.add(iLocationMapClassName + " locationMap = textResource.getLocationMap();");
		sc.add(LIST(sc) + "<" + E_OBJECT(sc) + "> elementsAtOffset = locationMap.getElementsAt(hoverRegion.getOffset());");
		sc.add("if (elementsAtOffset == null || elementsAtOffset.size() == 0) {");
		sc.add("return null;");
		sc.add("}");
		sc.add("return getHoverInfo(elementsAtOffset, textViewer, null);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetHoverInfo3Method(JavaComposite sc) {
		sc.addJavadoc(
			"Computes the hover info.",
			"@param elements the resolved elements",
			"@param constantValue a constant value iff result contains exactly 1 constant field, or <code>null</code>",
			"@param previousInput the previous input, or <code>null</code>",
			"@return the HTML hover info for the given element(s) or <code>null</code> if no information is available"
		);
		sc.add("private " + docBrowserInformationControlInputClassName + " getHoverInfo(" + LIST(sc) + "<" + E_OBJECT(sc) + "> elements, " + I_TEXT_VIEWER(sc) + " textViewer, " + docBrowserInformationControlInputClassName + " previousInput) {");
		sc.add("StringBuffer buffer = new StringBuffer();");
		sc.add(E_OBJECT(sc) + " proxyObject = getFirstProxy(elements);");
		sc.add(E_OBJECT(sc) + " containerObject = getFirstNonProxy(elements);");
		sc.add(E_OBJECT(sc) + " declarationObject = null;");
		sc.addComment("get the token text, which is hovered. It is needed to jump to the declaration.");
		sc.add("String tokenText = \"\";");
		sc.add("if (proxyObject != null) {");
		sc.add(iTextResourceClassName + " textResource = resourceProvider.getResource();");
		sc.add(iLocationMapClassName + " locationMap = textResource.getLocationMap();");
		sc.add("int offset = locationMap.getCharStart(proxyObject);");
		sc.add("int length = locationMap.getCharEnd(proxyObject) + 1 - offset;");
		sc.add("try {");
		sc.add("tokenText = textViewer.getDocument().get(offset, length);");
		sc.add("} catch (" + BAD_LOCATION_EXCEPTION(sc) + " e) {");
		sc.add("}");
		sc.add("declarationObject = " + ECORE_UTIL(sc) + ".resolve(proxyObject, resourceProvider.getResource());");
		sc.add("if (declarationObject != null) {");
		sc.add(htmlPrinterClassName + ".addParagraph(buffer, hoverTextProvider.getHoverText(containerObject, declarationObject));");
		sc.add("}");
		sc.add("} else {");
		sc.add(htmlPrinterClassName + ".addParagraph(buffer, hoverTextProvider.getHoverText(elements.get(0)));");
		sc.add("}");
		sc.add("if (buffer.length() > 0) {");
		sc.add(htmlPrinterClassName + ".insertPageProlog(buffer, 0, " + textHoverClassName + ".getStyleSheet());");
		sc.add(htmlPrinterClassName + ".addPageEpilog(buffer);");
		sc.add("return new " + docBrowserInformationControlInputClassName + "(previousInput, declarationObject, resourceProvider.getResource(), buffer.toString(), tokenText);");
		sc.add("}");
		sc.add("return null;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetStyleSheetMethod(JavaComposite sc) {
		sc.addJavadoc(
			"Sets the style sheet font.",
			"@return the hover style sheet"
		);
		sc.add("private static String getStyleSheet() {");
		sc.add("if (styleSheet == null) {");
		sc.add("styleSheet = loadStyleSheet();");
		sc.add("}");
		sc.add("String css = styleSheet;");
		sc.addComment("Sets background color for the hover text window");
		sc.add("css += \"body {background-color:#FFFFE1;}\\n\";");
		sc.add(FONT_DATA(sc) + " fontData = " + J_FACE_RESOURCES(sc) + ".getFontRegistry().getFontData(FONT)[0];");
		sc.add("css = " + htmlPrinterClassName + ".convertTopLevelFont(css, fontData);");
		sc.addLineBreak();
		sc.add("return css;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetFirstProxyMethod(JavaComposite sc) {
		sc.add("private static " + E_OBJECT(sc) + " getFirstProxy(" + LIST(sc) + "<" + E_OBJECT(sc) + "> elements) {");
		sc.add("return getFirstObject(elements, true);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetFirstNonProxyMethod(JavaComposite sc) {
		sc.add("private static " + E_OBJECT(sc) + " getFirstNonProxy(" + LIST(sc) + "<" + E_OBJECT(sc) + "> elements) {");
		sc.add("return getFirstObject(elements, false);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetFirstObjectMethod(JavaComposite sc) {
		sc.add("private static " + E_OBJECT(sc) + " getFirstObject(" + LIST(sc) + "<" + E_OBJECT(sc) + "> elements, boolean proxy) {");
		sc.add("for (" + E_OBJECT(sc) + " object : elements) {");
		sc.add("if (proxy == object.eIsProxy()) {");
		sc.add("return object;");
		sc.add("}");
		sc.add("}");
		sc.add("return null;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addLoadStyleSheetMethod(JavaComposite sc) {
		sc.addJavadoc(
			"Loads and returns the hover style sheet.",
			"@return the style sheet, or <code>null</code> if unable to load"
		);
		sc.add("private static String loadStyleSheet() {");
		sc.add(BUNDLE(sc) + " bundle = " + PLATFORM(sc) + ".getBundle(" + uiPluginActivatorClassName + ".PLUGIN_ID);");
		sc.add(URL(sc) + " styleSheetURL = bundle.getEntry(\"/" + UIConstants.DEFAULT_CSS_DIR + "/" + UIConstants.HOVER_STYLE_FILENAME + "\");");
		sc.add("if (styleSheetURL != null) {");
		sc.add("try {");
		sc.add("return " + streamUtilClassName + ".getContent(styleSheetURL.openStream());");
		sc.add("} catch (" + IO_EXCEPTION(sc) + " ex) {");
		sc.add("ex.printStackTrace();");
		sc.add("}");
		sc.add("}");
		sc.add("return \"\";");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addPresenterControlCreatorClass(JavaComposite sc) {
		sc.addJavadoc("Presenter control creator. Creates a hover control after focus.");
		sc.add("public static final class PresenterControlCreator extends " + ABSTRACT_REUSABLE_INFORMATION_CONTROL_CREATOR(sc) + " {");
		sc.addLineBreak();
		sc.add("public " + I_INFORMATION_CONTROL(sc) + " doCreateInformationControl(" + SHELL(sc) + " parent) {");
		sc.add("if (" + browserInformationControlClassName + ".isAvailable(parent)) {");
		sc.add(TOOL_BAR_MANAGER(sc) + " tbm = new " + TOOL_BAR_MANAGER(sc) + "(" + SWT(sc) + ".FLAT);");
		sc.add(browserInformationControlClassName + " iControl = new " + browserInformationControlClassName + "(parent, FONT, tbm);");
		sc.add("final OpenDeclarationAction openDeclarationAction = new OpenDeclarationAction(iControl);");
		sc.add("tbm.add(openDeclarationAction);");
		sc.add("final SimpleSelectionProvider selectionProvider = new SimpleSelectionProvider();");
		sc.addLineBreak();
		sc.add(I_INPUT_CHANGED_LISTENER(sc) + " inputChangeListener = new " + I_INPUT_CHANGED_LISTENER(sc) + "() {");
		sc.add("public void inputChanged(Object newInput) {");
		sc.add("if (newInput == null) {");
		sc.add("selectionProvider.setSelection(new " + STRUCTURED_SELECTION(sc) + "());");
		sc.add("} else if (newInput instanceof " + docBrowserInformationControlInputClassName + ") {");
		sc.add(docBrowserInformationControlInputClassName + " input = (" + docBrowserInformationControlInputClassName + ") newInput;");
		sc.add("Object inputElement = input.getInputElement();");
		sc.add("selectionProvider.setSelection(new " + STRUCTURED_SELECTION(sc) + "(inputElement));");
		sc.addComment(
			"If there is an element of type EObject in the " +
			"input element, the button to open the declaration " +
			"will be set enable"
		);
		sc.add("boolean isEObjectInput = inputElement instanceof " + E_OBJECT(sc) + ";");
		sc.add("openDeclarationAction.setEnabled(isEObjectInput);");
		sc.add("if (isEObjectInput) {");
		sc.add("String simpleName = inputElement.getClass().getSimpleName();");
		sc.add("simpleName = simpleName.substring(0, simpleName.length() - 4);");
		sc.add("openDeclarationAction.setText(\"Open \" + simpleName);");
		sc.add("} else");
		sc.add("openDeclarationAction.setText(\"Open Declaration\");");
		sc.add("}");
		sc.add("}");
		sc.add("};");
		sc.add("iControl.addInputChangeListener(inputChangeListener);");
		sc.addLineBreak();
		sc.add("tbm.update(true);");
		sc.add("return iControl;");
		sc.add("} else {");
		sc.add("return new " + DEFAULT_INFORMATION_CONTROL(sc) + "(parent, true);");
		sc.add("}");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addOpenDeclarationActionClass(JavaComposite sc) {
		sc.addJavadoc(
			"This action will be activated if the button in the hover window is pushed " +
			"to jump to the declaration."
		);
		sc.add("public static class OpenDeclarationAction extends " + ACTION(sc) + " {");
		sc.addLineBreak();
		sc.add("private final " + browserInformationControlClassName + " infoControl;");
		sc.addLineBreak();
		sc.addJavadoc(
			"Creates the action to jump to the declaration.",
			"@param infoControl the info control holds the hover information and the target element"
		);
		sc.add("public OpenDeclarationAction(" + browserInformationControlClassName + " infoControl) {");
		sc.add("this.infoControl = infoControl;");
		sc.add("setText(\"Open Declaration\");");
		sc.add(I_SHARED_IMAGES(sc) + " images = " + PLATFORM_UI(sc) + ".getWorkbench().getSharedImages();");
		sc.add("setImageDescriptor(images.getImageDescriptor(" + I_SHARED_IMAGES(sc) + ".IMG_ETOOL_HOME_NAV));");
		sc.add("}");
		sc.addLineBreak();
		sc.addJavadoc("Creates, sets, activates a hyperlink.");
		sc.add("public void run() {");
		sc.add(docBrowserInformationControlInputClassName + " infoInput = (" + docBrowserInformationControlInputClassName + ") infoControl.getInput();");
		sc.add("infoControl.notifyDelayedInputChange(null);");
		// FIXME should have protocol to hide, rather than dispose
		sc.add("infoControl.dispose();");
		sc.add("if (infoInput.getInputElement() instanceof " + E_OBJECT(sc) + ") {");
		sc.add(E_OBJECT(sc) + " decEO = (" + E_OBJECT(sc) + ") infoInput.getInputElement();");
		sc.add("if (decEO != null && decEO.eResource() != null) {");
		sc.add(hyperlinkClassName + " hyperlink = new " + hyperlinkClassName + "(null, decEO, infoInput.getTokenText());");
		sc.add("hyperlink.open();");
		sc.add("}");
		sc.add("}");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addConstructor(JavaComposite sc) {
		sc.addJavadoc(
			"Creates a new TextHover to collect the information about the hovered " +
			"element."
		);
		sc.add("public " + getResourceClassName() + "(" + iResourceProviderClassName + " resourceProvider) {");
		sc.add("super();");
		sc.add("this.resourceProvider = resourceProvider;");
		sc.add("this.hoverTextProvider = new " + uiMetaInformationClassName + "().getHoverTextProvider();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addSimpleSelectionProviderClass(JavaComposite sc) {
		sc.addJavadoc(
			"A simple default implementation of a {@link " + I_SELECTION_PROVIDER(sc) + "}. It stores " +
			"the selection and notifies all selection change listeners when the selection " +
			"is set."
		);
		sc.add("public static class SimpleSelectionProvider implements " + I_SELECTION_PROVIDER(sc) + " {");
		sc.addLineBreak();
		sc.add("private final " + LISTENER_LIST(sc) + " selectionChangedListeners;");
		sc.add("private " + I_SELECTION(sc) + " selection;");
		sc.addLineBreak();
		sc.add("public SimpleSelectionProvider() {");
		sc.add("selectionChangedListeners = new " + LISTENER_LIST(sc) + "();");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public " + I_SELECTION(sc) + " getSelection() {");
		sc.add("return selection;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void setSelection(" + I_SELECTION(sc) + " selection) {");
		sc.add("this.selection = selection;");
		sc.addLineBreak();
		sc.add("Object[] listeners = selectionChangedListeners.getListeners();");
		sc.add("for (int i = 0; i < listeners.length; i++) {");
		sc.add("((" + I_SELECTION_CHANGED_LISTENER(sc) + ") listeners[i]).selectionChanged(new " + SELECTION_CHANGED_EVENT(sc) + "(this, selection));");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void removeSelectionChangedListener(" + I_SELECTION_CHANGED_LISTENER(sc) + " listener) {");
		sc.add("selectionChangedListeners.remove(listener);");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void addSelectionChangedListener(" + I_SELECTION_CHANGED_LISTENER(sc) + " listener) {");
		sc.add("selectionChangedListeners.add(listener);");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("}");
		sc.addLineBreak();
	}
}
