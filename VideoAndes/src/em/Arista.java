package em;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

class Arista {
	private String tableName;
	private List<Field> ids;
	private List<Arista> foreings;
	private List<Field> foreingsField;

	public Arista(String tableName, List<Field> ids, List<Arista> foreings, List<Field> foreingsField) {
		super();
		this.tableName = tableName;
		this.ids = ids;
		this.foreings = foreings;
		this.foreingsField = foreingsField;
	}

	public final static String listFormat(List<Field> ids, String sep) {
		Iterator<Field> iter = ids.iterator();
		if (!iter.hasNext())
			return "";
		String ans = " " + iter.next().getName() + " ";
		while (iter.hasNext())
			ans += sep + " " + iter.next().getName() + " ";
		return ans;
	}

	public final static String listFormatString(List<String> ids, String sep) {
		Iterator<String> iter = ids.iterator();
		if (!iter.hasNext())
			return "";
		String ans = " " + iter.next() + " ";
		while (iter.hasNext())
			ans += sep + " " + iter.next() + " ";
		return ans;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Field> getIds() {
		return ids;
	}

	public void setIds(List<Field> ids) {
		this.ids = ids;
	}

	public List<Arista> getForeings() {
		return foreings;
	}

	public void setForeings(List<Arista> foreings) {
		this.foreings = foreings;
	}

	public List<Field> getForeingsField() {
		return foreingsField;
	}

	public void setForeingsField(List<Field> foreingsField) {
		this.foreingsField = foreingsField;
	}

}