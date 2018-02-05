package parser.Entities;

public class SubvertexType {
    private String type;
    private String name;
    private String kind;
    
	public SubvertexType(String name, String type) {
		this.setName(name);
		this.setType(type);
		this.kind = null;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
