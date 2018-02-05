package parser.CPN;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import parser.CPN.CPNCreators.ArcCreator;
import parser.CPN.CPNCreators.AttributeType;
import parser.CPN.CPNCreators.ColorCreator;
import parser.CPN.CPNCreators.IdCreator;
import parser.CPN.CPNCreators.PageCreator;
import parser.CPN.CPNCreators.PlaceCreator;
import parser.CPN.CPNCreators.PositionPicker;
import parser.CPN.CPNCreators.TransCreator;
import parser.Entities.ClassType;
import parser.Entities.ConnectionPointType;
import parser.Entities.OperationType;
import parser.Entities.RegionType;
import parser.Entities.StateMachineType;
import parser.Entities.StateType;
import parser.Entities.SubvertexType;
import parser.Entities.TransitionType;

import java.util.ArrayList;
import java.util.List;

public class CPNParser {
    private Document document;

    private final static String newPageID = "ID6";
    private final static String colsetBlockID = "ID1";
    private final static String cpnnetTag = "cpnet";
    private final static String instancesTag = "instances";
    private final static String instanceTag = "instance";

    
    public CPNParser(Document document) {
        this.document = document;
    }

    public void addDataToDocument(List<ClassType> classTypeList, List<StateMachineType> stateMachineList){
        Element page = document.getElementById(newPageID);
        Element colsetBlock = document.getElementById(colsetBlockID);
        Element cpnnet = (Element)document.getElementsByTagName(cpnnetTag).item(0);
        Element instances = (Element)document.getElementsByTagName(instancesTag).item(0);
        
        NodeList instance_list = instances.getElementsByTagName(instanceTag);
        
        System.out.println("ADDING DATA");
        for (ClassType classType: classTypeList){

            Element place = PlaceCreator.placeFromClass(classType, document);
            page.appendChild(place);
            for (Element prerequisite :ColorCreator.colorPrerequisites(classType,document)) {
                colsetBlock.appendChild(prerequisite);
            }
            colsetBlock.appendChild(ColorCreator.colorFromClass(classType,document));

            for (OperationType operationType: classType.getOperationList()) {
                Element transition = TransCreator.createTransitionFromOperation(operationType, document);
                page.appendChild(transition);
                Element arc = ArcCreator.createArcForField(place, transition, document);
                page.appendChild(arc);
            }
        }
        for (StateMachineType stateMachineType: stateMachineList){
        	List<Element> placeList = new ArrayList<>();
        	
        	for (RegionType regionType: stateMachineType.getRegionList()) {
        		// Add Places
        		for(SubvertexType subvertexType: regionType.getSubvertexList()) {
        			if(subvertexType.getType().equals("StateImpl")) {
        				// Add inputs and outputs for states
        				Element input_place = PlaceCreator.placeFromSubvertex(subvertexType, document, "input");
        				Element output_place = PlaceCreator.placeFromSubvertex(subvertexType, document, "output");
        				Element place_temp = input_place;
        				
        				place_temp.setAttribute("name", "input" + subvertexType.getName());
        				place_temp.setAttribute("kind", "input");
        				placeList.add(place_temp);
        				place_temp = output_place;
        				place_temp.setAttribute("name", "output" + subvertexType.getName());
        				place_temp.setAttribute("kind", "output");
        				placeList.add(place_temp);
        				
        				page.appendChild(input_place);
        				page.appendChild(output_place);
        				
        			} else {
        				// Add rest of places
    	            	Element place = PlaceCreator.placeFromSubvertex(subvertexType, document, null);
    	            	Element place_temp = place;
    	            	
    	            	place_temp.setAttribute("name", subvertexType.getName());
    	            	
    	            	placeList.add(place_temp);
    	            	page.appendChild(place);
        			}
        		}
        	}
        	
        	// Check if inside states there are entry points - if there are, then create places for them
        	for (RegionType regionType: stateMachineType.getRegionList()) {
	    		for (StateType stateType: regionType.getStateList()) {
	    			for (ConnectionPointType connectionType: stateType.getConnectionPointList()) {
	    				if (connectionType.getKind() == "entryPoint" || connectionType.getKind() == "exitPoint" ) {
	    	            	Element place = PlaceCreator.placeFromConnectionPoint(connectionType, document);
	    	            	Element place_temp_for_state = place;
	    	            	
	    	            	place_temp_for_state.setAttribute("name", connectionType.getName());
	    	            	
	    	            	placeList.add(place_temp_for_state);
	    	            	page.appendChild(place);
	    				}
	    			}
	    		}
        	}
        	
        	// Add Transitions from States
        	for (RegionType regionType: stateMachineType.getRegionList()) {
	            for (StateType stateType: regionType.getStateList()) {
    				Element transition = TransCreator.createTransitionFromState(stateType, document);
    				page.appendChild(transition); 
	                Element arc_source = ArcCreator.createArcSourceToTransition(placeList, "input" + stateType.getName(), transition, document);
	                page.appendChild(arc_source);
	                Element arc_target = ArcCreator.createArcTransitionToTarget(placeList, "output" + stateType.getName(), transition, document);
	                page.appendChild(arc_target);  				
	                addStates(stateType, page, cpnnet, instance_list, transition, placeList, null);
	                
	            	// Create Transitions for  Connection Points
	                for (ConnectionPointType connectionType: stateType.getConnectionPointList()) {
	                	if(connectionType.getKind() == "entryPoint") {
	                    	Element transition_for_output = TransCreator.createTransitionForInputOutput(connectionType.getName(), document);
	                    	page.appendChild(transition_for_output); 
	                    	Element arc_source_connection = ArcCreator.createArcSourceToTransition(placeList, connectionType.getName(), transition_for_output, document);
	                    	page.appendChild(arc_source_connection);
	                        Element arc_target_connection = ArcCreator.createArcTransitionToTarget(placeList, stateType.getName(), transition_for_output, document);
	                        page.appendChild(arc_target_connection);  
	                	} else if (connectionType.getKind() == "exitPoint") {
	                    	Element transition_for_output = TransCreator.createTransitionForInputOutput(connectionType.getName(), document);
	                    	page.appendChild(transition_for_output); 
	                    	Element arc_source_connection = ArcCreator.createArcSourceToTransition(placeList, stateType.getName() , transition_for_output, document);
	                    	page.appendChild(arc_source_connection);
	                        Element arc_target_connection = ArcCreator.createArcTransitionToTarget(placeList, connectionType.getName() , transition_for_output, document);
	                        page.appendChild(arc_target_connection);  
	                	}
	                }
	            }
        	}
        	
        	// Add Transitions from Transitions
        	for (RegionType regionType: stateMachineType.getRegionList()) {
	            for (TransitionType transitionType: regionType.getTransitionList()) {
	                Element transition = TransCreator.createTransitionFromTransition(transitionType, document);
	                page.appendChild(transition); 
	                Element arc_source = ArcCreator.createArcSourceToTransition(placeList, transitionType.getSource(), transition, document);
	                page.appendChild(arc_source);
	                Element arc_target = ArcCreator.createArcTransitionToTarget(placeList, transitionType.getTarget(), transition, document);
	                page.appendChild(arc_target);
	            }	
        	}
        }
    }

    public Document getParsedDocument(){
        return document;
    }

    public void addStates(StateType stateType, Element page, Element cpnnet, NodeList instanceList, Element transition, List<Element> placeList, String transID) {
    	Element instance_append = null;
    	NodeList instancelist_inside = null;
		// For every state Create Page
		Element subpage = PageCreator.pageFromName( stateType.getName(), document);
		
		// Add instances of Page
		for(int i = 0; i< instanceList.getLength(); i++) {
			Element instance = (Element)instanceList.item(i);
			if(instance.getAttribute("page").equals(page.getAttribute("id")) || instance.getAttribute("trans").equals(transID)) {
					instance_append = document.createElement(instanceTag);
					instance_append.setAttribute("id", IdCreator.getInstance().getNewId());
					instance_append.setAttribute("trans", transition.getAttribute("id"));
					instance.appendChild(instance_append);
					instancelist_inside = instance.getElementsByTagName(instanceTag);
					break;
			}
		}
		
		// Add CPN Sheet
		Element sheets = (Element)document.getElementsByTagName("sheets").item(0);
		
		Element cpnsheet = document.createElement("cpnsheet");
		cpnsheet.setAttribute("id", IdCreator.getInstance().getNewId());
		cpnsheet.setAttribute("panx", "0.000000");
		cpnsheet.setAttribute("pany", "0.000000");
		cpnsheet.setAttribute("zoom", "1.000000");	
		cpnsheet.setAttribute("instance", instance_append.getAttribute("id"));
		
		Element zorder = document.createElement("zorder");
		Element position = document.createElement("position");
		position.setAttribute("value", "0");
		
		zorder.appendChild(position);
		cpnsheet.appendChild(zorder);
		
		sheets.appendChild(cpnsheet);
		
		// Inside page put logic
    	List<Element> placeList_inside = new ArrayList<>();
    	
    	// Add inputPlaces and outputPlaces
		Element input_place = PlaceCreator.placeForInputOutput("input", document);
		Element output_place = PlaceCreator.placeForInputOutput("output", document);
		Element place_temp = input_place;
		
		place_temp.setAttribute("name", "input");
		place_temp.setAttribute("kind", "input");
		placeList_inside.add(place_temp);
		place_temp = output_place;
		place_temp.setAttribute("name", "output");
		place_temp.setAttribute("kind", "output");
		placeList_inside.add(place_temp);
		
		Element port_input = document.createElement("port");
		port_input.setAttribute("id", IdCreator.getInstance().getNewId());
		port_input.setAttribute("type", "In");
		input_place.appendChild(port_input);
		
		Element port_output = document.createElement("port");
		port_output.setAttribute("id", IdCreator.getInstance().getNewId());
		port_output.setAttribute("type", "Out");
		output_place.appendChild(port_output);
		
		subpage.appendChild(input_place);
		subpage.appendChild(output_place);
		
    	for (RegionType regionType: stateType.getRegionList()) {
    		// Add Places
    		for(SubvertexType subvertexType: regionType.getSubvertexList()) {
    			if(subvertexType.getType().equals("StateImpl")) {
    				// Add inputs and outputs for states
    				Element input_place_for_state = PlaceCreator.placeFromSubvertex(subvertexType, document, "input");
    				Element output_place_for_state = PlaceCreator.placeFromSubvertex(subvertexType, document, "output");
    				Element place_temp_for_state = input_place_for_state;
    				
    				place_temp_for_state.setAttribute("name", "input" + subvertexType.getName());
    				place_temp_for_state.setAttribute("kind", "input");
    				placeList_inside.add(place_temp_for_state);
    				place_temp_for_state = output_place_for_state;
    				place_temp_for_state.setAttribute("name", "output" + subvertexType.getName());
    				place_temp_for_state.setAttribute("kind", "output");
    				placeList_inside.add(place_temp_for_state);
    				
    				subpage.appendChild(input_place_for_state);
    				subpage.appendChild(output_place_for_state);
    				
    			} else {
    				// Add rest of places
	            	Element place = PlaceCreator.placeFromSubvertex(subvertexType, document, null);
	            	Element place_temp_for_state = place;
	            	
	            	place_temp_for_state.setAttribute("name", subvertexType.getName());
	            	
	            	placeList_inside.add(place_temp_for_state);
	            	subpage.appendChild(place);
    			}
    		}
    	}
    	// Add connectionPoints as places
        for (ConnectionPointType connectionType: stateType.getConnectionPointList()) {
        	if(connectionType.getKind() == "entryPoint" || connectionType.getKind() == "exitPoint") {
            	Element place = PlaceCreator.placeFromConnectionPoint(connectionType, document);
            	Element place_temp_for_state = place;
            	
            	place_temp_for_state.setAttribute("name", connectionType.getName());
            	
            	placeList_inside.add(place_temp_for_state);
            	subpage.appendChild(place);
        	}
        }
    	
    	// Add transitions
        // Add Transition from Transition
    	for (RegionType regionType: stateType.getRegionList()) {
            for (TransitionType transitionType: regionType.getTransitionList()) {
                Element transition_inside = TransCreator.createTransitionFromTransition(transitionType, document);
                subpage.appendChild(transition_inside); 
                Element arc_source = ArcCreator.createArcSourceToTransition(placeList_inside, transitionType.getSource(), transition_inside, document);
                subpage.appendChild(arc_source);
                Element arc_target = ArcCreator.createArcTransitionToTarget(placeList_inside, transitionType.getTarget(), transition_inside, document);
                subpage.appendChild(arc_target);
            }	
    	}
    	// Add Transitions from States
    	for (RegionType regionType: stateType.getRegionList()) {
    		
            for (StateType stateType_inside: regionType.getStateList()) {
				// Add transition from state
				Element transition_inside = TransCreator.createTransitionFromState(stateType_inside, document);
				subpage.appendChild(transition_inside); 
                Element arc_source = ArcCreator.createArcSourceToTransition(placeList_inside, "input" + stateType_inside.getName(), transition_inside, document);
                subpage.appendChild(arc_source);
                Element arc_target = ArcCreator.createArcTransitionToTarget(placeList_inside, "output" + stateType_inside.getName(), transition_inside, document);
                subpage.appendChild(arc_target);  				
                addStates(stateType_inside, subpage, cpnnet, instancelist_inside, transition_inside, placeList_inside, transition.getAttribute("id"));
                
            	// Create Transitions for  Connection Points
                for (ConnectionPointType connectionType: stateType_inside.getConnectionPointList()) {
                	if(connectionType.getKind() == "entryPoint") {
                    	Element transition_for_output = TransCreator.createTransitionForInputOutput(connectionType.getName(), document);
                    	subpage.appendChild(transition_for_output); 
                    	Element arc_source_connection = ArcCreator.createArcSourceToTransition(placeList_inside, connectionType.getName(), transition_for_output, document);
                    	subpage.appendChild(arc_source_connection);
                        Element arc_target_connection = ArcCreator.createArcTransitionToTarget(placeList_inside, stateType_inside.getName(), transition_for_output, document);
                        subpage.appendChild(arc_target_connection);  
                	} else if (connectionType.getKind() == "exitPoint") {
                    	Element transition_for_output = TransCreator.createTransitionForInputOutput(connectionType.getName(), document);
                    	subpage.appendChild(transition_for_output); 
                    	Element arc_source_connection = ArcCreator.createArcSourceToTransition(placeList_inside, stateType_inside.getName() , transition_for_output, document);
                    	subpage.appendChild(arc_source_connection);
                        Element arc_target_connection = ArcCreator.createArcTransitionToTarget(placeList_inside, connectionType.getName() , transition_for_output, document);
                        subpage.appendChild(arc_target_connection);  
                	}
                }
            
            }
    	}
		
    	// Create Transition from inputs to ... or from ... to output
    	int intial_final_states_number = 0;
    	for (RegionType regionType: stateType.getRegionList()) {
    		
            for (SubvertexType subvertexType: regionType.getSubvertexList()) {
            	// ... Inital States
            	if (subvertexType.getKind() == "initial"){
                	Element transition_from_input = TransCreator.createTransitionForInputOutput("inputTo" + subvertexType.getName(), document);
                	subpage.appendChild(transition_from_input); 
                	Element arc_source = ArcCreator.createArcSourceToTransition(placeList_inside, "input", transition_from_input, document);
                    subpage.appendChild(arc_source);
                    Element arc_target = ArcCreator.createArcTransitionToTarget(placeList_inside, subvertexType.getName(), transition_from_input, document);
                    subpage.appendChild(arc_target); 
                    intial_final_states_number ++;
            	}
            	
            	// ... Final States
            	else if(subvertexType.getType().equals("FinalStateImpl")) {
                	Element transition_for_output = TransCreator.createTransitionForInputOutput("outputTo" + subvertexType.getName(), document);
                	subpage.appendChild(transition_for_output); 
                	Element arc_source = ArcCreator.createArcSourceToTransition(placeList_inside, subvertexType.getName(), transition_for_output, document);
                    subpage.appendChild(arc_source);
                    Element arc_target = ArcCreator.createArcTransitionToTarget(placeList_inside, "output" , transition_for_output, document);
                    subpage.appendChild(arc_target);  
                    intial_final_states_number ++;
                
            	} 
            }    
    	}
    	
    	// ... Connection Points
        for (ConnectionPointType connectionType: stateType.getConnectionPointList()) {
        	if(connectionType.getKind() == "entryPoint") {
            	Element transition_for_output = TransCreator.createTransitionForInputOutput(connectionType.getName(), document);
            	subpage.appendChild(transition_for_output); 
            	Element arc_source = ArcCreator.createArcSourceToTransition(placeList_inside, "input", transition_for_output, document);
                subpage.appendChild(arc_source);
                Element arc_target = ArcCreator.createArcTransitionToTarget(placeList_inside, connectionType.getName() , transition_for_output, document);
                subpage.appendChild(arc_target);  
                intial_final_states_number ++;
        	} else if (connectionType.getKind() == "exitPoint") {
            	Element transition_for_output = TransCreator.createTransitionForInputOutput(connectionType.getName(), document);
            	subpage.appendChild(transition_for_output); 
            	Element arc_source = ArcCreator.createArcSourceToTransition(placeList_inside, connectionType.getName() , transition_for_output, document);
                subpage.appendChild(arc_source);
                Element arc_target = ArcCreator.createArcTransitionToTarget(placeList_inside, "output" , transition_for_output, document);
                subpage.appendChild(arc_target);  
                intial_final_states_number ++;
        	}
        }
        
    	
    	
    	// If in state there is no initial and no final state there has to be a connection between init and output in the state (causing an error in CpnTools)
    	if(intial_final_states_number < 2) {
        	Element transition_for_empty_state = TransCreator.createTransitionForInputOutput("InputToOutput", document);
        	subpage.appendChild(transition_for_empty_state); 
        	Element arc_source = ArcCreator.createArcSourceToTransition(placeList_inside, "input" , transition_for_empty_state, document);
            subpage.appendChild(arc_source);
            Element arc_target = ArcCreator.createArcTransitionToTarget(placeList_inside, "output" , transition_for_empty_state, document);
            subpage.appendChild(arc_target);
    	}
    	
		// AddSubpage
		Element subst = document.createElement("subst");
		String input_id_from_page = "";
		String output_id_from_page = "";
		String portSock;
		subst.setAttribute("subpage", subpage.getAttribute("id"));
    	for(Element place: placeList) {
    		if(place.getAttribute("name").equals("input" + stateType.getName())) {
    			input_id_from_page = place.getAttribute("id");
    		}
    		if(place.getAttribute("name").equals("output" + stateType.getName())) {
    			output_id_from_page = place.getAttribute("id");
    		}
    	}
    	portSock = "(" + input_place.getAttribute("id") + "," + input_id_from_page + ")(" + output_place.getAttribute("id")  + "," + output_id_from_page + ")" ;
		subst.setAttribute("portsock", portSock);

		transition.appendChild(subst);
		
		// Add page to cpn net
		cpnnet.appendChild(subpage);

    }
}
