package it.meta.res.metaRes.utility;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PodUtils {

    public static void getXMLString(Node node, boolean withoutNamespaces, StringBuffer buff, boolean endTag, boolean readChild) {
	buff.append("<").append(namespace(node.getNodeName(), withoutNamespaces));

	if (node.hasAttributes()) {
	    buff.append(" ");

	    NamedNodeMap attr = node.getAttributes();
	    int attrLenth = attr.getLength();
	    for (int i = 0; i < attrLenth; i++) {
		Node attrItem = attr.item(i);
		String name = namespace(attrItem.getNodeName(), withoutNamespaces);
		String value = attrItem.getNodeValue();

		buff.append(name).append("=").append("\"").append(value).append("\"");

		if (i < (attrLenth - 1)) {
		    buff.append(" ");
		}
	    }
	}

	if (readChild) {

	    if (node.hasChildNodes()) {
		buff.append(">");

		NodeList children = node.getChildNodes();
		int childrenCount = children.getLength();

		if (childrenCount == 1) {
		    Node item = children.item(0);
		    int itemType = item.getNodeType();
		    if (itemType == Node.TEXT_NODE) {
			if (item.getNodeValue() == null) {
			    buff.append("/>");
			} else {
			    buff.append(item.getNodeValue());
			    buff.append("</").append(namespace(node.getNodeName(), withoutNamespaces)).append(">");
			}

			endTag = false;
		    }
		}

		for (int i = 0; i < childrenCount; i++) {
		    Node item = children.item(i);
		    int itemType = item.getNodeType();
		    if ((itemType == Node.DOCUMENT_NODE) || (itemType == Node.ELEMENT_NODE)) {
			getXMLString(item, withoutNamespaces, buff, endTag, readChild);
		    }
		}
	    } else {
		if (node.getNodeValue() == null) {
		    buff.append("/>");
		} else {
		    buff.append(node.getNodeValue());
		    buff.append("</").append(namespace(node.getNodeName(), withoutNamespaces)).append(">");
		}

		endTag = false;
	    }
	} else {
	    buff.append(">");
	}
	if (endTag) {
	    buff.append("</").append(namespace(node.getNodeName(), withoutNamespaces)).append(">");
	}
    }

    private static String namespace(String str, boolean withoutNamespace) {
	if (withoutNamespace && str.contains(":")) {
	    return str.substring(str.indexOf(":") + 1);
	}

	return str;
    }

    public static void getAttributes(Node node, StringBuffer buff) {
	if (node.hasAttributes()) {
	    buff.append(" ");

	    NamedNodeMap attr = node.getAttributes();
	    int attrLenth = attr.getLength();
	    for (int i = 0; i < attrLenth; i++) {
		Node attrItem = attr.item(i);
		String name = namespace(attrItem.getNodeName(), false);
		String value = attrItem.getNodeValue();

		buff.append(name).append("=").append("\"").append(value).append("\"");

		if (i < (attrLenth - 1)) {
		    buff.append(" ");
		}
	    }
	}
    }

    public static void getChild(Node node, StringBuffer buff, Boolean endTag, boolean readChild) {

	if (node.hasChildNodes()) {
	    // buff.append(">");

	    NodeList children = node.getChildNodes();
	    int childrenCount = children.getLength();

	    if (childrenCount == 1) {
		Node item = children.item(0);
		int itemType = item.getNodeType();
		if (itemType == Node.TEXT_NODE) {
		    if (item.getNodeValue() == null) {
			buff.append("/>");
		    } else {
			buff.append(item.getNodeValue());
			buff.append("</").append(namespace(node.getNodeName(), true)).append(">");
		    }

		    endTag = false;
		}
	    }

	    for (int i = 0; i < childrenCount; i++) {
		Node item = children.item(i);
		int itemType = item.getNodeType();
		if ((itemType == Node.DOCUMENT_NODE) || (itemType == Node.ELEMENT_NODE)) {
		    getXMLString(item, true, buff, endTag, readChild);
		}
	    }
	} else {
	    if (node.getNodeValue() == null) {
		buff.append("/>");
	    } else {
		buff.append(node.getNodeValue());
		buff.append("</").append(namespace(node.getNodeName(), true)).append(">");
	    }

	    endTag = false;
	}

	if (endTag) {
	    buff.append("</").append(namespace(node.getNodeName(), true)).append(">");
	}
    }
}
