package parser.Entities;

public class TransitionType {
	private String name;
    private String type = "";
    private String defaultValue = "";
    
    private String source= "";
    private String target= "";
    
	public TransitionType(String source, String target, String name) {
		this.setSource(source);
		this.setTarget(target);
		this.setName(name);
	}
    
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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
}
