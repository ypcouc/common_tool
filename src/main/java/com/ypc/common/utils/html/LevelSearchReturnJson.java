package com.ypc.common.utils.html;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * 多级查询返回类型
 * @author cff
 *
 */
@JsonIgnoreProperties(value = {"handler"})
public class LevelSearchReturnJson implements Serializable {
	private static final long serialVersionUID = 1L;
	/**id*/
	private String id;
	/**uuid*/
	private String uuid;

	/** 值*/
	private String value;
	
	/** 说明*/
	private String label;

	/**物料类别编号*/
	private String materialTypeCode;

	/**公司id*/
	private String companyId;
	/**工厂id*/
	private String factoryId;
	/**级别从0开始*/
	private Integer level;
	/**产线id*/
	private String productionLineId;
	/**子节点*/
	private List<LevelSearchReturnJson> children;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getProductionLineId() {
		return productionLineId;
	}

	public void setProductionLineId(String productionLineId) {
		this.productionLineId = productionLineId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public String getMaterialTypeCode() {
		return materialTypeCode;
	}

	public void setMaterialTypeCode(String materialTypeCode) {
		this.materialTypeCode = materialTypeCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<LevelSearchReturnJson> getChildren() {
		if(this.children != null && this.children.size() == 0){
			this.children = null;
		}
		return children;
	}

	public void setChildren(List<LevelSearchReturnJson> children) {
		this.children = children;
	}
	
}
