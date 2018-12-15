package cn.itcast.web.action;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Standard;
import cn.whj.service.StandardService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

	//获取参数
	private Standard model = new Standard();
	@Override
	public Standard getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	@Autowired
	private StandardService standardService;
	
	//保存收派标准
				
	@Action(value="standardAction_add",results = {
			@Result(name="success", location="/pages/base/standard.jsp")
	})
	public String add(){
		standardService.add(model);
		return "success";
	}
	
	//分页参数的获取
	private int page;
	private int rows;
	
	
	public void setPage(int page) {
		this.page = page;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}


	@Action(value="standardAction_pagequery")
	public String pagequery() throws IOException{
		//封装分页查询参数
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Standard> page = standardService.findAllPage(pageable);
		//将查到 的分页数据转化为json
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		JSONObject fromObject = JSONObject.fromObject(map);
		String json = fromObject.toString();
		//返回到前台
		//设置字符格式，防止乱码
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().println(json);
		return NONE;
	}
	
	/**
	 * 查询所有收派标准数据，转换成json数组，返回到界面
	 * @return
	 * @throws IOException 
	 */
	@Action(value="standardAction_findAll")
	public String findAll() throws IOException{
		//1.查询所有收派标准数据
		List<Standard> list = standardService.findAll();
		//2.转换成json数组
		JSONArray jsonArray = JSONArray.fromObject(list);
		String json = jsonArray.toString();
		//3.返回到界面
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
		return NONE;
	}
}
