package org.tomking.Tomking.enums;

/**
 * 
 * @author pipihao@qq.com
 *
 */
public enum FieldBind {
	/*
	 * primary key 
	 * foreign key 
	 * unique
	 * not null
	 * auto_increment
	 * default
	 */
//	DEFAULT("DEFAULT"),
	PRIMARYKEY(" PRIMARY KEY "),//主键
	NOTNULL(" NOT NULL "), //不为NULL
	AUTOINCREMENT(" auto_increment "), //自增
	UNIQUE(" UNIQUE "); //唯一
	
	private String name;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private FieldBind(String name) {
		this.name = name;
	}

}
