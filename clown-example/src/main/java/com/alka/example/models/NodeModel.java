package com.alka.example.models;

//import com.clown.lightdb.annotations.LightTable;

//@LightTable(name="ms_nodes")
public class NodeModel {
	
	private Integer id;
	private String nodeName;
	private String nodeUrl;
	private Integer status;
	private String nodeDesc;
	private Integer parentId;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeUrl() {
		return nodeUrl;
	}
	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getNodeDesc() {
		return nodeDesc;
	}
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	
}
 