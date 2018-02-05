package parser.Entities;

public class ConnectionPointType {
	private String name;
	private String kind;
	
	
	public ConnectionPointType(String name, String kind) {
		this.name = name;
		this.kind = kind;
		
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	
}
