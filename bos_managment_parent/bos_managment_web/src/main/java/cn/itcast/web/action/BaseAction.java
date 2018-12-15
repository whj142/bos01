/**
 * 
 */
package cn.itcast.web.action;



import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author wuhuijie
 *2018年12月12日
 * 
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected T model;
	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	public BaseAction() {
		//AreaAction extends BaseAction<Area>
		//AreaAction areaAction = new AreaAction();
		//this == areaAction
		//this.getClass == AreaAction.class
		//this.getClass().getGenericSuperclass() == BaseAction<Area>.class
		ParameterizedType parameterizedType = (ParameterizedType)this.getClass().getGenericSuperclass();
		//parameterizedType.getActualTypeArguments()==[Area.class]
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		Class<T> entityClass = (Class<T>)actualTypeArguments[0];
		try {
			model = entityClass.newInstance();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
	}
	
	protected int page;
	protected int rows;
	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	/**
	 * 将page转化json
	 * @throws IOException 
	 */
	public void write2JsonObject(Page<?> page,String[] excludes) throws IOException {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		//将map转化为json
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject fromObject = JSONObject.fromObject(map, jsonConfig);
		String json = fromObject.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	
	/**
	 * 将list转化为jsonarr
	 * @throws IOException 
	 */
	public void write2JsonArray(List<?> list,String[] excludes) throws IOException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONArray array = JSONArray.fromObject(list, jsonConfig);
		String json = array.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
		
	}

}
