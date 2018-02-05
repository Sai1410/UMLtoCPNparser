package parser.Entities;

import java.util.ArrayList;
import java.util.List;

public class StateMachineType {
    private String name;
    private List<RegionType> regionList = new ArrayList<>();

    
    public StateMachineType(String name) {
        this.setName(name);
    }

    public void addRegion(RegionType region) {
    	this.regionList.add(region);
    }
    
	public String getName() {
		return name;
	}

	public List<RegionType> getRegionList(){
		return regionList;
	}

	public void setName(String name) {
		this.name = name;
	}
}
