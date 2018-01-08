package parser.UML;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import parser.Entities.ClassType;
import parser.Entities.OperationType;
import parser.Entities.PropertyType;

import java.util.ArrayList;
import java.util.List;

public class UMLReader {

    private final static String OWNED_ATTRIBUTE_NAME = "ownedAttribute";
    private final static String OWNED_OPERATION_NAME = "ownedOperation";
    private final static String OWNED_DEFAULT_VALUE = "defaultValue";
    private final static String PACKED_ELEMENT = "packagedElement";

    private List<ClassType> classList = new ArrayList<>();
    private Resource res;

    public UMLReader(String filePath) {
        if (loadUML(filePath)) {
            generateClassList();
        }
    }

    private boolean loadUML(String filePath) {
        ResourceSet set = new ResourceSetImpl();
        set.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        set.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
                .put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        res = set.getResource(URI.createFileURI(filePath), true);
        return res != null;
    }

    public List<ClassType> getClassList() {
        return classList;
    }

    private void generateClassList() {
        for (EObject eObject : res.getContents()) {
            for (EObject object : eObject.eContents()) {
                if(object.eContainingFeature().getName().equals(PACKED_ELEMENT)) {
                    ClassType classType = new ClassType(ValueExtractor.getTruncatedName(object.eGet(object.eClass().getEStructuralFeature("qualifiedName"))));
                    for (EObject ownedField : object.eContents()) {
                        if (ownedField.eContainingFeature().getName().equals(OWNED_ATTRIBUTE_NAME)) {
                            PropertyType propertyType = new PropertyType(
                                    ValueExtractor.getTruncatedName(ownedField.eGet(ownedField.eClass().getEStructuralFeature("qualifiedName")))
                            );
                            String type = extractType(ownedField);
                            if (!type.isEmpty()) {
                                propertyType.setType(type);
                            }
                            String defaultValue = extractDefaultValue(ownedField);
                            if (!defaultValue.isEmpty()) {
                                propertyType.setDefaultValue(defaultValue);
                            }
                            classType.addProperty(propertyType);
                        } else if (ownedField.eContainingFeature().getName().equals(OWNED_OPERATION_NAME)) {
                            OperationType operationType = new OperationType(ValueExtractor.getTruncatedName(ownedField.eGet(ownedField.eClass().getEStructuralFeature("qualifiedName"))));
                            classType.addOperation(operationType);
                        }
                    }
                    classList.add(classType);
                }
            }
        }
    }

    private String extractDefaultValue(EObject eObject) {
        String defaultValue = "";
        for (EObject innerObject : eObject.eContents()) {
            if (innerObject.eContainingFeature().getName().equals(OWNED_DEFAULT_VALUE)) {
                defaultValue = innerObject.eGet(innerObject.eClass().getEStructuralFeature("value")).toString();
            }
        }
        return defaultValue;
    }

    private String extractType(EObject eObject) {
        String type = "";
        type = ValueExtractor.extractType(eObject.eGet(eObject.eClass().getEStructuralFeature("type")));
        return type;
    }
}
