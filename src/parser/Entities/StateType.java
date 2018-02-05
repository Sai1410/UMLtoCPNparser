package parser.Entities;

import java.util.ArrayList;
import java.util.List;

public class StateType {
    private String name;
    private List<RegionType> regionList = new ArrayList<>();
    private List<ConnectionPointType> connectionPointList = new ArrayList<>();
    
    public StateType(String name) {
        this.setName(name);
    }

    public void addRegion(RegionType region) {
    	this.regionList.add(region);
    }
   
    public void addConnectionPoint(ConnectionPointType connectionPoint) {
    	this.connectionPointList.add(connectionPoint);
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

	public List<ConnectionPointType> getConnectionPointList() {
		return connectionPointList;
	}

	public void setConnectionPointList(List<ConnectionPointType> connectionPointList) {
		this.connectionPointList = connectionPointList;
	}
}
