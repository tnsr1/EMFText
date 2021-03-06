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
package org.emftext.sdk.codegen.resource.ui.generators.ui.launch;

import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.ABSTRACT_LAUNCH_CONFIGURATION_TAB_GROUP;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_LAUNCH_CONFIGURATION_DIALOG;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_LAUNCH_CONFIGURATION_TAB;

import org.emftext.sdk.codegen.parameters.ArtifactParameter;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.ui.generators.UIJavaBaseGenerator;
import org.emftext.sdk.concretesyntax.OptionTypes;

import de.devboost.codecomposers.java.JavaComposite;

public class LaunchConfigurationTabGroupGenerator extends UIJavaBaseGenerator<ArtifactParameter<GenerationContext>> {

	public void generateJavaContents(JavaComposite sc) {
		sc.add("package " + getResourcePackageName() + ";");sc.addLineBreak();sc.addImportsPlaceholder();
		sc.addLineBreak();
		sc.addJavadoc(
			"A class that provides the tabs for the launch configuration" +
			(getContext().isLaunchSupportEnabled() ? "." : " (currently disabled).")
		);
		sc.add("public class " + getResourceClassName());
		if (getContext().isLaunchSupportEnabled()) {
			sc.add(" extends " + ABSTRACT_LAUNCH_CONFIGURATION_TAB_GROUP(sc));
		}
		sc.add(" {");
		sc.addLineBreak();
		if (getContext().isLaunchSupportEnabled()) {
			addCreateTabsMethod(sc);
		}
		sc.add("}");
	}

	private void addCreateTabsMethod(JavaComposite sc) {
		sc.add("public void createTabs(" + I_LAUNCH_CONFIGURATION_DIALOG(sc) + " dialog, String mode) {");
		sc.addComment("Set the " + OptionTypes.OVERRIDE_LAUNCH_CONFIGURATION_TAB_GROUP.getLiteral() + " option to false to implement this method.");
		sc.add("setTabs(new " + I_LAUNCH_CONFIGURATION_TAB(sc) + "[] {new " + launchConfigurationMainTabClassName + "()});");
		sc.add("}");
		sc.addLineBreak();
	}
}
