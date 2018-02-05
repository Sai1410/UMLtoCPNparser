package parser.Entities;

import java.util.ArrayList;
import java.util.List;

public class RegionType {
	private String name;
	private List<TransitionType> transitionList = new ArrayList<>();
	private List<SubvertexType> subvertexList = new ArrayList<>();
	private List<StateType> stateList = new ArrayList<>();
	
	public RegionType(String name) {
		this.setName(name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    public void addTransition(TransitionType transitionType){
        transitionList.add(transitionType);
    }
    
    public void addSubvertex(SubvertexType subvertexType){
    	subvertexList.add(subvertexType);
    }
    public void addState(StateType stateType){
    	stateList.add(stateType);
    }
    public List<SubvertexType> getSubvertexList() {
    	return this.subvertexList;
    }
    
    public List<TransitionType> getTransitionList() {
    	return this.transitionList;
    }

    public List<StateType> getStateList() {
    	return this.stateList;
    }
    
}
