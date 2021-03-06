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
package org.emftext.sdk.codegen.resource.ui.generators.ui;

import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.CONTROL;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.FONT_METRICS;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.GC;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.JFACE_DIALOG;

import org.emftext.sdk.codegen.parameters.ArtifactParameter;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.ui.generators.UIJavaBaseGenerator;

import de.devboost.codecomposers.java.JavaComposite;

public class PixelConverterGenerator extends UIJavaBaseGenerator<ArtifactParameter<GenerationContext>> {

	public void generateJavaContents(JavaComposite sc) {
		
		sc.add("package " + getResourcePackageName() + ";");sc.addLineBreak();sc.addImportsPlaceholder();
		sc.addLineBreak();
		
		sc.addJavadoc("A utility class for pixel conversion.");
		sc.add("public class " + getResourceClassName() + " {");
		sc.addLineBreak();
		sc.add("private " + FONT_METRICS(sc) + " fFontMetrics;");
		sc.addLineBreak();
		sc.add("public " + getResourceClassName() + "(" + CONTROL(sc) + " control) {");
		sc.add(GC(sc) + " gc = new " + GC(sc) + "(control);");
		sc.add("gc.setFont(control.getFont());");
		sc.add("fFontMetrics = gc.getFontMetrics();");
		sc.add("gc.dispose();");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("public int convertHeightInCharsToPixels(int chars) {");
		sc.add("return " + JFACE_DIALOG(sc) + ".convertHeightInCharsToPixels(fFontMetrics, chars);");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("public int convertHorizontalDLUsToPixels(int dlus) {");
		sc.add("return " + JFACE_DIALOG(sc) + ".convertHorizontalDLUsToPixels(fFontMetrics, dlus);");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("public int convertVerticalDLUsToPixels(int dlus) {");
		sc.add("return " + JFACE_DIALOG(sc) + ".convertVerticalDLUsToPixels(fFontMetrics, dlus);");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("public int convertWidthInCharsToPixels(int chars) {");
		sc.add("return " + JFACE_DIALOG(sc) + ".convertWidthInCharsToPixels(fFontMetrics, chars);");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("}");
	}
}
