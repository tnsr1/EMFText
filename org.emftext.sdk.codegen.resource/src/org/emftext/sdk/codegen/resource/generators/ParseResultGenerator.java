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

import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.ARRAY_LIST;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.COLLECTION;
import static org.emftext.sdk.codegen.resource.generators.IClassNameConstants.E_OBJECT;

import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.parameters.ArtifactParameter;
import org.emftext.sdk.codegen.resource.GenerationContext;

public class ParseResultGenerator extends JavaBaseGenerator<ArtifactParameter<GenerationContext>> {
	
	@Override
	public void generateJavaContents(JavaComposite sc) {
		
        sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
        
        sc.add("public class " + getResourceClassName()+ " implements " + iParseResultClassName + " {");
        sc.addLineBreak();

        sc.add("private " + E_OBJECT + " root;");
        sc.add("private " + COLLECTION + "<" + iCommandClassName + "<" + iTextResourceClassName + ">> commands = new " + ARRAY_LIST + "<" + iCommandClassName + "<" + iTextResourceClassName + ">>();");
        sc.addLineBreak();
        
		sc.add("public " + getResourceClassName() + "() {");
		sc.add("super();");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("public void setRoot(" + E_OBJECT + " root) {");
		sc.add("this.root = root;");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("public " + E_OBJECT + " getRoot() {");
		sc.add("return root;");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("public " + COLLECTION + "<" + iCommandClassName + "<" + iTextResourceClassName + ">> getPostParseCommands() {");
		sc.add("return commands;");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("}");
    }

	
}
