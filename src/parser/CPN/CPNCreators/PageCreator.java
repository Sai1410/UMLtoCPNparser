package parser.CPN.CPNCreators;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Entities.ClassType;

public class PageCreator extends CPNCreator{

	private final static String pageTag = "page";
	
	private Element page;
	
    private PageCreator(Document document, String name){
        setPage(createPage(document, name));
    }

    public static Element pageFromName(String name, Document document) {
        PageCreator pageCreator = new PageCreator(document, name);
        return pageCreator.getPage();
    }
    
	private Element createPage(Document document, String name) {
		
		Element page = document.createElement(pageTag);
		page.setAttribute("id",IdCreator.getInstance().getNewId());
		
		
        Element pageattr = document.createElement("pageattr");
        pageattr.setAttribute("name", name);
        page.appendChild(pageattr);
		
        Element constraints = document.createElement("constraints");
        page.appendChild(constraints);
		return page;
	}

	public Element getPage() {
		return page;
	}

	public void setPage(Element page) {
		this.page = page;
	}
	
}
