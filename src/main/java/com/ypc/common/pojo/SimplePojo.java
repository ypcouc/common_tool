package com.ypc.common.pojo;

public class SimplePojo {

    private String id;
    private String name;
    private String code;
    private int status;
    
    

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }

        SimplePojo simplePojo = (SimplePojo)obj;
        return (this.id == simplePojo.getId() || (this.id != null && this.id.equals(simplePojo.getId())));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (null == this.code ? 0 : this.code.hashCode());
        return hash;
    }
}
