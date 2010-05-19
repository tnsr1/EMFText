package org.emftext.sdk.codegen.newproject.generators;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.emftext.sdk.codegen.newproject.NewProjectParameters;

/**
 * Generates a simple Ecore model with some example meta classes.
 */
public class MetaModelGenerator extends ModelGenerator {

	private static final EcoreFactory ECORE_FACTORY = EcoreFactory.eINSTANCE;
	
	public MetaModelGenerator() {
		super();
	}

	public EObject generateModel() {
		NewProjectParameters parameters = getContext().getParameters();

		EClass shapeClass = ECORE_FACTORY.createEClass();
		shapeClass.setName("Shape");
		shapeClass.setAbstract(true);
		
		EClass rectangleClass = ECORE_FACTORY.createEClass();
		rectangleClass.setName("Rectangle");
		rectangleClass.getESuperTypes().add(shapeClass);
		
		EClass circleClass = ECORE_FACTORY.createEClass();
		circleClass.setName("Circle");
		circleClass.getESuperTypes().add(shapeClass);

		EClass shapeSetClass = ECORE_FACTORY.createEClass();
		shapeSetClass.setName("ShapeSet");

		EReference shapesReference = ECORE_FACTORY.createEReference();
		shapesReference.setName("shapes");
		shapesReference.setEType(shapeClass);
		shapesReference.setLowerBound(1);
		shapesReference.setUpperBound(-1);
		shapesReference.setContainment(true);
		shapeSetClass.getEStructuralFeatures().add(shapesReference);

		EPackage ePackage = ECORE_FACTORY.createEPackage();
		ePackage.getEClassifiers().add(shapeClass);
		ePackage.getEClassifiers().add(rectangleClass);
		ePackage.getEClassifiers().add(circleClass);
		ePackage.getEClassifiers().add(shapeSetClass);
		
		ePackage.setName(parameters.getName());
		ePackage.setNsPrefix(parameters.getNamespacePrefix());
		ePackage.setNsURI(parameters.getNamespaceUri());

		getContext().setEPackage(ePackage);
		return ePackage;
	}

	public String getModelPath() {
		NewProjectParameters parameters = getContext().getParameters();
		String metaModelFileName = parameters.getEcoreFile();
		String pathToMetaModel = getFileInMetaModelFolder(metaModelFileName);
		return pathToMetaModel;
	}
}
