package parser.UML;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;


import parser.Entities.ClassType;
import parser.Entities.ConnectionPointType;
import parser.Entities.OperationType;
import parser.Entities.PropertyType;
import parser.Entities.RegionType;
import parser.Entities.StateMachineType;
import parser.Entities.StateType;
import parser.Entities.SubvertexType;
import parser.Entities.TransitionType;

import java.util.ArrayList;
import java.util.List;

public class UMLReader {

    private final static String OWNED_ATTRIBUTE_NAME = "ownedAttribute";
    private final static String OWNED_OPERATION_NAME = "ownedOperation";
    private final static String OWNED_DEFAULT_VALUE = "defaultValue";
    private final static String OWNED_REGION_VALUE = "region";
    private final static String PACKED_ELEMENT = "packagedElement";
    private final static String CLASS_TYPE = "ClassImpl";
    private final static String STATE_TYPE = "StateImpl";
    private final static String STATE_MACHINE = "StateMachineImpl";
    private final static String TRANSITION = "transition";
    private final static String SUBVERTEX = "subvertex";
    private final static String CONNECTION_POINT = "connectionPoint";

    
    private List<ClassType> classList = new ArrayList<>();
    private List<StateMachineType> stateMachineList = new ArrayList<>();
    private Resource res;

    public UMLReader(String filePath) {
        if (loadUML(filePath)) {
            generateList();
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
        return this.classList;
    }
    public List<StateMachineType> getStateMachineList() {
        return this.stateMachineList;
    }
    private void generateList() {
        for (EObject eObject : res.getContents()) {
            for (EObject object : eObject.eContents()) {
                if(object.eContainingFeature().getName().equals(PACKED_ELEMENT)) {
                	
                	//Class or StateMachine
					String packed_type = ValueExtractor.getType(object);
                	if (packed_type.equals(CLASS_TYPE)) {
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
                	} else if(packed_type.equals(STATE_MACHINE)) {
                        StateMachineType stateMachineType = new StateMachineType(ValueExtractor.getTruncatedName(object.eGet(object.eClass().getEStructuralFeature("qualifiedName"))));
                        for (EObject regionField : object.eContents()) {
                            if (regionField.eContainingFeature().getName().equals(OWNED_REGION_VALUE)) {
                            	RegionType regionType = new RegionType(ValueExtractor.getTruncatedName(regionField.eGet(regionField.eClass().getEStructuralFeature("qualifiedName"))));
                            	List<TransitionType> inherited_Transitions = new ArrayList<>();
                            	for (EObject ownedField : regionField.eContents()) {
                                    if (ownedField.eContainingFeature().getName().equals(TRANSITION)) {
                                    	//Get name
                                    	String name = ownedField.eGet(ownedField.eClass().getEStructuralFeature("name")).toString();
                                    	//Get source name
                                    	EObject source =  (EObject)ownedField.eGet(ownedField.eClass().getEStructuralFeature("source"));
                                    	String sourceName = source.eGet(source.eClass().getEStructuralFeature("name")).toString();
                                    	//Get target name
                                    	EObject target = (EObject)ownedField.eGet(ownedField.eClass().getEStructuralFeature("target"));
                                    	String targetName = target.eGet(target.eClass().getEStructuralFeature("name")).toString();
                                    	
                                    	// Apparently if u create transition from entry point from state to something inside state then this transition appears to be in inherited state
                                    	String tag = source.eContainingFeature().getName();
                                    	if(tag.equals("connectionPoint")) {
                                    		String type = ValueExtractor.getType(source);
                                    		if (type.equals("PseudostateImpl")) {
                                            	String source_kind = source.eGet(source.eClass().getEStructuralFeature("kind")).toString();
                            	                if(source_kind.equals("entryPoint")){
                            	                	// Take this transition to state
                            	                	TransitionType inherited_Transition =  new TransitionType(sourceName, targetName, name);
                            	                	inherited_Transitions.add(inherited_Transition);
                            	                } 
                                    		}
                                    	}else {
                    	                	TransitionType transitionType = new TransitionType(sourceName, targetName, name);
                    	                	regionType.addTransition(transitionType);
                    	                }                                  	


                                    } else if (ownedField.eContainingFeature().getName().equals(SUBVERTEX)) {
                                    	//Get name
                                    	String name = ownedField.eGet(ownedField.eClass().getEStructuralFeature("name")).toString();

                                    	//Get type
                                    	String type = ValueExtractor.getType(ownedField);
                                    	

                                    	if(type.equals(STATE_TYPE)) {
                                        	//Get state
                                        	StateType stateType_inside = new StateType(name);
                                        	stateLoop(stateType_inside, ownedField, inherited_Transitions);
                                        	regionType.addState(stateType_inside);
                                    	}

                                        SubvertexType subvertexType = new SubvertexType(
                                        			name, type);  
                                		//Get kind if exist (for fork, and initial states)
                                    	if (ownedField.eClass().getEStructuralFeature("kind") != null) {
                                    		String kind = ownedField.eGet(ownedField.eClass().getEStructuralFeature("kind")).toString();
                                    		subvertexType.setKind(kind);
                                    	}
                                    	regionType.addSubvertex(subvertexType);
                                    	
                                    }
                                }
                            	stateMachineType.addRegion(regionType);
                            }
                        }
                        stateMachineList.add(stateMachineType);
                	}
                }
            }
        }
    }


    private void stateLoop(StateType stateType, EObject field, List<TransitionType> inherited_transitions) {
        for (EObject stateField : field.eContents()) {
            if (stateField.eContainingFeature().getName().equals(OWNED_REGION_VALUE)) {
            	RegionType regionType = new RegionType(ValueExtractor.getTruncatedName(stateField.eGet(stateField.eClass().getEStructuralFeature("qualifiedName"))));
            	// Add inherited transitions
            	for(TransitionType inherited_Transition: inherited_transitions) {
                	regionType.addTransition(inherited_Transition);
            	}
            	
            	List<TransitionType> inherited_Transitions_inside = new ArrayList<>();
            	for (EObject ownedField : stateField.eContents()) {
                    if (ownedField.eContainingFeature().getName().equals(TRANSITION)) {
                    	//Get name
                    	String name = ownedField.eGet(ownedField.eClass().getEStructuralFeature("name")).toString();
                    	//Get source name
                    	EObject source =  (EObject)ownedField.eGet(ownedField.eClass().getEStructuralFeature("source"));
                    	String sourceName = source.eGet(source.eClass().getEStructuralFeature("name")).toString();
                    	//Get target name
                    	EObject target = (EObject)ownedField.eGet(ownedField.eClass().getEStructuralFeature("target"));
                    	String targetName = target.eGet(target.eClass().getEStructuralFeature("name")).toString();
                    	
                    	// Apparently if u create transition from entry point from state to something inside state then this transition appears to be in inherited state
                    	String tag = source.eContainingFeature().getName();
                    	if(tag.equals("connectionPoint")) {
                    		String type = ValueExtractor.getType(source);
                    		if (type.equals("PseudostateImpl")) {
                            	String source_kind = source.eGet(source.eClass().getEStructuralFeature("kind")).toString();
            	                if(source_kind.equals("entryPoint")){
            	                	// Take this transition to state
            	                	TransitionType inherited_Transition =  new TransitionType(sourceName, targetName, name);
            	                	inherited_Transitions_inside.add(inherited_Transition);
            	                } 
                    		}
                    	}else {
    	                	TransitionType transitionType = new TransitionType(sourceName, targetName, name);
    	                	regionType.addTransition(transitionType);
    	                }
                    	
                    }else if (ownedField.eContainingFeature().getName().equals(SUBVERTEX)) {
                    	//Get name
                    	String name = ownedField.eGet(ownedField.eClass().getEStructuralFeature("name")).toString();

                    	//Get type
                    	String type = ValueExtractor.getType(ownedField);
                    	
                    	if(type.equals(STATE_TYPE)) {
                    		StateType stateType_inside = new StateType(name);
                    		stateLoop(stateType_inside, ownedField, inherited_Transitions_inside);
                    		regionType.addState(stateType_inside);
                    	}
                	
                    	SubvertexType subvertexType = new SubvertexType(
                    			name, type);  
                    	
                		//Get kind if exist (for fork, and initial states)
                    	if (ownedField.eClass().getEStructuralFeature("kind") != null) {
                    		String kind = ownedField.eGet(ownedField.eClass().getEStructuralFeature("kind")).toString();
                    		subvertexType.setKind(kind);
                    	}
                    	
                    	regionType.addSubvertex(subvertexType);

                    } 
            	}
            	stateType.addRegion(regionType);
            	
            }
            else if (stateField.eContainingFeature().getName().equals(CONNECTION_POINT)) {
            	
            	String name = stateField.eGet(stateField.eClass().getEStructuralFeature("name")).toString();
            	String kind = stateField.eGet(stateField.eClass().getEStructuralFeature("kind")).toString();
            	ConnectionPointType connectionPointType = new ConnectionPointType(name, kind);      
            	stateType.addConnectionPoint(connectionPointType);
            	
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
